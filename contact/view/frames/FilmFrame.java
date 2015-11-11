package contact.view.frames;
	/*
* класс окна для вывода информации о фильмах
* таблица фильмов
* меню перехода к другим окнам и поиск по фильмам
* кнопки добавления, удаления и редактирования фильмов
*/
import java.sql.SQLException;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.javacourse.contact.entity.Film;
import edu.javacourse.contact.controllers.FilmController;
import contact.view.dialogs.*;
import contact.view.tables.FilmTableModel;


public class FilmFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {
	
	private FilmController sc = new FilmController();
    private JTable filmsList;

    private static final String INSERT_SC = "insertFilm";
    private static final String DELETE_SC = "deleteFilm";
    private static final String UPDATE_SC = "updateFilm";

    public FilmFrame() throws Exception {

        getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Меню");

        // переход к окну проката
        JMenuItem menuItem1 = new JMenuItem("Прокат");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ProprokatFrame s = new ProprokatFrame();
                    s.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    s.setVisible(true);
                    s.reloadProprokat();
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        menu.add(menuItem1);

        // переход к окну кинотеатров
        JMenuItem menuItem2 = new JMenuItem("Кинотеатры");
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    CinemaFrame p = new CinemaFrame();
                    p.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    p.setVisible(true);
                    p.reloadCinema();
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        menu.add(menuItem2);
        
        //переход к окну типу фильмов
        JMenuItem menuItem3 = new JMenuItem("Типы фильмов");
         menuItem3.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent actionEvent) {
                 Thread t= new Thread(){
                 	public void run(){
                 		FilmTypeDialog ptd = new FilmTypeDialog();
                 		ptd.setModal(true);
                 		ptd.setVisible(true);
                 		ptd.getResult();
                 	}
                 };
                 t.start();
             }
         });
             	
         menu.add(menuItem3);

        
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JPanel bot = new JPanel();
        bot.setLayout(new BorderLayout());
        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        right.add(new JLabel("Фильмы:"), BorderLayout.NORTH);
       filmsList = new JTable(1, 6);
        right.add(new JScrollPane(filmsList), BorderLayout.CENTER);

        JButton btnAddSt = new JButton("Добавить");
        btnAddSt.setName(INSERT_SC);
        btnAddSt.addActionListener(this);
        JButton btnUpdSt = new JButton("Исправить");
        btnUpdSt.setName(UPDATE_SC);
        btnUpdSt.addActionListener(this);
        JButton btnDelSt = new JButton("Удалить");
        btnDelSt.setName(DELETE_SC);
        btnDelSt.addActionListener(this);
        JPanel pnlBtnSt = new JPanel();
        pnlBtnSt.setLayout(new GridLayout(1, 6));
        pnlBtnSt.add(btnAddSt);
        pnlBtnSt.add(btnUpdSt);
        pnlBtnSt.add(btnDelSt);
        right.add(pnlBtnSt, BorderLayout.SOUTH);
        bot.add(right, BorderLayout.CENTER);
        getContentPane().add(bot, BorderLayout.CENTER);
        setBounds(100, 100, 1100, 500);
    }

    //обработчик событий
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(INSERT_SC)) {
                insertFilm();
            }
            if (c.getName().equals(UPDATE_SC)) {
                updateFilm();
            }
            if (c.getName().equals(DELETE_SC)) {
                deleteFilm();
            }
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadFilm();
        }
    }

    public void stateChanged(ChangeEvent e) {
        reloadFilm();
    }

    //перезагрузка списка фильмов
    public void reloadFilm() {
        Thread t = new Thread() {
            public void run() {
                if (filmsList != null) {
                    Collection<Film> s = new ArrayList<>();
                    try {
                        s = sc.getAllFilms();
                        filmsList.setModel(new FilmTableModel(new Vector<Film>(s)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(FilmFrame.this, e.getMessage());
                    }
                }
            }
        };
        t.start();
    }

    //метод добавления фильма
    private void insertFilm() {
        Thread t = new Thread() {
            public void run() {
                try {
                    FilmDialog sd = new FilmDialog(true, FilmFrame.this);
                    sd.setModal(true);
                    sd.setVisible(true);
                    if (sd.getResult()) {
                        Film s = sd.getFilm();
                        sc.insertFilm(s);
                        reloadFilm();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(FilmFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }

    //метод редактирования информации фильма
    private void updateFilm() {
        Thread t = new Thread() {
            public void run() {
                if( filmsList != null) {
                	FilmTableModel scm = (FilmTableModel) filmsList.getModel();
                    if (filmsList.getSelectedRow() >= 0) {
                    	Film s = scm.getFilm(filmsList.getSelectedRow());
                        try {
                        	FilmDialog sd = new FilmDialog(false, FilmFrame.this);
                            sd.setFilm(s);
                            sd.setModal(true);
                            sd.setVisible(true);
                            if (sd.getResult()) {
                            	Film us = sd.getFilm();
                                sc.updateFilm(us);
                                reloadFilm();
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(FilmFrame.this, e.getMessage());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(FilmFrame.this,
                                "Необходимо выделить фильм в списке");
                    }
                }
            }
        };
        t.start();
    }

    //удаление фильма
    private void deleteFilm() {
        Thread t = new Thread() {
            public void run() {
                if (filmsList != null) {
                    FilmTableModel scb = (FilmTableModel) filmsList.getModel();
                    if (filmsList.getSelectedRow() >= 0) {
                        //подтверждение удаления фильма
                        if (JOptionPane.showConfirmDialog(FilmFrame.this,
                                "Вы хотите удалить фильм?", "Удаление фильма",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            Film s = scb.getFilm(filmsList.getSelectedRow());
                            try {
                                sc.deleteFilm(s);
                                reloadFilm();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(FilmFrame.this, e.getMessage());
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(FilmFrame.this, "Необходимо выделить фильм  в списке");
                    }
                }
            }
        };
        t.start();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    FilmFrame sf = new FilmFrame();
                    sf.setDefaultCloseOperation(EXIT_ON_CLOSE);

                    sf.setVisible(true);
                    sf.reloadFilm();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

}
