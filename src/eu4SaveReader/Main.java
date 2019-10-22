package eu4SaveReader;

import eu4SaveReader.General.Game;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main (String[] args) {
        List<String> sessionsFilesPath = new ArrayList<>();
        String session1Path = "D:\\projets\\perso\\saves\\session1.eu4";
        String session2Path = "D:\\projets\\perso\\saves\\session2.eu4";
        String session3Path = "D:\\projets\\perso\\saves\\session3.eu4";
        String session4Path = "D:\\projets\\perso\\saves\\session4.eu4";

        Path exportFilePath = Paths.get("D:\\projets\\perso\\saves\\data.js");

        String title = "Le bon, la brute et le blobeur";

        sessionsFilesPath.add(session1Path);
        sessionsFilesPath.add(session2Path);
        sessionsFilesPath.add(session3Path);
        sessionsFilesPath.add(session4Path);

        Game game = new Game(sessionsFilesPath, exportFilePath, title);

        game.export();
    }
}
