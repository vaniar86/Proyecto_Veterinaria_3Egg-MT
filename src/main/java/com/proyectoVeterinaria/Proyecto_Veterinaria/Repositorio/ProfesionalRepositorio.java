
package com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Profesional;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Turno;
import com.proyectoVeterinaria.Proyecto_Veterinaria.Enumeraciones.EnumRol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String>{
    @Query("SELECT c FROM Profesional c WHERE c.idUsuario.id = :id")
    public Profesional BuscarProfesional(@Param("id")String id);
    
   /* @Query("SELECT * FROM Profesional ")
    public List<Profesional> BuscarProfesionalPorRol();*/

    //public List<Profesional> BuscarProfesionalPorRol(EnumRol enumRol);
    @Query("SELECT c FROM Profesional c WHERE c.rol = :rol")
    public List<Profesional> buscarProfesionalPorRol(@Param("rol") EnumRol rol);
    
}