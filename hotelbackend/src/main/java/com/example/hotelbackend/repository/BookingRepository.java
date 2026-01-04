package com.example.hotelbackend.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hotelbackend.model.BookedRoom;



public interface BookingRepository extends MongoRepository<BookedRoom, String> {

    List<BookedRoom> findByRoomId(String roomId);

 Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> findByGuestEmail(String email);
}
