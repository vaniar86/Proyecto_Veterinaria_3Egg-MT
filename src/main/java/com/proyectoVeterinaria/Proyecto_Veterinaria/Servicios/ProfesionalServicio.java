package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRolProfesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.ProfesionalRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.TurnoRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private TurnoRepositorio turnoRepositorio;
    @Transactional
    public void registrar(String nombre, String apellido, Long telefono, EnumRolProfesional rol, Usuario usuario) throws ErrorServicio {
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
    public void modificar(String id, String nombre, String apellido, Long telefono, EnumRolProfesional rol, Usuario usuario) throws ErrorServicio {
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
    
    public ArrayList<Profesional> listarProfesionales() {
        //listo todo los profesionales de la base de datos
        ArrayList<Profesional> profesionales = new ArrayList(profesionalRepositorio.findAll());
        
        return profesionales;
    }
    public List<Turno> listarTurnoXprofesional(String id){
      
        return turnoRepositorio.buscarTurnosPorProfesional(id);
    }
    public Profesional BuscarProfesional(String idUsuario){
        System.out.println(idUsuario);
        return profesionalRepositorio.BuscarProfesional(idUsuario);
    }
    
    public List<Profesional> listarProfesionalPorRol(EnumRolProfesional rol){
       
           System.out.println(rol);
           List<Profesional> lista = profesionalRepositorio.buscarProfesionalPorRol(rol);
           System.out.println(lista.get(0).getNombre());
           return lista;
        
     }
}
