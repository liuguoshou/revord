
//
$(function(){
	
	//select
	$(".catelog h2").click(function(){
		if($(this).children("a").hasClass("shou")){
			$(this).children("a").removeClass("shou").addClass("zhan");
			$(this).siblings(".catexx").show();
		}else{
			$(this).children("a").removeClass("zhan").addClass("shou");
			$(this).siblings(".catexx").hide();
		}
	})
	$(".c_th dl").click(function(){
		if($(this).find(".ab").hasClass("shou")){
			$(this).find(".ab").removeClass("shou").addClass("zhan");
			$(this).next(".cateul").show();
		}else{
			$(this).find(".ab").removeClass("zhan").addClass("shou");
			$(this).next(".cateul").hide();
		}
	})
	
	//隔行变色
	$(".jful li:odd,.jfdl dl:odd,.hsdl dl:odd").addClass("bj1");
	
	//
	$(".catesh dt").click(function(){
		$(this).parent().sibling(".cateul").toggle();	
	})
	

	//计算高度
	$(".warp").css("height",$(window).height()-60);
		   
})


$(document).ready(function(){
$("#flip").click(function(){
    $(".mybag").slideToggle("slow");
  });
});


//select
$(function(){
		
	$(".w_select").click(function(event){
		$(this).children("div").slideUp(100);
		
		$(this).children().find("p").click(function(){
			var qqq=$(this).children("a").text()
			$(this).parent().siblings().html(qqq)
		})		

		if($(this).children("div").is(":hidden")){
			$(this).children("div").slideDown(100).parent();
			$(this).addClass("w_selectd");
		}
		event.stopPropagation();
	})
	$(document).click(function(){
		$(".w_select").children("div").slideUp(100);

	})
	
})
var initselectclick=function(){
	$(".w_select").unbind();
	$(".w_select").click(function(event){
		$(this).children("div").slideUp(100);
		
		$(this).children().find("p").click(function(){
			//alert("aaa")	
			var qqq=$(this).children("a").text()
			$(this).parent().siblings().html(qqq)
			//alert(qqq)
		})		

		if($(this).children("div").is(":hidden")){
			$(this).children("div").slideDown(100).parent();
			$(this).addClass("w_selectd");
		}
		event.stopPropagation();
	}); 
	$(document).click(function(){
		$(".w_select").children("div").slideUp(100);
	})  	
};


