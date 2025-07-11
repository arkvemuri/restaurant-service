package com.restaurant.example.restaurant_service.exceptions;

public class RestaurantNotFoundException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L ;
    
    private String message ;
    
    public RestaurantNotFoundException() {}
    
    public RestaurantNotFoundException(String msg)
    {
        super(msg);
        this.message=msg;
    }

    public String getMessage()
    {
        return message ;
    }

    public void setMessage(String message)
    {
        this.message = message ;
    }
    
    
}
