package eu4SaveReader.General;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import eu4SaveReader.Utils.Areas;
import eu4SaveReader.Utils.Buildings;
import eu4SaveReader.Utils.Cultures;
import eu4SaveReader.Utils.Estates;
import eu4SaveReader.Utils.Goods;
import eu4SaveReader.Utils.ProvincesId;
import eu4SaveReader.Utils.Religions;
import eu4SaveReader.Utils.Tags;
import eu4SaveReader.Utils.Util;

public class Province {

    private int id;
    private String name;
    private String owner;
    private String controller;
    private int baseTax;
    private int baseProd;
    private int baseManpower;
    private double autonomy;
    private int estate;
    private String good;
    private String religion;
    private String culture;
    private boolean isPartHRE;
    private ArrayList<Double> institutions = new ArrayList<Double>();
    private HashMap<String, String> buildings = new HashMap<String, String>();
    private HashMap<Integer, Advisor> advisors = new HashMap<Integer, Advisor>();
    
    public Province(int id) {
    	this.id = id;
    	name = ProvincesId.provincesId.get(id);
    }
    
    private boolean extractPartHRE(String provinceInfos) {
    	boolean partHRE;
    	
    	try {
    		partHRE = Util.extractInfo(provinceInfos, "\n\t\thre=").equalsIgnoreCase("yes");
    	} catch (NullPointerException e) {
    		partHRE = false;
    	}
    	
    	return partHRE;
    }
    
    private ArrayList<Double> extractInstitutions(String provinceInfos) {
    	int startAddr, endAddr;
    	ArrayList<Double> institutions = new ArrayList<Double>();

	    if((startAddr = provinceInfos.indexOf("institutions={")) != -1) {
	    	startAddr += 15;
	    	endAddr = provinceInfos.indexOf("\n", startAddr);
	    	String[] institutionsString = provinceInfos.substring(startAddr, endAddr).split(" ");
	    	
	    	for(int i = 0; i < institutionsString.length; i++) {
	    		institutions.add(Double.parseDouble(institutionsString[i]));
	    	}
	    }
	    
	    return institutions;
    }
    
    private HashMap<String, String> extractBuildings(String provinceInfos) {
    	int startAddr, endAddr;
    	String[] buildingsInfos;
    	String[] buildingsBuilders;
    	HashMap<String, String> buildings = new HashMap<String, String>();

	    if((startAddr = provinceInfos.indexOf("buildings={")) != -1) {
	    	startAddr += 15;
	    	endAddr = provinceInfos.indexOf("}", startAddr) - 7;
	    	buildingsInfos = provinceInfos.substring(startAddr, endAddr).split("=yes\n\t\t\t");
	    	
	    	startAddr = provinceInfos.indexOf("building_builders={") + 23;
		    if(startAddr != -1) {	    	
		    	endAddr = provinceInfos.indexOf("}", startAddr) - 3;
		    	buildingsBuilders = provinceInfos.substring(startAddr, endAddr).split("\n\t\t\t");
		    	
		    	for(String builder : buildingsBuilders) {
		    		for(String building : buildingsInfos) {
			    		if(builder.split("=\"")[0].compareTo(building) == 0) {
			    			buildings.put(building, builder.split("=\"")[1].substring(0, builder.split("=\"")[1].length() - 1));
			    			break;
			    		}
		    		}
		    	}
		    }
	    }
    	
    	return buildings;
    }
    
    private HashMap<Integer, Advisor> extractAdvisors(String provinceInfos) {
    	int startAddr = 0;
    	int endAddr;
    	HashMap<Integer, Advisor> advisors = new HashMap<Integer, Advisor>();
    	
	    while((startAddr = provinceInfos.indexOf("\n\t\t\t\tadvisor={", startAddr)) != -1) {
	    	startAddr = startAddr + 16;
	    	endAddr = provinceInfos.indexOf("}", startAddr);
	    	Advisor advisor = new Advisor(provinceInfos.substring(startAddr, endAddr));
		    
		    advisors.put(advisor.getId(), advisor);
	    }
	    
	    while((startAddr = provinceInfos.indexOf("\n\t\t\tadvisor={", startAddr)) != -1) {
	    	startAddr = startAddr + 16;
	    	endAddr = provinceInfos.indexOf("}", startAddr);
	    	Advisor advisor = new Advisor(provinceInfos.substring(startAddr, endAddr));
		    
		    advisors.put(advisor.getId(), advisor);
	    }	    
	    
    	return advisors;
    }
    
