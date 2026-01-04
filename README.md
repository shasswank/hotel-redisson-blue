Hotel Redisson Blue is a full-stack web application designed to manage hotel operations and facilitate room bookings. It provides a seamless experience for both guests and administrators, allowing users to browse rooms and make reservations while administrators manage room availability, bookings, and user accounts.

✨ Key Features
User Authentication: Secure login and registration using JWT (Spring Security).

Role-Based Access: Distinct portals for Admin (management) and User (booking).

Room Management: Add, edit, and delete room types (e.g., Deluxe, Suite) with images and pricing.

Booking System: Real-time availability checks and reservation capability.

Profile Management: Users can view their booking history and manage their profiles.

Responsive Design: Optimized for various devices using React and modern CSS.

🛠️ Tech Stack
Backend
Language: Java

Framework: Spring Boot 3+

Security: Spring Security (JWT Auth)

Database: MySQL

Cloud Storage: AWS S3 (for storing room images)

Build Tool: Maven

Frontend
Library: React.js

Styling: CSS / Styled Components

HTTP Client: Axios

Routing: React Router DOM

🚀 Getting Started
Follow these instructions to set up the project locally.

Prerequisites
Java Development Kit (JDK) 17 or higher

Node.js & npm

MySQL Server

An AWS Account (for S3 bucket configuration)

Installation
Clone the Repository

Bash

git clone https://github.com/shasswank/hotel-redisson-blue
cd hotel-redisson-blue
Backend Setup (Spring Boot)

Navigate to the backend directory.

Open src/main/resources/application.properties and update your MySQL and AWS credentials:

Properties

spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

cloud.aws.credentials.access-key=YOUR_AWS_ACCESS_KEY
cloud.aws.credentials.secret-key=YOUR_AWS_SECRET_KEY
cloud.aws.s3.bucket=YOUR_BUCKET_NAME
Run the application:

Bash

mvn spring-boot:run
Frontend Setup (React)

Navigate to the frontend directory in a new terminal.

Install dependencies:

Bash

npm install
Start the React development server:

Bash

npm start
