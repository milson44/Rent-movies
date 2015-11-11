package edu.javacourse.contact.controllers;

import edu.javacourse.contact.entity.Film;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class FilmController {
	private static String URL_DATABASE = "jdbc:postgresql://localhost:2609/";
    private static FilmController instance;
    private Connection connection;
    private static String DATABASE_NAME = "test";
    private static String DATABASE_USER = "dev";
    private static String DATBASE_PASSWORD = "dev";

    public FilmController() throws Exception {
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

    public static synchronized FilmController getInstance() throws Exception {
        if (instance == null) {
            instance = new FilmController();
        }
        return instance;
    }


    public void updateFilm(Film film) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE films SET " +
                            "FilmName=?, type_id=?, Prokat=?, CountCinema=?, Oskar=?, Duration=? " +
                            "WHERE filmId=?");
            stmt.setString(1, film.getFilmName());
            stmt.setInt(2, film.getFilmType_id());
            stmt.setInt(3, film.getProkat());
            stmt.setInt(4, film.getCountCinema());
            stmt.setBoolean(5, film.getOskar());
            stmt.setInt(6, film.getDuration());
            stmt.setLong(7, film.getFilmId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void insertFilm(Film film) throws SQLException {
        PreparedStatement stmt = null;
        try {

            stmt = connection.prepareStatement(
                    "INSERT INTO films " +
                            "(FilmName, type_id, Prokat, CountCinema, Oskar, Duration)" +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, film.getFilmName());
            stmt.setInt(2, film.getFilmType_id());
            stmt.setInt(3, film.getProkat());
            stmt.setInt(4, film.getCountCinema());
            stmt.setBoolean(5, film.getOskar());
            stmt.setInt(6, film.getDuration());
            stmt.execute();
            if (stmt != null) {
                stmt.close();
            }
        } finally {

        }

    }

    public void deleteFilm(Film film) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "DELETE FROM films WHERE filmId=?");
            stmt.setLong(1, film.getFilmId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public Collection<Film> getAllFilms() throws SQLException {
        Collection<Film> films = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM films;");
            while (rs.next()) {
                Film fi = new Film(rs);
                films.add(fi);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return films;
    }
    
   public Collection<Film> getFilmsByQuery(String query) throws SQLException {
        Collection<Film> films = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Film sc = new Film(rs);
                films.add(sc);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return films;
    }

}
