QC.Login({
	btnId:"tx_weibo",
    redirectURI : 'http://passport.766.com/bind'
}, function(dt, opts){//登录成功
	//根据返回数据，更换按钮显示状态方法
	var dom = document.getElementById(opts['btnId']),
	_logoutTemplate=[
	                 //头像
	                 //'<span><img src="{figureurl}" class="{size_key}"/></span>',
	                 //昵称
	                 //'<span>{nickname}</span>',
	                 //退出
	                 //'<span><a href="javascript:QC.Login.signOut();">退出</a></span>'	
	                 ].join("");
	dom && (dom.innerHTML = QC.String.format(_logoutTemplate, {
		nickname : QC.String.escHTML(reqData.nickname),
		figureurl : reqData.figureurl
	}));
}, function(opts){//注销成功
	alert('QQ登录 注销成功');
});

if(QC.Login.check()){//如果已登录
	QC.Login.getMe(function(openId, accessToken){
		alert(["当前登录用户的", "openId为："+openId, "accessToken为："+accessToken].join("\n"));
	});
	//这里可以调用自己的保存接口
	//...
}