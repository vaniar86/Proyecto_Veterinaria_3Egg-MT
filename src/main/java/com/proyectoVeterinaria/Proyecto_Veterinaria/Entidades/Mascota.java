/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author yoel
 */
@Entity
public class Mascota {
    
   @Id
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private Especie especie;
    private Raza raza;
    private int edad;
    private int status;
    
    @OneToOne
    private Cliente cliente;
    @ManyToMany
    private Atencion atencion;
    @ManyToMany
    private Turno turno;
}
