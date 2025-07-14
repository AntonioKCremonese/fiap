package com.br.devs.shared_restaurant.controller;

import com.br.devs.shared_restaurant.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.dto.output.MenuItemOutputDTO;
import com.br.devs.shared_restaurant.service.MenuItemService;
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
@RequestMapping(path = "/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemOutputDTO create(@RequestBody @Valid MenuItemInputDTO input) {
        return menuItemService.create(input);
    }

    @PutMapping("/{id}")
    public MenuItemOutputDTO update(@PathVariable String id, @RequestBody @Valid MenuItemInputDTO input) {
        return menuItemService.update(id, input);
    }

    @GetMapping("/{id}")
    public MenuItemOutputDTO getById(@PathVariable String id) {
        return menuItemService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        menuItemService.delete(id);
    }
}