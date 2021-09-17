package com.sirma.task.employees.app.service;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sirma.task.employees.app.service.model.EmployeeCsvModel;
import com.sirma.task.employees.app.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


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
  private final EmployeeDataProcessor employeeDataProcessor;
  private final EmployeeRepository repository;

  /**
   * Tries to parse data from the filesystem from the latest upload.
   */
  public void parseEmployeesFromFile(MultipartFile file) {
    try {
      ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
      ms.setType(EmployeeCsvModel.class);

      Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
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
