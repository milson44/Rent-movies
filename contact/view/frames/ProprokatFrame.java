package contact.view.frames;
import db.DbHelper;
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

import edu.javacourse.contact.entity.Proprokat;
import contact.view.dialogs.FilmTypeDialog;
import contact.view.dialogs.ProprokatDialog;
import edu.javacourse.contact.controllers.ProprokatController;
import contact.view.tables.ProprokatTableModel;

public class ProprokatFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {

	  private ProprokatController sc = new ProprokatController();
	    private JTable proprokatsList;

	    private static final String INSERT_SC = "insertProprokat";
	    private static final String DELETE_SC = "deleteProprokat";
	    private static final String UPDATE_SC = "updateProprokat";


	    public ProprokatFrame() throws Exception {

	        getContentPane().setLayout(new BorderLayout());

	        JMenuBar menuBar = new JMenuBar();
	        JMenu menu = new JMenu("Меню");

	        JMenuItem menuItem1 = new JMenuItem("Фильмы");
	        menuItem1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent actionEvent) {
	                try {
	                    FilmFrame s = new FilmFrame();
	                    s.setDefaultCloseOperation(EXIT_ON_CLOSE);
	                    s.setVisible(true);
	                    s.reloadFilm();
	                    setVisible(false);
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	        });
	        menu.add(menuItem1);

	        JMenuItem menuItem2 = new JMenuItem("Типы фильмов");
	        menuItem2.addActionListener(new ActionListener() {
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
	        menu.add(menuItem2);
	        
	        
	        JMenuItem menuItem3 = new JMenuItem("Кинотеатры");
	        menuItem3.addActionListener(new ActionListener() {
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
	        menu.add(menuItem3);

	        menuBar.add(menu);
	        setJMenuBar(menuBar);

	        JPanel bot = new JPanel();
	        bot.setLayout(new BorderLayout());
	        JPanel right = new JPanel();
	        right.setLayout(new BorderLayout());
	        right.add(new JLabel("Прокат:"), BorderLayout.NORTH);
	        proprokatsList = new JTable(1, 8);
	        right.add(new JScrollPane(proprokatsList), BorderLayout.CENTER);
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
	        pnlBtnSt.setLayout(new GridLayout(1, 8));
	        pnlBtnSt.add(btnAddSt);
	        pnlBtnSt.add(btnUpdSt);
	        pnlBtnSt.add(btnDelSt);
	        right.add(pnlBtnSt, BorderLayout.SOUTH);

	        bot.add(right, BorderLayout.CENTER);

	        getContentPane().add(bot, BorderLayout.CENTER);

	        setBounds(100, 100, 1100, 500);
	    }

	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource() instanceof Component) {
	            Component c = (Component) e.getSource();
	            if (c.getName().equals(INSERT_SC)) {
	                insertProprokat();
	            }
	            if (c.getName().equals(UPDATE_SC)) {
	                updateProprokat();
	            }
	            if (c.getName().equals(DELETE_SC)) {
	                deleteProprokat();
	            }
	        }
	    }


	    public void valueChanged(ListSelectionEvent e) {
	        if (!e.getValueIsAdjusting()) {
	            reloadProprokat();
	        }
	    }

	    public void stateChanged(ChangeEvent e) {
	        reloadProprokat();
	    }

	    public void reloadProprokat() {
	        Thread t = new Thread() {
	            // Переопределяем в нем метод run
	            public void run() {
	                if (proprokatsList != null) {
	                    Collection<Proprokat> s = new ArrayList<>();
	                    try {
	                        s = sc.getAllProprokats();
	                        proprokatsList.setModel(new ProprokatTableModel(new Vector<Proprokat>(s)));
	                    } catch (SQLException e) {
	                        JOptionPane.showMessageDialog(ProprokatFrame.this, e.getMessage());
	                    }
	                }
	            }
	        };
	        t.start();
	    }

	    private void insertProprokat() {
	        Thread t = new Thread() {
	            public void run() {
	                try {
	                    ProprokatDialog sd = new ProprokatDialog(true, ProprokatFrame.this);
	                    sd.setModal(true);
	                    sd.setVisible(true);
	                    if (sd.getResult()) {
	                        Proprokat s = sd.getProprokat();
	                        sc.insertProprokat(s);
	                        reloadProprokat();
	                    }
	                } catch (SQLException e) {
	                    JOptionPane.showMessageDialog(ProprokatFrame.this, e.getMessage());
	                }
	            }
	        };
	        t.start();
	    }

	    private void updateProprokat() {
	        Thread t = new Thread() {
	            public void run() {
	                if (proprokatsList != null) {
	                	ProprokatTableModel scm = (ProprokatTableModel) proprokatsList.getModel();
	                    if (proprokatsList.getSelectedRow() >= 0) {
	                    	Proprokat s = scm.getProprokat(proprokatsList.getSelectedRow());
	                        try {
	                            ProprokatDialog sd = new ProprokatDialog(false, ProprokatFrame.this);
	                            sd.setProprokat(s);
	                            sd.setModal(true);
	                            sd.setVisible(true);
	                            if (sd.getResult()) {
	                            	Proprokat us = sd.getProprokat();
	                                sc.updateProprokat(us);
	                                reloadProprokat();
	                            }
	                        } catch (SQLException e) {
	                            JOptionPane.showMessageDialog(ProprokatFrame.this, e.getMessage());
	                        }
	                    }
	                    else {
	                        JOptionPane.showMessageDialog(ProprokatFrame.this,
	                                "Необходимо выделить прокат в списке");
	                    }
	                }
	            }
	        };
	        t.start();
	    }


	    private void deleteProprokat() {
	        Thread t = new Thread() {
	            public void run() {
	                if (proprokatsList != null) {
	                	ProprokatTableModel scb = (ProprokatTableModel) proprokatsList.getModel();
	                    if (proprokatsList.getSelectedRow() >= 0) {
	                        if (JOptionPane.showConfirmDialog(ProprokatFrame.this,
	                                "Вы хотите удалить прокат?", "Удаление проката",
	                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
	                        	Proprokat s = scb.getProprokat(proprokatsList.getSelectedRow());
	                            try {
	                                sc.deleteProprokat(s);
	                                reloadProprokat();
	                            } catch (SQLException e) {
	                                JOptionPane.showMessageDialog(ProprokatFrame.this, e.getMessage());
	                            }
	                        }
	                    }
	                    else {
	                        JOptionPane.showMessageDialog(ProprokatFrame.this, "Необходимо выделить прокат в списке");
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
	    	ProprokatFrame sf = new ProprokatFrame();
	    	sf.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	
	    	sf.setVisible(true);
	    	sf.reloadProprokat();
	    	} catch (Exception ex) {
	    	ex.printStackTrace();
	    	}
	    	
	    	}
	    	
	    	});
	    	}
}
