package com.sirma.task.employees.app.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service for saving and loading data to/from the filesystem.
 */
@Service
public class StorageService {
  //Files should be uploaded in the root path
  private final static String ROOT_PATH = System.getProperty("user.dir") + "/csv/";
  private final static String PROTOCOL = "file://";
  private final Path root = Paths.get(ROOT_PATH);
  @Value("${path.to.employeeFile}")
  private String latestFileName;

  /**
   * Saves the a file to the working directory. Updates the latestFileName to the new filename
   *
   * @param file {@link MultipartFile} to be saved.
   */
  @SneakyThrows
  public void save(MultipartFile file) {
    // Updates the latestFileName to the new file for future parsing. Old file is kept.
    latestFileName = file.getOriginalFilename();
    Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
  }

  /**
   * Gets the input stream of the latest saved file. Default file is passed through
   * the property path.to.employeeFile
   *
   * @return {@link InputStream}
   */
  @SneakyThrows
  public InputStream load() {
    String pathToFile = PROTOCOL + this.root.resolve(latestFileName).toString();

    Resource resource = new UrlResource(pathToFile);
    return resource.getInputStream();
  }

}
