package com.cybr406.user.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

// See https://devcenter.heroku.com/articles/heroku-postgresql
@Configuration
@Profile("heroku")
public class HerokuConfiguration {

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }


  @Bean
  public DataSource dataSource() throws URISyntaxException {
    URI dbUri = new URI(System.getenv("DATABASE_URL"));
    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
    
    return DataSourceBuilder.create()
        .url(dbUrl)
        .username(username)
        .password(password)
        .build();
  }
}