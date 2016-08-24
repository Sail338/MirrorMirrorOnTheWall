 var firebase = require('firebase');
var request = require('request')
	firebase.initializeApp({
		serviceAccount:'key.json',
		
			      databaseURL: "https://mirrormirroronthewall-3dbfc.firebaseio.com",
	});	

exports.handler = function (evt, context, callback) {
    //is android
  console.log(evt); 
    if(evt.isChrome === "no")
    {
	    var ref = firebase.database().ref('/' + String(evt.code))
	    ref.once('value').then(function(data){
		console.log(data.val());
		//build post req
		var data ={ 'data':{
			"title":evt.title,
			"text":evt.text
			
		},
			"to":data.val()
		};

		    request({

					url: 'https://fcm.googleapis.com/fcm/send',
				      method: 'POST',
				      headers: {
					                'Content-Type': 'application/json',
					      		'Authorization':'key=' + 'firevase_key'
					            },
				body:JSON.stringify(data)
			}

				,function(error,response,body){
					console.log(body);
					if(error){
			    console.log(error);
		    }
					else{
						console.log(response);
						context.done(null,"donezo");
					}
		    }
		    ).then(function(){
		    });
	    }).then(function(){
		context.done(null,"done");
	    });

            //Query the DB for reg id
            //send push notification
    } else{
	    console.log("fuckboi");
	    var db = firebase.database();
	    var code = String(evt.code);
	    var newPostKey = evt.code;
	    var updates = {
	    };
	    var data =evt.data;
	    updates['/' + newPostKey] = data;
	    firebase.database().ref().update(updates).then(function(){
			console.log("done");
			context.done(null,"doneze");	    
	    }); 
        //send to the db
    }
}

