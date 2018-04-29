package eu4SaveReader.Web.datatable;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class DataTable {
    private String name;
    private ArrayList<Column> columns = new ArrayList<>();
    private ArrayList<Row> rows = new ArrayList<>();
    private int sortCoulmnId = -1;
    private String visualizationType;

    public DataTable(String name) {
        this.name = name;
    }

    public DataTable(String name, ArrayList<Column> columns, ArrayList<Row> rows) {
        this.name = name;
        this.columns = columns;
        this.rows = rows;
    }

    public void addColumn(String type, String label) {
        columns.add(new Column(type, label));
    }

    public void addColumn(Column newColumn) {
        columns.add(newColumn);
    }

    public void addRow(ArrayList<Object> data) {
        rows.add(new Row(data));
    }

    public void addRow(Row newRow) {
        rows.add(newRow);
    }

    public void sort(int columnId) {
        sortCoulmnId = columnId;
    }

    public List<String> toScript() {
        List<String> script = new ArrayList<>();

        // Add columns
        for (Column c : columns) {
            script.add(c.toScript(name));
        }

        script.add("");

        // Add rows
        for(Row r : rows) {
            script.add(r.toScript(name));
        }

        script.add("");

        // Add sort
        if(sortCoulmnId >= 0) {
            script.add("data" + name + ".sort({column: " + sortCoulmnId + ", desc: true});");
        }

        script.add("");

        return script;
    }

    public String getVisualizationType() {
        return visualizationType;
    }

    public void setVisualizationType(String visualizationType) {
        this.visualizationType = visualizationType;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }
}
