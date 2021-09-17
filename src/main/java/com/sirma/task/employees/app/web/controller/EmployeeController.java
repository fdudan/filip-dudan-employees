package com.sirma.task.employees.app.web.controller;

import com.sirma.task.employees.app.service.EmployeeDataParser;
import com.sirma.task.employees.app.service.EmployeeService;
import com.sirma.task.employees.app.web.model.FileUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
  private final EmployeeService employeeService;
  private final EmployeeDataParser dataParser;

  @GetMapping("/")
  public ModelAndView getIndexPage() {
    ModelAndView mv = new ModelAndView();

    mv.addObject("employees", employeeService.getAllEmployees());
    mv.addObject("couples", employeeService.getAllCouples());
    mv.addObject("bestColleagues", employeeService.getBestColleagues());
    mv.setViewName("index");

    return mv;
  }


  @GetMapping("/upload")
  public ModelAndView handleFileUpload() {
    ModelAndView mv = new ModelAndView();

    mv.addObject("fileUploadDto", new FileUploadDto());
    mv.setViewName("upload");

    return mv;
  }
  @PostMapping("/upload")
  public ModelAndView handleFileUpload(@Valid @ModelAttribute("fileUploadDto") FileUploadDto fileUploadDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      ModelAndView mv = new ModelAndView();
      mv.addObject("fileUploadDto", fileUploadDto);
      mv.addObject("errors", bindingResult.getAllErrors());
      mv.setViewName("upload");
      return mv;
    }
    //Save new pattern
    dataParser.setParsingPattern(fileUploadDto.getPattern());
    //Try to parse new data.
    dataParser.parseEmployeesFromFile(fileUploadDto.getFile());
    return new ModelAndView("redirect:/");
  }

  @GetMapping("/deleteAll")
  public ModelAndView deleteAll() {
    employeeService.deleteAllEmployees();
    employeeService.deleteAllCouples();
    return new ModelAndView("redirect:/");
  }
}
