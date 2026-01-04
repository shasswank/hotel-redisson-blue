package com.example.hotelbackend.model;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection="USER")
@Data
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//     @ManyToMany(fetch = FetchType.EAGER,
//             cascade = {CascadeType.PERSIST,
//                     CascadeType.MERGE, CascadeType.DETACH})
//     @JoinTable(name = "user_roles",
//             joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//     inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
//  @DocumentReference
    private Collection<Role> roles = new HashSet<>();



}
