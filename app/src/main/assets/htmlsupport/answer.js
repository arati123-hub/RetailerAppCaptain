
var key="SelectedAnswer";
var isDirty="N";

/* [webView stringByEvaluatingJavaScriptFromString:@"myFunction();"];  
http://iphoneincubator.com/blog/windows-views/how-to-inject-javascript-functions-into-a-uiwebview
*/

var LSsupport = !(typeof window.localStorage == 'undefined');
	
if (!LSsupport)
	alert("Local storage support is not available. Please contact the FlipIt support team.");

function SelectedAnswer()
{
	if (localStorage.getItem(1) != null)
		return isDirty + "-" + localStorage.getItem(key);
	return isDirty;
}

function Checkbox_Selected(obj, size, selValue)
{
 	if (obj.checked)
		JsHandler.saveAnswerToDB(selValue);
	else
		JsHandler.saveAnswerToDB("0");

	isDirty="Y";
}


function Radio_Selected(obj, size, selValue)
{


 	if (obj.checked)
	{
		DeselectOthers(parseInt(selValue), size);
		JsHandler.saveAnswerToDB(selValue);
	}
	else
		JsHandler.saveAnswerToDB("0");

	isDirty="Y";
}


function DeselectOthers(selValue, size)
{
	for (var i=1; i<=size; i++)
	{
		if (document.getElementById(i.toString()))
		{
			var obj = document.getElementById(i.toString());
			if (i.toString() != selValue)
				obj.checked = false;
		}
	}
}