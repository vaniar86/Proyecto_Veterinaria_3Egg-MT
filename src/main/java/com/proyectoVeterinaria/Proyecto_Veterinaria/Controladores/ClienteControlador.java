package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.ClienteRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.ClienteServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.MascotaServicio;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Martin
 */
@Controller
@RequestMapping("/usuario")
public class ClienteControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private MascotaServicio mascotaServicio;

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE_REGISTRADO')")
    @GetMapping("/modificar-Cliente")
    public String modificarCliente(HttpSession session, @RequestParam(required = false) String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String direccion, @RequestParam Long telefono, @RequestParam String mail, @RequestParam String password, @RequestParam String password2, ModelMap model) {
        Cliente cliente = null;

        Cliente login = (Cliente) session.getAttribute("usuariosession");

        try {
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/inicio";
            }
            Optional<Cliente> respuesta = clienteRepositorio.findById(id);
            if (respuesta.isPresent()) {
                cliente = respuesta.get();
            }

            clienteServicio.modificar(id, nombre, apellido, direccion, telefono, mail, password, password2);

            session.setAttribute("usuariosession", cliente);
            return "redirect:/inicio";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            model.put("perfil", cliente);
            return "cliente.html";
        }
    }

    @PostMapping("/eliminar-cliente")
    public String eliminarCliente(HttpSession session, @RequestParam String id) {
        try {
            Cliente login = (Cliente) session.getAttribute("usuariosession");
            clienteServicio.eliminar(login.getId(), id);
        } catch (ErrorServicio ex) {
             Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/index";
    }

    @GetMapping("/mis-mascotas")
    public String misMascotas(HttpSession session, ModelMap model) {
        Cliente login = (Cliente) session.getAttribute("usuariosession");
        if (login == null) {
            return "redirect:/login";
        }
        List<Mascota> mascotas = mascotaServicio.buscarMascotaPorId(login.getId());
        //falta el metodo ese para traerme mascota
        model.put("mascotas", mascotas);
        return "mascotas";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN_REGISTRADO')")
    @GetMapping("/listado-clientes")
    public String listaClientes(HttpSession session, ModelMap model) {
        Cliente login = (Cliente) session.getAttribute("usuariosession");
        if (login == null) {
            return "redirect:/login";
        }
        List<Cliente> clientes = clienteServicio.listarTodos(login.getId());
        model.put("clientes", clientes);
        return "clientes";
    }
}
