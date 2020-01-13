package cloud.spring_member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMemberApplication.class, args);
    }

}
