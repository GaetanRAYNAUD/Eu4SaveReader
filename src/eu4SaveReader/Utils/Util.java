package eu4SaveReader.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public final class Util {

	private Util() {}
	
	public static final List<String> mouth = Arrays.asList("December", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November");
	
	public static final List<String> govRanks = Arrays.asList("", "Duchy", "Kingdom", "Empire");
	
	public static final List<String> continents = Arrays.asList("Europe", "Asia", "Africa", "North America" , "South America", "Oceania");
	
	public static final List<String> institutions = Arrays.asList("Feudalism", "Renaissance", "Colonialism", "Printing Press", "Global Trade", "Manufactories", "Enlightenment");
	
    public static final String extractInfo(String countryInfos, String landmark) {
    	int startAddr, endAddr;
    	String info;

	    if((startAddr = countryInfos.indexOf(landmark)) != -1) {
	    	startAddr += landmark.length();
	    	endAddr = countryInfos.indexOf("\n", startAddr);
	    	info = countryInfos.substring(startAddr, endAddr);
	    	info = info.replace("\n", "");
	    	info = info.replace("\t", "");
	    	info = info.replace("\r", "");
	    	
	    	return info;
	    }
	    
		return null;
    }
    
	public static final GregorianCalendar convertStringToDate(String dateString) {
		String[] date = dateString.split("\\.");
		return new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
	}
	
    public static final String printCountryList(ArrayList<String> list) {
    	if(list.size() > 0) {
	    	StringBuilder listString = new StringBuilder();
	    	
	    	for(String l : list) {
	    		listString.append(Tags.tags.get(l));
	    		listString.append(", ");
	    	}
	    	
	    	return listString.toString().substring(0, listString.toString().length() - 2);
    	}
    	
    	return "";
    }
    
    public static final String printDate(GregorianCalendar date) {
    	return date.get(GregorianCalendar.DAY_OF_MONTH) + " " + Util.mouth.get(date.get(GregorianCalendar.MONTH)) + " " + date.get(GregorianCalendar.YEAR);
    }
    
    public static final String printBoolean(Boolean bool) {
    	return (bool ? "Yes" : "No");
    }
}
