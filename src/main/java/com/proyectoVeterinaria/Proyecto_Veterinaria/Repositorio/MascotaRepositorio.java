
package com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String>{
    
     @Query("SELECT m FROM Mascota m WHERE m.cliente.id = :id")
    public List<Mascota> findByUser(@Param("id")String id);
    
    
}
