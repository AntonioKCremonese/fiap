package com.br.devs.shared_restaurant.core.entities;

import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
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
