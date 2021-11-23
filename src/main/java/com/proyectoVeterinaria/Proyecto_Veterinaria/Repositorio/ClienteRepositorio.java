
package com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
    
    @Query("SELECT c FROM Cliente c WHERE c.idUsuario.id = :mail")
    public Cliente findByMail(@Param("mail")String mail);

            
//    @Query("select c from Clientec where "
//            + "c.nombre LIKE :query")
//    public Cliente findByQuery(@Param("query") String query, @Param("queryNombre") String queryNombre);
    
}
