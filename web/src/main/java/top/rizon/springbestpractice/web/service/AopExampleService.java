package top.rizon.springbestpractice.web.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @author Rizon
 * @date 2019/12/26
 */
@Service
public class AopExampleService {

    @SneakyThrows
    public String sleepMethod() {
        Thread.sleep(5000);
        return "sleep 5s";
    }

}
