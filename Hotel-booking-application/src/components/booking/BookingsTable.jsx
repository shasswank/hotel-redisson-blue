import { parseISO } from "date-fns"
import React, { useState, useEffect } from "react"
import DateSlider from "../common/DateSlider"

const BookingsTable = ({ bookingInfo, handleBookingCancellation }) => {
	const [filteredBookings, setFilteredBookings] = useState(bookingInfo)
	const convertArrayToDate = (arr) => {
  if (Array.isArray(arr)) {
    return new Date(arr[0], arr[1] - 1, arr[2]) // month -1 because JS month start at 0
  }
  return new Date(arr) // in case it's already ISO string
}

	const filterBooknigs = (startDate, endDate) => {
		let filtered = bookingInfo
		if (startDate && endDate) {
			 const start = new Date(startDate);
    const end = new Date(endDate);
			filtered = bookingInfo.filter((booking) => {
				const bookingStarDate = convertArrayToDate(booking.checkInDate)
			
				// console.log(bookingInfo)
				const bookingEndDate = convertArrayToDate(booking.checkOutDate)
				console.log(bookingStarDate >= start )
				console.log(bookingEndDate <= end )
				console.log(bookingEndDate > start )
				
				return (
					bookingStarDate >= start && bookingEndDate <= end && bookingEndDate > start
				)
			})
		}
		setFilteredBookings(filtered)
	}
console.log("this is FilteredBookings ",filteredBookings)
	useEffect(() => {
		setFilteredBookings(bookingInfo)
	}, [bookingInfo])

	return (
		<section className="p-4">
			<DateSlider onDateChange={filterBooknigs} onFilterChange={filterBooknigs} />
			<table className="table table-bordered table-hover shadow">
				<thead>
					<tr>
						<th>S/N</th>
						<th>Booking ID</th>
						<th>Room ID</th>
						<th>Room Type</th>
						<th>Check-In Date</th>
						<th>Check-Out Date</th>
						<th>Guest Name</th>
						<th>Guest Email</th>
						<th>Adults</th>
						<th>Children</th>
						<th>Total Guest</th>
						<th>Confirmation Code</th>
						<th colSpan={2}>Actions</th>
					</tr>
				</thead>
				<tbody className="text-center">
					{filteredBookings.map((booking, index) => (
						<tr key={booking.id}>
							<td>{index + 1}</td>
							<td>{booking.id}</td>
							<td>{booking.room.id}</td>
							<td>{booking.room.roomType}</td>
							<td>{booking.checkInDate[0]+"-"+booking.checkInDate[1]+"-"+booking.checkInDate[2]}</td>
                            <td>{booking.checkOutDate[0]+"-"+booking.checkOutDate[1]+"-"+booking.checkOutDate[2]}</td>
							<td>{booking.guestName}</td>
							<td>{booking.guestEmail}</td>
							<td>{booking.numOfAdults}</td>
							<td>{booking.numOfChildren}</td>
							<td>{booking.totalNumOfGuests}</td>
							<td>{booking.bookingConfirmationCode}</td>
							<td>
								<button
									className="btn btn-danger btn-sm"
									onClick={() => handleBookingCancellation(booking.id)}>
									Cancel
								</button>
							</td>
						</tr>
					))}
				</tbody>
			</table>
			{filterBooknigs.length === 0 && <p> No booking found for the selected dates</p>}
		</section>
	)
}

export default BookingsTable
