package com.br.devs.shared_restaurant.application.controller;

import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.MenuItemOutputDTO;
import com.br.devs.shared_restaurant.core.interfaces.IMenuItemUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/menu-items")
public class MenuItemController {

    private final IMenuItemUseCase menuItemUseCase;

    public MenuItemController(IMenuItemUseCase menuItemUseCase) {
        this.menuItemUseCase = menuItemUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemOutputDTO create(@RequestBody @Valid MenuItemInputDTO input) {
        return menuItemUseCase.create(input);
    }

    @PutMapping("/{id}")
    public MenuItemOutputDTO update(@PathVariable String id, @RequestBody @Valid MenuItemInputDTO input) {
        return menuItemUseCase.update(id, input);
    }

    @GetMapping("/{id}")
    public MenuItemOutputDTO getById(@PathVariable String id) {
        return menuItemUseCase.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        menuItemUseCase.delete(id);
    }
}