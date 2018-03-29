package eu4SaveReader.Utils;

import java.util.HashMap;

public final class Dependencies {

	private Dependencies() {}
	
	public static final HashMap<String, String> dependenciesTypes = new HashMap<String, String>();
	static {
		dependenciesTypes.put("vassal", "Vassal");
		dependenciesTypes.put("march", "March");
		dependenciesTypes.put("client_vassal", "Client state");
		dependenciesTypes.put("personal_union", "Personnal union");
		dependenciesTypes.put("colony", "Colony");
		dependenciesTypes.put("tributary_state", "Tributary");
		dependenciesTypes.put("daimyo_vassal", "Daimyo");
	}
}
