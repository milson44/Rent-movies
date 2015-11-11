package edu.javacourse.contact.entity;
import db.DbHelper;

import java.sql.ResultSet;
import java.text.Collator;
import java.util.Locale;
import java.sql.SQLException;

public class Film {
	// Идентификатор фильма
    private Long filmId;
    // Имя фильма
    private String filmName;
    // Признак
    private int type_id;
    // стоимость проката
    private int prokat;
    // количество кинотеатров, в которых прокат 
    private int countCinema;
    //наличие призов
    private boolean oskar;
    //продолжительность
    private int duration;
    private int count_of_cinemas;
   
	
    
    public Film() {}

    public Film(ResultSet rs) throws SQLException
    {
    	setFilmId(rs.getLong(1));
    	setFilmName(rs.getString(2));
    	setFilmType_id(rs.getInt(3));
    	setProkat(rs.getInt(4));
    	setCount_of_cinemas(rs.getInt(5));
    	setOskar(rs.getBoolean(6));
    	setDuration(rs.getInt(7));
    
    	
    }
    public Long getFilmId() {
        return filmId;
    }
    
    public int getCount_of_cinemas(){
	 return count_of_cinemas;
 }
 
    public void setCount_of_cinemas(int count_of_cinemas){
	 this.count_of_cinemas = count_of_cinemas;
}
    public int getFilmType_id(){
	 return type_id;
 }
    public String getType(){
	 try{
		 DbHelper dbHelper = new DbHelper("test", "dev", "dev");
		 dbHelper.connectToDB();
		 ResultSet rs = dbHelper.getResultExecuteQuery(
				 "select name from film_types " + 
		 "where type_id = " + getFilmType_id() + ";");
		 if(rs.next()){
			 return rs.getString(1);
		 }
		 return null;
		 } catch(Exception ex) {
			 System.out.println(ex.getMessage());
		 }
	 return null;
 }
	public void setFilmType_id(int type_id){
		this.type_id = type_id;
	}
	 
    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }
 
    public String getFilmName() {
        return filmName;
    }
 
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }
 
 
    public int getProkat() {
        return prokat;
    }
 
    public void setProkat(int prokat) {
        this.prokat = prokat;
    }
 
    public int getCountCinema() {
        return countCinema;
    }
 
    public void setCountCinema(int countCinema) {
        this.countCinema = countCinema;
    }
    
    public boolean getOskar() {
    	
        return oskar;
    }
 
    public void setOskar(boolean oskar) {
        this.oskar = oskar;
    }
    
    public int getDuration() {
        return duration;
    }
 
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    
    public String toString(){
    	return filmName;
    	
    }

    public int compareTo(Object obj) {
        Collator c = Collator.getInstance(new Locale("ru"));
        c.setStrength(Collator.PRIMARY);
        return c.compare(this.toString(), obj.toString());
    }
}
