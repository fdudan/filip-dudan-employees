package com.sirma.task.employees.app.service;

import com.sirma.task.employees.app.service.model.EmployeeCouple;
import com.sirma.task.employees.app.service.model.EmployeeModel;
import com.sirma.task.employees.app.service.repository.CoupleRepository;
import com.sirma.task.employees.app.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Employee service created to abstract repositories and business logic from the controller.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final CoupleRepository coupleRepository;
  private final EmployeeDataProcessor dataProcessor;


  public List<EmployeeModel> getAllEmployees() {
    return employeeRepository.getAllEmployees();
  }

  public List<EmployeeCouple> getAllCouples() {
    return coupleRepository.getAllCouples();
  }

  public Triple<Integer, Integer, Long> getBestColleagues() {
    return dataProcessor.getLongestWorkingTogetherCouple();
  }

  public void deleteAllEmployees() {
    employeeRepository.deleteAll();
  }

  public void deleteAllCouples() {
    coupleRepository.deleteAll();
  }
}
