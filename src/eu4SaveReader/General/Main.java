package eu4SaveReader.General;

import eu4SaveReader.Excel.Excel;

public class Main {
	
	public static void main(String[] args) {
	    String savePath = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\mp_dimanche\\session16.eu4";
	    String excelPath = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\mp_dimanche\\gpo_dimanche - Copie.xlsx";
	    
	    Eu4File save = new Eu4File(savePath);
	    Excel excel = new Excel(excelPath);
	    
	    save.addPlayers(excel.extractPlayers());

	    save.extractPlayersInfos();
	    
	    excel.writeInfos(save);
	    
	    System.out.println(save);
	}
}
