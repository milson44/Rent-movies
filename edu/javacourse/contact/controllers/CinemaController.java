package edu.javacourse.contact.controllers;

import edu.javacourse.contact.entity.Cinema;
import edu.javacourse.contact.entity.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class CinemaController {
	
	  private static String URL_DATABASE = "jdbc:postgresql://localhost:2609/";
	    private static CinemaController instance;
	    private Connection connection;
	    private static String DATABASE_NAME = "test";
	    private static String DATABASE_USER = "dev";
	    private static String DATBASE_PASSWORD = "dev";

	    public CinemaController() throws Exception {
	        try {
	            Class.forName("org.postgresql.Driver");
	            System.out.println("Драйвер подключен");
	            connection = DriverManager.getConnection(URL_DATABASE + DATABASE_NAME, DATABASE_USER, DATBASE_PASSWORD);
	            System.out.println("Соединение установлено");
	        } catch (ClassNotFoundException e) {
	            throw new Exception(e);
	        } catch (SQLException e) {
	            throw new Exception(e);
	        }
	    }

	    public static synchronized CinemaController getInstance() throws Exception{
	        if (instance == null) {
	            instance = new CinemaController();
	        }
	        return instance;
	    }


	    public void updateCinema(Cinema cinema) throws SQLException {
	        PreparedStatement stmt = null;
	        try {
	            stmt = connection.prepareStatement(
	                    "UPDATE cinemas SET " +
	                            "cinemaName=?, HowManyPeople=?, FIOdirector=?, DateStartCinema=?, CountPlace1=?, CountPlace2=?, CountPlace3=? " +
	                            "WHERE cinemaId=?");
	            stmt.setString(1, cinema.getCinemaName());
	            stmt.setInt(2, cinema.getHowManyPeople());
	            stmt.setString(3, cinema.getFIOdirector());
	            stmt.setInt(4, cinema.getDateStartCinema());
	            stmt.setInt(5, cinema.getCountPlace1());
	            stmt.setInt(6, cinema.getCountPlace2());
	            stmt.setInt(7, cinema.getCountPlace3());
	            stmt.setLong(8, cinema.getCinemaId());
	            stmt.execute();
	        } finally {
	            if (stmt != null) {
	                stmt.close();
	            }
	        }
	    }

	    public void insertCinema(Cinema cinema) throws SQLException {
	        PreparedStatement stmt = null;
	        try {

	            stmt = connection.prepareStatement(
	                    "INSERT INTO cinemas " +
	                            "(cinemaName, HowManyPeople, FIOdirector, DateStartCinema, CountPlace1, CountPlace2, CountPlace3)" +
	                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
	            stmt.setString(1, cinema.getCinemaName());
	            stmt.setInt(2, cinema.getHowManyPeople());
	            stmt.setString(3, cinema.getFIOdirector());
	            stmt.setInt(4, cinema.getDateStartCinema());
	            stmt.setInt(5, cinema.getCountPlace1());
	            stmt.setInt(6, cinema.getCountPlace2());
	            stmt.setInt(7, cinema.getCountPlace3());
	            stmt.execute();
	            if (stmt != null) {
	                stmt.close();
	            }
	        } finally {

	        }

	    }

	    public void deleteCinema(Cinema cinema) throws SQLException {
	        PreparedStatement stmt = null;
	        try {
	            stmt = connection.prepareStatement(
	                    "DELETE FROM cinemas WHERE cinemaId=?");
	            stmt.setLong(1, cinema.getCinemaId());
	            stmt.execute();
	        } finally {
	            if (stmt != null) {
	                stmt.close();
	            }
	        }
	    }

	    public Collection<Cinema> getAllCinemas() throws SQLException {
	        Collection<Cinema> cinemas = new ArrayList<>();
	        Statement stmt = null;
	        ResultSet rs = null;
	        try {
	            stmt = connection.createStatement();
	            rs = stmt.executeQuery("SELECT* FROM cinemas;");
	            while (rs.next()) {
	                Cinema cin = new Cinema(rs);
	                cinemas.add(cin);
	            }
	        } finally {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        }

	        return cinemas;
	    }
	    
	    public Collection<Cinema> getCinemasByQuery(String query) throws SQLException {
	        Collection<Cinema> cinemas = new ArrayList<>();
	        Statement stmt = null;
	        ResultSet rs = null;
	        try {
	            stmt = connection.createStatement();
	            rs = stmt.executeQuery(query);
	            while (rs.next()) {
	                Cinema sc = new Cinema(rs);
	                cinemas.add(sc);
	            }
	        } finally {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        }
	        return cinemas;
	    }

}
