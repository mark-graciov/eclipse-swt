package com.mracu.plugin.regex.validator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class RegexValidatorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		// MessageDialog.openInformation(window.getShell(), "First",
		// "Hello, Eclipse world");
		ValidatorDialog validatorDialog = new ValidatorDialog(window.getShell());

		return null;
	}

}
