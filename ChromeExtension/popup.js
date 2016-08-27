var thecode = false;
function registerCallback(registrationId) {
	  if (chrome.runtime.lastError) {
		  console.log("phsit");
		      // When the registration fails, handle the error and retry the
		  //     // registration later.
		          return;
	        }


	console.log(registrationId);
	 sendToServer(registrationId);
	//set registration to true
}


chrome.runtime.onStartup.addListener(function() {


	     chrome.storage.local.get('code', function(result) {
		if(result.code === "" || result.code === null || result.code===undefined){
		     var codea = makeid();
		     console.log("codea is " + codea);
			thecode = result.code
			var views = chrome.extension.getViews({type: "popup"});
			    for (var i = 0; i < views.length; i++) {
				                    views[i].document.getElementById('x').innerHTML="MY custom value";
				            }

		     document.write(" Enter The Code In The Android App")
		     document.write( codea);
		     chrome.storage.local.set({'code':codea});
		    
	     }
			else{
				var code = result.code;
			var views = chrome.extension.getViews({type: "popup"});

		     console.log("BAAHHAAH" + result.code);
				  document.write(" Enter The Code In The Android App")
		                document.write( code);
			}
	   			
	     });

	 chrome.storage.local.get("registered", function(result) {
	if (result["registered"])
			       return;
	console.log("herro");
	var senderIds = ["server-id"];
	    chrome.gcm.register(senderIds, registerCallback);
});
});

	chrome.storage.local.get('code',function(result){

			var views = chrome.extension.getViews({type: "popup"});
			    for (var i = 0; i < views.length; i++) {
				                    views[i].document.getElementById('x').innerHTML=result.code;
				            }

	});

function sendToServer(data){
AWS.config.update({
	  region: 'us-east-1',
	  credentials: new AWS.CognitoIdentityCredentials({
		  IdentityPoolId: 'aws-id'
		    })
});
		code = result.code;
console.log("reult.code is " + result.code);
var lambda = new AWS.Lambda();
var data_send = {
	'data':String(data),
	'code':String(code)
};
console.log(data_send);
data_send = JSON.stringify(data_send);
var params = {
	  FunctionName: 'TestFunction', /* required */
	  Payload:data_send 
};


lambda.invoke(params, function(err, data) {
	  if (err){

		  console.log(err, err.stack)
	  } // an error occurred
	  else    {
		  console.log(data);   
	  	  // successful response
		chrome.storage.local.set({registered: true});
	  }
});
	    }


chrome.gcm.onMessage.addListener(function(message) {
	  // A message is an object with a data property that
	//   // consists of key-value pairsI.
	console.log(message)
	chrome.notifications.create(null,{
		title:String(message.data.title),
		message:String(message.data.text),
		type:'basic',
		iconUrl:'SsTdzwR.jpg'

	},function(){});
	   });



function makeid()
{
	    var text = "";
	    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	    for( var i=0; i < 7; i++ )
		        text += possible.charAt(Math.floor(Math.random() * possible.length));

	    return text;
}
