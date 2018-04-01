package eu4SaveReader.General;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import eu4SaveReader.Utils.Advisors;
import eu4SaveReader.Utils.Areas;
import eu4SaveReader.Utils.Cultures;
import eu4SaveReader.Utils.Dependencies;
import eu4SaveReader.Utils.Goods;
import eu4SaveReader.Utils.Governments;
import eu4SaveReader.Utils.Ideas;
import eu4SaveReader.Utils.Policies;
import eu4SaveReader.Utils.ProvincesId;
import eu4SaveReader.Utils.Religions;
import eu4SaveReader.Utils.Tags;
import eu4SaveReader.Utils.Util;

public class Country {

    private String tag;
    private String name;
    private int dev;
    private double realmDev;
    private int nbProvince;
    private String govType;
    private int govRank;
    private ArrayList<Boolean> continents = new ArrayList<Boolean>();
    private ArrayList<Boolean> institutions = new ArrayList<Boolean>();
    private double cash;
    private double income;
    private double inflation;
    private int debt;
    private int mercantilism;
    private BigDecimal professionalism;
    private double armyTradition;
    private double navyTradition;
    private int manpower;
    private int maxManpower;
    private int sailors;
    private int maxSailors;
    private int forceLimit;
    private int army;
    private int mercenaries;
    private int losses;
    private int stability;
    private double legitimacy;
    private double averageAutonomy;
    private double averageUnrest;
    private double warExhaustion;
    private BigDecimal religiousUnity;
    private double prestige;
    private int powerProjection;
    private int splendor;
    private double score;
    private double absolutism;
    private int admTech;
    private int dipTech;
    private int milTech;
    private int capital;
    private String religion;
    private String culture;
    private GregorianCalendar goldenAge;
    private boolean isPartHRE;
    private boolean isHREEmperor;
    private boolean isRevolutionTarget; //type de gouv : republic = default_republic, empire = government_name="default_monarchy"
    private ArrayList<Integer> tradeBonus = new ArrayList<Integer>();
    private ArrayList<String> rivals = new ArrayList<String>();
    private ArrayList<String> allies = new ArrayList<String>();
    private HashMap<Integer, Province> provinces = new HashMap<Integer, Province>();
    private HashMap<Integer, Advisor> advisors = new HashMap<Integer, Advisor>();
    private ArrayList<String> ancientsTags = new ArrayList<String>();
    private HashMap<Country, String> dependencies = new HashMap<Country, String>();
    private LinkedHashMap<String, Integer> ideas = new LinkedHashMap<String, Integer>();
    private ArrayList<String> policies = new ArrayList<String>();
    private ArrayList<Double> factions = new ArrayList<Double>();
    private ArrayList<String> states = new ArrayList<String>();
    
    public Country(String tag) {
		this.tag = tag;
	}

    private ArrayList<Boolean> extractContinents(String countryInfos) {
    	int startAddr, endAddr;
    	ArrayList<Boolean> continents = new ArrayList<Boolean>();
    	
	    if((startAddr = countryInfos.indexOf("continent={")) != -1) {
	    	startAddr += 15;
	    	endAddr = countryInfos.indexOf("\n", startAddr);
	    	String[] contienentsString = countryInfos.substring(startAddr, endAddr).split(" ");
	    	
	    	for(int i = 0; i < contienentsString.length; i++) {
	    		continents.add(contienentsString[i].equalsIgnoreCase("1"));
	    	}
	    }
	    
	    return continents;
    }
    
    private ArrayList<Boolean> extractInstitutions(String countryInfos) {
    	int startAddr, endAddr;
    	ArrayList<Boolean> institutions = new ArrayList<Boolean>();
    	
	    if((startAddr = countryInfos.indexOf("institutions={")) != -1) {
	    	startAddr += 15;
	    	endAddr = countryInfos.indexOf("\n", startAddr);
	    	String[] institutionsString = countryInfos.substring(startAddr, endAddr).split(" ");
	    	
	    	for(int i = 0; i < institutionsString.length; i++) {
	    		institutions.add(institutionsString[i].equalsIgnoreCase("1"));
	    	}
	    }
	    
	    return institutions;
    }
	    
    private int extractDebt(String countryInfos) {
	    int startAddr = 0;
	    int endAddr;
	    int debt = 0;
	    
	    while((startAddr = countryInfos.indexOf("loan={", startAddr)) != -1) {
	    	startAddr =  countryInfos.indexOf("amount=", startAddr) + 7;
	    	endAddr = countryInfos.indexOf("\n", startAddr);
	    	
	    	debt += Integer.parseInt(countryInfos.substring(startAddr, endAddr));
	    }
	    
	    return debt;
    }  
    
    private int extractLosses(String countryInfos) {
    	int startAddr, endAddr;
    	int losses = 0;

	    if((startAddr = countryInfos.indexOf("members={")) != -1) {
	    	startAddr += 14;
	    	endAddr = countryInfos.indexOf("\n", startAddr);
	    	String[] lossesString = countryInfos.substring(startAddr, endAddr).split(" ");
	    	int lossesInt[] = new int[9];
	    	
	    	for(int i = 0; i < lossesInt.length; i++) {
	    		lossesInt[i] = Integer.parseInt(lossesString[i]);
	    	}
	    	
	    	losses = IntStream.of(lossesInt).sum();
	    }
	    
	    return losses;
    }
    
