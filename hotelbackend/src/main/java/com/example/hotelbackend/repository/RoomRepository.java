package com.example.hotelbackend.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelbackend.model.Room;

@Repository
public interface RoomRepository extends MongoRepository<Room, String>{
// @Aggregation(pipeline = {
    //     "{ $match: { roomType: { $regex: ?0, $options: 'i' } } }",

    //     // Lookup BookedRoom for each room
    //     "{ $lookup: { " +
    //             "from: 'bookedRoom', " +
    //             "localField: '_id', " +
    //             "foreignField: 'roomId', " +
    //             "as: 'bookings' " +
    //         "} }",

    //     // Filter rooms where NO booking overlaps requested dates
    //     "{ $match: { " +
    //             "bookings: { $not: { " +
    //                 "$elemMatch: { " +
    //                     "$and: [ " +
    //                         "{ checkInDate: { $lte: ?2 } }, " +     // br.checkInDate <= checkOutDate
    //                         "{ checkOutDate: { $gte: ?1 } } " +    // br.checkOutDate >= checkInDate
    //                     "] " +
    //                 "} } " +
    //             "} " +
    //         "} }"
    // })
    // List<Room> findAvailableRooms(String roomType,
    //                               LocalDate checkInDate,
    //                               LocalDate checkOutDate);
    // List<Room>findAllbyRoomTyp
   List<Room> findAllByRoomTypeIgnoreCase(String roomType);

}

