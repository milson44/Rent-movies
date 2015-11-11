package edu.javacourse.contact.entity;
import db.DbHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Locale;

public class FilmType implements Comparable {

	private int type_id;
	private String name;
	
	public FilmType() {}
	public FilmType(ResultSet rs) throws SQLException {
	setFilmType_id(rs.getInt(1));
	setName(rs.getString(2));
	}
	public void setFilmType_id(int type_id) {
	this.type_id = type_id;
	}
	
	public int getFilmType_id() {
	return type_id;
	}
	
	public void setName(String name) {
	this.name = name;
	}
	
	public String getName() {
	return name;
	}
	
	public String toString() {
	return name;
	}
	
	public int compareTo(Object obj) {
	Collator c = Collator.getInstance(new Locale("ru"));
	c.setStrength(Collator.PRIMARY);
	return c.compare(this.toString(), obj.toString());
	}
}