    private String printInstitutions() {   	
    	StringBuilder institutionsString = new StringBuilder();
    	
    	for(int i = 0; i < institutions.size(); i ++) {
			institutionsString.append(Util.institutions.get(i));
			institutionsString.append(": " + institutions.get(i) + "%");
			institutionsString.append(", ");
    	}
    	
    	return institutionsString.toString().substring(0, institutionsString.toString().length() - 2);
    }
    
    private String printBuildings() {
    	if(buildings.isEmpty()) {
    		return "None";
    	}
    	StringBuilder buildingsString = new StringBuilder();
    	
    	for(Entry<String, String> b : buildings.entrySet()) {
    		buildingsString.append(Buildings.buildings.get(b.getKey()));
    		buildingsString.append(", ");
    	}
    	
    	return buildingsString.toString().substring(0, buildingsString.toString().length() - 2);
    }
    
    public void extractInfos(String provinceInfos) {
    	owner = Util.extractInfo(provinceInfos, "owner=").replace("\"", "");
    	controller = Util.extractInfo(provinceInfos, "controller=").replace("\"", "");
    	baseTax = Util.extractInfoDouble(provinceInfos, "base_tax=").intValue();
    	baseProd = Util.extractInfoDouble(provinceInfos, "base_production=").intValue();
    	baseManpower = Util.extractInfoDouble(provinceInfos, "base_manpower=").intValue();
    	autonomy = Util.extractInfoDouble(provinceInfos, "local_autonomy=");
    	estate = Util.extractInfoInt(provinceInfos, "estate=");
    	isPartHRE = extractPartHRE(provinceInfos);
    	good = Util.extractInfo(provinceInfos, "trade_goods=");
	    religion = Util.extractInfo(provinceInfos, "\n\t\treligion=");
	    culture = Util.extractInfo(provinceInfos, "\n\t\tculture=");
    	institutions = extractInstitutions(provinceInfos);
    	buildings = extractBuildings(provinceInfos);
    	advisors = extractAdvisors(provinceInfos);
    }
    
    public void updateAutonomy(ArrayList<String> states) {
    	if(estate != 0 && autonomy < 25) {
    		autonomy = 25;
    	}
    	
    	if(!states.contains(Areas.provinceArea.get(id)) && autonomy < 75) {
    		autonomy = 75;
    	}
    }
    @Override
    public String toString() {
		return "\n\tName: " + name
		+ "\n\tId: " + id
		+ "\n\tOwner: " + Tags.tags.get(owner)
		+ "\n\tController: " + Tags.tags.get(controller)
		+ "\n\tBase tax: " + baseTax
		+ "\n\tBase production: " + baseProd
		+ "\n\tBase manpower: " + baseManpower
		+ "\n\tLocal autonomy: " + autonomy
		+ "\n\tEstate: " + Estates.estates.get(estate)
		+ "\n\tIs part of HRE: " + Util.printBoolean(isPartHRE)
		+ "\n\tProduced good: " + Goods.goodsNames.get(good)
		+ "\n\tCulture: " + Cultures.cultures.get(culture)
		+ "\n\tReligion: " + Religions.religions.get(religion)
		+ "\n\tInsitutions: " + printInstitutions()
		+ "\n\tBuildings: " + printBuildings()
		+ "\n";
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBaseTax() {
		return baseTax;
	}

	public void setBaseTax(int baseTax) {
		this.baseTax = baseTax;
	}

	public int getBaseProd() {
		return baseProd;
	}

	public void setBaseProd(int baseProd) {
		this.baseProd = baseProd;
	}

	public int getBaseManpower() {
		return baseManpower;
	}

	public void setBaseManpower(int baseManpower) {
		this.baseManpower = baseManpower;
	}

	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getCulture() {
		return culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public ArrayList<Double> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(ArrayList<Double> institutions) {
		this.institutions = institutions;
	}

	public double getAutonomy() {
		return autonomy;
	}

	public void setAutonomy(double autonomy) {
		this.autonomy = autonomy;
	}

	public HashMap<String, String> getBuildings() {
		return buildings;
	}

	public void setBuildings(HashMap<String, String> buildings) {
		this.buildings = buildings;
	}
	
	public HashMap<Integer, Advisor> getAdvisors() {
		return advisors;
	}

	public void setAdvisors(HashMap<Integer, Advisor> advisors) {
		this.advisors = advisors;
	}

	public boolean isPartHRE() {
		return isPartHRE;
	}

	public void setPartHRE(boolean isPartHRE) {
		this.isPartHRE = isPartHRE;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public int getEstate() {
		return estate;
	}

	public void setEstate(int estate) {
		this.estate = estate;
	};	
}
