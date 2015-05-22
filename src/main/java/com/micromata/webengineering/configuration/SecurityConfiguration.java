package com.micromata.webengineering.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
        .antMatchers("/**").permitAll()
        .and()
        .formLogin()
        .loginPage("/login")
    ;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth

        .inMemoryAuthentication()
        .withUser("user")
        .password("foo")
        .roles("USER")
    ;

  }
}
