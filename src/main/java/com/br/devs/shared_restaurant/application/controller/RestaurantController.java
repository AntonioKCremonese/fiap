package com.br.devs.shared_restaurant.application.controller;

import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.RestaurantOutputDTO;
import com.br.devs.shared_restaurant.core.interfaces.IRestaurantUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantController {
    private final IRestaurantUseCase restaurantUseCase;

    public RestaurantController(IRestaurantUseCase restaurantUseCase) {
        this.restaurantUseCase = restaurantUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantOutputDTO create(@RequestBody @Valid RestaurantInputDTO input) {
        return restaurantUseCase.create(input);
    }

    @PutMapping("/{id}")
    public RestaurantOutputDTO update(@PathVariable String id, @RequestBody @Valid RestaurantInputDTO input) {
        return restaurantUseCase.update(id, input);
    }

    @GetMapping("/{id}")
    public RestaurantOutputDTO getById(@PathVariable String id) {
        return restaurantUseCase.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        restaurantUseCase.delete(id);
    }
}