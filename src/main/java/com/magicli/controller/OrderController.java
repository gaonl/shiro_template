package com.magicli.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by gaonl on 2018/11/8.
 */
@Controller
@RequestMapping(value = {"/order"})
public class OrderController implements ApplicationContextAware{
    ApplicationContext applicationContext = null;
    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete() {
        int a = 0;
        return "/order/delete";
    }

    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public String view() {
        return "/order/view";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
