package com.recycling.common.util;

/** 
 * JAVA 返回随机数，并根据概率、比率 
 * @author renli.yu 
 * 
 */  
public class MathRandom  
{  
 /** 
     * 0出现的概率
     */  
 public static double rate0 = 0.311;  
 /** 
     * 1出现的概率
     */  
 public static double rate1 = 0.111;  
 /** 
     * 2出现的概率
     */  
 public static double rate2 = 0.111;  
 /** 
     * 3出现的概率
     */  
 public static double rate3 = 0.111;  
 /** 
     * 4出现的概率 
     */  
 public static double rate4 = 0.111;  
 /** 
     * 5出现的概率
     */  
 public static double rate5 = 0.111;  
 /** 
  * 6出现的概率
  */  
 public static double rate6 = 0.131;
 /** 
  * 7出现的概率
  */  
 public static double rate7 = 0.003; 
  
 /** 
  * Math.random()产生一个double型的随机数，判断一下 
  * 例如0出现的概率为%50，则介于0到0.50中间的返回0 
     * @return int 
     * 
     */  
 public static int percentageRandom()  
 {  
  double randomNumber;  
  randomNumber =Math.random(); 
  System.out.println(randomNumber);
  if (randomNumber >= 0 && randomNumber <= rate0)  
  {  
   return 0;  
  }  
  else if (randomNumber >= rate0 && randomNumber <= rate0 + rate1)  
  {  
   return 1;  
  }  
  else if (randomNumber >= rate0 + rate1  
    && randomNumber <= rate0 + rate1 + rate2)  
  {  
   return 2;  
  }  
  else if (randomNumber >= rate0 + rate1 + rate2  
    && randomNumber <= rate0 + rate1 + rate2 + rate3)  
  {  
   return 3;  
  }  
  else if (randomNumber >= rate0 + rate1 + rate2 + rate3  
    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4)  
  {  
   return 4;  
  }  
  else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4  
    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4  
      + rate5)  
  {  
   return 5;  
  }  
  else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4 + rate5
		    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4  
		      + rate5 + rate6)  
		  {  
		   return 6;  
		  }  
  else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4 + rate5 + rate6
		    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4  
		      + rate5 + rate6 + rate7)  
		  {  
		   return 7;  
		  }  
  return -1;  
 }  
  
 /** 
  * 测试主程序 
     * @param agrs 
 * @throws InterruptedException 
     */  
 public static void main(String[] agrs) throws InterruptedException  
 {
	 int a1=0; int a2=0; int a3=0; int a4=0; int a5=0; int a6=0; int a7=0;int a8 =0;int a9=0;
	 int b = 0;
	 for(int i=0;i<10000;i++){
		 b++;
		 if(MathRandom.percentageRandom()==0){
			 a1++;
		 }
		 if(MathRandom.percentageRandom()==1){
			 a2++;
		 }
		 if(MathRandom.percentageRandom()==2){
			 a3++;
		 }
		 if(MathRandom.percentageRandom()==3){
			 a4++;
		 }
		 if(MathRandom.percentageRandom()==4){
			 a5++;
		 }
		 if(MathRandom.percentageRandom()==5){
			 a6++;
		 }
		 if(MathRandom.percentageRandom()==6){
			 a7++;
		 }
		 if(MathRandom.percentageRandom()==7){
			 a8++;
		 }
		 if(MathRandom.percentageRandom()==-1){
			 a9++;
		 }
		// Thread.sleep(100);
		 
	 }
	 System.out.println("a1="+a1+" a2="+a2+" a3="+a3+" a4="+a4+" a5="+a5+" a6="+a6+" a7="+a7+" a8="+a8+" a9="+a9);
	 System.out.println(b);
	 System.out.println(Math.random()*1000);
 }  
}  


