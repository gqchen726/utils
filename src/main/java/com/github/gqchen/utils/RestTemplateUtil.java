package com.github.gqchen.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

/**
 * @author: guoqing.chen01@hand-china.com 2021-09-24 10:46
 **/


public class RestTemplateUtil<T,R> {
    @Resource
    private RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);

    public R sendConsumerRequest(String urlStr, HttpMethod httpMethod, Map<String, String> headers, Boolean isSkipUnknownProperties, T requestBody, Class responseBodyType) throws URISyntaxException, IOException {


        ClientHttpRequest request = restTemplate.
                getRequestFactory().
                createRequest(
                        new URI(urlStr),
                        httpMethod
                );
        HttpHeaders requestHeaders = request.getHeaders();
        if (headers != null) {
            //向请求中添加请求头
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                String value = headers.get(key);
                requestHeaders.add(key, value);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        //跳过未知字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, !isSkipUnknownProperties);

        if (requestBody != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectMapper.writeValue(byteArrayOutputStream, requestBody);
            request.getBody().write(byteArrayOutputStream.toByteArray());
        }
        //执行请求
        ClientHttpResponse execute = null;
        try {
            execute = request.execute();
        } catch (Exception ste) {
            logger.error("远程调用请求异常: {}", ste.getMessage());
            return null;
        }
        Object o = objectMapper.readValue(execute.getBody(), responseBodyType);
        return (R)o;
    }
}
