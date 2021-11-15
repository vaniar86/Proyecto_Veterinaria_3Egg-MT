/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoVeterinaria.Proyecto_Veterinaria.config;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Order(1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracionesSeguridad extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().and()
			.authorizeRequests()
				.antMatchers("/css/*", "/js/*", "/img/*", "/**")
                                .permitAll()
				.and().formLogin()
					.loginPage("/login")//html del login
                                            .loginProcessingUrl("/logincheck")//formulario de login
                                            .usernameParameter("email")//user
                                            .passwordParameter("password")//pass
                                            .defaultSuccessUrl("/index")//html que se dirige al ingresar
                                            .permitAll()
                                        .and().logout()
                                             .logoutUrl("/logout")
                                              .logoutSuccessUrl("/")
                                              .permitAll()
                                              .and().csrf().disable();
        
    }
}
