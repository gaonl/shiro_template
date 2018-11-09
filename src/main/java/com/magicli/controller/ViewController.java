package com.magicli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by gaonl on 2018/11/8.
 */
@Controller
public class ViewController {
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }    

    @RequestMapping(value = {"/unauthorized"}, method = RequestMethod.GET)
    public String unauthorized() {
        return "unauthorized";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/any"}, method = RequestMethod.GET)
    public String any() {
        return "any";
    }
}