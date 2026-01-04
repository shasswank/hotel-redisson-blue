package com.example.hotelbackend.response;

import java.time.LocalDate;

import lombok.Data;



@Data

public class BookingResponse {

    private String id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String guestName;

    private String guestEmail;

    private int numOfAdults;

    private int numOfChildren;

    private int totalNumOfGuests;

    private String bookingConfirmationCode;

    private RoomResponse room;

    // public BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate,
    //                        String bookingConfirmationCode) {
    //     this.id = id;
    //     this.checkInDate = checkInDate;
    //     this.checkOutDate = checkOutDate;
    //     this.bookingConfirmationCode = bookingConfirmationCode;
    // }

    public BookingResponse(String id, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName, String guestEmail, int numOfAdults, int numOfChildren, int totalNumOfGuest, String bookingConfirmationCode, RoomResponse room) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.guestEmail=guestEmail;
        this.guestName=guestFullName;
        this.room=room;
        this.numOfAdults=numOfAdults;
        this.numOfChildren=numOfChildren;
        this.totalNumOfGuests=totalNumOfGuest;
        // throw new UnsupportedOperationException("Not supported yet.");
    }
}
