package eu4SaveReader.Utils;

import java.util.HashMap;

public final class Advisors {

	private Advisors() {}

	public static final HashMap<String, String> advisorsType = new HashMap<String, String>();
	static {
		advisorsType.put("philosopher", "Philosopher");
		advisorsType.put("natural_scientist", "Natural Scientist");
		advisorsType.put("artist", "Artist");
		advisorsType.put("statesman", "Statesman");
		advisorsType.put("treasurer", "Treasurer");
		advisorsType.put("naval_reformer", "Naval Reformer");
		advisorsType.put("army_reformer", "Army Reformer");
		advisorsType.put("trader", "Trader");
		advisorsType.put("theologian", "Theologian");
		advisorsType.put("spymaster", "Spymaster");
		advisorsType.put("colonial_governor", "Colonial Governor");
		advisorsType.put("diplomat", "Diplomat");
		advisorsType.put("master_of_mint", "Master of Mint");
		advisorsType.put("navigator", "Navigator");
		advisorsType.put("army_organiser", "Army Organizer");
		advisorsType.put("commandant", "Commandant");
		advisorsType.put("quartermaster", "Quartermaster");
		advisorsType.put("recruitmaster", "Master Recruiter");
		advisorsType.put("fortification_expert", "Military Engineer");
		advisorsType.put("inquisitor", "Inquisitor");
		advisorsType.put("grand_captain", "Grand Captain");
	}
}
