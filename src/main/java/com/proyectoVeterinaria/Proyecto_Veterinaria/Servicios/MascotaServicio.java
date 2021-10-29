package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumEspecie;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRaza;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MascotaServicio {

    @Transactional
    public void agregarMascota(String nombre, EnumEspecie especie, EnumRaza raza, int edad, int status) {

    }

    @Transactional
    public void modificar(String nombre, EnumEspecie especie, EnumRaza raza, int edad, int status) {
    }

    public void validar() {
    }

    public void deshabilitar() {
    }

    @Transactional
    public void eliminar(String id) {
    }
}
