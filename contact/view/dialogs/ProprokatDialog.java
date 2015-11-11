package contact.view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Date;

import edu.javacourse.contact.controllers.CinemaController;
import edu.javacourse.contact.controllers.FilmController;
import edu.javacourse.contact.controllers.ProprokatController;
import edu.javacourse.contact.entity.Cinema;
import edu.javacourse.contact.entity.Film;
import edu.javacourse.contact.entity.Proprokat;
import contact.view.frames.ProprokatFrame;


public class ProprokatDialog extends JDialog implements ActionListener {

	private static final int D_HEIGHT = 250;
    private final static int D_WIDTH = 650;
    private final static int L_X = 10;
    private final static int L_W = 200;
    private final static int C_W = 200;
    private ProprokatFrame owner;
    private boolean result = false;

    private long proprokatId = 0;
    private JComboBox filmList;
    private JComboBox cinemaList;
    private JSpinner dateProkatStart = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
    private JSpinner dateProkatEnd = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
    private JTextField costPlace1 = new JTextField();
    private JTextField costPlace2 = new JTextField();
    private JTextField costPlace3 = new JTextField();
    private JTextField sborProkat = new JTextField();
    

    public ProprokatDialog(boolean newProprokat, ProprokatFrame owner) {
        this.owner = owner;
        setTitle("Редактирование данных проката");
        getContentPane().setLayout(new FlowLayout());

        getContentPane().setLayout(null);

        JLabel l = new JLabel("Фильм:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);

        FilmController fi = null;
        Collection<Film> films = new ArrayList<Film>();
        try {
            fi = new FilmController();
            films = fi.getAllFilms();
            System.out.println("lol");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        filmList = new JComboBox(new Vector<Film>(films));
        filmList.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(filmList);

        l = new JLabel("Кинотеатр:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);

        CinemaController pc = null;
        Collection<Cinema> cinemas = new ArrayList<Cinema>();
        try {
            pc = new CinemaController();
            cinemas = pc.getAllCinemas();
            System.out.println("wow");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        cinemaList = new JComboBox(new Vector<Cinema>(cinemas));
        cinemaList.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(cinemaList);

        l = new JLabel("Дата начала проката:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        dateProkatStart.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(dateProkatStart);

        l = new JLabel("Дата конца проката:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        dateProkatEnd.setBounds(L_X + L_W + 10, 70, C_W, 20);
        getContentPane().add(dateProkatEnd);

        
        l = new JLabel("Цена места категории 1:", JLabel.RIGHT);
        l.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(l);
        costPlace1.setBounds(L_X + L_W + 10, 90, C_W, 20);
        getContentPane().add(costPlace1);
        
        l = new JLabel("Цена места категории 2:", JLabel.RIGHT);
        l.setBounds(L_X, 110, L_W, 20);
        getContentPane().add(l);
        costPlace2.setBounds(L_X + L_W + 10, 110, C_W, 20);
        getContentPane().add(costPlace2);
        
        l = new JLabel("Цена места категории 3 :", JLabel.RIGHT);
        l.setBounds(L_X, 130, L_W, 20);
        getContentPane().add(l);
        costPlace3.setBounds(L_X + L_W + 10, 130, C_W, 20);
        getContentPane().add(costPlace3);
        
        l = new JLabel("Реальный сбор от проката :", JLabel.RIGHT);
        l.setBounds(L_X, 150, L_W, 20);
        getContentPane().add(l);
        sborProkat.setBounds(L_X + L_W + 10, 150, C_W, 20);
        getContentPane().add(sborProkat);
        
        

        JButton btnCancel = new JButton("Отмена");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 150, 25);
        getContentPane().add(btnCancel);

        if (newProprokat) {
            JButton btnNew = new JButton("Сохранить");
            btnNew.setName("New");
            btnNew.addActionListener(this);
            btnNew.setBounds(L_X + L_W + C_W + 10 + 50, 10, 150, 25);
            getContentPane().add(btnNew);
        } else {
            JButton btnOk = new JButton("Сохранить");
            btnOk.setName("OK");
            btnOk.addActionListener(this);
            btnOk.setBounds(L_X + L_W + C_W + 10 + 50, 10, 150, 25);
            getContentPane().add(btnOk);
        }

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - ProprokatDialog.D_WIDTH) / 2, ((int) d.getHeight() - ProprokatDialog.D_HEIGHT) / 2,
        		ProprokatDialog.D_WIDTH, ProprokatDialog.D_HEIGHT);
    }

    public void setProprokat(Proprokat s) {
    	proprokatId = s.getProprokatId();
        costPlace1.setText(String.valueOf(s.getCostPlace1()));
        costPlace2.setText(String.valueOf(s.getCostPlace2()));
        costPlace3.setText(String.valueOf(s.getCostPlace3()));
        sborProkat.setText(String.valueOf(s.getSborProkat()));
        dateProkatStart.getModel().setValue(s.getDateProkatStart());
        dateProkatEnd.getModel().setValue(s.getDateProkatEnd());
        for (int i = 0; i < filmList.getModel().getSize(); i++) {
            Film sb = (Film) filmList.getModel().getElementAt(i);
            if (sb.getFilmId() == s.getFilmId()) {
                filmList.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < cinemaList.getModel().getSize(); i++) {
            Cinema pb = (Cinema) cinemaList.getModel().getElementAt(i);
            if (pb.getCinemaId() == s.getCinemaId()) {
                cinemaList.setSelectedIndex(i);
                break;
            }
        }
    }

    public Proprokat getProprokat() {
    	Proprokat s = new Proprokat();
        s.setProprokatId(proprokatId);
        s.setSborProkat(Integer.parseInt(sborProkat.getText()));
        s.setCostPlace1(Integer.parseInt(costPlace1.getText()));
        s.setCostPlace2(Integer.parseInt(costPlace2.getText()));
        s.setCostPlace3(Integer.parseInt(costPlace3.getText()));
        Date d = ((SpinnerDateModel) dateProkatStart.getModel()).getDate();
        s.setDateProkatStart(new java.sql.Date(d.getTime()));
        Date r = ((SpinnerDateModel) dateProkatEnd.getModel()).getDate();
        s.setDateProkatEnd(new java.sql.Date(r.getTime()));
        s.setFilmId(((Film) filmList.getSelectedItem()).getFilmId());
        s.setCinemaId(((Cinema) cinemaList.getSelectedItem()).getCinemaId());
        return s;
    }

    public boolean getResult() {
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("New")) {
            result = true;
            try {
            	ProprokatController.getInstance().insertProprokat(getProprokat());
                owner.reloadProprokat();
                sborProkat.setText("");
            } catch (Exception sql_e) {
                JOptionPane.showMessageDialog(this, sql_e.getMessage());
            }
            return;
        }
        if (src.getName().equals("OK")) {
            result = true;
        }
        if (src.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }
}
