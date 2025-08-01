package com.br.devs.shared_restaurant.application.controller;

import com.br.devs.shared_restaurant.core.dto.input.CuisineTypeInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.CuisineTypeOutputDTO;
import com.br.devs.shared_restaurant.core.interfaces.ICuisineTypeUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cuisine-types")
public class CuisineTypeController {

    private final ICuisineTypeUseCase cuisineTypeUseCase;

    public CuisineTypeController(ICuisineTypeUseCase cuisineTypeUseCase) {
        this.cuisineTypeUseCase = cuisineTypeUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuisineTypeOutputDTO create(@RequestBody @Valid CuisineTypeInputDTO input) {
        return cuisineTypeUseCase.create(input);
    }

    @PutMapping("/{id}")
    public CuisineTypeOutputDTO update(@PathVariable String id, @RequestBody @Valid CuisineTypeInputDTO input) {
        return cuisineTypeUseCase.update(id, input);
    }

    @GetMapping("/{id}")
    public CuisineTypeOutputDTO getById(@PathVariable String id) {
        return cuisineTypeUseCase.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        cuisineTypeUseCase.delete(id);
    }
}