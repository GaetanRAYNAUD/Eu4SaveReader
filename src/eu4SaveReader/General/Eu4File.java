package eu4SaveReader.General;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;

import eu4SaveReader.Utils.Util;

public class Eu4File {
	
	private String save;
	private ArrayList<Player> players = new ArrayList<>();
	private GregorianCalendar currentDate;

	public Eu4File(String savePath) {
		loadSave(savePath);
		extractDate();
	}
	
	private void loadSave(String savePath) {
		try {
			save = new String(Files.readAllBytes(Paths.get(savePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void extractDate() {
	    int startAddr, endAddr;

	    startAddr = save.indexOf("date=") + 5;
	    if(startAddr != -1) {
	    	endAddr = save.indexOf("\n", startAddr);
	    	currentDate = Util.convertStringToDate(save.substring(startAddr, endAddr));
	    } else {
	    	currentDate = new GregorianCalendar(1444, 11, 11);
	    }    
	}
	
	private void extractInfosCountry(Country country) {
	    int startAddr, endAddr;
	    String countryInfos, provinceInfos;

	    if((startAddr = save.indexOf("\n\t" + country.getTag() + "={")) != -1) {
	    	startAddr += 2;
	    	endAddr = save.indexOf("subject_focus=", startAddr);
	    	endAddr = save.indexOf("subject_focus=", endAddr + 1);
	    	countryInfos = save.substring(startAddr, endAddr);
	    	endAddr = countryInfos.lastIndexOf("}") + 1;
	    	countryInfos = countryInfos.substring(0, endAddr);
	    	country.extractInfos(countryInfos);
	    }
	    
	    for(Entry<Integer, Province> entryProv : country.getProvinces().entrySet()) {
	    	Province p = entryProv.getValue();
	    	
		    startAddr = save.indexOf("\n-" + p.getId() + "={") + 5;
		    if(startAddr != -1) {
		    	endAddr = save.indexOf("}\n-", startAddr);
		    	if(endAddr == -1) {
		    		endAddr = save.indexOf("countries={", startAddr);
		    	}
		    	provinceInfos = save.substring(startAddr, endAddr);
			    p.extractInfos(provinceInfos);
		    }
	    }
	    
	    country.updateProvinceBasedInfos();
	}
	
	private void updateDependencyName(Country country) {
	    int startAddr, endAddr;
	    String countryInfos;
	    
	    if((startAddr = save.indexOf("\n\t" + country.getTag() + "={")) != -1) {
	    	startAddr += 2;
	    	endAddr = save.indexOf("subject_focus=", startAddr);
	    	endAddr = save.indexOf("subject_focus=", endAddr + 1);
	    	countryInfos = save.substring(startAddr, endAddr);
	    	endAddr = countryInfos.lastIndexOf("}") + 1;
	    	countryInfos = countryInfos.substring(0, endAddr);
	    	country.updateName(countryInfos);
	    }		
	}
	
	private void updateDependencyNbProvinces(Country country) {
	    int startAddr, endAddr;
	    String countryInfos;
	    
	    if((startAddr = save.indexOf("\n\t" + country.getTag() + "={")) != -1) {
	    	startAddr += 2;
	    	endAddr = save.indexOf("subject_focus=", startAddr);
	    	endAddr = save.indexOf("subject_focus=", endAddr + 1);
	    	countryInfos = save.substring(startAddr, endAddr);
	    	endAddr = countryInfos.lastIndexOf("}") + 1;
	    	countryInfos = countryInfos.substring(0, endAddr);
	    	country.updataNbProvince(countryInfos);
	    }
	}
	
	private HashMap<Country, String> extractDependenciesPlayer(Player player) {
		int startAddr = 0;
		int endAddr;
		String dependencyString, overloadTag, dependencyTag, dependencyType;
		HashMap<Country, String> dependencies = new HashMap<Country, String>();
		
	    while((startAddr = save.indexOf("dependency={", startAddr)) != -1) {
	    	startAddr =  startAddr + 15;
	    	endAddr = save.indexOf("}", startAddr) - 1;
	    	dependencyString = save.substring(startAddr, endAddr);
	    	
	    	overloadTag = Util.extractInfo(dependencyString, "first=\"").replace("\"", "");
	    	if(overloadTag.equals(player.getCountry().getTag())) {
	    		dependencyTag = Util.extractInfo(dependencyString, "second=\"").replace("\"", "");
	    		Country dependency = new Country(dependencyTag);
	    		
	    		if(dependencyTag.matches("^.[0-9]*$")) {
	    			updateDependencyName(dependency);
	    		}
	    		
	    		dependencyType = Util.extractInfo(dependencyString, "subject_type=\"").replace("\"", "");
	    		
	    		if(dependencyType.equals("colony")) {
	    			updateDependencyNbProvinces(dependency);
	    		}
	    		
	    		dependencies.put(dependency, dependencyType);
	    	}
	    }
	    
		return dependencies;
	}
	
	public void extractPlayersInfos() {
		int nbCountriesHRE = 0;
	    
		for(Player p : players) {
			extractInfosCountry(p.getCountry());
	    	if(p.getCountry().isPartHRE()) {
	    		nbCountriesHRE++;
	    	}
		}
		
		for(Player p : players) {
			p.addDependencies(extractDependenciesPlayer(p));
			p.updateForceLimit(nbCountriesHRE);
		}
	}
	
	@Override
	public String toString() {
		return "Date : " + Util.printDate(currentDate)
		+ "\n" + players.toString();
	    
	}
	
	public Player getPlayerByTag(String tag) {
		for(Player p : players) {
			if(p.getCountry().getTag().equals(tag)) {
				return p;
			}
		}
		return null;
	}

	public Player getPlayerByName(String name) {
		for(Player p : players) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public void addPlayer(Player newPlayer) {
		players.add(newPlayer);
	}
	
	public void addPlayers(ArrayList<Player> players) {
		this.players.addAll(players);
	}
	
	public ArrayList<Player> getPlayers() {
		return players;	
	}
	
	public String getSave() {
		return save;
	}
	
	public GregorianCalendar getCurrentDate() {
		return currentDate;
	}
}
