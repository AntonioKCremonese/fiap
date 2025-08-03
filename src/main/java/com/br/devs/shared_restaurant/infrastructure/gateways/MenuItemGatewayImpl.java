package com.br.devs.shared_restaurant.infrastructure.gateways;

import com.br.devs.shared_restaurant.application.mapper.GenericMapper;
import com.br.devs.shared_restaurant.core.entities.MenuItem;
import com.br.devs.shared_restaurant.core.gateways.IMenuItemGateway;
import com.br.devs.shared_restaurant.infrastructure.model.MenuItemEntity;
import com.br.devs.shared_restaurant.infrastructure.repository.MenuItemRepository;
import com.br.devs.shared_restaurant.core.exceptions.MenuItemValidationException;
import org.springframework.stereotype.Repository;

@Repository
public class MenuItemGatewayImpl implements IMenuItemGateway {

    private final MenuItemRepository menuItemRepository;
    private final GenericMapper<MenuItemEntity, MenuItem, MenuItem> mapper;

    MenuItemGatewayImpl(MenuItemRepository menuItemRepository, GenericMapper<MenuItemEntity, MenuItem, MenuItem> mapper) {
        this.menuItemRepository = menuItemRepository;
        this.mapper = mapper;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        MenuItemEntity menuItemToSave = mapper.toEntity(menuItem, MenuItemEntity.class);
        return mapper.fromEntity(menuItemRepository.save(menuItemToSave), MenuItem.class);
    }

    @Override
    public MenuItem findMenuItemById(String id) {
        return mapper.fromEntity(menuItemRepository.findById(id).orElseThrow(MenuItemValidationException::menuItemNotFoundException), MenuItem.class);
    }

    @Override
    public void deleteMenuItemById(String id) {
        menuItemRepository.deleteById(id);
    }

    @Override
    public void validateMenuItemAlreadyExistsOnRestaurant(String name, String restaurantId) {
        menuItemRepository.findByNameAndRestaurant_Id(name, restaurantId).ifPresent(menuItem -> {throw MenuItemValidationException.menuItemAlreadyExistsForRestaurant();});
    }
}
