package com.chenmeng.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 转换工具类
 *
 * @author chenmeng
 */
public class ConvertUtil {

    /**
     * 将DO对象集合转换为VO对象集合
     *
     * @param doList DO对象集合
     * @param mapper 函数式接口, 示例: Function<EntDO, EntRespVO> mapper = EntRespVO::new;
     *
     * @return VO对象集合
     * @param <D> DO对象
     * @param <V> VO对象
     */
    public static <D, V> List<V> convertToRespVOList(List<D> doList, Function<D, V> mapper) {
        return doList.stream()
                .map(mapper)
                .collect(Collectors.toCollection(() -> new ArrayList<>(doList.size())));
    }
}
