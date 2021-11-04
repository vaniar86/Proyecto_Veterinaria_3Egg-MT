/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumAtencionPuntual;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumTipoAtencion;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Yoel
 */
@Entity
public class Atencion {
    
   @Id
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
   @Enumerated(EnumType.STRING)
   private EnumTipoAtencion tipoAtencion;
   @Enumerated(EnumType.STRING)
   private EnumAtencionPuntual atencionPuntual;
   private String descripcion;
   private String prescripcion;
   @Temporal(TemporalType.TIMESTAMP)
   private Date fechaAtencion;
   
   

    public Atencion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EnumTipoAtencion getTipoAtencion() {
        return tipoAtencion;
    }

    public void setTipoAtencion(EnumTipoAtencion tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    public EnumAtencionPuntual getAtencionPuntual() {
        return atencionPuntual;
    }

    public void setAtencionPuntual(EnumAtencionPuntual atencionPuntual) {
        this.atencionPuntual = atencionPuntual;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrescripcion() {
        return prescripcion;
    }

    public void setPrescripcion(String prescripcion) {
        this.prescripcion = prescripcion;
    }
   
   
   
}
