
package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRol;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusTurno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumTipoAtencion;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.ProfesionalRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ClienteServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.MascotaServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ProfesionalServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.TurnoServicio;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.util.Arrays;
import java.util.List;
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
import static sun.security.jgss.GSSUtil.login;


/**
 *
 * @author Vanina
 */

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    
    @Autowired
    private ProfesionalServicio profesionalServicio;
    
    @Autowired
    private MascotaServicio mascotaServicio;
    
    @Autowired
    private TurnoServicio turnoServicio;
    
    @GetMapping("/") 
    public String index(){
        return "index.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    
    
    @GetMapping("/login") 
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap modelo){
    // public String login(HttpSession session, ModelMap modelo, @RequestParam(required=false) String error, @RequestParam(required=false) String logout) {
       // System.out.println(" portal controlador  " + error);
        //System.out.println(session.toString());
        //System.out.println(session.getId());
       //if(error != "" || error !=null){
      /* if(error !=null){    
           System.out.println("error 2 portasl controlador" + error);
            modelo.put("error", "Nombre de usuario o clave Incorrectos");
       }*/
        
       if (logout != null) {
            modelo.put("logout", "¡Se ha deslogueado correctamente de nuestro sitio web!");
        }
        
        
        if (error != null) {
            modelo.put("error", "¡El usuario o contraseña no coinciden!");
            //session.invalidate();
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
   
    
    /* =================================   T U R N O S ========================= */

    @GetMapping("/turno") 
    public String turno(ModelMap modelo){
        //modelo.addAttribute("actProfe", 0);
        //modelo.addAttribute("actTurno", 0);
        //modelo.addAttribute("act", 3);
        
       
        return "turno.html";
    }
    
 
    
    @PostMapping("/nuevoTurnoATENCION")
    public String nuevoTurnoATENCION(HttpSession session, ModelMap modelo, @RequestParam(required = false) String tipoDeAtencion, @RequestParam(required = false) String profesionalSelec, @RequestParam(required = false) String horarioTurno, @RequestParam(required = false) String mascotaSelec) throws ErrorServicio{
        System.out.println(tipoDeAtencion);
        System.out.println(profesionalSelec);
        System.out.println(horarioTurno);
        System.out.println(mascotaSelec);
        
        String Act = "Open this select menu";
       
        System.out.println("estado profesionalSelec" + profesionalSelec);
        
        if(tipoDeAtencion.equalsIgnoreCase(Act)){
            modelo.addAttribute("actProfe", 0);
            
            
        }else{
             
             List<Profesional> listaProfesionales;
             
             
            switch(tipoDeAtencion)
            {
               case "1" :
                  // Atencion
                   
                   listaProfesionales = profesionalServicio.listarProfesionalPorRol(EnumRol.VETERINARIO);
                   modelo.addAttribute("actProfe", 2);
                   
                   if(!listaProfesionales.isEmpty()){
                                modelo.addAttribute("profesionalSelec", listaProfesionales);
                            }else{
                                modelo.put("message", "No hay Veteninarios en la nomina");
                            }
                   
                   System.out.println("PROFESIONAL     VETENINARIO");
                   //listaProfesionales = profesionalRepositorio.findAll();
                   /*System.out.println(listaProfesionales.toString());*/
                   //profesionalSelec = "me cago en vos"; 
                  break; 

               case "2" :
                   //Peluqueria
                  listaProfesionales = profesionalServicio.listarProfesionalPorRol(EnumRol.PELUQUERO);
                   modelo.addAttribute("actProfe", 2);
                   
                   if(!listaProfesionales.isEmpty()){
                                modelo.addAttribute("profesionalSelec", listaProfesionales);
                            }else{
                                modelo.put("message", "No hay peluqueros caninos en la nomina");
                            }
                   
                   System.out.println("PROFESIONAL     PELUQUERO");
                   //profesionalSelec = "me cago en vos";
                  break; 
                  
               case "3" :
                  // Vacunacion
                   listaProfesionales = profesionalServicio.listarProfesionalPorRol(EnumRol.ENFERMERO);

                   modelo.addAttribute("actProfe", 2);
                   
                   if(!listaProfesionales.isEmpty()){
                                modelo.addAttribute("profesionalSelec", listaProfesionales);
                            }else{
                                modelo.put("message", "No hay enfermeros en la nomina");
                            }
                   
                   
                   System.out.println("PROFESIONAL     ENFERMERO");
                   //profesionalSelec = "me cago en vos";
                  break; 
               default : 
                    modelo.addAttribute("actProfe", 0);
            }
            
            
           // System.out.println(request.getParameter("profesionalSelec"));
            
           
           
        }
        
        
            
        
        
        System.out.println("-------------------------------");
        
        return "turno.html";
    }
    
    @PostMapping("/nuevoTurnoPROFESIONAL")
    public String nuevoTurnoPROFESIONAL(HttpSession session, ModelMap modelo, @RequestParam(required = false) String tipoDeAtencion, @RequestParam(required = false) String profesionalSelec, @RequestParam(required = false) String horarioTurno, @RequestParam(required = false) String mascotaSelec) throws ErrorServicio{
        
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null) {
            return "redirect:/login";
        }
        
        System.out.println("tipoDeAtencion  " + tipoDeAtencion);
        System.out.println("profesionalSelec  " + profesionalSelec);
        System.out.println("horarioTurno  " + horarioTurno);
        System.out.println("mascotaSelec  " + mascotaSelec);
        
        String Act = "Open this select menu";
        
         if(profesionalSelec == null || profesionalSelec.equals(Act)){
                modelo.addAttribute("actTurno", 0);
                System.out.println("op1");
                
            }else{
                /*codigo para cargar los turnos disponible y las mascotas*/
                
                modelo.addAttribute("actTurno", 3);
                System.out.println("op2");
                
                //vuelvo a setear el profesional, como unico profesional
                Profesional profesional;
                profesional = profesionalRepositorio.getById(profesionalSelec);
                modelo.addAttribute("profesionalSelec", profesional);
                modelo.addAttribute("actProfe", 2);
                
                /*List<Turno> turnosDisponiblesPorProfesional = turnoServicio.listarTurnoDisponiblesPorProfesional(profesionalSelec);
         //       modelo.addAttribute("horarioTurno", turnosDisponiblesPorProfesional);
                modelo.addAttribute("horarioTurno", turnosDisponiblesPorProfesional);
                System.out.println("tamaño lista turnosDisponiblesPorProfesional  " + turnosDisponiblesPorProfesional.size());
                
                System.out.println("usuario   " + session.getId());
                System.out.println("usuario   " + session.toString());*/
                        
                        

                            List<Turno> turnosDisponiblesPorProfesional = turnoServicio.listarTurnoDisponiblesPorProfesional(profesionalSelec);           

                            if(!turnosDisponiblesPorProfesional.isEmpty()){
                                modelo.addAttribute("horarioTurno", turnosDisponiblesPorProfesional);
                            }else{
                                modelo.put("message", "El profesional no tiene turnos disponibles");
                            }

                       
                
                
                /*Cliente cliente = clienteServicio.buscarClientePorUsuario(login.getMail());
                List<Mascota> mascotas = mascotaServicio.buscarMascotaPorCliente(cliente.getId()); 
                
                modelo.addAttribute("mascotaSelec", mascotas);
                System.out.println("tamaño lista mascotas  " + mascotas.size());*/
                
                
                        try{
                            Cliente cliente = clienteServicio.buscarClientePorUsuario(login.getMail());

                            List<Mascota> mascotas = mascotaServicio.buscarMascotaPorCliente(cliente.getId());           

                            if(!mascotas.isEmpty()){
                                modelo.put("mascotaSelec", mascotas);
                            }else{
                                modelo.put("message", "El Cliente no tiene mascotas para motrar");
                            }

                       } catch (ErrorServicio e) {
                           throw new ErrorServicio("Ocurrió un error inesperado por favor pongase en contacto con el servicio tecnico");
                       }

                }

               return "turno.html";
           } 
    
    @PostMapping("/nuevoTurnoCOMPLETO")
    public String nuevoTurnoCOMPLETO(HttpSession session, ModelMap modelo, @RequestParam(required = false) String tipoDeAtencion, @RequestParam(required = false) String profesionalSelec, @RequestParam(required = false) String horarioTurno, @RequestParam(required = false) String mascotaSelec) throws ErrorServicio{
        System.out.println("tipoDeAtencion  " + tipoDeAtencion);
        System.out.println("profesionalSelec  " + profesionalSelec);
        System.out.println("horarioTurno  " + horarioTurno);
        System.out.println("mascotaSelec  "  + mascotaSelec);
        
        String Act = "Open this select menu";
        
        System.out.println("la variable prefesionalSelec" + profesionalSelec);
        
        turnoServicio.asignarTurno(mascotaSelec, horarioTurno);
        
        
         if(profesionalSelec == null || profesionalSelec.equals(Act)){
                modelo.addAttribute("actTurno", 0);
                System.out.println("op1");
            }else{
                /*codigoidProfesional para cargar los turnos disponible y las mascotas*/
                
                modelo.addAttribute("actTurno", 3);
                System.out.println("op2");
                
                
                //System.out.println(profesionalSelec);
            }
    
        return "turno.html";
    }
}