    private ArrayList<String> extractRivals(String countryInfos) {
	    int startAddr = 0;
	    int endAddr;
	    ArrayList<String> rivals = new ArrayList<String>();
	    
	    while((startAddr = countryInfos.indexOf("\n\t\trival={", startAddr)) != -1) {
	    	startAddr =  countryInfos.indexOf("country=", startAddr) + 9;
	    	endAddr = countryInfos.indexOf("\n", startAddr) - 1;
	    	rivals.add(countryInfos.substring(startAddr, endAddr));
	    }
	    
	    return rivals;
    }
    
    private ArrayList<String> extractAllies(String countryInfos) {
    	int startAddr, endAddr;
    	ArrayList<String> allies = new ArrayList<String>();
    	
	    if((startAddr = countryInfos.indexOf("\n\t\tallies={")) != -1) {
	    	startAddr += 15;
	    	endAddr = countryInfos.indexOf("}", startAddr);
	    	String alliesString = countryInfos.substring(startAddr, endAddr);
	    	alliesString = alliesString.replace("\n", "");
	    	alliesString = alliesString.replace("\t", "");
	    	alliesString = alliesString.replace("\r", "");
	    	alliesString = alliesString.substring(1, alliesString.length() - 1);
	    	allies = new ArrayList<String>(Arrays.asList(alliesString.split("\"\"")));
	    }
	    
    	return allies;
    }
    
    private HashMap<Integer, Province> extractProvinces(String countryInfos) {
    	int startAddr, endAddr;
		HashMap<Integer, Province> provinces = new HashMap<Integer, Province>();
		
	    if((startAddr = countryInfos.indexOf("owned_provinces={")) != -1) {
	    	startAddr += 21;
	    	endAddr = countryInfos.indexOf("\n", startAddr);
	    	ArrayList<String> provincesString = new ArrayList<String>(Arrays.asList(countryInfos.substring(startAddr, endAddr).split(" ")));
	    	
	    	for(String p : provincesString) {
	    		provinces.put(Integer.parseInt(p), new Province(Integer.parseInt(p)));
	    	}
	    }
	    	
    	return provinces;
    }
    
    private HashMap<Integer, Advisor> extractAdvisors(String countryInfos) {
    	int startAddr = 0;
    	int endAddr;
    	int advisorId;
    	HashMap<Integer, Advisor> advisors = new HashMap<Integer, Advisor>();
		
	    while((startAddr = countryInfos.indexOf("\n\t\tadvisor={", startAddr)) != -1) {
	    	startAddr =  countryInfos.indexOf("id=", startAddr) + 3;
	    	endAddr = countryInfos.indexOf("\n", startAddr);
	    	advisorId = Integer.parseInt(countryInfos.substring(startAddr, endAddr));
	    	advisors.put(advisorId, new Advisor(advisorId));
	    }
	    
		return advisors;
    }
    
    private ArrayList<String> extractAncientsTags(String countryInfos) {
	    int startAddr = 0;
	    int endAddr;
	    ArrayList<String> ancientsTags = new ArrayList<String>();
	    
	    while((startAddr = countryInfos.indexOf("changed_tag_from=", startAddr)) != -1) {
	    	startAddr =  startAddr + 18;
	    	endAddr = countryInfos.indexOf("\n", startAddr) - 1;
	    	ancientsTags.add(countryInfos.substring(startAddr, endAddr));
	    }
	    
	    return ancientsTags;
    }
    
    private ArrayList<Integer> extractTradeBonus(String countryInfos) {
    	int startAddr, endAddr;
    	ArrayList<Integer> tradeBonus = new ArrayList<Integer>();

	    if((startAddr = countryInfos.indexOf("traded_bonus={")) != -1) {
	    	startAddr += 18;
	    	endAddr = countryInfos.indexOf("\n", startAddr);
	    	String[] tradeBonusString = countryInfos.substring(startAddr, endAddr).split(" ");
	    	
	    	for(int i = 0; i < tradeBonusString.length; i++) {
	    		tradeBonus.add(Integer.parseInt(tradeBonusString[i]));
	    	}
	    }
	    
	    return tradeBonus;
    }
    
    private LinkedHashMap<String, Integer> extractIdeas(String countryInfos) {
    	int startAddr, endAddr;
    	LinkedHashMap<String, Integer> ideas = new LinkedHashMap<String, Integer>();
    	
	    if((startAddr = countryInfos.indexOf("active_idea_groups={")) != -1) {
	    	startAddr += 21;
	    	endAddr = countryInfos.indexOf("}", startAddr);
	    	String ideasString = countryInfos.substring(startAddr, endAddr);
	    	ideasString = ideasString.replace("\t", "");
	    	ideasString = ideasString.replace("\r", "");
	    	String[] ideasStringArray = ideasString.split("\n");
	    	for(String s : ideasStringArray) {
	    		ideas.put(s.split("=")[0], Integer.parseInt(s.split("=")[1]));
	    	}
	    }
    	
    	return ideas;
    }
    
