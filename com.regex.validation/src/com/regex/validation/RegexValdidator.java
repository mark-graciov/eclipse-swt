package com.regex.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValdidator {
	private String patternWord;
	private String expWord;

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
