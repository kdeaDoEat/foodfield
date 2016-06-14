<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<script type="text/javascript" src="/FoodField/resources/ckeditor/ckeditor.js" charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var oEditors = [];
		$(function() {

			CKEDITOR.replace('editor',{
				filebrowserImageUploadUrl: 'uploadPhoto?${_csrf.parameterName}=${_csrf.token}'
			});
			CKEDITOR.instances.editor.setData('${vo.contents}');
			
			$('#searchmap').on('click',function(){
				if($('#place').val() == ''){
					alert('�˻��ܾ �Է����ּ���!');
				}else{
					var popUrl = "searchMap?search="+$('#place').val();	//�˾�â�� ��µ� ������ URL
					var popOption = "width=870, height=660, resizable=no, scrollbars=no, status=no;";    //�˾�â �ɼ�(optoin)
					window.open(popUrl,"",popOption);
				} 
			});
			
			$('#submit').on('click',function(){
				var result = confirm('������ Ȯ���ϼ̽��ϱ�?');
				if(result){
					var con = CKEDITOR.instances.editor.getData().replace(/\r\n/g, '');
					if(charByteSize(con)>4000) {
						alert('4000byte ���Ϸ� �Է����ּ���! ���� : '+charByteSize(con));
						return;
					}
					$.ajax({
						url:'reviewModify?${_csrf.parameterName}=${_csrf.token}',
						type:'post',
						data:{title:$('input[name="title"]').val(),
								shop_name:$('input[name="shop_name"]').val(),
								shop_add:$('input[name="shop_add"]').val(),
								contents:con,
								num:"${vo.num}"
						},
						dataType:'json',
						success:function(result){
							location.herf="/FoodField/review/read?num=${vo.num}";
						}
					});
				}
			});
			window.parent.CKEDITOR.tools.callFunction('${CKEditorFuncNum}', '${file_path}', '���� ���� �Ϸ�.');
		})
		function charByteSize(msg) {
		if (msg == null || msg.length == 0)
			return 0;
		var i, size = 0;
		var charCode, chr = null;
		for (i = 0; i < msg.length; i++)
		{
			chr = msg.charAt(i);
			charCode = chr.charCodeAt(0);
			if (charCode <= 0x00007F)	size += 1;
			else if (charCode <= 0x0007FF)	size += 2;
			else if (charCode <= 0x00FFFF)  size += 3;
			else  size += 4;
		}
		return size;
	}
	</script>
    
<div class="container">
        <form class="form-horizontal" role="form" method="post" name="inputForm" id="inputForm">
		<!--  ���� �κ�  -->
		<div class="form-group">
		<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">����</label>
			<div class="col-xs-10 col-md-9">
				<input type="text" class="form-control" name="title"
					placeholder="input title" value="${vo.title }">
			</div>
		</div>
		
		<!-- ��Ұ˻��κ� -->
		<div class="form-group">
			<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">��ҡ�</label>
			<div class="col-xs-10 col-md-9">
				<div class="input-group">
					<input type="text" class="form-control" id="place"><span class="input-group-btn">
						<button class="btn btn-default" type="button" id="searchmap">�˻�</button>
					</span>
				</div>
			</div>
		</div>
		
		<!-- ��� �ߴ� �κ� -->
		<div class="form-group">
			<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">SHOPNAME��</label>
			<div class="col-xs-10 col-md-9">
				<input type="text" class="form-control" name="shop_name" placeholder="�����̸�" readonly="readonly" value="${vo.shop_name }">
			</div>
			<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">SHOPADD��</label>
			<div class="col-xs-10 col-md-9">
				<input type="text" class="form-control" name="shop_add" placeholder="�����ּ�" readonly="readonly" value="${vo.shop_add }">
			</div>
		</div>
		
		<!-- ���� �κ� -->
		<div class="form-group">
			<label class="col-xs-12 col-md-1 col-md-offset-1 control-label">���롡</label>
			<div class="col-xs-10 col-md-9">
				<textarea id="editor" style="width:100%; height:400px;"></textarea>
			</div>
		</div>
		<!-- ���۹�ư  -->
		<div class="form-group">
			<div class="col-xs-10 col-sm-offset-2" style="text-align: center;">
					<button class="btn icon-btn btn-default" type="button" id="submit"><span class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted"></span>������</button>
			</div>
		</div>
	</form>
  </div>