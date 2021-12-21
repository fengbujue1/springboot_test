package com.zyj.springboot_test.test.xml.converModel.trans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class ConvertMapBean {
    
    /**
     * 将一个 Map 对象转化为一个 JavaBean(暂时,对象中的属性类型可支持Boolean,String,Integer,Double,BigDecimal)
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException
     *             如果分析类属性失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InstantiationException
     *             如果实例化 JavaBean 失败
     * @throws InvocationTargetException
     *             如果调用属性的 setter 方法失败
     */
    public static Object convertMapToBean(Object obj, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
    	Class type = obj.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
      
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
           
            if (map.containsKey(propertyName)) {
          //  	System.out.println(propertyName+"类型为:"+descriptor.getPropertyType()); 
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
            	Object value = map.get(propertyName);
            	if(value==null){
            		continue;
            	}
            	
            	if("class java.lang.Integer".equals(descriptor.getPropertyType().toString())){
                	try {
                		if("".equals(value))
						{
							value = null;
						}else
						{
							String v = value.toString();
							value = Integer.parseInt(v);
						}
					} catch (Exception e) {
						throw new RuntimeException("转换对象时Map中的:"+propertyName+" 不能转换成Integer!", e);
					}
                }else if("class java.lang.Double".equals(descriptor.getPropertyType().toString())){
                	try {
                		if("".equals(value))
						{
							value = null;
						}else
						{
							String v = value.toString();
							value = Double.parseDouble(v);
						}
					} catch (Exception e) {
						throw new RuntimeException("转换对象时Map中的:"+propertyName+" 不能转换成Double!", e);
					}
                }else if("class java.math.BigDecimal".equals(descriptor.getPropertyType().toString())){
                	try {
                		if("".equals(value))
						{
							value = null;
						}else
						{
	                		Double dv = Double.parseDouble(value.toString());
	                		DecimalFormat a = new DecimalFormat("#.##");
	                		String frmStr = a.format(dv);
							value = new BigDecimal(frmStr);
						}
					} catch (Exception e) {
						throw new RuntimeException("转换对象时Map中的:"+propertyName+" 不能转换成BigDecimal!", e);
					}
                }else if("class java.lang.Boolean".equals(descriptor.getPropertyType().toString())){
                	try {
						String v = value.toString();
						value = Boolean.parseBoolean(v);
					} catch (Exception e) {
						throw new RuntimeException("转换对象时Map中的:"+propertyName+" 不能转换成Boolean!", e);
					}
				} else if ("class java.lang.String".equals(descriptor.getPropertyType().toString())) {
					String valueType = value.getClass().toString();
					if (!"class java.lang.String".equals(valueType)) {
						throw new RuntimeException("转换对象时Map中的:" + propertyName + " 不是String类型!");
					}
				}else if ("class java.lang.Long".equals(descriptor.getPropertyType().toString())) {
					try {
						if("".equals(value))
						{
							value = null;
						}else
						{
							String v = value.toString();
							value = Long.parseLong(v);
						}
					} catch (Exception e) {
						throw new RuntimeException("转换对象时Map中的:" + propertyName + " 不能转换成Long类型!", e);
					}
				}
                
                Object[] args = new Object[1];
                args[0] = value;

				try {
					descriptor.getWriteMethod().invoke(obj, args);
				} catch (Exception e) {
					System.out.println("propertyName:" + propertyName + "value:" + value);
					throw new RuntimeException(e);
				}
            }
        }
        return obj;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBeanToMap(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
}