    private ArrayList<String> extractPolicies(String countryInfos) {
	    int startAddr = 0;
	    int endAddr;
	    ArrayList<String> policies = new ArrayList<String>();
	    
	    while((startAddr = countryInfos.indexOf("active_policy={", startAddr)) != -1) {
	    	startAddr =  countryInfos.indexOf("policy=\"", startAddr) + 8;
	    	endAddr = countryInfos.indexOf("\n", startAddr);
	    	
	    	policies.add(countryInfos.substring(startAddr, endAddr).replace("\"", ""));
	    }
	    
	    return policies;
    }
    
    private ArrayList<Double> extractFactions(String countryInfos) {
    	ArrayList<Double> factions = new ArrayList<Double>();
    	
    	switch (govType) {
    		case  "dutch_republic":
    			factions.add(Double.parseDouble(Util.extractInfo(countryInfos, "statists_vs_orangists=")));
    			break;
    		
    		case "revolutionary_republic":
    			int startAddr = 0;
    			int endAddr;
    			
    		    while((startAddr = countryInfos.indexOf("faction={", startAddr)) != -1) {
    		    	startAddr += 9;
    		    	endAddr = countryInfos.indexOf("}", startAddr);
    		    	factions.add(Double.parseDouble(Util.extractInfo(countryInfos.substring(startAddr, endAddr), "influence=")));
    		    }
    		    break;
    	}
	    
    	return factions;
    }
    
    private ArrayList<String> extractStates(String countryInfos) {
	    int startAddr = 0;
	    int endAddr;
	    ArrayList<String> states = new ArrayList<String>();
	    
	    while((startAddr = countryInfos.indexOf("\tstate={", startAddr)) != -1) {
	    	startAddr =  countryInfos.indexOf("area=\"", startAddr) + 6;
	    	endAddr = countryInfos.indexOf("\"", startAddr);
	    	
	    	states.add(countryInfos.substring(startAddr, endAddr));
	    }
	    
	    return states;
    }      
    
    private void updateAdvisors() {
    	for(Entry<Integer, Advisor> entryCoun : getAdvisors().entrySet()) {
    		provLoop:
    		for(Entry<Integer, Province> entryProv : provinces.entrySet()) {
    			for(Entry<Integer, Advisor> entryAdv : entryProv.getValue().getAdvisors().entrySet()) {
    				if(entryAdv.getKey().intValue() == entryCoun.getKey().intValue()) {
    					entryCoun.setValue(entryAdv.getValue());
    					break provLoop;
    				}
    			}
    		}
    	}
    }    
    
