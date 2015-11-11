package contact.view.dialogs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import edu.javacourse.contact.controllers.CinemaController;
import edu.javacourse.contact.entity.Cinema;
import contact.view.frames.CinemaFrame;


public class CinemaDialog extends JDialog implements ActionListener{
	
	private static final int D_HEIGHT = 250;
    private final static int D_WIDTH = 600;
    private final static int L_X = 10;
    private final static int L_W = 160;
    private final static int C_W = 150;

    private CinemaFrame owner;

    private boolean result = false;
    private long cinema_id = 0;
    private JTextField cinemaName = new JTextField();
    private JTextField howManyPeople = new JTextField();
    private JTextField FIOdirector = new JTextField();
    private JTextField dateStartCinema = new JTextField();
    private JTextField countPlace1 = new JTextField();
    private JTextField countPlace2 = new JTextField();
    private JTextField countPlace3 = new JTextField();

    public CinemaDialog(boolean newCinema, CinemaFrame owner) {
        this.owner = owner;
        setTitle("Редактирование данных кинотеатра");
        getContentPane().setLayout(new FlowLayout());

        getContentPane().setLayout(null);

        JLabel l = new JLabel("Название кинотетра:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        cinemaName.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(cinemaName);

        l = new JLabel("Вместимость:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        howManyPeople.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(howManyPeople);

        l = new JLabel("ФИО директора:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        FIOdirector.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(FIOdirector);

        l = new JLabel("Дата пуска в эксплуатацию:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        dateStartCinema.setBounds(L_X + L_W + 10, 70, C_W, 20);
        getContentPane().add(dateStartCinema);

        l = new JLabel("Число мест категории 1:", JLabel.RIGHT);
        l.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(l);
        countPlace1.setBounds(L_X + L_W + 10, 90, C_W, 20);
        getContentPane().add(countPlace1);

        l = new JLabel("Число мест категории 2:", JLabel.RIGHT);
        l.setBounds(L_X, 110, L_W, 20);
        getContentPane().add(l);
        countPlace2.setBounds(L_X + L_W + 10, 110, C_W, 20);
        getContentPane().add(countPlace2);

        l = new JLabel("Число мест категории 3:", JLabel.RIGHT);
        l.setBounds(L_X, 130, L_W, 20);
        getContentPane().add(l);
        countPlace3.setBounds(L_X + L_W + 10, 130, C_W, 20);
        getContentPane().add(countPlace3);
        
        
        JButton btnCancel = new JButton("Отмена");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 150, 25);
        getContentPane().add(btnCancel);

        if (newCinema) {
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
        setBounds(((int) d.getWidth() - CinemaDialog.D_WIDTH) / 2, ((int) d.getHeight() - CinemaDialog.D_HEIGHT) / 2,
        		CinemaDialog.D_WIDTH, CinemaDialog.D_HEIGHT);
    }

    public void setCinema(Cinema p) {
    	cinema_id = p.getCinemaId();
        cinemaName.setText(p.getCinemaName());
        howManyPeople.setText(String.valueOf(p.getHowManyPeople()));
        FIOdirector.setText(p.getFIOdirector());
        dateStartCinema.setText(String.valueOf(p.getDateStartCinema()));
        countPlace1.setText(String.valueOf(p.getCountPlace1()));
        countPlace2.setText(String.valueOf(p.getCountPlace2()));
        countPlace3.setText(String.valueOf(p.getCountPlace3()));
    }

    public Cinema getCinema() {
    	Cinema p = new Cinema();
        p.setCinemaId(cinema_id);
        p.setCinemaName(cinemaName.getText());
        p.setHowManyPeople(Integer.parseInt(howManyPeople.getText()));
        p.setCountPlace1(Integer.parseInt(countPlace1.getText()));
        p.setCountPlace2(Integer.parseInt(countPlace2.getText()));
        p.setCountPlace3(Integer.parseInt(countPlace3.getText()));
        p.setFIOdirector(FIOdirector.getText());
        p.setDateStartCinema(Integer.parseInt(dateStartCinema.getText()));
        return p;
    }

    public boolean getResult() {
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("New")) {
            result = true;
            try {
                CinemaController.getInstance().insertCinema(getCinema());
                owner.reloadCinema();
                cinemaName.setText("");
                howManyPeople.setText("");
                countPlace1.setText("");
                countPlace2.setText("");
                countPlace3.setText("");
                FIOdirector.setText("");
                dateStartCinema.setText("");
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
