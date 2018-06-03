package com.dvla.factory;

import com.dvla.csv.ReadCSV;
import com.dvla.excel.ReadExcel;
import com.dvla.inter.DvlaVehicle;

public class DVLAFactory {
	public DvlaVehicle getVehicleDetailsFromFile(String fileExtn) {
		DvlaVehicle vehicle = null;
		if (fileExtn == null || fileExtn.trim().equals("")) {
			return null;
		} else if (fileExtn.trim().equals("csv")) {
			vehicle = ReadCSV.getInstance();
			return vehicle;
		} else if (fileExtn.trim().equals("xls") || fileExtn.trim().equals("xlsx")) {
			vehicle = ReadExcel.getInstance();
			return vehicle;
		}

		return null;

	}

}
