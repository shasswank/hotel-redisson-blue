package com.example.hotelbackend.service;

import java.time.LocalDate;
import java.util.List;

import com.example.hotelbackend.model.BookedRoom;



public interface IBookingService {
    void cancelBooking(String bookingId);

    List<com.example.hotelbackend.model.BookedRoom> getAllBookingsByRoomId(String roomId);

    String saveBooking(String roomId, BookedRoom bookingRequest);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);
boolean filterNonOverlappingBookings(LocalDate checkInDate, LocalDate checkOutDate, List<BookedRoom> roomType);
    List<com.example.hotelbackend.model.BookedRoom> getAllBookings();

    List<com.example.hotelbackend.model.BookedRoom> getBookingsByUserEmail(String email);

}
