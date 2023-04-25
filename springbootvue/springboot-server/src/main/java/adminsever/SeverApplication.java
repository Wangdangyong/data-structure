package adminsever;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 提供对单个 Spring Boot 应用的监控，信息包含应用状态、内存、线程、堆栈等，
 * 比较全面的监控了 Spring Boot 应用的整个生命周期
 */
@EnableAdminServer
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class SeverApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeverApplication.class, args);
    }

}
