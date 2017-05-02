package com.sharingif.cube.spring.boot.web.springmvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharingif.cube.context.annotation.RemoveLastImplOfDAOServiceAnnotationBeanNameGenerator;

@Controller
@EnableAutoConfiguration
@ComponentScan(basePackages="com.sharingif.cube.spring.boot.*",nameGenerator=RemoveLastImplOfDAOServiceAnnotationBeanNameGenerator.class)
public class SampleController {

	@Value("${wechat.token}")
	private String wechatToken;
	
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }

}
