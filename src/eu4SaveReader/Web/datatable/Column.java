package eu4SaveReader.Web.datatable;

public class Column {
    private String type;

    private String label;

    public Column(String type, String label) {
        this.type = type;
        this.label = label;
    }

    String toScript(String name) {
        return "data" + name + ".addColumn('" + type + "', '" + label + "');";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
