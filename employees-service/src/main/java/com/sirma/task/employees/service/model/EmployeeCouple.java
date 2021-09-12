package com.sirma.task.employees.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a working couple on a project.
 */
@Getter
@Setter
public class EmployeeCouple {
  private int projectId;
  private EmployeeCsvModel firstEmployee;
  private EmployeeCsvModel secondEmployee;
  private long timeWorkedTogether;

  public EmployeeCouple(int projectId,
                        EmployeeCsvModel firstEmployee,
                        EmployeeCsvModel secondEmployee){
    this.projectId = projectId;
    this.firstEmployee = firstEmployee;
    this.secondEmployee = secondEmployee;
    // laterStartTime represents the time of the employee that started later
    long laterStartTime =
        Math.max(firstEmployee.getDateFrom().getTime(),
            secondEmployee.getDateFrom().getTime());
    // earlierEndTime represents the time of the employee that started earlier
    long earlierEndTime =
        Math.min(firstEmployee.getDateTo().getTime(),
            secondEmployee.getDateTo().getTime());
    this.timeWorkedTogether = earlierEndTime - laterStartTime;
  }

  /**
   * Returns hours that the couple worked together.
   */
  public long getDaysWorkedTogether() {
    return timeWorkedTogether / 86400000;
  }

  @Override
  public String toString() {
    return projectId + " "
        + "FIRST EMPLOYEE: " + firstEmployee + "\n"
        + "SECOND EMPLOYEE: " + secondEmployee + "\n"
        + getDaysWorkedTogether()
        + " DAYS";
  }
}
