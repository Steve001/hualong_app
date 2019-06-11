var closeLoad = false;
mui.init();
var showMenu = false,
	showPop = "";

var subPages = ["index/home.html", "index/empty.html", "index/empty.html", "my/user.html"];
var subPagesLoad = [false, false, false, false];
var subPageStyle = {
	top: '0',
	bottom: '51px',
	zindex: '0',
	position: 'relative'
}
var self, pkbtn_def, pkbtn_activity, defstyle, deftxt, activetxt, activestyle, activeTab, targetTab, firstPage, tabindex;
mui.plusReady(function() {
	self = plus.webview.currentWebview();
	storage.init();
	plus.navigator.setStatusBarBackground('#fff');
	plus.navigator.setStatusBarStyle('dark');

	for(var i = 0; i < subPages.length; i++) {
		var sub = plus.webview.create(subPages[i], subPages[i], subPageStyle);
		if(i == 0) {
			firstPage = sub;
		}
		self.append(sub);
	}
	plus.webview.show(subPages[0]);

	//底部切換
	activeTab = "index/home.html";
	mui('.mui-bar-tab').on('tap', 'a', function(e) {
		targetTab = this.dataset.href;
		tabindex = this.dataset.index;
		if(targetTab == 'my/user.html') {
			plus.navigator.setStatusBarBackground('#13D1BE');
			plus.navigator.setStatusBarStyle('light');
		} else {
			plus.navigator.setStatusBarBackground('#fff');
			plus.navigator.setStatusBarStyle('dark');
		}
		updatePKBtn(0);
		if(targetTab == activeTab) {
			return;
		}

		log("我是第" + tabindex + ",targetTab=" + targetTab)
		plus.webview.show(targetTab); //显示页面
		if(subPagesLoad[tabindex] == false) {
			mui.fire(plus.webview.getWebviewById(targetTab), 'refreshPage'); //初次刷新页面
			subPagesLoad[tabindex] = true;
		}
		//隐藏当前;
		plus.webview.hide(activeTab);
		//更改当前活跃的选项卡
		activeTab = targetTab;
	});

	//监听popover的状态，用于按下Back的时候逻辑处理
	mui('body').on('shown', '.mui-popover', function(e) {
		//		plus.nativeUI.closeWaiting();
		showPop = true
	})

	//首页返回键处理 逻辑：1秒内，连续两次按返回键，则退出应用；
	var first = null;
	mui.back = function() {
		if(showPop || showMenu) {
			if(showPop) {
				mui('#mainPopoverEl').popover('hide')
				showPop = false
			}
			showMenu ? closeMenu() : void(0);
		} else {
			if(!first) {
				first = new Date().getTime();
				mui.toast('再按一次会退出哦');
				setTimeout(function() {
					first = null;
				}, 1000);
			} else {
				if(new Date().getTime() - first < 1000) {
					plus.runtime.quit();
				}
			}
		}
	};

	// 获取本地应用资源版本号
	//	plus.runtime.getProperty(plus.runtime.appid, function(inf) {
	//		storageUser.refreshVersion(inf.version);
	//		if(storageUser.IsLogin) {
	//			request("/Player/editPlayerDeviceNum", {
	//				playerid: storageUser.UId,
	//				devicenum: JSON.stringify(inf)
	//			}, function(json) {
	//				log(json)
	//			})
	//		}
	//		log("当前应用版本：" + JSON.stringify(inf));
	//		setTimeout(function() {
	//			//alert(inf.version)
	//			checkUpdate(inf.version)
	//		}, 10000)
	//	});
	var needwait = localStorage.getItem("needwait");
	var ck = needwait != null && mui.os.android;
	//alert(needwait+","+mui.os.android+","+ck);
	if(ck) {
		//		mui.alert('恭喜，更新成功了~', '卡游精灵', function() {
		//			localStorage.removeItem("needwait");
		//			createPKBtn();
		//		});		
		setTimeout(function() {
			localStorage.removeItem("needwait");
			//createPKBtn();
		}, 1000);

	} else {
		//createPKBtn();
	}
})

