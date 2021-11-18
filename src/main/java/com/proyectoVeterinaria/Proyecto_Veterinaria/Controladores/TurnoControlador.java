package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusTurno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumTipoAtencion;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.TurnoRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.TurnoServicio;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/turno")
    public String formularioTurno(ModelMap modelo) {
        //
        //modelo.put("tipoConsultas", tipoConsultas); NO TOCAR despues se habilita
        return "turno.html";
    }
    @GetMapping("/turnosMascota")
    public String turnosMascota(ModelMap model, @RequestParam String id ) throws ErrorServicio{
       List<Turno> turnos = turnoServicio.listarTurnosPorMascota(id);
        if(!turnos.isEmpty()){
            model.put("turnos", turnos);
        }else{
            model.put("message", "La mascota no posee turnos Cargados");
        }
         
        return "atencion";
    }
    
    
    //falta completar
}
