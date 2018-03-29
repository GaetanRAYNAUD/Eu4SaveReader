package eu4SaveReader.General;

import java.util.HashMap;

public class Player {

    private String name;
    private Country country;
    
    public Player(String tag) {
		this.country = new Country(tag);
		name = "AI";
	}
    
    public Player(String name, String tag) {
    	this.country = new Country(tag);
		this.name = name;
    }
    
    public void addDependencies(HashMap<Country, String> dependencies) {
    	country.addDependencies(dependencies);
    }
    
    public void updateForceLimit(int nbCountriesHRE) {
    	country.updateForceLimit(nbCountriesHRE);
    }
    
    @Override
    public String toString() {
		return name + " : " + country.toString();
    }
    
    public Country getCountry() {
		return country;
	}
    
    public String getName() {
		return name;
	}
    
    public void setCountry(Country country) {
		this.country = country;
	}
    
    public void setName(String name) {
		this.name = name;
	}
}
