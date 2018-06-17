/* Arxiu JS que conté utilitats com realitzar validacions */

function isUndefined(param) {
	var result = true;
	try {
		if (param != undefined) { result = false; }
	} catch (e) {
		result = true;
	}
	return result;
}

function isNull(param) {
	var result = true;
	try {
		if (param != null) { result = false; }
	} catch (e) {
		result = true;
	}
	return result;
}

function existInDOM(param) {
	var result = false;
	try {
		if ((isNull(param) == false) || (isUndefined(param) == false)) {
			result = true;
		}
	} catch (e) {
		result = false;
	}
	return result;
}

function isEmpty(param) {
	var result = false;
	try {
		if ((param == "") || (param.length == 0)){ result = true; }
	} catch (e) {
		result = true;
	}
	return result;
}

function isNotEmpty(param) {
	return !isEmpty(param);
}

function equals(a, b) {
	return (a == b);
}

function forceEquals(a, b) {
	return (a === b);
}

function haveThisLength(param, size) {
	var result = false;
	try {
		if (param.length == size) { result = true; }
	} catch (e) {
		result = false;
	}
	return result;
}

function haveMinimumLength(param, size) {
	var result = false;
	try {
		if (param.length >= size) { result = true; }
	} catch (e) {
		result = false;
	}
	return result;
}