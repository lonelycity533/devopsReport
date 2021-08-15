layui.use(['element', 'form', 'table', 'layer', 'upload'], function() {
	var form = layui.form;
	var layer = layui.layer;
	var main = {
		html: {
			
		},
		// 保存
		onClickSave: function() {
			var that = this;
			$('.btn-save').on('click',function(){
				var load = layer.load(3);
				$.ajax({
					url: base + '/data/table3.json',
					data: {},
					dataType: 'json',
					type: 'post',
					success: function() {
						layer.close(load)
						// 成功回调
						window.history.go(-1);
					},
					error: function() {
						layer.close(load)
						layer.msg('系统繁忙，请稍后再试～');
					}
				})
			})
		},
		init: function() {
			var that = this;
			that.onClickSave();
		}
	}
	main.init()
});
