package com.restaurant.example.restaurant_service.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantNotFoundExceptionTest {

    @Test
    void testDefaultConstructor() {
        RestaurantNotFoundException exception = new RestaurantNotFoundException();
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testConstructorWithMessage() {
        String message = "Restaurant not found";
        RestaurantNotFoundException exception = new RestaurantNotFoundException(message);
        
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testGetMessage() {
        String message = "Test message";
        RestaurantNotFoundException exception = new RestaurantNotFoundException(message);
        
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testSetMessage() {
        RestaurantNotFoundException exception = new RestaurantNotFoundException();
        String message = "New message";
        
        exception.setMessage(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testSetMessageOverride() {
        String originalMessage = "Original message";
        String newMessage = "New message";
        
        RestaurantNotFoundException exception = new RestaurantNotFoundException(originalMessage);
        exception.setMessage(newMessage);
        
        assertEquals(newMessage, exception.getMessage());
    }

    @Test
    void testExceptionInheritance() {
        RestaurantNotFoundException exception = new RestaurantNotFoundException("Test");
        assertTrue(exception instanceof RuntimeException);
    }
}