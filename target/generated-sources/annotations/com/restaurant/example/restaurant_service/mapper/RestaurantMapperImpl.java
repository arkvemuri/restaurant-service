package com.restaurant.example.restaurant_service.mapper;

import com.restaurant.example.restaurant_service.dto.RestaurantDTO;
import com.restaurant.example.restaurant_service.entity.Restaurant;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-27T14:57:02+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Eclipse Adoptium)"
)
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant mapRestaurantDTOToRestaurant(RestaurantDTO restaurantDTO) {
        if ( restaurantDTO == null ) {
            return null;
        }

        Restaurant restaurant = new Restaurant();

        restaurant.setId( restaurantDTO.getId() );
        restaurant.setName( restaurantDTO.getName() );
        restaurant.setAddress( restaurantDTO.getAddress() );
        restaurant.setCity( restaurantDTO.getCity() );
        restaurant.setRestaurantDescription( restaurantDTO.getRestaurantDescription() );

        return restaurant;
    }

    @Override
    public RestaurantDTO mapRestaurantToRestaurantDTO(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantDTO restaurantDTO = new RestaurantDTO();

        restaurantDTO.setId( restaurant.getId() );
        restaurantDTO.setName( restaurant.getName() );
        restaurantDTO.setAddress( restaurant.getAddress() );
        restaurantDTO.setCity( restaurant.getCity() );
        restaurantDTO.setRestaurantDescription( restaurant.getRestaurantDescription() );

        return restaurantDTO;
    }
}
