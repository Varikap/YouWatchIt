function postaviDugmad() {
	$.get("SessionServlet?session=provera",function(data) {  // gadja servlet za proveru sesije
			
			if(data.Authenticated == null){ // znaci da nije authentifikovan
				
				var loginRegister =  "<div class='row'>" +
			  	"<div class='col-sm-5'>"+
				"<br><button type='button'"+
				"onclick='location.href = \"profilePage.html\"'"+  
				"class='btn btn-primary '>LOGIN</button>" + 
			
			"</div>" +
			"<div class='col-sm-5'>"+
			    "<br><button type='button'"+
		  	    "onclick='logoutBtn()'"+ 
		  	    "class='btn btn-primary '>REGISTER</button>"+
			"</div>"+
		"</div>";
				
				$("#headerDugmad").append(loginRegister);
			}
			else{
				
				var profileLogout = 
				  "<div class='row'>" +
				  	"<div class='col-sm-5'>"+
						"<br><button type='button'"+
						"onclick='location.href = \"profilePage.html\"'"+  
						"class='btn btn-primary '>MY PROFILE</button>" + 
					
					"</div>" +
					"<div class='col-sm-5'>"+
					    "<br><button type='button'"+
				  	    "onclick='logoutBtn()'"+ 
				  	    "class='btn btn-primary '>LOGOUT</button>"+
					"</div>"+
				"</div>";
			      $("#headerDugmad").append(profileLogout);
			}
			
		});
}