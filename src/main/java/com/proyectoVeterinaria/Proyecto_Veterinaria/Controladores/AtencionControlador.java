package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Atencion;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusTurno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.AtencionRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.TurnoRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.AtencionServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ClienteServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.MascotaServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.TurnoServicio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author Martin
 */

@Controller
@RequestMapping("/misAtenciones")
@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
public class AtencionControlador {

    @Autowired
    private AtencionServicio atencionServicio;

    @Autowired
    private TurnoServicio turnoServicio;

    @Autowired
    private AtencionRepositorio atencionRepositorio;
    
    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private MascotaServicio mascotaServicio;

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @GetMapping("")
    public String atenciones(HttpSession session, ModelMap model) throws ErrorServicio {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
       

        if (login == null) {
            return "redirect:/login";
        }
        
        try{
             Cliente cliente = clienteServicio.buscarClientePorUsuario(login.getMail());
             
             List<Mascota> mascotas = mascotaServicio.buscarMascotaPorCliente(cliente.getId());           
             
             if(!mascotas.isEmpty()){
                 model.put("mascotas", mascotas);
             }else{
                 model.put("message", "El Cliente no tiene mascotas para motrar");
             }
              
        } catch (ErrorServicio e) {
            throw new ErrorServicio("Ocurrió un error inesperado por favor pongase en contacto con el servicio tecnico");
        }
        
       
        return "/atencion";
    }

    @PostMapping("/modificarTurno")
    public String statusTurno(HttpSession session, ModelMap model, @RequestParam String id, @RequestParam String status) throws ErrorServicio {
       
        try {
            Optional<Turno> turno = turnoServicio.buscaPorId(id);
            turnoServicio.statusTurno(id, status);/*el string Status que se recibe de la vista puede estar mandando
           "cancelar, modificar u otra cosa entonces este metodo del servicio recibe 
           el idTurno y el string ese, y procesa, no hace falta varios metodos*/

        } catch (Exception Error) {
            Logger.getLogger(ProfesionalController.class.getName()).log(Level.SEVERE, null, Error);
            Error.getMessage();
            return ("/index");
        }
        return ("redirect:/atencion");
    }
//    Lo dejo comentado porque se llama en el controlador de turnos
//        @GetMapping("/turnosMascota/{id}")
//        public void turnosMascota(ModelMap model, @PathVariable String id ) throws ErrorServicio{
//       List<Turno> turnos = turnoServicio.listarTurnosPorMascota(id);
//        if(!turnos.isEmpty()){
//            model.put("turnos", turnos);
//        }else{
//            model.put("message", "La mascota no posee turnos Cargados");
//        }
//        
//    }

    @PostMapping("/modificar-turno")
    public String modificarTurno(ModelMap modelo, @RequestParam String idTurno) {
        /* 
        ESTO solo si van a haber 2 botones (cancelar y modificar TURNO)
        si existe el boton modificar, se "libera" el turno actual
        y se lo redirecciona al formulario para crear un turno nuevo,
        sino directamente nos manejamos con el boton cancelar turno, y que vayan
        a sacar turno nuevo
         */
        try {
            turnoServicio.statusTurno(idTurno, "cancelar");
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "turno.html";
        }
        return "redirect:/login/crear-turno";
    }
}
