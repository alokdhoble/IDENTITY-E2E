package com.filehandling;

/**
 * These calls are use to hold file property.
 * @author Alok Dhoble
 *
 */
public class FileProperty {

	private String fileDirectory = null;
	private String fileName = null;
	private String fileExtention = null;
	private String fileMimeType = null;
	private String fileSize = null;
	
	public String getFileDirectory() {
		return fileDirectory;
	}
	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExtention() {
		return fileExtention;
	}
	public void setFileExtention(String fileExtention) {
		this.fileExtention = fileExtention;
	}
	public String getFileMimeType() {
		return fileMimeType;
	}
	public void setFileMimeType(String fileMimeType) {
		this.fileMimeType = fileMimeType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append("Directory -->").append(getFileDirectory())
			.append(", FileName -->").append(getFileName())
			.append(", FileSize -->").append(getFileSize())
			.append(", FileExtension -->").append(getFileExtention())
			.append(", FileMimeType -->").append(getFileMimeType());
		
		return strb.toString();
	}
	
}
