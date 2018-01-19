package com.sooncode.verification.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sooncode.verification.moduler.Array;
import com.sooncode.verification.moduler.Method;
import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification_apidoc.service.DomService;

/**
 * 加载参数配置文件
 * 
 * @author pc
 *
 */
public class MethodParameter {

	private static final String CONTROLLER = "controller";
	//private static final String CHINESE_ANNOTATION = "chineseAnnotation";
	private static final String METHOD = "method";
	private static final String PARAMETER = "parameter";
	private static final String ARRAY = "array";
	private static final String REF = "ref";
	private static final String MUST = "must";
    public final static Log logger = LogFactory.getLog(MethodParameter.class); 
	// private String path = PathUtil.getClassPath() ;
	private DomService domService;
	public Map<String, Parameter> paraMap = new HashMap<>();

	public MethodParameter(File file) {

		String str = readFile(file);
		InputSource inputSource = new InputSource(new StringReader(str));
		try {
			Document  document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
			this.domService = new DomService(document);
		} catch (SAXException | IOException | ParserConfigurationException e) {

			e.printStackTrace();
		}

		this.initMethodMap();

	}
	
	
	public MethodParameter(BufferedReader br) {
		
		String str = readFile(br);
		InputSource inputSource = new InputSource(new StringReader(str));
		try {
			Document  document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
			this.domService = new DomService(document);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			
			e.printStackTrace();
		}
		
		this.initMethodMap();
		
	}

	private void initMethodMap() {

		 
		Node node = domService.getNode4Document(CONTROLLER);

		List<Node> parameterlist = domService.getChildNodes(node, PARAMETER);
		for (Node n : parameterlist) {
			Parameter p = getParameter(n);
			if (p != null) {
				this.paraMap.put(p.getKey(), p);
			}
		}

		List<Node> methodlist = domService.getChildNodes(node, METHOD);
		for (Node n : methodlist) {
			Method c = getMethod(n);
			MethodParameterManager.controllerMap.put(c.getUrl(), c);
			logger.debug("[parameter_verification]"+c.getUrl());
		}

	}

	private Method getMethod(Node node) {
		NamedNodeMap nnm = node.getAttributes();
		RObject rObj = new RObject(Method.class);
		for (int i = 0; i < nnm.getLength(); i++) {
			Node n = nnm.item(i);
			String name = n.getNodeName();
			String value = n.getNodeValue();
			rObj.invokeSetMethod(name, value);
		}
		Method c = rObj.getObject();
		NodeList nodeList = node.getChildNodes();
		List<Parameter> parameters = new ArrayList<>();
		List<Array> arrays = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = nodeList.item(i);
			String nodeName = n.getNodeName();
			if (nodeName.equals(PARAMETER)) {
				Parameter p = getParameter(n);
				Parameter newPar = new Parameter();
				newPar.setEnumeration(p.getEnumeration());
				newPar.setKey(p.getKey());
				newPar.setMaxLength(p.getMaxLength());
				newPar.setType(p.getType());

				if (getParameterMust(n)) {
					newPar.setMust(true);

				} else {
					newPar.setMust(false);
				}
				parameters.add(newPar);
			} else if (nodeName.equals(ARRAY)) {
				Array a = getArray(n);
				arrays.add(a);
			}

		}
		c.setParameters(parameters);
		c.setArrays(arrays);
		return c;
	}

	private Array getArray(Node node) {
		NamedNodeMap nnm = node.getAttributes();
		RObject rObj = new RObject(Array.class);
		for (int i = 0; i < nnm.getLength(); i++) {
			Node n = nnm.item(i);
			String name = n.getNodeName();
			String value = n.getNodeValue();
			rObj.invokeSetMethod(name, value);
		}

		Array a = rObj.getObject();

		NodeList nodeList = node.getChildNodes();
		List<Parameter> parameters = new ArrayList<>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = nodeList.item(i);
			if (n.getNodeName().equals(PARAMETER)) {
				Parameter p = getParameter(n);
				parameters.add(p);
			}
		}
		a.setParameters(parameters);
		return a;
	}

	private Parameter getParameter(Node node) {

		NamedNodeMap nnm = node.getAttributes();
		RObject rObj = new RObject(Parameter.class);
		String ref = null;
		for (int i = 0; i < nnm.getLength(); i++) {
			Node n = nnm.item(i);
			String name = n.getNodeName();
			String value = n.getNodeValue();

			if (name.equals(REF)) {
				ref = value;
				break;
			}

			Class<?> clas = rObj.getSetMethodParamertType(name);
			if (clas == Integer.class) {
				rObj.invokeSetMethod(name, Integer.parseInt(value));
			} else if (clas == Boolean.class) {
				rObj.invokeSetMethod(name, Boolean.parseBoolean(value));
			} else {
				rObj.invokeSetMethod(name, value);
			}
		}

		Parameter p = new Parameter();
		if (ref != null) {
			p = this.paraMap.get(ref);
		} else {
			p = rObj.getObject();
		}

		return p;

	}

	private boolean getParameterMust(Node node) {
		NamedNodeMap nnm = node.getAttributes();
		for (int i = 0; i < nnm.getLength(); i++) {
			Node n = nnm.item(i);
			String name = n.getNodeName();
			String value = n.getNodeValue();
			if (name.equals(MUST) && value.trim().equals("false")) {
				return false;
			}

		}
		return true;
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
			br = new BufferedReader(new FileReader(file));
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				sb.append(temp + " ");
				temp = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	private static String readFile(BufferedReader br) {
		StringBuffer sb = new StringBuffer();
		try {
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				sb.append(temp + " ");
				temp = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
