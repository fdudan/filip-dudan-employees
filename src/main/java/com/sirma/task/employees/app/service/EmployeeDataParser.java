package com.sirma.task.employees.app.service;

import com.sirma.task.employees.app.service.model.EmployeeModel;
import com.sirma.task.employees.app.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Service for parsing the employee data from CSV file.
 */
@Service
@RequiredArgsConstructor
public class EmployeeDataParser {
  private final EmployeeDataProcessor employeeDataProcessor;
  private final EmployeeRepository repository;

  /**
   * Tries to parse data from the filesystem from the latest upload.
   */
  public void parseEmployeesFromFile(MultipartFile file) {
    try {
      List<EmployeeModel> employees = new LinkedList<>();

      BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] attributes = line.split(", ");
        //If the length is not 4 the file is not formatted correctly.
        if(attributes.length != 4)
          throw new IllegalArgumentException("Invalid file uploaded. " +
              "The file should be in format EmpID, ProjectID, DateFrom, DateTo. " +
              "No trailing spaces/lines are allowed!!");
        employees.add(new EmployeeModel(
            Integer.parseInt(attributes[0]),
            Integer.parseInt(attributes[1]),
            attributes[2],
            attributes[3]));
      }

      //Save all imported employees
      repository.saveAll(employees);
      //Generate couples
      employeeDataProcessor.createCouples();

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setParsingPattern(String parsingPattern) {
    EmployeeModel.datePattern = parsingPattern;
  }
}
