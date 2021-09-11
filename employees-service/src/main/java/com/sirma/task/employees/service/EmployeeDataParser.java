package com.sirma.task.employees.service;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sirma.task.employees.service.model.EmployeeCsvModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for parsing the employee data from CSV file.
 */
@Service
public class EmployeeDataParser {
  @Value("${path.to.employeeFile}")
  private String pathURI;

  public List<EmployeeCsvModel> parseEmployeesFromPath() {
    List<EmployeeCsvModel> employeeList = new ArrayList<>();
    try {
      ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
      ms.setType(EmployeeCsvModel.class);

      InputStream in = getResourceAsStream(pathURI);
      Reader reader = new BufferedReader(new InputStreamReader(in));
      CsvToBean cb = new CsvToBeanBuilder(reader)
          .withType(EmployeeCsvModel.class)
          .withMappingStrategy(ms)
          .build();

      employeeList.addAll(cb.parse());

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return employeeList;
  }

  // ToDO: code for debugging purposes only, to be removed
  private void getResourceFiles(String path) throws IOException {
    try (
        InputStream in = getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
      String resource;
      while ((resource = br.readLine()) != null) {
        System.out.println(resource);
      }
    }
  }

  private InputStream getResourceAsStream(String resource) {
    final InputStream in
        = getContextClassLoader().getResourceAsStream(resource);

    return in == null ? getClass().getResourceAsStream(resource) : in;
  }

  private ClassLoader getContextClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }
}
