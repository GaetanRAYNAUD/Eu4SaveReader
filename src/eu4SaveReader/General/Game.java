package eu4SaveReader.General;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import eu4SaveReader.Utils.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

public class Game {

    private String title;
    private Path exportFilePath;
    private List<Session> sessions = new ArrayList<>();

    private List<Integer> totalDev = new ArrayList<>();
    private List<Integer> totalLosses = new ArrayList<>();
    private List<Map<String, Integer>> devRank = new ArrayList<>();
    private List<Map<String, Double>> incomeRank = new ArrayList<>();
    private List<Map<String, Integer>> manpowerRank = new ArrayList<>();
    private List<Map<String, Integer>> forceLimitRank = new ArrayList<>();
    private List<Map<String, Integer>> nbProvRank = new ArrayList<>();
    private List<Map<String, Integer>> lossesRank = new ArrayList<>();
    private List<Map<String, Integer>> loanRank = new ArrayList<>();
    private List<Map<String, BigDecimal>> professionalismRank = new ArrayList<>();
    private List<Map<String, Double>> innovativenessRank = new ArrayList<>();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    public Game (List<String> sessionsFilesPath, Path exportFilePath, String title) {
        this.title = title;
        this.exportFilePath = exportFilePath;

        System.out.println("Processing session 1");
        sessions.add(new Session(sessionsFilesPath.get(0), 1));

        for (int i = 1; i < sessionsFilesPath.size(); i++) {
            System.out.println("Processing session " + (i + 1));
            sessions.add(new Session(sessionsFilesPath.get(i), i + 1, sessions.get(i - 1)));
        }

        for (Session ignored : sessions) {
            totalDev.add(0);
            totalLosses.add(0);

            devRank.add(new LinkedHashMap<>());
            incomeRank.add(new LinkedHashMap<>());
            manpowerRank.add(new LinkedHashMap<>());
            forceLimitRank.add(new LinkedHashMap<>());
            nbProvRank.add(new LinkedHashMap<>());
            lossesRank.add(new LinkedHashMap<>());
            loanRank.add(new LinkedHashMap<>());
            professionalismRank.add(new LinkedHashMap<>());
            innovativenessRank.add(new LinkedHashMap<>());
        }
    }

    private List<String> toScript () {
        List<String> script = new ArrayList<>();
        List<HashMap<String, String>> gamePlayers;

        JsonObject list = new JsonObject();
        JsonArray players = new JsonArray();
        JsonArray sessionsJson = new JsonArray();

        gamePlayers = Players.getSessions();

        for (String p : gamePlayers.get(0).keySet()) {
            players.add(playerToScript(p));
        }

        for (int i = 0; i < sessions.size(); i++) {
            sessionsJson.add(sessionInfoToScript(i));
        }

        sortRanksMaps();

        for (int k = 0; k < gamePlayers.get(0).size(); k++) {
            addRanksToPlayerScript(players.get(k).getAsJsonObject());
        }

        list.addProperty("title", title);
        list.addProperty("nbSessions", sessions.size());

        list.add("sessions", sessionsJson);
        list.add("players", players);

        script.add("const data = ");
        script.add(prettyGson.toJson(list));
        script.add(";");

        return script;
    }

