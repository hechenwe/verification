package com.sooncode.verification.service;

import java.util.ArrayList;
import java.util.List;

import com.sooncode.verification.moduler.Array;
import com.sooncode.verification.moduler.Method;
import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.intercepter.VerificationElement;
import com.sooncode.verification.service.intercepter.method.ArrayVerificationIntercepter;
import com.sooncode.verification.service.intercepter.method.JsonFormatVerificationIntercepter;
import com.sooncode.verification.service.intercepter.method.ParameterVerificationIntercepter;
import com.sooncode.verification.service.intercepter.method.RequestMethodVerificationIntercepter;
import com.sooncode.verification.service.intercepter.method.VerificationIntercepterChain;

public class VerificationService {

	public static VerificationResult verification(String jsonData, Method method, String requestMothod) {

		VerificationElement ve = new VerificationElement();
		ve.setJsonData(jsonData);
		ve.setMethod(method);
		ve.setTemporary(requestMothod);
		VerificationIntercepterChain vic = new VerificationIntercepterChain();

		vic.add(RequestMethodVerificationIntercepter.class)
		   .add(JsonFormatVerificationIntercepter.class)
		   .add(ParameterVerificationIntercepter.class)
		   .add(ArrayVerificationIntercepter.class);
		 
		return vic.doIntercepter(ve, vic);

	}

	public static void main(String[] args) {
		String jsonData = "{\"id\":\"88888888\",\"types\":[{\"type1\":\"BBB\"},{\"type2\":\"AAA\"}]}";
		Method method = new Method();
		method.setMethod("POST");

		Parameter p = new Parameter();
		p.setKey("id");
		p.setMaxLength(32);
		p.setMinLength(8);
		p.setMust(true);
		p.setType("INTEGER");
		p.setEnumeration("88888888;99999999");
		List<Parameter> pes = new ArrayList<>();
		pes.add(p);

		List<Array> arrays = new ArrayList<>();

		Array a = new Array();
		a.setKey("types");
		Parameter type1 = new Parameter();
		type1.setKey("type2");
		type1.setMaxLength(32);
		type1.setMinLength(3);
		type1.setMust(true);
		type1.setType("STRING");
		type1.setEnumeration("AAA;BBB");
		List<Parameter> types = new ArrayList<>();
		types.add(type1);
		a.setParameters(types);
		arrays.add(a);
		method.setParameters(pes);
		method.setArrays(arrays);
		 
		VerificationResult vr = VerificationService.verification(jsonData, method, "POST");

		System.out.println("vr " + vr.getIsPass() + " : " + vr.getReason());

	}
}
