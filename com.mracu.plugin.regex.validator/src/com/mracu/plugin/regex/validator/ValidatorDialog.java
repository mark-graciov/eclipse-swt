package com.mracu.plugin.regex.validator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * Create a dialog that contain fields for pattern and expression which must be
 * <p>
 * validated
 * 
 * @author Mihail Racu
 * @version 1.00
 * **/
public class ValidatorDialog {
	private static final String[] ICONS = { "icons/regexBomb.png",
			"icons/no_text.png", "icons/valid.png", "icons/not_valid.png" };
	private static final int SH_WIDTH = 320;
	private static final int SH_HEIGHT = 136;
	private Label imageLabel;
	private Text patternText;
	private Text valueText;
	private Shell shell;

	/**
	 * Constructor with a Composite parameter that create the validation box
	 * **/
	ValidatorDialog(Composite parent) {
		shell = new Shell(parent.getShell(), SWT.RESIZE | SWT.MAX | SWT.MIN);
		shell.setSize(SH_WIDTH, SH_HEIGHT);
		shell.setMinimumSize(SH_WIDTH, SH_HEIGHT);
		TableWrapLayout layout = new TableWrapLayout();
		shell.setLayout(layout);
		shell.setText("Regex Validator");

//		Image regexIcon = new Image(parent.getDisplay(), ResourceLoader
//				.load(ICONS[0]));
//		shell.setImage(regexIcon);

		layout.numColumns = 2;

		TableWrapData td = new TableWrapData();
		td.colspan = 2;
		Label patternLabel = new Label(shell, SWT.NONE);
		patternLabel.setText("Pattern");
		patternText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		patternText.setText("");
		td = new TableWrapData(TableWrapData.FILL_GRAB);
		patternText.setLayoutData(td);

		Label valueLabel = new Label(shell, SWT.NONE);
		valueLabel.setText("Value");
		valueText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		valueText.setText("");
		td = new TableWrapData(TableWrapData.FILL_GRAB);
		valueText.setLayoutData(td);

		td = new TableWrapData(TableWrapData.FILL);
		td.heightHint = 32;
		imageLabel = new Label(shell, SWT.NONE);
		imageLabel.setLayoutData(td);

		Button validateButton = new Button(shell, SWT.PUSH);
		validateButton.setText("Validate");
		td = new TableWrapData(TableWrapData.RIGHT);
		validateButton.setLayoutData(td);

		validateButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				drawValidation();
			}

		});

		shell.open();
	}

	/**
	 * Method draws an icon that confirm or not the validation
	 * 
	 * @since verion 1.00
	 * **/
	public void drawValidation() {
		if (patternText.getText() == "" && valueText.getText() == "") {
			imageLabel.setImage(new Image(shell.getDisplay(), ResourceLoader
					.load(ICONS[1])));
		} else {
			RegexValdidator regexValdidator = new RegexValdidator();
			regexValdidator.setPatternWord(patternText.getText());
			regexValdidator.setExpWord(valueText.getText());

			boolean isValid = regexValdidator.validateRegex();
			if (isValid) {
				imageLabel.setImage(new Image(shell.getDisplay(),
						ResourceLoader.load(ICONS[2])));
			} else {
				imageLabel.setImage(new Image(shell.getDisplay(),
						ResourceLoader.load(ICONS[3])));
			}
		}
	}
}
