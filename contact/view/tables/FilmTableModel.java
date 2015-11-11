package contact.view.tables;

import edu.javacourse.contact.entity.Film;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;


public class FilmTableModel extends AbstractTableModel{

	private Vector films;
	
	public FilmTableModel(Vector films){
		this.films = films;
	}
	
	public int getRowCount(){
		if(films != null) {
			return films.size(); 
		}
		return 0;
	}
	
	public int getColumnCount(){
		return 6;
	}
	
	public String getColumnName(int column) {
		String[] colNames = {"Имя фильма", "Тип фильма", "Стоимость проката", "Количество кинотеатров,в которых идет прокат", "Наличие призов", "Продолжительность" };
		return colNames[column];
	}
	
	 public Object getValueAt(int rowIndex, int columnIndex) {
	        if (films != null) {
	            Film sc = (Film) films.get(rowIndex);

	            switch (columnIndex) {
	                case 0:
	                    return sc.getFilmName();
	                case 1:
	                    return sc.getType();
	                case 2:
	                    return sc.getProkat();
	                case 3:
	                    return sc.getCountCinema();
	                case 4:
	                    return sc.getOskar();
	                case 5:
	                	return sc.getDuration();
	            }
	        }
	        return null;
	    }
	 
	 public Film getFilm(int rowIndex) {
	        if (films != null) {
	            if (rowIndex < films.size() && rowIndex >= 0) {
	                return (Film) films.get(rowIndex);
	            }
	        }
	        return null;
	    }
}
