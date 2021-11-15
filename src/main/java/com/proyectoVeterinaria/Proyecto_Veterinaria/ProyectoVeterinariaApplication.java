package com.proyectoVeterinaria.Proyecto_Veterinaria;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProyectoVeterinariaApplication extends WebSecurityConfigurerAdapter{

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    public static void main(String[] args) {
	SpringApplication.run(ProyectoVeterinariaApplication.class, args);

    }
        
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());

    }


}


