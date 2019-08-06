package com.liwen.demo.service;

import com.liwen.demo.feignclient.UserFeignrClient;
import com.liwen.demo.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class RibbonService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Eureka,nacos,zookeeper,consul做为服务注册中心，都可以直接注入使用
     */
    @Autowired
    private DiscoveryClient discoveryClient;


    /**
     * 手动模拟客户端负载均衡
     * @param id
     * @return
     */
    public User queryUserById(Long id){

        // 根据服务名称，获取服务实例
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");

        Random random = new Random();

        // 因为只有一个UserService,因此我们直接get(0)获取
        ServiceInstance instance = instances.get(random.nextInt(instances.size()));
        // 获取ip和端口信息
        String baseUrl = "http://"+instance.getHost() + ":" + instance.getPort()+"/user/";

        User user = this.restTemplate.getForObject(baseUrl + id, User.class);

        log.info("=============="+baseUrl+"===================");

        return user;
    }

    /**
     * 使用rabbion负载均衡
     * @param id
     * @return
     */
    public User queryRestTemplateRibbionUserById(Long id){
        // 根据服务名称，获取服务实例
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");

        Random random = new Random();

        // 因为只有一个UserService,因此我们直接get(0)获取
        ServiceInstance instance = instances.get(random.nextInt(instances.size()));

        // 获取ip和端口信息
        String baseUrl = "http://user-service/user/{userId}";

        User user = this.restTemplate.getForObject(baseUrl, User.class, id);

        log.info("=============="+baseUrl+"===================");

        return user;
    }



    @Autowired
    private UserFeignrClient userFeignrClient;
    /**
     * 使用feign测试负载均衡
     * @param id
     * @return
     */
    public User queryFeignRibbionUserById(Long id){
        User user = userFeignrClient.queryById(Integer.parseInt(id+""));
        return user;
    }

}
