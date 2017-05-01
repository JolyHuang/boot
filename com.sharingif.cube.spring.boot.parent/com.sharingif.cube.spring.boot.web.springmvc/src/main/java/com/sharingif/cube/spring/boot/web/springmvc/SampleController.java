package com.sharingif.cube.spring.boot.web.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharingif.cube.core.handler.chain.MDCChain;

@Controller
@SpringBootApplication(scanBasePackages="com.sharingif.cube.spring.boot.*")
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
    
    @Bean
    public MDCChain getMDCChainere() {
    	return new MDCChain();
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }

}
