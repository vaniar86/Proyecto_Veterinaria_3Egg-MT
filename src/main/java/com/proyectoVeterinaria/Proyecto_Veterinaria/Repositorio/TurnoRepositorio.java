package com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

    
    @Query("SELECT c, m , a , d  FROM Turno c INNER JOIN c.mascota m INNER JOIN c.atencion a INNER JOIN m.cliente d WHERE c.profesional.id = :id")
    public List<Turno> buscarTurnosPorProfesional(@Param("id")String id);
    
     @Query("SELECT c, m , a , d  FROM Turno c INNER JOIN c.mascota m INNER JOIN c.atencion a INNER JOIN m.cliente d WHERE c.id = :id")
    public Turno buscarTurnoPorId(@Param("id")String id);
    
    /*
    devuelve los turnos de X profesional, descomentar solo cuando vaya a usarse
    @Query("SELECT c FROM Turno c WHERE c.profesional = :id")
    public List<Turno> buscarTurnosPorProfesional(@Param("profesional")String id);
    buscarTurnosAsignados
     */
    
     @Query("Select t, a, p.nombre FROM Turno t  INNER JOIN t.atencion a INNER JOIN t.profesional p WHERE t.mascota.id = :id")
    public  List<Turno> buscarTurnosPorMascotas(@Param ("id")String id);
    
     @Query("Select t, a, p, DATE_FORMAT(a.fechaAtencion, '%d %M %Y') AS fechaT FROM Turno t INNER JOIN t.atencion a INNER JOIN t.profesional p WHERE t.mascota.id = :id AND t.status= 'ATENDIDO' ")
    public List<Turno> buscarTurnosAtendidosXMasc(@Param ("id")String id);
    
     @Query("Select t, p, a FROM Turno t INNER JOIN t.atencion a INNER JOIN t.profesional p WHERE t.status != 'DISPONIBLE' ")
    public List<Turno> buscarTurnosAsignados();
    
}
