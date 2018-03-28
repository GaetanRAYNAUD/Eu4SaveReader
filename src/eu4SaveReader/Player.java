package eu4SaveReader;

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
    
    public void extractInfos(String playerInfos) {
    	country.extractInfos(playerInfos);
    }
    
    public void updateProvinceBasedInfos() {
    	country.updateProvinceBasedInfos();
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