function createPKBtn() {
	log("我是self：" + JSON.stringify(self))
	if(pkbtn_def || pkbtn_activity)
		return;
	var leftPos = Math.ceil((window.innerWidth - 60) / 2);
	var txtp1, txtp2;
	var iconp1, iconp2;
	if(mui.os.android) {
		txtp1 = {
			top: '2px',
			left: '2px',
			width: '56px',
			height: '56px'
		}
		iconp1 = {
			top: '0px',
			left: '0px',
			width: '60px',
			height: '60px'
		}
		txtp2 = {
			top: '2px',
			left: '2px',
			width: '56px',
			height: '56px'
		}
		iconp2 = {
			top: '1px',
			left: '1px',
			width: '56px',
			height: '56px'
		}
	} else {
		txtp1 = {
			top: '4px',
			left: '0px'
		}
		iconp1 = {
			top: '1px',
			left: '0px',
			width: '60px',
			height: '60px'
		}
		txtp2 = {
			top: '4px',
			left: '0px'
		}
		iconp2 = {
			top: '1px',
			left: '0px',
			width: '60px',
			height: '60px'
		}
	}
	//log("位置：" + txt_t + "," + txt_l + "," + icon_t + "," + icon_l)
	deftxt = {
		tag: 'font',
		id: 'icon',
		text: '\ue702',
		position: txtp1,
		textStyles: {
			fontSrc: '../fonts/iconfont.ttf',
			family: "iconfont",
			size: '56px',
			color: "#717171"
		}
	};
	defstyle = [{
		tag: 'rect',
		id: 'iconBg',
		position: iconp1,
		rectStyles: {
			color: '#fff',
			radius: '30px',
		}
	}, deftxt];
	activetxt = {
		tag: 'font',
		id: 'icon',
		text: '\ue703',
		position: txtp2,
		textStyles: {
			fontSrc: '../fonts/iconfont.ttf',
			family: "iconfont",
			size: '56px',
			color: '#fff'
		}
	};
	activestyle = [{
		tag: 'rect',
		id: 'iconBg',
		position: iconp2,
		rectStyles: {
			color: '#13D1BE',
			radius: '30px',
			borderColor: '#3e7ee7',
			borderWidth: '2px'
		}
	}, activetxt];
	pkbtn_def = new plus.nativeObj.View('pkbtn_def', {
		bottom: '0px',
		left: leftPos + 'px',
		width: '60px',
		height: '65px',
		position: 'dock' //此种停靠方式表明该控件应浮在窗口最上层，以免被其他窗口遮住
	}, defstyle);
	//	var defbmp = new plus.nativeObj.Bitmap('defbmp');
	//	defbmp.load('/images/PKdefault.svg', function() {
	//		log('defbmp.png load success!');
	//		pkbtn_def.drawBitmap(defbmp, {
	//		top: '0px',
	//		left: '0px',
	//		width: '60px',
	//		height: '60px'
	//	}, {
	//		top: '0px',
	//		left: '0px',
	//		width: '60px',
	//		height: '60px'
	//	});
	//		
	//	}, function(e) {
	//		log('defbmp.png load failed! ' + JSON.stringify(e));
	//	});

	pkbtn_activity = new plus.nativeObj.View('pkbtn_activity', {
		bottom: '0px',
		left: (leftPos + 1) + 'px',
		width: '60px',
		height: '65px',
		position: 'dock' //此种停靠方式表明该控件应浮在窗口最上层，以免被其他窗口遮住
	}, activestyle);
	//	var actbmp = new plus.nativeObj.Bitmap('actbmp');
	//	actbmp.load('/images/PK.svg', function() {
	//		log('actbmp.png load success!');
	//		
	//		pkbtn_activity.drawBitmap(actbmp, {
	//		top: '0px',
	//		left: '0px',
	//		width: '60px',
	//		height: '60px'
	//	}, {
	//		top: '0px',
	//		left: '0px',
	//		width: '60px',
	//		height: '60px'
	//	});
	//		
	//	}, function(e) {
	//		log('actbmp.png load failed! ' + JSON.stringify(e));
	//	});
	pkbtn_def.hide();
	pkbtn_activity.hide();

	self.append(pkbtn_activity);
	self.append(pkbtn_def);
	log("当前Webview窗口：" + self.getURL());

	if(mui.os.ios) {
		setTimeout(function() {
			pkbtn_def.draw(defstyle);
			pkbtn_activity.draw(activestyle);

			pkbtn_def.show();

		}, 800);
	} else {
		pkbtn_def.show();
	}

	//自定义监听图标点击事件
	pkbtn_def.addEventListener('click', function(e) {
		updatePKBtn(1);
		targetTab = "pk/pk.html";
		plus.navigator.setStatusBarBackground('#13D1BE');
		plus.navigator.setStatusBarStyle('light');
		if(targetTab == activeTab) {
			return;
		}
		plus.webview.show(targetTab);
		if(subPagesLoad[2] == false) {
			mui.fire(plus.webview.getWebviewById(targetTab), 'refreshPage'); //初次刷新页面
			subPagesLoad[2] = true;
		}
		//隐藏当前;
		plus.webview.hide(activeTab);
		//更改当前活跃的选项卡
		activeTab = targetTab;
		document.getElementsByClassName("mui-active")[0].setAttribute("class", "mui-tab-item");

	});

}

function updatePKBtn(type) {
	//	if(type == 1) { //显示活动
	//		pkbtn_def.hide();
	//		pkbtn_activity.show();
	//	} else { //显示默认
	//		pkbtn_def.show();
	//		pkbtn_activity.hide();
	//	}
}