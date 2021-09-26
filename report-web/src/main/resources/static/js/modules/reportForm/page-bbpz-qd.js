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
					field: 'reportId',
					title: 'ID',
				}, {
					field: 'reportName',
					title: '报表',
				}, {
					field: 'reportDescribe',
					title: '描述',
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
			tableData:[],
			layerIndex:'',
			layerType:''
		},
		// 查询信息
		onQueryData: function(data) {
			var that = this;
			// table渲染
			table.render({
				elem: '#table',
				title: '清单报表配置',
				url: base + '/report/system/qdReport/getQdReport',
				// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp4.json',
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
								url: base + '/report/system/qdReport/deleteReportByIds/' + ids,
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
				// 新增
				if (event == 'add') {
					that.data.layerType='add';
					that.data.layerIndex = layer.open({
						type: 1,
						title: '清单报表配置',
						skin: 'layer-form',
						shadeClose: true,
						area: ['90%', '486px'],
						maxHeight: '80%',
						content: $('.layer-form'),
						success: function() {
							$('.layer-form').removeClass('layui-hide');
							// 清空数据
							$('.data-pzmc').val('');
							$('.data-pzms').val('');
							$('.data-kxpz-fieldname').val('');
							$('.part-2').html('');
						}
					});
				}
			});
		},
		// 编辑
		onClickEdit: function() {
			var that = this;
			$(document).on('click', '.btn-edit', function() {
				that.data.layerType='edit';
				var $this=$(this);
				var $index=$('.layui-table-wrap .btn-edit').index($this);
				var $data=that.data.tableData[$index];
				var load = layer.load(3);
				$.ajax({
					url: base + '/report/system/qdReport/getQdDetailByName',
					// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp5.json',
					data: {reportName:$data.reportName},
					dataType: 'json',
					type: 'get',
					success: function(res) {
						layer.close(load);
						// 成功回调
						if(res.code==20000){
							that.data.layerIndex=layer.open({
								type: 1,
								title: '清单报表配置',
								skin: 'layer-form',
								shadeClose: true,
								area: ['90%', '486px'],
								maxHeight: '80%',
								content: $('.layer-form'),
								success: function() {
									$('.layer-form').removeClass('layui-hide');
									// 赋值
									$('.data-pzmc').val(res.data.databaseName);
									$('.data-pzms').val();
									var con='';
									for(var i=0,j=res.data.fieldList.length;i<j;i++){
										con += '<div class="part-el">';
										con += '<div class="layui-col-md10">';
										con += '<div class="layui-col-md12 layui-form-item-title">已配置属性'+(i+1)+'</div>';
										con += '<div class="layui-col-md4">';
										con += '<div class="layui-form-item">';
										con += '<label class="layui-form-label">field_name</label>';
										con += '<select class="data-xpz-fieldname">';
										if(res.data.fieldList[i].sqlType=='主SQL'){
											con += '<option value="主SQL" selected>主SQL</option>';
										}else{
											con += '<option value="主SQL">主SQL</option>';
										}
										if(res.data.fieldList[i].sqlType=='从SQL'){
											con += '<option value="从SQL" selected>从SQL</option>';
										}else{
											con += '<option value="从SQL">从SQL</option>';
										}
										con += '</select>';
										con += '</div>';
										con += '</div>';
										con += '<div class="layui-col-md4">';
										con += '<div class="layui-form-item">';
										con += '<label class="layui-form-label">field_value</label>';
										con += '<input type="text" placeholder="" autocomplete="off" class="layui-input data-xpz-fieldvalue" value="'+res.data.fieldList[i].sqlContent+'">';
										con += '</div>';
										con += '</div>';
										con += '<div class="layui-col-md2">';
										con += '<i class="iconfont icon-lajitong btn-layer-del"></i>';
										con += '</div>';
										con += '</div>';
										con += '</div>';
									}
									$('.part-2').append(con);
									form.render('select','layer_form');
									var businessList='';
									for(var i=0,j=res.data.businessList.length;i<j;i++){
										businessList+=res.data.businessList[i].businessField+',';
									}
									businessList=businessList.substring(0,businessList.length-1);
									$('.data-kxpz-fieldname').val(businessList);
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
				
			})
			$(document).on('click', '.btn-layer-add', function() {
				var length=$('.part-el').length;
				var con = '<div class="part-el">';
				con += '<div class="layui-col-md10">';
				con += '<div class="layui-col-md12 layui-form-item-title">新增属性'+(length+1)+'</div>';
				con += '<div class="layui-col-md4">';
				con += '<div class="layui-form-item">';
				con += '<label class="layui-form-label">field_name</label>';
				con += '<select class="data-xpz-fieldname">';
				con += '<option>主SQL</option>';
				con += '<option>从SQL</option>';
				con += '</select>';
				con += '</div>';
				con += '</div>';
				con += '<div class="layui-col-md4">';
				con += '<div class="layui-form-item">';
				con += '<label class="layui-form-label">field_value</label>';
				con += '<input type="text" placeholder="" autocomplete="off" class="layui-input data-xpz-fieldvalue">';
				con += '</div>';
				con += '</div>';
				con += '<div class="layui-col-md2">';
				con += '<i class="iconfont icon-lajitong btn-layer-del"></i>';
				con += '</div>';
				con += '</div>';
				con += '</div>';
				$('.part-2').append(con);
				form.render('select','layer_form');
			})
			// 删除配置
			$(document).on('click', '.btn-layer-del', function() {
				var $this=$(this);
				//移除dom
				$this.closest('.part-el').remove();
			})
			// 确定保存
			$(document).on('click', '.btn-layer-confirm', function() {
				var $this=$(this);
				var $pzmc=$('.data-pzmc').val();
				var $pzms=$('.data-pzms').val();
				var $kxpzFieldname=$('.data-kxpz-fieldname').val();
				// 下面部分看情况删减，是否需要强制输入
				if(!$pzmc){
					layer.msg('请输入配置名称', {
						icon: 5,
						anim: 6
					});
					return;
				}
				if(!$pzms){
					layer.msg('请输入配置描述', {
						icon: 5,
						anim: 6
					});
					return;
				}
				for(var i=0,j=$('.part-el').length;i<j;i++){
					var $xpzFieldname=$('.part-el').eq(i).find('.data-xpz-fieldname').val();
					var $xpzFieldvalue=$('.part-el').eq(i).find('.data-xpz-fieldvalue').val();
					var $title=$('.part-el').eq(i).closest('.part-el').find('.layui-form-item-title').text();
					if(!$xpzFieldname){
						layer.msg('请选择'+$title+'的field_name', {
							icon: 5,
							anim: 6
						});
						return;
					}
					if(!$xpzFieldvalue){
						layer.msg('请输入'+$title+'的field_value', {
							icon: 5,
							anim: 6
						});
						return;
					}
				}
				if(!$kxpzFieldname){
					layer.msg('请输入业务可选字段配置', {
						icon: 5,
						anim: 6
					});
					return;
				}
				var obj={};
				var businessField=$kxpzFieldname.split(/，|,/);
				var businessFieldArr=[];
				var fieldList=[];
				businessField.forEach(function(item){
					businessFieldArr.push({businessField:item})
				})
				for(var i=0,j=$('.part-el').length;i<j;i++){
					var $xpzFieldname=$('.part-el').eq(i).find('.data-xpz-fieldname').val();
					var $xpzFieldvalue=$('.part-el').eq(i).find('.data-xpz-fieldvalue').val();
					var $xpzPzmc=$('.part-el').eq(i).find('.data-xpz-pzmc').val();
					fieldList.push({sqlContent:$xpzFieldname,sqlType:$xpzFieldvalue})
				}
				obj.reportName=$pzmc;
				obj.reportDescribe=$pzms;
				obj.businessField=businessFieldArr;
				obj.fieldList=fieldList;
				if(that.data.layerType=='add'){
					var load = layer.load(3);
					$.ajax({
						url: base + '/report/system/qdReport/insertQdReport',
						// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp4.json',
						data: JSON.stringify(obj),
						contentType: 'application/json;charset=utf-8',
						dataType: 'json',
						type: 'post',
						success: function(res) {
							layer.close(load);
							// 成功回调
							if(res.code==200){
								layer.close(that.data.layerIndex);
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
				}
				if(that.data.layerType=='edit'){
					var load = layer.load(3);
					$.ajax({
						url: base + '/report/system/qdReport/updateReportDataConfig',
						// url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp4.json',
						data: JSON.stringify(obj),
						contentType: 'application/json;charset=utf-8',
						dataType: 'json',
						type: 'post',
						success: function(res) {
							layer.close(load);
							// 成功回调
							if(res.code==200){
								layer.close(that.data.layerIndex);
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
				}
				
			})
		},
		init: function() {
			var that = this;
			that.onQueryData({field:{reportName:''}});
			that.onClickTool();
			that.onClickSubmit();
			that.onClickEdit();
		}
	}
	main.init()
});
