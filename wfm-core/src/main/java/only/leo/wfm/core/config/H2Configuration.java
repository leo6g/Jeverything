package only.leo.wfm.core.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * @Author: LEO
 * @Date: 2021/8/17 15:09
 */
@Configuration
public class H2Configuration {
    @Bean(name = "h2server")
    public Server h2server(){
        Server server =null;
        try {
            server = Server.createTcpServer("-tcp","-tcpAllowOthers");
            server.start();
            System.setProperty("h2.baseDir",System.getProperty("user.dir"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return server;
    }

}
