package org.test.school.utils.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIException extends Exception{

     private String message;
     private int statusCode;

     public APIException(String message,int statusCode){
          this.message=message;
          this.statusCode=statusCode;
     }
}
