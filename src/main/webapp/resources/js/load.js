/**
 * 
 */
(function ($) {
  $.fn.ShowMask = function (options) {
    var defaults = {
      top: 150,
      left: 150
    }
    var options = $.extend(defaults, options);
    $("html").append('<div id="ui-mask"></div><div id="ui-mask-div" style="z-index: 99999;position: fixed;top:' + options.top + 'px;left:' + options.left + 'px;"><img src="Images/ui-loading.gif" alt="" /><span>操作正在进行中，请耐心等待......</span></div>')
    _this_ = $("#ui-mask");
    _this_.height($(document).height())
    _this_.show();
  };
  $.fn.HideMask = function (options) {
    _this_ = $("#ui-mask");
    _this_.remove();
  };
})(jQuery);