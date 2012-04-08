// This function is used by the login screen to validate user/pass
// are entered.
function validateRequired(form) {
	var bValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	oRequired = new required();
	for (x in oRequired) {
		if ((form[oRequired[x][0]].type == 'text'
				|| form[oRequired[x][0]].type == 'textarea'
				|| form[oRequired[x][0]].type == 'select-one'
				|| form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password')
				&& form[oRequired[x][0]].value == '') {
			if (i == 0)
				focusField = form[oRequired[x][0]];
			fields[i++] = oRequired[x][1];
			bValid = false;
		}
	}
	if (fields.length > 0) {
		focusField.focus();
		alert(fields.join('\n'));
	}
	return bValid;
}

// This function is a generic function to create form elements
function createFormElement(element, type, name, id, value, parent) {
	var e = document.createElement(element);
	e.setAttribute("name", name);
	e.setAttribute("type", type);
	e.setAttribute("id", id);
	e.setAttribute("value", value);
	parent.appendChild(e);
}

function confirmDelete(obj) {
	var msg = "Are you sure you want to delete this " + obj + "?";
	ans = confirm(msg);
	return ans;
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; //返回参数值
}

function getRedirect(name, service) {
	var url = getUrlParam(service);
	if(url != null && url.length > 7)
		url = "?service=" + url;
	else
		url = "";
	window.location.href = name + url;
}