package org.acme.config;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.vertx.http.HttpServerStart;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Optional;
import java.util.Set;

/**
 * HTTP 服务器启动后，将 UDS 文件权限设为 {@code rwxrwxrwx}（等效 chmod 777）。
 *
 * <p>Quarkus 不提供 UDS 文件权限的配置项。
 * 默认权限受进程 umask 控制，无法满足 Nginx 反代等跨用户访问场景。
 * 通过监听 {@link StartupEvent}（HTTP 服务器已绑定端口、socket 文件已创建），
 * 在启动完成后立即放宽权限。</p>
 *
 * @see io.quarkus.vertx.http.runtime.VertxHttpConfig#domainSocket()
 */
@ApplicationScoped
public class UnixDomainSocketPermissionInitializer {

    /**
     * UDS 文件路径，默认与 Quarkus {@code VertxHttpConfig.domainSocket()} 一致。
     *
     * @see io.quarkus.vertx.http.runtime.VertxHttpConfig#domainSocket()
     */
    String getUdsPath(Config config) {
        Optional<String> optionalUdsPath = config.getOptionalValue("quarkus.http.domain-socket", String.class);
        return optionalUdsPath.orElse("/var/run/io.quarkus.app.socket");
    }

    void onStartup(@ObservesAsync HttpServerStart event) {
        // 通过 Config API 动态读取 domain-socket-enabled，
        // 避免作为 @ConfigProperty 注入导致 OsAwareConfigSource 的 ordinal=1 默认值无法被覆盖
        Config config = ConfigProvider.getConfig();
        boolean enabled = config.getValue("quarkus.http.domain-socket-enabled", Boolean.class);
        if (!enabled) {
            return;
        }

        String udsPath = getUdsPath(config);
        var path = Path.of(udsPath);
        if (!Files.exists(path)) {
            Log.warnf("UDS 文件不存在，跳过权限设置: %s", udsPath);
            return;
        }

        try {
            Files.setPosixFilePermissions(path, Set.of(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE,
                    PosixFilePermission.GROUP_READ,
                    PosixFilePermission.GROUP_WRITE,
                    PosixFilePermission.GROUP_EXECUTE,
                    PosixFilePermission.OTHERS_READ,
                    PosixFilePermission.OTHERS_WRITE,
                    PosixFilePermission.OTHERS_EXECUTE
            ));
            Log.infof("已设置 UDS 文件权限为 777: %s", udsPath);
        } catch (IOException e) {
            Log.errorf(e, "设置 UDS 文件权限失败: %s", udsPath);
        }
    }
}
