package org.ashish.acoolgames.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CommonUtil {
	private static final Logger logger = Logger.getLogger(CommonUtil.class);
	
	public static Properties loadPropertiesFile(String propFilePath) throws IOException 
	{
		logger.info(System.getProperty("java.class.path"));
		Properties dbProperties = new Properties();
		
		String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir:"+current);
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" +currentDir);
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
		
		try(FileInputStream fileInputStream = new FileInputStream(propFilePath)) 
		{
			dbProperties.load(fileInputStream);
		} catch (IOException ex) 
		{
			logger.error("Exception while loading properties : " + ex);
			//throw new RuntimeException(ex);
		}
		
		try(FileInputStream fileInputStream = new FileInputStream("localDataSource.properties"))
		{
			
		}catch (IOException ex) 
		{
			logger.error("Exception while loading properties : " + ex);
			//throw new RuntimeException(ex);
		}
		
		
		return dbProperties;
	}
}
