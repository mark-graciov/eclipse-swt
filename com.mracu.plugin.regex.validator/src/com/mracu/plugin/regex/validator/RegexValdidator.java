package com.mracu.plugin.regex.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate an expression using user-defined pattern
 * 
 * @author Mihail Racu
 * @since version 1.00
 * **/
public class RegexValdidator {
	private String patternWord;
	private String expWord;

	/**
	 * Return result of validation
	 * 
	 * @since version 1.00
	 * **/
	public boolean validateRegex() {
		boolean isValid;
		Pattern pattern = Pattern.compile(patternWord);
		Matcher matcher = pattern.matcher(expWord);
		isValid = matcher.matches();
		return isValid;
	}

	public String getPatternWord() {
		return patternWord;
	}

	public void setPatternWord(String patternWord) {
		this.patternWord = patternWord;
	}

	public String getExpWord() {
		return expWord;
	}

	public void setExpWord(String expWord) {
		this.expWord = expWord;
	}

}
