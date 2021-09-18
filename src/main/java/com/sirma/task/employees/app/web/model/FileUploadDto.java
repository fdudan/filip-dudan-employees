package com.sirma.task.employees.app.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

/**
 * Model to bind and validate file upload
 */
@Getter
@Setter
@NoArgsConstructor
@Validated
public class FileUploadDto {

  private MultipartFile file;

  @NotBlank(message = "Date patterns is required. E.g. dd-MM-yyyy ")
  private String pattern = "yyyy-MM-dd";

  /**
   * Validates if the bound {@link MultipartFile file} is null or empty
   *
   * @return true if file is not null and not empty, else false.
   */
  @AssertTrue(message = "You must upload a file!!")
  public boolean isFileProvided() {
    return (file != null) && (!file.isEmpty());
  }
}
