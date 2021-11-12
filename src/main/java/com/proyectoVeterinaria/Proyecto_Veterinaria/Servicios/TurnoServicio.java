package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusTurno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.TurnoRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @Transactional
    public void crearTurno(Date fecha, EnumStatusTurno status, Mascota mascota, Profesional profesional) throws ErrorServicio {
        validar(fecha, status, mascota, profesional);

        Turno turno = new Turno();
        turno.setFecha(new Date());
        turno.setMascota(mascota);
        turno.setProfesional(profesional);
        turno.setStatus(status);

        turnoRepositorio.save(turno);
    }

    @Transactional
    public void modificar(Date fecha, EnumStatusTurno status, Mascota mascota, Profesional profesional, String id) throws ErrorServicio {
        validar(fecha, status, mascota, profesional);
        Optional<Turno> respuesta = turnoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Turno turno = respuesta.get();
            turno.setFecha(fecha);
            turno.setMascota(mascota);
            turno.setProfesional(profesional);
            turno.setStatus(status);

            turnoRepositorio.save(turno);

        } else {
            throw new ErrorServicio("No se encontro con el id del turno solicitado");
        }
    }

    public void validar(Date fecha, EnumStatusTurno status, Mascota mascota, Profesional profesional) throws ErrorServicio {

        if (fecha == null) {
            throw new ErrorServicio("error con la fecha");
        }

        if (mascota == null) {
            throw new ErrorServicio("error, la mascota no fue seleccionada");
        }

        if (status == null) {
            throw new ErrorServicio("error, no se encuentra el status del turno");
        }

    }

    @Transactional
    public void eliminar(String id) throws ErrorServicio {
        Optional<Turno> respuesta = turnoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            respuesta.get().setStatus(EnumStatusTurno.DISPONIBLE);
            respuesta.get().setFecha(new Date());
            respuesta.get().setMascota(null);
            respuesta.get().setProfesional(null);
        } else {
            throw new ErrorServicio("El turno no existe");
        }

    }

    @Transactional
    public void statusTurno(String id, String status) throws ErrorServicio {
        /*recibo el id del turno y un String que puede decir "cancelar, modificar, atendido, etc"
        luego segun lo que diga, se hara tal accion,la persona toca el boton y se manda de manera oculta
        BORRAR ESTE COMENTARIO: cuando admin, profesional y cliente, ya tengan funcionando los botones
         */
        Optional<Turno> respuesta = turnoRepositorio.findById(id);
        Turno turno = respuesta.get();
        if (respuesta.isPresent()) {
            if (status.equals("cancelar")) {
                turno.setMascota(null);
                turno.setStatus(EnumStatusTurno.DISPONIBLE);
                turnoRepositorio.save(turno);
            } else {
                if (status.equals("atendido")) {
                    turno.setStatus(EnumStatusTurno.ATENDIDO);
                    turnoRepositorio.save(turno);
                } else {
                    if (status.equals("suspendido")) {
                        turno.setStatus(EnumStatusTurno.SUSPENDIDO);
                        turnoRepositorio.save(turno);
                        //notificar por mail
                    } else {
                        if (status.equals("ausente")) {
                            turno.setStatus(EnumStatusTurno.AUSENTE);
                            turnoRepositorio.save(turno);
                        }
                    }
                }
            }
        } else {
            throw new ErrorServicio("No se encontro con el id del turno solicitado");
        }
    }

    public ArrayList<Turno> listarTurnos() {
        //listo todo los turnos de la base de datos
        ArrayList<Turno> turnos = new ArrayList(turnoRepositorio.findAll());
        return turnos;
    }

    
    /*
    public ArrayList<Turno> listarTurnosPorProfesional(Profesional profe){
        //listo todo los turnos de ESE profesional
        ArrayList<Turno> turnos = new ArrayList(turnoRepositorio.buscarTurnosPorProfesional(profe.getNombre()));
        return turnos;
    }
    //La query de turnoRepositorio da error al hacer clean&build y termina afectando a esta llamada
     */
}