    public void updateForceLimit(int nbCountriesHRE) {
    	double forceLimit = 0;
    	double modifiers = 1;
    	
    	forceLimit += 6;
    	
    	for(Entry<Integer, Province> entryProv : provinces.entrySet()) {
    		Province p = entryProv.getValue();
    		
    		double localForceLimit = 0;
    		localForceLimit += (p.getBaseTax() + p.getBaseProd() + p.getBaseManpower()) * 0.1;
    		if(p.getGood().equals("grain")) {
    			localForceLimit += 0.5;
    		}
    		
    		for(Entry<String, String> b : p.getBuildings().entrySet()) {
    			if(b.getKey().equals("native_fortified_house")) {
    				localForceLimit += 10;
    			} else if(b.getKey().equals("regimental_camp")) {
    				localForceLimit += 1;
    			} else if(b.getKey().equals("conscription_center")) {
    				localForceLimit += 2;
    			}
    		}
    		
    		if(p.getEstate() != 2 && p.getEstate() != 4 && p.getEstate() != 5) {
    			localForceLimit *= (1 - (p.getAutonomy() / 100));
    		}
    		
    		forceLimit += localForceLimit;
    	}
    	
    	int bonus = 0;
    	for(Entry<Country, String> d : dependencies.entrySet()) {
    		if(d.getValue().equals("vassal")) {
    			bonus += 1;		
    		} else if(d.getValue().equals("march")) {
    			bonus += 2;
    		} else if(d.getValue().equals("colony")) {
    			if(d.getKey().getNbProvince() >= 10) {
    				bonus += 5;
    			}
    		}
    	}
    	
    	forceLimit += bonus;
    		
		for(Entry<String, Integer> i : ideas.entrySet()) {
    		switch (i.getKey()) {
    			case "influence_ideas":
    			case "ASK_ideas":
    				if(i.getValue() == 7) {
    					forceLimit += bonus;
    				}
    				break;
    				
    			case "BOS_ideas":
    				if(i.getValue() >= 5) {
    					forceLimit += bonus;
    				}
    				break;
    		}
		}
		
    	for(String p : policies) {
    		switch (p) {
    			case "autonomous_estates":
    			case "vassal_obligations_act":
    			case "unified_army_command":
    				forceLimit += bonus;
    				break;
    		}
    	}
    		
    	if(isHREEmperor) {
    		forceLimit += 0.5 * nbCountriesHRE;
    	}
    	
    	if(tag.equals("JMN")) {
    		forceLimit += 100;
    	}
    	
    	for(Entry<Integer, Advisor> entry : advisors.entrySet()) {
    		if(entry.getValue().getType() != null) {
    			if(entry.getValue().getType().equals("army_organiser")) {
    				modifiers += 0.1;
    				break;
    			}
    		}
    	}

    	switch (govType) {
    		case "steppe_horde":
    			modifiers += 0.1 * govRank;
    			break;
    			
    		case "tribal_federation":
    			modifiers += 0.1;
    			break;
    			
			case  "dutch_republic":
	    		if(factions.get(0) > 0) {
	    			modifiers += 0.25;
	    		}
				break;
			
			case "revolutionary_republic":
	    		if(factions.get(2) > factions.get(1) && factions.get(2) > factions.get(0)) {
	    			modifiers += 0.2;
	    		}
			    break;
    	}
    	
    	if(tradeBonus.contains(1)) {
    		modifiers += 0.2;
    	}
    	
    	if(isRevolutionTarget) {
    		modifiers += 0.4;
    	}
    	
    	for(Entry<String, Integer> i : ideas.entrySet()) {
    		switch (i.getKey()) {
    			case "RUS_ideas":
    				if(i.getValue() >= 4) {
    					modifiers += 0.5;
    				}
    				break;
    				
    			case "quantity_ideas":
    				if(i.getValue() == 7) {
    					modifiers += 0.5;
    				}
    				break;
    				
    			case "SCO_ideas":
    				modifiers += 0.33;
    				break;
    				
    			case "MOS_ideas":
    			case "TUR_ideas":
    				if(i.getValue() == 7) {
    					modifiers += 0.33;
    				}
    				break;
    				
    			case "CHICK_ideas":
    			case "daimyo_ideas":
    			case "NPL_ideas":
    			case "STK_ideas":
    				modifiers += 0.25;
    				break;
    				
    			case "RUM_ideas":
    			case "SUK_ideas":
    			case "WLS_ideas":
    			case "PRM_ideas":
    				if(i.getValue() == 7) {
    					modifiers += 0.25;
    				}
    				break;    				
    				
    			case "HES_ideas":
    				if(i.getValue() >= 6) {
    					modifiers += 0.25;
    				}
    				break;
    				
    			case "MOL_ideas":
    				if(i.getValue() >= 3) {
    					modifiers += 0.25;
    				}
    				break;
    				
    			case "RYA_ideas":
    				if(i.getValue() >= 1) {
    					modifiers += 0.25;
    				}
    				break;
    				
    			case "TPR_ideas":
    				if(i.getValue() >= 4) {
    					modifiers += 0.25;
    				}
    				break;
    				
    			case "ARW_ideas":
    			case "IMG_ideas":
    			case "INC_ideas":
    			case "MAZ_ideas":
    			case "SHN_ideas":
    			case "TKI_ideas":
    			case "TVE_ideas":
    				modifiers += 0.2;
    				break;
    				
    			case "BAL_ideas":
    			case "LXA_ideas":
    			case "laotian_ideas":
    			case "somali_ideas":
    				if(i.getValue() == 7) {
    					modifiers += 0.2;
    				}
    				break; 
    				
    			case "offensive_ideas":
    				if(i.getValue() >= 6) {
    					modifiers += 0.2;
    				}
    				break;
    				
    			case "ALB_ideas":
    				if(i.getValue() >= 1) {
    					modifiers += 0.2;
    				}
    				break;
    				
    			case "AKT_ideas":
    				if(i.getValue() >= 3) {
    					modifiers += 0.2;
    				}
    				break;
    				
    			case "HSK_ideas":
    				if(i.getValue() >= 4) {
    					modifiers += 0.2;
    				}
    				break;
    				
    			case "MRI_ideas":
    			case "MNS_ideas":
    				if(i.getValue() >= 2) {
    					modifiers += 0.2;
    				}
    				break;
    				
    			case "ROM_ideas":
    				if(i.getValue() >= 4) {
    					modifiers += 0.15;
    				}
    				break;
    		}
    	}
    	
    	for(String p : policies) {
    		switch (p) {
    			case "pen_rely_on_sword_act":
    			case "agricultural_cultivations":
    			case "colonial_garrisons":
    					modifiers += 0.1;
    				break;
    		}
    	}
    	
    	this.forceLimit = (int) (forceLimit * modifiers); 
    }
    
    private String printContinents() {
    	if(!continents.contains(true)) {
    		return "None";
    	}    	
    	StringBuilder continentsString = new StringBuilder();
    	
    	for(int i = 0; i < continents.size(); i ++) {
    		if(continents.get(i)) {
    			continentsString.append(Util.continents.get(i));
    			continentsString.append(", ");    			
    		}
    	}
    	
    	return continentsString.toString().substring(0, continentsString.toString().length() - 2);
    }
    
