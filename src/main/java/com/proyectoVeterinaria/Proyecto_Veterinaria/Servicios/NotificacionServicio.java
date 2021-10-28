package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServicio {
    
    @Async
    public void enviar(String cuerpo, String titulo, String mail){
        
    }
}
