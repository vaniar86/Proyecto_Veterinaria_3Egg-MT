package com.proyectoVeterinaria.Proyecto_Veterinaria.Servicios;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Atencion;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumAtencionPuntual;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumTipoAtencion;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Errores.ErrorServicio;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio.AtencionRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AtencionServicio {

    public AtencionServicio() {
    }


    
    
    public String tipoAtencion() {
        return null;
    }

    public String Descripcion() {
        return null;
    }

    public String Prescipcion() {
        return null;
    }

    @Transactional
    public void modificar() {
    }

    public void validar() {
    }
/*String Id Primary Key defaultValue
Enum TipoAtencion
Enum AtencionPuntual
String Descripcion
String Prescripcion
*/

    @Autowired
    AtencionRepositorio atencionRepositorio;
    
    @Transactional
    public void registrarAtencion (EnumTipoAtencion tipoAtencion, EnumAtencionPuntual atencionPuntual, String descripcion, String prescripcion)throws ErrorServicio{
    
    //public void registrar (String nombre, String apellido, String mail, String clave)throws ErrorServicio{
        
        validar(tipoAtencion, atencionPuntual, descripcion, prescripcion);
        
        Atencion atencion = new Atencion();
        atencion.setTipoAtencion(tipoAtencion);
        atencion.setAtencionPuntual(atencionPuntual);
        atencion.setDescripcion(descripcion);
        atencion.setPrescripcion(prescripcion);
        atencion.setFechaAtencion(new Date());
        
        atencionRepositorio.save(atencion);
        
       // mailServicio.enviar("baruj aba al tinder de mascotas", "Tinder de mascotas", usuario.getMail());
    }
    
    
    
     @Transactional
    public void modificar(String idAtencion, EnumTipoAtencion tipoAtencion, EnumAtencionPuntual atencionPuntual, String descripcion, String prescripcion) throws ErrorServicio {
        
        if(idAtencion == null || idAtencion.isEmpty()){
            throw new ErrorServicio("error en el id de la ATENCION");
        }
        validar(tipoAtencion, atencionPuntual, descripcion, prescripcion);
        
        Optional<Atencion> respuesta = atencionRepositorio.findById(idAtencion);
        if(respuesta.isPresent()){
            Atencion atencion = respuesta.get();
            
            atencion.setTipoAtencion(tipoAtencion);
            atencion.setAtencionPuntual(atencionPuntual);
            atencion.setDescripcion(descripcion);
            atencion.setPrescripcion(prescripcion);
            atencion.setFechaAtencion(new Date());
                
                atencionRepositorio.save(atencion);
            
            
        }else{
            throw new ErrorServicio("la mascota no existe");
        }
    }
    
    private void validar(EnumTipoAtencion tipoAtencion, EnumAtencionPuntual atencionPuntual, String descripcion, String prescripcion) throws ErrorServicio{
        if(descripcion == null || descripcion.isEmpty()){
            throw new ErrorServicio("error en la descripcion");
        }
        if(prescripcion == null || prescripcion.isEmpty()){
            throw new ErrorServicio("error en la prescripcion");
        }
        if(tipoAtencion == null ){
            throw new ErrorServicio("error en el tipo de atencion");
        }
        if(atencionPuntual == null){
            throw new ErrorServicio("error en la atencion puntual");
        }
        
    }



}


