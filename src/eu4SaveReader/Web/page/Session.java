package eu4SaveReader.Web.page;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu4SaveReader.GamePlayer;
import eu4SaveReader.General.Country;
import eu4SaveReader.General.Eu4File;
import eu4SaveReader.General.Player;
import eu4SaveReader.Utils.Tags;
import eu4SaveReader.Utils.Util;
import eu4SaveReader.Web.datatable.DataTable;
import jdk.nashorn.api.scripting.JSObject;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Session {

    private int num;
    private String name;

    private Eu4File save;

    private EnumSet<GamePlayer> gamePlayers;
    private Map<GamePlayer, Player> playersMap = new HashMap<>();

    private DataTable generalTable = new DataTable("General");
    private DataTable devTable = new DataTable("Dev");
    private DataTable incomeTable = new DataTable("Income");
    private DataTable manpowerTable = new DataTable("Manpower");
    private DataTable forceLimitTable = new DataTable("ForceLimit");
    private DataTable nbProvincesTable = new DataTable("nbProvinces");
    private DataTable professionalismTable = new DataTable("Professionalism");

    private JsonObject options = new JsonObject();

    public Session(String savePath, int num) {
        this.num = num;
        save = new Eu4File(savePath);
        gamePlayers = GamePlayer.getAllPlayers();

        for(GamePlayer p : gamePlayers) {
            Player newPlayer = new Player(p.getName(), p.getTag());
            save.addPlayer(newPlayer);
            playersMap.put(p, newPlayer);
        }

        save.extractPlayersInfos();
        populateTables();
    }

    private void populateGeneralTable() {
        generalTable.addColumn("string", "Joueur (Pays)");
        generalTable.addColumn("number", "Developpement");
        generalTable.addColumn("number", "Revenu");
        generalTable.addColumn("number", "Reserve militaire");
        generalTable.addColumn("number", "Limite terrestre");
        generalTable.addColumn("number", "Nombre de provinces");
        generalTable.addColumn("number", "Pertes");
        generalTable.addColumn("number", "Emprun");
        generalTable.addColumn("number", "Professionnalisme");

        for(Player p : playersMap.values()) {
            ArrayList<Object> playerDatas = new ArrayList<>();
            playerDatas.add(p.getName() + " (" + Tags.tags.get(p.getCountry().getTag()) + ")");
            playerDatas.add(p.getCountry().getDev());
            playerDatas.add(p.getCountry().getIncome());
            playerDatas.add(p.getCountry().getMaxManpower());
            playerDatas.add(p.getCountry().getForceLimit());
            playerDatas.add(p.getCountry().getNbProvince());
            playerDatas.add(p.getCountry().getLosses());
            playerDatas.add(p.getCountry().getDebt());
            playerDatas.add(p.getCountry().getProfessionalism());

            generalTable.addRow(playerDatas);
        }
    }

    private void populateTables() {
        populateGeneralTable();
        populateStatTable(devTable, Country::getDev);
        populateStatTable(incomeTable, Country::getIncome);
        populateStatTable(manpowerTable, Country::getMaxManpower);
        populateStatTable(forceLimitTable, Country::getForceLimit);
        populateStatTable(nbProvincesTable, Country::getNbProvince);
        populateStatTable(professionalismTable, Country::getProfessionalism);
        populateOptions();
    }

    private void populateStatTable (DataTable table, Function<Country, Object> dataGetter) {
        table.addColumn("string", "Joueur");
        table.addColumn("number", "Session " + num);
        table.addColumn("number", "Session " + (num - 1));

        for(Player p : playersMap.values()) {
            ArrayList<Object> playerDatas = new ArrayList<>();
            playerDatas.add(p.getName());
            playerDatas.add(dataGetter.apply(p.getCountry()));
            playerDatas.add(dataGetter.apply(p.getCountry()));

            table.addRow(playerDatas);
        }

        table.sort(1);
    }

    private void populateOptions() {
        JsonObject legend = new JsonObject();
        JsonObject chartArea = new JsonObject();
        JsonObject minorGridlines = new JsonObject();
        JsonObject vAxis = new JsonObject();

        legend.addProperty("position", "top");

        chartArea.addProperty("left", 50);
        chartArea.addProperty("top", 50);
        chartArea.addProperty("width", "95%");
        chartArea.addProperty("height", "85%");

        minorGridlines.addProperty("count", 4);

        vAxis.addProperty("format", "decimal");
        vAxis.add("minorGridlines", minorGridlines);

        options.add("legend", legend);
        options.addProperty("theme", "material");
        options.add("chartArea", chartArea);
        options.add("vAxis", vAxis);
    }

    private JsonObject addStringFilter(String containerId, int filterColumnIndex, String label, String placeHolder) {
        JsonObject stringFilter = new JsonObject();
        JsonObject options = new JsonObject();
        JsonObject ui = new JsonObject();

        ui.addProperty("label", label);
        ui.addProperty("labelStacking", "vertical");
        ui.addProperty("placeholder", placeHolder);

        options.addProperty("filterColumnIndex", filterColumnIndex);
        options.addProperty("matchType", "any");
        options.add("ui", ui);

        stringFilter.addProperty("controlType", "StringFilter");
        stringFilter.addProperty("containerId", containerId);
        stringFilter.add("options", options);

        return stringFilter;
    }

    private JsonObject addChartWrapperTable(String containerId, boolean alternatingRowStyle, boolean showRowNumber, String width, boolean allowHtml, String tableRowClass, String oddTableRowClass, String tableCellClass, String tableHeaderClass) {
        JsonObject chartWrapperTable = new JsonObject();
        JsonObject options = new JsonObject();
        JsonObject cssClassNames = new JsonObject();

        cssClassNames.addProperty("tableRow", tableRowClass);
        cssClassNames.addProperty("oddTableRow", oddTableRowClass);
        cssClassNames.addProperty("tableCell", tableCellClass);
        cssClassNames.addProperty("headerCell", tableHeaderClass);

        options.addProperty("alternatingRowStyle", alternatingRowStyle);
        options.addProperty("showRowNumber", showRowNumber);
        options.addProperty("width", width);
        options.addProperty("allowHtml", allowHtml);
        options.add("cssClassNames", cssClassNames);

        chartWrapperTable.addProperty("chartType", "Table");
        chartWrapperTable.addProperty("containerId", containerId);
        chartWrapperTable.add("options", options);

        return chartWrapperTable;
    }

    private String addChartElementVar(DataTable table) {
        return "var chart" + table.getName() + "Div = document.getElementById('chart-" + table.getName() + "');";
    }

    private String addDataTableVar(DataTable table) {
        return "var data" + table.getName() + " = new google.visualization.DataTable();";
    }

    private String addChartVar(DataTable table) {
        return "var chart" + table.getName() + " = new google.visualization.ColumnChart(chart" + table.getName() + "Div);";
    }

    private String addDrawChart(DataTable table) {
        return "chart" + table.getName() + ".draw(data" + table.getName() + ", options);";
    }

    public List<String> toScript() {
        List<String> script = new ArrayList<>();
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

        // Function declaration
        script.add("var drawSession" + num + " = function () {");

        script.add("");

        // Declaration element div replaced with visualization
        script.add("var dashboardGeneralDiv = document.getElementById('dashboard-General');");
        script.add(addChartElementVar(devTable));
        script.add(addChartElementVar(incomeTable));
        script.add(addChartElementVar(manpowerTable));
        script.add(addChartElementVar(forceLimitTable));
        script.add(addChartElementVar(nbProvincesTable));
        script.add(addChartElementVar(professionalismTable));

        script.add("");

        // Declaration of charts and bind to html element
        script.add("var dashboardGeneral = new google.visualization.Dashboard(dashboardGeneralDiv);");
        script.add(addChartVar(devTable));
        script.add(addChartVar(incomeTable));
        script.add(addChartVar(manpowerTable));
        script.add(addChartVar(forceLimitTable));
        script.add(addChartVar(nbProvincesTable));
        script.add(addChartVar(professionalismTable));

        script.add("");

        // Datatables declaration
        script.add(addDataTableVar(generalTable));
        script.add(addDataTableVar(devTable));
        script.add(addDataTableVar(incomeTable));
        script.add(addDataTableVar(manpowerTable));
        script.add(addDataTableVar(forceLimitTable));
        script.add(addDataTableVar(nbProvincesTable));
        script.add(addDataTableVar(professionalismTable));

        script.add("");

        // Declaration options for statTables
        script.add("var options =");
        script.add(prettyGson.toJson(options));

        script.add("");

        // Add stringFilter for generalTable
        script.add("var stringFilterGeneral = new google.visualization.ControlWrapper(");
        script.add(prettyGson.toJson(addStringFilter("control-General", 0, "Rechercher", "Joueur")));
        script.add(");");

        // Add ChartWrapper for generalTable
        script.add("var chartGeneral = new google.visualization.ChartWrapper(");
        script.add(prettyGson.toJson(addChartWrapperTable("chart-General", true, true, "100%", true, "tableRow", "tableRow", "tableCell", "tableHeader" )));
        script.add(");");


        script.add("");

        // Populate datatables
        script.addAll(generalTable.toScript());
        script.addAll(devTable.toScript());
        script.addAll(incomeTable.toScript());
        script.addAll(manpowerTable.toScript());
        script.addAll(forceLimitTable.toScript());
        script.addAll(nbProvincesTable.toScript());
        script.addAll(professionalismTable.toScript());

        // Bind StringFilter with generalTable
        script.add("dashboardGeneral.bind(stringFilterGeneral, chartGeneral);");

        // Draw all charts
        script.add("dashboardGeneral.draw(dataGeneral);");
        script.add(addDrawChart(devTable));
        script.add(addDrawChart(incomeTable));
        script.add(addDrawChart(manpowerTable));
        script.add(addDrawChart(forceLimitTable));
        script.add(addDrawChart(nbProvincesTable));
        script.add(addDrawChart(professionalismTable));

        script.add("};");

        return script;
    }
}
