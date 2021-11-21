/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusTurno;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Yoel
 */

@Entity
public class Turno {
    
    
   @Id
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
   @Temporal(TemporalType.TIMESTAMP)
   private Date fecha;
   @Enumerated(EnumType.STRING)
   private EnumStatusTurno status;
   @OneToOne
   private Mascota mascota;
   @OneToOne
   private Profesional profesional;
   
    @OneToOne
   private Atencion atencion;

    public Turno() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EnumStatusTurno getStatus() {
        return status;
    }

    public void setStatus(EnumStatusTurno status) {
        this.status = status;
    }


    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional atencion) {
        this.profesional = profesional;
    }
    
    public Atencion getAtencion() {
        return atencion;
    }

    public void setAtencion (Atencion  atencion) {
        this.atencion = atencion;
    }
   
   
   
   
}
