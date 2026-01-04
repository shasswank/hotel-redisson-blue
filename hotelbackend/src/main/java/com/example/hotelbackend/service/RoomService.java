 package com.example.hotelbackend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotelbackend.exception.ResourceNotFoundException;
import com.example.hotelbackend.model.Room;
import com.example.hotelbackend.repository.RoomRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;
    private final GridFsTemplate gridFsTemplate;
    private final GridFSBucket gridFSBucket;

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice,BigDecimal roomNo) throws IOException {

        Room room = new Room();
        room.setRoomType(roomType.toUpperCase());
        room.setRoomPrice(roomPrice);
        room.setRoomNo(roomNo);
        // Store image in GridFS
        if (file != null && !file.isEmpty()) {
            ObjectId fileId = gridFsTemplate.store(
                    file.getInputStream(),
                    file.getOriginalFilename(),
                    file.getContentType()
            );
            room.setPhotoId(fileId.toString());
        }

        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        
        return roomRepository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(String roomId) throws IOException {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (room.getPhotoId() == null) {
            return null;
        }

        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(room.getPhotoId()))
        );

        // if (file == null)
        //     throw new ResourceNotFoundException("Image not found!");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        gridFSBucket.downloadToStream(file.getObjectId(), os);

        return os.toByteArray();
    }

    @Override
    public void deleteRoom(String roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        // Delete image from GridFS
        if (room.getPhotoId() != null) {
            gridFsTemplate.delete(
                    Query.query(Criteria.where("_id").is(room.getPhotoId()))
            );
        }

        roomRepository.deleteById(roomId);
    }

    @Override
    public Room updateRoom(String roomId, String roomType, BigDecimal roomPrice,BigDecimal roomNo, MultipartFile photo) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (roomType != null) room.setRoomType(roomType);
        if (roomPrice != null) room.setRoomPrice(roomPrice);
        if (roomNo != null) room.setRoomNo(roomNo);
        // Update image
        if (photo != null && !photo.isEmpty()) {

            // delete old image
            if (room.getPhotoId() != null) {
                gridFsTemplate.delete(
                        Query.query(Criteria.where("_id").is(room.getPhotoId()))
                );
            }

            // upload new image
            try {
              ObjectId newPhotoId = gridFsTemplate.store(
                    photo.getInputStream(),
                    photo.getOriginalFilename(),
                    photo.getContentType()
            );

            room.setPhotoId(newPhotoId.toString());  
        return roomRepository.save(room);

            } catch (IOException e) {
    throw new RuntimeException("Error storing room photo: " + e.getMessage());

            }

            // ObjectId newPhotoId = gridFsTemplate.store(
            //         photo.getInputStream(),
            //         photo.getOriginalFilename(),
            //         photo.getContentType()
            // );

            // room.setPhotoId(newPhotoId.toString());
        }

        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(String roomId) {
        return roomRepository.findById(roomId);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findAll()
                .stream()
                .map(Room::getRoomType)
                .distinct()
                .toList();
    }

    // @Override
    // public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
    //     System.out.println(checkInDate);
    //     return roomRepository.findAvailableRooms(roomType, checkInDate, checkOutDate); // You can implement later
    // }
}
