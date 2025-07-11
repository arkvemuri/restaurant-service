package com.restaurant.example.restaurant_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse ;
import org.springframework.web.bind.annotation.CrossOrigin ;
import org.springframework.web.bind.annotation.ExceptionHandler ;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus ;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.example.restaurant_service.dto.RestaurantDTO;
import com.restaurant.example.restaurant_service.exceptions.RestaurantNotFoundException ;
import com.restaurant.example.restaurant_service.service.RestaurantService;

import jakarta.validation.Valid ;

@RestController
@RequestMapping("/api/v1/restaurant")
@CrossOrigin
public class RestaurantController {

	@Autowired
	RestaurantService restaurantService;
	
	@GetMapping("/fetchAllRestaurants")
	public ResponseEntity<List<RestaurantDTO>> fetchAllRestaurants() {
		List<RestaurantDTO> allRestaurants = restaurantService.findAllRestaurants();
		
		
		return new ResponseEntity<List<RestaurantDTO>>(allRestaurants,HttpStatus.OK);
	}
	
	@PostMapping("/addRestaurant")
	public ResponseEntity<RestaurantDTO> saveRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO)
	{
		RestaurantDTO restaurantAdded = restaurantService.addRestaurantInDB(restaurantDTO);
		
		return new ResponseEntity<>(restaurantAdded,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/fetchById/{id}")
	public ResponseEntity<RestaurantDTO> findRestaurantById(@PathVariable Integer id)
	{
		return restaurantService.fetchRestaurantById(id);
	}
	
}