    private JsonObject playerToScript (String pseudo) {
        int i = 0;
        JsonObject institutionPlayer = new JsonObject();
        JsonObject playerJson = new JsonObject();
        JsonObject sessionPlayerJson = new JsonObject();
        JsonArray sessionsPlayerJson = new JsonArray();
        Player player;
        playerJson.addProperty("pseudo", pseudo);

        player = sessions.get(0).getSave().getPlayerByName(pseudo);

        institutionPlayer.addProperty("feudalism", player.getCountry().getInstitutions().get(0));
        institutionPlayer.addProperty("renaissance", player.getCountry().getInstitutions().get(1));
        institutionPlayer.addProperty("colonialism", player.getCountry().getInstitutions().get(2));
        institutionPlayer.addProperty("printingPress", player.getCountry().getInstitutions().get(3));
        institutionPlayer.addProperty("globalTrade", player.getCountry().getInstitutions().get(4));
        institutionPlayer.addProperty("manufactories", player.getCountry().getInstitutions().get(5));
        institutionPlayer.addProperty("enlightenment", player.getCountry().getInstitutions().get(6));

        sessionPlayerJson.addProperty("tag", player.getCountry().getTag());
        sessionPlayerJson.addProperty("country", Tags.tagsFR.get(player.getCountry().getTag()));
        sessionPlayerJson.addProperty("culture", Cultures.culturesFR.get(player.getCountry().getCulture()));
        sessionPlayerJson.addProperty("religion", Religions.religionsFR.get(player.getCountry().getReligion()));
        sessionPlayerJson.addProperty("government", Governments.governementTypesFR.get(player.getCountry().getGovType()));
        sessionPlayerJson.addProperty("rank", player.getCountry().getGovRank());
        sessionPlayerJson.addProperty("capital", ProvincesIdFR.provincesId.get(player.getCountry().getCapital()));
        sessionPlayerJson.addProperty("dev", player.getCountry().getDev());
        sessionPlayerJson.addProperty("income", player.getCountry().getIncome());
        sessionPlayerJson.addProperty("manpower", player.getCountry().getMaxManpower());
        sessionPlayerJson.addProperty("forceLimit", player.getCountry().getForceLimit());
        sessionPlayerJson.addProperty("nbProv", player.getCountry().getNbProvince());
        sessionPlayerJson.addProperty("losses", player.getCountry().getLosses());
        sessionPlayerJson.addProperty("loan", player.getCountry().getDebt());
        sessionPlayerJson.addProperty("professionalism", player.getCountry().getProfessionalism());
        sessionPlayerJson.addProperty("innovativeness", player.getCountry().getInnovativeness());
        sessionPlayerJson.add("institutions", institutionPlayer);

        sessionsPlayerJson.add(sessionPlayerJson);

        devRank.get(0).put(player.getName(), player.getCountry().getDev());
        incomeRank.get(0).put(player.getName(), player.getCountry().getIncome());
        manpowerRank.get(0).put(player.getName(), player.getCountry().getMaxManpower());
        forceLimitRank.get(0).put(player.getName(), player.getCountry().getForceLimit());
        nbProvRank.get(0).put(player.getName(), player.getCountry().getNbProvince());
        lossesRank.get(0).put(player.getName(), player.getCountry().getLosses());
        loanRank.get(0).put(player.getName(), player.getCountry().getDebt());
        professionalismRank.get(0).put(player.getName(), player.getCountry().getProfessionalism());
        innovativenessRank.get(0).put(player.getName(), player.getCountry().getInnovativeness());

        totalDev.set(0, totalDev.get(0) + player.getCountry().getDev());
        totalLosses.set(0, totalLosses.get(0) + player.getCountry().getLosses());
        i++;

        for (int j = 1; j < sessions.size(); j++) {
            player = sessions.get(j).getSave().getPlayerByName(pseudo);
            Player previousPlayer = sessions.get(j - 1).getSave().getPlayerByName(pseudo);
            sessionPlayerJson = new JsonObject();

            BigDecimal devEvol;
            BigDecimal incomeEvol;
            BigDecimal manpowerEvol;
            BigDecimal forceLimitEvol;
            BigDecimal lossesEvol;
            BigDecimal loanEvol;

            if (player != null) {
                devEvol = new BigDecimal(player.getCountry().getDev()).divide(new BigDecimal(previousPlayer.getCountry().getDev()),
                        new MathContext(3, RoundingMode.HALF_EVEN)).add(new BigDecimal(- 1)).multiply(new BigDecimal(100))
                        .setScale(0, RoundingMode.HALF_EVEN);

                incomeEvol = new BigDecimal(player.getCountry().getIncome()).divide(new BigDecimal(previousPlayer.getCountry().getIncome()),
                        new MathContext(3, RoundingMode.HALF_EVEN)).add(new BigDecimal(- 1)).multiply(new BigDecimal(100))
                        .setScale(0, RoundingMode.HALF_EVEN);

                manpowerEvol = new BigDecimal(player.getCountry().getMaxManpower()).divide(new BigDecimal(previousPlayer.getCountry().getMaxManpower()),
                        new MathContext(3, RoundingMode.HALF_EVEN)).add(new BigDecimal(- 1)).multiply(new BigDecimal(100))
                        .setScale(0, RoundingMode.HALF_EVEN);

                forceLimitEvol = new BigDecimal(player.getCountry().getForceLimit()).divide(new BigDecimal(previousPlayer.getCountry().getForceLimit()),
                        new MathContext(3, RoundingMode.HALF_EVEN)).add(new BigDecimal(- 1)).multiply(new BigDecimal(100))
                        .setScale(0, RoundingMode.HALF_EVEN);

                int nbProvEvol = player.getCountry().getNbProvince() - previousPlayer.getCountry().getNbProvince();

                try {
                    lossesEvol = new BigDecimal(player.getCountry().getLosses()).divide(new BigDecimal(previousPlayer.getCountry().getLosses()),
                            new MathContext(3, RoundingMode.HALF_EVEN)).add(new BigDecimal(- 1)).multiply(new BigDecimal(100))
                            .setScale(0, RoundingMode.HALF_EVEN);
                } catch (ArithmeticException e) {
                    lossesEvol = new BigDecimal(0);
                }

                try {
                    loanEvol = new BigDecimal(player.getCountry().getDebt()).divide(new BigDecimal(previousPlayer.getCountry().getDebt()),
                            new MathContext(3, RoundingMode.HALF_EVEN)).add(new BigDecimal(- 1)).multiply(new BigDecimal(100))
                            .setScale(0, RoundingMode.HALF_EVEN);

                } catch (ArithmeticException e) {
                    loanEvol = new BigDecimal(0);
                }

                BigDecimal professionalismEvol = player.getCountry().getProfessionalism().add(previousPlayer.getCountry().getProfessionalism().negate())
                        .setScale(3, RoundingMode.HALF_EVEN);

                BigDecimal innovativenessEvol = new BigDecimal(player.getCountry().getInnovativeness()).add(new BigDecimal(previousPlayer.getCountry().getInnovativeness()).negate())
                        .setScale(3, RoundingMode.HALF_EVEN);

                institutionPlayer.addProperty("feudalism", player.getCountry().getInstitutions().get(0));
                institutionPlayer.addProperty("renaissance", player.getCountry().getInstitutions().get(1));
                institutionPlayer.addProperty("colonialism", player.getCountry().getInstitutions().get(2));
                institutionPlayer.addProperty("printingPress", player.getCountry().getInstitutions().get(3));
                institutionPlayer.addProperty("globalTrade", player.getCountry().getInstitutions().get(4));
                institutionPlayer.addProperty("manufactories", player.getCountry().getInstitutions().get(5));
                institutionPlayer.addProperty("enlightenment", player.getCountry().getInstitutions().get(6));

                sessionPlayerJson.addProperty("tag", player.getCountry().getTag());
                sessionPlayerJson.addProperty("country", Tags.tagsFR.get(player.getCountry().getTag()));
                sessionPlayerJson.addProperty("culture", Cultures.culturesFR.get(player.getCountry().getCulture()));
                sessionPlayerJson.addProperty("religion", Religions.religionsFR.get(player.getCountry().getReligion()));
                sessionPlayerJson.addProperty("government", Governments.governementTypesFR.get(player.getCountry().getGovType()));
                sessionPlayerJson.addProperty("rank", player.getCountry().getGovRank());
                sessionPlayerJson.addProperty("capital", ProvincesIdFR.provincesId.get(player.getCountry().getCapital()));
                sessionPlayerJson.addProperty("dev", player.getCountry().getDev());
                sessionPlayerJson.addProperty("devEvol", devEvol);
                sessionPlayerJson.addProperty("income", player.getCountry().getIncome());
                sessionPlayerJson.addProperty("incomeEvol", incomeEvol);
                sessionPlayerJson.addProperty("manpower", player.getCountry().getMaxManpower());
                sessionPlayerJson.addProperty("manpowerEvol", manpowerEvol);
                sessionPlayerJson.addProperty("forceLimit", player.getCountry().getForceLimit());
                sessionPlayerJson.addProperty("forceLimitEvol", forceLimitEvol);
                sessionPlayerJson.addProperty("nbProv", player.getCountry().getNbProvince());
                sessionPlayerJson.addProperty("nbProvEvol", nbProvEvol);
                sessionPlayerJson.addProperty("losses", player.getCountry().getLosses());
                sessionPlayerJson.addProperty("lossesEvol", lossesEvol);
                sessionPlayerJson.addProperty("loan", player.getCountry().getDebt());
                sessionPlayerJson.addProperty("loanEvol", loanEvol);
                sessionPlayerJson.addProperty("professionalism", player.getCountry().getProfessionalism());
                sessionPlayerJson.addProperty("professionalismEvol", professionalismEvol);
                sessionPlayerJson.addProperty("innovativeness", player.getCountry().getInnovativeness());
                sessionPlayerJson.addProperty("innovativenessEvol", innovativenessEvol);
                sessionPlayerJson.add("institutions", institutionPlayer);

                sessionsPlayerJson.add(sessionPlayerJson);

                devRank.get(j).put(player.getName(), player.getCountry().getDev());
                incomeRank.get(j).put(player.getName(), player.getCountry().getIncome());
                manpowerRank.get(j).put(player.getName(), player.getCountry().getMaxManpower());
                forceLimitRank.get(j).put(player.getName(), player.getCountry().getForceLimit());
                nbProvRank.get(j).put(player.getName(), player.getCountry().getNbProvince());
                lossesRank.get(j).put(player.getName(), player.getCountry().getLosses());
                loanRank.get(j).put(player.getName(), player.getCountry().getDebt());
                professionalismRank.get(j).put(player.getName(), player.getCountry().getProfessionalism());
                innovativenessRank.get(j).put(player.getName(), player.getCountry().getInnovativeness());

                totalDev.set(i, totalDev.get(i) + player.getCountry().getDev());
                totalLosses.set(i, totalLosses.get(i) + player.getCountry().getLosses());
                i++;
            }
        }

        playerJson.add("sessions", sessionsPlayerJson);

        return playerJson;
    }

