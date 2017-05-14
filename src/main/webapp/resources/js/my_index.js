$(function() {

});
function search() {
    function e() {
        var e = $.cookie("searchVal");
        if (e) {
            for (var t = decodeURIComponent(e), n = JSON.parse(t), o = n.data, s = "", a = 0; a < o.length && 4 > a; a++) s += '<a href="category.html?from=1' + i + "&scat=4&key_word=" + decodeURIComponent(decodeURIComponent(o[a])) + '"><li class="keyword" data-icon="&#x005b">' + decodeURIComponent(decodeURIComponent(o[a])) + "</li></a>";
            $(".history .u_list").html(s),
            $(".history").css("display", "block")
        }
    }
    function t() {
        var e = $("#search_input").val(),
        n = Date.parse(new Date);
        return "" == e.length ? ($(".search_clear").css("display", "none"), $(".search_sug").css("display", "none"), $(".hot_keywords").css("display", "block"), void(null == $.cookie("searchVal") ? $(".history").css("display", "none") : $(".history").css("display", "block"))) : ($(".search_clear").css("display", "block"), $(".hot_keywords,.history").css("display", "none"), $(".search_sug").css("display", "block"), void(e ? $.ajax({
            type: "get",
            url: "index.php?ctl=index&act=keywordsPromptNew&keystr=" + e + "&dotime=" + n + i,
            dataType: "text",
            success: function(e) {
                $(".search_sug .u_list").html(e),
                $(".import").on("click",
                function() {
                    var e = $(this).prev("a").text();
                    $("#search_input").val(e).focus(),
                    t()
                })
            }
        }) : $(".search_sug .u_list").html("")))
    }
    var i = "";
    "undefined" != typeof is_home && "1" == is_home && (i = "&sfrom=1");
    var n = $("#default_kwd").attr("value");
    $("#search_input").on("focus",
    function() {
        "" == $("#search_input").val() && ($(".search_module").addClass("fixed_search"), $(this).attr("placeholder", ""), $(".cancle,.search_show,.hot_keywords").css("display", "block"), $(".supernatant,.idx_viewpoint").css("display", "none"), e())
    }),
    $(".cancle").on("click",
    function() {
        $(".supernatant,.idx_viewpoint").css("display", "block"),
        $(".search_module").removeClass("fixed_search"),
        $("#search_input").val("").attr("placeholder", n),
        $(".cancle,.search_clear,.search_show,.hot_keywords,history,.search_sug").css("display", "none")
    }),
    $(".search_clear").on("click",
    function() {
        $("#search_input").val("").focus(),
        $(this).css("display", "none"),
        $(".search_sug").css("display", "none")
    }),
    $(".clear_btn").on("click",
    function() {
        $(".history .u_list").html(""),
        $(".history").css("display", "none"),
        $.cookie("searchVal", null)
    }),
    $("#search_input").on("input propertychange",
    function() {
        t()
    }),
    $(".search_btn").on("click",
    function() {
        var e = $("#search_input").val(),
        t = 2;
        $(".search_module").hasClass("fixed_search") ? "" == e ? alert({
            type: "toast",
            content: "请输入有效的关键字",
            time: 1e3
        }) : window.location.href = "category.html?from=1" + i + "&scat=" + t + "&key_word=" + encodeURIComponent(encodeURIComponent(e)) : "" == n ? alert({
            type: "toast",
            content: "请输入有效的关键字",
            time: 1e3
        }) : (t = 1, window.location.href = "category.html?from=1" + i + "&scat=" + t + "&key_word=" + encodeURIComponent(encodeURIComponent(n)))
    })
} !
function(e, t, i, n) {
    function o(t, i) {
        this.element = t,
        this.$element = e(t),
        this.options = e.extend({},
        o.defaults, i),
        this._init()
    }
    o.prototype = {
        _init: function() {
            this.$container = e('<div class="loading-container hidden" />'),
            this.$indicator = e('<div class="loading-indicator" data-icon="&#x005e" />').appendTo(this.$container),
            this.options.overlay && (this.$container.addClass("overlay"), this.$overlay = e('<div class="loading-overlay" />').appendTo(this.$container)),
            this.options.overlay && "custom" !== this.options.position && this.$indicator.addClass(this.options.position),
            this.options.fixed && this.$container.addClass("fixed"),
            "" === this.element.style.position && (this._addPositionRelative = !0),
            this._show()
        },
        _show: function() {
            this._addPositionRelative && (this.element.style.position = "relative"),
            this.$container.appendTo(this.$element).removeClass("hidden")
        },
        _hide: function() {
            this.$container.addClass("hidden"),
            this.$container.detach(),
            this._addPositionRelative && (this.element.style.position = "")
        },
        done: function() {
            this.$container.remove()
        }
    },
    o.defaults = {
        position: "center",
        overlay: !0,
        fixed: !0
    },
    e.fn.loading = function(e) {
        return this.each(function() {
            new o(this, e)
        })
    }
} (Zepto, window, document),
$(function() {
    $("#search_input").val(""),
    $("#search_input").on("keydown",
    function() {
        "13" == event.keyCode && $(".search_btn").click()
    }),
    search()
}),
$(function(e) {
    var t = function(t, i) {
        this.$element = e(t),
        this.init()
    };
    t.prototype = {
        init: function() {
            this.idx_swipe(),
            //this.idx_appdownload(),
            this.idx_timer(),
            this.idx_loadmore()
        },
        idx_swipe: function() {
            1 == index_switch ? e.ajax({
                type: "get",
                url: "index.php?ctl=index&act=homeActive",
                success: function(t) {
                    var i = JSON.parse(t);
                    e("#banner_swiper").html(i),
                    e(".banner").swipeSlide({
                        visibleSlides: 1,
                        autoPlay: !0,
                        onInit: function() {
                            //e("[data-lazyload]").lazyload()
                        }
                    })
                }
            }) : e(".banner").swipeSlide({
                visibleSlides: 1,
                autoPlay: !0,
                onInit: function() {
                    //e("[data-lazyload]").lazyload()
                }
            }),
            e("#best_brand").swipeSlide({
                visibleSlides: 3.5,
                bulletNavigation: ""
            }),
            e("#best_shop").swipeSlide({
                visibleSlides: 2,
                slidesPerGroup: 2
            })
        },
        idx_appdownload: function() {
            e("#appdownload").appstart(),
            e(".supernatant a").attr("href", "javascript:void(0)"),
            e(".supernatant .close_btn").on("click",
            function() {
                e(".supernatant").remove(),
                e.cookie("_", "1")
            })
        },
        idx_timer: function() {
            var t = delay_endtime;
            e(".timer").data("seconds-left", t)
        },
        idx_loadmore: function() {
            var t, i = total_page
            
        }
    };
    var i = function(e) {
        return new t(e)
    };
    window.index = e.index = i,
    i()
}); //@ sourceURL=index/my_index.js
