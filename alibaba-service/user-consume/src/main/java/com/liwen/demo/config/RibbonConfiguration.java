package com.liwen.demo.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置ribbon负载均衡策略:默认不配置是轮询策略
 */
@Configuration
public class RibbonConfiguration {
    /**
     * ribbon负载均衡规则：随机路由
     * @return
     */
    @Bean
    public IRule ribbonRule(){
        return new RandomRule();
    }

    /**
     * ribbon负载均衡规则：权重
     * 在nacos注册中心页面中配置权重
     * @return
     */
//    @Bean
//    public IRule ribbonRule(){
//        return new NacosWeightedRule();
//    }
}
