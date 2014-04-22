function showDialogOnStop(el) {
	
	var showDlg = el.innerHTML.indexOf("Stop") != -1 || (el.innerText != undefined && el.innerText.indexOf("Stop") != -1);
	
	if(showDlg)	
		PF('dlg1').show();
}

function hideDialogOnComplete(args) {
	
	if (args && !args.validationFailed) 
		PF('dlg1').hide();
}

function disableView(){
	
	var btn = document.getElementById("form1:toggleButtonId");
	var chckBox = document.getElementById("form1:finishedCheckboxId");
	
	var jBtn = $(btn);
	var jChckBox = $(chckBox);
	
	jBtn.hide();
	jChckBox.hide();
}

