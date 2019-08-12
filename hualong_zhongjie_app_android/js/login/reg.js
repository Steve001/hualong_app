mui.init();
mui.plusReady(function() {
	//initOauth();
	storage.init();

	//	var btn_sendvalidcode = document.getElementById("btn_sendvalidcode");
	var btn_ok = document.getElementById("btn_ok");
	var inpt_mobile = document.getElementById("inpt_mobile");
	var inpt_company_name = document.getElementById("inpt_company_name");
	var inpt_zhongjie_name = document.getElementById("inpt_zhongjie_name");
	var inpt_password = document.getElementById("inpt_password");
	//	var inpt_validcode = document.getElementById("inpt_validcode");
	var ckb_agree = document.getElementById("ckb_agree");
	var inpt_tjname = document.getElementById("inpt_tjname");

	if(ismobileno(inpt_mobile.value)) {
		appUI.removeDisabled(btn_sendvalidcode);
	}

	//协议勾选
	ckb_agree.addEventListener("tap", function() {
		if(this.checked) {
			appUI.showTopTip("请先同意服务条款");
		}
	});
	
	
	btn_ok.addEventListener("click", function() {
		var data = {
			"jigou": inpt_company_name.value,
			"tjname": inpt_tjname.value,
			"userName": inpt_zhongjie_name.value,
			"userPhone": inpt_mobile.value,
			"userPassword": inpt_password.value,
		}
		
		if(inpt_company_name.value.trim() == "") {
			appUI.showTopTip("请输入机构名称");
		}else if(inpt_tjname.value.trim() == "") {
			appUI.showTopTip("请输入app推荐人姓名");
		}else if(inpt_zhongjie_name.value.trim() == "") {
			appUI.showTopTip("请输入您的姓名");
		}else if(inpt_mobile.value.trim() == "") {
			appUI.showTopTip("请输入手机号");
		}else if(!ismobileno(inpt_mobile.value)) {
			appUI.showTopTip("手机号格式不正确");
		}else if(inpt_password.value.trim() == "") {
			appUI.showTopTip("请输入密码");
		}else {
			//appUI.setDisabled(btn_login);
			request("/addUser", data, function(json) {
				appUI.removeDisabled(btn_ok);
				mui.toast(json.message);
				if(json.status == "success") {
					openNew("login.html");
				}
			});
		}
	});
	
	
	//服务条款
	document.getElementById("servicedesc").addEventListener("tap", function() {
		openNew("../my/myMsgDetail.html", {
			id: 1
		});
	});
});

//function btnDisabled(isShowMsg) {
//	var btn_sendvalidcode = document.getElementById("btn_sendvalidcode");
//	var btn_ok = document.getElementById("btn_ok");
//	var val_mobileinpt = document.getElementById("inpt_mobile").value;
//	var val_validcodeinpt = document.getElementById("inpt_validcode").value;
//	var ckb_agree = document.getElementById("ckb_agree");
//
//	var ck_ok = true,
//		ck_sendvalidcode = true;
//
//	if(val_mobileinpt.length != 11) {
//		if(isShowMsg)
//			appUI.showTopTip("手机号码长度不正确");
//		//mui.toast("手机号码长度不正确");
//		ck_ok = false;
//		ck_sendvalidcode = false;
//	} else if(!ismobileno(val_mobileinpt)) {
//		if(isShowMsg)
//			appUI.showTopTip("手机号码格式不正确");
//		//mui.toast("手机号码格式不正确");
//		ck_ok = false;
//		ck_sendvalidcode = false;
//	} else if(val_validcodeinpt.length != 6) {
//		if(isShowMsg)
//			appUI.showTopTip("验证码长度不正确");
//		//mui.toast("验证码长度不正确");
//		ck_ok = false;
//	}
//	if(ck_ok) {
//		appUI.removeDisabled(btn_ok);
//	} else {
//		appUI.setDisabled(btn_ok);
//	}
//
//	if(ck_sendvalidcode && btn_sendvalidcode.innerHTML.indexOf("重新") == -1) {
//		appUI.removeDisabled(btn_sendvalidcode);
//	} else {
//		appUI.setDisabled(btn_sendvalidcode);
//	}
//
//}