    private String printInstitutions() {
    	if(!institutions.contains(true)) {
    		return "None";
    	}
    	
    	StringBuilder institutionsString = new StringBuilder();
    	
    	for(int i = 0; i < institutions.size(); i ++) {
    		if(institutions.get(i)) {
    			institutionsString.append(Util.institutions.get(i));
    			institutionsString.append(", ");    			
    		}
    	}
    	
    	return institutionsString.toString().substring(0, institutionsString.toString().length() - 2);
    }
    
    private String printProvinces() {
    	if(provinces.size() > 0) {
	    	StringBuilder provincesString = new StringBuilder();
	    	
	    	for(Entry<Integer, Province> entryProv : provinces.entrySet()) {    		
	    		provincesString.append(ProvincesId.provincesId.get(entryProv.getKey()));
	    		provincesString.append(", ");
	    	}
	    	
	    	return provincesString.toString().substring(0, provincesString.toString().length() - 2);
    	}
    	
    	return "None";
    }
    
    private String printAdvisors() {
    	if(advisors.size() > 0) {
	    	StringBuilder advisorsString = new StringBuilder();
	    	
	    	for(Entry<Integer, Advisor> entry : advisors.entrySet()) {
	    		if(entry.getValue().getType() != null) {
	    			advisorsString.append(Advisors.advisorsType.get(entry.getValue().getType()));
	    			advisorsString.append(", ");
	    		} else if(advisors.size() == 1){
	    			advisorsString.append("None  ");
	    		}
	    	}
	    	
	    	return advisorsString.toString().substring(0, advisorsString.toString().length() - 2);
    	}
    	
    	return "None";
    }
    
    private String printTradeBonus() {
    	if(tradeBonus.size() > 0) {
	    	StringBuilder tradeBonusString = new StringBuilder();
	    	
	    	for(Integer t : tradeBonus) {
	    		tradeBonusString.append(Goods.getGoodFromId(t));
	    		tradeBonusString.append(", ");
	    	}
	    	
	    	return tradeBonusString.toString().substring(0, tradeBonusString.toString().length() - 2);
    	}
    	
    	return "None";
    }
    
    private String printIdeas() {
    	if(ideas.size() > 0) {
	    	StringBuilder ideasString = new StringBuilder();
	    	
	    	for(Entry<String, Integer> i : ideas.entrySet()) {
	    		if(Character.isUpperCase(i.getKey().charAt(0))) {
	    			ideasString.append(Tags.tags.get(i.getKey().split("_")[0]) + " ideas");
	    		} else {
	    			ideasString.append(Ideas.ideas.get(i.getKey()));
	    		}
	    		
	    		ideasString.append("(");
	    		ideasString.append(i.getValue());
	    		ideasString.append(")");	    		
	    		ideasString.append(", ");
	    	}
	    	
	    	return ideasString.toString().substring(0, ideasString.toString().length() - 2);
    	}
    	
    	return "None";
    }
    
    private String printPolicies() {
    	if(policies.size() > 0) {
	    	StringBuilder policiesString = new StringBuilder();
	    	
	    	for(String p : policies) {
	    		policiesString.append(Policies.policies.get(p));    		
	    		policiesString.append(", ");
	    	}
	    	
	    	return policiesString.toString().substring(0, policiesString.toString().length() - 2);
    	}
    	
    	return "None";
    }    
    
    private String printDependencies() {
    	if(dependencies.size() > 0) {
	    	StringBuilder dependenciesString = new StringBuilder();
	    	
	    	for(Entry<Country, String> d : dependencies.entrySet()) {
	    		if(d.getKey().getTag().matches("^.[0-9]*$")) {
	    			dependenciesString.append(d.getKey().getName());
	    		} else {
	    			dependenciesString.append(Tags.tags.get(d.getKey().getTag()));
	    		}
	    		dependenciesString.append("(");
	    		dependenciesString.append(Dependencies.dependenciesTypes.get(d.getValue()));
	    		dependenciesString.append(")");
	    		dependenciesString.append(", ");
	    	}
	    	
	    	return dependenciesString.toString().substring(0, dependenciesString.toString().length() - 2);    		
    	}
    	
    	return "None";
    }
    
    private String printStates( ) {
    	if(states.size() > 0) {
	    	StringBuilder statesString = new StringBuilder();
	    	
	    	for(String s : states) {
	    		statesString.append(Areas.areas.get(s));
	    		statesString.append(", ");
	    	}
	    	
	    	return statesString.toString().substring(0, statesString.toString().length() - 2);
    	}
    	
    	return "None";
    }
    
