layui.use(['element', 'form', 'table', 'layer', 'laydate'], function() {
	var form = layui.form;
	var layer = layui.layer;
	var table = layui.table;
	var laydate = layui.laydate;
	var main = {
		html: {
			cols1: [
				[{
					field: 'ID',
					title: 'ID',
				}, {
					field: 'USERNAME',
					title: 'USERNAME',
				}, {
					field: 'CREATE_TIME',
					title: 'CREATE_TIME',
				}]
			],
		},
		data: {
			tableData: [],
			reportId: '',
			reportDetailId: '',
			databaseId: '',
			reportName:'',
			businessInfos: []
		},
		// 添加条件
		onClickAdd: function() {
			var that = this;
			$(document).on('click', '.btn-add', function() {
				var $this = $(this);
				var $form = $this.closest('.layui-form');
				var $row = $this.closest('.layui-row');
				var $clone = $row.clone();
				var $index = $row.index() + 1;
				$clone.find('.layui-form-label').html('条件' + ($index + 1));
				$clone.find('select').prop('name', 'dataType' + ($index + 1)).val('网厅');
				$clone.find('input').prop('name', 'dataValue' + ($index + 1)).val('');
				$form.find('.list').append($clone);
				$this.remove();
				form.render('select', 'form');
			})
		},
		// 查询信息
		onQueryData: function(data) {
			var that = this;
			// table渲染
			table.render({
				elem: '#table',
				title: that.reportName,
				defaultToolbar: [],
				toolbar: '#tableToolbar',
				cols: that.html.cols1,
				// page: {
				// 	prev: '<i class="layui-icon layui-icon-left"></i>',
				// 	next: '<i class="layui-icon layui-icon-right"></i>',
				// 	limits: [10, 20, 30, 40, 50, 1000],
				// 	layout: ['count', 'limit', 'skip', 'prev', 'page', 'next']
				// },
				data: data
			});
			return;
		},
		// 提交
		onClickSubmit: function() {
			var that = this;
			form.on('submit(btn)', function(data) {
				var businessInfoList = [];
				for (var i = 0, j = $('.list .layui-row').length; i < j; i++) {
					var dataType = '';
					var dataValue = 'dataValue' + (i + 1);
					for (var key in data.field) {
						if (key == 'dataType' + (i + 1)) {
							dataType = data.field[key]
						}
						if (key == 'dataValue' + (i + 1)) {
							dataValue = data.field[key]
						}
					}
					businessInfoList.push({
						"columnName": "username",
						"dataType": dataType,
						"dataValue": dataValue
					})
				}
				var obj = {
					"businessInfoList": businessInfoList,
					"databaseId": that.databaseId,
					"reportDetailId": that.reportDetailId,
					"reportId": that.reportId
				}
				var load = layer.load(3);
				$.ajax({
					url: base + '/report/system/qdReport/getReportData?current=1&size=10',
					// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp6.json?current=1&size=10',
					data: JSON.stringify(obj),
					contentType: 'application/json;charset=utf-8',
					dataType: 'json',
					type: 'post',
					success: function(res) {
						layer.close(load)
						// 成功回调
						if (res.code == 20000) {
							that.onQueryData(res.data.list)
							return;
						}
						layer.msg(res.message);
					},
					error: function() {
						layer.close(load)
						layer.msg('系统繁忙，请稍后再试～');
					}
				})
			})
		},
		// 监听工具栏事件
		onClickTool: function() {
			var that = this;
			table.on('toolbar(table)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id),
					data = checkStatus.data,
					event = obj.event;
				// 导出
				if (event == 'export') {
					var load = layer.load(3);
					$.ajax({
						url: base + '/report/system/data/table3.json',
						data: {},
						dataType: 'json',
						type: 'post',
						success: function(res) {
							layer.close(load)
							// 成功回调
							table.exportFile('table', table.cache['table'], 'xls');
						},
						error: function() {
							layer.close(load)
							layer.msg('系统繁忙，请稍后再试～');
						}
					})
				}
			});

		},
		init: function() {
			var that = this;
			that.reportId = mixin.onGetUrlKey('reportId');
			that.databaseId = mixin.onGetUrlKey('databaseId');
			that.reportDetailId = mixin.onGetUrlKey('reportDetailId');
			that.reportName = mixin.onGetUrlKey('reportName');
			$('.layui-form-wrap-title').text(that.reportName)
			var load = layer.load(3);
			$.ajax({
				url: base + '/report/system/qdReport/getReportBusiness',
				// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp5.json',
				data: {
					reportId: that.reportId,
					reportDetailId: that.reportDetailId,
				},
				dataType: 'json',
				type: 'get',
				success: function(res) {
					layer.close(load)
					// 成功回调
					if (res.code == 20000) {
						that.businessInfos = res.data.businessInfos;
						var html = '';
						for (var i = 0, j = that.businessInfos.length; i < j; i++) {
							html += '<div class="layui-row">';
							html += '<div class="layui-col-md8">';
							html += '<div class="layui-col-md2">';
							html += '<div class="layui-form-item label-width-70">';
							html += '<label class="layui-form-label">条件'+(i+1)+'</label>';
							html += '<select name="dataType'+(i+2)+'">';
							if(that.businessInfos[i].dataType=='网厅'){
								html += '<option value="网厅" selected>网厅</option>';
							}else{
								html += '<option value="网厅">网厅</option>';
							}
							if(that.businessInfos[i].dataType=='掌厅'){
								html += '<option value="掌厅" selected>掌厅</option>';
							}else{
								html += '<option value="掌厅">掌厅</option>';
							}
							if(that.businessInfos[i].dataType=='短厅'){
								html += '<option value="短厅" selected>短厅</option>';
							}else{
								html += '<option value="短厅">短厅</option>';
							}
							html += '</select>';
							html += '</div>';
							html += '</div>';
							html += '<div class="layui-col-md2 pl-20">';
							html += '<div class="layui-form-item label-width-0">';
							html += '<input name="dataValue'+(i+1)+'" type="text" class="layui-input" value="'+that.businessInfos[i].dataValue+'">';
							html += '</div>';
							html += '</div>';
							html += '<div class="layui-col-md2">';
							html += '<div class="layui-form-item label-width-0">';
							if(i==that.businessInfos.length-1){
								html += '<a href="javascript:" class="btn btn-add">+添加条件</a>';
							}
							html += '</div>';
							html += '</div>';
							html += '</div>';
							html += '</div>';
						}
						$('.list').html(html)
						form.render('select', 'form');
						return;
					}
					layer.msg(res.message);
				},
				error: function() {
					layer.close(load)
					layer.msg('系统繁忙，请稍后再试～');
				}
			})
			that.onClickAdd();
			that.onClickSubmit();
			that.onClickTool();
		}
	}
	main.init()
});
