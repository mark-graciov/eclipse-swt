package com.mracu.plugin.regex.validator;

import java.io.InputStream;

final public class ResourceLoader {

	public static InputStream load(String path) {
		InputStream is = ResourceLoader.class.getResourceAsStream(path);
		if (is == null) {
			is = ResourceLoader.class.getResourceAsStream("/" + path);
		}
		return is;
	}
}
