var backid = "main.html";
var backurl = "../" + backid;

mui.plusReady(function() {

	storage.init();
	storageUser = kidstorageuser.getInstance();

	//获取版本信息
	//得到当前版本
	var version = plus.runtime.version;
	document.getElementById("version").innerText = version;

	backid = appPage.getParam("backid") || "main.html";
	backurl = "../" + backid;

	var btn_login = document.getElementById("btn_login");
	var inpt_mobile = document.getElementById("inpt_mobile");
	var inpt_pwd = document.getElementById("inpt_pwd");

	var data = {
		'version': version,
		'platform': plus.os.name
	}

	//检测更新
	request("/checkVersion", data, function(json) {
		if(json.status == "success") {
			var data = json.appdata[0];
			if(data.version != '0') {
				var w = plus.nativeUI.showWaiting("正在更新资源...");
				//说明有更新
				var dtask = plus.downloader.createDownload(data.apk, {}, function(d, status) {
					if(status == 200) {
						// 下载成功
						var path = d.filename;
						plus.runtime.install(path); // 安装下载的apk文件  
					} else { //下载失败  
						alert("Download failed: " + status);
					}
				});
				dtask.addEventListener("statechanged", function(task, status) {
					switch(task.state) {
						case 1: // 开始  
							w.setTitle("　　 开始下载...　　 ");
							break;
						case 2: // 已连接到服务器  
							w.setTitle("　　 开始下载...　　 ");
							break;
						case 3:
							var a = task.downloadedSize / task.totalSize * 100;
							w.setTitle("　　 已下载" + parseInt(a) + "%　　 ");
							break;
						case 4: // 下载完成  
							w.close();
							break;
					}
				});
				dtask.start();
			} else {
				//无更新
				if(storageUser.IsLogin) {
					openNew("../main.html");
					return;
				}
			}
		}
	}, false, function() {
		plus.nativeUI.closeWaiting();
	});

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
})