package com.sirma.task.employees.app.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Employee model
 */
@Getter
@Setter
public class EmployeeModel {
  public static String datePattern = "yyyy-MM-dd";

  private int employeeId;

  private int projectId;

  private Date dateFrom;

  private Date dateTo;

  @SneakyThrows
  public EmployeeModel(int employeeId, int projectId, String dateFrom, String dateTo) {
    this.employeeId = employeeId;
    this.projectId = projectId;
    this.dateFrom = DateUtils.parseDate(dateFrom, datePattern);

    if (dateTo.equals("NULL"))
      this.dateTo = Date.from(Instant.now());
    else
      this.dateTo = DateUtils.parseDate(dateTo, datePattern);
  }

  private String prettyDate(Date date) {
    DateFormat dateFormat = new SimpleDateFormat(datePattern);
    return dateFormat.format(date);
  }

  public String getDateFromString() {
    return prettyDate(this.dateFrom);
  }

  public String getDateToString() {
    return prettyDate(this.dateTo);
  }

  @SneakyThrows
  @Override
  public String toString() {
    return "Employee ID: " + employeeId + ", "
        + "Project ID: " + projectId + ", "
        + "Date: " + getDateFromString() + " - "
        + getDateToString();
  }
}
