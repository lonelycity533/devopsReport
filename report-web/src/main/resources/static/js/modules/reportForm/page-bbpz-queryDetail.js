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
					field: 'PASSWORD',
					title: 'PASSWORD',
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
			reportName: '',
			businessInfos: []
		},
		// 添加条件
		onClickAdd: function() {
			var that = this;
			$(document).on('click', '.btn-add', function() {
				var $this = $(this);
				var $row = $this.closest('.layui-row');
				var $index = $row.index() + 1;
				var html='';
				html += '<div class="layui-row">';
				html += '<div class="layui-col-md10">';
				html += '<div class="layui-col-md1">';
				html += '<div class="layui-form-item label-width-0">';
				html += '<select class="data-column-select">';
				for(var i=0,j=that.data.businessInfos.length;i<j;i++){
					html += '<option value="'+that.data.businessInfos[i].columnName+'">'+that.data.businessInfos[i].columnName+'</option>';
				}
				html += '</select>';
				html += '</div>';
				html += '</div>';
				if(that.data.businessInfos[0].dataType=='DATE'){
					html += '<div class="layui-col-md2 pl-20 lay-input hide">';
				}else{
					html += '<div class="layui-col-md2 pl-20 lay-input">';
				}
				html += '<div class="layui-form-item label-width-0">';
				html += '<input name="dataValue' + ($index + 1) +'" type="text" class="layui-input data-value" value="">';
				html += '</div>';
				html += '</div>';
				if(that.data.businessInfos[0].dataType=='DATE'){
					html += '<div class="layui-col-md4 pl-20 lay-date">';
				}else{
					html += '<div class="layui-col-md4 pl-20 lay-date hide">';
				}
				html += '<div class="layui-form-item label-width-0">';
				html += '<div class="layui-col-md5">';
				html += '<input name="start' + ($index + 1) +'" type="text" class="layui-input layui-date data-start data-start'+($index+1)+'" readonly placeholder="请选择">';
				html += '</div>';
				html += '<div class="layui-col-md1 line">-</div>';
				html += '<div class="layui-col-md5">';
				html += '<input name="end' + ($index + 1) +'" type="text" class="layui-input layui-date data-end data-end'+($index+1)+'" readonly placeholder="请选择">';
				html += '</div>';
				html += '</div>';
				html += '</div>';
				html += '<div class="layui-col-md2">';
				html += '<div class="layui-form-item label-width-0">';
				html += '<a href="javascript:" class="btn btn-add">+添加条件</a>';
				html += '</div>';
				html += '</div>';
				html += '</div>';
				html += '</div>';
				$('.list').append(html);
				$this.remove();
				form.render('select', 'form');
				form.on('select', function(data) {
					var dataType='';
					for(var i=0,j=that.data.businessInfos.length;i<j;i++){
						if(data.value==that.data.businessInfos[i].columnName){
							dataType=that.data.businessInfos[i].dataType;
							break;
						}
					}
					if (dataType == 'DATE') {
						$(data.elem).closest('.layui-row').find('.lay-input').hide();
						$(data.elem).closest('.layui-row').find('.lay-date').show();
					} else {
						$(data.elem).closest('.layui-row').find('.lay-input').show();
						$(data.elem).closest('.layui-row').find('.lay-date').hide();
					}
				});
				laydate.render({
					elem: '.data-start'+($index + 1),
					format: 'yyyy-MM-dd HH:mm:ss',
					type: 'datetime',
					trigger: 'click'
				});
				laydate.render({
					elem: '.data-end'+($index + 1),
					format: 'yyyy-MM-dd HH:mm:ss',
					type: 'datetime',
					trigger: 'click'
				});
			})
		},
		// 初始化时间选择
		onInitLaydate: function() {
			var that = this;
			$('.data-start').each(function() {
				laydate.render({
					elem: this,
					format: 'yyyy-MM-dd HH:mm:ss',
					type: 'datetime',
					trigger: 'click'
				});
			});
			$('.data-end').each(function() {
				laydate.render({
					elem: this,
					format: 'yyyy-MM-dd HH:mm:ss',
					type: 'datetime',
					trigger: 'click'
				});
			});
		},
		// 查询信息
		onQueryData: function(data) {
			var that = this;
			// table渲染
			table.render({
				elem: '#table',
				title: that.reportName,
				defaultToolbar: [],
				cols: that.html.cols1,
				toolbar: '#tableToolbar',
				data: data,
				page: {
					prev: '<i class="layui-icon layui-icon-left"></i>',
					next: '<i class="layui-icon layui-icon-right"></i>',
					limits: [10, 20, 30, 40, 50, 1000],
					layout: ['count', 'limit', 'skip', 'prev', 'page', 'next']
				},
				request: {
					pageName: 'current', //页码的参数名称，默认：page
					limitName: 'size' //每页数据量的参数名，默认：limit
				},
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
					var columnName = '';
					if($('.list .layui-row').eq(i).find('.data-column').length>0){
						columnName = $('.list .layui-row').eq(i).find('.data-column').text();
					}else{
						columnName = $('.list .layui-row').eq(i).find('.data-column-select').val();
					}
					var start = '';
					var end = '';
					for (var key in data.field) {
						for(var x=0,y=that.data.businessInfos.length;x<y;x++){
							if(columnName==that.data.businessInfos[x].columnName){
								dataType=that.data.businessInfos[x].dataType;
								break;
							}
						}	
						if (key == 'dataValue' + (i + 1)) {
							dataValue = data.field[key]
						}
						if (key == 'start' + (i + 1)) {
							start = data.field[key]
						}
						if (key == 'end' + (i + 1)) {
							end = data.field[key]
						}
					}
					if (start && end) {
						dataValue = [start, end]
					}
					businessInfoList.push({
						"columnName": columnName,
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
					url: base + '/report/system/qdReport/getReportData?current=1&size=100000',
					// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp6.json?current=1&size=100000',
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
						url: base + '/report/system/qdReport/exportReportData',
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
						that.data.businessInfos = res.data.businessInfos;
						var length=that.data.businessInfos>1?2:that.data.businessInfos.length;
						var html = '';
						for (var i = 0, j = length; i < j; i++) {
							html += '<div class="layui-row">';
							html += '<div class="layui-col-md10">';
							html += '<div class="layui-col-md1">';
							html += '<div class="layui-form-item label-width-90">';
							html += '<label class="layui-form-label data-column">' + that.data.businessInfos[i].columnName + '</label>';
							html += '</div>';
							html += '</div>';
							if (that.data.businessInfos[i].dataType == 'DATE') {
								html += '<div class="layui-col-md2 pl-20 lay-input hide">';
							} else {
								html += '<div class="layui-col-md2 pl-20 lay-input">';
							}
							html += '<div class="layui-form-item label-width-0">';
							if (that.data.businessInfos[i].dataValue) {
								html += '<input name="dataValue' + (i + 1) +'" type="text" class="layui-input data-value" value="' + that.data.businessInfos[i].dataValue + '">';
							} else {
								html += '<input name="dataValue' + (i + 1) +'" type="text" class="layui-input data-value" value="">';
							}
							html += '</div>';
							html += '</div>';
							if (that.data.businessInfos[i].dataType == 'DATE') {
								html += '<div class="layui-col-md4 pl-20 lay-date">';
							} else {
								html += '<div class="layui-col-md4 pl-20 lay-date hide">';
							}
							html += '<div class="layui-form-item label-width-0">';
							html += '<div class="layui-col-md5">';
							html += '<input name="start' + (i + 1) +'" type="text" class="layui-input layui-date data-start data-start'+(i+1)+'" readonly placeholder="请选择">';
							html += '</div>';
							html += '<div class="layui-col-md1 line">-</div>';
							html += '<div class="layui-col-md5">';
							html += '<input name="end' + (i + 1) +'" type="text" class="layui-input layui-date data-end data-end'+(i+1)+'" readonly placeholder="请选择">';
							html += '</div>';
							html += '</div>';
							html += '</div>';
							html += '<div class="layui-col-md2">';
							html += '<div class="layui-form-item label-width-0">';
							if (i == that.data.businessInfos.length - 1) {
								html +='<a href="javascript:" class="btn btn-add">+添加条件</a>';
							}
							html += '</div>';
							html += '</div>';
							html += '</div>';
							html += '</div>';
						}
						$('.list').html(html)
						form.render('select', 'form');
						that.onInitLaydate();
						form.on('select', function(data) {
							if (data.value == 'DATE') {
								$(data.elem).closest('.layui-row').find('.lay-input').hide();
								$(data.elem).closest('.layui-row').find('.lay-date').show();
							} else {
								$(data.elem).closest('.layui-row').find('.lay-input').show();
								$(data.elem).closest('.layui-row').find('.lay-date').hide();
							}
						});
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
