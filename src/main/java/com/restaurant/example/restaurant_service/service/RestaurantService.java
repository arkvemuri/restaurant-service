package com.restaurant.example.restaurant_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurant.example.restaurant_service.dto.RestaurantDTO;
import com.restaurant.example.restaurant_service.entity.Restaurant;
import com.restaurant.example.restaurant_service.mapper.RestaurantMapper;
import com.restaurant.example.restaurant_service.repo.RestaurantRepo;

@Service
public class RestaurantService {

	private final RestaurantRepo restaurantRepo;

	public RestaurantService(RestaurantRepo restaurantRepo) {
		this.restaurantRepo = restaurantRepo;
	}

	public List<RestaurantDTO> findAllRestaurants() {
		
		List<Restaurant> restaurants = restaurantRepo.findAll();
		
		return restaurants.stream()
				.map(RestaurantMapper.INSTANCE::mapRestaurantToRestaurantDTO)
				.toList();
		
	}

	public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
		
		Restaurant savedRestaurant = restaurantRepo.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));
		
		return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(savedRestaurant);
	}

	public ResponseEntity<RestaurantDTO> fetchRestaurantById(Integer id) {
		
		if (id == null) {
			return ResponseEntity.notFound().build();
		}
		
		Optional<Restaurant> restaurant = restaurantRepo.findById(id);
		
		if(restaurant.isPresent())
		{
			return ResponseEntity.ok(RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get()));
		}
		return ResponseEntity.notFound().build();
	}
}
