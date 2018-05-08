package eu4SaveReader.Utils;

import java.util.HashMap;

public final class Dependencies {

    private Dependencies () {
    }

    public static final HashMap<String, String> dependenciesTypes = new HashMap<>();

    static {
        dependenciesTypes.put("vassal", "Vassal");
        dependenciesTypes.put("march", "March");
        dependenciesTypes.put("client_vassal", "Client state");
        dependenciesTypes.put("personal_union", "Personnal union");
        dependenciesTypes.put("colony", "Colony");
        dependenciesTypes.put("tributary_state", "Tributary");
        dependenciesTypes.put("daimyo_vassal", "Daimyo");
    }

    public static final HashMap<String, String> dependenciesTypesFR = new HashMap<>();

    static {
        dependenciesTypesFR.put("vassal", "Vassal");
        dependenciesTypesFR.put("march", "Marche");
        dependenciesTypesFR.put("client_vassal", "État client");
        dependenciesTypesFR.put("personal_union", "Union personnelle");
        dependenciesTypesFR.put("colony", "Colonie");
        dependenciesTypesFR.put("tributary_state", "Tributaire");
        dependenciesTypesFR.put("daimyo_vassal", "Daimyo");

    }
}
