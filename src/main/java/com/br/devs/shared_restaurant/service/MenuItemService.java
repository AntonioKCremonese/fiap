package com.br.devs.shared_restaurant.service;

import com.br.devs.shared_restaurant.dto.input.MenuItemInputDTO;
import com.br.devs.shared_restaurant.dto.output.MenuItemOutputDTO;
import com.br.devs.shared_restaurant.exception.MenuItemValidationException;
import com.br.devs.shared_restaurant.mapper.GenericMapper;
import com.br.devs.shared_restaurant.model.MenuItem;
import com.br.devs.shared_restaurant.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final GenericMapper<MenuItem, MenuItemInputDTO, MenuItemOutputDTO> mapper;
    private final RestaurantService restaurantService;

    public MenuItemService(MenuItemRepository menuItemRepository,
                           GenericMapper<MenuItem, MenuItemInputDTO, MenuItemOutputDTO> mapper,
                           RestaurantService restaurantService) {
        this.menuItemRepository = menuItemRepository;
        this.mapper = mapper;
        this.restaurantService = restaurantService;
    }

    @Transactional
    public MenuItemOutputDTO create(MenuItemInputDTO menuItemInputDTO) {
        MenuItem menuItem = mapper.toEntity(menuItemInputDTO, MenuItem.class);
        menuItem.setRestaurant(restaurantService.findById(menuItemInputDTO.getRestaurant().getId()));
        menuItem = menuItemRepository.save(menuItem);
        return mapper.fromEntity(menuItem, MenuItemOutputDTO.class);
    }

    @Transactional(readOnly = true)
    public MenuItemOutputDTO getById(String id) {
        MenuItem menuItem = findById(id);
        return mapper.fromEntity(menuItem, MenuItemOutputDTO.class);
    }

    @Transactional
    public MenuItemOutputDTO update(String id, MenuItemInputDTO menuItemInputDTO) {
        MenuItem existingMenuItem = findById(id);
        mapper.updateEntity(menuItemInputDTO, existingMenuItem);
        existingMenuItem.setRestaurant(restaurantService.findById(menuItemInputDTO.getRestaurant().getId()));
        existingMenuItem = menuItemRepository.save(existingMenuItem);
        return mapper.fromEntity(existingMenuItem, MenuItemOutputDTO.class);
    }

    @Transactional
    public void delete(String id) {
        menuItemRepository.delete(findById(id));
    }

    private MenuItem findById(String id) {
        return menuItemRepository.findById(id)
                .orElseThrow(MenuItemValidationException::menuItemNotFoundException);
    }
}