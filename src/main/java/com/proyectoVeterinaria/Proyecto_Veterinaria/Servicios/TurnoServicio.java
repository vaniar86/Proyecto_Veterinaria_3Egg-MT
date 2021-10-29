package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusTurno;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TurnoServicio {

    @Transactional
    public void crearTurno(Date fecha, EnumStatusTurno status, Mascota mascota, Profesional profesional) {
    }

    @Transactional
    public void modificar(Date fecha, EnumStatusTurno status, Mascota mascota, Profesional profesional) {
    }

    public void validar() {
    }

    @Transactional
    public void eliminar(String id) {
    }
}
