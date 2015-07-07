package com.mracu.plugin.regex.validator.viewer;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mracu.plugin.regex.validator.model.Pattern;

public class ListLabelPrivider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		return super.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof Pattern) {
			return ((Pattern) element).getPatternName();
		}
		return null;
	}
}
