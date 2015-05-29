package com.micromata.webengineering.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Global error handling.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@ControllerAdvice
public class ExceptionConfiguration {
  private static final Logger LOG = LoggerFactory.getLogger(ExceptionConfiguration.class);

  @ExceptionHandler(Exception.class)
  public ModelAndView handleError(Exception e, HttpServletRequest request) {
    LOG.error("Uncaught exception. msg={}, url={}", e.getMessage(), request.getRequestURL(), e);

    ModelAndView mav = new ModelAndView("error");
    mav.addObject("exception", e);
    return mav;
  }
}
