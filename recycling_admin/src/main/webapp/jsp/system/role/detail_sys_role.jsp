<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<form id="role_modify_dialog_form" action="<%=contextPath%>/admin/saveSystemRole.do" class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label" for="role_name"><span style="color: red">*</span>角色名称:</label>
        <div class="controls">
            <input id="role_name" name="role_name" type="text" value="${map.role_name}"/>
        </div>
    </div>
    <div class="control-group">
        <c:forEach items="${adminMenusList}" var="menu" varStatus="m">
            <div id="accordionUpdate" class="accordion-style1 panel-group">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <input name="updateParentMenu${menu.menuId}" id="updateParentMenu${menu.menuId}" value="${menu.menuId}" type="checkbox" >
                            <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordionUpdate" href="#collapseUpdate${m.count}" aria-expanded="false">
                                <i class="ace-icon fa fa-angle-right bigger-110" data-icon-hide="ace-icon fa fa-angle-down" data-icon-show="ace-icon fa fa-angle-right"></i>
                                &nbsp;&nbsp;&nbsp;&nbsp;${menu.menuName}
                            </a>
                        </h4>
                    </div>

                    <div class="panel-collapse collapse" id="collapseUpdate${m.count}" aria-expanded="false">
                        <div class="panel-body">
                            <label>
                                <c:forEach items="${menu.listSubMenu}" var="subMenu" varStatus="sub">
                                    <input name="updateSubMenu${menu.menuId}" id="updateSubMenu${subMenu.menuId}" value="${subMenu.menuId}" type="checkbox" >
                                    &nbsp;&nbsp;&nbsp;&nbsp;${subMenu.menuName}
                                </c:forEach>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <input id="update_parent_menu" name="parent_menu" type="hidden" value="${map.super_menu_ids}"/>
    <input id="update_sub_menu" name="sub_menu" type="hidden" value="${map.sub_menu_ids}"/>
    <input type="hidden" id="role_id" name="role_id" value="${map.admin_role_id}"/>
    <input type="hidden" name="method" value="update"/>
</form>
<script type="text/javascript">
    $(function(){
        //父菜单
        if($("#update_parent_menu").val()){
            $.each(JSON.parse($("#update_parent_menu").val()), function(i, n){
                $("#updateParentMenu"+n).prop("checked",true);
            });
        }
        //子菜单
        if($("#update_sub_menu").val()){
            $.each(JSON.parse($("#update_sub_menu").val()), function(i, n){
                $("#updateSubMenu"+n).prop("checked",true);
            });
        }



        $('#accordion-style').on('click', function(ev){
            var target = $('input', ev.target);
            var which = parseInt(target.val());
            if(which == 2) {
                $('#accordionUpdate').addClass('accordion-style2');
            }else {
                $('#accordionUpdate').removeClass('accordion-style2');
            }
        });
        $("input[name^='updateParentMenu']").on('click',function(){
            if($(this).prop('checked')){
                $("input[name ='updateSubMenu"+$(this).val()+"']").each(function(){
                    $(this).prop("checked",true);
                })
            }else{
                $("input[name ='updateSubMenu"+$(this).val()+"']").each(function(){
                    $(this).prop("checked",false);
                })
            }
        })
    })
</script>
