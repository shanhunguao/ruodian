package com.ncu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Created by zhoufan
 * @Date 2020/3/5 8:51
 */
@Controller
public class ViewController {

    /**
     * pc登录
     *
     * @PathVariable注解为动态url
     */
    @RequestMapping("/sso/{login}")
    public String test(@PathVariable String login) {
        return "/sso/" + login;
    }


}
