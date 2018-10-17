package eu4SaveReader.General;

import eu4SaveReader.Utils.Areas;
import eu4SaveReader.Utils.Util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

public class Eu4File {

    private String save;
    private String saveFilePath;
    private ArrayList<Player> players = new ArrayList<>();
    private GregorianCalendar currentDate;

    public Eu4File (String savePath) {
        loadSave(savePath);
        extractDate();
    }

    private void loadSave (String saveFilePath) {
        this.saveFilePath = saveFilePath;

        try {
            save = new String(Files.readAllBytes(Paths.get(saveFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void extractDate () {
        int startAddr, endAddr;

        startAddr = save.indexOf("date=") + 5;
        if (startAddr != - 1) {
            endAddr = save.indexOf("\n", startAddr);
            currentDate = Util.convertStringToDate(save.substring(startAddr, endAddr));
        } else {
            currentDate = new GregorianCalendar(1444, 11, 11);
        }
    }

    private void extractInfosCountry (Country country) {
        int startAddr, endAddr;
        String countryInfos, provinceInfos;

        if ((startAddr = save.indexOf("\n\t" + country.getTag() + "={")) != - 1) {
            startAddr += 2;
            endAddr = save.indexOf("subject_focus=", startAddr);
            endAddr = save.indexOf("subject_focus=", endAddr + 1);
            countryInfos = save.substring(startAddr, endAddr);
            endAddr = countryInfos.lastIndexOf("}") + 1;
            countryInfos = countryInfos.substring(0, endAddr);
            country.extractInfos(countryInfos);
        }

        for (Entry<Integer, Province> entryProv : country.getProvinces().entrySet()) {
            Province p = entryProv.getValue();

            startAddr = save.indexOf("\n-" + p.getId() + "={") + 5;
            if (startAddr != - 1) {
                endAddr = save.indexOf("}\n-", startAddr);
                if (endAddr == - 1) {
                    endAddr = save.indexOf("countries={", startAddr);
                }
                provinceInfos = save.substring(startAddr, endAddr);
                p.extractInfos(provinceInfos);
            }
        }
    }

    private void extractStates () {
        int startAddr, endAddr;

        if ((startAddr = save.indexOf("map_area_data{")) != - 1) {
            startAddr += 14;
            endAddr = save.indexOf("\n}\n", startAddr);

            String areasInfos = save.substring(startAddr, endAddr);

            Areas.areas.forEach((key, value) -> {
                if (areasInfos.contains(key)) {
                    int beginAddr = areasInfos.indexOf("area=\"" + key + "\"");

                    if (beginAddr > - 1) {
                        int stopAddr = areasInfos.indexOf("\n\t\t}\n", beginAddr);

                        String areaInfos = areasInfos.substring(beginAddr, stopAddr);
                        List<String> countries = new ArrayList<>(Arrays.asList(areaInfos.split("country_state=\\{")));
                        countries.remove(0);
                        countries.forEach(country -> {
                            int countryAddr = country.indexOf("country=");

                            if (countryAddr > - 1) {
                                String countryTag = country.substring(countryAddr + 9, country.indexOf("\n", countryAddr + 8) - 1);

                                Optional<Player> player = players.stream().filter(player1 -> player1.getCountry().getTag().equalsIgnoreCase(countryTag)).findFirst();

                                player.ifPresent(player1 -> player1.getCountry().addState(key));
                            }
                        });
                    }
                }
            });
        }
    }

    private void updateDependencyName (Country country) {
        int startAddr, endAddr;
        String countryInfos;

        if ((startAddr = save.indexOf("\n\t" + country.getTag() + "={")) != - 1) {
            startAddr += 2;
            endAddr = save.indexOf("subject_focus=", startAddr);
            endAddr = save.indexOf("subject_focus=", endAddr + 1);
            countryInfos = save.substring(startAddr, endAddr);
            endAddr = countryInfos.lastIndexOf("}") + 1;
            countryInfos = countryInfos.substring(0, endAddr);
            country.updateName(countryInfos);
        }
    }

    private void updateDependencyNbProvinces (Country country) {
        int startAddr, endAddr;
        String countryInfos;

        if ((startAddr = save.indexOf("\n\t" + country.getTag() + "={")) != - 1) {
            startAddr += 2;
            endAddr = save.indexOf("subject_focus=", startAddr);
            endAddr = save.indexOf("subject_focus=", endAddr + 1);
            countryInfos = save.substring(startAddr, endAddr);
            endAddr = countryInfos.lastIndexOf("}") + 1;
            countryInfos = countryInfos.substring(0, endAddr);
            country.updataNbProvince(countryInfos);
        }
    }

    private HashMap<Country, String> extractDependenciesPlayer (Player player) {
        int startAddr = 0;
        int endAddr;
        String dependencyString, overloadTag, dependencyTag, dependencyType;
        HashMap<Country, String> dependencies = new HashMap<Country, String>();

        while ((startAddr = save.indexOf("dependency={", startAddr)) != - 1) {
            startAddr = startAddr + 15;
            endAddr = save.indexOf("}", startAddr) - 1;
            dependencyString = save.substring(startAddr, endAddr);

            overloadTag = Util.extractInfo(dependencyString, "first=\"").replace("\"", "");
            if (overloadTag.equals(player.getCountry().getTag())) {
                dependencyTag = Util.extractInfo(dependencyString, "second=\"").replace("\"", "");
                Country dependency = new Country(dependencyTag);

                if (dependencyTag.matches("^.[0-9]*$")) {
                    updateDependencyName(dependency);
                }

                dependencyType = Util.extractInfo(dependencyString, "subject_type=\"").replace("\"", "");

                if (dependencyType.equals("colony")) {
                    updateDependencyNbProvinces(dependency);
                }

                dependencies.put(dependency, dependencyType);
            }
        }

        return dependencies;
    }

    public void extractPlayersInfos () {
        int nbCountriesHRE = 0;

        for (Player p : players) {
            extractInfosCountry(p.getCountry());
            if (p.getCountry().isPartHRE()) {
                nbCountriesHRE++;
            }
        }

        extractStates();

        for (Player p : players) {
            p.getCountry().updateProvinceBasedInfos();
            p.addDependencies(extractDependenciesPlayer(p));
            p.updateForceLimit(nbCountriesHRE);
        }
    }

    public void printPlayersInfo () {
        try {
            Files.write(Paths.get(save.substring(0, save.length() - 3) + "txt"), toListString(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> toListString () {
        List<String> list = new ArrayList<>();

        list.add("Date : " + Util.printDate(currentDate));

        for (Player p : players) {
            list.addAll(p.toListString());
        }

        return list;
    }

    public Player getPlayerByTag (String tag) {
        for (Player p : players) {
            if (p.getCountry().getTag().equals(tag)) {
                return p;
            }
        }
        return null;
    }

    public Player getPlayerByName (String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public void addPlayer (Player newPlayer) {
        players.add(newPlayer);
    }

    public void addPlayers (ArrayList<Player> players) {
        this.players.addAll(players);
    }

    public ArrayList<Player> getPlayers () {
        return players;
    }

    public String getSave () {
        return save;
    }

    public GregorianCalendar getCurrentDate () {
        return currentDate;
    }

    public String getSaveFilePath () {
        return saveFilePath;
    }
}
