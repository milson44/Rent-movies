package contact.view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import edu.javacourse.contact.entity.*;
import edu.javacourse.contact.controllers.*;
import contact.view.frames.*;

public class SearchCinemaDialog extends JDialog implements ActionListener{

	 private final static int D_HEIGHT = 300;
	    private final static int D_WIDTH = 700;
	    private final static int L_X = 10;
	    private final static int L_W = 120;
	    private final static int C_W = 500;
	    private boolean result = false;
	    private Collection<Cinema> cinemas = new ArrayList<Cinema>();
	    private JSpinner filmName=new JSpinner();
	    private JComboBox cinemaList;
	    private JSpinner dateProkatStart = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
	    
	    public SearchCinemaDialog() {

	        setTitle("Поиск по фильмам");
	        getContentPane().setLayout(null);

	        JLabel l = new JLabel("Кинотеатр:", JLabel.RIGHT);
	        l.setBounds(L_X, 30, L_W, 20);
	        getContentPane().add(l);

	        CinemaController pc = null;
	        Collection<Cinema> cinemas = new ArrayList<Cinema>();
	        try {
	            pc = new CinemaController();
	            cinemas = pc.getAllCinemas();
	            System.out.println("mrr");
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	        }
	        cinemaList = new JComboBox(new Vector<Cinema>(cinemas));
	        cinemaList.setBounds(L_X + L_W + 10, 30, C_W, 20);
	        getContentPane().add(cinemaList);
	        
	        JButton btnBack = new JButton("Назад");
	        btnBack.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent actionEvent) {
	                setVisible(false);
	            }
	        });
	        btnBack.setBounds(L_X + C_W + 10, 10, 160, 20);
	        getContentPane().add(btnBack);
	        
	        l = new JLabel("Выдать список фильмов отечественного производства, которые когда-либо шли в этом кинотеатре", JLabel.RIGHT);
	        l.setBounds(L_X, 50, C_W, 20);
	        getContentPane().add(l);

	        JButton btnSearch1 = new JButton("Найти");
	        btnSearch1.addActionListener(this);
	        btnSearch1.setName("SEARCH1");
	        btnSearch1.setBounds(L_X + C_W + 10, 50, 160, 20);
	        getContentPane().add(btnSearch1);

	        l = new JLabel("Найти кинотеатры, в которых в 2000 году шли только зарубежные фильмы", JLabel.RIGHT);
	        l.setBounds(L_X, 70, C_W, 20);
	        getContentPane().add(l);

	        JButton btnSearch2 = new JButton("Найти");
	        btnSearch2.addActionListener(this);
	        btnSearch2.setName("SEARCH2");
	        btnSearch2.setBounds(L_X + C_W + 10, 70, 160, 20);
	        getContentPane().add(btnSearch2);

	        l = new JLabel("Сумма реального сбора с проката фильмов за апрель 2000 года", JLabel.RIGHT);
	        l.setBounds(L_X, 90, C_W, 20);
	        getContentPane().add(l);

	        JButton btnSearch3 = new JButton("Найти");
	        btnSearch3.addActionListener(this);
	        btnSearch3.setName("SEARCH3");
	        btnSearch3.setBounds(L_X + C_W + 10, 90, 160, 20);
	        getContentPane().add(btnSearch3);
	        
	        

	        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	        setBounds(((int) d.getWidth() - SearchCinemaDialog.D_WIDTH) / 2, ((int) d.getHeight() - SearchCinemaDialog.D_HEIGHT) / 2,
	                SearchCinemaDialog.D_WIDTH, SearchCinemaDialog.D_HEIGHT);
	    }

	    public Collection<Cinema> getResult() {
	        return cinemas;
	    }

	    public void actionPerformed(ActionEvent e) {
	        JButton src = (JButton) e.getSource();
	        
	        if (src.getName().equals("SEARCH1")) {
	            try {
	               // Date date = ((SpinnerDateModel) dateProkatStart.getModel()).getDate();
	                //String year = date.toString();
	                //year = year.substring(year.length() - 4, year.length());
	                
	                CinemaController sc = new CinemaController();
	                cinemas = sc.getCinemasByQuery("SELECT * "+
	                								"FROM films "+
	                								"JOIN proprokats ON films.filmId = film_id AND cinema_id = proprokats.cinema_id AND type_id = '2';");
	            } catch (Exception ex) {
	                System.out.println(ex.getMessage());
	            }
	        }
	        if (src.getName().equals("SEARCH2")) {
	            try {
				//	Date date = ((SpinnerDateModel) dateProkatStart.getModel()).getDate();
	               // String year = date.toString();
	               // year = year.substring(year.length() - 4, year.length());
	                //final int search_year = 2000;
	                CinemaController sc = new CinemaController();
	                cinemas = sc.getCinemasByQuery(""+
	                		"SELECT * "+
	                		"FROM cinemas "+
	                		  "JOIN proprokats "+
	                		    "ON cinemas.cinemaId = cinema_id AND date_part('year', dateProkatStart) <= 2000 AND date_part('year', dateProkatEnd) >= 2000 "+
	                		  "JOIN films ON films.filmId = film_id AND type_id = '1' "+
	                		"EXCEPT "+
	                		"SELECT * "+
	                		"FROM cinemas "+
	                		 " JOIN proprokats "+
	                		    "ON cinemas.cinemaId = cinema_id AND date_part('year', dateProkatStart) <= 2000 AND date_part('year', dateProkatEnd) >= 2000 "+
	                		  "JOIN films ON films.filmId = film_id AND type_id = '2'");
	            
	            }
	            catch(Exception ex) {
	            	System.out.println(ex.getMessage());
	            }
	        }

	        if (src.getName().equals("SEARCH3")) {
	            try {
	                //Date date = ((SpinnerDateModel) dateProkatStart.getModel()).getDate();
	                //String year = date.toString();
//	                year = year.substring(year.length() - 4, year.length());
	                
	                CinemaController sc = new CinemaController();
	                cinemas = sc.getCinemasByQuery("SELECT cinemas.cinemaName, sum(sborProkat) AS sborProkat "+
	                		"FROM cinemas "+
	                		"JOIN proprokats ON cinemas.cinemaId = cinema_id "+
	                		"WHERE dateProkatStart <= '2000-04-30' AND dateProkatEnd >= '2000-04-01' "+
	                		"GROUP BY proprokats.proprokatId, cinemas.cinemaId;");
	            } catch (Exception ex) {
	                System.out.println(ex.getMessage());
	            }
	        }
	        setVisible(false);
	    }
}
