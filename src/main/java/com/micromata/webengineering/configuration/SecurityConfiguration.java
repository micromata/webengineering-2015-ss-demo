package com.micromata.webengineering.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()

        .antMatchers("/css/**").permitAll()
        .antMatchers("/**").authenticated()
    ;
  }
}