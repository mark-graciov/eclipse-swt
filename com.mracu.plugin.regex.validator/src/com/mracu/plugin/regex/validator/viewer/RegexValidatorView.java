package com.mracu.plugin.regex.validator.viewer;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import com.mracu.plugin.regex.validator.RegexValdidator;
import com.mracu.plugin.regex.validator.model.Pattern;
import com.mracu.plugin.regex.validator.model.PatternDataStore;
import com.mracu.plugin.regex.validator.util.Icons;
import com.mracu.plugin.regex.validator.util.ResourceLoader;
import com.mracu.plugin.regex.validator.viewer.dialogs.AddUpdateDialog;

public class RegexValidatorView extends ViewPart {
	ArrayList<Pattern> patternDataList = new ArrayList<Pattern>();
	ListViewer viewer;
	Label labelIsValid;
	Text expressionText;
	FormToolkit toolkit;

	@Override
	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = true;
		parent.setLayout(gridLayout);
		createListSection(parent);
		createExpressionSection(parent);
	}

	public void createListSection(Composite parent) {
		Section listSection = toolkit.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.minimumWidth = 300;
		listSection.setLayoutData(gridData);
		listSection.setText("Pattern list");
		listSection.setDescription("This section contain list of patterns that can be matched");
		Composite listClient = toolkit.createComposite(listSection);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		listClient.setLayout(gridLayout);
		createPaternListView(listClient);
		createButtonControls(listClient);

		listSection.setClient(listClient);
	}

	public void createExpressionSection(Composite parent) {
		Section expressionSection = toolkit.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.minimumWidth = 300;
		expressionSection.setLayoutData(gridData);
		expressionSection.setText("Expression");
		expressionSection.setDescription("This section contain expression to match");
		Composite expresionClient = toolkit.createComposite(expressionSection);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		expresionClient.setLayout(gridLayout);
		createExpressionArea(expresionClient);
		expressionSection.setClient(expresionClient);
		gridData.minimumHeight = expressionSection.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		gridData.minimumWidth = expressionSection.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
	}

	public void createPaternListView(Composite parent) {

		List patternList = new List(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.COMPOSITION_SELECTION);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.verticalSpan = 4;
		patternList.setLayoutData(gridData);
		viewer = new ListViewer(patternList);
		viewer.setLabelProvider(new ListLabelPrivider());
		viewer.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object obj1, Object obj2) {
				return ((Pattern) obj1).getPatternName().compareToIgnoreCase(((Pattern) obj2).getPatternName());
			}
		});
		viewer.setContentProvider(new ListContentProvider());
		patternDataList = loadData();
		viewer.setInput(patternDataList);
	}

	public void createButtonControls(Composite parent) {

		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;

		Button buttonAdd = new Button(parent, SWT.PUSH);
		buttonAdd.setText("Add");
		buttonAdd.setLayoutData(gridData);
		final Shell shell = parent.getShell();

		buttonAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				AddUpdateDialog dlg = new AddUpdateDialog(shell, new AddUpdateDialog.AddCallback() {
					@Override
					public void onAdd(String pName, String pValue) {
						patternDataList.add(new Pattern(pName, pValue));
						saveData(patternDataList);
					}
				});

				dlg.open();
				viewer.refresh();
			}
		});

		Button buttonModify = new Button(parent, SWT.PUSH);
		buttonModify.setText("Modify");
		buttonModify.setLayoutData(gridData);
		buttonModify.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

				Pattern pattern = (Pattern) selection.getFirstElement();
				final int index = patternDataList.indexOf(pattern);
				if (pattern == null) {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
					messageBox.setMessage("Please select pattern first");
					messageBox.open();
				} else {
					AddUpdateDialog dlg = new AddUpdateDialog(shell, new AddUpdateDialog.AddCallback() {
						@Override
						public void onAdd(String pName, String pValue) {

							patternDataList.set(index, new Pattern(pName, pValue));
							saveData(patternDataList);
						}
					}, pattern);
					dlg.open();
					viewer.refresh();
				}

			}
		});

		Button buttonRemove = new Button(parent, SWT.PUSH);
		buttonRemove.setText("Remove");
		buttonRemove.setLayoutData(gridData);
		buttonRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

				Pattern pattern = (Pattern) selection.getFirstElement();
				if (pattern == null) {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
					messageBox.setMessage("Please select pattern first");
					messageBox.open();
				}
				patternDataList.remove(pattern);
				saveData(patternDataList);

				viewer.refresh();
			}
		});

	}

	public void createExpressionArea(final Composite parent) {
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 2;

		expressionText = toolkit.createText(parent, "", SWT.MULTI | SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		expressionText.setLayoutData(gridData);

		gridData = new GridData(GridData.FILL);
		labelIsValid = new Label(parent, SWT.NONE);
		labelIsValid.setLayoutData(gridData);

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		Button validateButton = new Button(parent, SWT.PUSH);
		validateButton.setText("Validate");
		validateButton.setLayoutData(gridData);
		validateButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

				Pattern pattern = (Pattern) selection.getFirstElement();
				if (pattern == null || expressionText.getText().isEmpty()) {
					MessageBox messageBox = new MessageBox(parent.getShell(), SWT.ICON_WARNING | SWT.OK);
					messageBox.setMessage("Pattern or expression is empty!");
					messageBox.open();
				} else {
					RegexValdidator regexValdidator = new RegexValdidator();
					regexValdidator.setPatternWord(expressionText.getText());
					regexValdidator.setExpWord(pattern.getPatternValue());

					boolean isValid = regexValdidator.validateRegex();
					if (isValid) {
						labelIsValid
								.setImage(new Image(parent.getDisplay(), ResourceLoader.load(Icons.VALID.getIcon())));
						parent.layout();
					} else {
						labelIsValid.setImage(
								new Image(parent.getDisplay(), ResourceLoader.load(Icons.NOT_VALID.getIcon())));
						parent.layout();
					}
				}

			}
		});
	}

	public ArrayList<Pattern> loadData() {
		ArrayList<Pattern> listData = new ArrayList<Pattern>();
		PatternDataStore pds = new PatternDataStore();
		listData = pds.readPatternData();
		return listData;
	}

	public void saveData(ArrayList<Pattern> elements) {
		PatternDataStore pds = new PatternDataStore();
		pds.writePatternData(elements);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

}