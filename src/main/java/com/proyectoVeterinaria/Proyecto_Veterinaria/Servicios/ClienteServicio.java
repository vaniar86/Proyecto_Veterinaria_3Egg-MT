package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio implements UserDetailsService {

    @Transactional
    public void registrar(String nombre, String apellido, String direccion, Long telefono, String mail, String password, String password2) throws ErrorServicio {
        //FALTA la clase ErrorServicio en package Errores,
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Exception("El apellido del usuario no puede ser nulo");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new Exception("La direccion del usuario no puede ser nulo");
        }
        if (telefono == null) {
            throw new Exception("El número del usuario no puede ser nulo");
        }
        if (mail == null || mail.isEmpty()) {
            throw new Exception("El mail es un requisito para el registro");
        }
        if (password == null || password.isEmpty()) {
            throw new Exception("El password es un requisito para el registro");
        }
        if (password2 == null || password2.isEmpty()) {
            throw new Exception("Confirme su contraseña");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);

        Usuario usuario = new Usuario();
        usuario.setRol(0);
        usuario.setMail(mail);
        usuario.setPass(password);

        cliente.setIdUsuario(usuario);
    }

    @Transactional
    public void modificar(String id, String nombre, String apellido, String direccion, Long telefono, String mail, String password, String password2) {
    }

    //metodo interno de Registrar, que verifica al momento de registrar, que ese mail no este en uso 
    private void validar(String email) {
    }

    @Transactional
    public void eliminar(String id) {
    }

    public //listar nombres (usamos el metodo mostrar cliente y mostramos muchos)
    //buscar por id (usamos el metodo buscar por id, dentro de los demas metodos que hay en clienteservicio)
    //mostrar cliente por id (visualiza perfil)
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

    }
}
