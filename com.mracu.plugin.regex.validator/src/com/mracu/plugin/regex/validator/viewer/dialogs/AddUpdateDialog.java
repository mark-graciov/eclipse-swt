package com.mracu.plugin.regex.validator.viewer.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mracu.plugin.regex.validator.model.Pattern;

public class AddUpdateDialog extends IconAndMessageDialog {

	public static interface AddCallback {
		public void onAdd(String pName, String pValue);
	}

	public static final int I_ADD_ID = IDialogConstants.CLIENT_ID;
	public static final String I_ADD_LABEL = "Add";
	public static final int TEXT_WIDTH = 200;

	private Label patternNameLabel;
	private Text patternNameText;
	private Label patternValueLabel;
	private Text patternValueText;
	private AddCallback callback;
	private Pattern pattern;

	public AddUpdateDialog(Shell parent, AddCallback callback) {
		super(parent);
		this.callback = callback;
	}

	public AddUpdateDialog(Shell parent, AddCallback callback, Pattern pattern) {
		super(parent);
		this.callback = callback;
		this.pattern = pattern;
	}

	protected Control createDialogArea(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		composite.setLayout(new FillLayout());

		patternNameLabel = new Label(composite, SWT.LEFT);
		patternNameLabel.setText("PatternName:");
		gridData = new GridData();
		gridData.widthHint = TEXT_WIDTH;
		patternNameText = new Text(parent, SWT.BORDER);
		patternNameText.setLayoutData(gridData);

		patternValueLabel = new Label(parent, SWT.LEFT);
		patternValueLabel.setText("Pattern:");

		patternValueText = new Text(parent, SWT.BORDER);
		patternValueText.setLayoutData(gridData);

		if (pattern != null) {
			patternNameText.setText(pattern.getPatternName());
			patternValueText.setText(pattern.getPatternValue());
		}
		return composite;

	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, I_ADD_ID, I_ADD_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected void buttonPressed(int buttonId) {

		if (buttonId == I_ADD_ID) {
			addData();
		} else {
			close();
		}

	}

	public void setFieldsFromObject(Pattern element) {
		if (element != null) {
			patternNameText.setText(element.getPatternName());
			patternValueText.setText(element.getPatternValue());
		} else
			return;
	}

	protected void addData() {
		if (patternNameText.getText() != "" && patternValueText.getText() != "") {
			callback.onAdd(patternNameText.getText(),
					patternValueText.getText());
		}
		close();
	}

	@Override
	protected Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
