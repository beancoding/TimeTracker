function showDialogOnStop(el) {
	
	var showDlg = el.innerText.indexOf("Stop") != -1;
	
	if(showDlg)	
		PF('dlg1').show();
}

function hideDialogOnComplete(args) {
	
	if (args && !args.validationFailed) 
		PF('dlg1').hide();
	
	var btn = document.getElementById("form1:toggleButtonId");
	var chckBox = document.getElementById("form1:finishedCheckboxId");
	
	var jBtn = $(btn);
	
	if( jBtn.text().indexOf("Stop") != -1 && $(chckBox).prop("disabled"))
		jBtn.hide();
}

