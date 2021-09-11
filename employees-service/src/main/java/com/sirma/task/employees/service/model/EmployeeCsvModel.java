package com.sirma.task.employees.service.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Model for the Employee class. Used for binding CSV entries to POJO.
 */
@Getter
@Setter
public class EmployeeCsvModel {
  public static String datePattern = "yyyy-MM-dd";

  @CsvBindByPosition(position = 0)
  private int employeeId;

  @CsvBindByPosition(position = 1)
  private int projectId;

  @CsvBindByPosition(position = 2)
  private String dateFrom;

  //Field can't be bound to Date because of NULL values.
  @CsvBindByPosition(position = 3)
  private String dateTo;

  /**
   * Handles conversion of dateTo String to Date. This method is needed because
   * NULL can't be cast to Date
   *
   * @return parsed {@link Date} from the dateTo string, current date if NULL.
   */
  @SneakyThrows
  public Date getDateTo() {
    if (dateTo.equals("NULL"))
      return Date.from(Instant.now());
    return DateUtils.parseDate(dateTo, datePattern);
  }

  /**
   * Handles conversion of dateFrom String to Date. This method is needed because
   * CsvDate binding works only with const patterns!
   *
   * @return parsed {@link Date} from the dateTo string.
   */
  @SneakyThrows
  public Date getDateFrom() {
    return DateUtils.parseDate(dateFrom, datePattern);
  }

  private String prettyDate(Date date) {
    DateFormat dateFormat = new SimpleDateFormat(datePattern);
    return dateFormat.format(date);
  }

  @SneakyThrows
  @Override
  public String toString() {
    return "Employee ID: " + employeeId + ", "
        + "Project ID: " + projectId + ", "
        + "Date: " + prettyDate(getDateTo()) + " - "
        +  prettyDate(getDateTo());
  }
}
