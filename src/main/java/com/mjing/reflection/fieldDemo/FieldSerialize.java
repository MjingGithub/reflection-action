package com.mjing.reflection.fieldDemo;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 * 
 * @author jing.ming
 *
 */
public class FieldSerialize {
	/**
	 * 通过一个obj获取其class,下面所有的字段,包括父类的,去除静态的字段不序列化
	 * @param obj
	 * @return
	 */
	public Field[] getDeclaredFileds(Object obj){
		List<Field> accumFiledList = new ArrayList<Field>() ;
		Class clz = obj.getClass() ;
		while(clz!=null){
			Field[] f = clz.getDeclaredFields() ;
			for(Field s:f){
				//去除静态static的字段不序列化
				if(!Modifier.isStatic(s.getModifiers())){
					accumFiledList.add(s) ;
				}
				/*if (!Modifier.isPublic(s.getModifiers())) {
					s.setAccessible(true);
				}
				Object value = s.get(obj);*/
			}
			clz = clz.getSuperclass() ;
		}
		Field[] accumField = accumFiledList.toArray(new Field[accumFiledList.size()]) ;
		//Array.getLength(accumField) ;
		return accumField ;
	}
	
	private Element serializeVariable(Class fieldtype,Object child,Document target,Map table) throws IllegalArgumentException, IllegalAccessException{
		if(child==null){
			return new Element("null") ;
		}else if(!fieldtype.isPrimitive()){
			Element reference = new Element("reference") ;
			if(table.containsKey(child)){
				reference.setText(table.get(child).toString());
			}else{
				reference.setText(Integer.toString(table.size()));
				serializeHelper(child, target, table) ;
			}
			return reference ;
		}else{
			Element value = new Element("value") ;
			value.setText(child.toString());
			return value ;
		}
	}
	
	private  Document serializeHelper(Object source,Document target,Map table) throws IllegalArgumentException, IllegalAccessException{
		String id=Integer.toString(table.size()) ;
		table.put(source, id) ;
		Class sourceClass = source.getClass() ;
		Element el = new Element("object") ;
		el.setAttribute("class", sourceClass.getName());
		el.setAttribute("id", id);
		target.getRootElement().addContent(el) ;
		
		if(!sourceClass.isArray()){
			Field[] fields= getDeclaredFileds(source) ;
			for(Field f:fields){
				if (!Modifier.isPublic(f.getModifiers())) {
					f.setAccessible(true);
				}
				Element fel = new Element("field") ;
				fel.setAttribute("name",f.getName());
				Class delClass = f.getDeclaringClass() ;
				fel.setAttribute("declaringClass", delClass.getName());
				Class ctype = f.getType() ;
				Object child = f.get(source);
				if (Modifier.isTransient(f.getModifiers()) ){
					child = null;
				}
				fel.addContent(serializeVariable(ctype, child,target, table));
				el.addContent(fel) ;
			}
		}else{
			Class componentType = sourceClass.getComponentType() ;
			int length = Array.getLength(source);
			el.setAttribute( "length", Integer.toString(length) );
			for (int i=0; i<length; i++) {
				el.addContent( serializeVariable( componentType,Array.get(source,i),target,table ) );
			}
		}
		return target ;
	}
	
	public Document serializeObject(Object source) throws Exception {
		return serializeHelper(source, new Document(new Element("serialized")), new IdentityHashMap());
	}

	public static void main(String[] args) throws Exception {
		Person p = new Person(1l,"Lily",12) ;
		
		System.out.println(new FieldSerialize().serializeObject(p)) ;
		//System.out.print( Modifier.toString( Member.class.getModifiers() ) );//public abstract interface
		//Modifier.isStatic( field.getModifiers() );
	}
	

}
