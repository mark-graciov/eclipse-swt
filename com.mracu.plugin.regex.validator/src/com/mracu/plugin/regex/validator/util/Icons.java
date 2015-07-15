package com.mracu.plugin.regex.validator.util;

public enum Icons {
	NO_TEXT("icons/no_text.png"), NOT_VALID("icons/not_valid.png"), REGEX_BOMB(
			"icons/regexBomb.png"), VALID("icons/valid.png");

	private String icon;

	private Icons(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return this.icon;
	}
}
