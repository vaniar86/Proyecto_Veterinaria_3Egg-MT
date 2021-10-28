package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio implements UserDetailsService{
    
    @Transactional
    public void registrar(String nombre, String apellido, String direccion, Long telefono, String mail, String password, String password2) {
    }

    @Transactional
    public void modificar(String id, String nombre, String apellido, String direccion, Long telefono, String mail, String password, String password2) {
    }

    //metodo interno de Registrar
    private void validar() {
    }

    @Transactional    
    public void eliminar(String id) {
    }

    public 
    //listar nombres (usamos el metodo mostrar cliente y mostramos muchos)
    //buscar por id (usamos el metodo buscar por id, dentro de los demas metodos que hay en clienteservicio)
    //mostrar cliente por id (visualiza perfil)
    
    
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        
    }
}
