package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.TurnoRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ProfesionalServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.TurnoServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Martin
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private TurnoServicio turnoServicio;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @GetMapping("/turnos")
    public String turnosPorProfesional(HttpSession session, ModelMap model, @RequestParam Profesional profe) throws ErrorServicio {

        Cliente login = (Cliente) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }
        /*
        List<Turno> turnos = turnoServicio.listarTurnosPorProfesional(profe);
        model.put("turnos", turnos);
        //La query de turnoRepositorio da error al hacer clean&build y termina afectando a esta llamada
        */
        return "/atencion";
    }

    @GetMapping("/listar-Profesionales")
    public String listaProfesionales(HttpSession session, ModelMap model) throws ErrorServicio {

        Cliente login = (Cliente) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }
        /*
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        model.put("profesionales", profesionales);
        
        //La query de turnoRepositorio da error al hacer clean&build y termina afectando a esta llamada
        que esta
        */
        return "/Profesionales";
    }
}
