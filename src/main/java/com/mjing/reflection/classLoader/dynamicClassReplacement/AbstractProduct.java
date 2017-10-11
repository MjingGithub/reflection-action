package com.mjing.reflection.classLoader.dynamicClassReplacement;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jing.ming
 *
 */
public class AbstractProduct implements Product {

	static private ClassLoader cl = null;
	static private String directory = null;
	static private Class implClass;
	static private List instances = new ArrayList();

	public static Product newInstance() throws InstantiationException, IllegalAccessException {
		AbstractProduct obj = (AbstractProduct) implClass.newInstance();
		Product anAProxy = (Product) ProductIH.newInstance(obj);
		instances.add(new WeakReference(anAProxy));//WeakReference弱引用帮助避免内存泄露,及时回收对象
		return anAProxy;
	}

	public static void reload(String dir) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		cl = new SimpleClassLoader(dir);
		implClass = cl.loadClass("ProductImpl");
		if (directory == null) {
			directory = dir;
			return;
		}
		directory = dir;
		List newInstances = new ArrayList();
		Method evolve = implClass.getDeclaredMethod("evolve", new Class[] { Object.class });
		for (int i = 0; i < instances.size(); i++) {
			Proxy x = (Proxy) ((WeakReference) instances.get(i)).get();
			if (x != null) {
				ProductIH aih = (ProductIH) Proxy.getInvocationHandler(x);
				Product oldObject = aih.getTarget();
				Product replacement = (Product) evolve.invoke(null, new Object[] { oldObject });
				aih.setTarget(replacement);
				newInstances.add(new WeakReference(x));
			}
		}
		instances = newInstances;
	}

}
