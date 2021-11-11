package com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

    /*
    devuelve los turnos de X profesional, descomentar solo cuando vaya a usarse
    @Query("SELECT c FROM Turno c WHERE c.profesional = :id")
    public List<Turno> buscarTurnosPorProfesional(@Param("profesional")String id);
     */
}
