package com.mracu.plugin.regex.validator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
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
	private FormToolkit toolkit;
	private Form form;
	private static final String[] ICONS = { "icons/no_text.png",
			"icons/valid.png", "icons/not_valid.png" };
	private ImageDescriptor ICON;

	// private Label imageLabel;

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
		final Text patternText = toolkit.createText(form.getBody(), "");
		td = new TableWrapData(TableWrapData.FILL_GRAB);
		patternText.setLayoutData(td);

		Label valueLabel = toolkit.createLabel(form.getBody(), "Value");
		final Text valueText = toolkit.createText(form.getBody(), "");
		td = new TableWrapData(TableWrapData.FILL_GRAB);
		valueText.setLayoutData(td);

		
//		final Label imageLabel = toolkit.createLabel(form.getBody(),
//				"dsdasdasd");
		Button validateButton = toolkit.createButton(form.getBody(),
				"Validate", SWT.PUSH);
		td = new TableWrapData(TableWrapData.RIGHT);
		validateButton.setLayoutData(td);
		td.colspan = 2;
		validateButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				if (patternText.getText() == "" && valueText.getText() == "") {
//					ICON = Activator.getImageDescriptor(ICONS[0]);
//					imageLabel.setImage(ICON.createImage());
					System.out.println("Type texte please!");
				} else {

					RegexValdidator regexValdidator = new RegexValdidator();

					regexValdidator.setPatternWord(patternText.getText());
					regexValdidator.setExpWord(valueText.getText());

					boolean isValid = regexValdidator.validateRegex();
					if (isValid) {
//						ICON = Activator.getImageDescriptor(ICONS[1]);
//						imageLabel.setImage(ICON.createImage());
						System.out.println("Expression is valid!");
					} else {
//						ICON = Activator.getImageDescriptor(ICONS[2]);
//						imageLabel.setImage(ICON.createImage());
						System.out.println("Expression is not valid!");
					}

				}

			}
		});
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
