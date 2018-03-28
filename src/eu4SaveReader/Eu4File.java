package eu4SaveReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map.Entry;

public class Eu4File {
	
	private String save;
	private ArrayList<Player> players = new ArrayList<Player>();
	private String currentDate;
	private int nbCountriesHRE;
	
	public Eu4File(String savePath) {
		loadSave(savePath);
		extractDate();
	}
	
	public void extractPlayersInfos() {
		for(Player p : players) {
			extractInfosPlayer(p);
		}
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
	    	currentDate = save.substring(startAddr, endAddr);
	    } else {
	    	currentDate = "1444.11.11";
	    }    
	}
	
	private void extractInfosPlayer(Player player) {
	    int startAddr, endAddr;
	    String playerInfos, provinceInfos;

	    startAddr = save.indexOf("\n\t" + player.getCountry().getTag() + "={") + 2;
	    if(startAddr != -1) {
	    	endAddr = save.indexOf("subject_focus=", startAddr);
	    	endAddr = save.indexOf("subject_focus=", endAddr + 1);
	    	playerInfos = save.substring(startAddr, endAddr);
	    	endAddr = playerInfos.lastIndexOf("}") + 1;
	    	playerInfos = playerInfos.substring(0, endAddr);
		    player.extractInfos(playerInfos);
	    }
	    
	    for(Entry<Integer, Province> entryProv : player.getCountry().getProvinces().entrySet()) {
	    	Province p = entryProv.getValue();
	    	
		    startAddr = save.indexOf("\n-" + p.getId() + "={") + 5;
		    if(startAddr != -1) {
		    	endAddr = save.indexOf("}\n-", startAddr);
		    	provinceInfos = save.substring(startAddr, endAddr);
			    p.extractInfos(provinceInfos);
		    }
	    }
	    
	    player.updateProvinceBasedInfos();
		
	    nbCountriesHRE = 0;
	    for(Player p : players) {
	    	if(p.getCountry().isPartHRE()) {
	    		nbCountriesHRE++;
	    	}
		}
	    
	    player.updateForceLimit(nbCountriesHRE);
	}
	
	public ArrayList<Player> getPlayers() {
		return players;	
	}
	
	public Player getPlayerByTag(String tag) {
		for(Player p : players) {
			if(p.getCountry().getTag() == tag) {
				return p;
			}
		}
		return null;
	}
	
	public void addPlayer(Player newPlayer) {
		players.add(newPlayer);
	}
	
	public String getSave() {
		return save;
	}
	
	public String getCurrentDate() {
		return currentDate;
	}
}
