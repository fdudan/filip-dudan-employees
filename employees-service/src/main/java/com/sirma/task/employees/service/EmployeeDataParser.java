package com.sirma.task.employees.service;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sirma.task.employees.service.model.EmployeeCsvModel;
import com.sirma.task.employees.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Service for parsing the employee data from CSV file.
 */
@Service
@RequiredArgsConstructor
public class EmployeeDataParser {
  private final StorageService storageService;
  private final EmployeeDataProcessor employeeDataProcessor;
  private final EmployeeRepository repository;

  /**
   * Called on initialization to parse any data from the filesystem.
   */
  @PostConstruct
  public void onInit(){
    parseEmployeesFromPath();
  }

  /**
   * Tries to parse data from the filesystem from the latest upload.
   */
  public void parseEmployeesFromPath() {
    try {
      ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
      ms.setType(EmployeeCsvModel.class);

      InputStream in = storageService.load();
      Reader reader = new BufferedReader(new InputStreamReader(in));
      CsvToBean cb = new CsvToBeanBuilder(reader)
          .withType(EmployeeCsvModel.class)
          .withMappingStrategy(ms)
          .build();

      //Save all imported employees
      repository.saveAll(cb.parse());
      //Generate couples
      employeeDataProcessor.createCouples();

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setParsingPattern(String parsingPattern) {
    EmployeeCsvModel.datePattern = parsingPattern;
  }
}
