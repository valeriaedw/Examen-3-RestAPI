package com.cenfotec.tisa.repository;

import com.cenfotec.tisa.model.Cliente;
import com.cenfotec.tisa.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Query(value = "SELECT * FROM ORDEN \n" +
            "INNER JOIN PRODUCTO \n" +
            "ON \n" +
            "ORDEN.PRODUCTO_ORDEN = PRODUCTO.ID\n" +
            "WHERE PRODUCTO.TIPO=  :orden",nativeQuery = true)
    List<Orden> findByItem(@Param("orden") String orden);





}
