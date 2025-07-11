package com.restaurant.example.restaurant_service.dto;

import jakarta.validation.Valid ;
import jakarta.validation.constraints.NotBlank ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RestaurantDTO {

	private int id;
	@Valid
	@NotBlank(message="Name is required")
	private String name;
	private String address;
	private String city;
	private String restaurantDescription;
}
