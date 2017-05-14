/**
 * 设置勾选状态
 */
var gdsArr = [];;// 选中商品的id集合
function setCheck(check, _this) {
	var id = $(_this).attr("id");
	if (id == "checkAll") {
		if (check)
			$('input').iCheck('check');
		else
			$('input').iCheck('uncheck');
	} else {
		var fid = id.split('ck_')[1];//收藏商品记录的id
		if (check) {
			gdsArr.push(fid);
		} else {
			gdsArr.remove(fid);
		}
	}
}
/**
 * 打开商品详情
 */
function OpenProduct(id) {
	var url = ctx + "/" + "productDetail?productId=" + id;
	window.location.href = url;
}
/**
 * 结算
 */
function Settlement(userId) {
	var len = gdsArr.length
	if (len == 0) {
		alert("请选择商品");
		return;
	}
	var json = {};
	for(var i=0;i<gdsArr.length;i++)
	{
	    json[i]=gdsArr[i];
	}
	var gdsJSON = JSON.stringify(json);//收藏商品的id的json
	var r = $.ajax({
        type: "post",
        url: ctx + "/member/delMyFavorite",
        async: false,
        data: {
        	"gdsJSON" : gdsJSON
        }
	 }).responseText;
	var json=eval("("+r+")");
	if(json["result"]=="success"){
		window.location.reload();
	}else{
		window.location.reload();
	};
}






/**
 * 编辑与非编辑状态切换
 */
function showEdit(){
$("#footEdit").toggle();
if($("#state").val()=="hide"){
	$("#state").val("show");
	$('input').iCheck({
		checkboxClass : 'icheckbox_square-red',
		increaseArea : '20%' // optional
	});
	//将方法ifChanged绑定到input
	$('input').on("ifChanged", function(event) {
		var check = event.currentTarget.checked;
		var _this = this;
		setCheck(check, _this)
	});
}else{
	$("#state").val("hide");
	$('input').off("ifChanged");
	$("input").iCheck({
		checkboxClass : 'icheckbox_square-hide',
		increaseArea : '20%' // optional
	});
}
}