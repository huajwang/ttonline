package com.troika.vtools.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class M2MUtil {
	public static Map<String, Object> getMap(Object object) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Field[] field = object.getClass().getDeclaredFields();//获取实体类的所有属性，返回Field数组 
        Map<String, Object> map = new HashMap<String,Object>();
		for(int i=0 ; i<field.length ; i++){     //遍历所有属性
                String name = field[i].getName();    //获取属性的名字
                String nameUp = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便调用get，set方法
                String type = field[i].getGenericType().toString(); 
                if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = object.getClass().getMethod("get"+nameUp);
                    String value = (String) m.invoke(object);    //调用getter方法获取属性值
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("class java.lang.Integer")){    
                    Method m = object.getClass().getMethod("get"+nameUp);
                    Integer value = (Integer) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("class java.lang.Short")){    
                    Method m = object.getClass().getMethod("get"+nameUp);
                    Short value = (Short) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }      
                if(type.equals("class java.lang.Double")){    
                    Method m = object.getClass().getMethod("get"+nameUp);
                    Double value = (Double) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }                 
                if(type.equals("class java.lang.Boolean")){
                    Method m = object.getClass().getMethod("get"+nameUp);   
                    Boolean value = (Boolean) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("class java.util.Date")){
                    Method m = object.getClass().getMethod("get"+nameUp);                   
                    Date value = (Date) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("class java.math.BigDecimal")){
                    Method m = object.getClass().getMethod("get"+nameUp);                   
                    BigDecimal value = (BigDecimal) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("class java.sql.Timestamp")){
                    Method m = object.getClass().getMethod("get"+nameUp);                   
                    Timestamp value = (Timestamp) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("class java.lang.Long")){
                    Method m = object.getClass().getMethod("get"+nameUp);                   
                    Long value = (Long) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("class java.lang.Float")){
                    Method m = object.getClass().getMethod("get"+nameUp);                   
                    Float value = (Float) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("long")&&nameUp.equals("serialVersionUID")){
                    Method m = object.getClass().getMethod("get"+nameUp);                   
                    long value = (long) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("int")){
                    Method m = object.getClass().getMethod("get"+nameUp);                   
                    int value = (int) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
                if(type.equals("char")){
                    Method m = object.getClass().getMethod("get"+nameUp);                   
                    char value = (char) m.invoke(object);
                    //if(value != null)
                    	map.put(name, value);
                }
            }//获取属性的类型
			return map;
        }
}
