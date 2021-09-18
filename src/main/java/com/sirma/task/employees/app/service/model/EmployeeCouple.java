package com.sirma.task.employees.app.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a working couple on a project.
 */
@Getter
@Setter
public class EmployeeCouple {
  private final int DAY = 86400000;
  private int projectId;
  private EmployeeModel firstEmployee;
  private EmployeeModel secondEmployee;
  private long timeWorkedTogether;

  public EmployeeCouple(int projectId,
                        EmployeeModel firstEmployee,
                        EmployeeModel secondEmployee) {
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

    //Adds 1 day of working together if they leave/come on the same day
    //This is needed because the difference between two same days is 0 instead of 1.
    if(firstEmployee.getDateTo().equals(secondEmployee.getDateFrom())
    || secondEmployee.getDateTo().equals(firstEmployee.getDateFrom()))
      timeWorkedTogether+=DAY;
    if(firstEmployee.getDateTo().equals(secondEmployee.getDateTo())
    || secondEmployee.getDateTo().equals(firstEmployee.getDateTo()))
      timeWorkedTogether+=DAY;
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
