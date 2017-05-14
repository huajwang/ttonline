<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script src="${ctx}/resources/js/jquery.js"></script>
<script src="${ctx}/resources/js/common.js"></script>
<style type="text/css">
#ui-mask
{
  background-color: #666;
  position: absolute;
  z-index: 9999;
  left: 0;
  top: 0;
  display: none;
  width: 100%;
  height: 100%;
  opacity: 0.5;
  filter: alpha(opacity=50);
  -moz-opacity: 0.5;
}
#ui-mask-div img
{
  width: 50px;
  height: 50px;
  float: left;
}
#ui-mask-div span
{
  height: 50px;
  line-height: 50px;
  display: block;
  float: left;
  color: Red;
  font-weight: bold;
  margin-left: 5px;
}
</style>
<script>
	var ctx = '${ctx}';
</script>