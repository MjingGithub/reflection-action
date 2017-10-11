package com.mjing.reflection.dynamicProxy;
/**
 * 模拟spring AOP的一个动态代理的demo
 * @author jing.ming
 *
 */
public class ProxyMainDemo {

	public static void main(String[] args) {
		IVehical car = new Car() ;
		VehicalProxy proxy = new VehicalProxy(car) ;
		
		IVehical proxyObj =  proxy.createProxy() ;
		proxyObj.run(); 
	}

}
