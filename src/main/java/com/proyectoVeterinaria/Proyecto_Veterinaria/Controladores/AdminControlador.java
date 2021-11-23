package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusTurno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.TurnoRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ClienteServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ProfesionalServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.TurnoServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Martin
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminControlador {

    @Autowired
    private TurnoServicio turnoServicio;
    
     @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private TurnoRepositorio turnoRepositorio;
    
    
    @GetMapping("")
    public String vistaAdmin(HttpSession session, ModelMap model){
        try {
             Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null) {
            return "redirect:/login";
        }
        
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        List<Turno> turnos = turnoServicio.listarTurnosAsignados();
 
            
        if(!profesionales.isEmpty()){
             model.put("profesionales", profesionales );
             model.put("statusTurno", EnumStatusTurno.values());
             if(!turnos.isEmpty()){
              model.put("turnos", turnos);
              
              
             }else{
               model.put("error", "No existen asigandos turnos");
             }
            }else{
                model.put("message", "No hay profesionales Cargados");

            }
                return "administrador";
        } catch (Exception e) {
            model.put("error", "No es posible acceder a la página");
             return "redirect:/inicio";
        } 
        
       
    }

    @GetMapping("/filtrarXProfesional/{id}")
    public String turnosPorProfesional(HttpSession session, ModelMap model, @PathVariable String id) throws ErrorServicio {
            System.out.println(id);
        try {            
            List<Turno> turnos = turnoServicio.turnoPorProfesional(id);     
             
            if(!turnos.isEmpty()){       
              model.put("turnos", turnos);
//               model.put("profesionales", profesionales );
             }else{
                System.out.println("lentro al else");
//                 model.put("profesionales", profesionales );
                model.put("message", "No existen asigandos turnos para este profesional");
             }
             List<Profesional> profesionales = profesionalServicio.listarProfesionales();
             System.out.println(profesionales.get(0).getNombre());
             System.out.println(!turnos.isEmpty());
             model.put("profesionales", profesionales );
            System.out.println("llego hasta aca");
            return "administrador";
        } catch (Exception e) {
            
            model.put("error", "No es posible recuperar el listado de turnos de este profesional");
            
             return "redirect:/admin";
        }

    }

    @GetMapping("/listar-Profesionales")
    public String listaProfesionales(HttpSession session, ModelMap model) throws ErrorServicio {

        Cliente login = (Cliente) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }

        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        model.put("profesionales", profesionales);
        return "/Profesionales";
    }
    
    
    @GetMapping("/modificar-status/{id}/{status}")
    public String modificarStatus(@PathVariable String id, @PathVariable String status, ModelMap model)throws ErrorServicio{
        
    
        try{
            Optional<Turno> respuesta = turnoServicio.buscaPorId(id);
            if(respuesta.isPresent()){
             Turno turno = respuesta.get();
                if(turno.getStatus().equals(EnumStatusTurno.ATENDIDO) && status.equalsIgnoreCase("cancelar")){
                    model.put("error", "No puede cancelar un turno ya atendido");
                    return ("redirect:/admin");            
                 }               
            }
        
              turnoServicio.statusTurno(id, status);    
        
        }catch (ErrorServicio e){
            e.getMessage();
        }
        return ("redirect:/admin");
    }
    
     @GetMapping("/turnosDisponibles")
    public String turnosDisponibles(HttpSession session, ModelMap model) throws ErrorServicio {

        Usuario login = (Usuario) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }

         try {
              List<Turno> turnos = turnoServicio.listarTurnosDisponibles();
                model.put("turnos", turnos);
                return "turnosDisponibles";
         } catch (Exception e) {
              model.put("error", "No es posible recuperar el listado de turnos de este profesional");
            
             return "redirect:/admin";
         }
    }
    
     @GetMapping("/asignarTurno/{id}")
    public String asignarTurno (HttpSession session, ModelMap model, @PathVariable String id) throws ErrorServicio {
        Mascota  mascota = new Mascota();
        Cliente cliente = new Cliente();
        Usuario login = (Usuario) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }

         try {
              Optional<Turno> respuesta = turnoServicio.buscaPorId(id);
               if(respuesta.isPresent()){
                   Turno turno = respuesta.get();
                   System.out.println(turno.getProfesional().getNombre());
                   Optional<Profesional> resp= profesionalServicio.buscarPorId(turno.getProfesional().getId());
                   
                   model.put("turno", turno);
                   model.put("mascota", mascota);
                   model.put("cliente", cliente);
               }                
                return "cargarTurno";
         } catch (Exception e) {
              model.put("error", "No es posible recuperar el listado de turnos de este profesional");
            
             return "redirect:/admin";
         }
    }
    
//    @GetMapping("/clienteList")
//    public String mascotasPorCliente(Model model, @RequestParam String query){
//        Cliente cliente = null;
//        if(query != null){
//            cliente = clienteServicio.FindByQuery(query);
//            
//            System.out.println(cliente);
//        } 
//        return "redirect:/turnosDisponibles";
//    }
}
