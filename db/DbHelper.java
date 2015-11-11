package db;
import java.sql.*;
import java.util.logging.*;
import java.lang.String;


public class DbHelper {
	private static String URL_DATABASE = "jdbc:postgresql://localhost:2609/";
    private String db_name;
    private String db_user;
    private String db_password;
    private Connection connection;

    public DbHelper(String db_name, String db_user, String db_password) {
        this.db_name = db_name;
        this.db_user = db_user;
        this.db_password = db_password;
        this.connection = null;
    }

    public void connectToDB() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL_DATABASE + this.db_name, this.db_user, this.db_password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeConnectionToDB() {

        if (this.connection != null) { 
            try {
                this.connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ResultSet getResultExecuteQuery(String query) {
        try {
            Statement statement = this.connection.createStatement();
            return statement.executeQuery(query);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void createTable(String query) {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Таблица добавлена");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void createTrigger(String query) {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Триггер добавлен");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        DbHelper db_helper = new DbHelper("test", "dev", "dev");

        db_helper.connectToDB();
        
        db_helper.createTable("" +
                "CREATE TABLE IF NOT EXISTS film_types (" +
                "type_id bigserial PRIMARY KEY, " +
                "name character varying UNIQUE NOT NULL)");


        /**
         * Cоздание таблицы фильмов
         */
        db_helper.createTable(
                "CREATE TABLE IF NOT EXISTS films ( "                        +
                        "filmId             bigserial         PRIMARY KEY,"      +
                        "filmName       character varying  UNIQUE NOT NULL," +
                        "type_id        integer REFERENCES film_types,"      +
                        "prokat         integer            NOT NULL,"        +
                        "countCinema    integer            NOT NULL DEFAULT 0,"        +
                        "oskar          boolean            NOT NULL,"        +
                        "duration       integer            NOT NULL"         +
                        ")");

         /**
         * Создание таблицы кинотеатра
         */
        db_helper.createTable(
                "CREATE TABLE IF NOT EXISTS cinemas ( "                             +
                        "cinemaId                 bigserial          PRIMARY KEY,"        +
                        "cinemaName         character varying  UNIQUE NOT NULL,"    +
                        "howManyPeople      integer            NOT NULL,"           +
                        "FIOdirector        character varying  UNIQUE NOT NULL,"    +
                        "DateStartCinema    integer            NOT NULL,"           + 
                        "CountPlace1        integer            NOT NULL,"           +
                        "CountPlace2        integer            NOT NULL,"           +
                        "CountPlace3        integer          NOT NULL" + ")"           
                        
                //"CHECK (howManyPeople = CountPlace1" + "CountPlace2" + "CountPlace3" + ")" + ")"    
        		);

        /**
         * Создание таблицы проката
         */
        db_helper.createTable(
                "CREATE TABLE IF NOT EXISTS proprokats ( "                                    +
                        "proprokatId                   bigserial                       PRIMARY KEY,"   +
                        "film_id               integer REFERENCES films (filmId) ON DELETE SET NULL ON UPDATE CASCADE,"      +
                        "cinema_id             integer REFERENCES cinemas (cinemaId) ON DELETE SET NULL ON UPDATE CASCADE,"      +
                        "dateProkatStart      date                            NOT NULL,"      +
                        "dateProkatEnd        date                            NOT NULL,"      +
                        "CostPlace1           integer                         NOT NULL,"      +
                        "CostPlace2           integer                         NOT NULL,"      +
                        "CostPlace3           integer                         NOT NULL,"      +
                        "sborProkat           integer                         NOT NULL"       +                      
        		        ")");

    
    	
    	/**
    	* Создание триггера при добавлении записи в таблицу проката (увеличивает число кинотеатров в таблице фильмов)
    	*/ 
    	
    	db_helper.createTrigger(
                "CREATE FUNCTION increase_count_of_cinemas() RETURNS trigger AS " +
                        "$increase_count_of_cinemas$ " +
                        "BEGIN " +
                        "UPDATE films SET countCinema = countCinema + 1 WHERE filmId = NEW.film_id; " +
                        "RETURN NULL; " +
                        "END;" +
                        "$increase_count_of_cinemas$ " +
                        "language plpgsql;" +
                        "CREATE TRIGGER after_new_proprokats " +
                        "AFTER INSERT ON proprokats " +
                        "FOR EACH ROW EXECUTE PROCEDURE increase_count_of_cinemas();"
        );

       
    	/**
         * Создание триггера при удалении записи в таблицу проката (уменьшает число кинотеатров в таблице фильмов)
         */

        db_helper.createTable(
                "CREATE FUNCTION decrease_count_of_cinemas() RETURNS trigger AS " +
                        "$decrease_count_of_cinemas$ " +
                        "BEGIN " +
                        "UPDATE films SET countCinema = countCinema - 1 where filmId = OLD.film_id; " +
                        "RETURN NULL; " +
                        "END;" +
                        "$decrease_count_of_cinemas$ " +
                        "language plpgsql;" +
                        "CREATE TRIGGER before_delete_proprokats " +
                        "AFTER DELETE ON proprokats " +
                        "FOR EACH ROW EXECUTE PROCEDURE decrease_count_of_cinemas();"
        );
        
        
       }
}
    