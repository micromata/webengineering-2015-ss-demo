package com.micromata.webengineering.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private static final Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);

  @Autowired
  private DataSource dataSource;

  @Autowired
  private Environment environment;

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
    if (environment.acceptsProfiles("heroku") == false) {
      LOG.warn("Adding default user:foo");
      auth
          .inMemoryAuthentication()
          .withUser("user")
          .password("foo")
          .roles("USER")
      ;

      auth
          .jdbcAuthentication()
          .dataSource(dataSource)
          .usersByUsernameQuery("select username,password,'true' from user where username = ?")
          .authoritiesByUsernameQuery("select username,'ROLE_USER' from user where username = ?")
      ;
    }


  }
}
