package com.sirma.task.employees.app.service.repository;

import com.sirma.task.employees.app.service.model.EmployeeCouple;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Mock repository for Couples. This is done in order to keep the data storage and
 * operations separate from business logic.
 */
@Component
public class CoupleRepository {
  List<EmployeeCouple> employeeCouples = new LinkedList<>();

  public void saveCouple(EmployeeCouple couple) {
    employeeCouples.add(couple);
  }

  public List<EmployeeCouple> getAllCouples() {
    return employeeCouples;
  }

  public void deleteAll() {
    employeeCouples = new LinkedList<>();
  }
}
