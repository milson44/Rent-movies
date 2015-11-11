package edu.javacourse.contact.controllers;
import edu.javacourse.contact.controllers.*;
import edu.javacourse.contact.entity.FilmType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class FilmTypesController {

	private static String URL_DATABASE = "jdbc:postgresql://localhost:2609/";
	private static FilmTypesController instance;
	private Connection connection;
	private static String DATABASE_NAME = "test";
	private static String DATABASE_USER = "dev";
	private static String DATBASE_PASSWORD = "dev";
	
	public FilmTypesController() throws Exception {
	try {
	Class.forName("org.postgresql.Driver");
	connection = DriverManager.getConnection(URL_DATABASE + DATABASE_NAME, DATABASE_USER, DATBASE_PASSWORD);
	} catch (ClassNotFoundException e) {
	throw new Exception(e);
	} catch (SQLException e) {
	throw new Exception(e);
	}
	}
	
	public static synchronized FilmTypesController getInstance() throws Exception{
	if (instance == null) {
	instance = new FilmTypesController();
	}
	return instance;
	}
	
	public void insertFilmType(FilmType film_type) throws SQLException {
	PreparedStatement stmt = null;
	try {
	
	stmt = connection.prepareStatement(
	"INSERT INTO film_types " +
	"(name)" +
	"VALUES (?)");
	stmt.setString(1, film_type.getName());
	stmt.execute();
	if (stmt != null) {
	stmt.close();
	}
	} finally {
	if (stmt != null) {
	stmt.close();
	}
	}
	
	}
	
	public void deleteFilmType(FilmType film_type) throws SQLException {
	PreparedStatement stmt = null;
	try {
	stmt = connection.prepareStatement(
	"DELETE FROM film_types WHERE type_id=?");
	stmt.setInt(1, film_type.getFilmType_id());
	stmt.execute();
	} finally {
	if (stmt != null) {
	stmt.close();
	}
	}
	}
	
	public Collection<FilmType> getAllFilmTypes() throws SQLException{
	Collection<FilmType> film_types = new ArrayList<>();
	Statement stmt = null;
	ResultSet rs = null;
	try {
	stmt = connection.createStatement();
	rs = stmt.executeQuery("SELECT * FROM film_types;");
	while (rs.next()) {
		FilmType t = new FilmType(rs);
		film_types.add(t);
	System.out.println(t.getFilmType_id() + " " + t.getName());
						}
		} finally {
			if (rs != null) {
				rs.close();
							}
		if (stmt != null) {
			stmt.close();
						}
				}
	
	return film_types;
		}
	}
