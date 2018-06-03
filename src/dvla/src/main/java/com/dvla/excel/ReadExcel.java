package com.dvla.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dvla.dataobject.VehicleDetails;
import com.dvla.inter.DvlaVehicle;

public class ReadExcel implements DvlaVehicle {
	final static Logger logger = Logger.getLogger(ReadExcel.class);
	private static ReadExcel excel = null;

	private ReadExcel() {

	}

	public static ReadExcel getInstance() {
		synchronized (ReadExcel.class) {
			if (excel == null) {
				excel = new ReadExcel();
			}

		}
		return excel; 
	}

	@Override
	public List<VehicleDetails> getVehicleDetails(String filePath) {
		logger.info("Entry point for readExcel() method ");
		if (filePath == null || "".equals(filePath.trim())) {
			logger.error("Source Path is empty... filePath -->" + filePath + "<--");
			return null;

		} else {
			File fl = new File(filePath);
			if (!fl.exists() || !fl.isFile()) {
				logger.error(
						"Invalid source Path OR filePath is a direcotry and not a fully qualified file path... filePath -->"
								+ filePath + "<--");
				return null;
			} else {
				String fileName = fl.getName();
				if (fileName.lastIndexOf(".") == -1 ) {
					logger.error( "Please give file name with extention..." );
					return null;
				}
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				String excelExtentions = "xls,xlsx,xlm,xla";
				if ( ext.equals(null) || ext.trim().equals("") || !excelExtentions.toLowerCase().contains( ext.toLowerCase() )  ) {
					logger.error( "Given file is not excel file... " );
					return null;
				}
			}

		}
		List<VehicleDetails> dtls = null;
		try {
			dtls = readExcel(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage(), e);
		}

		// TODO Auto-generated method stub
		return dtls;
	}

	private List<VehicleDetails> readExcel(String filePath) throws IOException {
		List<VehicleDetails> dtls = null;

		FileInputStream excelFile = new FileInputStream(new File(filePath));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		VehicleDetails vehicle = null;
		dtls = new ArrayList<VehicleDetails>();
		while (iterator.hasNext()) {

			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			int i = 1;
			vehicle = new VehicleDetails();
			while (cellIterator.hasNext()) {

				Cell currentCell = cellIterator.next();
				switch (i) {
				case 1:
					vehicle.setVehicleNumber(currentCell.getStringCellValue().trim());
					break;
				case 2:
					vehicle.setVehicleColor(currentCell.getStringCellValue().trim());
					break;
				case 3:
					vehicle.setVehicleModel(currentCell.getStringCellValue().trim());
					break;
				}
				i++;

				if (i == 4) {
					break;
				}
			}
			dtls.add(vehicle);

		}

		return dtls;

	}

}
