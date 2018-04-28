package eu4SaveReader.Utils;

import java.util.HashMap;

public final class Estates {

	private Estates() {}
	
	public static final HashMap<Integer, String> estates = new HashMap<>();
	static {
		estates.put(0, "None");
		estates.put(1, "Clergy");
		estates.put(2, "Nobility");
		estates.put(3, "Burghers");
		estates.put(4, "Cossacks");
		estates.put(5, "Tribes");
		estates.put(6, "Dhimmi");
	}
}
