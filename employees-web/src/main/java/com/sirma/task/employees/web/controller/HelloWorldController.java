package com.sirma.task.employees.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

  @GetMapping("/")
  public ModelAndView getIndexPage() {
    ModelAndView mv = new ModelAndView();

    mv.setViewName("index");
    return mv;
  }
}
