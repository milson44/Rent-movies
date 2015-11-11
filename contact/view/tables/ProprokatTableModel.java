package contact.view.tables;

import java.text.DateFormat;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import edu.javacourse.contact.entity.Proprokat;

public class ProprokatTableModel extends AbstractTableModel{
	
	private Vector proprokats;

    public ProprokatTableModel(Vector proprokats) {
        this.proprokats = proprokats;
    }

    public int getRowCount() {
        if (proprokats != null) {
            return proprokats.size();
        }
        return 0;
    }

    public int getColumnCount() {
        return 8;
    }

    public String getColumnName(int column) {
        String[] colNames = {"Фильм", "Кинотеатр", "Дата старта", "Дата конца", "стоимость места кат1", "стоимость места кат2","стоимость места кат3","Сбор от проката"};
        return colNames[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (proprokats != null) {
        	Proprokat sc = (Proprokat) proprokats.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return sc.getFilmsName();
                case 1:
                	return sc.getCinemaName();
                case 2:
                    return DateFormat.getDateInstance(DateFormat.SHORT).format(sc.getDateProkatStart());
                case 3:
                	 return DateFormat.getDateInstance(DateFormat.SHORT).format(sc.getDateProkatEnd());
                case 4:
                	return sc.getCostPlace1();
                case 5:
                	return sc.getCostPlace2();
                case 6:
                	return sc.getCostPlace3();
                case 7:
                	return sc.getSborProkat();
                	
            }
        }
        return null;
    }

    public Proprokat getProprokat(int rowIndex) {
        if (proprokats != null) {
            if (rowIndex < proprokats.size() && rowIndex >= 0) {
                return (Proprokat) proprokats.get(rowIndex);
            }
        }
        return null;
    }

}
