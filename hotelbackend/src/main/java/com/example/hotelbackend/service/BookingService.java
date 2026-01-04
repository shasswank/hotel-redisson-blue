package com.example.hotelbackend.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotelbackend.exception.InvalidBookingRequestException;
import com.example.hotelbackend.exception.ResourceNotFoundException;
import com.example.hotelbackend.model.BookedRoom;
import com.example.hotelbackend.model.Room;
import com.example.hotelbackend.repository.BookingRepository;
import com.example.hotelbackend.repository.RoomRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
   private final IRoomService roomService;
private final RoomRepository roomRepository;

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }


    @Override
    public List<BookedRoom> getBookingsByUserEmail(String email) {
        return bookingRepository.findByGuestEmail(email);
    }

    @Override
    public void cancelBooking(String bookingId) {
        BookedRoom booked=bookingRepository.findById(bookingId).get();
        System.out.println(booked.getCheckInDate()+"and"+booked.getCheckInDate());
   String id= booked.getRoom().getId();
    Room room=  roomRepository.findById(id).get();
   List<BookedRoom> getBook =room.getBookings();
   getBook.remove(booked);
   room.setBookings(getBook);
   roomRepository.save(room);

        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(String roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public String saveBooking(String roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        System.out.print("this is date "+bookingRequest.getCheckOutDate());
        Room room = roomService.getRoomById(roomId).get();

        List<BookedRoom> existingBookings = room.getBookings();
        if(room.getBookings()!=null){
        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        if (roomIsAvailable){

            Room reRoom=new  Room();
            reRoom.setId(roomId);
            reRoom.setRoomPrice(room.getRoomPrice());
            reRoom.setRoomType(room.getRoomType());
             bookingRequest.setRoom(reRoom);
            room.addBooking(bookingRequest);
            // roomRepository.save(room);
    bookingRepository.save(bookingRequest);
               roomRepository.save(room);
        }else{
            throw  new InvalidBookingRequestException("Sorry, This room is not available for the selected dates;");
        }
        }
        else{
         Room reRoom=new  Room();
            reRoom.setId(roomId);
            reRoom.setRoomPrice(room.getRoomPrice());
            reRoom.setRoomType(room.getRoomType());
             bookingRequest.setRoom(reRoom);
            room.addBooking(bookingRequest);
            // roomRepository.save(room);
    bookingRepository.save(bookingRequest);
               roomRepository.save(room);
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code :"+confirmationCode));

    }

    @Override
    public boolean  filterNonOverlappingBookings(
        LocalDate myCheckIn,
        LocalDate myCheckOut,
        List<BookedRoom> bookings) {


    for (BookedRoom booking : bookings) {

        LocalDate checkIn = booking.getCheckInDate();
        LocalDate checkOut = booking.getCheckOutDate();

        // Condition for NON OVERLAP
        boolean notOverlap =
                checkOut.isBefore(myCheckIn) ||
                checkIn.isAfter(myCheckOut);
        if (!notOverlap) {
            return false;
        }
    }

    return true;
}

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }




}
