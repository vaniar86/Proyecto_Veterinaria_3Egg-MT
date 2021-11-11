package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ProfesionalServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.TurnoServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    @GetMapping("/turnos")
    public String turnosPorProfesional(HttpSession session, ModelMap model) throws ErrorServicio {

        Cliente login = (Cliente) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }

        /*Turno turnos = ;
        model.put("turnos", mascota.getFoto());
        El metodo para traer turnos por profesional esta en TurnoServicio, pero no habilitado aun
         */
        return "/atencion";
    }
    
    @GetMapping("/listar-Profesionales")
    public String listaProfesionales(HttpSession session, ModelMap model) throws ErrorServicio {

        Cliente login = (Cliente) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }
       // List<Profesional> profesionales = profesionalServicio.;
        //model.put("profesionales", profesionales);
        
        return "/Profesionales";
    }
}
