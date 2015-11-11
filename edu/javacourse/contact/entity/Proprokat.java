package edu.javacourse.contact.entity;

import db.DbHelper;
import edu.javacourse.contact.controllers.ProprokatController;
import edu.javacourse.contact.entity.FilmType;
import java.text.DateFormat;
import java.util.*;
import java.sql.ResultSet;
import java.text.Collator;
import java.util.Locale;
import java.sql.SQLException;

public class Proprokat implements Comparable {

	// Идентификатор кинотеатра
    private Long cinema_id;
    // Идентификатор кинотеатра
    private Long film_id;
    // ИД проката
    private Long proprokatId;
    // Дата начала проката
	private Date dateProkatStart;
	// Дата конца проката
    private Date dateProkatEnd;
    //стоимость мест категории 1
    private int CostPlace1;
    //стоимость мест категории 2
    private int CostPlace2;
    //стоимость мест категории 3
    private int CostPlace3;
    //сбор от проката
    private int sborProkat;
 
    public Proprokat() {}
    
    public Proprokat(ResultSet rs) throws SQLException {
    	setProprokatId(rs.getLong(1));
        setCinemaId(rs.getLong(2));
        setFilmId(rs.getLong(3));
        setDateProkatStart(rs.getDate(4));
        setDateProkatEnd(rs.getDate(5));
        setCostPlace1(rs.getInt(6));
        setCostPlace2(rs.getInt(7));
        setCostPlace3(rs.getInt(8));
        setSborProkat(rs.getInt(9));
    }
    public Long getProprokatId() {
        return proprokatId;
    }
 
    public void setProprokatId(Long proprokatId) {
        this.proprokatId = proprokatId;
    }
    
 
    public Long getCinemaId() {
        return cinema_id;
    }
 
    public void setCinemaId(Long cinema_id) {
        this.cinema_id = cinema_id;
    }
    public Long getFilmId() {
        return film_id;
    }
 
    public void setFilmId(Long film_id) {
        this.film_id = film_id;
    }
    public Date getDateProkatStart() {
        return dateProkatStart;
    }
 
    public void setDateProkatStart(Date dateProkatStart) {
        this.dateProkatStart = dateProkatStart;
    }
 
    public Date getDateProkatEnd() {
        return dateProkatEnd;
    }
 
    public void setDateProkatEnd(Date dateProkatEnd) {
        this.dateProkatEnd = dateProkatEnd;
    }
 
    public int getCostPlace1() {
        return CostPlace1;
    }
 
    public void setCostPlace1(int CostPlace1) {
        this.CostPlace1 = CostPlace1;
    }
 
    public int getCostPlace2() {
        return CostPlace2;
    }
 
    public void setCostPlace2(int CostPlace2) {
        this.CostPlace2 = CostPlace2;
    }
 
    public int getCostPlace3() {
        return CostPlace3;
    }
 
    public void setCostPlace3(int CostPlace3) {
        this.CostPlace3 = CostPlace3;
    }
    public int getSborProkat() {
        return sborProkat;
    }
 
    public void setSborProkat(int sborProkat) {
        this.sborProkat = sborProkat;
    }   
    
    //получение данных фильма

    public String getFilmsName() {

        try {
            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
            dbHelper.connectToDB();
            String query = "select filmName from films where filmId = " + getFilmId() + ";";
            ResultSet rs = dbHelper.getResultExecuteQuery(query);
            if (rs.next()) {
                String filmName = rs.getString(1);
                return filmName;
            }
            return null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }



    //получение данных кинотеатра

    public String getCinemaName() {
        try {
            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
            dbHelper.connectToDB();
            String query = "select cinemaName from cinemas where cinemaId =" + getCinemaId() + ";";
            ResultSet rs = dbHelper.getResultExecuteQuery(query);
            if (rs.next()) {
                String cinemaName = rs.getString(1);
                return cinemaName;
            }
            return null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public String toString() {
        return film_id + " " + cinema_id + " " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateProkatStart) + " " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateProkatEnd) + " " + CostPlace1 + " " + CostPlace2 + " " + CostPlace3 + " " + sborProkat ;
    }
    public int compareTo(Object obj) {
        Collator c = Collator.getInstance(new Locale("ru"));
        c.setStrength(Collator.PRIMARY);
        return c.compare(this.toString(), obj.toString());
    }

    
}
