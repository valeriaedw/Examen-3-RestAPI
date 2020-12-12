package com.cenfotec.tisa.repository;

import com.cenfotec.tisa.model.Cliente;
import com.cenfotec.tisa.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByApellidosLike(String key);
    List<Cliente> findByDireccionCobroLike(String key);


    @Query(value = "SELECT * FROM CLIENTE INNER JOIN ORDEN ON CLIENTE.ID = ORDEN.CLIENTE_ORDEN WHERE CLIENTE.ID = :id AND ORDEN.ESTADO = FALSE", nativeQuery = true)
    Cliente buscaClienteOrden(@Param("id") long id);

}
