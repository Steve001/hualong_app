var backid = "main.html";
var backurl = "../" + backid;

mui.plusReady(function() {

	storage.init();

	backid = appPage.getParam("backid") || "main.html";
	backurl = "../" + backid;

	var btn_login = document.getElementById("btn_login");
	var inpt_mobile = document.getElementById("inpt_mobile");
	var inpt_pwd = document.getElementById("inpt_pwd");

	storageUser = kidstorageuser.getInstance();
	
	if( storageUser.IsLogin){
		openNew("../main.html");
		return;
	}

	btn_login.addEventListener("tap", function() {
		var data = {
			"userPhone": inpt_mobile.value,
			"userPassword": inpt_pwd.value
		}
		if(inpt_mobile.value.trim() == "") {
			appUI.showTopTip("请输入手机号");
		} else if(!ismobileno(inpt_mobile.value)) {
			appUI.showTopTip("手机号格式不正确");
		} else if(inpt_pwd.value.trim() == "") {
			appUI.showTopTip("请输入密码");
		} else {
			appUI.setDisabled(btn_login);
			request("/userLogin", data, function(json) {
				appUI.removeDisabled(btn_login);
				if(json.status == "success") {
					var data = json.appdata[0];
					log(data);
					storageUser.login(data);
					storageUser.log();
					//					appPage.loginBack(backid, backurl);
					openNew("../main.html");
				} else {
					appUI.showTopTip(json.message);
				}
			}, true, function() {
				appUI.removeDisabled(btn_login);
			});
		}
	});

	//注册
	document.getElementById("btn_reg").addEventListener("tap", function() {
		openNew("reg.html");
	});
	//手机登录
})