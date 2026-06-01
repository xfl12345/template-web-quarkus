package org.acme;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.runtime.annotations.RegisterResources;
import io.quarkus.runtime.annotations.StaticInitSafe;
import io.quarkus.vertx.core.runtime.config.VertxConfiguration;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
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

    private static final Map<String, String> CONFIG;

    static {
        String osName = System.getProperty("os.name", "").toLowerCase();
        boolean unixLike = !osName.startsWith("windows");
        CONFIG = unixLike
                ? Map.of(
                "quarkus.vertx.prefer-native-transport", "true",
                "quarkus.http.domain-socket-enabled", "true")
                : Collections.emptyMap();
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
