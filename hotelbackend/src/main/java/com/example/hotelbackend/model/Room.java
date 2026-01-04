// package com.example.hotelbackend.model;



// // import org.apache.commons.lang3.RandomStringUtils;
// import java.math.BigDecimal;
// import java.sql.Blob;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// import lombok.Data;

// // import com.example.hotelbackend.model.BookedRoom;


//  @Data
//  @Document(collection="RoomHotel")
// public class Room {
//     @Id
//     private  Long id;
//     private String roomType;
//     private BigDecimal roomPrice;
//     private boolean isBooked = false;
//     // @Lob
//     private Blob photo;

//     // @OneToMany(mappedBy="room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//     private List<BookedRoom> bookings;

//     public Room() {
//         this.bookings = new ArrayList<>();
//     }
//     public void addBooking(BookedRoom booking){
//         if (bookings == null){
//             bookings = new ArrayList<>();
//         }
//         bookings.add(booking);
//         booking.setRoom(null);
//         isBooked = true;
//         String bookingCode = UUID.randomUUID().toString();
//         booking.setBookingConfirmationCode(bookingCode);
//     }
// }
package com.example.hotelbackend.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "rooms")
// @AllArgsConstructor
public class Room {

    @Id
    private String id;   // MongoDB uses String or ObjectId

    private String roomType;
    private BigDecimal roomPrice;
    private BigDecimal roomNo;
    private boolean isBooked = false;

    // ID of image stored in GridFS
    private String photoId;

    // Store BookedRoom documents reference
   
    @DBRef 
    private List<BookedRoom> bookings = new ArrayList<>();

    public void addBooking(BookedRoom booking){
        if (bookings == null){
            bookings = new ArrayList<>();
        }

        bookings.add(booking);

        isBooked = true;

        // Generate unique code for confirmation
        String bookingCode = UUID.randomUUID().toString();
        booking.setBookingConfirmationCode(bookingCode);
    }

}
