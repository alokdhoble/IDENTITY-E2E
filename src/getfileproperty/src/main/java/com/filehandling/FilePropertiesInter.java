package com.filehandling;

import java.io.IOException;
import java.util.List;

public interface FilePropertiesInter {
	public List<FileProperty> checkFileProperty(String sourceDir) throws IOException;
}
