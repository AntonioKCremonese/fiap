package com.br.devs.shared_restaurant.core.entities;

import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean availableForDineInOnly;
    private String photoPath;
    private Restaurant restaurant;

    public static void update(MenuItem existingMenuItem, MenuItemInputDTO menuItemInputDTO) {
        existingMenuItem.setName(menuItemInputDTO.getName());
        existingMenuItem.setDescription(menuItemInputDTO.getDescription());
        existingMenuItem.setPrice(menuItemInputDTO.getPrice());
        existingMenuItem.setAvailableForDineInOnly(menuItemInputDTO.isAvailableForDineInOnly());
        existingMenuItem.setPhotoPath(menuItemInputDTO.getPhotoPath());
    }
}
