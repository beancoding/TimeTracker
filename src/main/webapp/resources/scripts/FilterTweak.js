$(document).ready(function(){
	
	var filterTextbox = $("input[id^='jobsForm']input[id$='filter']");
	filterTextbox.css("display","inline");
	filterTextbox.css("width","220px");
	$(".ui-panelgrid.ui-widget").removeClass("ui-panelgrid");
});