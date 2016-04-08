<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<form id="role_add_dialog_form" action="<%=contextPath%>/admin/saveSystemRole.do" class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label" for="role_name"><span style="color: red">*</span>角色名称:</label>
        <div class="controls">
            <input id="role_name" name="role_name" type="text" />
        </div>
    </div>
    <div class="control-group">
        <c:forEach items="${adminMenusList}" var="menu" varStatus="m">
            <div id="accordion" class="accordion-style1 panel-group">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <input name="parentMenu${menu.menuId}" id="parentMenu${menu.menuId}" value="${menu.menuId}" type="checkbox" >
                            <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse${m.count}" aria-expanded="false">
                                <i class="ace-icon fa fa-angle-right bigger-110" data-icon-hide="ace-icon fa fa-angle-down" data-icon-show="ace-icon fa fa-angle-right"></i>
                                &nbsp;&nbsp;&nbsp;&nbsp;${menu.menuName}
                            </a>
                        </h4>
                    </div>

                    <div class="panel-collapse collapse" id="collapse${m.count}" aria-expanded="false">
                        <div class="panel-body">
                            <label class="inline">
                                <c:forEach items="${menu.listSubMenu}" var="subMenu" varStatus="sub">
                                    <input name="subMenu${menu.menuId}" value="${subMenu.menuId}" type="checkbox" >
                                    &nbsp;&nbsp;&nbsp;&nbsp;${subMenu.menuName}
                                </c:forEach>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <input id="parent_menu" name="parent_menu" type="hidden"/>
    <input id="sub_menu" name="sub_menu" type="hidden"/>
    <input type="hidden" name="method" value="save"/>
</form>
<script type="text/javascript">
    $(function(){
        $('#accordion-style').on('click', function(ev){
            var target = $('input', ev.target);
            var which = parseInt(target.val());
            if(which == 2) $('#accordion').addClass('accordion-style2');
            else $('#accordion').removeClass('accordion-style2');
        });
        $("input[name^='parentMenu']").on('click',function(){
            if($(this).prop('checked')){
                $("input[name ='subMenu"+$(this).val()+"']").each(function(){
                    $(this).prop("checked",true);
                })
            }else{
                $("input[name ='subMenu"+$(this).val()+"']").each(function(){
                    $(this).prop("checked",false);
                })
            }
        })
    })
</script>
