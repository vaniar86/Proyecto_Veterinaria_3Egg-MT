/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoVeterinaria.Proyecto_Veterinaria.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author Usuario
 */
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {
    @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
            .authorizeRequests()
                .antMatchers("/css/*", "/js/*","/img/*", "/**").permitAll()
            .and().formLogin()
                .loginPage("/login") // Que formulario esta mi login
                    .loginProcessingUrl("/logincheck")
                    .usernameParameter("username") 
                    .passwordParameter("password")
                    .defaultSuccessUrl("/inicio") // A que URL viaja
                    .permitAll()
            .and().logout() // Aca configuro la salida
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll().and().csrf().disable();
        }
        
}
