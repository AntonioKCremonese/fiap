package com.br.devs.shared_restaurant.application.usecases;

import com.br.devs.shared_restaurant.core.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.core.dto.output.MenuItemOutputDTO;
import com.br.devs.shared_restaurant.core.entities.MenuItem;
import com.br.devs.shared_restaurant.core.gateways.IMenuItemGateway;
import com.br.devs.shared_restaurant.core.gateways.IRestaurantGateway;
import com.br.devs.shared_restaurant.core.interfaces.IMenuItemUseCase;
import com.br.devs.shared_restaurant.core.presenters.MenuItemPresenter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuItemUseCaseImpl implements IMenuItemUseCase {

    private final IMenuItemGateway menuItemGateway;
    private final IRestaurantGateway restaurantGateway;

    MenuItemUseCaseImpl(IMenuItemGateway menuItemGateway, IRestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    @Transactional
    public MenuItemOutputDTO create(MenuItemInputDTO menuItemInputDTO) {
        MenuItem menuItem = MenuItemPresenter.toEntity(menuItemInputDTO);
        menuItem.setRestaurant(restaurantGateway.findRestaurantById(menuItemInputDTO.getRestaurant().getId()));
        menuItemGateway.validateMenuItemAlreadyExistsOnRestaurant(menuItem.getName(), menuItem.getRestaurant().getId());
        return MenuItemPresenter.toDTO(menuItemGateway.save(menuItem));
    }

    @Override
    @Transactional
    public MenuItemOutputDTO getById(String id) {
        MenuItem menuItem = findById(id);
        return MenuItemPresenter.toDTO(menuItem);
    }

    @Override
    @Transactional
    public MenuItemOutputDTO update(String id, MenuItemInputDTO menuItemInputDTO) {
        MenuItem existingMenuItem = findById(id);
        MenuItem.update(existingMenuItem, menuItemInputDTO);
        existingMenuItem.setRestaurant(restaurantGateway.findRestaurantById(menuItemInputDTO.getRestaurant().getId()));
        return MenuItemPresenter.toDTO(menuItemGateway.save(existingMenuItem));
    }

    @Override
    @Transactional
    public void delete(String id) {
        menuItemGateway.deleteMenuItemById(findById(id).getId());
    }

    private MenuItem findById(String id) {
        return this.menuItemGateway.findMenuItemById(id);
    }
}