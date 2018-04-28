package eu4SaveReader;

import java.util.EnumSet;

public enum GamePlayer {

    Player1("Sharqu", "FRA"),
    Player2("Ohroms", "GBR"),
    Player3("Poseidon", "SPA"),
    Player4("Meteo", "HAB"),
    Player5("Ormace", "HUN"),
    Player6("UAV-alpha", "RUS"),
    Player7("Thitub", "POL"),
    Player8("Genesis", "BRA"),
    Player9("Sisao", "SWE"),
    Player10("Nalox", "BRB"),
    Player11("Zeleph", "UKR"),
    Player12("Stellaria", "WES"),
    Player13("Assassin blanc", "KOJ"),
    Player14("Pietrobu", "POR"),
    Player15("Morgenstar", "NAP"),
    Player16("Tokipant", "MLO"),
    Player17("Emacab", "SWI"),
    Player18("Darmius", "CND"),
    Player19("Elpha", "MOR"),
    Player20("Bnlover", "TUN"),
    Player21("Buffalo", "ETH"),
    Player22("Geronimo", "YEM"),
    Player23("Sasaynel", "MSY"),
    Player24("El Battory", "PER"),
    Player25("Aikiko", "SRV"),
    Player26("Skarn", "BYZ"),
    Player27("Shingacook", "KAZ"),
    Player28("Scoutix", "DAN"),
    Player29("Robindesoibs", "VEN"),
    Player30("Azrock", "CHG"),
    Player31("Vélo", "VIJ"),
    Player32("Ocelot", "BAH"),
    Player33("Miox", "ORI"),
    Player34("Prince", "MER"),
    Player35("Labtec", "MAD"),
    Player36("Jayllos112", "PUN"),
    Player37("Sephirt", "NPL");

    private String name;
    private String tag;

    GamePlayer(String name, String tag){
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public static EnumSet<GamePlayer> getAllPlayers() {
        return EnumSet.allOf(GamePlayer.class);
    }
}
