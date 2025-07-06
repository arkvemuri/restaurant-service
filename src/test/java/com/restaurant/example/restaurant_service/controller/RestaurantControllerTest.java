package com.restaurant.example.restaurant_service.controller;

import com.restaurant.example.restaurant_service.dto.RestaurantDTO;
import com.restaurant.example.restaurant_service.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RestaurantService restaurantService;

    @Test
    void testFetchAllRestaurants() throws Exception {
        // Mock data
        List<RestaurantDTO> mockRestaurants = Arrays.asList(
                new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
                new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2")
        );

        // Mock service
        when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);

        // Perform test
        mockMvc.perform(get("/restaurant/fetchAllRestaurants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockRestaurants)));

        verify(restaurantService, times(1)).findAllRestaurants();
    }

    @Test
    void testFetchAllRestaurants_EmptyList() throws Exception {
        // Mock empty list
        List<RestaurantDTO> mockRestaurants = Arrays.asList();

        // Mock service
        when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);

        // Perform test
        mockMvc.perform(get("/restaurant/fetchAllRestaurants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(restaurantService, times(1)).findAllRestaurants();
    }

    @Test
    void testSaveRestaurant() throws Exception {
        RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

        when(restaurantService.addRestaurantInDB(any(RestaurantDTO.class))).thenReturn(mockRestaurant);

        mockMvc.perform(post("/restaurant/addRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRestaurant)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(mockRestaurant)));

        verify(restaurantService, times(1)).addRestaurantInDB(any(RestaurantDTO.class));
    }

    @Test
    void testSaveRestaurant_InvalidData() throws Exception {
        // Test with invalid restaurant data (missing required fields)
        String invalidRestaurantJson = "{\"name\":\"\",\"address\":\"\"}";

        mockMvc.perform(post("/restaurant/addRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRestaurantJson))
                .andExpect(status().isBadRequest());

        verify(restaurantService, never()).addRestaurantInDB(any(RestaurantDTO.class));
    }

    @Test
    void testFindRestaurantById() throws Exception {
        Integer mockRestaurantId = 1;
        RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
        ResponseEntity<RestaurantDTO> mockResponse = new ResponseEntity<>(mockRestaurant, HttpStatus.OK);

        when(restaurantService.fetchRestaurantById(mockRestaurantId)).thenReturn(mockResponse);

        mockMvc.perform(get("/restaurant/fetchById/{id}", mockRestaurantId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockRestaurant)));

        verify(restaurantService, times(1)).fetchRestaurantById(mockRestaurantId);
    }

    @Test
    void testFindRestaurantById_NotFound() throws Exception {
        Integer mockRestaurantId = 999;
        ResponseEntity<RestaurantDTO> mockResponse = ResponseEntity.notFound().build();

        when(restaurantService.fetchRestaurantById(mockRestaurantId)).thenReturn(mockResponse);

        mockMvc.perform(get("/restaurant/fetchById/{id}", mockRestaurantId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).fetchRestaurantById(mockRestaurantId);
    }

    @Test
    void testFindRestaurantById_InvalidId() throws Exception {
        // Test with invalid ID format
        mockMvc.perform(get("/restaurant/fetchById/{id}", "invalid")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(restaurantService, never()).fetchRestaurantById(any());
    }
}