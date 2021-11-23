
package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumAtencionPuntual;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRolProfesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusTurno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumTipoAtencion;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.UsuarioRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.AtencionServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.MascotaServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ProfesionalServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.TurnoServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ADMIN')")
@Controller
@RequestMapping("/profesional")
public class ProfesionalController {
    
    @Autowired
    private AtencionServicio atencionServicio;
    @Autowired
    private MascotaServicio mascotaservicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private TurnoServicio turnoServicio;
    
    
     @GetMapping("")
    public String listarTurnoPorId(HttpSession session, ModelMap model){
       
        Usuario login = (Usuario) session.getAttribute("usuariosession");     
       
        if (login == null) {
            return "redirect:/login";
        }
         try {
             Profesional profesional = profesionalServicio.BuscarProfesional(login.getMail());           
             
                System.out.println(profesional);
                
                List<Turno>turnoXprofesional = turnoServicio.turnoXprofesional(profesional.getId());
                System.out.println(turnoXprofesional);
                
                if(!turnoXprofesional.isEmpty()){
                    model.put("turnos", turnoXprofesional);
                }else{
                     model.put("message", "No hay turnos cargados");
                }
  
    
         } catch (Exception e) {
              model.put("error", "Ocurrió un error al intentar recuperar los turnos");
         }
          return "profesional";
    }
    
    
    @GetMapping("/modificarAtencion/{id}")
   public String modificarAtencion (@PathVariable String id,HttpSession session,ModelMap model){
        System.out.println("controlador de turnos");
       try {
           Usuario usuario = (Usuario) session.getAttribute("usuariosession");    
       
        if (usuario == null) {
            return "redirect:/login";
        }
        
        
        Turno turno = turnoServicio.buscarTurnoPorId(id);
        System.out.println(turno.getId() + "turnoID");
         model.put("turno", turno);
         model.put("tipoAtencion", EnumTipoAtencion.values());
         model.put("atenciones", EnumAtencionPuntual.values());  
               
        
         return "atencionPuntual";
        
       } catch (Exception e) {
           System.out.println(e.getMessage());
           model.put("error", "Ocurrio un error al cargar la pagina");
             return "redirect:/profesional";

       }
   }    
    
    
    @GetMapping("/modificar-status/{id}/{status}")
    public String modificarStatus(@PathVariable String id, @PathVariable String status, ModelMap model)throws ErrorServicio{
        
    
        try{
            Optional<Turno> respuesta = turnoServicio.buscaPorId(id);
            if(respuesta.isPresent()){
             Turno turno = respuesta.get();
                if(turno.getStatus().equals(EnumStatusTurno.ATENDIDO) && status.equalsIgnoreCase("ausente")){
                    model.put("error", "No puede marcar como ausente un turno ya atendido");
                    return ("redirect:/profesional");            
                 }               
            }
        if (status.equals("atendido") || status.equals("ausente")) {
        turnoServicio.statusTurno(id, status);    
        }
        }catch (ErrorServicio e){
            e.getMessage();
        }
        return ("redirect:/profesional");
    }
    
    @PostMapping("/cargarAtencion") 
    public String cargarAtención(ModelMap model,@RequestParam String atencionId, @RequestParam String turnoId, @RequestParam String descripcion, @RequestParam String prescripcion, @RequestParam EnumAtencionPuntual atencion){
        try {
            atencionServicio.modificar(atencionId, atencion, descripcion, prescripcion);
            turnoServicio.statusTurno(turnoId, "atendido");
             return ("redirect:/profesional");
             
        } catch (Exception e) {
            model.put("error", "Ocurrio un error al cargar la atención");
            return "atencionPuntual";            
        }
    }
    
    @GetMapping("/historialAtencion/{id}")
    public String historialAtenciones(@PathVariable String id, ModelMap model,HttpSession session){
        try {
            Mascota mascota = mascotaservicio.buscarMascotaPorId(id);
            model.put("mascota", mascota);
            List<Turno> turnos = turnoServicio.listarTurnosAtendidos(mascota.getId());
            
            if(!turnos.isEmpty()){
                model.put("turnos", turnos);
            }else{
                model.put("message", "No hay atenciones cargadas para esta mascota");
            }
            return "historialAtencion";
            
        } catch (Exception e) {
            model.put("error", "Ocurrio un error al cargar el historial");
            
            return ("redirect:/profesional");   
            
        }

    } 
    
    
    
    
    
    @PostMapping("/actualizarprofesional")  
    public String Actualizar(HttpSession sesion,ModelMap model,@RequestParam String id,@RequestParam long telefono,@RequestParam String nombre,@RequestParam String apellido,@RequestParam EnumRolProfesional rol,@RequestParam Usuario idUsuario)throws ErrorServicio{       
        try{
        if (id== null || id.isEmpty()) {
            profesionalServicio.registrar(nombre, apellido,telefono, rol, idUsuario);
        }else{
            profesionalServicio.modificar(id, nombre, apellido,telefono, rol, idUsuario);
            return ("redirect/inicio");
        }
        }catch(Exception error){
            Profesional profesional = new Profesional();
            profesional.setId(id);
            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setRol(rol);
            profesional.setTelefono(telefono);
        }
        return id;
    }
    
    @GetMapping("/listar-Turnos")
    public String listarTurnos(ModelMap model){
   
        ArrayList<Turno> turnos = turnoServicio.listarTurnos();
        model.put("turnos", turnos);
     return ("turnos");   
    }
    
   

  //  
  // @PostMapping("/eliminarprofesional") 
  // public String eliminarProfesional(HttpSession sesion,String id)throws ErrorServicio{        
  //     try{
  //         Usuario login = (Usuario) sesion.getAttribute("usuariosession");
  //     profesionalServicio.eliminar(id);
  //     }catch(Exception Error){
  //         Logger.getLogger(ProfesionalController.class.getName()).log(Level.SEVERE, null, Error);
  //         Error.getMessage();
 //          return ("/index");
 //      }
 //      return ("redirect:/profesional");
 //  }
 //  
   @GetMapping("/editar-profesional")
    public String editarPerfil(HttpSession session, @RequestParam(required = false) String id, @RequestParam(required = false) String accion, ModelMap model) {
       
        Usuario login = (Usuario) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }
        try{
        Profesional profesional = new Profesional();
        model.put("perfil", profesional);
        }catch(Exception Error){
            Error.getMessage();
            return "profesional.html";
        }
        
        return "profesional.html";
    }
}
