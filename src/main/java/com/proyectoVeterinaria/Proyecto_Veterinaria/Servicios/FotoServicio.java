package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Foto;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service 
public class FotoServicio {
    
    @Autowired
    FotoRepositorio fotoRepositorio;
    
    @Transactional
    public Foto guardar(MultipartFile archivo) throws ErrorServicio{
        if(archivo != null){
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                
                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println("error en la foto"  + e.toString());
            }    
        }else{
            throw new ErrorServicio("no incluye archivo");
        }return null;
    }
    
    @Transactional
    public Foto modificar(MultipartFile archivo, String idFoto) throws ErrorServicio{
        if(archivo != null){
            try {
                Foto foto = new Foto();
                if(idFoto != null){
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if(respuesta.isPresent()){
                        foto = respuesta.get();
                    }  
                }
                
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                
                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println("error en la foto" + e.toString());
            }    
        }else{
            //throw new ErrorServicio("no incluye archivo");
        }return null;    
    }
}
