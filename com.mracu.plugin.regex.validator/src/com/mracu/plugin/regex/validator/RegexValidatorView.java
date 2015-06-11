package com.mracu.plugin.regex.validator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;

public class RegexValidatorView extends ViewPart {
	private static final String[] ICONS = { "icons/no_text.png",
			"icons/valid.png", "icons/not_valid.png" };
	private FormToolkit toolkit;
	private Form form;
	Label imageLabel;
	Text patternText;
	Text valueText;

	@Override
	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createForm(parent);
		form.setText("Regex Validator");
		TableWrapLayout layout = new TableWrapLayout();
		form.getBody().setLayout(layout);
		layout.numColumns = 2;

		TableWrapData td = new TableWrapData();
		td.colspan = 2;
		Label patternLabel = toolkit.createLabel(form.getBody(), "Pattern");
		patternText = toolkit.createText(form.getBody(), "");
		td = new TableWrapData(TableWrapData.FILL_GRAB);
		patternText.setLayoutData(td);

		Label valueLabel = toolkit.createLabel(form.getBody(), "Value");
		valueText = toolkit.createText(form.getBody(), "");
		td = new TableWrapData(TableWrapData.FILL_GRAB);
		valueText.setLayoutData(td);

		td = new TableWrapData(TableWrapData.FILL);
		td.heightHint = 32;
		imageLabel = toolkit.createLabel(form.getBody(), "");
		imageLabel.setLayoutData(td);

		Button validateButton = toolkit.createButton(form.getBody(),
				"Validate", SWT.PUSH);
		td = new TableWrapData(TableWrapData.RIGHT);
		validateButton.setLayoutData(td);

		validateButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				drawValidation();
			}

		});
	}

	public void drawValidation() {
		if (patternText.getText() == "" && valueText.getText() == "") {
			imageLabel.setImage(new Image(form.getDisplay(), ResourceLoader
					.load(ICONS[0])));
		} else {
			RegexValdidator regexValdidator = new RegexValdidator();
			regexValdidator.setPatternWord(patternText.getText());
			regexValdidator.setExpWord(valueText.getText());

			boolean isValid = regexValdidator.validateRegex();
			if (isValid) {
				imageLabel.setImage(new Image(form.getDisplay(), ResourceLoader
						.load(ICONS[1])));
			} else {
				imageLabel.setImage(new Image(form.getDisplay(), ResourceLoader
						.load(ICONS[2])));
			}
		}
	}

	@Override
	public void setFocus() {
		form.setFocus();

	}

	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

}
