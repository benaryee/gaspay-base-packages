package com.gaspay.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
@Slf4j
@Component
public class HttpUtils {


    public static HttpComponentsClientHttpRequestFactory getTrustAllRequestFactory() {
        return getTrustAllRequestFactory(3000, 10000);
    }

    public static HttpComponentsClientHttpRequestFactory getTrustAllRequestFactory(
            Integer connectTimeout, Integer readTimeout) {

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        if (connectTimeout != null) {
            requestFactory.setConnectTimeout(connectTimeout);
        }

        if (readTimeout != null) {
            requestFactory.setReadTimeout(readTimeout);
        }

        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

            HttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();

            requestFactory.setHttpClient((org.apache.hc.client5.http.classic.HttpClient) httpClient);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        return requestFactory;
    }



}
