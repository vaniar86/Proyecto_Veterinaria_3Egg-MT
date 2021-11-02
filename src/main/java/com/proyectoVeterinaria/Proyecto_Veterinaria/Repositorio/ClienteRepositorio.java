
package com.proyectoVeterinaria.Proyecto_Veterinaria.Repositorio;

import com.proyectoVeterinaria.Proyecto_Veterinaria.Entidades.Cliente;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
    
    @Query()
    public void registrar(@Param("id") String id);
    
    @Query()
    public void modificar(@Param("id") String id);
    
    @Query()
    public void eliminar(@Param("id") String id);
    
    @Query()
    public Cliente buscarClientePorId(@Param("id") String id);
    
    @Query()
    public ArrayList <Cliente> listarClientes();
    
}
