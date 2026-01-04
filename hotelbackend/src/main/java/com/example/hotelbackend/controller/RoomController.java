
package com.example.hotelbackend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotelbackend.exception.ResourceNotFoundException;
import com.example.hotelbackend.model.BookedRoom;
import com.example.hotelbackend.model.Room;
import com.example.hotelbackend.repository.BookingRepository;
import com.example.hotelbackend.repository.RoomRepository;
import com.example.hotelbackend.response.BookingResponse;
import com.example.hotelbackend.response.RoomResponse;
import com.example.hotelbackend.service.BookingService;
import com.example.hotelbackend.service.IRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final IRoomService roomService;
    private final BookingService bookingService;
private final BookingRepository bookingRepository;
private final RoomRepository roomRepository;
    // -------------------------------
    // ADD ROOM
    // -------------------------------
    @PostMapping(value = "/add/new-room", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Room> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice,
            @RequestParam("roomNo") BigDecimal roomNo
    ) throws IOException {

        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice, roomNo);
        return ResponseEntity.ok(savedRoom);
    }

    // -------------------------------
    // GET ROOM TYPES
    // -------------------------------
    @GetMapping("/room/types")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }

    // -------------------------------
    // GET ALL ROOMS
    // -------------------------------
    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws IOException {

        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> responseList = new ArrayList<>();

        for (Room room : rooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            RoomResponse roomResponse = getRoomResponse(room);

            if (photoBytes != null) {
                roomResponse.setPhoto(Base64.encodeBase64String(photoBytes));
            }
            responseList.add(roomResponse);
        }
        return ResponseEntity.ok(responseList);
    }

    // -------------------------------
    // DELETE ROOM
    // -------------------------------
    @DeleteMapping("/delete/room/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomId) {

        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // -------------------------------
    // UPDATE ROOM
    // -------------------------------
    @PutMapping(value = "/update/{roomId}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable String roomId,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) BigDecimal roomPrice,
            @RequestParam(required = false) BigDecimal roomNo,
            @RequestParam(required = false) MultipartFile photo
    ) throws IOException {

        byte[] photoBytes = photo != null && !photo.isEmpty()
                ? photo.getBytes()
                : roomService.getRoomPhotoByRoomId(roomId);

        Room updatedRoom = roomService.updateRoom(roomId,roomType,roomNo,roomPrice, photo);
        RoomResponse response = getRoomResponse(updatedRoom);

        if (photoBytes != null) {
            response.setPhoto(Base64.encodeBase64String(photoBytes));
        }

        return ResponseEntity.ok(response);
    }

    // -------------------------------
    // GET ROOM BY ID
    // -------------------------------
    @GetMapping("/room/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable String roomId) throws IOException {

        Room room = roomService.getRoomById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        RoomResponse response = getRoomResponse(room);

        byte[] photoBytes = roomService.getRoomPhotoByRoomId(roomId);
        if (photoBytes != null) {
            response.setPhoto(Base64.encodeBase64String(photoBytes));
        }

        return ResponseEntity.ok(response);
    }

    // -------------------------------
    // GET AVAILABLE ROOMS
    // -------------------------------
    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("roomType") String roomType
    ) throws IOException {
 List<Room> allRooms=roomRepository.findAllByRoomTypeIgnoreCase(roomType);
 
        List<Room>AllavailableRooms=new ArrayList<>();
         for (Room bRoom : allRooms) {
            List<BookedRoom> booking=bRoom.getBookings();
                    boolean availableRooms = bookingService.filterNonOverlappingBookings(checkInDate, checkOutDate, booking);
                    if(availableRooms){
                     AllavailableRooms.add(bRoom); 
                    }
         }
        List<RoomResponse> responseList = new ArrayList<>();

        for (Room room : AllavailableRooms) {
            RoomResponse response = getRoomResponse(room);
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());

            if (photoBytes != null) {
                response.setPhoto(Base64.encodeBase64String(photoBytes));
            }
            responseList.add(response);
        }

        return responseList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(responseList);
    }

    // -------------------------------------------------------------------------
    // PRIVATE MAPPER
    // -------------------------------------------------------------------------
    private RoomResponse getRoomResponse(Room room) {

        List<BookedRoom> bookings = bookingService.getAllBookingsByRoomId(room.getId());

        List<BookingResponse> bookingInfo = bookings.stream()
                .map(booking -> new BookingResponse(
                        booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getBookingConfirmationCode(),
                        null, 0, 0, 0, null, null
                ))
                .toList();

        return new RoomResponse(
                room.getId(),
                room.getRoomType(),
                room.getRoomPrice(),
                room.getRoomNo(),
                room.isBooked(),
                null,
                bookingInfo
        );
    }
}
