<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<script type="text/javascript" src="/FoodField/resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var oEditors = [];
		$(function() {
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
					$.ajax({
						url:'reviewModify',
						type:'post',
						data:{title:$('input[name="title"]').val(),
								shop_name:$('input[name="shop_name"]').val(),
								shop_add:$('input[name="shop_add"]').val(),
								contents:oEditors.getById["ir1"].getIR()
						},
						dataType:'json',
						success:function(result){
							location.href="/FoodField/review/read?num=${vo.num}";
						}
					});
				}
			});
			
			nhn.husky.EZCreator.createInIFrame({
				oAppRef: oEditors,
				elPlaceHolder: "ir1",
				//SmartEditor2Skin.html ������ �����ϴ� ���
				sSkinURI: "/FoodField/resources/editor/SmartEditor2Skin.html",	
				htParams : {
					// ���� ��� ���� (true:���/ false:������� ����)
					bUseToolbar : true,				
					// �Է�â ũ�� ������ ��� ���� (true:���/ false:������� ����)
					bUseVerticalResizer : true,		
					// ��� ��(Editor | HTML | TEXT) ��� ���� (true:���/ false:������� ����)
					bUseModeChanger : true,			
					fOnBeforeUnload : function(){
						
					}
				}, 
				fOnAppLoad : function(){
					//���� ����� ������ text ������ �����ͻ� �ѷ��ְ��� �Ҷ� ���
					oEditors.getById["ir1"].exec("PASTE_HTML", ['${vo.contents}']);
				},
				fCreator: "createSEditor2"
			});
		})
		
	</script>
    
<div class="container">
        <div class="row">
            <div class="col-lg-12">
            	<!-- �Է� �ڽ�  -->
				<form class="form-horizontal" role="form" method="post" name="inputForm" id="inputForm">
					<!--  ���� �κ�  -->
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">����</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="title"
								placeholder="input title" value="${vo.title }">
						</div>
					</div>
					<!-- ��Ұ˻��κ� -->
					<div class="form-group">
						<label for="human" class="col-sm-2 control-label">���</label>
						<div class="col-sm-10">
							<div class="input-group">
								<input type="text" class="form-control" id="place"><span class="input-group-btn">
									<button class="btn btn-default" type="button" id="searchmap">�˻�</button>
								</span>
							</div>
						</div>
					</div>
					<!-- ��� �ߴ� �κ� -->
					<div class="form-group">
						<label for="email" class="col-sm-2 control-label">SHOPNAME</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="shop_name" placeholder="�����̸�" readonly="readonly" value="${vo.shop_name }">
						</div>
						<label for="email" class="col-sm-2 control-label">SHOPADD</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="shop_add" placeholder="�����ּ�" readonly="readonly" value="${vo.shop_add }">
						</div>
					</div>
					<!-- ���� �κ� -->
					<div class="form-group">
						<label for="message" class="col-sm-2 control-label">����</label>
						<div class="col-sm-10">
							<textarea rows="10" cols="30" id="ir1" name="contents"
								style="width: 95%; height: 412px; min-width:260px; display:none;"></textarea>
						</div>
					</div>
					<!-- ���۹�ư  -->
					<div class="form-group">
						<div class="col-sm-10 col-sm-offset-2" style="text-align: center;">
								<button class="btn icon-btn btn-primary" type="button" id="submit"><span class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted"></span>�������Ϸ�</button>
								
						</div>
					</div>
				</form>
            </div>
        </div>
  </div>