mui.init();
var btn_submit;
var tui_name;
var tui_sex;
var tui_phone;
var tui_mj;
var tui_time;
var tui_bz;

mui.plusReady(function() {
	storage.init();
	btn_submit = document.getElementById("verify");
	tui_name = document.getElementById("tui_name");
	tui_sex = document.getElementById("tui_sex");
	tui_phone = document.getElementById("tui_phone");
	tui_mj = document.getElementById("tui_mj");
	tui_time = document.getElementById("tui_time");
	//tui_area = document.getElementById("tui_area");
	//	tui_bz=document.getElementById("tui_bz");
	//注册

	btn_submit.addEventListener('click', function() {
		var data = {
			"cusName": tui_name.value,
			"cusSex": tui_sex.options[tui_sex.selectedIndex].value,
			"cusPhone": tui_phone.value,
			//"cusArea": tui_mj.value,
			"cusArea": tui_mj.options[tui_mj.selectedIndex].value,
			"cusDate": tui_time.value,
			"cusUserid": storageUser.userId
			//			"cusBz":tui_bz.value
		}
		if(data.cusName.trim() == "") {
			//appUI.showTopTip("请输入客户姓名");
			mui.toast("请输入客户姓名");
			//inpt_mobile.focus();
		} else if(data.cusPhone.trim() == "") {
			//appUI.showTopTip("请输入联系电话");
			mui.toast("请输入联系电话");
		} else if(data.cusDate.trim() == "") {
			//appUI.showTopTip("请输入联系电话");
			mui.toast("请选择预计到访时间");
		} else {
			appUI.setDisabled(btn_submit);
			request("/addCus", data, function(json) {
				appUI.removeDisabled(btn_submit);
				mui.toast(json.message);
				if(json.status == "success") {
					mui.back();
				}
			}, true, function() {
				appUI.removeDisabled(btn_submit);
			})
		}
	})

})