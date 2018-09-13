$(document).ready(function(e) {
				$('#submitButton').click(function(e) {
					e.preventDefault(); // !!
					var username = $('#uname');
					var password = $('#pass');
					params = {
								username : username.val(),
								password : password.val()
							 };
					$.post('LoginServlet',params, function(data) {
					if(data.msg == 'Success') {
						window.location.replace('index.html');
					} else {
						username.val('');
						password.val('');
					}
					},'json');
				});	
});	