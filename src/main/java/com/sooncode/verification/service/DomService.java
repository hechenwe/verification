package com.sooncode.verification.service;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomService {
	private Document document;

	public DomService(Document document) {
		this.document = document;
	}

	public List<Node> getNodes4Document(String key) {
		NodeList root = this.document.getElementsByTagName(key);
		List<Node> list = new ArrayList<>();
		for (int i = 0; i < root.getLength(); i++) {
			Node node = root.item(0); // NodeList中的某一个节点
			list.add(node);
		}
		return list;
	}

	public Node getNode4Document(String key) {
		NodeList root = this.document.getElementsByTagName(key);
		Node node = root.item(0);
		return node;
	}

	public List<Node> getChildNodes(Node node) {
		NodeList nodeList = node.getChildNodes();
		List<Node> nodes = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = nodeList.item(0); // NodeList中的某一个节点
			nodes.add(n);
		}
		return nodes;
	}

	public List<Node> getChildNodes(Node node, String childNodeName) {
		NodeList nodeList = node.getChildNodes();
		List<Node> nodes = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = nodeList.item(0); // NodeList中的某一个节点
			String nodeName = n.getNodeName();
			if (nodeName.equals(childNodeName)) {
				nodes.add(n);
			}
		}
		return nodes;
	}

	public String getAttribute(Node node, String key) {
		NamedNodeMap nnm = node.getAttributes();
		for (int i = 0; i < nnm.getLength(); i++) {
			Node n = nnm.item(i);
			String name = n.getNodeName();
			String value = n.getNodeValue();
			if (key.equals(name)) {
				return value;
			}
		}
		return null;

	}
}
