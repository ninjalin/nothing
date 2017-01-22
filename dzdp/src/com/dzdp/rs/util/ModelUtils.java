package com.dzdp.rs.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ModelUtils {
	
	public static void testReflect(Object model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Field[] field = model.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组  
        for(int j=0 ; j<field.length ; j++){     //遍历所有属性
                String name = field[j].getName();    //获取属性的名字
                //System.out.println("attribute name:"+name);     
                name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString();    //获取属性的类型
                if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get"+name);
                    Method ms = model.getClass().getMethod("set"+name, String.class);
                    String value = (String) m.invoke(model);    //调用getter方法获取属性值
                   /* if(value != null){
                        System.out.println("attribute value:"+value);
                    }else{
                    	 ms.setAccessible(true);//因为写成private 所以这里必须设置
                    	 ms.invoke(model,"");
                    }*/
                    if(value == null){
                    	ms.setAccessible(true);//因为写成private 所以这里必须设置
                    	ms.invoke(model,"");
                    }
                }
                
                if(type.equals("class java.lang.Double")){     
                    Method m = model.getClass().getMethod("get"+name);
                    Method ms = model.getClass().getMethod("set"+name, Double.class);
                    Double value = (Double) m.invoke(model);
                    if(value == null){                    
                    	ms.setAccessible(true);//因为写成private 所以这里必须设置
                    	ms.invoke(model,0.0);
                    }
                }  
                /*if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get"+name);
                    String value = (String) m.invoke(model);    //调用getter方法获取属性值
                    if(value != null){

                        System.out.println("attribute value:"+value);
                    }
                }
                if(type.equals("class java.lang.Integer")){     
                    Method m = model.getClass().getMethod("get"+name);
                    Integer value = (Integer) m.invoke(model);
                    if(value != null){
                        System.out.println("attribute value:"+value);
                    }
                }
                if(type.equals("class java.lang.Short")){     
                    Method m = model.getClass().getMethod("get"+name);
                    Short value = (Short) m.invoke(model);
                    if(value != null){
                        System.out.println("attribute value:"+value);                    }
                }       
                if(type.equals("class java.lang.Double")){     
                    Method m = model.getClass().getMethod("get"+name);
                    Double value = (Double) m.invoke(model);
                    if(value != null){                    
                        System.out.println("attribute value:"+value);  
                    }
                }                  
                if(type.equals("class java.lang.Boolean")){
                    Method m = model.getClass().getMethod("get"+name);    
                    Boolean value = (Boolean) m.invoke(model);
                    if(value != null){                      
                        System.out.println("attribute value:"+value);
                    }
                }
                if(type.equals("class java.util.Date")){
                    Method m = model.getClass().getMethod("get"+name);                    
                    Date value = (Date) m.invoke(model);
                    if(value != null){
                        System.out.println("attribute value:"+value.toLocaleString());
                    }
                } */               
            }
    }

}
