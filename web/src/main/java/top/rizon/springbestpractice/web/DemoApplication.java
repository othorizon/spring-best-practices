package top.rizon.springbestpractice.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Rizon
 */
@SpringBootApplication(scanBasePackages = "top.rizon.springbestpractice")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
