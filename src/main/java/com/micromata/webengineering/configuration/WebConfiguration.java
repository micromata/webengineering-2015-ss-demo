package com.micromata.webengineering.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configure simple url-template patterns.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/about").setViewName("about");
  }
}