    public void extractInfos(String countryInfos) {
	    dev = Util.extractInfoDouble(countryInfos, "raw_development=").intValue();
	    realmDev = Util.extractInfoDouble(countryInfos, "realm_development=");
	    nbProvince = Util.extractInfoInt(countryInfos, "num_of_cities=");
	    govType = Util.extractInfo(countryInfos, "government=\"").replace("\"", "");
	    govRank = Util.extractInfoInt(countryInfos, "government_rank=");
	    continents = extractContinents(countryInfos);
	    institutions = extractInstitutions(countryInfos);
	    cash = Util.extractInfoDouble(countryInfos, "treasury=");
	    income = Util.extractInfoDouble(countryInfos, "estimated_monthly_income=");
	    inflation = Util.extractInfoDouble(countryInfos, "inflation=");
	    debt = extractDebt(countryInfos);
	    mercantilism = Util.extractInfoDouble(countryInfos, "mercantilism=").intValue();
	    professionalism = Util.extractInfoBigDecimal(countryInfos, "\tarmy_professionalism=").multiply(new BigDecimal("100")).setScale(3);
	    armyTradition = Util.extractInfoDouble(countryInfos, "army_tradition=");
	    navyTradition = Util.extractInfoDouble(countryInfos, "navy_tradition=");
	    manpower = Integer.parseInt(Util.extractInfo(countryInfos, "\n\t\tmanpower=").replace(".", ""));
	    maxManpower = Integer.parseInt(Util.extractInfo(countryInfos, "max_manpower=").replace(".", ""));
	    sailors = Util.extractInfoDouble(countryInfos, "\n\t\tsailors=").intValue();
	    maxSailors = Util.extractInfoDouble(countryInfos, "max_sailors=").intValue();
	    losses = extractLosses(countryInfos);
	    army = Util.extractInfoInt(countryInfos, "num_of_regulars=");
	    mercenaries = Util.extractInfoInt(countryInfos, "num_of_mercenaries=");
	    stability = Util.extractInfoDouble(countryInfos, "\tstability=").intValue();
	    legitimacy = Util.extractInfoDouble(countryInfos, "legitimacy=");
	    prestige = Util.extractInfoDouble(countryInfos, "prestige=");
	    powerProjection = Util.extractInfoDouble(countryInfos, "current_power_projection=").intValue();
	    splendor = Util.extractInfoDouble(countryInfos, "splendor=").intValue();
	    score = Util.extractInfoDouble(countryInfos, "\n\t\tscore=");
	    absolutism = Util.extractInfoDouble(countryInfos, "\tabsolutism=");
	    admTech = Util.extractInfoInt(countryInfos, "adm_tech=");
	    dipTech = Util.extractInfoInt(countryInfos, "dip_tech=");
	    milTech = Util.extractInfoInt(countryInfos, "mil_tech=");
	    capital = Util.extractInfoInt(countryInfos, "\n\t\tcapital=");
	    averageAutonomy = Util.extractInfoDouble(countryInfos, "average_autonomy=");
	    averageUnrest = Util.extractInfoDouble(countryInfos, "average_unrest=");
	    warExhaustion = Util.extractInfoDouble(countryInfos, "war_exhaustion=");
	    religiousUnity = new BigDecimal(Util.extractInfo(countryInfos, "religious_unity=")).multiply(new BigDecimal("100")).setScale(1);
	    religion = Util.extractInfo(countryInfos, "\n\t\treligion=");
	    culture = Util.extractInfo(countryInfos, "primary_culture=");
	    goldenAge = Util.convertStringToDate(Util.extractInfo(countryInfos, "golden_era_date="));
	    isRevolutionTarget = (govType.equals("revolutionary_empire") || govType.equals("revolutionary_republic"));
	    tradeBonus = extractTradeBonus(countryInfos);
	    rivals = extractRivals(countryInfos);
	    allies = extractAllies(countryInfos);
	    provinces = extractProvinces(countryInfos);
	    advisors = extractAdvisors(countryInfos);
	    ancientsTags = extractAncientsTags(countryInfos);
	    ideas = extractIdeas(countryInfos);
	    policies = extractPolicies(countryInfos);
	    factions = extractFactions(countryInfos);
	    states = extractStates(countryInfos);
    }
    
    public void updateProvinceBasedInfos() {
        updateAdvisors();
	    isPartHRE = provinces.get(capital).isPartHRE();
	    provinces.get(capital).setAutonomy(0);
	    
	    for(Province p : provinces.values()) {
	    	p.updateAutonomy(states);
	    }
	    
    }
    
    public void addDependencies(HashMap<Country, String> dependencies) {
    	this.dependencies.putAll(dependencies);
    }
    
    public void updateName(String countryInfos) {
    	name = Util.extractInfo(countryInfos, "\n\t\tname=\"").replace("\"", "");
    }
    
    public void updataNbProvince(String countryInfos) {
    	nbProvince = Util.extractInfoInt(countryInfos, "num_of_cities=");
    }
    
