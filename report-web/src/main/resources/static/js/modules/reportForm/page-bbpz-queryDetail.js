layui.use(['element', 'form', 'table', 'layer','laydate'], function() {
	var form = layui.form;
	var layer = layui.layer;
	var table = layui.table;
	var laydate = layui.laydate;
	var main = {
		html: {
			cols1: [
				[{
					field: 'col1',
					title: 'Column1',
				}, {
					field: 'col2',
					title: 'Column2',
				}, {
					field: 'col3',
					title: 'Column3',
				}, {
					field: 'col4',
					title: 'Column4',
				}, {
					field: 'col4',
					title: 'Column5',
				}, {
					field: 'col4',
					title: 'Column6',
				}, {
					field: 'col4',
					title: 'Column7',
				}]
			],
		},
		data:{
			tableData:[],
		},
		// 初始化时间选择
		onInitLaydate: function() {
			var that = this;
			laydate.render({
				elem: '#dateStart'
			});
			laydate.render({
				elem: '#dateEnd'
			});
		},
		// 添加条件
		onClickAdd:function(){
			var that = this;
			$(document).on('click','.btn-add',function(){
				var $this=$(this);
				var $form=$this.closest('.layui-form');
				var $row=$this.closest('.layui-row');
				var $clone=$row.clone();
				var $index=$row.index();
				$clone.find('.layui-form-label').html('条件'+($index+1))
				$form.append($clone);
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
				title: 'XX报表',
				url: base + '/report/system/qdReport/getReportBusiness',
				url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp4.json',
				defaultToolbar: [],
				toolbar: '#tableToolbar',
				where: data.field,
				cols: that.html.cols1,
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
				parseData: function(res) { //res 即为原始返回的数据
					that.data.tableData = res.data.records
					return {
						"code": res.code == '20000' ? 0 : 1, //解析接口状态
						"msg": res.message, //解析提示文本
						"count": res.data.total, //解析数据长度
						"data": res.data.records //解析数据列表
					};
				}
			});
			return;
		},
		// 提交
		onClickSubmit: function() {
			var that = this;
			form.on('submit(btn)', function(data) {
				that.onQueryData(data)
				return false;
			});
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
					//向服务端发送删除指令
					var load = layer.load(3);
					$.ajax({
						url: base + '/report/system/data/table3.json',
						data: {},
						dataType: 'json',
						type: 'post',
						success: function(res) {
							layer.close(load)
							// 成功回调
							table.exportFile('table', table.cache['table'],'xls'); 
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
			that.onInitLaydate();
			that.onClickAdd();
			var reportId=mixin.onGetUrlKey('reportId');
			var reportDetailId=mixin.onGetUrlKey('reportDetailId');
			that.onQueryData({field:{reportId:reportId,reportDetailId:reportDetailId}});
			that.onClickSubmit();
			that.onClickTool();
		}
	}
	main.init()
});
