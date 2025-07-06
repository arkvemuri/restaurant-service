package com.restaurant.example.restaurant_service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.example.restaurant_service.dto.RestaurantDTO;
import com.restaurant.example.restaurant_service.entity.Restaurant;
import com.restaurant.example.restaurant_service.repo.RestaurantRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RestaurantIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        restaurantRepo.deleteAll();
    }

    @Test
    public void testCreateAndFetchRestaurant() throws Exception {
        // Create restaurant data
        RestaurantDTO restaurantDTO = new RestaurantDTO(0, "Integration Test Restaurant", "Test Address", "Test City", "Test Description");

        // Save restaurant via API
        String response = mockMvc.perform(post("/restaurant/addRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Parse the response to get the saved restaurant
        RestaurantDTO savedRestaurant = objectMapper.readValue(response, RestaurantDTO.class);
        assertNotNull(savedRestaurant);
        assertTrue(savedRestaurant.getId() > 0);
        assertEquals("Integration Test Restaurant", savedRestaurant.getName());

        // Fetch the restaurant by ID via API
        mockMvc.perform(get("/restaurant/fetchById/{id}", savedRestaurant.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedRestaurant.getId()))
                .andExpect(jsonPath("$.name").value("Integration Test Restaurant"))
                .andExpect(jsonPath("$.address").value("Test Address"))
                .andExpect(jsonPath("$.city").value("Test City"))
                .andExpect(jsonPath("$.restaurantDescription").value("Test Description"));

        // Verify the restaurant was saved in the database
        assertTrue(restaurantRepo.findById(savedRestaurant.getId()).isPresent());
    }

    @Test
    public void testFetchAllRestaurants() throws Exception {
        // Create and save restaurants directly to database
        Restaurant restaurant1 = new Restaurant(0, "Restaurant 1", "Address 1", "City 1", "Description 1");
        Restaurant restaurant2 = new Restaurant(0, "Restaurant 2", "Address 2", "City 2", "Description 2");

        restaurantRepo.save(restaurant1);
        restaurantRepo.save(restaurant2);

        // Fetch all restaurants via API
        mockMvc.perform(get("/restaurant/fetchAllRestaurants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Restaurant 1"))
                .andExpect(jsonPath("$[1].name").value("Restaurant 2"));
    }

    @Test
    public void testFetchRestaurantById_NotFound() throws Exception {
        // Try to fetch a non-existing restaurant
        mockMvc.perform(get("/restaurant/fetchById/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateRestaurantWithInvalidData() throws Exception {
        // Try to create restaurant with invalid data
        String invalidJson = "{\"name\":\"\",\"address\":\"\"}";

        mockMvc.perform(post("/restaurant/addRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateMultipleRestaurants() throws Exception {
        // Create multiple restaurants
        RestaurantDTO restaurant1 = new RestaurantDTO(0, "Restaurant A", "Address A", "City A", "Description A");
        RestaurantDTO restaurant2 = new RestaurantDTO(0, "Restaurant B", "Address B", "City B", "Description B");

        // Save first restaurant
        String response1 = mockMvc.perform(post("/restaurant/addRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant1)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestaurantDTO savedRestaurant1 = objectMapper.readValue(response1, RestaurantDTO.class);

        // Save second restaurant
        String response2 = mockMvc.perform(post("/restaurant/addRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant2)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestaurantDTO savedRestaurant2 = objectMapper.readValue(response2, RestaurantDTO.class);

        // Verify both restaurants have different IDs
        assertNotEquals(savedRestaurant1.getId(), savedRestaurant2.getId());

        // Verify both restaurants exist in database
        assertTrue(restaurantRepo.findById(savedRestaurant1.getId()).isPresent());
        assertTrue(restaurantRepo.findById(savedRestaurant2.getId()).isPresent());
    }

    @Test
    public void testRestaurantDataPersistence() throws Exception {
        // Create a restaurant with special characters
        RestaurantDTO restaurantDTO = new RestaurantDTO(0, "Restaurant & Café", "123 Main St, Apt #4", "New York, NY", "Best food in town! 🍕");

        // Save restaurant
        String response = mockMvc.perform(post("/restaurant/addRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestaurantDTO savedRestaurant = objectMapper.readValue(response, RestaurantDTO.class);

        // Verify the data was persisted correctly
        mockMvc.perform(get("/restaurant/fetchById/{id}", savedRestaurant.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Restaurant & Café"))
                .andExpect(jsonPath("$.address").value("123 Main St, Apt #4"))
                .andExpect(jsonPath("$.city").value("New York, NY"))
                .andExpect(jsonPath("$.restaurantDescription").value("Best food in town! 🍕"));
    }
} 