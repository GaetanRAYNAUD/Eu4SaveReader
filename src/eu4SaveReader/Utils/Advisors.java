package eu4SaveReader.Utils;

import java.util.HashMap;

public final class Advisors {

    private Advisors () {
    }

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

    public static final HashMap<String, String> advisorsTypeFR = new HashMap<>();

    static {
        advisorsTypeFR.put("philosopher", "Philosophe");
        advisorsTypeFR.put("natural_scientist", "Scientifique");
        advisorsTypeFR.put("artist", "Artiste");
        advisorsTypeFR.put("statesman", "Homme d'État");
        advisorsTypeFR.put("treasurer", "Financier");
        advisorsTypeFR.put("naval_reformer", "Réformateur naval");
        advisorsTypeFR.put("army_reformer", "Réformateur militaire");
        advisorsTypeFR.put("trader", "Négociant");
        advisorsTypeFR.put("theologian", "Théologien");
        advisorsTypeFR.put("spymaster", "Maître-espion");
        advisorsTypeFR.put("colonial_governor", "Gouverneur colonial");
        advisorsTypeFR.put("diplomat", "Diplomate");
        advisorsTypeFR.put("master_of_mint", "Maître de la monnaie");
        advisorsTypeFR.put("navigator", "Navigateur");
        advisorsTypeFR.put("army_organiser", "Intendant aux armées");
        advisorsTypeFR.put("commandant", "Maître-instructeur");
        advisorsTypeFR.put("quartermaster", "Fourrier");
        advisorsTypeFR.put("recruitmaster", "Maître-recruteur");
        advisorsTypeFR.put("fortification_expert", "Ingénieur militaire");
        advisorsTypeFR.put("inquisitor", "Inquisiteur");
        advisorsTypeFR.put("grand_captain", "Grand capitaine");

    }
}
