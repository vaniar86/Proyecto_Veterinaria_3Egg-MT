package com.proyectoVeterinaria.Proyecto_Veterinaria.Controladores;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios.MascotaServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.MascotaRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

    @Autowired
    private MascotaServicio mascotaServicio;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @GetMapping("/mascota/{id}")
    public ResponseEntity<byte[]> fotoMascota(@PathVariable String id) throws ErrorServicio {

        try {
            Optional<Mascota> respuesta = mascotaRepositorio.findById(id);
            Mascota mascota = respuesta.get();
            if (mascota.getFoto() == null) {
                throw new ErrorServicio("La mascota no tiene una foto");
            }
            byte[] foto = mascota.getFoto().getContenido();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (ErrorServicio e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
