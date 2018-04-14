package com.anssy.webcore.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HttpClientUtils {

    private static Logger logger = Logger.getLogger(HttpClientUtils.class);


    public static String get(String url, List<NameValuePair> params) {
        CloseableHttpClient hc = HttpClients.createDefault();
        String body = null;
        try {
            HttpGet httpget = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build();//设置请求和传输超时时间
            httpget.setConfig(requestConfig);
            if (params == null) {
                httpget.setURI(new URI(httpget.getURI().toString()));
            } else {
                String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
                httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            }
            HttpResponse httpresponse = hc.execute(httpget);
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity, "UTF-8");
            if (entity != null)
                entity.consumeContent();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String post(String url, List<NameValuePair> params) {
        CloseableHttpClient hc = HttpClients.createDefault();
        String body = null;
        try {
            HttpPost httppost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build();//设置请求和传输超时时间
            httppost.setConfig(requestConfig);
            if (params != null)
                httppost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpresponse = hc.execute(httppost);
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
            if (entity != null)
                entity.consumeContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

}