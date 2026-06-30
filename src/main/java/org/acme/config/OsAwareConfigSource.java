package org.acme.config;

import io.quarkus.logging.Log;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.runtime.annotations.RegisterResources;
import io.quarkus.runtime.annotations.StaticInitSafe;
import io.quarkus.vertx.core.runtime.config.VertxConfiguration;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 根据操作系统类型自动提供默认配置。
 * <p>
 * 在 UNIX 兼容系统（Linux、macOS、BSD 等）上，自动启用 Vert.x native transport，
 * 这是 Unix Domain Socket 等特性的前提条件。
 * <p>
 * 使用最低 ordinal（1），
 * 确保配置文件中的显式值可以覆盖此默认值。
 *
 * @see <a href="https://quarkus.io/guides/config-extending-support">Extending Configuration Support</a>
 * {@link io.quarkus.vertx.http.runtime.VertxHttpConfig#domainSocket() quarkus.http.domain-socket}
 * {@link io.quarkus.vertx.http.runtime.VertxHttpConfig#domainSocketEnabled() quarkus.http.domain-socket-enabled}
 * {@link VertxConfiguration#preferNativeTransport() quarkus.vertx.prefer-native-transport}
 */
@RegisterForReflection(targets = {
        // io.netty.channel.epoll.Epoll.class,
        // io.netty.channel.epoll.Native.class,
        io.vertx.core.impl.transports.EpollTransport.class,
        io.vertx.core.impl.transports.KQueueTransport.class,
        io.vertx.core.impl.transports.JDKTransport.class
})
@RegisterResources(globs = {"META-INF/services/io.vertx.core.spi.transport.Transport"})
@StaticInitSafe
public class OsAwareConfigSource implements ConfigSource {

    Map<String, String> CONFIG = Collections.emptyMap();

    boolean isUnixLikeSystem() {
        String osName = System.getProperty("os.name", "").toLowerCase();
        return !osName.startsWith("windows");
    }

    void init() {
        if (!this.isUnixLikeSystem()) {
            return;
        }

        Path udsDirPath = Path.of("/var/run");
        Path fallbackUdsFilePath = Path.of("/tmp/app/quarkus.sock");
        var unixConfig = Map.of(
                "quarkus.vertx.prefer-native-transport", "true",
                "quarkus.http.domain-socket-enabled", "true");

        var writable = Files.isWritable(udsDirPath);
        if (!writable) {
            Log.warnf("目录 [%s] 不可写，UDS 的默认路径将使用备用路径 [%s]", udsDirPath, fallbackUdsFilePath);
            udsDirPath = fallbackUdsFilePath.getParent();
            try {
                Log.infof("正在尝试创建 UDS 文件目录[%s]", udsDirPath);
                Files.createDirectories(udsDirPath);
                Log.infof("UDS 文件目录[%s]创建成功", udsDirPath);
            } catch (FileAlreadyExistsException ignore) {
                Log.infof("UDS 文件目录[%s]已存在", udsDirPath);
            } catch (IOException | UnsupportedOperationException e) {
                Log.warnf(e, "尝试创建 UDS 文件目录[%s]失败", udsDirPath);
            }
            var tempMap = HashMap.<String, String>newHashMap(unixConfig.size() + 1);
            tempMap.putAll(unixConfig);
            tempMap.put("quarkus.http.domain-socket", fallbackUdsFilePath.toString());
            unixConfig = Map.copyOf(tempMap);
        }

        CONFIG = unixConfig;
    }

    public OsAwareConfigSource() {
        this.init();
    }

    @Override
    public int getOrdinal() {
        return 1;
    }

    @Override
    public Set<String> getPropertyNames() {
        return CONFIG.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return CONFIG.get(propertyName);
    }

    @Override
    public String getName() {
        return OsAwareConfigSource.class.getSimpleName();
    }
}
