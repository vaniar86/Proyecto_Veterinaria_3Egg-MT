/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Yoel
 */
@Service
public class UsuarioServicio implements UserDetailsService {
    //implements UserDetailsService   para el bloque de seguridad
    
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        if(usuario != null){
            
            //array con los permisos   estilo GrantedAuthority
            List<GrantedAuthority> permisos = new ArrayList<>();
            
           
            
            //creamos los permisos individualmente
           /* GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTO");
            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTA");
            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTO");
            
            //construimos el array
            permisos.add(p1);
            permisos.add(p2);
            permisos.add(p3);
            
            */
           GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

           
            
            //constructor usuario, clave, lista de permisos
            User user = new User(usuario.getMail(), usuario.getPass(), permisos);
            return user;
        }else{
            return null;
        }//fin bloque de seguriada
        
    }
}
