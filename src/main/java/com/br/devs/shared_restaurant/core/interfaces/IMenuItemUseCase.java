package com.br.devs.shared_restaurant.core.interfaces;

import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.MenuItemOutputDTO;

public interface IMenuItemUseCase {
    MenuItemOutputDTO create(MenuItemInputDTO menuItemInputDTO);
    MenuItemOutputDTO getById(String id);
    MenuItemOutputDTO update(String id, MenuItemInputDTO menuItemInputDTO);
    void delete(String id);
}