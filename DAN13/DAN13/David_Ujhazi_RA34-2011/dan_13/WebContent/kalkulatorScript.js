var sveOk = function(data) {
	alert("podaci: " + data);
};

var nijeOk = function(data) {
	alert(data);
};

setTimeout(function() {
	$.ajax({
		url : "1.txt",
		success : sveOk,
		error : nijeOk,
	});
}, 3000);

setTimeout(function() {
	$.ajax({
		url : "JSONServlet",
		success : function(data) {
			alert("ime je" + data.ime);
		},
		error : nijeOk,
		dataType : 'json'
	});
}, 3000);

setTimeout(function() {
	var osoba = {};
	osoba.ime = "ZZika";
	osoba.starost = 21;
	
	var strOsoba = JSON.stringify(osoba);
	
	$.ajax({
		url : "JSONServlet",
		data: strOsoba,
		success : function(data) {
			alert("negativna starost je " + data.starost);
		},
		type: "POST",
		error : nijeOk,
		dataType : 'json'
	});
}, 3000);

setTimeout(function() {
	$.ajax({
		url : "JSONListServlet",
		success : function(data) {
			var tbl = $("#tblOsobe");
			for (let i = 0; i < data.length; i++) {
				var trStart = "<tr>";
				var tdIme = "<td>" + data[i].ime + "</td>";
				var tdStarost = "<td>" + data[i].starost+ "</td>";
				var trEnd = "</tr>";
				tbl.append(trStart + tdIme + tdStarost + trEnd);
			}
		},
		error : nijeOk,
		dataType : 'json'
	});
}, 3000);


$(document).ready(function() {
	alert("WTF");
	$("#logDiv").append("<h1>TEST JQ</h1>");
	$("#logDiv").css("background-color", "blue");
	setTimeout(function() {
		$("#logDiv").empty();
	}, 3000)

	$("#LogDiv button").css("background-color", "green");
	$("#LogDiv button").click(function() {
		alert("jq onlick woop woop");
	});
	// $("#logDiv").append("");
});

/**
 * 
 * @param {string}
 *            downloadURL -
 * @param {function}
 *            successCallback - call if download succeeds
 */
function download(downloadURL, successCallback, failCallback) {
	if (downloadURL === "ok.png") {
		setTimeout(function() {
			successCallback();
		}, 5000);
	} else {
		setTimeout(function() {
			failCallback();
		}, 5000)
	}
}

download("ok.png", function() {
	alert("PROSAO!!!");
}, function() {
	alert("NIJE PROSAO");
})

var successCallback = function() {
	alert("prosao ref");
}

var failCallback = function() {
	alert("nije prosao ref");
}

download("not-ok.png", successCallback, failCallback);

window.onload = function() {

	var txtX = document.getElementById("txtX");
	var txtY = document.getElementById("txtY");
	var txtZ = document.getElementById("txtZ");
	var btnPlus = document.getElementById("btnPlus");

	document.addEventListener("ValueChanged", function(e) {
		alert("handler okinut " + e);
		if (e.detail > 0) {
			txtZ.style.backgroundColor = "red";
		} else if (e.detail < 0) {
			txtZ.style.backgroundColor = "blue";
		} else {
			txtZ.style.backgroundColor = "grey";
		}
	}, false);

	btnPlus.onclick = function() {
		var xVrednost = txtX.value;
		if (xVrednost === "") {
			alert("morate  uneti x !");
			return;
		}

		var yVrednost = txtY.value;
		if (yVrednost === "") {
			alert("morate  uneti y !");
			return;
		}
		xVrednost = parseInt(xVrednost);
		yVrednost = parseInt(yVrednost);
		var zVrednost = xVrednost + yVrednost;

		// AKO JE > 0 , oboji POLJE u CRVENO

		// AKO JE < 0 OBOJI u PLAVO

		// AKO JE == 0 , oboji u SIVO

		txtZ.value = zVrednost;
		var eventPayload = {
			'detail' : zVrednost

		};
		var event = new CustomEvent('ValueChanged', eventPayload);
		document.dispatchEvent(event);

	};
};