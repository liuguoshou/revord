$(function(){  
	if (error) {
		error = JSON.parse(error);
		if (error) {
			var message=error.errMsg;
			if(message){
				alert(message);
			}
		}
	}
});  