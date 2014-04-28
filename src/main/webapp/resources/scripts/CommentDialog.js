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

$(document).ready(function(){
	
	var jBttn = $("button[id*='timeAdjustmentButton']");
	jBttn.prop("disabled", true);
	jBttn.addClass("ui-state-disabled");
	
	var timeAdjustmentInput = $("input[id*='timeAdjustmentVal']");
	timeAdjustmentInput.keyup(function() {
		
		var val = timeAdjustmentInput.val();
		if(val && Math.abs(val) > 0) {
			
			jBttn.prop('disabled', false);
			jBttn.removeClass("ui-state-disabled");
		}
		else {
			
			jBttn.prop("disabled", true);
			jBttn.addClass("ui-state-disabled");
		}
	});	
});

