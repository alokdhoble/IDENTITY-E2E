package com.dvla.inter;

import java.util.List;

import com.dvla.dataobject.VehicleDetails;

public interface DvlaVehicle {

	public List<VehicleDetails> getVehicleDetails(String filePath);
}
