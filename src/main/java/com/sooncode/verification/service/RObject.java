package com.sooncode.verification.service;

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

/**
 * 反射创建的对象
 * 
 * @author pc
 *
 */
public class RObject {

	private static final String NULL_STR = "";
	private static final String CLASS = "class ";
	// private static final String LIST_INTERFACE = "interface java.util.List";
	// private static final String JAVA_TYPES = "Integer Long Short Byte Float
	// Double Character Boolean Date String";
	// private static final String UID = "serialVersionUID";

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
		for (Class<?> tempClass = thisClass.getSuperclass(); tempClass != Object.class; tempClass = tempClass.getSuperclass()) {
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

		boolean haveThisField = this.hasField(fieldName);
		if (haveThisField) {
			PropertyDescriptor pd;
			try {
				pd = new PropertyDescriptor(fieldName, this.object.getClass());
				Method method = pd.getWriteMethod();
				method.invoke(this.object, args);
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}

	/**
	 * 获取Set方法的参数类型
	 * 
	 * @param fieldName
	 * @return
	 */
	public Class<?> getSetMethodParamertType(String fieldName) {
		boolean haveThisField = this.hasField(fieldName);
		if (haveThisField) {
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
		}else{
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
		try {
			Method method = null;
			for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				method = clazz.getDeclaredMethod(methodName, new Object().getClass());
			}
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
	 * 
	 * @param object
	 * @param methodName
	 * @return
	 */
	public static Method getDeclaredMethod(Object object, String methodName) {
		Method method = null;
		for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(methodName);
				return method;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
