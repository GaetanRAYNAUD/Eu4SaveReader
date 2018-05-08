package eu4SaveReader.General;

import eu4SaveReader.Utils.Players;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Session {

    private int num;
    private Eu4File save;
    private HashMap<String, String> gamePlayers;
    private Map<String, Player> playersMap = new HashMap<>();
    private Session previousSession;

    public Session (String savePath, int num) {
        this.num = num;
        save = new Eu4File(savePath);
        gamePlayers = Players.getSessions().get(num - 1);

        for (Entry<String, String> p : gamePlayers.entrySet()) {
            Player newPlayer = new Player(p.getKey(), p.getValue());
            save.addPlayer(newPlayer);
            playersMap.put(p.getKey(), newPlayer);
        }

        save.extractPlayersInfos();
    }

    public Session (String savePath, int num, Session previousSession) {
        this(savePath, num);
        this.previousSession = previousSession;
    }

    public List<String> toListString () {
        return save.toListString();
    }

    public Eu4File getSave () {
        return save;
    }
}
