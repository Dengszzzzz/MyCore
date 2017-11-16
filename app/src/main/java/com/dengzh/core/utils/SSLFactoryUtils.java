package com.dengzh.core.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by hpw on 16/11/2.
 * https访问需要
 */

public class SSLFactoryUtils {

    //设置https 访问的时候对所有证书都进行信任
    public static SSLSocketFactory getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final X509TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();


            return sslSocketFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

