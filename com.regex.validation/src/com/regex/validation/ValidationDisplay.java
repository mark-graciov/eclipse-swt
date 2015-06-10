package com.regex.validation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ValidationDisplay {
	protected static final int TEXT_WITDH = 150;
	protected static final String[] ICONS = { "icons/icon_regex.png",
			"icons/valid.png", "icons/not_valid.png", "icons/no_text.png" };

	public static void main(String args[]) {
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		GridLayout gridLayout = new GridLayout();
		shell.setLayout(gridLayout);
		shell.setText("Regex Validator");

		Image regexIcon = new Image(display, ResourceLoader.load(ICONS[0]));
		shell.setImage(regexIcon);

		Group borderGroup = new Group(shell, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		borderGroup.setLayout(gridLayout);
		borderGroup.setText("Parameters");

		GridData textData = new GridData();
		textData.widthHint = TEXT_WITDH;

		Label patternLabel = new Label(borderGroup, SWT.NONE);
		patternLabel.setText("Regex");
		final Text patternText = new Text(borderGroup, SWT.SINGLE | SWT.BORDER);
		patternText.setLayoutData(textData);

		Label valueLabel = new Label(borderGroup, SWT.NONE);
		valueLabel.setText("Value");
		final Text valueText = new Text(borderGroup, SWT.SINGLE | SWT.BORDER);
		valueText.setLayoutData(textData);

		GridData imageData = new GridData();
		imageData.heightHint = 32;
		imageData.widthHint = 32;
		final Label imageLabel = new Label(borderGroup, SWT.NONE);
		imageLabel.setLayoutData(imageData);
		Button validateButton = new Button(borderGroup, SWT.PUSH);
		validateButton.setText("Validate");
		validateButton
				.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));

		validateButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				if (patternText.getText() == "" && valueText.getText() == "") {
					imageLabel.setImage(new Image(display, ResourceLoader
							.load(ICONS[3])));
				} else {
					RegexValdidator regexValdidator = new RegexValdidator();
					regexValdidator.setPatternWord(patternText.getText());
					regexValdidator.setExpWord(valueText.getText());

					boolean isValid = regexValdidator.validateRegex();
					if (isValid) {
						imageLabel.setImage(new Image(display, ResourceLoader
								.load(ICONS[1])));
					} else {
						imageLabel.setImage(new Image(display, ResourceLoader
								.load(ICONS[2])));
					}
				}

			}

		});

		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}

}
