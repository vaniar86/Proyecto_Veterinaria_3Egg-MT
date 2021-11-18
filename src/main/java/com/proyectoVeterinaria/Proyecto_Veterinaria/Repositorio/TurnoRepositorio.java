package com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

    
    @Query("SELECT c FROM Turno c WHERE c.profesional.id = :id")
    public List<Turno> buscarTurnosPorProfesional(@Param("id")String id);
    
    
}
