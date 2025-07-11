package com.restaurant.example.restaurant_service.repo;

import com.restaurant.example.restaurant_service.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RestaurantRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Test
    void testSaveRestaurant() {
        // Create a restaurant
        Restaurant restaurant = new Restaurant(0, "Test Restaurant", "Test Address", "Test City", "Test Description");

        // Save the restaurant
        Restaurant savedRestaurant = restaurantRepo.save(restaurant);

        // Verify the restaurant was saved
        assertNotNull(savedRestaurant);
        assertTrue(savedRestaurant.getId() > 0);
        assertEquals("Test Restaurant", savedRestaurant.getName());
        assertEquals("Test Address", savedRestaurant.getAddress());
        assertEquals("Test City", savedRestaurant.getCity());
        assertEquals("Test Description", savedRestaurant.getRestaurantDescription());
    }

    @Test
    void testFindAllRestaurants() {
        // Create and save multiple restaurants
        Restaurant restaurant1 = new Restaurant(0, "Restaurant 1", "Address 1", "City 1", "Description 1");
        Restaurant restaurant2 = new Restaurant(0, "Restaurant 2", "Address 2", "City 2", "Description 2");

        entityManager.persist(restaurant1);
        entityManager.persist(restaurant2);
        entityManager.flush();

        // Find all restaurants
        List<Restaurant> restaurants = restaurantRepo.findAll();

        // Verify the results
        assertNotNull(restaurants);
        assertTrue(restaurants.size() >= 2);
        
        // Verify that our restaurants are in the list
        boolean foundRestaurant1 = restaurants.stream()
                .anyMatch(r -> r.getName().equals("Restaurant 1"));
        boolean foundRestaurant2 = restaurants.stream()
                .anyMatch(r -> r.getName().equals("Restaurant 2"));
        
        assertTrue(foundRestaurant1);
        assertTrue(foundRestaurant2);
    }

    @Test
    void testFindById_ExistingRestaurant() {
        // Create and save a restaurant
        Restaurant restaurant = new Restaurant(0, "Test Restaurant", "Test Address", "Test City", "Test Description");
        Restaurant savedRestaurant = entityManager.persist(restaurant);
        entityManager.flush();

        // Find the restaurant by ID
        Optional<Restaurant> foundRestaurant = restaurantRepo.findById(savedRestaurant.getId());

        // Verify the result
        assertTrue(foundRestaurant.isPresent());
        assertEquals(savedRestaurant.getId(), foundRestaurant.get().getId());
        assertEquals("Test Restaurant", foundRestaurant.get().getName());
    }

    @Test
    void testFindById_NonExistingRestaurant() {
        // Try to find a restaurant with a non-existing ID
        Optional<Restaurant> foundRestaurant = restaurantRepo.findById(999);

        // Verify the result
        assertFalse(foundRestaurant.isPresent());
    }

    @Test
    void testUpdateRestaurant() {
        // Create and save a restaurant
        Restaurant restaurant = new Restaurant(0, "Original Name", "Original Address", "Original City", "Original Description");
        Restaurant savedRestaurant = entityManager.persist(restaurant);
        entityManager.flush();

        // Update the restaurant
        savedRestaurant.setName("Updated Name");
        savedRestaurant.setAddress("Updated Address");
        Restaurant updatedRestaurant = restaurantRepo.save(savedRestaurant);

        // Verify the update
        assertEquals("Updated Name", updatedRestaurant.getName());
        assertEquals("Updated Address", updatedRestaurant.getAddress());
        assertEquals(savedRestaurant.getId(), updatedRestaurant.getId());
    }

    @Test
    void testDeleteRestaurant() {
        // Create and save a restaurant
        Restaurant restaurant = new Restaurant(0, "Test Restaurant", "Test Address", "Test City", "Test Description");
        Restaurant savedRestaurant = entityManager.persist(restaurant);
        entityManager.flush();

        // Delete the restaurant
        restaurantRepo.delete(savedRestaurant);

        // Verify the restaurant was deleted
        Optional<Restaurant> foundRestaurant = restaurantRepo.findById(savedRestaurant.getId());
        assertFalse(foundRestaurant.isPresent());
    }
} 