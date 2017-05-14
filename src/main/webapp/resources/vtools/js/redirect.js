var stop=true; 
$(window).scroll(function(){ 
	//$(window).height()浏览器可视界面高度
	//$(window).scrollTop()浏览器可视窗口顶端距离网页顶端的高度（垂直偏移）
	//$(document).height()整个网页的文档高度
    totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop()); //浏览器可视界面底部相对与网页整体高度的位置
    if($(document).height() <= totalheight){ 
        if(stop==true){ 
			stop=false;
			window.location.href = $("#autoRedirect").val();
        } 
    } 
});