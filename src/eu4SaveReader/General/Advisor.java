package eu4SaveReader.General;

import java.util.GregorianCalendar;

import eu4SaveReader.Utils.Advisors;
import eu4SaveReader.Utils.Cultures;
import eu4SaveReader.Utils.ProvincesId;
import eu4SaveReader.Utils.Religions;
import eu4SaveReader.Utils.Util;

public class Advisor {

	private int id;
	private String name;
	private String type;
	private int skill;
	private int birthProvince;
	private String culture;
	private String religion;
	private GregorianCalendar birthDate;
	
	public Advisor(int id) {
		
	}
	
	public Advisor(String advisorInfos) {
		extractInfos(advisorInfos);
	}
	
	private int extractId(String advisorInfos) {
    	int startAddr, endAddr;
    	int id = 0;

	    if((startAddr = advisorInfos.indexOf("id={")) != -1) {
	    	startAddr = advisorInfos.indexOf("id=", startAddr + 4);
	    	startAddr += 3;
	    	endAddr = advisorInfos.indexOf("\n", startAddr);
	    	id = Integer.parseInt(advisorInfos.substring(startAddr, endAddr));
	    }
	    
	    return id;
	}
	
	private void extractInfos(String advisorInfos) {
		id = extractId(advisorInfos);
		name = Util.extractInfo(advisorInfos, "name=").replace("\"", "");
		type = Util.extractInfo(advisorInfos, "type=");
		skill = Util.extractInfoInt(advisorInfos, "skill=");
		birthProvince = Util.extractInfoInt(advisorInfos, "location=");
		culture = Util.extractInfo(advisorInfos, "culture=");
		religion = Util.extractInfo(advisorInfos, "religion=");
		birthDate = Util.convertStringToDate(Util.extractInfo(advisorInfos, "date="));
	}
	
    @Override
    public String toString() {
		return "\n\tName: " + name
		+ "\n\tId: " + id
		+ "\n\tType: " + Advisors.advisorsType.get(type)
		+ "\n\tSkill: " + skill
		+ "\n\tBorn in: " + ProvincesId.provincesId.get(birthProvince)
		+ "\n\tBirth date: " + Util.printDate(birthDate)		
		+ "\n\tCulture: " + Cultures.cultures.get(culture)
		+ "\n\tReligion: " + Religions.religions.get(religion)	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public int getBirthProvince() {
		return birthProvince;
	}

	public void setBirthProvince(int birthProvince) {
		this.birthProvince = birthProvince;
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

	public GregorianCalendar getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(GregorianCalendar birthDate) {
		this.birthDate = birthDate;
	}
}
