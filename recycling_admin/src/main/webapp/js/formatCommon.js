// 创建一个闭包    
(function($) {    
 	// 插件的定义
 	//格式化日期对象    
 	//obj日期对象，format格式串
 	$.fn.formatDateTime = function(obj, format) {    
  		var o = {  
			"M+" : obj.getMonth()+1, //month  
			"d+" : obj.getDate(),    //day  
			"h+" : obj.getHours(),   //hour  
			"m+" : obj.getMinutes(), //minute  
			"s+" : obj.getSeconds(), //second  
			"q+" : Math.floor((obj.getMonth()+3)/3), //quarter  
			"S"  : obj.getMilliseconds() //millisecond  
		}  
		
		if(/(y+)/.test(format)) format=format.replace(RegExp.$1,(obj.getFullYear()+"").substr(4 - RegExp.$1.length));  
		
		for(var k in o){
			if(new RegExp("("+ k +")").test(format)){
				format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
			}
		}
  		return format;  
	};
	
	//obj日期串
	$.fn.formatDateString = function(obj) {    
  		var value = obj;
		if(obj && obj.length > 10){
	 		value = obj.substr(0,10);
		}
		return value;
	};
	
	$.fn.formatDateTime = function(obj, format) {    
  		
  		return format;  
	};
	
	
	$.fn.formatBoolean = function(value) {    
  		var s = "";
		if("true" == value){
	 		  s="是"	;
		}else{
		      s="否"	;
		}
		return s;
	};
	
	//value数字字符串，scale小数位数
	$.fn.formatNumber = function(value, scale) {
		var s = null;    
  		if(value && "" != value){
	 		    s = Math.round(parseFloat(value) * Math.pow(10,scale)) / Math.pow(10,scale);
	 		    s = s.toString();
	 		    var dotIndex = s.indexOf(".");
	 		    var dt = scale;
	 		    if(-1 == dotIndex&&scale>0){
	 		    	s += ".";
				}else{
					var sub = s.substring(dotIndex+1);
					dt = scale - sub.length;
				} 
				
				for(var i = 0; i < dt; i++){
						s += "0";
				}		
		}
  		return s;  
	};	
	
	//value数字字符串，scale小数位数
	$.fn.formatNumber2 = function(value, scale) {
		var s = "";    
  		if(value && "" != value){
	 		    s = Math.round(parseFloat(value) * Math.pow(10,scale)) / Math.pow(10,scale);
	 		    s = s.toString();
	 		    var dotIndex = s.indexOf(".");
	 		    var dt = scale;
	 		    if(-1 == dotIndex){
	 		    	s += ".";
				}else{
					var sub = s.substring(dotIndex+1);
					dt = scale - sub.length;
				} 
				
				for(var i = 0; i < dt; i++){
						s += "0";
				}		
		}
  		return s;  
	};	
	
	//截取字符串s中指定起始和结束位置中的子串
	//s 要格式化的字符串
	//start 起始位置
	//stop 结束位置

	$.fn.formatString = function(s,start,stop) {    
  		if(null == s || s.length <= stop - start){
  			return s;
  		}else{
  			return s.substring(start,stop);
  		}
	};
})(jQuery);


var TimeObjectUtil;
/**
 * @title 时间工具类
 * @note 本类一律违规验证返回false
 * @author {boonyachengdu@gmail.com}
 * @date 2013-07-01
 * @formatter "2013-07-01 00:00:00" , "2013-07-01"
 */
