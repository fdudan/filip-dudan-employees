package com.sirma.task.employees.web.controller;

import com.sirma.task.employees.service.EmployeeDataParser;
import com.sirma.task.employees.service.EmployeeDataProcessor;
import com.sirma.task.employees.service.model.EmployeeCouple;
import com.sirma.task.employees.service.model.EmployeeCsvModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
  private final EmployeeDataParser dataParser;
  private final EmployeeDataProcessor employeeDataProcessor;

  @GetMapping("/")
  public ModelAndView getIndexPage() throws Exception {
    List<EmployeeCsvModel> employees = dataParser.parseEmployeesFromPath();
    //ToDo: debugging purposes only, to be replaced with proper logging.
    System.out.println("Projects: " + employeeDataProcessor.getProjects());
    System.out.println(employeeDataProcessor.getAllCouples());
    List<EmployeeCouple> couples = employeeDataProcessor.getAllCouples();
    for (EmployeeCouple couple : couples) {
      System.out.println("Couple that work together found: ");
      System.out.println(couple);
    }
    ModelAndView mv = new ModelAndView();
    mv.addObject("employees",employees);
    mv.addObject("couples",couples);
    mv.setViewName("index");
    return mv;
  }
}
