package cn.net.health.user;


 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/11/6 11:45
 */
@EnableSwagger2
@SpringBootApplication
public class UserMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMongoApplication.class, args);
    }

}
