package com.mracu.plugin.regex.validator.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;

public class PatternDataStore {
	private URL url;

	public void writePatternData(ArrayList<Pattern> patternData) {

		try {
			url = new URL(
					"platform:/plugin/com.mracu.plugin.regex.validator/files/pattern_data.txt");
			File file = new File(FileLocator.resolve(url).toURI());
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(patternData);
			oos.close();
			fos.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException ioe) {

			ioe.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Pattern> readPatternData() {
		ArrayList<Pattern> patternData = new ArrayList<Pattern>();
		try {
			url = new URL(
					"platform:/plugin/com.mracu.plugin.regex.validator/files/pattern_data.txt");
			InputStream fis = url.openConnection().getInputStream();
			ObjectInputStream ois = new ObjectInputStream(fis);
			patternData = (ArrayList<Pattern>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
		}

		return patternData;
	}
}
