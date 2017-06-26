package com.sooncode.verification.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
import com.sooncode.verification.moduler.Array;
import com.sooncode.verification.moduler.Method;
import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 接口 参数验证 服务
 * 
 * @author pc
 *
 */
public class VerificationService {

	public static final String ATTRIBUTE_KEY = "JSON";

	/**
	 * 验证接口
	 * 
	 * @param request
	 * @param interfac
	 * @return
	 */
	public static VerificationResult verificationInterface(String methodName ,  String parameter4json , Method interfac) {

		VerificationResult vr = new VerificationResult();
		if (!methodName.equals(interfac.getMethod())) {
			vr.setIsPass(false);
			vr.setReason("请求方式错误");
			return vr;
		}
		vr = verificationJson(parameter4json, interfac);
		return vr;
	}

	/**
	 * 验证JSON格式的参数
	 * 
	 * @param json
	 * @param interfac
	 * @return
	 */
	private static VerificationResult verificationJson(String json, Method interfac) {
		VerificationResult vr = new VerificationResult();
		List<Parameter> parameters = interfac.getParameters();
		Map<String, String[]> map = new HashMap<String, String[]>();
		// ------------------------------------
		JSONObject jsonRoot = new JSONObject();
		try {
			jsonRoot = JSONObject.fromObject(json);
		} catch (Exception e) {
			vr.setIsPass(false);
			vr.setReason("JSON数据格式异常");
			return vr;
		}

		// -----------------验证参数有无-------------------
		if (parameters != null) {
			for (Parameter p : parameters) {
				String key = p.getKey();
				if(p.getMust() == null || p.getMust() == true){
					if (jsonRoot.get(key) == null  ) {
						vr.setIsPass(false);
						vr.setReason("缺少[" + key + "]参数");
						return vr;
					}
				}
				Object val = jsonRoot.get(key);
				if(val != null){
					map.put(key, new String[] { val.toString() });
					
				}
				 
			}
		}

		vr = verificationParameter(parameters, map);
		if (vr.getIsPass() == false) {
			return vr;
		}

		// 验证JSON 数组
		if (interfac.getArrays() != null) {
			for (Array a : interfac.getArrays()) {
				vr = verificationArray(json, a);
				if (vr.getIsPass() == false) {
					return vr;
				}

			}
		}

		vr.setIsPass(true);
		vr.setReason("参数正常");
		return vr;

	}

	/**
	 * 验证参数
	 * 
	 * @param request
	 * @param interfac
	 * @return
	 */
	private static VerificationResult verificationParameter(List<Parameter> parameters, Map<String, String[]> map) {
		VerificationResult vr = new VerificationResult();
		if (parameters == null) {
			vr.setIsPass(true);
			vr.setReason("参数正常");
			return vr;
		}
		for (Parameter p : parameters) {
			String key = p.getKey();
			String type = p.getType();
			//Boolean isNeed = p.getMust();//
			Integer maxLength = p.getMaxLength();
			String[] values = map.get(key);
			String value;
		   if( p.getMust() != null && p.getMust()== false && values == null){
				continue;
			}
			
			if (values != null) {
				value = values[0];
			} else {
				value = null;
			}

			if ( values == null) {

				vr.setIsPass(false);
				vr.setReason("缺少[" + key + "]参数");
				return vr;
			}

			if (values != null  && value != null  && maxLength < value.length()) {
				vr.setIsPass(false);
				vr.setReason("[" + key + "]值溢出");
				return vr;
			}

			vr = verification(key, value, type);

			if (vr.getIsPass() == false) {
				return vr;
			}

			vr = verificationValues(key, value, p.getEnumeration());

			if (vr.getIsPass() == false) {
				return vr;
			}

		}

		vr.setIsPass(true);
		vr.setReason("参数正常");
		return vr;

	}

	/**
	 * 验证 参数值的类型
	 * 
	 * @param value
	 *            参数值
	 * @param type
	 *            类型
	 * @return
	 */
	private static VerificationResult verification(String key, String value, String type) {

		VerificationResult vr = new VerificationResult();

		Boolean bool = RegexService.verification(value, type);
		vr.setIsPass(bool);
		if (bool) {
			vr.setReason("参数正常");
		} else {
			vr.setReason("[" + key + "]:数据类型错误");
		}
		return vr;
	}

	/**
	 * 验证参数的 值域
	 * 
	 * @param key
	 *            参数名称
	 * @param value
	 *            参数值
	 * @param values
	 *            参数值域
	 * @return
	 */
	private static VerificationResult verificationValues(String key, String value, String enumeration) {
		VerificationResult vr = new VerificationResult();
		if(enumeration==null) {
			vr.setIsPass(true);
			vr.setReason("参数正常");
			return vr;
		}
		String[] strs = enumeration.split(";");
		List<String> values =  Arrays.asList(strs);
		if (values == null||values.size()==0) {
			vr.setIsPass(true);
			vr.setReason("参数正常");
			return vr;
		}

		boolean b = false;
		for (Object string : values) {
			if (string.equals(value)) {
				b = true;
				break;
			}
		}
		if (b) {
			vr.setIsPass(true);
			vr.setReason("参数正常");
		} else {
			vr.setIsPass(false);
			vr.setReason("[" + key + "]值错误");
		}

		return vr;

	}

	/**
	 * 验证 JSON格式参数 中的数组
	 * 
	 * @param json
	 * @param array
	 * @return
	 */
	private static VerificationResult verificationArray(String json, Array array) {
		JSONObject jsonRoot = JSONObject.fromObject(json);
		
		Object arrayObject =  jsonRoot.get(array.getKey());
		JSONArray jsonArray =  JSONArray.fromObject(arrayObject);// jsonRoot.get(array.getKey());
		VerificationResult vr = new VerificationResult();

		if (jsonArray == null) {
			vr.setIsPass(false);
			vr.setReason("缺少[" + array.getKey() + "]参数");
			return vr;
		}

		List<Parameter> parameters = array.getParameters();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			Map<String, String[]> map = new HashMap<>();
			for (Parameter p : parameters) {
				String key = p.getKey();
				String value = "";
				try {
					value = obj.getString(key);
				} catch (Exception e) {
					vr.setIsPass(false);
					vr.setReason("缺少[" + key + "]参数");
					return vr;
				}

				map.put(key, new String[] { value });
			}

			vr = verificationParameter(parameters, map);
			if (vr.getIsPass() == false) {
				return vr;
			}

		}

		vr.setIsPass(true);
		vr.setReason("参数正常");
		return vr;

	}

}
