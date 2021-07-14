package com.crm.oms.common.utils;

import org.dozer.DozerBeanMapper;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean数据转换工具类.
 *
 * @auther 方洋
 */
public class ConvertUtil {

    private static DozerBeanMapper dozerBeanMapper;

    static {
        dozerBeanMapper = new DozerBeanMapper();
    }

    /**
     * 对象的属性的拷贝.
     *
     * @param source           对象属性
     * @param destinationClass 转换的范型
     * @param <T>              范型
     * @return the T
     */
    public static <T> T convert(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }

        return dozerBeanMapper.map(source, destinationClass);
    }


    /**
     * 数组对象拷贝方法.
     *
     * @param sources          对象属性.
     * @param destinationClass 需要转换的数组.
     * @param <T>              范型
     * @return T
     */
    public static <T> List<T> convertList(List sources, Class<T> destinationClass) {
        List<T> list = new ArrayList<>();
        if (sources == null || sources.size() == 0) {
            return list;
        }

        for (Object source : sources) {
            list.add(dozerBeanMapper.map(source, destinationClass));
        }

        return list;
    }


}
