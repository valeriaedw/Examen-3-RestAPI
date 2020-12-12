package com.cenfotec.tisa.controllers;


import com.cenfotec.tisa.model.Orden;
import com.cenfotec.tisa.model.Producto;
import com.cenfotec.tisa.repository.OrdenRepository;
import com.cenfotec.tisa.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/ordenes"})
public class OrdenController {

    private OrdenRepository repository;

    @Autowired
    ProductoRepository productoRepository;

    OrdenController(OrdenRepository ordenRepository){

        this.repository = ordenRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Orden> findById(@PathVariable long id){

        return repository.findById(id)
                .map(record->
                        ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public Orden create(@RequestBody Orden orden){

        Orden ordenPrecio = calcTotal(orden);
        ordenPrecio.setEstado(true);
        return repository.save(ordenPrecio);
    }

    //metodo que calcula el total de la orden
    private Orden calcTotal(Orden orden){

        Optional<Producto> list = productoRepository.findById(orden.getTipo().getId());

        int costoTotal = list.get().getPrecio()* orden.getCant();
        orden.setTotal(costoTotal);
        return orden;
    }

    @GetMapping("/lista_items")
    public List listarItem(@RequestBody String item) {

        return repository.findByItem(item);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Orden>
    update(@PathVariable("id") long id, @RequestBody Orden orden){
        return repository.findById(id)
                .map(record->{

                    if(orden.getTipo() == null){
                        record.setCant(orden.getCant());


                    }else if(orden.getCant() == 0){
                        record.setTipo(orden.getTipo());

                    }else{
                        record.setTipo(orden.getTipo());
                        record.setCant(orden.getCant());
                    }
                    calcTotal(record);

                    Orden updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping(path={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        return repository.findById(id)
                .map(record->{
                    record.setEstado(false);
                    Orden updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
