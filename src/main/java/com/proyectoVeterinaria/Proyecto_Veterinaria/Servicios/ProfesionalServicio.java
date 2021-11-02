package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRol;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.ProfesionalRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.UsuarioRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(String nombre, String apellido, Long telefono, EnumRol rol, Usuario usuario) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }
        if (telefono == null) {
            throw new ErrorServicio("El número del telefono no puede ser nulo");
        }

        Profesional profesional = new Profesional();
        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setTelefono(telefono);
        profesional.setRol(rol);
        profesional.setIdUsuario(usuario);

        profesionalRepositorio.save(profesional);
    }

    @Transactional
    public void modificar(String id, String nombre, String apellido, Long telefono, EnumRol rol, Usuario usuario) throws ErrorServicio {
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            if (profesional.getId().equals(id)) {
                profesional.setNombre(nombre);
                profesional.setApellido(apellido);
                profesional.setTelefono(telefono);
                profesional.setRol(rol);
                profesional.setIdUsuario(usuario);
                profesionalRepositorio.save(profesional);
            } else {
                throw new ErrorServicio("No tiene permisos suficientes para realizar la operacion");
            }

        } else {
            throw new ErrorServicio("No existe ese id de profesional");
        }
    }

    public void validar() {
    }

    public void deshabilitar() {
    }

    @Transactional
    public void eliminar(String id) throws ErrorServicio {
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            if (profesional.getIdUsuario().equals(id)) {
                profesionalRepositorio.deleteById(id);
            } else {
                throw new ErrorServicio("No existe ese profesional");
            }
        }
    }

}
