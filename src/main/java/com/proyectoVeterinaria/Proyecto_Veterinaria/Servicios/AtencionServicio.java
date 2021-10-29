package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumAtencionPuntual;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AtencionServicio {

    @Transactional
    public void registrarAtencion(EnumAtencionPuntual enumAtencionPuntual) {
    }

    public String tipoAtencion() {
        return null;
    }

    public String Descripcion() {
        return null;
    }

    public String Prescipcion() {
        return null;
    }

    @Transactional
    public void modificar() {
    }

    public void validar() {
    }

    public void deshabilitar() {
    }

    @Transactional
    public void eliminar(String id) {
    }

}
