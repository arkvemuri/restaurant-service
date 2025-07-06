package com.restaurant.example.restaurant_service.mapper;

import com.restaurant.example.restaurant_service.dto.RestaurantDTO;
import com.restaurant.example.restaurant_service.entity.Restaurant;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T23:05:00+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant mapRestaurantDTOToRestaurant(RestaurantDTO restaurantDTO) {
        if ( restaurantDTO == null ) {
            return null;
        }

        Restaurant restaurant = new Restaurant();

        restaurant.setAddress( restaurantDTO.getAddress() );
        restaurant.setCity( restaurantDTO.getCity() );
        restaurant.setId( restaurantDTO.getId() );
        restaurant.setName( restaurantDTO.getName() );
        restaurant.setRestaurantDescription( restaurantDTO.getRestaurantDescription() );

        return restaurant;
    }

    @Override
    public RestaurantDTO mapRestaurantToRestaurantDTO(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantDTO restaurantDTO = new RestaurantDTO();

        restaurantDTO.setAddress( restaurant.getAddress() );
        restaurantDTO.setCity( restaurant.getCity() );
        restaurantDTO.setId( restaurant.getId() );
        restaurantDTO.setName( restaurant.getName() );
        restaurantDTO.setRestaurantDescription( restaurant.getRestaurantDescription() );

        return restaurantDTO;
    }
}
