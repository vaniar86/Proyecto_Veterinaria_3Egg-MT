package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Transactional
    public Foto guardar(MultipartFile archivo) {
        return null ;
    }
    
    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) {
        return null;
    }

}
