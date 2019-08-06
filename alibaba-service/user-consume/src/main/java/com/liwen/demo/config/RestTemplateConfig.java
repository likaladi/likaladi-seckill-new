package com.liwen.demo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
//    @Bean
////    @LoadBalanced
//    public RestTemplate restTemplate() {
//        // 这次我们使用了OkHttp客户端,只需要注入工厂即可
//        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
//    }

    /**
     * 手动测 模拟客户端负载均衡
     * @param simpleClientHttpRequestFactory
     * @return
     */
//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory simpleClientHttpRequestFactory){
//        return new RestTemplate(simpleClientHttpRequestFactory);
//    }

    /**
     * 使用ribbon测试负载均衡；注入@LoadBalanced就可以使用
     * @param simpleClientHttpRequestFactory
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(ClientHttpRequestFactory simpleClientHttpRequestFactory){
        return new RestTemplate(simpleClientHttpRequestFactory);
    }


    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        return factory;
    }


}
