package com.mjing.reflection.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class VehicalProxy {

	private IVehical vehicle ;
	public VehicalProxy(IVehical vehicle){
		this.vehicle = vehicle ;
	}
	public IVehical createProxy(){
		final Class<?>[] interfaces = new Class[]{IVehical.class} ;
		InvocationHandler ih = new IVehicalInvocationHandler(vehicle) ;
		return (IVehical)Proxy.newProxyInstance(IVehical.class.getClassLoader(), interfaces, ih) ;
	}
}