TimeObjectUtil = {
    /**
     * 获取当前时间毫秒数
     */
    getCurrentMsTime : function() {
        var myDate = new Date();
        return myDate.getTime();
    },
    /**
     * 毫秒转时间格式
     */
    longMsTimeConvertToDateTime : function(time) {
        var myDate = new Date(time);
        return this.formatterDateTime(myDate);
    },
    /**
     * 时间格式转毫秒
     */
    dateToLongMsTime : function(date) {
        var myDate = new Date(date);
        return myDate.getTime();
    },
    /**
     * 格式化日期（不含时间）
     */
    formatterDate : function(date) {
        var datetime = date.getFullYear()
            + "-"// "年"
            + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
            + (date.getMonth() + 1))
            + "-"// "月"
            + (date.getDate() < 10 ? "0" + date.getDate() : date
                .getDate());
        return datetime;
    },
    /**
     * 格式化日期（含时间"00:00:00"）
     */
    formatterDate2 : function(date) {
        var datetime = date.getFullYear()
            + "-"// "年"
            + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
            + (date.getMonth() + 1))
            + "-"// "月"
            + (date.getDate() < 10 ? "0" + date.getDate() : date
                .getDate()) + " " + "00:00:00";
        return datetime;
    },
    /**
     * 格式化去日期（含时间）
     */
    formatterDateTime : function(date) {
        var datetime = date.getFullYear()
            + "-"// "年"
            + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
            + (date.getMonth() + 1))
            + "-"// "月"
            + (date.getDate() < 10 ? "0" + date.getDate() : date
                .getDate())
            + " "
            + (date.getHours() < 10 ? "0" + date.getHours() : date
                .getHours())
            + ":"
            + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
                .getMinutes())
            + ":"
            + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
                .getSeconds());
        return datetime;
    },
    /**
     * 时间比较{结束时间大于开始时间}
     */
    compareDateEndTimeGTStartTime : function(startTime, endTime) {
        return ((new Date(endTime.replace(/-/g, "/"))) > (new Date(
            startTime.replace(/-/g, "/"))));
    },
    /**
     * 验证开始时间合理性{开始时间不能小于当前时间{X}个月}
     */
    compareRightStartTime : function(month, startTime) {
        var now = formatterDayAndTime(new Date());
        var sms = new Date(startTime.replace(/-/g, "/"));
        var ems = new Date(now.replace(/-/g, "/"));
        var tDayms = month * 30 * 24 * 60 * 60 * 1000;
        var dvalue = ems - sms;
        if (dvalue > tDayms) {
            return false;
        }
        return true;
    },
    /**
     * 验证开始时间合理性{结束时间不能小于当前时间{X}个月}
     */
    compareRightEndTime : function(month, endTime) {
        var now = formatterDayAndTime(new Date());
        var sms = new Date(now.replace(/-/g, "/"));
        var ems = new Date(endTime.replace(/-/g, "/"));
        var tDayms = month * 30 * 24 * 60 * 60 * 1000;
        var dvalue = sms - ems;
        if (dvalue > tDayms) {
            return false;
        }
        return true;
    },
    /**
     * 验证开始时间合理性{结束时间与开始时间的间隔不能大于{X}个月}
     */
    compareEndTimeGTStartTime : function(month, startTime, endTime) {
        var sms = new Date(startTime.replace(/-/g, "/"));
        var ems = new Date(endTime.replace(/-/g, "/"));
        var tDayms = month * 30 * 24 * 60 * 60 * 1000;
        var dvalue = ems - sms;
        if (dvalue > tDayms) {
            return false;
        }
        return true;
    },
    /**
     * 获取最近几天[开始时间和结束时间值,时间往前推算]
     */
    getRecentDaysDateTime : function(day) {
        var daymsTime = day * 24 * 60 * 60 * 1000;
        var yesterDatsmsTime = this.getCurrentMsTime() - daymsTime;
        var startTime = this.longMsTimeConvertToDateTime(yesterDatsmsTime);
        var pastDate = this.formatterDate2(new Date(startTime));
        var nowDate = this.formatterDate2(new Date());
        var obj = {
            startTime : pastDate,
            endTime : nowDate
        };
        return obj;
    },
    /**
     * 获取今天[开始时间和结束时间值]
     */
    getTodayDateTime : function() {
        var daymsTime = 24 * 60 * 60 * 1000;
        var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;
        var currentTime = this.longMsTimeConvertToDateTime(this.getCurrentMsTime());
        var termorrowTime = this.longMsTimeConvertToDateTime(tomorrowDatsmsTime);
        var nowDate = this.formatterDate2(new Date(currentTime));
        var tomorrowDate = this.formatterDate2(new Date(termorrowTime));
        var obj = {
            startTime : nowDate,
            endTime : tomorrowDate
        };
        return obj;
    },
    /**
     * 获取明天[开始时间和结束时间值]
     */
    getTomorrowDateTime : function() {
        var daymsTime = 24 * 60 * 60 * 1000;
        var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;
        var termorrowTime = this.longMsTimeConvertToDateTime(tomorrowDatsmsTime);
        var theDayAfterTomorrowDatsmsTime = this.getCurrentMsTime()+ (2 * daymsTime);
        var theDayAfterTomorrowTime = this.longMsTimeConvertToDateTime(theDayAfterTomorrowDatsmsTime);
        var pastDate = this.formatterDate2(new Date(termorrowTime));
        var nowDate = this.formatterDate2(new Date(theDayAfterTomorrowTime));
        var obj = {
            startTime : pastDate,
            endTime : nowDate
        };
        return obj;
    }
};