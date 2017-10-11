package com.mjing.reflection.classLoader;
/**
 * 
 * @author jing.ming
 *第一个输出的是 ClassLoaderTree类的类加载器，即系统类加载器。它是 sun.misc.Launcher$AppClassLoader类的实例；
 *第二个输出的是扩展类加载器，是 sun.misc.Launcher$ExtClassLoader类的实例。
 *需要注意的是这里并没有输出引导类加载器，这是由于有些 JDK 的实现对于父类加载器是引导类加载器的情况，getParent()方法返回 null。
 */
public class ClassLoaderTest {

	public static void main(String[] args) {
		try {  
            ClassLoader loader = ClassLoaderTest.class.getClassLoader();  //获得ClassLoaderTest这个类的类加载器 
            while(loader != null) { 
                System.out.println(loader); 
                loader = loader.getParent();    //获得父加载器的引用 
            } 
            System.out.println(loader);
              
  
          /*  String rootUrl = "http://localhost:8080/httpweb/classes";  
            NetworkClassLoader networkClassLoader = new NetworkClassLoader(rootUrl);  
            String classname = "org.classloader.simple.NetClassLoaderTest";  
            Class clazz = networkClassLoader.loadClass(classname);  
            System.out.println(clazz.getClassLoader());  */
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}

