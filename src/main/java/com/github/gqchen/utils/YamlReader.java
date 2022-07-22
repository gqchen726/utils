package com.github.gqchen.utils;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * yaml文件解析
 *  半成品：缺陷-叶子节点key重复时，value会被覆盖
 */
public class YamlReader {
    // 存放yaml读取的参数
    private Map<Object,Object> properties;
    // 单例模式
    public static final YamlReader yamlReader = new YamlReader();
    private static Yaml yaml;
    private Map<Object, Object> result;
    // 计数器
    private int counter;
    // 根节点和子节点的字符串形式
    private String root;
    private String lastKey;
    private Logger logger = Logger.getLogger(YamlReader.class);

    private YamlReader() {
    }

    static {
            yaml = new Yaml();
    }
    {
        result = new LinkedHashMap<>();
    }
    /**
     * 装载yaml文件，并简单解析
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public Map<Object,Object> getProperties(String fileName) throws FileNotFoundException {
            properties = (Map<Object,Object>)yaml.load(new FileReader(fileName));
            return properties;
    }

    /**
     * 将Object类型的对象判断转换为Map集合
     * @param properties
     * @return
     */
    public Map<Object,Object> caseMapper(Object properties) {
        Map<Object,Object> map;
        if (properties instanceof Map) {
             map = (Map<Object, Object>)properties;
        } else {
            return null;
        }
        return map;
    }

    /**
     * 获取yaml文件的所有的叶子节点的参数
     * @param map
     * @return
     */
    public Map<Object,Object> getvalues(Map map) {

        if (map != null) {
            for (Object o : map.keySet()) {
                String key = o.toString();
                Object value = map.get(o.toString());
//                System.out.println(key+":"+value);
                final Map<Object, Object> temp = this.caseMapper(map.get(o.toString()));
                if (temp == null) {
                    result.put(key,value);
                }
                if (temp != null) {
                    this.getvalues(temp);
                }
//                System.out.println("result:\t"+result);
            }
        }

        return result;
    }

    /**
     * 获取yaml文件的所有的叶子节点的参数(修订版)
     *  添加节点索引,例：url -> baidu.api.url
     * @param map
     * @return
     */
    public Map<Object,Object> getvaluesG(Map map,StringBuilder keyB) {

        if (keyB == null) {
            keyB = new StringBuilder();
        }
        if (map != null) {
            for (Object o : map.keySet()) {
                String key = o.toString();
                Object value = map.get(o.toString());

                final Map<Object, Object> temp = this.caseMapper(map.get(o.toString()));
                if (temp == null) {
                    counter ++;
                    String finalKey;
                    if (root != null) {
                        finalKey = root+keyB.toString()+key;
                    } else {
                        finalKey = keyB.toString()+key;
                    }
                    result.put(finalKey,value);
                    if (counter == 1) {
                        root = keyB.toString();

                        keyB.delete(0,keyB.length());
                    }

                }
                if (temp != null) {
                    if (counter > 1) {
                        keyB.delete(0,keyB.length());
                    }
                    keyB.append(key).append(".");
                    System.out.println("keyB: " + keyB);
                    System.out.println("root: " + root);
                    this.getvaluesG(temp,keyB);
                }



            }
        }

        return result;
    }
    /**
     * 获取yaml文件的所有的叶子节点的参数(最终修订版)
     *  添加节点索引,例：url -> baidu.api.url
     * @param map
     * @return
     */
    public Map<Object,Object> getvaluesG2(Object map,StringBuilder keyB,boolean isInitialization) {
        if (isInitialization) {
            // 初始化计数器
            counter = 0;
        }
        final Map<Object, Object> caseMapper = caseMapper(map);
        Map<Object, Object> temp = null;
        if (keyB == null) {
            keyB = new StringBuilder();
        }
        if (counter == 0) {
            root = keyB.toString();

            keyB.delete(0,keyB.length());
        }
        counter ++;
        if (map != null) {
            for (Object o : caseMapper.keySet()) {
                String key = o.toString();
                Object value = caseMapper.get(o.toString());

                temp = this.caseMapper(caseMapper.get(o.toString()));
                if (temp == null) {

                    String finalKey;
                    finalKey = root+keyB.toString()+key;
//                    if (root != null) {

//                    } else {
//                        finalKey = keyB.toString()+key;
//                    }
                    result.put(finalKey,value);


                }
                if (temp != null) {
//                    if (counter > 1) {
//                        keyB.delete((keyB.length()-(key.length()+1)),keyB.length());
//                    }

                    keyB.append(key).append(".");
                    lastKey = key;

                    /*System.out.println("keyB: " + keyB);
                    System.out.println("root: " + root);*/
                    this.getvaluesG2(temp,keyB,false);
                }


                /*System.out.println("lastKey: "+lastKey);
                System.out.println("-------------------");*/
            }

            final Map<Object, Object> lastMap = this.caseMapper(caseMapper.get(lastKey));
//            if (temp == null && lastMap != null) {
//
//            }
            if (keyB.length() >= (lastKey.length()+1)) {
                keyB.delete((keyB.length()-(lastKey.length()+1)),keyB.length());
            }
        }

        return result;
    }

    /**
     * 快速获取结果
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public Map<Object,Object> getResult(String filePath) throws FileNotFoundException{
        return yamlReader.getvalues(yamlReader.getProperties(filePath));
    }
    /**
     * 快速获取结果（改）
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public Map<Object,Object> getResultG(String filePath) throws FileNotFoundException{
        return yamlReader.getvaluesG(yamlReader.getProperties(filePath),null);
    }
    /**
     * 快速获取结果（最终版）
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public Map<Object,Object> getResultG2(String filePath) throws FileNotFoundException{
        Map<Object, Object> result = null;
        final Map<Object, Object> properties = yamlReader.getProperties(filePath);
        for(Object obj : properties.keySet()) {
            result = yamlReader.getvaluesG2(properties.get(obj),new StringBuilder(obj.toString()).append("."),true);

        }
        logger.debug(result);
        return result;
    }
}