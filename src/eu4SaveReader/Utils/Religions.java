package eu4SaveReader.Utils;

import java.util.HashMap;

public final class Religions {
	
	private Religions() {}
	
	public static final HashMap<String, String> religions = new HashMap<String, String>();
	static {
		religions.put("catholic", "Catholic");
		religions.put("protestant", "Protestant");
		religions.put("reformed", "Reformed");
		religions.put("anglican", "Anglican");		
		religions.put("orthodox", "Orthodox");
		religions.put("coptic", "Coptic");	
		religions.put("sunni", "Sunni");
		religions.put("shiite", "Shia");
		religions.put("ibadi", "Ibadi");
		religions.put("buddhism", "Theravada");
		religions.put("vajrayana", "Vajrayana");		
		religions.put("mahayana", "Mahayana");
		religions.put("confucianism", "Confucian");
		religions.put("shinto", "Shinto");
		religions.put("hinduism", "Hindu");
		religions.put("sikhism", "Sikh");
		religions.put("animism", "Animist");
		religions.put("shamanism", "Fetishist");
		religions.put("totemism", "Totemist");
		religions.put("inti", "Inti");
		religions.put("nahuatl", "Nahuatl");
		religions.put("mesoamerican_religion", "Mayan");
		religions.put("tengri_pagan_reformed", "Tengri");
		religions.put("norse_pagan_reformed", "Norse");		
		religions.put("jewish", "Jewish");
		religions.put("zoroastrian", "Zoroastrian");
	};
}
