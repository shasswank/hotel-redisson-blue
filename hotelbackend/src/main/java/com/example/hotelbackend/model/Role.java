package com.example.hotelbackend.model;

// import java.util.Collection;
// import java.util.HashSet;
// import java.util.List;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// import lombok.Data;

// // import com.example.hotelbackend.User;

// @Data
// @Document(collection="ROLE")
// public class Role {
//     @Id
//     // @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private String id;
//     private String name;

//     // @JsonIgnore
//     // @ManyToMany(mappedBy = "roles")
//     // private Collection<User> users = new HashSet<>();
//     public  Collection<User> users = new HashSet<>();

//     public Role(String name) {
//         this.name = name;
//     }

//     public void assignRoleToUser(User user){
//         user.getRoles().add(this);
//         this.getUsers().add(user);
//     }

//     public void removeUserFromRole(User user){
//         user.getRoles().remove(this);
//         this.getUsers().remove(user);

//     }

//     public void removeAllUsersFromRole(){
//         if (this.getUsers() != null){
//             List<User> roleUsers = this.getUsers().stream().toList();
//             roleUsers.forEach(this :: removeUserFromRole);
//         }
//     }
//     public  String getName(){
//         return name != null? name : "";
//     }
// }
public enum Role {
    USER,
    ADMIN
}
