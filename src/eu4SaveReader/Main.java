package eu4SaveReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import eu4SaveReader.Web.page.Session;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main (String[] args) {
        String session4Path = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\Session 4\\kebab_4.eu4";
/*	    String excelPath = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\kebab finally gets removed.xlsx";

        EnumSet<GamePlayer> gamePlayers = GamePlayer.getAllPlayers();

	    Eu4File save = new Eu4File(session4Path);
*//*	    Excel excel = new Excel(excelPath);

	    try {
			save.addPlayers(excel.extractPlayers());
		} catch (FileNotFoundException e) {
			System.out.println("File not found or open in other program");
			System.exit(1);
		}*//*

        for(GamePlayer p : gamePlayers) {
            Player newPlayer = new Player(p.getName(), p.getTag());
            save.addPlayer(newPlayer);
        }

	    save.extractPlayersInfos();

*//*	    excel.writeInfos(save);*//*

	    System.out.println(save);*/

        Session session4 = new Session(session4Path, 4);
        Path file = Paths.get("C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\Web\\session4.js");

        try {
            Files.write(file, session4.toScript(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(session4.toScript());
    }
}