    private JsonObject sessionInfoToScript (int i) {
        JsonObject sessionJson = new JsonObject();

        sessionJson.addProperty("startDate", dateFormat.format(sessions.get(i).getSave().getCurrentDate().getTime()));
        sessionJson.addProperty("totalDev", totalDev.get(i));
        sessionJson.addProperty("totalLosses", totalLosses.get(i));

        if (i > 0) {
            BigDecimal totalDevEvol = new BigDecimal(totalDev.get(i)).divide(new BigDecimal(totalDev.get(i - 1)), new MathContext(3, RoundingMode.HALF_EVEN))
                    .add(new BigDecimal(- 1)).multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_EVEN);
            BigDecimal totalLossesEvol = new BigDecimal(totalLosses.get(i)).divide(new BigDecimal(totalLosses.get(i - 1)), new MathContext(3, RoundingMode.HALF_EVEN))
                    .add(new BigDecimal(- 1)).multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_EVEN);

            sessionJson.addProperty("totalDevEvol", totalDevEvol);
            sessionJson.addProperty("totalLossesEvol", totalLossesEvol);
        }

        return sessionJson;
    }

    private void sortRanksMaps () {
        for (int i = 0; i < sessions.size(); i++) {
            List<Map.Entry<String, Integer>> devRankEntry = new ArrayList<>(devRank.get(i).entrySet());
            List<Map.Entry<String, Double>> incomeRankEntry = new ArrayList<>(incomeRank.get(i).entrySet());
            List<Map.Entry<String, Integer>> manpowerRankEntry = new ArrayList<>(manpowerRank.get(i).entrySet());
            List<Map.Entry<String, Integer>> forceLimitRankEntry = new ArrayList<>(forceLimitRank.get(i).entrySet());
            List<Map.Entry<String, Integer>> nbProvRankEntry = new ArrayList<>(nbProvRank.get(i).entrySet());
            List<Map.Entry<String, Integer>> lossesRankEntry = new ArrayList<>(lossesRank.get(i).entrySet());
            List<Map.Entry<String, Integer>> loanRankEntry = new ArrayList<>(loanRank.get(i).entrySet());
            List<Map.Entry<String, BigDecimal>> professionalismRankEntry = new ArrayList<>(professionalismRank.get(i).entrySet());
            List<Map.Entry<String, Double>> innovativenessRankEntry = new ArrayList<>(innovativenessRank.get(i).entrySet());

            devRankEntry.sort(Map.Entry.comparingByValue());
            incomeRankEntry.sort(Map.Entry.comparingByValue());
            manpowerRankEntry.sort(Map.Entry.comparingByValue());
            forceLimitRankEntry.sort(Map.Entry.comparingByValue());
            nbProvRankEntry.sort(Map.Entry.comparingByValue());
            lossesRankEntry.sort(Map.Entry.comparingByValue());
            loanRankEntry.sort(Map.Entry.comparingByValue());
            professionalismRankEntry.sort(Map.Entry.comparingByValue());
            innovativenessRankEntry.sort(Map.Entry.comparingByValue());

            Map<String, Integer> devRankSorted = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : devRankEntry) {
                devRankSorted.put(entry.getKey(), entry.getValue());
            }

            Map<String, Double> incomeRankSorted = new LinkedHashMap<>();
            for (Map.Entry<String, Double> entry : incomeRankEntry) {
                incomeRankSorted.put(entry.getKey(), entry.getValue());
            }

            Map<String, Integer> manpowerRankSorted = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : manpowerRankEntry) {
                manpowerRankSorted.put(entry.getKey(), entry.getValue());
            }

            Map<String, Integer> forceLimitRankSorted = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : forceLimitRankEntry) {
                forceLimitRankSorted.put(entry.getKey(), entry.getValue());
            }

            Map<String, Integer> nbProvRankSorted = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : nbProvRankEntry) {
                nbProvRankSorted.put(entry.getKey(), entry.getValue());
            }

            Map<String, Integer> lossesRankSorted = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : lossesRankEntry) {
                lossesRankSorted.put(entry.getKey(), entry.getValue());
            }

            Map<String, Integer> loanRankSorted = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : loanRankEntry) {
                loanRankSorted.put(entry.getKey(), entry.getValue());
            }

            Map<String, BigDecimal> professionalismRankSorted = new LinkedHashMap<>();
            for (Map.Entry<String, BigDecimal> entry : professionalismRankEntry) {
                professionalismRankSorted.put(entry.getKey(), entry.getValue());
            }

            Map<String, Double> innovativenessRankSorted = new LinkedHashMap<>();
            for (Map.Entry<String, Double> entry : innovativenessRankEntry) {
                innovativenessRankSorted.put(entry.getKey(), entry.getValue());
            }

            devRank.get(i).clear();
            devRank.get(i).putAll(devRankSorted);
            incomeRank.get(i).clear();
            incomeRank.get(i).putAll(incomeRankSorted);
            manpowerRank.get(i).clear();
            manpowerRank.get(i).putAll(manpowerRankSorted);
            forceLimitRank.get(i).clear();
            forceLimitRank.get(i).putAll(forceLimitRankSorted);
            nbProvRank.get(i).clear();
            nbProvRank.get(i).putAll(nbProvRankSorted);
            lossesRank.get(i).clear();
            lossesRank.get(i).putAll(lossesRankSorted);
            loanRank.get(i).clear();
            loanRank.get(i).putAll(loanRankSorted);
            professionalismRank.get(i).clear();
            professionalismRank.get(i).putAll(professionalismRankSorted);
            innovativenessRank.get(i).clear();
            innovativenessRank.get(i).putAll(innovativenessRankSorted);

            List<String> indexesDev = new LinkedList<>(devRank.get(i).keySet());
            List<String> indexesIncome = new LinkedList<>(incomeRank.get(i).keySet());
            List<String> indexesManpower = new LinkedList<>(manpowerRank.get(i).keySet());
            List<String> indexesForceLimit = new LinkedList<>(forceLimitRank.get(i).keySet());
            List<String> indexesNbProv = new LinkedList<>(nbProvRank.get(i).keySet());
            List<String> indexesLosses = new LinkedList<>(lossesRank.get(i).keySet());
            List<String> indexesLoan = new LinkedList<>(loanRank.get(i).keySet());
            List<String> indexesProfessionalism = new LinkedList<>(professionalismRank.get(i).keySet());
            List<String> indexesInnovativeness = new LinkedList<>(innovativenessRank.get(i).keySet());

            for (int l = 0; l < devRank.get(i).size(); l++) {
                devRank.get(i).put(indexesDev.get(l), indexesDev.size() - l);
                incomeRank.get(i).put(indexesIncome.get(l), (double) indexesIncome.size() - l);
                manpowerRank.get(i).put(indexesManpower.get(l), indexesManpower.size() - l);
                forceLimitRank.get(i).put(indexesForceLimit.get(l), indexesForceLimit.size() - l);
                nbProvRank.get(i).put(indexesNbProv.get(l), indexesNbProv.size() - l);
                lossesRank.get(i).put(indexesLosses.get(l), indexesLosses.size() - l);
                loanRank.get(i).put(indexesLoan.get(l), indexesLoan.size() - l);
                professionalismRank.get(i).put(indexesProfessionalism.get(l), new BigDecimal(indexesProfessionalism.size() - l));
                innovativenessRank.get(i).put(indexesInnovativeness.get(l), (double) indexesInnovativeness.size() - l);
            }
        }
    }

    private void addRanksToPlayerScript(JsonObject playerJson) {
        String pseudo = playerJson.get("pseudo").getAsString();
        JsonArray playerSessionsJson = playerJson.get("sessions").getAsJsonArray();

        for(int i = 0; i < playerSessionsJson.size(); i++) {
            JsonObject playerSessionJson = playerSessionsJson.get(i).getAsJsonObject();
            playerSessionJson.addProperty("devRank", devRank.get(i).get(pseudo));
            playerSessionJson.addProperty("incomeRank", incomeRank.get(i).get(pseudo).intValue());
            playerSessionJson.addProperty("manpowerRank", manpowerRank.get(i).get(pseudo));
            playerSessionJson.addProperty("forceLimitRank", forceLimitRank.get(i).get(pseudo));
            playerSessionJson.addProperty("nbProvRank", nbProvRank.get(i).get(pseudo));
            playerSessionJson.addProperty("lossesRank", lossesRank.get(i).get(pseudo));
            playerSessionJson.addProperty("loanRank", loanRank.get(i).get(pseudo));
            playerSessionJson.addProperty("professionalismRank", professionalismRank.get(i).get(pseudo).intValue());
            playerSessionJson.addProperty("innovativenessRank", innovativenessRank.get(i).get(pseudo).intValue());
        }
    }

    public void export () {
        try {
            Files.write(exportFilePath, toScript(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
