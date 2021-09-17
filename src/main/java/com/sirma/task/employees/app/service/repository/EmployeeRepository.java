package com.sirma.task.employees.app.service.repository;

import com.sirma.task.employees.app.service.model.EmployeeCsvModel;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Mock repository for Employees. This is done in order to keep the data storage and
 * operations separate from business logic.
 */
@Component
public class EmployeeRepository {
  private List<EmployeeCsvModel> employees = new LinkedList<>();

  public void saveAll(List<EmployeeCsvModel> employees) {
    this.employees.addAll(employees);
  }

  public void deleteAll() {
    this.employees = new LinkedList<>();
  }

  public List<EmployeeCsvModel> getAllEmployees() {
    return this.employees;
  }

  /**
   * Extracts the project IDs from the employees list.
   *
   * @return {@link List<Integer>} containing all project IDs.
   */
  public List<Integer> getAllProjects() {
    return this.employees
        .stream()
        .filter(distinctByKey(EmployeeCsvModel::getProjectId))
        .map(EmployeeCsvModel::getProjectId)
        .collect(Collectors.toList());
  }

  /**
   * Maps distinct elements.
   */
  public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
    Map<Object, Boolean> map = new ConcurrentHashMap<>();
    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

}
