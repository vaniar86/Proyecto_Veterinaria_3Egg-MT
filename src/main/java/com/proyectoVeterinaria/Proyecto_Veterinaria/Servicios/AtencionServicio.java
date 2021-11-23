package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Atencion;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumAtencionPuntual;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumTipoAtencion;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.AtencionRepositorio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.TurnoRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AtencionServicio {

    
/*String Id Primary Key defaultValue
Enum TipoAtencion
Enum AtencionPuntual
String Descripcion
String Prescripcion
*/
     @Autowired
    private TurnoServicio turnoServicio;
     
      @Autowired
    private TurnoRepositorio turnoRepositorio;
     
     
    @Autowired
    private AtencionRepositorio atencionRepositorio;
    
    @Transactional
    public void registrarAtencion (String turnoId, EnumTipoAtencion tipoAtencion, EnumAtencionPuntual atencionPuntual, String descripcion, String prescripcion)throws ErrorServicio{
    
    //public void registrar (String nombre, String apellido, String mail, String clave)throws ErrorServicio{
        
        validar(atencionPuntual, descripcion, prescripcion);
        
        Atencion atencion = new Atencion();
        atencion.setTipoAtencion(tipoAtencion);
        atencion.setAtencionPuntual(atencionPuntual);
        atencion.setDescripcion(descripcion);
        atencion.setPrescripcion(prescripcion);
        atencion.setFechaAtencion(new Date());
        
        Turno turno = turnoServicio.buscarTurnoPorId(turnoId);
        turno.setAtencion(atencion);
        
        turnoRepositorio.save(turno);
       
        atencionRepositorio.save(atencion);
        
       // mailServicio.enviar("baruj aba al tinder de mascotas", "Tinder de mascotas", usuario.getMail());
    }
    
    
    
     @Transactional
    public void modificar(String turnoId, String idAtencion, EnumAtencionPuntual atencionPuntual, String descripcion, String prescripcion) throws ErrorServicio {
        
        validar(atencionPuntual, descripcion, prescripcion);
        
        Optional<Atencion> respuesta = atencionRepositorio.findById(idAtencion);
        if(respuesta.isPresent()){
            Atencion atencion = respuesta.get();
            
            
            atencion.setAtencionPuntual(atencionPuntual);
            atencion.setDescripcion(descripcion);
            atencion.setPrescripcion(prescripcion);
            atencion.setFechaAtencion(new Date());
                
                atencionRepositorio.save(atencion);
            
            
        }else{
            throw new ErrorServicio("la mascota no existe");
        }
    }
    
    public Atencion buscarAtencionPorId(String id){
        return atencionRepositorio.buscarAtencionPorId(id);
    }
    
    private void validar( EnumAtencionPuntual atencionPuntual, String descripcion, String prescripcion) throws ErrorServicio{
        if(descripcion == null || descripcion.isEmpty()){
            throw new ErrorServicio("error en la descripcion");
        }
        if(prescripcion == null || prescripcion.isEmpty()){
            throw new ErrorServicio("error en la prescripcion");
        }
      
        if(atencionPuntual == null){
            throw new ErrorServicio("error en la atencion puntual");
        }
        
    }



}


