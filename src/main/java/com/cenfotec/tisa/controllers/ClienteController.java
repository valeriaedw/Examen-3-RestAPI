package com.cenfotec.tisa.controllers;

import com.cenfotec.tisa.model.Cliente;
import com.cenfotec.tisa.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/clientes"})
public class ClienteController {

    private ClienteRepository repository;

    ClienteController(ClienteRepository clienteRepository){
        this.repository = clienteRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Cliente> findById(@PathVariable long id){

        return repository.findById(id)
                .map(record->
                        ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente){
        return repository.save(cliente);
    }


    @GetMapping("/lista_apellidos")
    public List findByApellido(@RequestBody String apellido) {
        String keyword = "%" + apellido + "%";
        return repository.findByApellidosLike(keyword);
    }


    @GetMapping("/lista_direcciones")
    public List findByDireccionCobro(@RequestBody String dir){
        String keyword = "%" + dir + "%";
        return repository.findByDireccionCobroLike(keyword);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente>
    update(@PathVariable("id") long id, @RequestBody Cliente cliente){
        return repository.findById(id)
                .map(record->{
                    record.setNombre(cliente.getNombre());
                    record.setApellidos(cliente.getApellidos());
                    record.setDireccionVivienda(cliente.getDireccionVivienda());
                    record.setDireccionCobro(cliente.getDireccionCobro());
                    record.setNumTarjeta(cliente.getNumTarjeta());
                    record.setMes(cliente.getMes());
                    record.setAnio(cliente.getAnio());
                    Cliente updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping(path={"/{id}"})
    public ResponseEntity<Cliente> delete(@PathVariable("id") long id){

        Cliente clientes = repository.buscaClienteOrden(id);
        if(clientes != null) {
            repository.deleteById(clientes.getId());
            return ResponseEntity.ok().build();
        }else{
           return ResponseEntity.notFound().build();
        }

    }

}
