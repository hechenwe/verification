package com.sooncode.verification_apidoc.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * 反射创建的对象
 * 
 * @author pc
 *
 */
public class RObject {
	public static Logger logger = Logger.getLogger("RObject.class");
	private static final String NULL_STR = "";
	private static final String CLASS = "class ";
	 

	/** 被反射代理的对象 */
	private Object object;

	public <T> RObject(T object) {
		this.object = object;
	}

	public RObject(Class<?> clas) {
		try {
			this.object = clas.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RObject(String className) {
		Class<?> clas;
		try {
			clas = Class.forName(className);
			this.object = clas.newInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 获取对象的类名 */
	public String getSimpleName() {
		return this.object.getClass().getSimpleName();
	}

	/** 获取对象的全类名 */
	public String getName() {
		return this.object.getClass().getName();
	}

	/** 获取被反射代理的对象 */
	public <T> T getObject() {
		@SuppressWarnings("unchecked")
		T t = (T) object;
		return t;
	}

	/**
	 * 获取被反射代理对象的属性集
	 * 
	 * @return
	 */
	public List<Field> getFields() {
		List<Field> list = new ArrayList<>();
		Class<?> thisClass = this.object.getClass();
		List<Field> thisFields = Arrays.asList(thisClass.getDeclaredFields());
		list.addAll(thisFields);
		for ( Class<?> tempClass = thisClass.getSuperclass(); tempClass != Object.class; tempClass = tempClass.getSuperclass()) {
			Field[] fields = tempClass.getDeclaredFields();
				for (Field f : fields) {
					int i = f.getModifiers();
					boolean isPrivate = Modifier.isPrivate(i);
					if (isPrivate == false) {
						list.add(f);
					}
				}
		}
		return list;
	}

	/**
	 * 判断属性是否存在
	 * 
	 * @param field
	 * @return
	 */
	public Boolean hasField(String fieldName) {
		if (fieldName == null || fieldName.equals(NULL_STR)) {
			return false;
		}
		
		List<Field> fields = this.getFields();
		for (Field f : fields) {
			if (f.getName().equals(fieldName.trim())) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 获取 list 类型的属性名称
	 * 
	 * @param listClass
	 *            list的数据类型
	 * @return 属性名称
	 */
	public String getListFieldName(Class<?> listClass) {
		List<Field> fields = getFields();
		for (Field f : fields) {
			Class<?> type = f.getType();
			if (type.getName().equals(List.class.getName())) {
				ParameterizedType pt = (ParameterizedType) f.getGenericType();
				String str = pt.getActualTypeArguments()[0].toString(); // 获取List泛型参数类型名称
				str = str.replace(CLASS, NULL_STR).trim();// 全类名
				if (str.equals(listClass.getName())) {
					return f.getName();
				}
			}
		}
		return null;
	}

	/**
	 * 执行对象的SET方法
	 * 
	 * @param fieldName
	 * @param args
	 */
	public void invokeSetMethod(String fieldName, Object... args) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, this.object.getClass());
			Method method = pd.getWriteMethod();
			method.invoke(this.object, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取Set方法的参数类型
	 * 
	 * @param fieldName
	 * @return
	 */
	public Class<?> getSetMethodParamertType(String fieldName) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, this.object.getClass());
			Method method = pd.getWriteMethod();
			Class<?>[] c = method.getParameterTypes();
			return c[0];
		} catch (IntrospectionException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 执行对象的GET方法
	 * 
	 * @param fieldName
	 * @return
	 */

	public <T> T invokeGetMethod(String fieldName) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, this.object.getClass());
			// 获得set方法
			Method method = pd.getReadMethod();
			@SuppressWarnings("unchecked")
			T t = (T) method.invoke(this.object);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/** 获取对象的属性和其对应的值 */
	public Map<String, Object> getFiledAndValue() {
		Map<String, Object> map = new HashMap<>();
		List<Field> fields = this.getFields();
		for (Field field : fields) {
			String name = field.getName().replace("$cglib_prop_", "");
				map.put(name, this.invokeGetMethod(name));
		}
		return map;
	}
 

	/**
	 * 反射执行方法
	 * 
	 * @param methodName
	 *            方法名称
	 * @param args
	 *            方法需要的参数集
	 * @return 方法执行的返回值
	 */

	public <T> T invoke(String methodName, Object... args) {
		Method method =  getDeclaredMethod (methodName);
		try {
			@SuppressWarnings("unchecked")
			T t = (T) method.invoke(object, args);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    /**
     * 获取类的方法
     * @param object
     * @param methodName
     * @return
     */
	public Method getDeclaredMethod(String methodName) {
		List<Method> list =  getDeclaredMethods();
		for (Method method : list) {
			if(method.getName().equals(methodName)){
				return method;
			}
		}
		return null;
	}
	
	/**
	 * 获取类的方法
	 * @return 方法集
	 */
	public  List<Method> getDeclaredMethods() {
		List<Method>  methods = new ArrayList<>();
		for (Class<?> tempClass = this.object.getClass(); tempClass != Object.class; tempClass = tempClass.getSuperclass()) {
			try {
				List<Method> list  = Arrays.asList(tempClass.getDeclaredMethods());
				methods.addAll(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return methods;
	}
	/**
	 * 对象转换 注意 对象中的属性均是基本数据类：Integer Long Short Byte Float Double Character
	 * Boolean Date String
	 * 
	 * @param oldObj
	 *            被转换的对象
	 * @param newObjClass
	 *            新对象的Class
	 * @return 新对象
	 * 
	 */
	public static Object to(Object oldObj, Class<?> newObjClass) {
        if(oldObj==null || newObjClass==null){
        	return null;
        }
		RObject rOldObj = new RObject(oldObj);
		RObject rNewObj = new RObject(newObjClass);

		Map<String, Object> map = rOldObj.getFiledAndValue();

		for (Entry<String, Object> en : map.entrySet()) {
			String key = en.getKey();
			Object val = en.getValue();
			if (rNewObj.hasField(key)) {
				rNewObj.invokeSetMethod(key, val);
			}
		}

		return rNewObj.getObject();

	}

	/**
	 * 批量对象转换
	 * 
	 * @param oldObjes
	 *            被转换的对象集
	 * @param newObjClass
	 *            新对象的Class
	 * @return 新对象集
	 */
	public static List<?> tos(List<?> oldObjes, Class<?> newObjClass) {
		List<Object> list = new ArrayList<Object>();
		  if(oldObjes==null || oldObjes.size()==0 || newObjClass==null){
	        	return new ArrayList<>();
	        }
		for (Object obj : oldObjes) {
			Object newObje = to(obj, newObjClass);
			list.add(newObje);
		}
		return list;
	}

	 
}
