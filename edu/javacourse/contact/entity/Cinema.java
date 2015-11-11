package edu.javacourse.contact.entity;
import db.DbHelper;
import edu.javacourse.contact.entity.Film;

import java.sql.ResultSet;
import java.text.Collator;
import java.util.Locale;
import java.sql.SQLException;

public class Cinema implements Comparable {
	// Идентификатор контакта
    private Long cinemaId;
    // Имя кинотеатра
    private String cinemaName;
    // вместимость
    private int howManyPeople;
    //ФИО директора
    private String FIOdirector;
    //дата пуска в эксплуатацию
    private int DateStartCinema;
    //число мест категории 1
    private int CountPlace1;
    //число мест категории 2
    private int CountPlace2;
    //число мест категории 3
    private int CountPlace3;
 
    public Cinema() {}
 
    public Cinema(ResultSet rs) throws SQLException {
        setCinemaId(rs.getLong(1));
        setCinemaName(rs.getString(2));
        setHowManyPeople(rs.getInt(3));
        setFIOdirector(rs.getString(4));
        setDateStartCinema(rs.getInt(5));
        setCountPlace1(rs.getInt(6));
        setCountPlace2(rs.getInt(7));
        setCountPlace3(rs.getInt(8));
    }


	public Long getCinemaId() {
        return cinemaId;
    }
 
    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }
 
    public String getCinemaName() {
        return cinemaName;
    }
 
    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
 
    public int getHowManyPeople() {
        return howManyPeople;
    }
 
    public void setHowManyPeople(int howManyPeople) {
        this.howManyPeople = howManyPeople;
    }
 
    public String getFIOdirector() {
        return FIOdirector;
    }
    
    public void setFIOdirector(String FIOdirector) {
        this.FIOdirector=FIOdirector;
    }
     
    public int getDateStartCinema() {
    	 return DateStartCinema;
     }
     
    public void setDateStartCinema(int DateStartCinema){
    	 this.DateStartCinema=DateStartCinema;
     }
    
    public int getCountPlace1() {
        return CountPlace1;
    }
    
    public void setCountPlace1(int CountPlace1) {
        this.CountPlace1 = CountPlace1;
    }
 
    public int getCountPlace2() {
        return CountPlace2;
    }
 
    public void setCountPlace2(int CountPlace2) {
        this.CountPlace2 = CountPlace2;
    }
 
    public int getCountPlace3() {
        return CountPlace3;
    }
 
    public void setCountPlace3(int CountPlace3) {
        this.CountPlace3 = CountPlace3;
    }
    
    public int getCountCinemas(final String type) {
        try {
            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
            dbHelper.connectToDB();
            ResultSet rs = dbHelper.getResultExecuteQuery(
                    "select count(distinct(filmId)) from proprokats " +
                    "where cinemaId = " + getCinemaId() +
                    " and filmId in (select id from films where film_type_id = " +
                            "(select id from film_types where name = '" + type +"'));");
             if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }
   

   /* @Override
    public String toString() {
        return "Cinema{" + "cinemaId=" + cinemaId + ", cinemaName=" + cinemaName + ", HowManyPeople=" + howManyPeople + ", FIOdirector=" + FIOdirector + ", CountPlace1=" + CountPlace1 + ", CountPlace2="+CountPlace2+", CountPlace3="+CountPlace3+ '}';
    }*/
    public String toString() {
        return cinemaName;
    }

    public int compareTo(Object obj) {
        Collator c = Collator.getInstance(new Locale("ru"));
        c.setStrength(Collator.PRIMARY);
        return c.compare(this.toString(), obj.toString());
    }
    
}
