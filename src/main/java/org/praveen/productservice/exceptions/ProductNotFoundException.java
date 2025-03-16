package org.praveen.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductNotFoundException extends Exception{
    private Long id;
    public ProductNotFoundException(String message,Long id){
        super(message);
        this.id = id;
    }
}
