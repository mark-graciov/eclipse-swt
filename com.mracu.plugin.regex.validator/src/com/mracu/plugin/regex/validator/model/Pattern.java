package com.mracu.plugin.regex.validator.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Pattern implements Serializable {
	private static final long serialVersionUID = 1L;

	private String patternName;
	private String patternValue;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public Pattern(String patternName, String patternValue) {
		super();
		this.patternName = patternName;
		this.patternValue = patternValue;
	}

	public Pattern() {
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String patternName) {
		propertyChangeSupport.firePropertyChange("patternName",
				this.patternName, this.patternName = patternName);
	}

	public String getPatternValue() {
		return patternValue;
	}

	public void setPatternValue(String patternValue) {
		propertyChangeSupport.firePropertyChange("patternExpression",
				this.patternValue,
				this.patternValue = patternValue);
	}
}
