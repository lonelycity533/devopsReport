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
				}, {
					field: 'databaseName',
					title: '数据源名称',
				}, {
					field: 'databaseType',
					title: '数据库类型',
				}, {
					field: 'createTime',
					title: '创建时间',
				}, {
					field: 'updateTime',
					title: '更新时间',
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
				if (event == 'add') {
					var layerIndex = '';
					layerIndex = layer.open({
						type: 1,
						title: '新增',
						skin: 'layer-form',
						shadeClose: true,
						area: ['520px', '377px'],
						content: $('.layer-form'),
						btn: ['连接测试','确认生成', '返回'],
						success: function() {
							$('.layer-form').removeClass('layui-hide');
							$('.data-name').val('');
							$('.data-username').val('');
							$('.data-password').val('');
							$('.data-connectinfo').val('');
							$('.data-type').val('oracle');
							form.render('select','layerForm');
						},
						btn1:function(index){
							var $name=$('.data-name').val();
							var $type=$('.data-type').val();
							if(!$name){
								layer.msg('请输入数据源名称', {
									icon: 5,
									anim: 6
								});
								return false;
							}
							if(!$type){
								layer.msg('请选择数据库类型', {
									icon: 5,
									anim: 6
								});
								return false;
							}
							var obj={};
							obj.dataBaseName=$name;
							obj.dataBaseType=$type;
							var load = layer.load(3);
							$.ajax({
								url: base + '/report/system/database/testDatabase',
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
										return;
									}
									layer.msg(res.message);
								},
								error: function() {
									layer.close(load)
									layer.msg('系统繁忙，请稍后再试～');
								}
							})
						},
						btn2: function(index) {
							var $name=$('.data-name').val();
							var $username=$('.data-username').val();
							var $password=$('.data-password').val();
							var $connectinfo=$('.data-connectinfo').val();
							var $type=$('.data-type').val();
							if(!$name){
								layer.msg('请输入数据源名称', {
									icon: 5,
									anim: 6
								});
								return false;
							}
							if(!$username){
								layer.msg('请输入用户名', {
									icon: 5,
									anim: 6
								});
								return false;
							}
							if(!$password){
								layer.msg('请输入密码', {
									icon: 5,
									anim: 6
								});
								return false;
							}
							if(!$connectinfo){
								layer.msg('请输入连接信息', {
									icon: 5,
									anim: 6
								});
								return false;
							}
							if(!$type){
								layer.msg('请选择数据库类型', {
									icon: 5,
									anim: 6
								});
								return false;
							}
							layer.close(index);
							var obj={};
							obj.databaseName=$name;
							obj.databaseUsername=$username;
							obj.databasePassword=$password;
							obj.databaseUrl=$connectinfo;
							obj.databaseType=$type;
							var load = layer.load(3);
							$.ajax({
								url: base + '/report/system/database/insertDatabase',
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
										table.reload('table');
										return;
									}
									layer.msg(res.message);
								},
								error: function() {
									layer.close(load)
									layer.msg('系统繁忙，请稍后再试～');
								}
							})
						},
					});
				}
			});
		
		},
		// 编辑
		onClickEdit: function() {
			var that = this;
			var layerIndex = '';
			// 编辑
			$(document).on('click', '.btn-edit', function() {
				var $this=$(this);
				var $index=$('.layui-table-wrap .btn-edit').index($this);
				var $data=that.data.tableData[$index];
				layerIndex = layer.open({
					type: 1,
					title: '编辑',
					skin: 'layer-form',
					shadeClose: true,
					area: ['520px', '377px'],
					content: $('.layer-form'),
					btn: ['连接测试','确认生成', '返回'],
					success: function() {
						$('.layer-form').removeClass('layui-hide');
						$('.data-name').val($data.databaseName);
						$('.data-username').val($data.databaseUsername);
						$('.data-password').val($data.databasePassword);
						$('.data-connectinfo').val($data.databaseUrl);
						$('.data-type').val($data.databaseType);
						form.render('select','layerForm');
						
					},
					btn1:function(index){
						var $name=$('.data-name').val();
						var $type=$('.data-type').val();
						if(!$name){
							layer.msg('请输入数据源名称', {
								icon: 5,
								anim: 6
							});
							return false;
						}
						if(!$type){
							layer.msg('请选择数据库类型', {
								icon: 5,
								anim: 6
							});
							return false;
						}
						var load = layer.load(3);
						var obj={};
						obj.dataBaseName=$name;
						obj.dataBaseType=$type;
						var load = layer.load(3);
						$.ajax({
							url: base + '/report/system/database/testDatabase',
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
									return;
								}
								layer.msg(res.message);
							},
							error: function() {
								layer.close(load)
								layer.msg('系统繁忙，请稍后再试～');
							}
						})
					},
					btn2: function(index) {
						var $name=$('.data-name').val();
						var $username=$('.data-username').val();
						var $password=$('.data-password').val();
						var $connectinfo=$('.data-connectinfo').val();
						var $type=$('.data-type').val();
						if(!$name){
							layer.msg('请输入数据源名称', {
								icon: 5,
								anim: 6
							});
							return false;
						}
						if(!$username){
							layer.msg('请输入用户名', {
								icon: 5,
								anim: 6
							});
							return false;
						}
						if(!$password){
							layer.msg('请输入密码', {
								icon: 5,
								anim: 6
							});
							return false;
						}
						if(!$connectinfo){
							layer.msg('请输入连接信息', {
								icon: 5,
								anim: 6
							});
							return false;
						}
						if(!$type){
							layer.msg('请选择数据库类型', {
								icon: 5,
								anim: 6
							});
							return false;
						}
						layer.close(index);
						var obj={};
						obj.databaseName=$name;
						obj.databaseUsername=$username;
						obj.databasePassword=$password;
						obj.databaseUrl=$connectinfo;
						obj.databaseType=$type;
						obj.databaseId=$data.databaseId;
						var load = layer.load(3);
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
									table.reload('table');
									return;
								}
								layer.msg(res.message);
							},
							error: function() {
								layer.close(load)
								layer.msg('系统繁忙，请稍后再试～');
							}
						})
					},
				});
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
