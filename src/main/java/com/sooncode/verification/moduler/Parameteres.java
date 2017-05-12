package com.sooncode.verification.moduler;

import java.util.ArrayList;
import java.util.List;

import com.sooncode.verification.enumes.DataType;


public class Parameteres{
	
	private List<Parameter>   pes = new ArrayList<>();
	
	
	public Parameteres add(Parameter parameter){
		pes.add(parameter);
		return this;
	}
	/**
	 * 添加参数
	 * @param key 参数名称
	 * @param type 测试数据类型
	 * @param maxLength 最大长度
	 * @param values 参数值域
	 */
	public Parameteres add(String key,String type,Integer maxLength, Object[] values){
		/*Parameter p = new Parameter(key, type, maxLength, values);
		pes.add(p);*/
		return this;
	}
	/**
	 * 添加参数
	 * @param key 参数名称
	 * @param type 测试数据类型 枚举
	 * @param maxLength 最大长度
	 * @param values 参数值域
	 */
	public Parameteres add(String key,DataType type,Integer maxLength, Object[] values){
		/*Parameter p = new Parameter(key, type, maxLength, values);
		pes.add(p);*/
		return this;
	}
	public List<Parameter> getPes() {
		return pes;
	}

}
 


