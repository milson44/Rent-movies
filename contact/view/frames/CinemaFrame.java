package contact.view.frames;

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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.javacourse.contact.controllers.CinemaController;
import edu.javacourse.contact.entity.Cinema;
import edu.javacourse.contact.entity.Film;
import contact.view.dialogs.CinemaDialog;
import contact.view.dialogs.FilmTypeDialog;
import contact.view.dialogs.SearchCinemaDialog;
import contact.view.tables.CinemaTableModel;
import contact.view.tables.FilmTableModel;

public class CinemaFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener  {
	
	private CinemaController pc = new CinemaController();
    private JTable cinemasList;

    private static final String INSERT_PC = "insertCinema";
    private static final String DELETE_PC = "deleteCinema";
    private static final String UPDATE_PC = "updateCinema";

    public CinemaFrame() throws Exception {

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
        
     // переход к окну фильмов
    	JMenuItem menuItem2 = new JMenuItem("Фильмы");
    	menuItem2.addActionListener(new ActionListener() {
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
    	menu.add(menuItem2);
        

       
        
      //запуск диалогового окна для поиска
        JMenuItem menuItem3 = new JMenuItem("Поиск");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Thread t = new Thread() {
                    public void run() {
                        SearchCinemaDialog ssd = new SearchCinemaDialog();
                        ssd.setModal(true);
                        ssd.setVisible(true);
                        Collection<Cinema> s = ssd.getResult();
                        cinemasList.setModel(new CinemaTableModel(new Vector<Cinema>(s)));
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
        right.add(new JLabel("Кинотеатры:"), BorderLayout.NORTH);
        cinemasList = new JTable(1, 7);
        right.add(new JScrollPane(cinemasList), BorderLayout.CENTER);
        JButton btnAddPb = new JButton("Добавить");
        btnAddPb.setName(INSERT_PC);
        btnAddPb.addActionListener(this);
        JButton btnUpdPb = new JButton("Исправить");
        btnUpdPb.setName(UPDATE_PC);
        btnUpdPb.addActionListener(this);
        JButton btnDelPb = new JButton("Удалить");
        btnDelPb.setName(DELETE_PC);
        btnDelPb.addActionListener(this);
        JPanel pnlBtnSt = new JPanel();
        pnlBtnSt.setLayout(new GridLayout(1, 4));
        pnlBtnSt.add(btnAddPb);
        pnlBtnSt.add(btnUpdPb);
        pnlBtnSt.add(btnDelPb);
        right.add(pnlBtnSt, BorderLayout.SOUTH);

        bot.add(right, BorderLayout.CENTER);

        getContentPane().add(bot, BorderLayout.CENTER);

        setBounds(100, 100, 1100, 500);
    }

    //обработчик событий
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(INSERT_PC)) {
                insertCinema();
            }
            if (c.getName().equals(UPDATE_PC)) {
                updateCinema();
            }
            if (c.getName().equals(DELETE_PC)) {
                deleteCinema();
            }
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadCinema();
        }
    }

    public void stateChanged(ChangeEvent e) {
        reloadCinema();
    }

    //перезагрузка списка кинотеатров
    public void reloadCinema() {
        Thread t = new Thread() {
            public void run() {
                if (cinemasList != null) {
                    Collection<Cinema> p = new ArrayList<>();
                    try {
                        p = pc.getAllCinemas();
                        cinemasList.setModel(new CinemaTableModel(new Vector<Cinema>(p)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(CinemaFrame.this, e.getMessage());
                    }
                }
            }
        };
        t.start();
    }

    //метод добавления кинотеатра
    private void insertCinema() {
        Thread t = new Thread() {
            public void run() {
                try {
                    //запуск диалогового окна для добавления кинотеатра
                	CinemaDialog pd = new CinemaDialog(true, CinemaFrame.this);
                    pd.setModal(true);
                    pd.setVisible(true);
                    if (pd.getResult()) {
                    	Cinema p = pd.getCinema();
                        pc.insertCinema(p);
                        reloadCinema();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(CinemaFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }

    //метод редактирования информации кинотеатра
    private void updateCinema() {
        Thread t = new Thread() {
            public void run() {
                if (cinemasList != null) {
                	CinemaTableModel ptm = (CinemaTableModel) cinemasList.getModel();
                    if (cinemasList.getSelectedRow() >= 0) {
                    	Cinema p = ptm.getCinema(cinemasList.getSelectedRow());
                        try {
                            //запуск диалогового окна для добавления кинотеатра
                        	CinemaDialog pd = new CinemaDialog(false, CinemaFrame.this);
                            pd.setCinema(p);
                            pd.setModal(true);
                            pd.setVisible(true);
                            if (pd.getResult()) {
                            	Cinema up = pd.getCinema();
                                pc.updateCinema(up);
                                reloadCinema();
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(CinemaFrame.this, e.getMessage());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(CinemaFrame.this,
                                "Необходимо выделить кинотеатр в списке");
                    }
                }
            }
        };
        t.start();
    }

    //удаление кинотеатра
    private void deleteCinema() {
        Thread t = new Thread() {
            public void run() {
                if (cinemasList != null) {
                    CinemaTableModel ptm = (CinemaTableModel) cinemasList.getModel();
                    if (cinemasList.getSelectedRow() >= 0) {
                        //подтверждение удаления кинотеатра
                        if (JOptionPane.showConfirmDialog(CinemaFrame.this,
                                "Вы хотите удалить кинотеатр?", "Удаление кинотеатра",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        	Cinema p = ptm.getCinema(cinemasList.getSelectedRow());
                            try {
                                pc.deleteCinema(p);
                                reloadCinema();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(CinemaFrame.this, e.getMessage());
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(CinemaFrame.this, "Необходимо выделить кинотеатр в списке");
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
                	CinemaFrame pf = new CinemaFrame();
                    pf.setDefaultCloseOperation(EXIT_ON_CLOSE);

                    pf.setVisible(true);
                    pf.reloadCinema();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

}
