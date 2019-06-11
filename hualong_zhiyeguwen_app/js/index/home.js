var lon, lat, city, timerobj, timer, bannerjson, singlematchjson, newmatchjson, nearstorejson, cardgroupjson, newsjson, newmatchpage = 2,
	nearstorepage = 2,
	swiper, hottopicjson;
mui.init();

mui.plusReady(function() {
	storage.init();
	//注册登录事件
	appPage.registerCheckLoginEvent();
	initPage();
	//咨询
	document.getElementById("newslist").addEventListener('click', function() {
		openNew('news.html');
	});
	//推介
	document.getElementById("tuijie").addEventListener('click', function() {
		openNew('tuijie.html');
	});
	//客户
	document.getElementById("customer").addEventListener('click', function() {
		openNew('customer.html');
	})
})

function initPage() {
	lon = storageLocation.Lon;
	lat = storageLocation.Lat;
	city = storageLocation.City;
	swiper = new Swiper('.swiper-container', {
		autoplay: 3000, //可选选项，自动滑动
		pagination: '.swiper-pagination',
		loop: true,
		autoplayDisableOnInteraction: false,
	});
}
//下拉刷新具体业务实现
function pulldownRefresh() {
	loadData();
}
//一次性拉取数据
function loadData() {
	request("/Index/getIndexLis", {
		lon: lon,
		lat: lat,
		cityid: storageLocation.CityId
	}, function(json) {
		if(json.code == "0") {
			newmatchpage = 2, nearstorepage = 2;
			bannerjson = {};
			bannerjson.data = json.data.bannerdata;

			if(!swiper) {
				render("#banner_warp", "banner_view", bannerjson);
				swiper = new Swiper('.swiper-container', {
					autoplay: 3000, //可选选项，自动滑动
					pagination: '.swiper-pagination',
					loop: true,
					autoplayDisableOnInteraction: false,
				});
			} else {
				swiper.stopAutoplay();
				swiper.removeAllSlides();
				var item, str;
				for(var i = 0; i < bannerjson.data.length; i++) {
					item = bannerjson.data[i];
					str = '<div class="swiper-slide addetail" data-href="' + item.HrefUrl + '" data-param=\'' + item.HrefParam + '\'><img class="loadthumb" data-url="' + item.ImgUrl + '" data-wh=",320" />';
					//log(str);
					swiper.appendSlide(str);
				}

				//render("#banner_warp", "banner_view", bannerjson,true);
				//				swiper.appendSlide("<div class='swiper-slide'><img src='../../images/banner.png' /></div>"+"<div class='swiper-slide'><img src='../../images/banner.png' /></div>"+"<div class='swiper-slide'><img src='../../images/banner.png' /></div>"+"<div class='swiper-slide'><img src='../../images/banner.png' /></div>");
				swiper.startAutoplay();
			}

			singlematchjson = {};
			singlematchjson.data = json.data.recentmatchdata;
			showSigleMatch();

			newmatchjson = {};
			newmatchjson.data = json.data.newestmatchdata;
			showNewMatch();

			nearstorejson = {};
			nearstorejson.data = json.data.nearbystoredata;
			showNearStore();

			cardgroupjson = {};
			cardgroupjson.data = json.data.taopaidata;
			render("#cardgroup_warp", "cardgroup_view", cardgroupjson);

			newsjson = {};
			newsjson.data = json.data.newestnewsdata;
			render("#news_warp", "news_view", newsjson);

			hottopicjson = {};
			hottopicjson.data = json.data.hottopicdata;
			document.getElementById("topic_warp").setAttribute('data-id', hottopicjson.data.NewsId)

			appPage.imgInit();
		} else {
			appUI.showTopTip(json.msg)
		}
		appPage.endPullRefresh();
	}, false, function() {
		appPage.endPullRefresh();
		var arr = document.getElementsByClassName("nodata");
		for(var i = 0; i < arr.length; i++) {
			arr[i].innerText = "暂无数据";
		}
	});
}
//拉取单条报名中数据
function loadData_SigleMatch() {
	request("/Index/getRecentMatchOne", {
		lon: lon,
		lat: lat,
		cityid: storageLocation.CityId
	}, function(json) {
		singlematchjson = {};
		singlematchjson.data = json.data.matchDdistanceMin;
		showSigleMatch();
		appPage.imgInit();
	}, false, function() {}, false);
}
//赛事换一组
function loadData_NewMatch() {
	log(newmatchpage)
	request("/Index/newestMatchChangeGroup", {
		cityid: storageLocation.CityId,
		pageindex: newmatchpage
	}, function(json) {
		if(json.code == 0) {
			newmatchjson = {};
			newmatchjson.data = json.data;
			newmatchpage = json.pageindex;
			showNewMatch();
			appPage.imgInit();
		} else {
			log("空赛事" + json.msg)
		}
	}, true);
}
//店铺换一组
function loadData_NearStore() {
	request("/Index/nearbyStoreChangeGroup", {
		lon: lon,
		lat: lat,
		cityid: storageLocation.CityId,
		pageindex: nearstorepage
	}, function(json) {
		if(json.code == 0) {
			nearstorejson = {};
			nearstorejson.data = json.data;
			nearstorepage = json.pageindex;
			showNearStore();
			appPage.imgInit();
		} else {
			log("空店铺" + json.msg)
		}
	}, true);
}
//单条报名中赛事绑定显示
function showSigleMatch() {
	render("#siglematch_warp", "siglematch_view", singlematchjson);
	if(singlematchjson.data != null) {
		document.getElementById("siglematch_warp").style.display = 'block';
		timerobj = appUI.countDown(singlematchjson.data.MatchBeginTime || "2017-1-1 00:00:00");
		if(timerobj.hour == "0" && timerobj.minute == "0" && timerobj.second == "0") { //倒计时结束
			window.clearInterval(timer); //清除定时器	
			loadData_SigleMatch();
			return;
		}
		//倒计时
		timer = setInterval(function() {
			timerobj = appUI.countDown(singlematchjson.data.MatchBeginTime);
			//log(JSON.stringify(timerobj));
			if(timerobj.hour == "0" && timerobj.minute == "0" && timerobj.second == "0") { //倒计时结束
				window.clearInterval(timer); //清楚定时器			
				loadData_SigleMatch(); //更新显示内容
				return;
			}
			document.getElementById("timer_h").innerText = timerobj.hour < 10 ? "0" + timerobj.hour : timerobj.hour;
			document.getElementById("timer_m").innerText = timerobj.minute < 10 ? "0" + timerobj.minute : timerobj.minute;
			document.getElementById("timer_s").innerText = timerobj.second < 10 ? "0" + timerobj.second : timerobj.second;
		}, 1000);
	} else {
		document.getElementById("siglematch_warp").style.display = 'none';
	}
}
//赛事绑定显示
function showNewMatch() {
	render("#newmatch_warp", "newmatch_view", newmatchjson);
}
//店铺推荐绑定显示
function showNearStore() {
	render("#nearstore_warp", "nearstore_view", nearstorejson);
}

//自定义监听城市选择
window.addEventListener("citySelect", function(event) {
	storage.init();
	document.getElementById("city").innerHTML = event.detail.city;
	loadData();
})

var pkEvent = {
	gonearPeople: function() {
		openNew("nearPeople.html");
	}
}