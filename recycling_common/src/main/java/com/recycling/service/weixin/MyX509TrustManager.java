package com.recycling.service.weixin;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Title:MyX509TrustManager.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class MyX509TrustManager implements X509TrustManager {  
	  
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
    }  
  
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
    }  
  
    public X509Certificate[] getAcceptedIssuers() {  
        return null;  
    }  
}  

