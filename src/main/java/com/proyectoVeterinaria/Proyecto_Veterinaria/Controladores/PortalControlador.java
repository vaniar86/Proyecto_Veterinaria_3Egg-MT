
package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ClienteServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Vanina
 */

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
     @GetMapping("/") 
    public String index(){
        return "index.html";
    }
    
    @GetMapping("/login") 
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap modelo){
       System.out.println("error 1 portal controlador" + error);
       //if(error != "" || error !=null){
      /* if(error !=null){    
           System.out.println("error 2 portasl controlador" + error);
            modelo.put("error", "Nombre de usuario o clave Incorrectos");
       }*/
        
       if(logout != null){
            modelo.put("logout", "Se ha deslogueado correctamente");
       }
        return "login.html";
    }
    
    @GetMapping("/registro") 
    public String registro(){      
       
        return "registro.html";
    }
    
     @PostMapping("/registrar") 
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido,@RequestParam String direccion,@RequestParam Long telefono, @RequestParam String mail,  @RequestParam String pass1 ,@RequestParam String pass2 ){
         try {
             clienteServicio.registrar(nombre, apellido, direccion, telefono, mail, pass1, pass2);
             
             
                System.err.println("mail  " + mail);
                System.err.println("clave1  " + pass1);
                System.err.println("clave2  " + pass2);
                
         } catch (ErrorServicio e) {
  
             modelo.put("error", e.getMessage());
             modelo.put("nombre", nombre);
             modelo.put("apellido", apellido);
             modelo.put("telefono", telefono);
             modelo.put("mail", mail);
             modelo.put("pass1", pass1);
             modelo.put("pass2", pass2);
             return "registro.html";
         }
        //modelo.put("titulo", "Registro exitoso");
        //modelo.put("descripcion", "Usted se ha logrado registrar exitosamente en nuestro sitio web.");
        
        return "index.html";
    }
    
}
