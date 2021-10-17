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
					templet: '<div><a href="javascript:" class="color-blue btn-detail">详情</a></div>'
				}]
			],
		},
		data:{
			tableData:[],
		},
		// 查询信息
		onQueryData: function(data) {
			var that = this;
			// table渲染
			table.render({
				elem: '#table',
				title: '清单报表',
				url: base + '/report/system/qdReport/getQdReport',
				url: base + '/other/2021/devops-report/report-web/src/main/resources/static/data/tmp4.json',
				defaultToolbar: [],
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
		// 详情
		onClickDetail: function() {
			var that = this;
			$(document).on('click', '.btn-detail', function() {
				var $this=$(this);
				var $index=$('.layui-table-wrap .btn-detail').index($this);
				var $data=that.data.tableData[$index];
				window.location.href=base+'/other/2021/devops-report/report-web/src/main/resources/templates/reportForm/page-bbpz-queryDetail.html?reportId='+$data.reportId+'&reportDetailId='+$data.reportDetailId+'&databaseId='+$data.databaseId+'&reportName='+$data.reportName;
			})
		},
		init: function() {
			var that = this;
			that.onQueryData({field:{reportName:''}});
			that.onClickSubmit();
			that.onClickDetail();
		}
	}
	main.init()
});
