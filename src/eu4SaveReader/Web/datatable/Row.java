package eu4SaveReader.Web.datatable;

import java.util.ArrayList;
import java.util.Calendar;

public class Row {
    private ArrayList<Object> data;

    public Row(ArrayList<Object> data) {
        this.data = data;
    }

    String toScript(String name) {
        StringBuilder script = new StringBuilder();
        script.append("data" + name + ".addRow([");

        String prefix = "";

        for(Object o : data) {
            script.append(prefix);
            prefix = ", ";

            if(o.getClass() == String.class) {
                script.append("'")
                    .append(o)
                    .append("'");

            } else if(o.getClass() == Calendar.class) {
                script.append(" new Date('")
                    .append(((Calendar) o).get(Calendar.YEAR))
                    .append("-")
                    .append(((Calendar) o)
                    .get(Calendar.MONTH))
                    .append("-")
                    .append(((Calendar) o).get(Calendar.DAY_OF_MONTH))
                    .append("')");
            } else {
                script.append(o);
            }
        }

        script.append("]);");

        return script.toString();
    }

    public ArrayList<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }
}
