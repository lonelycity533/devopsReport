layui.use(['element', 'form', 'table', 'layer', 'upload'], function() {
	var form = layui.form;
	var layer = layui.layer;
	var main = {
		html: {
			
		},
		data:{
			formData:{}
		},
		// 保存
		onClickSave: function() {
			var that = this;
			$('.btn-save').on('click',function(){
				var load = layer.load(3);
				var obj={};
				obj.createTime=that.data.formData.createTime;
				obj.databaseId=$('.data-id').val();
				obj.databaseName=that.data.formData.databaseName;
				obj.databasePassword=$('.data-password').val();
				obj.databaseType=$('.data-type').val();
				obj.databaseUrl=that.data.formData.databaseUrl;
				obj.databaseUsername=$('.data-username').val();
				obj.updateTime=new Date().format('yyyy-MM-dd hh:mm:ss');
				$.ajax({
					url: base + '/report/system/database/updateDatabase',
					// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp3.json',
					data: JSON.stringify(obj),
					contentType: 'application/json;charset=utf-8',
					dataType: 'json',
					type: 'post',
					success: function(res) {
						layer.close(load)
						// 成功回调
						if(res.code==200){
							layer.msg(res.message, {
								icon: 1
							});
						}
						setTimeout(function(){
							window.history.go(-1);
						},500)
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
			var info=JSON.parse(window.sessionStorage.getItem('BbpzSjkInfo'))
			if(info){
				that.data.formData=info;
				$('.data-id').val(that.data.formData.databaseId);
				$('.data-username').val(that.data.formData.databaseUsername);
				$('.data-password').val(that.data.formData.databasePassword);
				$('.data-type').val(that.data.formData.databaseType);
				form.render('select','form');
			}
			that.onClickSave();
		}
	}
	main.init()
});
