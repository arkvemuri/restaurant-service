package com.restaurant.example.restaurant_service.service;

import com.restaurant.example.restaurant_service.dto.RestaurantDTO;
import com.restaurant.example.restaurant_service.entity.Restaurant;
import com.restaurant.example.restaurant_service.mapper.RestaurantMapper;
import com.restaurant.example.restaurant_service.repo.RestaurantRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepo restaurantRepo;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        reset(restaurantRepo, restaurantMapper);
    }

    @Test
    public void testFindAllRestaurants() {
        // Create mock restaurants
        List<Restaurant> mockRestaurants = Arrays.asList(
                new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
                new Restaurant(2, "Restaurant 2", "Address 2", "city 2", "Desc 2")
        );
        when(restaurantRepo.findAll()).thenReturn(mockRestaurants);

        // Call the service method
        List<RestaurantDTO> restaurantDTOList = restaurantService.findAllRestaurants();

        // Verify the result
        assertNotNull(restaurantDTOList);
        assertEquals(mockRestaurants.size(), restaurantDTOList.size());
        
        // Verify that the repository method was called
        verify(restaurantRepo, times(1)).findAll();
    }

    @Test
    public void testFindAllRestaurants_EmptyList() {
        // Mock empty list
        when(restaurantRepo.findAll()).thenReturn(Arrays.asList());

        // Call the service method
        List<RestaurantDTO> restaurantDTOList = restaurantService.findAllRestaurants();

        // Verify the result
        assertNotNull(restaurantDTOList);
        assertEquals(0, restaurantDTOList.size());
        
        // Verify that the repository method was called
        verify(restaurantRepo, times(1)).findAll();
    }

    @Test
    public void testAddRestaurantInDB() {
        // Create a mock restaurant to be saved
        RestaurantDTO mockRestaurantDTO = new RestaurantDTO(0, "Restaurant 1", "Address 1", "city 1", "Desc 1");
        Restaurant mockRestaurant = new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

        // Mock the repository behavior
        when(restaurantRepo.save(any(Restaurant.class))).thenReturn(mockRestaurant);

        // Call the service method
        RestaurantDTO savedRestaurantDTO = restaurantService.addRestaurantInDB(mockRestaurantDTO);

        // Verify the result
        assertNotNull(savedRestaurantDTO);
        assertEquals(mockRestaurant.getName(), savedRestaurantDTO.getName());
        assertEquals(mockRestaurant.getAddress(), savedRestaurantDTO.getAddress());
        assertEquals(mockRestaurant.getCity(), savedRestaurantDTO.getCity());
        assertEquals(mockRestaurant.getRestaurantDescription(), savedRestaurantDTO.getRestaurantDescription());

        // Verify that the repository method was called
        verify(restaurantRepo, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testFetchRestaurantById_ExistingId() {
        // Create a mock restaurant ID
        Integer mockRestaurantId = 1;

        // Create a mock restaurant to be returned by the repository
        Restaurant mockRestaurant = new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

        // Mock the repository behavior
        when(restaurantRepo.findById(mockRestaurantId)).thenReturn(Optional.of(mockRestaurant));

        // Call the service method
        ResponseEntity<RestaurantDTO> response = restaurantService.fetchRestaurantById(mockRestaurantId);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockRestaurantId, response.getBody().getId());

        // Verify that the repository method was called
        verify(restaurantRepo, times(1)).findById(mockRestaurantId);
    }

    @Test
    public void testFetchRestaurantById_NonExistingId() {
        // Create a mock non-existing restaurant ID
        Integer mockRestaurantId = 999;

        // Mock the repository behavior
        when(restaurantRepo.findById(mockRestaurantId)).thenReturn(Optional.empty());

        // Call the service method
        ResponseEntity<RestaurantDTO> response = restaurantService.fetchRestaurantById(mockRestaurantId);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // Verify that the repository method was called
        verify(restaurantRepo, times(1)).findById(mockRestaurantId);
    }

    @Test
    public void testFetchRestaurantById_NullId() {
        // Call the service method with null ID
        ResponseEntity<RestaurantDTO> response = restaurantService.fetchRestaurantById(null);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // Verify that the repository method was not called since we handle null before calling it
        verify(restaurantRepo, never()).findById(any());
    }
}