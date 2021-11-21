/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRol;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.UsuarioRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.UsuarioServicio;
import java.util.List;
import java.util.Optional;
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

/**
 *
 * @author pc
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/editar-pefil")
    public String editarPerfil(HttpSession session, ModelMap model, @RequestParam String id) {
    //public String editarPerfil(HttpSession session, @RequestParam String id, ModelMap model) {
       
        Usuario login = (Usuario) session.getAttribute("usuariosession");
       
        System.out.println("usuarioControlador  " + login.toString());
        
        
        if (login == null || !login.getMail().equals(id)) {
            return "redirect:/inicio";
        }

        try {
            Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
            if (respuesta.isPresent()) {
                Usuario usuario = respuesta.get();
                model.addAttribute("perfil", usuario);
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "perfil.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-perfil")
    public String registrar(ModelMap model, HttpSession session, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2) {
    //public String registrar(ModelMap model, HttpSession session, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam EnumRolProfesional rol) {
        Usuario usuario = null;

        Usuario login = (Usuario) session.getAttribute("usuariosession");

        try {
            if (login == null || !login.getMail().equals(mail)) {
                return "redirect:/inicio";
            }
            Optional<Usuario> respuesta = usuarioRepositorio.findById(mail);
            if (respuesta.isPresent()) {
                usuario = respuesta.get();
            }

            
            //usuarioServicio.modificar(id, nombre, apellido, mail, clave1, archivo, clave2, idZona);
            usuarioServicio.modificar(mail, clave1, clave2, EnumRol.CLIENTE);

            session.setAttribute("usuariosession", usuario);
            return "redirect:/index";
        } catch (Exception e) {
            //List<Zona> zonas = zonaRepositorio.findAll();
            //model.put("zonas", zonas);
            /*model.put("error", e.getMessage());
            model.put("perfil", usuario);
            return "perfil.html";*/
            return "index.html";
        }
    }
}
