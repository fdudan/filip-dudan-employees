package com.sirma.task.employees.app.service;

import com.sirma.task.employees.app.service.model.EmployeeCouple;
import com.sirma.task.employees.app.service.model.EmployeeCsvModel;
import com.sirma.task.employees.app.service.repository.CoupleRepository;
import com.sirma.task.employees.app.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service used for employee data processing.
 */
@Service
@RequiredArgsConstructor
public class EmployeeDataProcessor {
  private final EmployeeRepository employeeRepository;
  private final CoupleRepository coupleRepository;

  /**
   * Iterates through all employees creating a couple if the the employees worked together on the
   * same project. Results are grouped by project and saved in the CoupleRepository
   */
  public void createCouples() {
    List<EmployeeCsvModel> employees = employeeRepository.getAllEmployees();
    for (Integer projectId : employeeRepository.getAllProjects()) {
      //Find all employees that worked on the same project
      List<EmployeeCsvModel> employeesOnSameProject = employees.stream()
          .filter(e -> e.getProjectId() == projectId)
          .collect(Collectors.toList());

      //Iterate to find couples that worked at the same time
      for (int i = 0; i < employeesOnSameProject.size() - 1; i++) {
        for (int j = i + 1; j < employeesOnSameProject.size(); j++) {
          if (checkIfWorkedTogether(employeesOnSameProject.get(i), employeesOnSameProject.get(j)))
            coupleRepository.saveCouple(new EmployeeCouple(projectId,
                employeesOnSameProject.get(i),
                employeesOnSameProject.get(j)));
        }
      }
    }
  }

  /**
   * Checks if employees worked together.
   *
   * @param employee1 {@link EmployeeCsvModel} an employee to compare
   * @param employee2 {@link EmployeeCsvModel} an employee to compare
   * @return true if employees worked together, else false.
   */
  private boolean checkIfWorkedTogether(EmployeeCsvModel employee1, EmployeeCsvModel employee2) {

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


  public Triple<Integer, Integer, Long> getLongestWorkingTogetherCouple() {
    MultiKeyMap map = mapCouplesAndAccumulateTime();
    MultiKey keyForMaxCouple = null;
    long valueForMaxCouple = 0;
    for (Object key : map.keySet()) {
      if ((long) map.get(key) > valueForMaxCouple) {
        keyForMaxCouple = (MultiKey) key;
        valueForMaxCouple = (long) map.get(key);
      }
    }
    System.out.println(" key " + keyForMaxCouple + " " + valueForMaxCouple);

    return Triple.of((int) keyForMaxCouple.getKey(0), (int) keyForMaxCouple.getKey(1), valueForMaxCouple);
  }

  /**
   * Maps employees that worked together on different projects and accumulates times.
   *
   * @return {@link MultiKeyMap} of the mapped couples.
   */
  private MultiKeyMap mapCouplesAndAccumulateTime() {
    MultiKeyMap map = new MultiKeyMap();
    for (EmployeeCouple couple : coupleRepository.getAllCouples()) {
      int key1 = couple.getFirstEmployee().getEmployeeId();
      int key2 = couple.getSecondEmployee().getEmployeeId();
      // keys need to be ordered for multimap
      if (key1 > key2) {
        int tmp = key2;
        key2 = key1;
        key1 = tmp;
      }
      //put or accumulate days worked together
      if (map.containsKey(key1, key2)) {
        long value = (long) map.get(key1, key2);
        map.put(key1, key2, value + couple.getDaysWorkedTogether());
      } else
        map.put(key1, key2, couple.getDaysWorkedTogether());
    }
    return map;
  }
}
