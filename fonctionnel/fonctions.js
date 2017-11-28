var filePath='clients.txt';
// var carriageReturn = '\r\n';
var carriageReturn = '\n';
$(document).ready(function() {
	readTextFile(filePath);
	timer = setInterval(function(){readTextFile(filePath)}, 1000);
});

function readTextFile(filePath){
			$.get(filePath, function(data) {
				console.log(data);
				var lines = data.split(carriageReturn);
				var tab;
				for (var i = 0; i < 3; i++) {
					tab = lines[i].split(':');
					$('#client'+(i+1)).empty();
					if(tab[1].substr(0,4)=='true'){
						$('#client'+(i+1)).append(tab[0]);
					}
				}
			}, 'text');
}
