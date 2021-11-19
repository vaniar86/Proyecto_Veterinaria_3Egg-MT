/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRol;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Yoel
 */
@Service
public class UsuarioServicio implements UserDetailsService {
    //implements UserDetailsService   para el bloque de seguridad
    
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void registrar (String mail, String clave, String clave2, EnumRol rol)throws ErrorServicio{
    
    //public void registrar (String nombre, String apellido, String mail, String clave)throws ErrorServicio{
        System.out.println(clave + "     " + clave2);
        validar(mail, clave, clave2, rol);
        
        Usuario usuario = new Usuario();
        usuario.setMail(mail);
        usuario.setRol(EnumRol.CLIENTE);
       
        
        // encriptar la clave
        // usar el mismo encriptacion que definimos en el archivo Tindermascotas3Application
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setPass(encriptada);
            
        usuario.setFechaAlta(new Date());
        
        usuarioRepositorio.save(usuario);
        
        // ----->>>>>>>  
        //NotificacionServicio.enviar("baruj aba al tinder de mascotas", "Tinder de mascotas", usuario.getMail());
    }
    
    @Transactional
    public void modificar(String mail, String clave, String clave2, EnumRol rol) throws ErrorServicio{
        
        validar(mail, clave, clave2, rol);
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(mail);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
        
            usuario.setMail(mail);
            
            // encriptar la clave
            // usar el mismo encriptacion que definimos en el archivo Tindermascotas3Application
            String encriptada = new BCryptPasswordEncoder().encode(clave2);
            usuario.setPass(encriptada);
            
            usuarioRepositorio.save(usuario);
        }else{
            throw new ErrorServicio("No se encontro el usuario solicitado");
            
        }
    }
    
    public void validar(String mail, String clave, String clave2, EnumRol rol) throws ErrorServicio{

        
        if(mail == null || mail.isEmpty()){
            throw new ErrorServicio("error con el mail");
        }
        
        if(clave == null || clave.isEmpty() || clave.length()<6){
            throw new ErrorServicio("error con la clave, longitud menor que 6 digitos");
        }
        if(clave2 == null || clave.isEmpty() || clave2.length()<6){
            throw new ErrorServicio("error con la clave, longitud menor que 6 digitos");
        }
        
        if(!clave.equals(clave2)){
            
            throw new ErrorServicio("no coinciden las claves");
        }
        
        if(rol == null){
            throw new ErrorServicio("no se identifico rol");
        }
    }
    
    
    
    
    
    
    
    
    
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        System.out.println(usuario);
        if(usuario != null){
            System.out.println("ingreso al if");
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
           /*if(usuario.getRol().CLIENTE != EnumRol.CLIENTE){
               GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_PROFESIONAL");
                permisos.add(p1);
           }else{
                GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
                permisos.add(p1);
           }*/
            System.out.println(usuario.getRol());
           GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
                permisos.add(p1);


            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

           
            
            //constructor usuario, clave, lista de permisos
            User user = new User(usuario.getMail(), usuario.getPass(), permisos);
            
            System.out.println("MOSTRAR USER   " + user.toString());
            return user;
        }else{
            return null;
        }//fin bloque de seguriada
        
    }
}
