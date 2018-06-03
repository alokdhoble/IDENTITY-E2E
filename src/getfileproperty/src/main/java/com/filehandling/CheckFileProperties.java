package com.filehandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @author Alok Dhoble Description : This file is used to give following list of
 *         properties of each file available in the given folder. List of File
 *         Properties : 1. Directory path 2. File Name 3. File Extension 4. File
 *         Size 5. File Mime Type Caller class will instantiate this class and
 *         execute checkFileProperty method.
 * 
 *         This class is giving file property for only following mime Types 1.
 *         application/octet-stream 2. application/excel 3. application/x-excel
 *         4. application/x-msexcel 5. application/vnd.ms-excel 6.
 *         application/vnd.openxmlformats-officedocument.spreadsheetml.sheet 7.
 *         text/csv
 *
 */
public class CheckFileProperties implements FilePropertiesInter{

	private String sourceDir = null;
	final static Logger logger = Logger.getLogger(CheckFileProperties.class);

	private final float sizeKb = 1024.0f;
	private final float sizeMB = sizeKb * sizeKb;
	private final float sizeGB = sizeMB * sizeKb;
	private final float sizeTB = sizeGB * sizeKb;

	private final String ALLOWED_MIME_TYPES = "application/octet-stream,application/excel,application/x-excel,application/x-msexcel,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,text/csv";
	private static CheckFileProperties checkFiles = null;
	public static CheckFileProperties getInstance() throws IOException {
		
		synchronized(CheckFileProperties.class) {
			if ( checkFiles == null ) {
				checkFiles = new CheckFileProperties();
				
			}	
		}
		
		return checkFiles;
	}
	private CheckFileProperties() throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("input.properties");

		// load a properties file
		prop.load(input);
		String propFileLocation = prop.getProperty("propFileLocation");

		Properties props = new Properties();
		props.load(new FileInputStream(propFileLocation));
		PropertyConfigurator.configure(props);

		setSourceDir(prop.getProperty("source.directory"));

	}

	public static void main(String[] args) {
		CheckFileProperties prop = null;

		try {
			prop = CheckFileProperties.getInstance();
			List<FileProperty> list = prop.checkFileProperty(prop.getSourceDir());
			logger.info(list);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is the entry point to walk through all the file(s) available in
	 * source directory and its sub directory.
	 * 
	 * @param sourceDir
	 * @throws IOException
	 */
	public List<FileProperty> checkFileProperty(String sourceDir) throws IOException {
		logger.info("Entry point for checkFileProperty() method... ");
		List<FileProperty> fileList = null;
		if (sourceDir == null && sourceDir.trim().length() <= 0) {
			logger.error("Source Folder Name cannot be null/empty ...");
		} else {
			File currentDir = new File(sourceDir); // current directory

			if (!currentDir.exists())
				logger.error("Folder does not exist");
			else if (currentDir.isFile())
				logger.error("Please give folder directory");
			else if (currentDir.isDirectory())
				fileList = displayDirectoryContents(currentDir);
			else
				logger.error("Please give folder directory");

		}
		logger.info("Exit from checkFileProperty() method...");
		return fileList;
	}

	/**
	 * This is recursive function which will iterate through all the source
	 * folder and its sub-folders to find out properties of eligible mime Type.
	 * 
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	private List<FileProperty> displayDirectoryContents(File dir) throws IOException {
		logger.info("Entry point for displayDirectoryContents() method .... ");
		List<FileProperty> fileList = new ArrayList<FileProperty>();
		File[] files = dir.listFiles();
		String fileMimeType = null;
		FileProperty fileProp = null;
		for (File file : files) {
			if (file.isDirectory()) {
				logger.info("directory:" + file.getCanonicalPath());
				displayDirectoryContents(file);
			} else {
				fileMimeType = Files.probeContentType(file.toPath());

				if (fileMimeType != null && ALLOWED_MIME_TYPES.toUpperCase().contains(fileMimeType.toUpperCase())) {
					fileProp = new FileProperty();
					fileProp.setFileName(file.getName());
					fileProp.setFileDirectory(getFilePath(file));
					fileProp.setFileExtention(getFileExtension(file));
					fileProp.setFileMimeType(Files.probeContentType(file.toPath()));
					fileProp.setFileSize(giveFileSize(file.length()));
					fileList.add(fileProp);
					
					logger.info(fileProp.toString());
				}

			}
		}
		logger.info("Exit from displayDirectoryContents() method .... ");
		return fileList;

	}

	/**
	 * This method is use to get file path of the source file.
	 * @param file
	 * @return
	 */
	private String getFilePath(File file) {
		return file.getParent() != null ? file.getParent() : "./";
	}

	/**
	 * This method is use to get File Extension.
	 * @param file
	 * @return
	 */
	private String getFileExtension(File file) {
		String name = file.getName();
		String ext = "N/A";
		try {
			if (name.lastIndexOf(".") != -1)
				ext = name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ext;
	}

	/**
	 * This method gives file size in KB/MB/GB/TB.
	 * 
	 * @param fileSize
	 * @return
	 */
	private String giveFileSize(long fileSize) {

		DecimalFormat df = new DecimalFormat("0.00");
		if (fileSize < sizeMB)
			return df.format(fileSize / sizeKb) + " Kb";
		else if (fileSize < sizeGB)
			return df.format(fileSize / sizeMB) + " Mb";
		else if (fileSize < sizeTB)
			return df.format(fileSize / sizeGB) + " GB";
		return df.toString();
	}
	public String getSourceDir() {
		return sourceDir;
	}
	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}
}
