package com.mjing.reflection.basics;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author jing.ming
 * 反射一般用法遵循的步骤:
 * 1.examine the program for its structure or data .
 * 2.Make decisions using the results of the examination .
 * 3.Change the behavior , structure ,or data of the program based upon the decisions .
 */
public class SetObjectColor {

	
	public void setObjectColor(Object obj,Color color){
		
		Class cls = obj.getClass() ;
		try {
			Method method = cls.getMethod("setColor", new Class[]{Color.class}) ;
			method.invoke(obj, new Object[]{color}) ;//如果方法是static的,第一个调用方法的参数可以是null,第二个参数也可以是空数组或者null
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//如果有方法是从父类继承的,protected申明的,上面的方法就会找不到.这时候可以使用下面的方法.
	public static Method getSupportedMethod(Class cls,String name,Class[] paramTypes) throws NoSuchMethodException{
		if(cls==null){
			throw new NoSuchMethodException() ;
		}
		try{
			return cls.getDeclaredMethod(name, paramTypes) ;
		}catch(NoSuchMethodException e){
			return getSupportedMethod(cls.getSuperclass(), name, paramTypes) ;
		}
	}
	
	public static void setColor(Object obj,Color color){
		Class cls = obj.getClass() ;
		try{
			Method method = getSupportedMethod(cls, "setColor", new Class[]{Color.class}) ;
		    method.invoke(obj, new Object[]{color}) ;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Class.class.isInstance(Class.class);//==>true
		Class vlz = int[][].class.getComponentType() ;
		System.out.println(vlz.getName());//[I
		System.out.println(int[].class.getComponentType().getName());//int
		System.out.println(int.class.getComponentType());//null
	}
}
