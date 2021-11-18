
package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumEspecie;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRaza;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusMascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ClienteServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.MascotaServicio;
import com.sun.istack.logging.Logger;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
@Controller
@RequestMapping("/mascota")
public class MascotaControlador {

    @Autowired
    private ClienteServicio clienteServicio;
    
    
    @Autowired
    private MascotaServicio mascotaServicio;

    @GetMapping("")
    public String vistaMascota(HttpSession session, ModelMap model) throws ErrorServicio{
         Usuario login = (Usuario) session.getAttribute("usuariosession");      

        if (login == null) {
            return "redirect:/login";
        }
        Mascota mascota = new Mascota();
        model.put("mascota", mascota);
        model.put("tipos", EnumEspecie.values());
        model.put("razas", EnumRaza.values());
        
        
        return "cargarMascota";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/crearMascota")
    public String Actualizar(HttpSession session, ModelMap model, @RequestParam(required = false) String id, @RequestParam String nombre, @RequestParam EnumRaza raza, @RequestParam int edad,@RequestParam EnumEspecie idTipo,MultipartFile archivo){
            System.out.println("entro al controlador"); 
        
        Usuario login = (Usuario) session.getAttribute("usuariosession");
             if (login == null) {
                 model.put("error", "No tiene permisos para realizar esta acción");
                 return "redirect:/login";
             }
                    
     try{
          Cliente cliente = clienteServicio.buscarClientePorUsuario(login.getMail());
          
         if (id==null || id.isEmpty()) {
             mascotaServicio.agregarMascota(nombre, cliente.getId(), idTipo, raza, edad, archivo);
         }else{
             mascotaServicio.modificar(id,nombre, cliente.getId(), idTipo, raza, edad, archivo);
            
         }
          return "cargarMascota";
     
     }catch(ErrorServicio e){
         model.put("error", "Ocurrió un error, pongase en contacto con servicio técnico");
         
  
        return "redirect:/inicio";
    }
    
    }
    @PostMapping("/eliminar-mascota")
    public String EliminarMascota(HttpSession sesion,@RequestParam String id)throws ErrorServicio{
        try{
            Usuario login= (Usuario) sesion.getAttribute("usuariosession");
            mascotaServicio.eliminar(id, id);
        }catch(Exception error){
          //  Logger.getLogger(MascotaControlador.class.getName()).log(Level.SEVERE, null, error);
           error.getMessage();
          return("/index");
        }
        return ("redirect/mascota");
    }
    
    
     @GetMapping("/editar-mascota")
    public String editarPerfil(HttpSession session, @RequestParam(required = false) String id, @RequestParam(required = false) String accion, ModelMap model) {
       
        Usuario login = (Usuario) session.getAttribute("usuariosession");

        if (login == null) {
            return "redirect:/login";
        }
        try{
        Mascota mascota = new Mascota();
        model.put("perfil", mascota);
        }catch(Exception error){
            error.getMessage();
            return "mascota.html";
        }
        
        return "mascota.html";
    }
    

}
