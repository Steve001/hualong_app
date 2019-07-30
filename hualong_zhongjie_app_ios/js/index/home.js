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
	//document.getElementById("newslist").addEventListener('click', function() {
		//openNew('news.html');
	//}); 
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
/* function loadData() {
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
} */
