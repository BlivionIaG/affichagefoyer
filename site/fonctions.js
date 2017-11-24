var filePath='text.txt';
var carriageReturn = '\r\n';

$(document).ready(function() {
	timer = setInterval(function(){readTextFile(filePath)}, 1000);

	readTextFile(filePath);
});

function readTextFile(filePath){
			$.get(filePath, function(data) {
				console.log(data);
				var lines = data.split(carriageReturn);
				var tab;
				var tab;
				for (var i = 0; i < 3; i++) {
					tab = lines[i].split(':');
					$('#yo'+(i+1)).empty();
					if(tab[1].substr(0,4)=='true'){	
						$('#yo'+(i+1)).append(tab[0]);
					}
				}
			}, 'text');
}
