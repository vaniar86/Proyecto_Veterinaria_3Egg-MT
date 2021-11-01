package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRol;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProfesionalServicio {

    @Transactional
    public void registrar(String nombre, String apellido, Long telefono, EnumRol rol)throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Exception("El apellido del usuario no puede ser nulo");
        }
        if (telefono == null) {
            throw new Exception("El número del telefono no puede ser nulo");
        }
        
        Profesional profesional = new Profesional();
        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setTelefono(telefono);
        profesional.setRol(rol);
        
        Usuario usuario =new Usuario();
        
    }

    @Transactional
    public void modificar() {
    }

    public void validar() {
    }

    public void deshabilitar() {
    }

    @Transactional
    public void eliminar(String id) {
    }

}
