package com.sirma.task.employees.web.controller;

import com.sirma.task.employees.service.EmployeeDataParser;
import com.sirma.task.employees.service.EmployeeService;
import com.sirma.task.employees.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
  private final EmployeeService employeeService;
  private final StorageService storageService;
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

  @PostMapping("/")
  public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file,
                                       @RequestParam("format") String format) {

    //Save new pattern
    dataParser.setParsingPattern(format);
    storageService.save(file);
    //Try to parse new data.
    dataParser.parseEmployeesFromPath();
    return new ModelAndView("redirect:/");
  }

  @GetMapping("/deleteAll")
  public ModelAndView deleteAll() {
    employeeService.deleteAllEmployees();
    employeeService.deleteAllCouples();
    return new ModelAndView("redirect:/");
  }
}
