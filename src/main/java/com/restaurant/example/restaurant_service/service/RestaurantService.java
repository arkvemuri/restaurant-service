package com.restaurant.example.restaurant_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
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
		
		List<RestaurantDTO> restaurantDTOList = restaurants.stream()
				.map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant))
				.collect(Collectors.toList());
		
		return restaurantDTOList;
		
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
