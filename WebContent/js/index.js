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

function postaviVideo() {
		$.get('GetVideos', function(data) {
			for(var i=0;i<data.length;i++) {
				
				var imgSrc = data[i].imageURL;
				var title = data[i].name;
				var views = data[i].pregledi;
				var author = data[i].ownerUsername;
				var createdYear = data[i].created.year;
				var createdMonth = data[i].created.month;
				var createdDay = data[i].created.dayOfMonth;
				var id = data[i].id.toString();
				var createdDate = createdDay.toString()+". "+createdMonth.toString()+" "+createdYear.toString();
				console.log(title);
				console.log(author);
				console.log(views);
				console.log(id);
				var kocka = "<div class='col-sm-4'>"+
								"<br><br>"+
									 "<div class='col-sm-12'>"+
										"<a href='#'>"+
											"<img src='"+imgSrc+"'/>"+
										"</a>"+
									"</div>"+  
									"<div class ='col-sm-12' >"+
										"<p>"+ title +"<p>" +
									"</div>" +
										"<div id='autor' class ='col-sm-6'>"+
											"<p>"+ author +"<p>" +
										"</div>" +
										"<div  id='date' class='col-sm-4'>"+
											"<p>"+ createdDate +"<p>"+
										"</div>" +
							"</div>";
				
				$('#videoContainer').append(kocka);
				}
		});
}