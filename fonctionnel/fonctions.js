var filePath='clients.txt';
var carriageReturn = '\r\n';

$(document).ready(function() {
	// timer = setInterval(function(){readTextFile(filePath)}, 500);
	readTextFile(filePath);
});

function readTextFile(filePath){
			$.get(filePath, function(data) {
				console.log(data);
				var lines = data.split(carriageReturn);
				var tab2;
				for (var i = 0; i < lines.length; i++) {
					tab2 = lines[i].split(':');
					for (var j = 0; j < tab2.length; j++) {
						$('#yo').append(tab2[j]+'\t');
					}
					$('#yo').append('<br>');
				}
				//$('#yo').text(data);
			}, 'text');
}
