package eu4SaveReader.General;

public class Main {
	
	public static void main(String[] args) {
//	    String savePath = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\mp_dimanche\\session16.eu4";
		String savePath = "C:\\Users\\gaeta\\Documents\\Paradox Interactive\\Europa Universalis IV\\save games\\Bosnie.eu4";
	    Eu4File save = new Eu4File(savePath);
	    
	    save.addPlayer(new Player("Assassinblanc", "SWE"));
	    save.addPlayer(new Player("Meteo", "FRA"));
	    save.addPlayer(new Player("UAV-alpha", "PLC"));
	    save.addPlayer(new Player("warpc", "GBR"));
	    save.addPlayer(new Player("Shy", "NED"));
	    save.addPlayer(new Player("Shy", "RUS"));
	    save.addPlayer(new Player("QNG"));
	    
	    save.extractPlayersInfos();
	    
	    System.out.println("Date : " + save.getCurrentDate());

	    System.out.println(save.getPlayerByTag("SWE"));
	    System.out.println(save.getPlayerByTag("FRA"));
	    System.out.println(save.getPlayerByTag("PLC"));
	    System.out.println(save.getPlayerByTag("GBR"));
	    System.out.println(save.getPlayerByTag("NED"));
	    System.out.println(save.getPlayerByTag("RUS"));
	    System.out.println(save.getPlayerByTag("QNG"));
	}
}
