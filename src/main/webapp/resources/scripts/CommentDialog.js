function showDialogOnStop(el){
	if(el.innerText.indexOf("Stop") != -1)
		PF('dlg1').show();
}
function hideDialogOnComplete(args){
	if (args && !args.validationFailed) 
		PF('dlg1').hide();
}