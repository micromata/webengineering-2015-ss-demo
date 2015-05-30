package com.micromata.webengineering.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
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
    if (environment.acceptsProfiles("heroku") == false) {
      LOG.warn("CSRF disabled!?");
      http.csrf().disable();
    }

    http.authorizeRequests()
        .antMatchers("/**").permitAll()
        .and()
        .formLogin()
        .loginPage("/login")

        .and()
        .logout()
        .logoutSuccessUrl("/")
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
          .passwordEncoder(new ShaPasswordEncoder(256))
          .dataSource(dataSource)
          .usersByUsernameQuery("select username,password,'true' from users where username = ?")
          .authoritiesByUsernameQuery("select username,'ROLE_USER' from users where username = ?")
      ;
    }


  }
}
