package com.example.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author freshchen
 * @since 2023/3/23
 */
@RestController
@Slf4j
public class Controller {


    @GetMapping("/ping")
    public String ping() {
        log.info("ping pong");
        return "pong";
    }

}
