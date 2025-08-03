package com.br.devs.shared_restaurant.core.presenters;

import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.MenuItemOutputDTO;
import com.br.devs.shared_restaurant.core.entities.MenuItem;
import com.br.devs.shared_restaurant.core.entities.Restaurant;

public class MenuItemPresenter {

    public static MenuItem toEntity(MenuItemInputDTO input) {
        return new MenuItem(null,
                input.getName(),
                input.getDescription(),
                input.getPrice(),
                input.isAvailableForDineInOnly(),
                input.getPhotoPath(),
                new Restaurant()
        );
    }

    public static MenuItemOutputDTO toDTO(MenuItem menuItem) {
        return new MenuItemOutputDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isAvailableForDineInOnly(),
                menuItem.getPhotoPath(),
                RestaurantPresenter.toDTO(menuItem.getRestaurant())
        );
    }
}
