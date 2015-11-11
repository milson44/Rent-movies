package edu.javacourse.contact.controllers;

import edu.javacourse.contact.entity.Proprokat;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class ProprokatController {

	private static String URL_DATABASE = "jdbc:postgresql://localhost:2609/";
    private static ProprokatController instance;
    private Connection connection;
    private static String DATABASE_NAME = "test";
    private static String DATABASE_USER = "dev";
    private static String DATBASE_PASSWORD = "dev";

    public ProprokatController() throws Exception {
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

    public static synchronized ProprokatController getInstance() throws Exception{
        if (instance == null) {
            instance = new ProprokatController();
        }
        return instance;
    }
    


    public void updateProprokat(Proprokat proprokat) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE proprokats SET " +
                            "Film_id=?, Cinema_id=?, DateProkatStart=?, DateProkatEnd=?, CostPlace1=?, CostPlace2=?, CostPlace3=?, SborProkat=?" +
                            "WHERE proprokatId=?");
            stmt.setLong(1, proprokat.getFilmId());
            stmt.setLong(2, proprokat.getCinemaId());
            stmt.setDate(3, new Date(proprokat.getDateProkatStart().getTime()));
            stmt.setDate(4, new Date(proprokat.getDateProkatEnd().getTime()));
            stmt.setInt(5, proprokat.getCostPlace1());
            stmt.setInt(6, proprokat.getCostPlace2());
            stmt.setInt(7, proprokat.getCostPlace3());
            stmt.setInt(8, proprokat.getSborProkat());
            stmt.setLong(9, proprokat.getProprokatId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void insertProprokat(Proprokat proprokat) throws SQLException {
        PreparedStatement stmt = null;
        try {

            stmt = connection.prepareStatement(
                    "INSERT INTO proprokats" +
                            "(Film_Id, Cinema_Id, DateProkatStart, DateProkatEnd, CostPlace1, CostPlace2, CostPlace3, SborProkat)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setLong(1, proprokat.getFilmId());
            stmt.setLong(2, proprokat.getCinemaId());
            stmt.setDate(3, new Date(proprokat.getDateProkatStart().getTime()));
            stmt.setDate(4, new Date(proprokat.getDateProkatEnd().getTime()));
            stmt.setInt(5, proprokat.getCostPlace1());
            stmt.setInt(6, proprokat.getCostPlace2());
            stmt.setInt(7, proprokat.getCostPlace3());
            stmt.setInt(8, proprokat.getSborProkat());
            stmt.execute();
            if (stmt != null) {
                stmt.close();
            }
        } finally {

        }

    }

    public void deleteProprokat(Proprokat proprokat) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "DELETE FROM proprokats WHERE proprokatId=?");
            stmt.setLong(1, proprokat.getProprokatId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public Collection<Proprokat> getAllProprokats() throws SQLException {
        Collection<Proprokat> proprokats = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM proprokats;");
            while (rs.next()) {
                Proprokat pro = new Proprokat(rs);
                proprokats.add(pro);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return proprokats;
    }
}
