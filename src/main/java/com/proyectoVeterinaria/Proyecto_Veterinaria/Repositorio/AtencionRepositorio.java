
package com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Atencion;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AtencionRepositorio extends JpaRepository<Atencion, String> {
   
    
    @Query("SELECT c FROM Atencion c WHERE c.id = :id")
    public List<Atencion> buscarAtencionPorUsuario(@Param("id")String id);
    
    
}