    @Override
    public String toString() {
		return Tags.tags.get(tag)
		+ "\n\tCapital: " + ProvincesId.provincesId.get(capital)
		+ "\n\tCulture: " + Cultures.cultures.get(culture)
		+ "\n\tReligion: " + Religions.religions.get(religion)
		+ "\n\tIs a: " + Governments.governementTypes.get(govType)
		+ "\n\tRank: " + Util.govRanks.get(govRank)
		+ "\n\tHas provinces in: " + printContinents()
		+ "\n\tInstitutions embraced: " + printInstitutions()
		+ "\n\tIdeas: " + printIdeas()
		+ "\n\tPolicies: " + printPolicies()
		+ "\n\tDevelopment: " + dev
		+ "\n\tRealm development: " + realmDev
		+ "\n\tNumber of provinces: " + nbProvince
		+ "\n\tTreasury: " + cash
		+ "\n\tIncome: " + income
		+ "\n\tInflation: " + inflation
		+ "\n\tDebt: " + debt
		+ "\n\tMercantilism: " + mercantilism + "%"
		+ "\n\tActual manpower:  " + manpower
		+ "\n\tMaximum manpower:  " + maxManpower
		+ "\n\tActual sailors: " + sailors
		+ "\n\tMaximum sailors: " + maxSailors
		+ "\n\tLand force limit: "	+ forceLimit
		+ "\n\tActive regular regiments: "	+ army
		+ "\n\tActive mercenaries: " + mercenaries
		+ "\n\tProfessionalism: " + professionalism	+ "%"
		+ "\n\tArmy tradition: " + armyTradition
		+ "\n\tNavy tradition: " + navyTradition
		+ "\n\tLosses: " + losses
		+ "\n\tStability: " + stability
		+ "\n\tLegitimacy: " + legitimacy
		+ "\n\tAverage autonomy: " + averageAutonomy
		+ "\n\tAverage unrest: " + averageUnrest
		+ "\n\tWar exhaustion: " + warExhaustion
		+ "\n\tReligious unity: " + religiousUnity + "%"
		+ "\n\tPrestige: " + prestige
		+ "\n\tPower projection: " + powerProjection
		+ "\n\tSplendor: " + splendor
		+ "\n\tScore: " + score
		+ "\n\tAbsolutism: " + absolutism
		+ "\n\tTech: " + admTech + ", " + dipTech + ", " + milTech
		+ "\n\tGolden age started: " + Util.printDate(goldenAge)
		+ "\n\tIs part HRE: " + Util.printBoolean(isPartHRE)
		+ "\n\tTrade bonus: " + printTradeBonus()
		+ "\n\tRivals: " + Util.printCountryList(rivals)
		+ "\n\tAllies: " + Util.printCountryList(allies)
		+ "\n\tDependencies: " + printDependencies()
		+ "\n\tProvinces: " + printProvinces()
		+ "\n\tStates: " + printStates()
		+ "\n\tAdvisors: " + printAdvisors()
		+ "\n\tAncients country name: " + Util.printCountryList(ancientsTags)
		+ "\n";
    }
    
    public int getAdmTech() {
		return admTech;
	}
    
    public ArrayList<String> getAllies() {
		return allies;
	}
    
    public double getCash() {
		return cash;
	}
    
    public String getCulture() {
		return culture;
	}
    
    public int getDebt() {
		return debt;
	}
    
    public int getDev() {
		return dev;
	}
    
    public int getDipTech() {
		return dipTech;
	}
    
    public int getForceLimit() {
		return forceLimit;
	}
    
    public double getIncome() {
		return income;
	}
    
    public int getLosses() {
		return losses;
	}
    
    public int getMaxManpower() {
		return maxManpower;
	}
    
    public int getMilTech() {
		return milTech;
	}
    
    public String getName() {
		return name;
	}
    
    public int getNbProvince() {
		return nbProvince;
	}
    
    public int getPowerProjection() {
		return powerProjection;
	}
    
    public double getPrestige() {
		return prestige;
	}
    
	public BigDecimal getProfessionalism() {
		return professionalism;
	}    
    
    public HashMap<Integer, Province> getProvinces() {
		return provinces;
	}
    
    public String getReligion() {
		return religion;
	}
    
    public int getMaxSailors() {
		return maxSailors;
	}
    
    public String getTag() {
		return tag;
	}
    
    public void setAdmTech(int admTech) {
		this.admTech = admTech;
	}
    
    public void setAllies(ArrayList<String> allies) {
		this.allies = allies;
	}
    
    public void setCash(double cash) {
		this.cash = cash;
	}
    
    public void setCulture(String culture) {
		this.culture = culture;
	}
    
    public void setDebt(int debt) {
		this.debt = debt;
	}
    
    public void setDev(int dev) {
		this.dev = dev;
	}
    
    public void setDipTech(int dipTech) {
		this.dipTech = dipTech;
	}
    
    public void setForceLimit(int forceLimit) {
		this.forceLimit = forceLimit;
	}
    
    public void setIncome(double f) {
		this.income = f;
	}
    
    public void setLosses(int losses) {
		this.losses = losses;
	}
    
    public void setMaxManpower(int maxManpower) {
		this.maxManpower = maxManpower;
	}
    
