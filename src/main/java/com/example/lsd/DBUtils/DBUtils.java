package com.example.lsd.DBUtils;

import com.example.lsd.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.Period;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Helper class, cannot be instantiated
public class DBUtils {
    private static Connection con;

    // Creates a Connection with the mySql DataBase
    public static Connection createDBConnection() {
        try{
            //load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //get connection
            String url = "jdbc:mysql://localhost:3306/LSD";
            String username = "root";
            String password="gH75$Y@65";
            con = DriverManager.getConnection(url, username, password);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return con;
    }

    // Checks if the user with the Cnic is already registered.
    public static boolean isAlreadyExist(String cnic) {
        con = createDBConnection();

        String query = "SELECT * FROM serviceseeker WHERE cnic = ?";
        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1,  cnic);

                try (ResultSet rs = pstmt.executeQuery()) {
                    // If the result set has at least one row, it means the user alreeady exist
                    return rs.next();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Checks if the user with the username is already registered.
    public static boolean usernameAlreadyExist(String username) {
        con = createDBConnection();

        String query = "SELECT * FROM serviceseeker WHERE username = ?";
        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1,  username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    // If the result set has at least one row, it means the user alreeady exist
                    return rs.next();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Validates the login credentials.
    public static boolean login(String username, String password) {
        con = createDBConnection();

        String query = "SELECT * FROM logincredentials WHERE username = ? AND password = ?";
        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    // If the result set has at least one row, it means the credentials are valid
                    return rs.next();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Checks if the CNIC provided by the user is valid or not.
    public static boolean evcExist(String cnic) {
        con = createDBConnection();

        String query = "SELECT * FROM evc WHERE cnic = ?";
        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, cnic);

                try (ResultSet rs = pstmt.executeQuery()) {
                    // If the result set has at least one row, it means the credentials are valid
                    return rs.next();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Gets the Info of Service Seeker by their username
    public static ServiceSeeker getServiceSeekerInfo(String username) {
        Connection con = createDBConnection();
        String query = "SELECT * FROM serviceseeker WHERE username = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Create a ServiceSeeker object and populate it with data from the ResultSet
                        ServiceSeeker serviceSeeker = new ServiceSeeker();
                        serviceSeeker.setUsername(rs.getString("username"));
                        serviceSeeker.setName(rs.getString("name"));
                        String dob = rs.getString("date_of_birth");
                        serviceSeeker.setDateOfBirth(dob);
                        int age = CalculateAge(dob);
                        serviceSeeker.setAge(age);
                        serviceSeeker.setGender(rs.getString("gender"));
                        serviceSeeker.setEmail(rs.getString("email"));
                        serviceSeeker.setPhone(rs.getString("phone"));
                        serviceSeeker.setCnic(rs.getString("cnic"));
                        serviceSeeker.setAddress(rs.getString("address"));
                        serviceSeeker.setZipcode(rs.getString("zipcode"));
                        serviceSeeker.setImage(rs.getString("image"));

                        return serviceSeeker;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null; // Return null if no matching user found
    }

    public static void insertLoginCredentials(String username, String password) {
        Connection con = createDBConnection();
        String query = "INSERT INTO logincredentials (username, password) VALUES (?, ?)";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Insert the Info of Service Seeker.
    public static void insertServiceSeeker(ServiceSeeker serviceSeeker) {
        Connection con = createDBConnection();
        String query = "INSERT INTO serviceseeker (username, name, date_of_birth, gender, email, phone, cnic, address, zipcode, joining_date, image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, serviceSeeker.getUsername());
                pstmt.setString(2, serviceSeeker.getName());
                pstmt.setString(3, serviceSeeker.getDateOfBirth());
                pstmt.setString(4, serviceSeeker.getGender());
                pstmt.setString(5, serviceSeeker.getEmail());
                pstmt.setString(6, serviceSeeker.getPhone());
                pstmt.setString(7, serviceSeeker.getCnic());
                pstmt.setString(8, serviceSeeker.getAddress());
                pstmt.setString(9, serviceSeeker.getZipcode());
                pstmt.setString(10, serviceSeeker.getJoining_date());
                pstmt.setString(11, serviceSeeker.getImage());

                pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // Update the Info of Service Seeker.
    public static void updateServiceSeekerInfo(String username, String image, String email,
                                               String phone, String address, String zipcode) {
        Connection con = createDBConnection();
        String query = "UPDATE serviceseeker SET image=?, email=?, phone=?, address=?, zipcode=? WHERE username=?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, image);
                pstmt.setString(2, email);
                pstmt.setString(3, phone);
                pstmt.setString(4, address);
                pstmt.setString(5, zipcode);
                pstmt.setString(6, username);

                pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // If the CNIC is valid, this function is used to get the basic data of the user, who is trying to register.
    public static User getUserInfo(String cnic) {
        Connection con = createDBConnection();
        String query = "SELECT * FROM evc WHERE cnic = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, cnic);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Create a User object and populate it with data from the ResultSet
                        User user = new User();
                        user.setCnic(rs.getString("cnic"));
                        user.setName(rs.getString("name"));
                        user.setDateOfBirth(rs.getString("date_of_birth"));
                        user.setGender(rs.getString("gender"));
                        user.setAge(CalculateAge(user.getDateOfBirth().toString()));

                        return user;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null; // Return null if no matching user found
    }


    // For the password change, checking the entered old password is correct
    public static boolean oldPasswordCorrect(String username, String enteredOldPassword) {
        Connection con = createDBConnection();

        String query = "SELECT password FROM logincredentials WHERE username = ?";
        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1,  username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Retrieve the old password from the result set
                        String oldPasswordFromDB = rs.getString("password");

                        // Compare the entered old password with the one from the database
                        return oldPasswordFromDB.equals(enteredOldPassword);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Updates the password of a Service Seeker
    public static void updatePassword(String username, String newPassword) {
        Connection con = createDBConnection();

        String query = "UPDATE logincredentials SET password=? WHERE username=?";
        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, username);

                pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // Admin Service Provider CRUD Operations
    // Retrieve Operation
    public static ObservableList<ServiceProvider> getAllServiceProviders() {
        ObservableList<ServiceProvider> listServiceProviders = FXCollections.observableArrayList();

        Connection con = createDBConnection();
        String query = "SELECT * FROM serviceprovider";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String username = rs.getString("username");
                        String name = rs.getString("name");
                        String status = rs.getString("status");
                        String dob = rs.getString("date_of_birth");
                        int age = CalculateAge(dob);
                        String gender = rs.getString("gender");
                        String email = rs.getString("email");
                        String phone = rs.getString("phone");
                        String zipCode = rs.getString("zipcode");
                        String joiningDate = rs.getString("joining_date");
                        double ratings = rs.getDouble("rating");
                        String image = rs.getString("image");

                        // Get categories for the service provider
                        List<Category> categories = getServiceProviderCategories(username);

                        // Create ServiceProvider object
                        ServiceProvider serviceProvider = new ServiceProvider(username, name, status, dob, age, gender, email, phone, zipCode, joiningDate, ratings, categories, image);

                        // Add to the observable list
                        listServiceProviders.add(serviceProvider);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listServiceProviders;
    }


    // To Display the Details of the Service Provider
    public static ServiceProvider getSpecificServiceProviders(String username) {
        Connection con = createDBConnection();
        String query = "SELECT * FROM serviceprovider WHERE username = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        String status = rs.getString("status");
                        String dob = rs.getString("date_of_birth");
                        int age = CalculateAge(dob);
                        String gender = rs.getString("gender");
                        String email = rs.getString("email");
                        String phone = rs.getString("phone");
                        String zipCode = rs.getString("zipcode");
                        String joiningDate = rs.getString("joining_date");
                        double ratings = rs.getDouble("rating");
                        String image = rs.getString("image");

                        // Get categories for the service provider
                        List<Category> categories = getServiceProviderCategories(username);

                        // Create ServiceProvider object
                        ServiceProvider serviceProvider = new ServiceProvider(username, name, status, dob, age, gender, email, phone, zipCode, joiningDate, ratings, categories, image);

                        return serviceProvider;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // Create Operation
    public static boolean insertServiceProvider(ServiceProvider serviceProvider) {
        Connection con = createDBConnection();

        String sql = "INSERT INTO serviceprovider (username, name, status, date_of_birth, gender, email, phone, zipcode, joining_date, rating, image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, serviceProvider.getUsername());
            preparedStatement.setString(2, serviceProvider.getName());
            preparedStatement.setString(3, serviceProvider.getStatus());
            preparedStatement.setString(4, serviceProvider.getDob());
            preparedStatement.setString(5, serviceProvider.getGender());
            preparedStatement.setString(6, serviceProvider.getEmail());
            preparedStatement.setString(7, serviceProvider.getPhone());
            preparedStatement.setString(8, serviceProvider.getZipCode());
            preparedStatement.setString(9, serviceProvider.getJoiningDate());
            preparedStatement.setDouble(10, serviceProvider.getRating());
            preparedStatement.setString(11, serviceProvider.getImage());

            int rowsInserted = preparedStatement.executeUpdate();

            // If rowsInserted > 0, the insertion was successful
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's error-handling strategy
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception according to your application's error-handling strategy
            }
        }

        return false;
    }

    // Delete Operation
    public static boolean deleteServiceProvider(String username) {
        Connection con = createDBConnection();

        // Delete from provider_categories first to maintain referential integrity
        String deleteCategoriesQuery = "DELETE FROM provider_categories WHERE provider_username = ?";

        // Then delete from serviceprovider
        String deleteServiceProviderQuery = "DELETE FROM serviceprovider WHERE username = ?";

        try {
            con.setAutoCommit(false); // Start a transaction

            try (PreparedStatement deleteCategoriesStmt = con.prepareStatement(deleteCategoriesQuery)) {
                deleteCategoriesStmt.setString(1, username);
                deleteCategoriesStmt.executeUpdate();
            }

            try (PreparedStatement deleteServiceProviderStmt = con.prepareStatement(deleteServiceProviderQuery)) {
                deleteServiceProviderStmt.setString(1, username);
                int rowsAffected = deleteServiceProviderStmt.executeUpdate();

                con.commit(); // Commit the transaction

                // If rowsAffected > 0, the deletion was successful
                return rowsAffected > 0;
            }
        } catch (Exception ex) {
            try {
                con.rollback(); // Rollback the transaction in case of an exception
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Reset auto-commit to true
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Update Operation
    public static boolean updateServiceProvider(ServiceProvider serviceProvider) {
        Connection con = createDBConnection();

        // Update serviceprovider table
        String updateServiceProviderQuery = "UPDATE serviceprovider SET name = ?, date_of_birth = ?, gender = ?, email = ?, phone = ?, image = ?, status = ?, zipcode = ? WHERE username = ?";

        try (PreparedStatement updateServiceProviderStmt = con.prepareStatement(updateServiceProviderQuery)) {
            updateServiceProviderStmt.setString(1, serviceProvider.getName());
            updateServiceProviderStmt.setString(2, serviceProvider.getDob());
            updateServiceProviderStmt.setString(3, serviceProvider.getGender());
            updateServiceProviderStmt.setString(4, serviceProvider.getEmail());
            updateServiceProviderStmt.setString(5, serviceProvider.getPhone());
            updateServiceProviderStmt.setString(6, serviceProvider.getImage());
            updateServiceProviderStmt.setString(7, serviceProvider.getStatus());
            updateServiceProviderStmt.setString(8, serviceProvider.getZipCode());
            updateServiceProviderStmt.setString(9, serviceProvider.getUsername());

            int rowsAffected = updateServiceProviderStmt.executeUpdate();

            // If rowsAffected > 0, the update was successful
            return rowsAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    // Load all categories
    public static List<Category> loadAllCategories() {
        List<Category> categories = new ArrayList<>();
        Connection con = createDBConnection();

        // Query to retrieve all categories
        String categoryQuery = "SELECT id, name, price FROM categories";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(categoryQuery)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int categeoryId = rs.getInt("id");
                        String categoryName = rs.getString("name");
                        double categoryPrice = rs.getDouble("price");
                        categories.add(new Category(categeoryId, categoryName, categoryPrice));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return categories;
    }

    // Load Category name based on id
    public static String getCategoryNameById(int categoryId) {
        Connection con = createDBConnection();
        String query = "SELECT name FROM categories WHERE id = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, categoryId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Retrieve and return the category name
                        return rs.getString("name");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null; // Return null if no matching category found
    }

    // Load Category price based on name
    public static double getCategoryPiceByName(String categoryName) {
        Connection con = createDBConnection();
        String query = "SELECT price FROM categories WHERE name = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, categoryName);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Retrieve and return the category name
                        return rs.getDouble("price");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0.0;
    }

    // Load Category Id based on Name
    public static int getCategoryIdByName(String categoryName) {
        Connection con = createDBConnection();
        String query = "SELECT id FROM categories WHERE name = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, categoryName);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Retrieve and return the category name
                        return rs.getInt("id");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    // Load categories of a specific person
    public static List<Category> getServiceProviderCategories(String username) {
        List<Category> categories = new ArrayList<>();
        Connection con = createDBConnection();

        // Query to get categories for the given service provider username
        String categoryQuery = "SELECT c.id, c.name, c.price " +
                "FROM provider_categories pc " +
                "JOIN categories c ON pc.category_id = c.id " +
                "WHERE pc.provider_username = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(categoryQuery)) {
                pstmt.setString(1, username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int categoryId = rs.getInt("id");
                        String categoryName = rs.getString("name");
                        double categoryPrice = rs.getDouble("price");
                        categories.add(new Category(categoryId, categoryName, categoryPrice));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return categories;
    }

    // Delete all the Categories of a Specific Service Provider
    public static boolean deleteServiceProviderCategories(String username) {
        Connection con = createDBConnection();
        String query = "DELETE FROM provider_categories WHERE provider_username = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Insert Categories for a Specific Service Provider
    public static void insertServiceProviderCategories(String username, List<Category> categories) {
        Connection con = createDBConnection();
        String query = "INSERT INTO provider_categories (provider_username, category_id) VALUES (?, ?)";

        try {
            con.setAutoCommit(false); // Start a transaction
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                for (Category category : categories) {
                    pstmt.setString(1, username);
                    pstmt.setInt(2, category.getCategoryId());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                con.commit(); // Commit the transaction
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback(); // Rollback in case of an error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Reset auto-commit to true
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Admin Bookings CRUD Operations
    // Retrieve Operation
    public static ObservableList<Booking> getAllBookings() {
        ObservableList<Booking> listBookings = FXCollections.observableArrayList();

        Connection con = createDBConnection();
        String query = "SELECT * FROM booking";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int bookingId = rs.getInt("booking_id");
                        String seekerUsername = rs.getString("seeker_username");
                        String providerUsername = rs.getString("provider_username");
                        LocalDateTime bookingDate = rs.getTimestamp("booking_date").toLocalDateTime();
                        int serviceTypeId = rs.getInt("service_type_id");
                        String status = rs.getString("status");
                        String description = rs.getString("description");
                        String image = rs.getString("image");

                        String serviceType = DBUtils.getCategoryNameById(serviceTypeId);

                        // Create Booking object
                        Booking booking = new Booking(bookingId, seekerUsername, providerUsername, bookingDate, serviceType, status, description, image);

                        // Add to the observable list
                        listBookings.add(booking);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listBookings;
    }


    // Add Operation
    public static boolean insertBooking(Booking booking) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            con = createDBConnection();
            String query = "INSERT INTO booking (booking_id, seeker_username, provider_username, booking_date, service_type_id, status, description, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, booking.getBookingId());
            pstmt.setString(2, booking.getSeekerUsername());
            pstmt.setString(3, booking.getProviderUsername());
            pstmt.setTimestamp(4, Timestamp.valueOf(booking.getBookingDate())); // Convert LocalDateTime to Timestamp
            pstmt.setInt(5, getCategoryIdByName(booking.getServiceType()));
            pstmt.setString(6, booking.getStatus());
            pstmt.setString(7, booking.getDescription());
            pstmt.setString(8, booking.getBookingImage());

            int rowsAffected = pstmt.executeUpdate();
            success = rowsAffected > 0; // Check if any rows were affected
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }


    // Delete Operation
    public static boolean deleteBooking(int bookingId) {
        Connection con = createDBConnection();
        String query = "DELETE FROM booking WHERE booking_id = ?";
        boolean success = false;

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, bookingId);
                int rowsAffected = pstmt.executeUpdate();
                success = rowsAffected > 0; // Check if any rows were affected
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    // Update the Booking
    public static boolean updateBookingStatus(int bookingId, String newStatus) {
        Connection con = createDBConnection();
        String query = "UPDATE booking SET status = ? WHERE booking_id = ?";
        boolean success = false;

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, newStatus); // Set the new status
                pstmt.setInt(2, bookingId);    // Set the booking ID

                int rowsAffected = pstmt.executeUpdate();
                success = rowsAffected > 0; // Check if any rows were affected
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }










    // Get Bookings of Specific Service Seeker
    public static ObservableList<Booking> getBookingsForSeeker(String seekerUsername) {
        ObservableList<Booking> listBookings = FXCollections.observableArrayList();

        Connection con = createDBConnection();
        String query = "SELECT * FROM booking WHERE seeker_username = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, seekerUsername);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int bookingId = rs.getInt("booking_id");
                        String providerUsername = rs.getString("provider_username");
                        LocalDateTime bookingDate = rs.getTimestamp("booking_date").toLocalDateTime();
                        int serviceTypeId = rs.getInt("service_type_id");
                        String status = rs.getString("status");
                        String description = rs.getString("description");
                        String image = rs.getString("image");

                        String serviceType = DBUtils.getCategoryNameById(serviceTypeId);

                        // Create Booking object
                        Booking booking = new Booking(bookingId, seekerUsername, providerUsername, bookingDate, serviceType, status, description, image);

                        // Add to the observable list
                        listBookings.add(booking);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listBookings;
    }



    // Retrieve image for Service Provider
    public static String getServiceProviderImage(String username) {
        Connection con = createDBConnection();
        String query = "SELECT image FROM serviceprovider WHERE username = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Retrieve and return the image path
                        return rs.getString("image");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null; // Return null if no matching image found
    }


    // Retrieve image for Service Seeker
    public static String getServiceSeekerImage(String username) {
        Connection con = createDBConnection();
        String query = "SELECT image FROM serviceseeker WHERE username = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Retrieve and return the image path
                        return rs.getString("image");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null; // Return null if no matching image found
    }


    // Gets the total number of reviews given to a specific Service Provider
    public static int NumberOfReviewsForServiceProvider(String providerUsername) {
        Connection con = createDBConnection();
        int numberOfReviews = 0;

        String query = "SELECT COUNT(r.review_id) AS number_of_reviews " +
                "FROM serviceprovider sp " +
                "JOIN booking b ON sp.username = b.provider_username " +
                "JOIN review r ON b.booking_id = r.booking_id " +
                "WHERE sp.username = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, providerUsername);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    numberOfReviews = rs.getInt("number_of_reviews");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions as appropriate for your application
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions as appropriate for your application
            }
        }

        return numberOfReviews;
    }

    // Returns the number of bookings(pending, completed, cancelled) of the specific service provider
    public static Map<String, Integer> bookingCountsByStatusForServiceProvider(String username) {
        Map<String, Integer> bookingCounts = new HashMap<>();

        // Initialize statuses with zero counts
        bookingCounts.put("Pending", 0);
        bookingCounts.put("Completed", 0);
        bookingCounts.put("Cancelled", 0);

        String query = "SELECT status, COUNT(*) AS count FROM booking WHERE provider_username = ? GROUP BY status";

        try (Connection con = createDBConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String status = rs.getString("status");
                    int count = rs.getInt("count");
                    bookingCounts.put(status, count); // Update the count for each status
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle exception appropriately
        }

        return bookingCounts;
    }

    // DashBoard Related things
    // Returns the Count of Service Seekers
    public static int countServiceSeekers() {
        Connection con = createDBConnection();
        String query = "SELECT COUNT(*) FROM serviceseeker";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Return the count
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0; // Return 0 if an error occurs
    }

    // Returns the Count of Service Providers
    public static int countServiceProviders() {
        Connection con = createDBConnection();
        String query = "SELECT COUNT(*) FROM serviceprovider";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Return the count
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0; // Return 0 if an error occurs
    }

    // Returns the Total No. of Bookings
    public static int countBookings() {
        Connection con = createDBConnection();
        String query = "SELECT COUNT(*) FROM booking";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Return the count
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0; // Return 0 if an error occurs
    }

    // Returns Count of Bookings done on the Current Date
    public static int countBookingsToday() {
        Connection con = createDBConnection();
        String query = "SELECT COUNT(*) FROM booking WHERE DATE(booking_date) = CURDATE()";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Return the count
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0; // Return 0 if an error occurs
    }

    // Returns the Total Money made by Proximity Connect
    public static double sumOfMoneyForAllBookings() {
        Connection con = createDBConnection();
        String query = "SELECT SUM(amount) FROM payment";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Return the sum of money
                        return rs.getDouble(1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0; // Return 0 if an error occurs
    }


    // DashBoard Charts
    // No. of Customers Chart
    public static XYChart.Series<String, Integer> displayNOPChart() {
        Connection con = createDBConnection();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Providers");
        String query = "SELECT YEAR(joining_date), COUNT(username) FROM serviceprovider GROUP BY YEAR(joining_date) ORDER BY YEAR(joining_date) DESC LIMIT 10";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
                    }
                    return series;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static XYChart.Series<String, Integer> displayNOSChart() {
        Connection con = createDBConnection();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Seekers");
        String query = "SELECT YEAR(joining_date), COUNT(username) FROM serviceseeker GROUP BY YEAR(joining_date) ORDER BY YEAR(joining_date) DESC LIMIT 10";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
                    }
                    return series;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    // No. of Bookings Chart
    public static XYChart.Series<String, Integer> displayNOBChart() {
        Connection con = createDBConnection();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Bookings");
        String query = "SELECT booking_date, COUNT(booking_id) FROM booking GROUP BY booking_date ORDER BY booking_date DESC LIMIT 10";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Date bookingDate = rs.getDate(1);
                        // Specify the desired date format
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        // Format the date as a string
                        String formattedDate = dateFormat.format(bookingDate);
                        series.getData().add(new XYChart.Data<>(formattedDate, rs.getInt(2)));
                    }
                    return series;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // Income per day Chart
    public static XYChart.Series<String, Double> displayIPDChart() {
        Connection con = createDBConnection();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Income/Day");
        String query = "SELECT DATE(booking_date), SUM(p.amount) FROM booking b INNER JOIN payment p ON b.booking_id = p.booking_id GROUP BY DATE(b.booking_date) ORDER BY DATE(b.booking_date) DESC LIMIT 10";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getDouble(2)));
                    }
                    return series;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // Retrieve all Suggestion
    public static ObservableList<Suggestion> getAllSuggestions() {
        // Initialize the list
        ObservableList<Suggestion> Suggestions = FXCollections.observableArrayList();

        // Create a database connection
        Connection con = createDBConnection();
        String query = "select * from suggestions ORDER BY comment_date DESC";

        try {
            // Create a statement
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Iterate through the result set
            while (rs.next()) {
                // Extract data from each row
                int suggestionId = rs.getInt("suggestion_id");
                String seekerUsername = rs.getString("seeker_username");
                String comment = rs.getString("comment");
                Date commentDate = rs.getDate("comment_date");

                // Create a Suggestion object and add it to the list
                Suggestion suggestion = new Suggestion(suggestionId, seekerUsername, comment, commentDate);
                Suggestions.add(suggestion);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close the connection
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Return the list of suggestions
        return Suggestions;
    }



    // Add suggestions
    public static boolean insertSuggestion(String seekerUsername, String comment) {
        Connection con = createDBConnection();
        String query = "INSERT INTO suggestions (seeker_username, comment) VALUES (?, ?)";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, seekerUsername);
                pstmt.setString(2, comment);

                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0; // Return true if at least one row was affected (successful insertion)
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Return false if an exception occurred or no rows were affected
    }


    // To get unavailable slots for a specific provider on a given date
    public static List<String> getUnavailableSlotsForProvider(String providerUsername, LocalDate date) {
        List<String> unavailableSlots = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = createDBConnection();
            String query = "SELECT TIME(booking_date) as slot_time FROM booking WHERE provider_username = ? AND DATE(booking_date) = ?";

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, providerUsername);
            pstmt.setString(2, date.toString()); // Convert LocalDate to java.sql.Date

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Time slotTime = rs.getTime("slot_time");
                unavailableSlots.add(slotTime.toString()); // Add the time string to the list
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return unavailableSlots;
    }


    // Insert the Rating of Service Provider on completion of a service
    // Insert a review into the review table
    public static void insertReview(int bookingId, double rating) {
        Connection con = createDBConnection();
        String query = "INSERT INTO review (booking_id, rating) VALUES (?, ?)";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, bookingId);
                pstmt.setDouble(2, rating);

                pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Retrieves the rating of the service provider
    public static double getServiceProviderRating(String username) {
        Connection con = createDBConnection();
        String query = "SELECT rating FROM serviceprovider WHERE username = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Return the current rating
                        return rs.getDouble("rating");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Return a default value if an error occurs
        return 0.0;
    }

    // Update the rating of a service provider
    public static void updateServiceProviderRating(String username, double newRating) {
        Connection con = createDBConnection();
        String query = "UPDATE serviceprovider SET rating = ? WHERE username = ?";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setDouble(1, newRating);
                pstmt.setString(2, username);

                pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Method to get an account by CNIC
    public static Account getAccountByCnic(String cnic) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Account account = null;

        try {
            con = createDBConnection();
            String query = "SELECT * FROM account WHERE user_cnic = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, cnic);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setUserCnic(rs.getString("user_cnic"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setCreditCardNumber(rs.getString("credit_card_number"));
                account.setCreditCardExpiry(rs.getDate("credit_card_expiry").toLocalDate());
                account.setCvv(rs.getInt("cvv"));
                account.setPin(rs.getInt("pin"));
                account.setBalance(rs.getDouble("balance"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return account;
    }

    // Method to insert a payment
    public static boolean insertPayment(Payment payment) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            con = createDBConnection();
            String query = "INSERT INTO payment (booking_id, amount, payment_date, payment_method) VALUES (?, ?, ?, ?)";

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, payment.getBookingId());
            pstmt.setDouble(2, payment.getAmount());
            pstmt.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));
            pstmt.setString(4, payment.getPaymentMethod());

            int rowsAffected = pstmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    // Deducts the specified amount from the user's account
    public static boolean deductAmount(String userCnic, double deductingAmount) {
        Connection con = DBUtils.createDBConnection();
        String selectQuery = "SELECT balance FROM account WHERE user_cnic = ?";
        String updateQuery = "UPDATE account SET balance = ? WHERE user_cnic = ?";

        try {
            // Retrieve the current balance
            double currentBalance;
            try (PreparedStatement selectStmt = con.prepareStatement(selectQuery)) {
                selectStmt.setString(1, userCnic);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (!resultSet.next()) {
                        // User not found
                        return false;
                    }
                    currentBalance = resultSet.getDouble("balance");
                }
            }

            // Check if the balance is sufficient
            if (currentBalance < deductingAmount) {
                // Insufficient balance
                return false;
            }

            // Deduct the amount and update the balance
            double newBalance = currentBalance - deductingAmount;
            try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                updateStmt.setDouble(1, newBalance);
                updateStmt.setString(2, userCnic);
                updateStmt.executeUpdate();
            }

            // Transaction successful
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle exceptions as needed
            return false;

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Retrieves the max booking_id from the booking table
    public static int getMaxBookingId() {
        Connection con = createDBConnection();
        String query = "SELECT MAX(booking_id) FROM booking";

        try {
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return -1; // Return -1 if an error occurs or no booking_id is found
    }


















    // Calculates the Time period a Service Provider has served for Proximity Connect
    public static String calculateTimeServed(String inputDate) {
        // Parse the input date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(inputDate, formatter);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the period between the two dates
        Period period = Period.between(startDate, currentDate);

        // Format the period into the desired string format
        return period.getYears() + "y, " + period.getMonths() + "m, " + period.getDays() + "d";
    }


    // Calculates the Current age
    public static int CalculateAge(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dob, formatter);


        // Extract year, month, and day
        int birthYear = dateOfBirth.getYear();
        int birthMonth = dateOfBirth.getMonthValue();
        int birthDay = dateOfBirth.getDayOfMonth();

        // Get the current date
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        int currentDay = currentDate.getDayOfMonth();

        // Calculate age
        int age = currentYear - birthYear;

        // Check if the birthday has occurred this year
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--; // Subtract one year if the birthday hasn't occurred yet
        }

        //age_fld.setText(String.valueOf(age));
        return age;
    }
}
