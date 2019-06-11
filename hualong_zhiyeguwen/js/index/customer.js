mui.init();

mui.plusReady(function() {
	storage.init();
	var data = {
		"userId": storageUser.userId
	}
	request("/getCus", data, function(json) {
		mui.toast(json.message);
		if(json.status == "success") {
			var lists = {
				"dataList": json.appdata
			}
			var html = template("template", lists);
			document.getElementById("target").innerHTML = html;
		}
	}, true, function() {});

})