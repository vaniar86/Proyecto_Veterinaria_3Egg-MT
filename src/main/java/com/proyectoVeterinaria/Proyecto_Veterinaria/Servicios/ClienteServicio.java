package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio{
    
    @Transactional
    public void registrar(String nombre, String apellido, String direccion, Long telefono, String mail, String password, String password2) {
    }

    @Transactional
    public void modificar(String id, String nombre, String apellido, String direccion, Long telefono, String mail, String password, String password2) {
    }

    //metodo interno de Registrar, que verifica al momento de registrar, que ese mail no este en uso 
    private void validar(String email) {
    }

    @Transactional    
    public void eliminar(String id) {
    }

}
