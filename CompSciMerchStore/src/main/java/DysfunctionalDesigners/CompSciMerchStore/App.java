package DysfunctionalDesigners.CompSciMerchStore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger logger = Logger.getLogger(App.class.getName());
    public static void main( String[] args )
    {
    	try {//ask how to disable all java.awt logging
			InputStream configFile = new FileInputStream("src/main/resources/logger.properties");
			LogManager.getLogManager().readConfiguration(configFile);
			configFile.close();
		} catch (IOException ex) {
			System.out.println("WARNING: Could not open configuration file");
		    System.out.println("WARNING: Logging not configured (console output only)");
		}
    	logger.info("Starting main app");

        new GUIdo_Frame();
    }
}
