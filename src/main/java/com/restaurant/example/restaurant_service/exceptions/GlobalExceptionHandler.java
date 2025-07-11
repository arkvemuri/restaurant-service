package com.restaurant.example.restaurant_service.exceptions;

import org.springframework.http.HttpStatus ;
import org.springframework.http.ProblemDetail ;
import org.springframework.web.bind.annotation.ExceptionHandler ;
import org.springframework.web.bind.annotation.ResponseStatus ;
import org.springframework.web.bind.annotation.RestControllerAdvice ;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    
    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleRestaurantNotFoundException(RestaurantNotFoundException ex)
    {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
        pd.setTitle("Restaurant is not found");
        pd.setDetail("No Restaurant Detail was found with the specified Restaurant ID");
        
        return pd;
    }
    
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex)
    {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        pd.setTitle("Unexpected Error");
        pd.setDetail("System has encountered an unexpected error");
        
        return pd;
    }
}
