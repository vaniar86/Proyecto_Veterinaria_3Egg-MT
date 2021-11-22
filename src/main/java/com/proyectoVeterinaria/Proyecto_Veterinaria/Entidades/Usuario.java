/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 *
 * @author Yoel
 */
@Entity
public class Usuario {
    
    @Id
    private String mail;
    private String pass;
    
    
    @Enumerated(EnumType.STRING)
    private EnumRol rol;
    
    private Date fechaAlta;

    public Usuario() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public EnumRol getRol() {
        return rol;
    }

    public void setRol(EnumRol rol) {
        this.rol = rol;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public String toString() {
        return "Usuario{" + "mail=" + mail + ", pass=" + pass + ", rol=" + rol + ", fechaAlta=" + fechaAlta + '}';
    }

    
    
    
    
    
}
