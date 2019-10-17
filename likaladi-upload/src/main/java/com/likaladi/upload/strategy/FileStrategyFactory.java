//package com.likaladi.upload.strategy;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class FileStrategyFactory {
//    private final Map<String, Strategy> strategyMap = new ConcurrentHashMap<>();
//
//    /**
//     * 注入所以实现了Strategy接口的Bean
//     * @param strategyMap
//     */
//    @Autowired
//    public FileStrategyFactory(Map<String, Strategy> strategyMap) {
//        this.strategyMap.clear();
//        strategyMap.forEach((k, v)-> this.strategyMap.put(k, v));
//    }
//
//    /**
//     * 计算价格
//     * @param memberLevel   会员等级
//     * @return              价格
//     */
//    public BigDecimal calculatePrice(String memberLevel) {
//        return strategyMap.get(memberLevel).calculatePrice();
//    }
//}
