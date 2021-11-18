package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Foto;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumEspecie;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRaza;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusMascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.MascotaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MascotaServicio {
    
    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void agregarMascota(String nombre, String idCliente , EnumEspecie especie, EnumRaza raza, int edad,  MultipartFile archivo) throws ErrorServicio {
       
        validar(nombre, idCliente, raza, especie);
        
        Cliente cliente = clienteServicio.buscarPorId(idCliente);
        
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEdad(edad);
        mascota.setRaza(raza);
        mascota.setEspecie(especie);
        mascota.setStatus(EnumStatusMascota.ALTA);
        mascota.setCliente(cliente);
      
        
        
        Foto foto = fotoServicio.guardar(archivo);
        mascota.setFoto(foto);
        
        mascotaRepositorio.save(mascota);
         

    }

    @Transactional
    public void modificar(String idMascota, String nombre, String idCliente, EnumEspecie especie, EnumRaza raza, int edad, MultipartFile archivo) throws ErrorServicio {
        //se saco el EnumStatusMascota, ya que la logica de Modificar, directamente lo "setea" a EnumMascotaStatus.MODIFICADO , desdes la vista el cliente toca el boton modificar y cambia datos basicos, si tocas "eliminar" es para borrar al perro de tu lista, y suponemos que "se murio" o lo quisiste sacar....
        if(idMascota == null || idMascota.isEmpty()){
            throw new ErrorServicio("error en el id de la mascota");
        }
        validar(nombre, idCliente , raza, especie);
        
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if(respuesta.isPresent()){
            Mascota mascota = respuesta.get();
            if(mascota.getCliente().getId().equals(idMascota)){
                mascota.setNombre(nombre);
                mascota.setEdad(edad);
                mascota.setRaza(raza);
                mascota.setEspecie(especie);
                
                String idFoto = null;
                if(mascota.getFoto().getId() != null){
                    idFoto = mascota.getFoto().getId();
                }
                
                Foto foto = fotoServicio.modificar(archivo, idFoto);
                mascota.setFoto(foto);
                
                mascota.setStatus(EnumStatusMascota.MODIFICADO);
                
                mascotaRepositorio.save(mascota);
            }else{
                throw new ErrorServicio("el usuario y la mascota no coinsiden");
            }
            
        }else{
            throw new ErrorServicio("la mascota no existe");
        }
    }


    public void deshabilitar() {
    }

    
    @Transactional
    public void eliminar (String idMascota, String idUsuario)throws ErrorServicio{
        
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if(respuesta.isPresent()){
            Mascota mascota = respuesta.get();
            if(mascota.getCliente().getId().equals(idMascota)){
                mascota.setStatus(EnumStatusMascota.BAJA);
                
                mascotaRepositorio.save(mascota);
            }else{
                throw new ErrorServicio("el usuario y la mascota no coinsiden");
            }
            
        }else{
            throw new ErrorServicio("la mascota no existe");
        }
    
    }
    
    private void validar(String nombre, String idCliente, EnumRaza raza, EnumEspecie especie) throws ErrorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("error en el nombre");
        }
        if(idCliente == null ){
            throw new ErrorServicio("error en el cliente");
        }
        if(raza == null){
            throw new ErrorServicio("error en la raza");
        }
        if(especie == null){
            throw new ErrorServicio("error en la especie");
        }
    }
    
    public Mascota buscarMascotaPorId(String id) throws ErrorServicio {
        Optional<Mascota> respuesta = mascotaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("La mascota seleccionada no existe.");
        }
    }
    
     public ArrayList<Mascota> listarMascotas(String id) {
        ArrayList<Mascota> mascotas = new ArrayList(mascotaRepositorio.findAll());
        return mascotas;
    }
     
     public List<Mascota> buscarMascotaPorCliente(String userId){
       
             return mascotaRepositorio.findByUser(userId);
        
     }
}
