
package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRolProfesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.UsuarioRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ProfesionalServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.TurnoServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
@Controller
@RequestMapping("/profesional")
public class ProfesionalController {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private TurnoServicio turnoServicio;
    
    
    
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
    
    @GetMapping("")
    public String listarTurnoPorId(HttpSession session, ModelMap model){
        Usuario usuario= (Usuario) session.getAttribute("usuariosession");
        if (usuario== null || usuario.getMail().isEmpty()) {
            return "redirect/index";
        }
        Profesional profesional = profesionalServicio.BuscarProfesional(usuario.getMail());

        
        List<Turno>turnoXprofesional = turnoServicio.turnoXprofesional(profesional.getId());
        model.put("turnos", turnoXprofesional);
        
        return "profesional";
    }
    
    
    
    @PostMapping("/modificar-status")
    public String modificarStatus(String id, String status)throws ErrorServicio{
    
        try{
        if (status.equals("atendido") || status.equals("ausente")) {
        turnoServicio.statusTurno(id, status);    
        }
        }catch (ErrorServicio e){
            e.getMessage();
        }
        return("/status");
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
