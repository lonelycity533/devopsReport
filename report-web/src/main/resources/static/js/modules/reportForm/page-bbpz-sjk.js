layui.use(['element', 'form', 'table', 'layer'], function() {
	var form = layui.form;
	var layer = layui.layer;
	var table = layui.table;
	var main = {
		html: {
			cols1: [
				[{
					type: 'checkbox',
				}, {
					field: 'databaseId',
					title: 'ID',
					templet: function(d) {
						return '<div class="data-id" data-id="' + d.databaseId + '">' + d.databaseId +
							'</div>'
					}
				}, {
					field: 'databaseName',
					title: '数据源名称',
				}, {
					field: 'databaseUrl',
					title: '数据库地址',
				}, {
					field: 'databaseType',
					title: '数据库类型',
				}, {
					field: 'createTime',
					title: '创建时间',
				}, {
					field: '',
					title: '操作',
					templet: '<div><i class="iconfont icon-xiugai btn-edit"></i></div>'
				}]
			],
		},
		data:{
			tableData:[]
		},
		// 查询信息
		onQueryData: function(data) {
			var that = this;
			// table渲染
			table.render({
				elem: '#table',
				title: '统计报表配置',
				url: base + '/report/system/database/getDatabaseList',
				// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp1.json',
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
					that.data.tableData = res.data.data.records
					return {
						"code": res.code == '20000' ? 0 : 1, //解析接口状态
						"msg": res.message, //解析提示文本
						"count": res.data.data.total, //解析数据长度
						"data": res.data.data.records //解析数据列表
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
				// 删除
				if (event == 'delete') {
					if (data.length === 0) {
						layer.msg('请选择至少一行');
					} else {
						var ids=[];
						data.forEach(function(item){
							ids.push(item.databaseId)
						})
						ids=ids.join(',')
						layer.confirm('真的删除行么', function(index) {
							layer.close(index);
							// 向服务端发送删除指令
							var load=layer.load(3);
							$.ajax({
								url: base + '/report/system/database/removeDatabase/' + ids,
								// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp2.json',
								data: {'ids':ids},
								dataType:'json',
								type:'post',
								success:function(res){
									layer.close(load)
									// 成功回调
									if (res.code == 200){
										layer.msg(res.message, {
											icon: 1
										});
										table.reload('table');
										return;
									}
									layer.msg(res.message);
								},
								error:function(){
									layer.close(load)
									layer.msg('系统繁忙，请稍后再试～');
								}
							})
						});
					}
				}
			});
		
		},
		// 编辑
		onClickEdit: function() {
			var that = this;
			$(document).on('click', '.btn-edit', function() {
				var $this=$(this);
				var $index=jQuery('.layui-table-wrap .btn-edit').index($this);
				window.sessionStorage.setItem('BbpzSjkInfo',JSON.stringify(that.data.tableData[$index]));
				window.location.href = base + '/report-web/src/main/resources/templates/reportForm/page-bbpz-sjkEdit.html';
			})
		},
		init: function() {
			var that = this;
			that.onQueryData({field:{databaseName:''}});
			that.onClickSubmit();
			that.onClickTool();
			that.onClickEdit();
		}
	}
	main.init()
});
