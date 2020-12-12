package com.cenfotec.tisa.controllers;

import com.cenfotec.tisa.repository.ProductoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/productos"})
public class ProductoController {

    private ProductoRepository repository;

    ProductoController (ProductoRepository productoRepository){
        this.repository = productoRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }
}
