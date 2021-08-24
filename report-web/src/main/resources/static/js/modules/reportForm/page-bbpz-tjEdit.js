layui.use(['element', 'form', 'table', 'layer', 'upload'], function() {
	var form = layui.form;
	var layer = layui.layer;
	var table = layui.table;
	var upload = layui.upload;
	var main = {
		html: {
			cols1: [
				[{
					type: 'checkbox',
				}, {
					field: 'col1',
					title: '',
				}, {
					field: 'col2',
					title: '行字段1',
					templet: '<div><i class="iconfont icon-xiugai btn-edit"></i></div>'
				}, {
					field: 'col3',
					title: '行字段2',
					templet: '<div><i class="iconfont icon-xiugai btn-edit"></i></div>'
				}, {
					field: 'col4',
					title: '行字段3',
					templet: '<div><i class="iconfont icon-xiugai btn-edit"></i></div>'
				}, {
					field: 'col5',
					title: '行字段4',
					templet: '<div><i class="iconfont icon-xiugai btn-edit"></i></div>'
				}, {
					field: 'col6',
					title: '行字段5',
					templet: '<div><i class="iconfont icon-xiugai btn-edit"></i></div>'
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
		// 上传
		onClickUpload: function() {
			var that = this;
			//执行实例
			upload.render({
				elem: '.btn-upload',
				url: base + '/report/system/data/table4.json',
				accept: 'file',
				// auto:false,
				choose: function(obj) {
					obj.preview(function(index, file, result) {
						$('.btn-upload-name').text(file.name)
					});
				},
				done: function(res) {
					//上传完毕回调
					table.render({
						elem: '#table',
						title: '统计报表配置',
						defaultToolbar: [],
						cols: that.html.cols1,
						data: res.data,
					});
				},
				error: function() {
					//请求异常回调
					layer.msg('系统繁忙，请稍后再试～');
				}
			});
		},
		// 编辑
		onClickEdit: function() {
			var that = this;
			var layerIndex = '';
			// 编辑
			$(document).on('click', '.btn-edit', function() {
				layerIndex = layer.open({
					type: 1,
					title: '统计报表配置',
					skin: 'layer-form',
					shadeClose: true,
					area: ['90%', '486px'],
					maxHeight: '80%',
					content: $('.layer-form'),
					success: function() {
						$('.layer-form').removeClass('layui-hide');
					}
				});
			})
			// 编辑全部
			$(document).on('click', '.btn-editAll', function() {
				var $checked = $('.layui-table-view[lay-id="table"]').find(
					'.layui-table-body .layui-form-checked');
				if ($checked.length === 0) {
					layer.msg('请选择至少一行');
					return;
				}
				layerIndex = layer.open({
					type: 1,
					title: '统计报表配置',
					skin: 'layer-form',
					shadeClose: true,
					area: ['90%', '486px'],
					maxHeight: '80%',
					content: $('.layer-form'),
					success: function() {
						$('.layer-form').removeClass('layui-hide');
					}
				});
			})
			$(document).on('click', '.btn-layer-add', function() {
				var filter = 'filter' + $('.part-el').length;
				var con = '<div class="part-el">';
				con += '<div class="layui-col-md10">';
				con += '<div class="layui-col-md12 layui-form-item-title">已配置属性1（取消）</div>';
				con += '<div class="layui-col-md4">';
				con += '<div class="layui-form-item">';
				con += '<label class="layui-form-label">field_name</label>';
				con += '<select class="data-xpz-fieldname">';
				con += '<option>请选择</option>';
				con += '<option>请选择1</option>';
				con += '<option>请选择2</option>';
				con += '</select>';
				con += '</div>';
				con += '</div>';
				con += '<div class="layui-col-md4">';
				con += '<div class="layui-form-item">';
				con += '<label class="layui-form-label">field_value</label>';
				con +=
					'<input type="text" placeholder="" autocomplete="off" class="layui-input data-xpz-fieldvalue">';
				con += '</div>';
				con += '</div>';
				con += '<div class="layui-col-md4">';
				con += '<div class="layui-form-item">';
				con += '<label class="layui-form-label">配置名称</label>';
				con +=
					'<input type="text" placeholder="请输入配置名称" autocomplete="off" class="layui-input data-xpz-pzmc">';
				con += '</div>';
				con += '</div>';
				con += '</div>';
				con += '<div class="layui-col-md2">';
				con += '<i class="iconfont icon-lajitong btn-layer-del"></i>';
				con += '</div>';
				con += '</div>';
				$('.part-3').append(con);
				form.render('select', 'layer_form');
			})
			// 删除配置
			$(document).on('click', '.btn-layer-del', function() {
				var $this = $(this);
				//移除dom
				$this.closest('.part-el').remove();
			})
			// 确定
			$(document).on('click', '.btn-layer-confirm', function() {
				var $this = $(this);
				var $pzmc = $('.data-pzmc').val();
				var $ypzFieldname = $('.data-ypz-fieldname').val();
				var $ypzFieldvalue = $('.data-ypz-fieldvalue').val();
				var $ypzPzmc = $('.data-ypz-pzmc').val();
				var $kxpzFieldname = $('.data-kxpz-fieldname').val();
				// 下面部分看情况删减，是否需要强制输入
				if (!$pzmc) {
					layer.msg('请输入配置名称', {
						icon: 5,
						anim: 6
					});
					return;
				}
				if (!$ypzFieldname) {
					layer.msg('请选择field_name', {
						icon: 5,
						anim: 6
					});
					return;
				}
				if (!$ypzFieldvalue) {
					layer.msg('请输入field_value', {
						icon: 5,
						anim: 6
					});
					return;
				}
				if (!$ypzPzmc) {
					layer.msg('请输入配置名称', {
						icon: 5,
						anim: 6
					});
					return;
				}
				for (var i = 0, j = $('.part-el').length; i < j; i++) {
					var $xpzFieldname = $('.part-el').eq(i).find('.data-xpz-fieldname').val();
					var $xpzFieldvalue = $('.part-el').eq(i).find('.data-xpz-fieldvalue').val();
					var $xpzPzmc = $('.part-el').eq(i).find('.data-xpz-pzmc').val();
					if (!$xpzFieldname) {
						layer.msg('请选择field_name', {
							icon: 5,
							anim: 6
						});
						return;
					}
					if (!$xpzFieldvalue) {
						layer.msg('请输入field_value', {
							icon: 5,
							anim: 6
						});
						return;
					}
					if (!$xpzPzmc) {
						layer.msg('请输入配置名称', {
							icon: 5,
							anim: 6
						});
						return;
					}
				}
				if (!$kxpzFieldname) {
					layer.msg('请输入field_name', {
						icon: 5,
						anim: 6
					});
					return;
				}
				layer.close(layerIndex);
				layer.msg('修改成功！', {
					icon: 1,
				});
			})
		},
		// 保存
		onClickSave: function() {
			var that = this;
			$('.btn-save').on('click',function(){
				var load = layer.load(3);
				$.ajax({
					url: base + '/report/system/data/table3.json',
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
			that.onQueryData({});
			that.onClickSubmit();
			that.onClickUpload();
			that.onClickEdit();
			that.onClickSave();
		}
	}
	main.init()
});
