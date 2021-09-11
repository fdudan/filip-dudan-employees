package com.sirma.task.employees.service;

import com.sirma.task.employees.service.model.EmployeeCouple;
import com.sirma.task.employees.service.model.EmployeeCsvModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service used for employee data processing.
 */
@Service
@RequiredArgsConstructor
public class EmployeeDataProcessor {
  private final EmployeeDataParser employeeDataParser;

  public List<EmployeeCouple> getAllCouples() throws ParseException {
    List<EmployeeCouple> employeeCouples = new LinkedList<>();
    List<EmployeeCsvModel> employees = employeeDataParser.parseEmployeesFromPath();
    for (Integer projectId : getProjects()) {
      //Find all employees that worked on the same project
      List<EmployeeCsvModel> employeesOnSameProject = employees.stream()
          .filter(e -> e.getProjectId() == projectId)
          .collect(Collectors.toList());

      //Iterate to find couples that worked at the same time
      for (int i = 0; i < employeesOnSameProject.size() - 1; i++) {
        for (int j = i + 1; j < employeesOnSameProject.size(); j++) {
          if (checkIfWorkedTogether(employeesOnSameProject.get(i), employeesOnSameProject.get(j)))
            employeeCouples.add(new EmployeeCouple(projectId,
                employeesOnSameProject.get(i),
                employeesOnSameProject.get(j)));
        }
      }
    }
    //Sort employee couples ascending.
    employeeCouples.sort((o1, o2) -> o1.getTimeWorkedTogether() <= o2.getTimeWorkedTogether() ? 1 : -1);
    return employeeCouples;
  }

  /**
   * Checks if employees worked together.
   *
   * @param employee1 {@link EmployeeCsvModel} an employee to compare
   * @param employee2 {@link EmployeeCsvModel} an employee to compare
   * @return true if employees worked together, else false.
   */
  private boolean checkIfWorkedTogether(EmployeeCsvModel employee1, EmployeeCsvModel employee2){

    EmployeeCsvModel firstEmployee, secondEmployee;
    //Sort employees by their start date.
    if (employee1.getDateFrom().before(employee2.getDateFrom()) ||
        employee1.getDateFrom().equals(employee2.getDateFrom())) {
      firstEmployee = employee1;
      secondEmployee = employee2;
    } else {
      firstEmployee = employee2;
      secondEmployee = employee1;
    }
    //return true if first employee still worked before second started
    return (firstEmployee.getDateTo().after(secondEmployee.getDateFrom()));

  }

  /**
   * Extracts the project IDs from the employees list.
   *
   * @return {@link List<Integer>} containing all project IDs.
   */
  public List<Integer> getProjects() {
    return employeeDataParser.parseEmployeesFromPath()
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
