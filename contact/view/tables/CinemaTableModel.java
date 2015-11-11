package contact.view.tables;

import edu.javacourse.contact.entity.Cinema;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class CinemaTableModel extends AbstractTableModel {

	private Vector cinemas;

	public CinemaTableModel(Vector cinemas){
		this.cinemas=cinemas;
	}
	
	public int getRowCount(){
		if(cinemas!=null){
			return cinemas.size();
		}
		return 0;
	}
	 public int getColumnCount() {
	        return 7;
	    }

	    public String getColumnName(int column) {
	        String[] colNames = {"Название кинотеатра", "Вместительность", "ФИО директора", "Дата пуска в эксплуатацию", "Количество мест категории 1", "Количество мест категории 2",
	        "Количество мест категории 3"};
	        return colNames[column];
	    }

	    public Object getValueAt(int rowIndex, int columnIndex) {
	        if (cinemas != null) {
	            Cinema pub = (Cinema) cinemas.get(rowIndex);

	            switch (columnIndex) {
	                case 0:
	                    return pub.getCinemaName();
	                case 1:
	                    return pub.getHowManyPeople();
	                case 2:
	                    return pub.getFIOdirector();
	                case 3:
	                    return pub.getDateStartCinema();
	                case 4:
	                    return pub.getCountPlace1();
	                case 5:
	                    return pub.getCountPlace2();
	                case 6:
	                    return pub.getCountPlace3();
	            }
	        }
	        return null;
	    }

	    public Cinema getCinema(int rowIndex) {
	        if (cinemas != null) {
	            if (rowIndex < cinemas.size() && rowIndex >= 0) {
	                return (Cinema) cinemas.get(rowIndex);
	            }
	        }
	        return null;
	    }
}
