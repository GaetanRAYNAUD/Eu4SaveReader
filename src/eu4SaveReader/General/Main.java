package eu4SaveReader.General;

public class Main {
	
	public static void main(String[] args) {
	    String savePath = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\mp_dimanche\\session16.eu4";
//		String savePath = "C:\\Users\\gaeta\\Documents\\Paradox Interactive\\Europa Universalis IV\\save games\\Bosnie.eu4";
	    Eu4File save = new Eu4File(savePath);
	    
	    save.addPlayer(new Player("Assassinblanc", "SCA"));
	    save.addPlayer(new Player("Meteo", "FRA"));
	    save.addPlayer(new Player("UAV-alpha", "POL"));
	    save.addPlayer(new Player("warpc", "GBR"));
	    save.addPlayer(new Player("Shy", "NED"));

//	    save.addPlayer(new Player("MNG"));
	    
	    save.extractPlayersInfos();
	    
	    System.out.println("Date : " + save.getCurrentDate());

//	    System.out.println(save.getPlayerByTag("MNG"));
	    System.out.println(save.getPlayerByTag("SCA"));
	    System.out.println(save.getPlayerByTag("FRA"));
	    System.out.println(save.getPlayerByTag("POL"));
	    System.out.println(save.getPlayerByTag("GBR"));
	    System.out.println(save.getPlayerByTag("NED"));
	}
}
