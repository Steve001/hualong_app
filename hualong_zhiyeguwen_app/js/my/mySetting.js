mui.init({
	beforeback: function() {
		appPage.closeLogin();
	}
});
mui.plusReady(function() {
	storage.init();
	//判断是否登录
	if(storageUser.IsLogin) {

		document.getElementById("loginOut").addEventListener("tap", function() {
			var btnArray = ['否', '是'];
			mui.confirm('退出后您将不能查看个人数据，确定退出？', '', btnArray, function(e) {
				if(e.index == 1) {
					//appUI.showWaiting();
					storageUser.loginOut(); //退出
					plus.storage.clear();

					plus.oauth.getServices(function(services) {
						for(var i in services) {
							var service = services[i];
							log("我是" + service.id)
							if(service.id == "qq" || service.id == "weixin") {
								log(service.id)
								//								var isInstalled = plusIsInstalled(service.id);
								//								if(isInstalled) {
								service.logout(function(e) {
									log("注销成功")
								}, function(e) {
									log("注销失败")
								});
								//}

							}
						}
					});
					setTimeout(function() {
						//log("关闭了")
						//appUI.closeWaiting();
						mui.back();
					}, 800);

				}
			})

		});

	}
	//appPage.closeLogin();
})