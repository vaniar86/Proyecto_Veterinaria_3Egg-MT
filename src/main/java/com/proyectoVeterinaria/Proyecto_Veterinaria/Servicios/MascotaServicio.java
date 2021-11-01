package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumEspecie;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRaza;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MascotaServicio {

    @Transactional
    public void agregarMascota(String nombre, EnumEspecie especie, EnumRaza raza, int edad, int status) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("Ingrese el nombre del perro");
        }
        
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEdad(edad);
        mascota.setRaza(raza);
        mascota.setEspecie(especie);
        mascota.setStatus(status);

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
