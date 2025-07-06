package com.restaurant.example.restaurant_service.mapper;

import com.restaurant.example.restaurant_service.dto.RestaurantDTO;
import com.restaurant.example.restaurant_service.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantMapperTest {

    private final RestaurantMapper mapper = RestaurantMapper.INSTANCE;

    @Test
    public void testMapRestaurantToRestaurantDTO() {
        // Create a restaurant entity
        Restaurant restaurant = new Restaurant(1, "Test Restaurant", "Test Address", "Test City", "Test Description");

        // Map to DTO
        RestaurantDTO dto = mapper.mapRestaurantToRestaurantDTO(restaurant);

        // Verify the mapping
        assertNotNull(dto);
        assertEquals(restaurant.getId(), dto.getId());
        assertEquals(restaurant.getName(), dto.getName());
        assertEquals(restaurant.getAddress(), dto.getAddress());
        assertEquals(restaurant.getCity(), dto.getCity());
        assertEquals(restaurant.getRestaurantDescription(), dto.getRestaurantDescription());
    }

    @Test
    public void testMapRestaurantDTOToRestaurant() {
        // Create a restaurant DTO
        RestaurantDTO dto = new RestaurantDTO(1, "Test Restaurant", "Test Address", "Test City", "Test Description");

        // Map to entity
        Restaurant restaurant = mapper.mapRestaurantDTOToRestaurant(dto);

        // Verify the mapping
        assertNotNull(restaurant);
        assertEquals(dto.getId(), restaurant.getId());
        assertEquals(dto.getName(), restaurant.getName());
        assertEquals(dto.getAddress(), restaurant.getAddress());
        assertEquals(dto.getCity(), restaurant.getCity());
        assertEquals(dto.getRestaurantDescription(), restaurant.getRestaurantDescription());
    }

    @Test
    public void testMapRestaurantToRestaurantDTO_WithNullValues() {
        // Create a restaurant entity with null values
        Restaurant restaurant = new Restaurant(1, null, null, null, null);

        // Map to DTO
        RestaurantDTO dto = mapper.mapRestaurantToRestaurantDTO(restaurant);

        // Verify the mapping
        assertNotNull(dto);
        assertEquals(restaurant.getId(), dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getAddress());
        assertNull(dto.getCity());
        assertNull(dto.getRestaurantDescription());
    }

    @Test
    public void testMapRestaurantDTOToRestaurant_WithNullValues() {
        // Create a restaurant DTO with null values
        RestaurantDTO dto = new RestaurantDTO(1, null, null, null, null);

        // Map to entity
        Restaurant restaurant = mapper.mapRestaurantDTOToRestaurant(dto);

        // Verify the mapping
        assertNotNull(restaurant);
        assertEquals(dto.getId(), restaurant.getId());
        assertNull(restaurant.getName());
        assertNull(restaurant.getAddress());
        assertNull(restaurant.getCity());
        assertNull(restaurant.getRestaurantDescription());
    }

    @Test
    public void testMapRestaurantToRestaurantDTO_WithEmptyStrings() {
        // Create a restaurant entity with empty strings
        Restaurant restaurant = new Restaurant(1, "", "", "", "");

        // Map to DTO
        RestaurantDTO dto = mapper.mapRestaurantToRestaurantDTO(restaurant);

        // Verify the mapping
        assertNotNull(dto);
        assertEquals(restaurant.getId(), dto.getId());
        assertEquals("", dto.getName());
        assertEquals("", dto.getAddress());
        assertEquals("", dto.getCity());
        assertEquals("", dto.getRestaurantDescription());
    }

    @Test
    public void testMapRestaurantDTOToRestaurant_WithEmptyStrings() {
        // Create a restaurant DTO with empty strings
        RestaurantDTO dto = new RestaurantDTO(1, "", "", "", "");

        // Map to entity
        Restaurant restaurant = mapper.mapRestaurantDTOToRestaurant(dto);

        // Verify the mapping
        assertNotNull(restaurant);
        assertEquals(dto.getId(), restaurant.getId());
        assertEquals("", restaurant.getName());
        assertEquals("", restaurant.getAddress());
        assertEquals("", restaurant.getCity());
        assertEquals("", restaurant.getRestaurantDescription());
    }

    @Test
    public void testMapRestaurantToRestaurantDTO_WithSpecialCharacters() {
        // Create a restaurant entity with special characters
        Restaurant restaurant = new Restaurant(1, "Restaurant & Café", "123 Main St, Apt #4", "New York, NY", "Best food in town! 🍕");

        // Map to DTO
        RestaurantDTO dto = mapper.mapRestaurantToRestaurantDTO(restaurant);

        // Verify the mapping
        assertNotNull(dto);
        assertEquals(restaurant.getId(), dto.getId());
        assertEquals("Restaurant & Café", dto.getName());
        assertEquals("123 Main St, Apt #4", dto.getAddress());
        assertEquals("New York, NY", dto.getCity());
        assertEquals("Best food in town! 🍕", dto.getRestaurantDescription());
    }

    @Test
    public void testMapRestaurantDTOToRestaurant_WithSpecialCharacters() {
        // Create a restaurant DTO with special characters
        RestaurantDTO dto = new RestaurantDTO(1, "Restaurant & Café", "123 Main St, Apt #4", "New York, NY", "Best food in town! 🍕");

        // Map to entity
        Restaurant restaurant = mapper.mapRestaurantDTOToRestaurant(dto);

        // Verify the mapping
        assertNotNull(restaurant);
        assertEquals(dto.getId(), restaurant.getId());
        assertEquals("Restaurant & Café", restaurant.getName());
        assertEquals("123 Main St, Apt #4", restaurant.getAddress());
        assertEquals("New York, NY", restaurant.getCity());
        assertEquals("Best food in town! 🍕", restaurant.getRestaurantDescription());
    }
} 