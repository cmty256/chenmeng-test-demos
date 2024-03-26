package com.chenmeng.common.utils;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.SynthesizingMethodParameter;

import java.lang.reflect.Method;

/**
 * Class工具类
 *
 * @author cmty256
 */
public class ClassUtil {

    /**
     * 用于获取方法参数名的参数名称发现器对象
     */
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 获取方法指定索引位置的参数对象
     *
     * @param method         方法对象
     * @param parameterIndex 参数索引位置
     * @return MethodParameter 对象
     */
    public static MethodParameter getMethodParameter(Method method, int parameterIndex) {
        // 创建一个 MethodParameter 对象
        MethodParameter methodParameter = new SynthesizingMethodParameter(method, parameterIndex);
        // 初始化参数名称发现器
        methodParameter.initParameterNameDiscovery(PARAMETER_NAME_DISCOVERER);
        // 返回 MethodParameter 对象
        return methodParameter;
    }
}
