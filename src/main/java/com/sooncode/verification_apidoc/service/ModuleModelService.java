package com.sooncode.verification_apidoc.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sooncode.verification_apidoc.model.ArrayModel;
import com.sooncode.verification_apidoc.model.InterfacModel;
import com.sooncode.verification_apidoc.model.ModuleModel;
import com.sooncode.verification_apidoc.model.ParameterConstraintModel;
import com.sooncode.verification_apidoc.model.ParameterModel;
 

public class ModuleModelService {

	private static final String CONTROLLER = "controller";
	private static final String CHINESE_ANNOTATION = "chineseAnnotation";
	private static final String METHOD = "method";
	private static final String PARAMETER = "parameter";
	private static final String PARAMETER_RETURN = "parameter-return";
	private static final String ARRAY = "array";
	private static final String REF = "ref";
	private static final String MUST = "must";
	private static final String KEY = "key";
	private static final String TYPE = "type";
	private static final String MAX_LENGTH = "maxLength";
	private static final String MIN_LENGTH = "minLength";
	private static final String URL = "url";
	private static final String EXAMPLE = "example";
	private static final String ENUMERATION = "enumeration";

	private Map<String, ParameterModel> publicParameterModels = new HashMap<>();
	private DomService domService;

	public ModuleModelService(File file) {

		String str = readFile(file);
		InputSource inputSource = new InputSource(new StringReader(str));
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
			domService = new DomService(document);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取模块
	 * 
	 * @return
	 */
	public ModuleModel getModuleModel() {

		Node moduleNode = domService.getNode4Document(CONTROLLER);
		this.initPublicParameterModels(moduleNode);
		String chineseAnnotation = domService.getAttribute(moduleNode, CHINESE_ANNOTATION);
		ModuleModel mm = new ModuleModel();
		mm.setModuleName(chineseAnnotation);
		List<InterfacModel> interfacModels = this.getInterfacModels(moduleNode);
		mm.setInterfacModels(interfacModels);
		return mm;

	}

	/**
	 * 获取接口(方法)
	 * 
	 * @param modulenode
	 * @return
	 */
	private List<InterfacModel> getInterfacModels(Node modulenode) {
		List<InterfacModel> list = new ArrayList<>();
		List<Node> interfacNodes = domService.getChildNodes(modulenode, METHOD);
		for (Node interfacNode : interfacNodes) {
			String chineseAnnotation = domService.getAttribute(interfacNode, CHINESE_ANNOTATION);
			String method = domService.getAttribute(interfacNode, METHOD);
			String url = domService.getAttribute(interfacNode, URL);

			InterfacModel im = new InterfacModel();
			im.setInterfacCode(url);
			im.setInterfacName(chineseAnnotation);
			im.setRequestType(method);
			im.setUrl(url);
			List<ParameterModel> parameterModels = this.getParameterModels4Interfac(interfacNode);
            List<ArrayModel> arrayModels = this.getArrayModels(interfacNode);
            List<ParameterModel> parameterReturModels = this.getParameterReturnModels4Interfac(interfacNode);
			im.setParameterModels(parameterModels);
			im.setArrayModels(arrayModels);
			im.setParameterReturModels(parameterReturModels);
			list.add(im);
		}

		return list;
	}

	
	
	
	
	
	/**
	 * 获取 参数
	 * 
	 * @param interfacNode
	 * @return
	 */
	private List<ParameterModel> getParameterModels4Interfac(Node interfacNode) {
		List<ParameterModel> list = new ArrayList<>();

		List<Node> parameterNodes = domService.getChildNodes(interfacNode, PARAMETER);
		for (Node parameterNode : parameterNodes) {
			String ref = domService.getAttribute(parameterNode, REF);
			String must = domService.getAttribute(parameterNode, MUST);
			must = (must == null) ? "true" : "false";
			ParameterModel pm = new ParameterModel();

			if (ref != null) {

				pm = publicParameterModels.get(ref);
				if (pm != null) {
					pm.setIsMust(must);
					list.add(pm);
				}
			} else {

				String chineseAnnotation = domService.getAttribute(parameterNode, CHINESE_ANNOTATION);
				String key = domService.getAttribute(parameterNode, KEY);
				String type = domService.getAttribute(parameterNode, TYPE);
				String example = domService.getAttribute(parameterNode, EXAMPLE);
				String maxLength = domService.getAttribute(parameterNode, MAX_LENGTH);
				String minLength = domService.getAttribute(parameterNode, MIN_LENGTH);
				String enumeration = domService.getAttribute(parameterNode, ENUMERATION);
				minLength = (minLength == null) ? "1" : minLength;
				pm.setIsMust(must);
				pm.setParameterName(chineseAnnotation);
				pm.setParameterCode(key);
				pm.setParameterDataType(type);
				pm.setMaxLength(Integer.parseInt(maxLength));
				pm.setMinLength(Integer.parseInt(minLength));
				pm.setParameterExample(example);
				pm.setEnumeration(enumeration);
				List<ParameterConstraintModel> parameterConstraintModels = this.getParameterConstraintModels(parameterNode);
				pm.setParameterConstraintModels(parameterConstraintModels);
				list.add(pm);
			}

		}

		return list;

	}
	/**
	 * 获取 参数
	 * 
	 * @param interfacNode
	 * @return
	 */
	private List<ParameterModel> getParameterReturnModels4Interfac(Node interfacNode) {
		List<ParameterModel> list = new ArrayList<>();
		
		List<Node> parameterNodes = domService.getChildNodes(interfacNode, PARAMETER_RETURN);
		for (Node parameterNode : parameterNodes) {
			String ref = domService.getAttribute(parameterNode, REF);
			String must = domService.getAttribute(parameterNode, MUST);
			must = (must == null) ? "true" : "false";
			ParameterModel pm = new ParameterModel();
			
			if (ref != null) {
				
				pm = publicParameterModels.get(ref);
				if (pm != null) {
					pm.setIsMust(must);
					list.add(pm);
				}
			} else {
				
				String chineseAnnotation = domService.getAttribute(parameterNode, CHINESE_ANNOTATION);
				String key = domService.getAttribute(parameterNode, KEY);
				String type = domService.getAttribute(parameterNode, TYPE);
				String example = domService.getAttribute(parameterNode, EXAMPLE);
				String maxLength = domService.getAttribute(parameterNode, MAX_LENGTH);
				String minLength = domService.getAttribute(parameterNode, MIN_LENGTH);
				String enumeration = domService.getAttribute(parameterNode, ENUMERATION);
				minLength = (minLength == null) ? "1" : minLength;
				pm.setIsMust(must);
				pm.setParameterName(chineseAnnotation);
				pm.setParameterCode(key);
				pm.setParameterDataType(type);
				pm.setMaxLength(Integer.parseInt(maxLength));
				pm.setMinLength(Integer.parseInt(minLength));
				pm.setParameterExample(example);
				pm.setEnumeration(enumeration);
				List<ParameterConstraintModel> parameterConstraintModels = this.getParameterConstraintModels(parameterNode);
				pm.setParameterConstraintModels(parameterConstraintModels);
				list.add(pm);
			}
			
		}
		
		return list;
		
	}

	
	private List<ArrayModel> getArrayModels (Node interfacNode){
		List<ArrayModel> arrayModels = new LinkedList<ArrayModel>();
		List<Node> arrays = domService.getChildNodes(interfacNode, ARRAY);
		
		if(arrays.size() == 0){
			return arrayModels;
		}
		
		for (Node array : arrays) {
			String chineseAnnotation = domService.getAttribute(array, CHINESE_ANNOTATION);
			String key = domService.getAttribute(array, KEY);
			
			List<ParameterModel> parameterModels =  getParameterModels4Interfac(array);
			ArrayModel arrayModel = new ArrayModel();
			arrayModel.setChineseAnnotation(chineseAnnotation);
			arrayModel.setKey(key);
			arrayModel.setParameterModels(parameterModels);
			arrayModels.add(arrayModel);
		}
		
		
		return arrayModels;
	}
	
	
	
	
	/**
	 * 初始化参数
	 * 
	 * @param moduleNode
	 */
	private void initPublicParameterModels(Node moduleNode) {
		List<Node> parameterNodes = domService.getChildNodes(moduleNode, PARAMETER);
		Map<String, ParameterModel> map = new HashMap<>();
		for (Node parameterNode : parameterNodes) {
			String chineseAnnotation = domService.getAttribute(parameterNode, CHINESE_ANNOTATION);
			String key = domService.getAttribute(parameterNode, KEY);
			String type = domService.getAttribute(parameterNode, TYPE);
			String must = domService.getAttribute(parameterNode, MUST);
			String example = domService.getAttribute(parameterNode, EXAMPLE);
			must = (must == null) ? "true" : "false";
			String maxLength = domService.getAttribute(parameterNode, MAX_LENGTH);
			String minLength = domService.getAttribute(parameterNode, MIN_LENGTH);
			String enumeration = domService.getAttribute(parameterNode, ENUMERATION);
			minLength = (minLength == null) ? "1" : minLength;
			ParameterModel pm = new ParameterModel();
			pm.setIsMust(must);
			pm.setParameterName(chineseAnnotation);
			pm.setParameterCode(key);
			pm.setParameterDataType(type);
			pm.setMaxLength(Integer.parseInt(maxLength));
			pm.setMinLength(Integer.parseInt(minLength));
			pm.setParameterExample(example);
			pm.setEnumeration(enumeration);
			List<ParameterConstraintModel> parameterConstraintModels = this.getParameterConstraintModels(parameterNode);
			pm.setParameterConstraintModels(parameterConstraintModels);
			map.put(key, pm);
		}

		publicParameterModels = map;

	}

	private List<ParameterConstraintModel> getParameterConstraintModels(Node parameterNode) {

		return null;

	}

	/**
	 * 读文件
	 * 
	 * @param file
	 * @return
	 */
	private static String readFile(File file) {

		if (!file.exists() || file.isDirectory()) {
			return null;
		}

		BufferedReader br;
		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			br = new BufferedReader(isr);
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				sb.append(temp + " ");
				temp = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
