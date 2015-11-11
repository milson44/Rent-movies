
package contact.view.dialogs;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import javax.swing.*;

import edu.javacourse.contact.controllers.FilmController;
import edu.javacourse.contact.entity.Film;
import edu.javacourse.contact.entity.FilmType;
import contact.view.frames.FilmFrame;
import edu.javacourse.contact.controllers.FilmTypesController;

public class FilmDialog extends JDialog implements ActionListener{
	
	private static final int D_HEIGHT = 220;
    private final static int D_WIDTH = 600;
    private final static int L_X = 10;
    private final static int L_W = 150;
    private final static int C_W = 150;
    private FilmFrame owner;
    private boolean result = false;
    public final static boolean OSKAR_YES = true;
    public final static boolean OSKAR_NO = false;
    private long filmId = 0;
    private JTextField filmName = new JTextField();
    private JComboBox film_typeList; // поле ввода типа
    private JTextField prokat = new JTextField();
    private JTextField countCinema = new JTextField();
    private JCheckBox oskar = new JCheckBox();
    private JTextField duration = new JTextField();

    public FilmDialog(boolean newFilm, FilmFrame owner) {
        this.owner = owner;
        setTitle("Редактирование данных фильма");
        getContentPane().setLayout(new FlowLayout());

        getContentPane().setLayout(null);

        JLabel l = new JLabel("Имя Фильма:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        filmName.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(filmName);

        l = new JLabel("Тип фильма:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        
     // получение списка подписчиков
        FilmTypesController ptc = null;
        Collection<FilmType> film_types = new ArrayList<FilmType>();
        try {
            ptc = new FilmTypesController();
            film_types = ptc.getAllFilmTypes();
            System.out.println(film_types);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        film_typeList = new JComboBox(new Vector<FilmType>(film_types));
        film_typeList.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(film_typeList);


        l = new JLabel("Стоимость проката:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        prokat.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(prokat);
        
        l = new JLabel("Количество кинотеатров:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        countCinema.setBounds(L_X + L_W + 10, 70, C_W, 20);
        getContentPane().add(countCinema);
        
        l = new JLabel("Наличие наград:", JLabel.RIGHT);
        l.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(l);
        oskar.setBounds(L_X + L_W + 10, 90, C_W, 20);
        getContentPane().add(oskar);
        
        l = new JLabel("Продолжительность:", JLabel.RIGHT);
        l.setBounds(L_X, 110, L_W, 20);
        getContentPane().add(l);
        duration.setBounds(L_X + L_W + 10, 110, C_W, 20);
        getContentPane().add(duration);

        JButton btnCancel = new JButton("Отмена");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 150, 25);
        getContentPane().add(btnCancel);

        if (newFilm) {
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
        setBounds(((int) d.getWidth() - FilmDialog.D_WIDTH) / 2, ((int) d.getHeight() - FilmDialog.D_HEIGHT) / 2,
        		FilmDialog.D_WIDTH, FilmDialog.D_HEIGHT);
    }

    public void setFilm(Film fi) {
        filmId = fi.getFilmId();
        filmName.setText(fi.getFilmName());
        for (int i = 0; i < film_typeList.getModel().getSize(); i++) {
            FilmType pt = (FilmType) film_typeList.getModel().getElementAt(i);
            if (pt.getFilmType_id() == pt.getFilmType_id()) {
                film_typeList.setSelectedIndex(i);
                break;
            }
        }
        prokat.setText(String.valueOf(fi.getProkat()));
        countCinema.setText(String.valueOf(fi.getCountCinema()));
        oskar.setSelected(fi.getOskar());
        duration.setText(String.valueOf(fi.getDuration()));
        
    }

    public Film getFilm() {
        Film fi = new Film();
        fi.setFilmId(filmId);
        fi.setFilmName(filmName.getText());
        fi.setFilmType_id(((FilmType) film_typeList.getSelectedItem()).getFilmType_id());
        System.out.println(((FilmType) film_typeList.getSelectedItem()).getFilmType_id());
        fi.setProkat(Integer.parseInt(prokat.getText()));
        fi.setCountCinema(Integer.parseInt(countCinema.getText()));
        fi.setOskar(oskar.isSelected());
        fi.setDuration(Integer.parseInt(duration.getText()));
        return fi;
    }

    public boolean getResult() {
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("New")) {
            result = true;
            try {
                FilmController.getInstance().insertFilm(getFilm());
                owner.reloadFilm();
                filmName.setText("");
                prokat.setText("");
                countCinema.setText("");
                oskar.setText("");
                duration.setText("");
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
