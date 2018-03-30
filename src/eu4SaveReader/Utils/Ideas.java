package eu4SaveReader.Utils;

import java.util.HashMap;

public final class Ideas {
	
	private Ideas() {}
	
	public static final HashMap<String, String> ideas = new HashMap<String, String>();
	static {
		ideas.put("administrative_ideas", "Administrative");
		ideas.put("aristocracy_ideas", "Aristocratic");
		ideas.put("defensive_ideas", "Defensive");
		ideas.put("diplomatic_ideas", "Diplomatic");
		ideas.put("economic_ideas", "Economic");
		ideas.put("expansion_ideas", "Expansion");
		ideas.put("exploration_ideas", "Exploration");
		ideas.put("humanist_ideas", "Humanist");
		ideas.put("influence_ideas", "Influence");
		ideas.put("innovativeness_ideas", "Innovative");
		ideas.put("maritime_ideas", "Maritime");
		ideas.put("naval_ideas", "Naval");
		ideas.put("offensive_ideas", "Offensive");
		ideas.put("plutocracy_ideas", "Plutocratic");
		ideas.put("quality_ideas", "Quality");
		ideas.put("quantity_ideas", "Quantity");
		ideas.put("religious_ideas", "Religious");
		ideas.put("spy_ideas", "Espionage");
		ideas.put("trade_ideas", "Trade");
	}
}
