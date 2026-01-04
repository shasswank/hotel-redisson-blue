 package com.example.hotelbackend.response;

 import java.util.List;

 import lombok.Data;


 @Data

//  @NoArgsConstructor
 public class JwtResponse {
     private String id;
     private String email;
     private String token;
     private String type = "Bearer";
     private List<String> roles;

     public JwtResponse(String id, String email, String token, List<String> roles) {
         this.id = id;
         this.email = email;
         this.token = token;
         this.roles = roles;
     }
 }
