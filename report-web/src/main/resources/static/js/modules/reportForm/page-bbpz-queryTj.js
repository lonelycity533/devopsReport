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
					field: 'col1',
					title: 'ID',
					templet: function(d) {
						return '<div class="data-id" data-id="' + d.col1 + '">' + d.col1 +
							'</div>'
					}
				}, {
					field: 'col2',
					title: '报表',
				}, {
					field: 'col3',
					title: '描述',
				}, {
					field: 'col4',
					title: '创建时间',
				}, {
					field: 'col5',
					title: '操作',
					templet: '<div><a href="javascript:" class="color-blue btn-detail">详情</a></div>'
				}]
			],
		},
		// 查询信息
		onQueryData: function(data) {
			var that = this;
			// table渲染
			table.render({
				elem: '#table',
				title: '统计报表配置',
				url: base + '/report/system/data/table3.json',
				defaultToolbar: [],
				where: data.field,
				cols: that.html.cols1,
				page: {
					prev: '<i class="layui-icon layui-icon-left"></i>',
					next: '<i class="layui-icon layui-icon-right"></i>',
					limits: [10, 20, 30, 40, 50, 1000],
					layout: ['count', 'limit', 'skip', 'prev', 'page', 'next']
				},
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
		// 删除
		onClickDel: function() {
			var that = this;
			$('.btn-del').on('click', function() {
				var $checked = $('.layui-table-view[lay-id="table"]').find(
					'.layui-table-body .layui-form-checked');
				var arr = []; //删除列表的数组
				for (var i = 0, j = $checked.length; i < j; i++) {
					arr.push($checked.eq(i).closest('tr').find('.data-id').data('id'))
				}
				if ($checked.length === 0) {
					layer.msg('请选择至少一行');
				} else {
					layer.confirm('确定要删除选中的统计报表配置吗？', function(index) {
						layer.close(index);
						//向服务端发送删除指令
						var load = layer.load(3);
						$.ajax({
							url: base + '/report/system/data/table3.json',
							data: {},
							dataType: 'json',
							type: 'post',
							success: function() {
								layer.close(load)
								// 成功回调
								var $tr = $(
										'.layui-table-view[lay-id="table"]')
									.find('.layui-table-body tr'); //当前行目
								for (var i = $tr.length - 1; i > -1; i--) {
									var $checkbox = $tr.eq(i).find(
										'.layui-form-checkbox');
									// 如果是选中的则删除
									if ($checkbox.hasClass(
											'layui-form-checked')) {
										$tr.eq(i).remove();
									}
								}
								layer.msg('删除成功！', {
									icon: 1,
								});
							},
							error: function() {
								layer.close(load)
								layer.msg('系统繁忙，请稍后再试～');
							}
						})
					});
				}
			})
		},
		// 详情
		onClickDetail: function() {
			var that = this;
			var layerIndex='';
			$(document).on('click', '.btn-detail', function() {
				window.location.href=base+'/reportForm/queryDetail';
			})
		},
		init: function() {
			var that = this;
			that.onQueryData({});
			that.onClickSubmit();
			that.onClickDel();
			that.onClickDetail();
		}
	}
	main.init()
});
