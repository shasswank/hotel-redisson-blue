package com.example.hotelbackend.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.hotelbackend.model.Room;


public interface IRoomService {
    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, BigDecimal roomNo) throws IOException;

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    byte[] getRoomPhotoByRoomId(String roomId) throws IOException ;

    void deleteRoom(String roomId);

    Room updateRoom(String roomId, String roomType, BigDecimal roomPrice,BigDecimal roomNo, MultipartFile photo);

    Optional<Room> getRoomById(String roomId);

    // List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
