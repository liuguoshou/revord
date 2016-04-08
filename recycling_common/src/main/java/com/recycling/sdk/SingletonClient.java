package com.recycling.sdk;


import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class SingletonClient {
	private static Client client=null;
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
		ResourceBundle bundle=PropertyResourceBundle.getBundle("config.emsms_config");
		if(client==null){
			try {
				client=new Client(bundle.getString("emsms_softwareSerialNo"),bundle.getString("emsms_key"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	
}
