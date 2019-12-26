package top.rizon.springbestpractice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.rizon.springbestpractice.web.service.AopExampleService;

/**
 * @author Rizon
 * @date 2019/12/26
 */
@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
@Slf4j
public class DemoController {
    private final AopExampleService aopService;

    @GetMapping("aop")
    public ResponseEntity<?> aopDemo() {
        return ResponseEntity.ok(aopService.sleepMethod());
    }
}
