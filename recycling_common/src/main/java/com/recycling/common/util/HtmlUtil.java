package com.recycling.common.util;

public class HtmlUtil {
	/**     * 转义HTML特殊字符     */   
	public static  String trunhtml(String html){  
		if(html == null)        
			return null;       
		final StringBuilder newhtml = new StringBuilder("");      
		final char[] chararray = html.toCharArray();       
		for(char c : chararray){   
			if(c == '\r')         
				continue;    
			if(c == '&')   
			newhtml.append("&#38;");   
			else if(c == '#')     
			newhtml.append("&#35;");     
			else if(c == '*')      
			newhtml.append("&#42;");   
			else if(c == ':')            
			newhtml.append("&#58;");     
			else if(c == ';')       
		    newhtml.append("&#59;");     
			else if(c == '<')       
		    newhtml.append("&lt;");    
			else if(c == '>')          
		    newhtml.append("&gt;");     
			else if(c == ' ')         
			newhtml.append("&nbsp;");     
			else if(c == '\n')       
		    newhtml.append("<br />");     
			else if(c == '"')         
			newhtml.append("&quot;");        
			else if(c == '\'')       
			newhtml.append("&#39;");       
			else if(c == '/')         
			newhtml.append("&#47;");      
			else if(c == '$')       
			newhtml.append("&#36;");      
			else if(c == '(')         
			newhtml.append("&#40;");       
			else if(c == ')')       
			newhtml.append("&#41;"); 
			else if(c == '{')       
			newhtml.append("&#123;");    
			else if(c == '}')          
			newhtml.append("&#125;");
			else if(c == '*')          
			newhtml.append("&#42;");       
			else if(c == '%')        
			newhtml.append("&#37;"); 
			else if(c == '+')
			newhtml.append("&#43;"); 
			else if(c == '-') 
			newhtml.append("&#45;");
			else if(c == '~') 
			newhtml.append("&#126;");  
			else if(c == '\t')     
			newhtml.append("&nbsp;&nbsp;&nbsp;&nbsp;");  
			else               
			newhtml.append(c);       
			}        
			return newhtml.toString();
		}
	
	public static void main(String[] args) {
		String mainStr = "<html><head><meta charset=\"utf-8\" /><meta name=\"viewport\" ";
		System.out.println(trunhtml(mainStr));
	}
	
}
