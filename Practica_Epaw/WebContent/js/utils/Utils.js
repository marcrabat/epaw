/* Arxiu JS que conté utilitats com agafar elements del DOM */
// Fer import de babel.
var entityMap = {
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': '&quot;',
    "'": '&#39;',
    "/": '&#x2F;'
};

function escapeHtml(string) {
    return String(string).replace(/[&<>"'\/]/g, function (s) {
        return entityMap[s];
    });
}

function loadScript(url, callback) {
    // Adding the script tag to the head as suggested before
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = url;

    // Then bind the event to the callback function.
    // There are several events for cross browser compatibility.
    if (callback != null) {
		script.onreadystatechange = callback;
		script.onload = callback;
	}

    // Fire the loading
    head.appendChild(script);
}

function createElement(type, id) {
	var newElement = document.createElement(type);
	if (id != "") {
		newElement.id = id;
	}
	return newElement;
}

function getElement(id) {
	return document.getElementById(id);
}

function getValue(id) {
	var value = null;
	if ((document.getElementById(id) != null) && (document.getElementById(id) != undefined)) {
		value = document.getElementById(id).value;
	}
	return value;
}

function getHtml(id) {
	var html = null;
	if ((document.getElementById(id) != null) && (document.getElementById(id) != undefined)) {
		html = document.getElementById(id).innerHTML;
	}
	return html;
}

function getSelectValue(id) {
	var value = null;
	if ((document.getElementById(id) != null) && (document.getElementById(id) != undefined)) {
		var selectedOption = this.options[select.selectedIndex];
		if (selectedOption != null) { value = selectedOption.value; }
	}
	return value;
}

function addOptionInSelect(selectId, optionId, optionValue) {
	var select = document.getElementById(selectId);
	if ((select != null) && (select != undefined)) {
		var option = document.createElement("option");
		option.id = optionId;
		option.text = optionValue;
		select.add(option);
	}
}

// Funcion que remueve una opcion del select, recibiendo la posicion donde se encuntra este option
function removeOptionInSelect(selectId, optionPos) {
	var select = document.getElementById(selectId);
	if ((select != null) && (select != undefined)) {
		select.remove(optionPos)
	}
}

function setValue(id, value) {
	if ((document.getElementById(id) != null) && (document.getElementById(id) != undefined)) {
		document.getElementById(id).value = value;
	}
}

function setHtml(id, html) {
	if ((document.getElementById(id) != null) && (document.getElementById(id) != undefined)) {
		document.getElementById(id).innerHTML = html;
	}
}

function executeAjax(parametros, url, method, success, error) {
	$.ajax({
		data:  parametros,
		url:   url,
		type:  method,
		success:  function(response) { success(response); },
		error: function(e) { error(e); }
	});
}

function executeAjaxWithBeforeSend(parametros, url, method, beforeSend, success, error) {
	$.ajax({
		data:  parametros,
		url:   url,
		type:  method,
		beforeSend: function() { beforeSend; },
		success:  function(response) { succes(response); },
		error: function(e) { error(e); }
	});
}

function replaceIllegalCharacters(value, replaceString) {
	var cleanString = value.replace(/[|&;$%@"<>()+,]/g, replaceString);
	return cleanString;
}

// Hi ha que provar-la no se si funciona.
function replaceIllegalCharactersWhitRegEx(value, replaceString, regEx) {
	var cleanString = value.replace("/[" + regEx + "]/g", replaceString);
	return cleanString;
}

// Funcion que envia el formulario, realizando un encoding sobre la URL.
function formSubmit(form) {
	form.action = encodeURIComponent(form.action);
	form.submit();
}

/** Funcion que envia el formulario a un target recibido por parametro, realizando un encoding sobre la URL.
@param form - Formulario
@param target - Ventana objetivo donde se enviara el formulario.
*/
function formSubmitOnTarget(form, target) {
	form.taget = target;
	formSubmit();
}

function redirect(targetWindow, url) {
	targetWindow.location.href = encodeURIComponent(url);
}

function showErrorsInAlert(errors) {
	var message = "";

	for (var i = 0; i < errors.length; i++) {
		message += errors[i].error + "\n";
	}

	if (message != "") {
		alert(message);
	}
}

function goBack() {
    window.history.back();
}

// ASYNC / AWAIT
/* Aixo t'ho deixo per a tu Marc Alcaraz.
async function getProcessedData(url) {
	let result;
	try {
		result = await funcio..
	}
}
*/

