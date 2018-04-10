package eu4SaveReader.General;

import java.io.FileNotFoundException;

import eu4SaveReader.Excel.Excel;

public class Main {
	
	public static void main(String[] args) {
	    String savePath = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\Session 2\\kebab finally gets removed session 2.eu4";
	    String excelPath = "C:\\Users\\gaeta\\OneDrive\\Documents\\Eu4\\kebab finally gets removed\\kebab finally gets removed.xlsx";
	    
	    Eu4File save = new Eu4File(savePath);
	    Excel excel = new Excel(excelPath);
	    
	    try {
			save.addPlayers(excel.extractPlayers());
		} catch (FileNotFoundException e) {
			System.out.println("File not found or open in other program");
			System.exit(1);
		}

	    save.extractPlayersInfos();
	    
	    excel.writeInfos(save);
	    
	    System.out.println(save);
	}
}
