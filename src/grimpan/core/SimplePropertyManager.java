package grimpan.core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SimplePropertyManager implements PropertyManager {
	private Properties panProperties = null;
	private String propertyFile = null;
	
	public SimplePropertyManager(){
		this.panProperties = new Properties();
	}
	public SimplePropertyManager(String filename){
		this();
		this.propertyFile = filename;

		if (!loadProperties()){
			System.out.println("***Properties File Load Failed***");
		}
	}

	@Override
	public boolean loadProperties(){
		boolean success = true;
		try {
			panProperties.load(getClass().getResourceAsStream(propertyFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@Override
	public boolean storeProperties(){
		boolean success = true;
		try {
			panProperties.store(new FileOutputStream(propertyFile), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@Override
	public Object updateProperty(String pkey, String pvalue){
		Object result =  panProperties.setProperty(pkey, pvalue);
		storeProperties();
		return result;
	}

	@Override
	public Properties getPanProperties() {
		return panProperties;
	}

	@Override
	public void setPropertyFileName(String propertyFile) {
		this.propertyFile = propertyFile;
		loadProperties();
	}
}
