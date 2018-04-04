package eu4SaveReader.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import eu4SaveReader.General.Eu4File;
import eu4SaveReader.General.Player;
import eu4SaveReader.Utils.Util;

public class Excel {

	private String filePath;
	private int nbSession;
	
	public Excel(String filePath) {
		this.filePath = filePath;
	}
	
	public ArrayList<Player> extractPlayers() throws FileNotFoundException {
		FileInputStream excelFile;
        Workbook workbook;
        ArrayList<Player> players = new ArrayList<Player>();
        
		try {
			excelFile = new FileInputStream(new File(filePath));
			workbook = new XSSFWorkbook(excelFile);
	        Sheet generalInfoSheet = workbook.getSheetAt(0);
	        
	        Row namesRow = generalInfoSheet.getRow(0);
	        Row tagsRow = generalInfoSheet.getRow(2);
	        
	        Iterator<Cell> namesIterator = namesRow.iterator();
	        Iterator<Cell> tagsIterator = tagsRow.iterator();
	        namesIterator.next();
	        tagsIterator.next();
	        
            while (namesIterator.hasNext() && tagsIterator.hasNext()) {
                Cell nameCell = namesIterator.next();
                Cell tagCell = tagsIterator.next();
                
                if (nameCell.getCellTypeEnum() == CellType.STRING && tagCell.getCellTypeEnum() == CellType.STRING) {
                    players.add(new Player(nameCell.getStringCellValue(), tagCell.getStringCellValue()));
                }
            }
            
            Cell nbSessionCell = workbook.getSheetAt(1).getRow(1).getCell(3);
            
            if(nbSessionCell != null) {
            	nbSession = (int) nbSessionCell.getNumericCellValue();
            }
            
            workbook.close();
            excelFile.close();
		} catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
			e.printStackTrace();
		}
		
		return players;
	}
	
	public void writeInfos(Eu4File save) {
		FileInputStream excelFile;
		FileOutputStream updatedExcelFile;
        XSSFWorkbook workbook;
        
        try {
			excelFile = new FileInputStream(new File(filePath));
			workbook = new XSSFWorkbook(excelFile);
			XSSFSheet generalInfoSheet = workbook.getSheetAt(0);
			
			int numSessionFirstRow = (nbSession) * 9 + 3;
	        Row infosRow = generalInfoSheet.getRow(numSessionFirstRow);
	        Cell endDateCell = infosRow.getCell(5);
	        endDateCell.setCellValue(Util.printDate(save.getCurrentDate()));
	        
	        for(Player p : save.getPlayers()) {
	        	Row tagsRow = generalInfoSheet.getRow(2);
	        	Iterator<Cell> tagsIterator = tagsRow.cellIterator();
	        	
	        	while(tagsIterator.hasNext() && !tagsIterator.next().getStringCellValue().equals(p.getCountry().getTag())) {
	        		
	        	}
	        	
	        	int playerColumn = tagsIterator.next().getAddress().getColumn() - 1;
	        	
	        	generalInfoSheet.getRow(numSessionFirstRow + 1).getCell(playerColumn).setCellValue(p.getCountry().getDev());
	        	generalInfoSheet.getRow(numSessionFirstRow + 2).getCell(playerColumn).setCellValue(p.getCountry().getIncome());
	        	generalInfoSheet.getRow(numSessionFirstRow + 3).getCell(playerColumn).setCellValue(p.getCountry().getMaxManpower());
	        	generalInfoSheet.getRow(numSessionFirstRow + 4).getCell(playerColumn).setCellValue(p.getCountry().getForceLimit());
	        	generalInfoSheet.getRow(numSessionFirstRow + 5).getCell(playerColumn).setCellValue(p.getCountry().getNbProvince());
	        	generalInfoSheet.getRow(numSessionFirstRow + 6).getCell(playerColumn).setCellValue(p.getCountry().getLosses());
	        	generalInfoSheet.getRow(numSessionFirstRow + 7).getCell(playerColumn).setCellValue(p.getCountry().getDebt());
	        	generalInfoSheet.getRow(numSessionFirstRow + 8).getCell(playerColumn).setCellValue(p.getCountry().getProfessionalism().divide(new BigDecimal(100)).doubleValue());
	        }
	        
	        excelFile.close();
			
			updatedExcelFile = new FileOutputStream(new File(filePath));
            workbook.write(updatedExcelFile);
            
            workbook.close();
            updatedExcelFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public int getNbSession() {
		return nbSession;
	}
}
