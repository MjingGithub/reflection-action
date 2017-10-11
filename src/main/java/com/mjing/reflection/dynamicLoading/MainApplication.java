package com.mjing.reflection.dynamicLoading;

import java.util.Properties;

/**
 * 
 * @author jing.ming
 *
 */
public class MainApplication {
	
	private Properties prop ;
	private CustomerDatabase custDb ;

	//Reading the configuration and loading the facade
	public synchronized CustomerDatabase createDBFacade(){
		if(custDb==null){
			try{
			String dbClassName = prop.getProperty("db.class", "com.wci.app.StubCustomerDB") ;
			Class cls = Class.forName(dbClassName);
			custDb = (CustomerDatabase)cls.newInstance() ;
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return custDb ;
	}
}
