package com.yunli.bigdata.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * @author pingchangxin
 * @description 封装使用springframework BeanUtils
 * @date 2021/06/22
 **/
public class BeanBaseUtils {
	private BeanBaseUtils(){}

	public static void copyBean(Object source, Object target) {
		copyBean(source, target, false);
	}

	public static void copyBean(Object source, Object target, boolean forceCast) {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null) {
				Class<?> paramType = writeMethod.getParameterTypes()[0];
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							if (forceCast) {
								if (null == value && paramType.isPrimitive()) {
									continue;	// 忽略null赋值给基本类型
								} else if (null != value){
									value = cast(value, paramType);
								}
							}
							if (ClassUtils.isAssignable(paramType, value == null ? readMethod.getReturnType() : value.getClass())) {
								writeMethod.invoke(target, value);
							}
						} catch (Exception ex) {
							throw new RuntimeException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}

				}
			}
		}

	}

	private static Object cast(Object value, Class<?> returnType) {
		if (value != null) {
			if (value.getClass().isAssignableFrom(BigDecimal.class) && returnType == String.class) {
				return toPlainString((BigDecimal) value);
			} else if (value.getClass().isAssignableFrom(String.class) && returnType == BigDecimal.class) {
				return "".equals(value) ? null : new BigDecimal((String) value);
			}
		}
		return value;
	}

    /**
     * 将BigDecimal转化为字符串，返回不带指数字段的字符串表示形式，并去掉小数点后不必要的0
     * @param num
     * @return
     */
    public static String toPlainString(BigDecimal num) {
        if (null == num) {
            return "";
        }
        String str = num.toPlainString();
        if (str.indexOf(".") > 0) {
            // 正则表达
            str = str.replaceAll("0+$", "");// 去掉后面无用的零
            str = str.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
        }
        return str;
    }
    
	public static <T> T copyBean(Object source, Class<T> targetClass) {
		return copyBean(source, targetClass, false);
	}

	public static <T> T copyBean(Object source, Class<T> targetClass, boolean forceCast) {
		T target = null;
		try {
			target = targetClass.newInstance();
			copyBean(source, target, forceCast);
		} catch (Exception e) {
			throw new RuntimeException("拷贝对象异常", e);
		}
		return target;
	}

	public static <T> List<T> copyList(List<?> dataList, Class<T> clazz) {
		return copyList(dataList, clazz, false);
	}

	public static <T> List<T> copyList(List<?> dataList, Class<T> clazz, boolean forceCast) {
		List<T> newList = new ArrayList<T>();
		try {
            if (dataList == null || dataList.size() == 0) {
                return newList;
            }
			for (Object source : dataList) {
				T target = clazz.newInstance();
				copyBean(source, target, forceCast);
				newList.add(target);
			}
		} catch (Exception e) {
			throw new RuntimeException("bean list 转换异常", e);
		}
		return newList;
	}
}
