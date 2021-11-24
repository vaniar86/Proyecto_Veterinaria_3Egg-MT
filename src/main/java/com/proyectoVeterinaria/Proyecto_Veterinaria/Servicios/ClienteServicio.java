package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Usuario;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRol;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRolProfesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.ClienteRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(String nombre, String apellido, String direccion, Long telefono, String mail, String password, String password2) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new ErrorServicio("La direccion del usuario no puede ser nulo");
        }
        if (telefono == null) {
            throw new ErrorServicio("El número del telefono no puede ser nulo");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("El mail es un requisito para el registro");
        }
        if (password == null || password.isEmpty()) {
            throw new ErrorServicio("El password es un requisito para el registro");
        }
        if (password2 == null || password2.isEmpty()) {
            throw new ErrorServicio("Confirme su contraseña");
        }
                          
        
         
        Cliente verifyCliente = clienteRepositorio.findByMail(mail);
        if(verifyCliente == null){
            usuarioServicio.registrar(mail, password, password2, EnumRol.CLIENTE);
            Cliente cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            
            
            //recupero el usuario de la db y seteo el usuario al cliente
            Usuario usuario = usuarioRepositorio.findById(mail).get();
            cliente.setIdUsuario(usuario);
            
              //creo el cliente
            clienteRepositorio.save(cliente);
        }else{
            throw  new ErrorServicio ("Ya existe un usuario cargado con ese email");
        }
        
         
        
        
       
        
        //notificar por mail "bienvenido usuario" ????
    }

    @Transactional
    public void modificar(String id, String nombre, String apellido, String direccion, Long telefono, String mail, String password, String password2) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);

        Cliente cliente = respuesta.get();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        
        
        usuarioServicio.modificar(mail, password, password2, EnumRol.CLIENTE);

        
        //recupero el usuario de la db y seteo el usuario al cliente
        Usuario usuario = new Usuario();
        usuario = usuarioRepositorio.findById(mail).get();
        cliente.setIdUsuario(usuario);

        cliente.setIdUsuario(usuario);

        clienteRepositorio.save(cliente);
        /*mandar notificacion por mail "se han modificado sus datos" "fue usted?, si fue usted rechaze este mail, sino
            contactese urgente" ..... solo si tenemos ganas de notificar */
    }

    //metodo interno de Registrar, que verifica al momento de registrar, que ese mail no este en uso 
    private void validar(String email) throws ErrorServicio {
    }

    @Transactional
    public void eliminar(String idUsuario, String idCliente) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(idCliente);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            if (cliente.getIdUsuario().equals(idUsuario)) {
                clienteRepositorio.deleteById(idCliente);
            } else {
                throw new ErrorServicio("No existe ese cliente");
            }
        }
    }

    public Cliente buscarPorId(String id) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("El cliente solicitado no existe.");
        }
    }

    public Cliente mostrarPorId(String id) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            //return clienteRepositorio.buscarClientePorId(id);
            return null;
        } else {
            throw new ErrorServicio("El cliente solicitado no existe.");
        }
        /*  AGREGAR UN TO STRING*/
    }

    public ArrayList<Cliente> listarTodos(String id) {
        ArrayList<Cliente> clientes = new ArrayList(clienteRepositorio.findAll());
        return clientes;
    }
    
   public Cliente  buscarClientePorUsuario(String mail)throws ErrorServicio{
       
       try {
           return clienteRepositorio.findByMail(mail);
       } catch (Exception e) {
           throw  new ErrorServicio("Ocurrió un error");
       }
   }
   
//   public Cliente FindByQuery(String query) {
//       String nombre;
//        try{
//            nombre = query;   
//        } catch (Exception e){
//            nombre= null;
//        }
//        return clienteRepositorio.findByQuery("%"+query+"%", nombre);
//   }
}
