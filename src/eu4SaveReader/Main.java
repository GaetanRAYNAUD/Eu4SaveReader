package eu4SaveReader;

import eu4SaveReader.General.Game;
import eu4SaveReader.General.Session;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main (String[] args) {
        List<String> sessionsFilesPath = new ArrayList<>();
        String session1Path = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\Session 1\\kebab_1.eu4";
        String session2Path = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\Session 2\\kebab_2.eu4";
        String session3Path = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\Session 3\\kebab_3.eu4";
        String session4Path = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\Session 4\\kebab_4.eu4";
        String session5Path = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\Session 5\\kebab_5.eu4";
        String session6Path = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\Session 6\\kebab_6.eu4";

        Path exportFilePath = Paths.get("C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\Web\\scripts\\data.js");

        String title = "Kebab finally gets removed";

        sessionsFilesPath.add(session1Path);
        sessionsFilesPath.add(session2Path);
        sessionsFilesPath.add(session3Path);
        sessionsFilesPath.add(session4Path);
        sessionsFilesPath.add(session5Path);
        sessionsFilesPath.add(session6Path);

        Game game = new Game(sessionsFilesPath, exportFilePath, title);

        game.export();
    }
}
