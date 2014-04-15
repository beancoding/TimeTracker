function showDialogOnStop(el) {
	
	var showDlg = el.innerText.indexOf("Stop") != -1;
	
	if(showDlg)	
		PF('dlg1').show();
}

function hideDialogOnComplete(args) {
	
	if (args && !args.validationFailed) 
		PF('dlg1').hide();
	
	var btn = $("#form1:toggleButtonId");
	
	if( btn.text().indexOf("Stop") != -1 && $("#form1:finishedCheckboxId").prop("disabled"))
		btn.hide();
}

