package com.br.devs.shared_restaurant.old_controller;

import com.br.devs.shared_restaurant.core.dto.input.RestaurantInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.RestaurantOutputDTO;
import com.br.devs.shared_restaurant.old_service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantOutputDTO create(@RequestBody @Valid RestaurantInputDTO input) {
        return restaurantService.create(input);
    }

    @PutMapping("/{id}")
    public RestaurantOutputDTO update(@PathVariable String id, @RequestBody @Valid RestaurantInputDTO input) {
        return restaurantService.update(id, input);
    }

    @GetMapping("/{id}")
    public RestaurantOutputDTO getById(@PathVariable String id) {
        return restaurantService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        restaurantService.delete(id);
    }
}