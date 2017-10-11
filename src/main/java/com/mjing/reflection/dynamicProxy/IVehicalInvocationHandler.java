package com.mjing.reflection.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IVehicalInvocationHandler implements InvocationHandler{
	
	private final IVehical ivehical ;
	
	public IVehicalInvocationHandler(IVehical ivehical){
		this.ivehical = ivehical ;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		System.out.println("---------------before running----------"); 
		Object robj = method.invoke(ivehical, args) ;
		System.out.println("---------------after running----------"); 
		return robj ;
		
	}

}
