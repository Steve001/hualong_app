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

	if(ismobileno(inpt_mobile.value)) {
		appUI.removeDisabled(btn_sendvalidcode);
	}

	//协议勾选
	ckb_agree.addEventListener("tap", function() {
		if(this.checked) {
			appUI.showTopTip("宝宝，同意下服务条款呗");
			//mui.toast("宝宝，同意下服务条款呗");
		}
	});
	//下一步
	btn_ok.addEventListener("click", function() {
		//		if(inpt_mobile.value.trim() == "") {
		//			appUI.showTopTip("请输入手机号");
		//			//mui.toast("请输入手机号");
		//			//inpt_mobile.focus();
		//		} else if(!ismobileno(inpt_mobile.value)) {
		//			appUI.showTopTip("手机号格式不正确");
		//			//mui.toast("手机号格式不正确");
		//		} else if(!ckb_agree.checked) { //当前为true点击了为false
		//			appUI.showTopTip("宝宝，同意下服务条款呗");
		//			//mui.toast("宝宝，同意下服务条款呗");
		//		} else {
		//			appUI.setDisabled(btn_ok);
		//			var url = HttpKit.getBaseUrl() + "/addUser";
		//			var httModel = {
		//				"jigou": inpt_company_name.value,
		//				"userName": inpt_zhongjie_name.value,
		//				"userPhone": inpt_mobile.value,
		//				"userPassword": inpt_password.value
		//			};
		//			var params = {
		//				task: JSON.stringify(httModel).replace(/'/g, "''"),
		//			}
		//
		//			HttpKit.sendRequest(url, params, function(result) {
		//				console.debug(JSON.stringify(result));
		//			});
		request("/addUser", {
			jigou: inpt_company_name.value,
			userName: inpt_zhongjie_name.value,
			userPhone: inpt_mobile.value,
			userPassword: inpt_password.value
		}, function(json) {
			appUI.removeDisabled(btn_ok);
			mui.toast(json.message);
			if(json.status == "success") {
				openNew("login.html");
			}
		});
		//		}
	})
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