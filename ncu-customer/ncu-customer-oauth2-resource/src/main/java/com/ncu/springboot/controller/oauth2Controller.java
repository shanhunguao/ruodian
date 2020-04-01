package com.ncu.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Created by zhoufan
 * @Date 2020/3/31 9:16
 */
@RestController
public class oauth2Controller {

    @RequestMapping("/view")
    public String view(){
        return "view";
    }

    @RequestMapping("/insert")
    public String insert(){
        return "insert";
    }

    @RequestMapping("/update")
    public String update(){
        return "update";
    }

    @RequestMapping("/delete")
    public String delete(){
        return "delete";
    }

}
