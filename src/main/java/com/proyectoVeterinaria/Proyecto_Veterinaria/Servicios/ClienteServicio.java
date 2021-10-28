package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio implements UserDetailsService{
    
    @Transactional
    public void registrar() {
    }

    @Transactional
    public void modificar() {
    }

    public void validar() {
    }

    public void deshabilitar() {
    }

    @Transactional    
    public void eliminar() {
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        
    }
}