    public void setMilTech(int milTech) {
		this.milTech = milTech;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    public void setNbProvince(int nbProvince) {
		this.nbProvince = nbProvince;
	}
    
    public void setPowerProjection(int powerProjection) {
		this.powerProjection = powerProjection;
	}
    
    public void setPrestige(double prestige) {
		this.prestige = prestige;
	}
    
    public void setProfessionalism(BigDecimal professionalism) {
		this.professionalism = professionalism;
	}
    
    public void setProvinces(HashMap<Integer, Province> provinces) {
		this.provinces = provinces;
	}
    
    public void setReligion(String religion) {
		this.religion = religion;
	}
    
    public void setMaxSailors(int maxSailors) {
		this.maxSailors = maxSailors;
	}
    
    public void setTag(String tag) {
		this.tag = tag;
	}

	public int getSplendor() {
		return splendor;
	}

	public void setSplendor(int splendor) {
		this.splendor = splendor;
	}

	public int getMercantilism() {
		return mercantilism;
	}

	public void setMercantilism(int mercantilism) {
		this.mercantilism = mercantilism;
	}

	public double getAbsolutism() {
		return absolutism;
	}

	public void setAbsolutism(double absolutism) {
		this.absolutism = absolutism;
	}

	public int getStability() {
		return stability;
	}

	public void setStability(int stability) {
		this.stability = stability;
	}

	public double getLegitimacy() {
		return legitimacy;
	}

	public void setLegitimacy(double legitimacy) {
		this.legitimacy = legitimacy;
	}

	public int getGovRank() {
		return govRank;
	}

	public void setGovRank(int govRank) {
		this.govRank = govRank;
	}

	public GregorianCalendar getGoldenAge() {
		return goldenAge;
	}

	public void setGoldenAge(GregorianCalendar goldenAge) {
		this.goldenAge = goldenAge;
	}

	public double getRealmDev() {
		return realmDev;
	}

	public void setRealmDev(double realmDev) {
		this.realmDev = realmDev;
	}

	public ArrayList<Boolean> getContinents() {
		return continents;
	}

	public void setContinents(ArrayList<Boolean> continents) {
		this.continents = continents;
	}

	public ArrayList<Boolean> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(ArrayList<Boolean> institutions) {
		this.institutions = institutions;
	}

	public ArrayList<String> getRivals() {
		return rivals;
	}

	public void setRivals(ArrayList<String> rivals) {
		this.rivals = rivals;
	}
	
	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

	public int getArmy() {
		return army;
	}

	public void setArmy(int army) {
		this.army = army;
	}

	public int getMercenaries() {
		return mercenaries;
	}

	public void setMercenaries(int mercenaries) {
		this.mercenaries = mercenaries;
	}

	public double getAverageAutonomy() {
		return averageAutonomy;
	}

	public void setAverageAutonomy(double averageAutonomy) {
		this.averageAutonomy = averageAutonomy;
	}

	public double getAverageUnrest() {
		return averageUnrest;
	}

	public void setAverageUnrest(double averageUnrest) {
		this.averageUnrest = averageUnrest;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getInflation() {
		return inflation;
	}

	public void setInflation(double inflation) {
		this.inflation = inflation;
	}

	public double getWarExhaustion() {
		return warExhaustion;
	}

	public void setWarExhaustion(double warExhaustion) {
		this.warExhaustion = warExhaustion;
	}

	public double getArmyTradition() {
		return armyTradition;
	}

	public void setArmyTradition(double armyTradition) {
		this.armyTradition = armyTradition;
	}

	public double getNavyTradition() {
		return navyTradition;
	}

	public void setNavyTradition(double navyTradition) {
		this.navyTradition = navyTradition;
	}

	public BigDecimal getReligiousUnity() {
		return religiousUnity;
	}

	public void setReligiousUnity(BigDecimal religiousUnity) {
		this.religiousUnity = religiousUnity;
	}

	public HashMap<Integer, Advisor> getAdvisors() {
		return advisors;
	}

	public void setAdvisors(HashMap<Integer, Advisor> advisors) {
		this.advisors = advisors;
	}

	public LinkedHashMap<String, Integer> getIdeas() {
		return ideas;
	}

	public void setIdeas(LinkedHashMap<String, Integer> ideas) {
		this.ideas = ideas;
	}

	public int getManpower() {
		return manpower;
	}

	public void setManpower(int manpower) {
		this.manpower = manpower;
	}

	public int getSailors() {
		return sailors;
	}

	public void setSailors(int sailors) {
		this.sailors = sailors;
	}

	public boolean isRevolutionTarget() {
		return isRevolutionTarget;
	}

	public void setRevolutionTarget(boolean isRevolutionTarget) {
		this.isRevolutionTarget = isRevolutionTarget;
	}

	public ArrayList<Integer> getTradeBonus() {
		return tradeBonus;
	}

	public void setTradeBonus(ArrayList<Integer> tradeBonus) {
		this.tradeBonus = tradeBonus;
	}

	public boolean isHREEmperor() {
		return isHREEmperor;
	}

	public void setHREEmperor(boolean isHREEmperor) {
		this.isHREEmperor = isHREEmperor;
	}

	public boolean isPartHRE() {
		return isPartHRE;
	}

	public void setPartHRE(boolean isPartHRE) {
		this.isPartHRE = isPartHRE;
	}
	
	public HashMap<Country, String> getDependencies() {
		return dependencies;
	}

	public void setDependencies(HashMap<Country, String> dependencies) {
		this.dependencies = dependencies;
	}

	public String getGovType() {
		return govType;
	}

	public void setGovType(String govType) {
		this.govType = govType;
	}

	public ArrayList<String> getStates() {
		return states;
	}

	public void setStates(ArrayList<String> states) {
		this.states = states;
	}	
}
