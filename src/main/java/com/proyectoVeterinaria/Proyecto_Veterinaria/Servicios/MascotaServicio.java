package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Foto;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumEspecie;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRaza;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumStatusMascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.MascotaRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MascotaServicio {
    
    @Autowired
    MascotaRepositorio mascotaRepositorio;
    FotoServicio fotoServicio;

    @Transactional
    public void agregarMascota(String nombre, Cliente cliente, EnumEspecie especie, EnumRaza raza, int edad, int status, MultipartFile archivo) throws ErrorServicio {
        /*if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("Ingrese el nombre del perro");
        }*/
        
        validar(nombre, cliente, raza, especie);
        
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEdad(edad);
        mascota.setRaza(raza);
        mascota.setEspecie(especie);
        mascota.setStatus(EnumStatusMascota.ALTA);
        
        
        Foto foto = fotoServicio.guardar(archivo);
        mascota.setFoto(foto);
        
        mascotaRepositorio.save(mascota);
         

    }

    @Transactional
    public void modificar(String idMascota, String nombre, Cliente cliente, EnumEspecie especie, EnumRaza raza, int edad, int status, MultipartFile archivo) throws ErrorServicio {
        
        if(idMascota == null || idMascota.isEmpty()){
            throw new ErrorServicio("error en el id de la mascota");
        }
        validar(nombre, cliente, raza, especie);
        
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if(respuesta.isPresent()){
            Mascota mascota = respuesta.get();
            if(mascota.getCliente().getId().equals(idMascota)){
                mascota.setNombre(nombre);
                mascota.setEdad(edad);
                mascota.setRaza(raza);
                mascota.setEspecie(especie);
                //mascota.setStatus(status);
                
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
                //mascota.setBaja(new Date());
                mascota.setStatus(EnumStatusMascota.BAJA);
                
                mascotaRepositorio.save(mascota);
            }else{
                throw new ErrorServicio("el usuario y la mascota no coinsiden");
            }
            
        }else{
            throw new ErrorServicio("la mascota no existe");
        }
    
    }
    
    private void validar(String nombre, Cliente cliente, EnumRaza raza, EnumEspecie especie) throws ErrorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("error en el nombre");
        }
        if(cliente == null ){
            throw new ErrorServicio("error en el cliente");
        }
        if(raza == null){
            throw new ErrorServicio("error en la raza");
        }
        if(especie == null){
            throw new ErrorServicio("error en la especie");
        }
    }
}
