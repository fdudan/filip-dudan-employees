package com.sirma.task.employees.app.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller advice for handling exceptions.
 */
@ControllerAdvice
public class ExceptionController {
  private static final String DEFAULT_ERROR_VIEW = "error";

  /**
   * Handles all exceptions that occurred in the application. The user is redirected to a default
   * error page.
   *
   * @param req the {@link HttpServletRequest that caused the exception}
   * @param e the exception that occurred.
   * @return {@link ModelAndView} of the default error page.
   */
  @ExceptionHandler(value = Exception.class)
  public ModelAndView
  defaultErrorHandler(HttpServletRequest req, Exception e)  {
    //ToDo: replace this with proper logging.
    e.printStackTrace();
    // Send the user to a default error-view.
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName(DEFAULT_ERROR_VIEW);
    return mav;
  }
}
