package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusTurno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.TurnoRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.TurnoServicio;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/turno")
public class TurnoControlador {

    @Autowired
    private TurnoServicio turnoServicio;

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @PostMapping("/crear-turno") 
    public String registrar(ModelMap modelo, @RequestParam Date fecha, @RequestParam EnumStatusTurno status, @RequestParam  Mascota mascota, @RequestParam Profesional profesional){
         try {
             turnoServicio.crearTurno(fecha, status, mascota, profesional);
         } catch (ErrorServicio e) {
  
             modelo.put("error", e.getMessage());
             modelo.put("fecha", fecha);
             modelo.put("status", status);
             modelo.put("mascota", mascota);
             modelo.put("profesional", profesional);
             return "turno.html";
         }
        
        return "cliente.html";
    }
    
    //falta completar
    
}
