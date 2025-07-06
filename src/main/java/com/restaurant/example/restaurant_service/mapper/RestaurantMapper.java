package com.restaurant.example.restaurant_service.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

import com.restaurant.example.restaurant_service.dto.RestaurantDTO;
import com.restaurant.example.restaurant_service.entity.Restaurant;

@Mapper
public interface RestaurantMapper {

	RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);
	
	
	Restaurant mapRestaurantDTOToRestaurant(RestaurantDTO restaurantDTO);
	
	
	RestaurantDTO mapRestaurantToRestaurantDTO(Restaurant restaurant);
}
