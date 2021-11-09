
package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumEspecie;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRaza;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusMascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
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
    private String perro;
    @Autowired
    private MascotaServicio mascotaServicio;

    
    @PostMapping("/actualizar-mascota")
    public String Actualizar(HttpSession sesion,ModelMap model,@RequestParam(required = false) String id,@RequestParam String nombre,@RequestParam EnumRaza raza,@RequestParam int edad,@RequestParam EnumEspecie especie,@RequestParam int status,MultipartFile archivo,@RequestParam(required = false) Cliente cliente){
     
//Cambiar los parametros de los metodos de mascotaServicio, tiene que recibir un enum y no un int (EnumStatusMascota)        
     try{
         if (id==null|| id.isEmpty()) {
             mascotaServicio.agregarMascota(nombre, cliente, especie, raza, edad, status, archivo);
         }else{
             mascotaServicio.modificar(id,nombre, cliente, especie, raza, edad, archivo);
             return ("redirect/mascota");
         }
     
     }catch(Exception Error){
         Mascota mascota = new Mascota();
         mascota.setNombre(nombre);
         mascota.setRaza(raza);
         mascota.setStatus(EnumStatusMascota.ALTA);
         mascota.setEspecie(especie);
         mascota.setEdad(edad);
 //        mascota.setCliente(cliente);
  
        return ("/");
    }
     return ("/");
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
