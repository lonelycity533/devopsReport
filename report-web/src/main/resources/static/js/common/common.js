(function() {
	// date格式化
	Date.prototype.format = function(format) {
		/*
		 * eg:format="YYYY-MM-dd hh:mm:ss";
		 */
		var o = {
			"M+": this.getMonth() + 1, // month
			"d+": this.getDate(), // day
			"h+": this.getHours(), // hour
			"m+": this.getMinutes(), // minute
			"s+": this.getSeconds(), // second
			"q+": Math.floor((this.getMonth() + 3) / 3), // quarter
			"S": this.getMilliseconds()
		}

		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for (var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] :
					("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	};
	var mixin = {
		 htmlspecialchars:function(str){
			 str = str.replace(/&amp;/g, '&');
              str = str.replace(/&lt;/g, '<');
              str = str.replace(/&gt;/g, '>');
              str = str.replace(/&quot;/g, "'");
              str = str.replace(/&#39;/g, "\"");
              return str;
		},
		onSetHeight: function(number) {
			var that = this;
			var height = $('body').height();
			return height - number;
		},
		onAutoScroll: function(obj) {
			var $table = obj.find(".layui-table-body .layui-table");
			$table.animate({
				'margin-top': "-50px"
			}, 400, function() {
				$(this).css({
					marginTop: "0px"
				}).find("tr:first").appendTo(this);
			})
		},
		/*
		拼接弹框templet
		obj例
		var obj = {
			cls: '',//class
			field:'',//对应列的key 如col1、col2、col3
			must: true,//是否必须选项
			multiple:true,//是否多选
			arr: [{//select数组
				value: 'http',
				name: 'http'
			}, {
				value: 'https',
				name: 'https'
			}, {
				value: '其他',
				name: '其他'
			}]
		}
		*/
		onSplicingTempletSelect: function(obj, col) {
			var html = '';
			// 选择框
			html += '<select class="data-' + obj.cls + '"';
			// 是否多选
			if (obj.multiple) {
				html += ' multiple';
			}
			html += '>'
			// 是否必须
			if (obj.must) {
				html += '<option value="">*代表必填项</option>';
			}
			obj.arr.forEach(function(n, i) {
				html += String(col[obj.field]).indexOf(String(n.value)) > -1 ? '<option value="' + n.value + '" selected>' + n
					.name +
					'</option>' : '<option value="' + n.value + '">' + n.name + '</option>';
			})
			html += '</select>'

			return html;
		},
		// 基础路径
		onSetBase: function() {
			var host = window.location.host;
			var protocol = window.location.protocol + '//';
			// window.base=protocol+host
			if (window.location.href.indexOf('/other/2021/rbac') > -1) {
				window.base = protocol + host + '/other/2021/rbac'
			} else {
				window.base = protocol + host
			}
		},
		onGetUrlKey: function(name) {
			return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[
				1].replace(/\+/g, '%20')) || null;
		},
	}
	mixin.onSetBase();
	window.mixin = mixin;
})()
