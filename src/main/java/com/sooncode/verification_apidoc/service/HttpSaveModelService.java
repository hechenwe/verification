package com.sooncode.verification_apidoc.service;

import java.util.List;

import com.sooncode.verification_apidoc.model.AddInterfacModel;
import com.sooncode.verification_apidoc.model.AddModuleModel;
import com.sooncode.verification_apidoc.model.AddProjectModel;
import com.sooncode.verification_apidoc.model.InterfacModel;
import com.sooncode.verification_apidoc.model.ModuleModel;
import com.sooncode.verification_apidoc.model.ParameterModel;
import com.sooncode.verification_apidoc.model.ProjectModel;

import net.sf.json.JSONArray;

public class HttpSaveModelService {

	
	private String url ;
	
	
	public HttpSaveModelService (String url){
		this.url = url;
	}
	
	
	public String saveProject(ProjectModel projectModel){
		String urlString = url  + "/projectController/addProject";
		AddProjectModel addPM =    (AddProjectModel) RObject.to(projectModel, AddProjectModel.class);
		SJson sj = new SJson(addPM);
		String result = HttpRequest.postRequest(urlString, sj.getJsonString());
		SJson resultJson = new SJson(result);
		String id = (String) resultJson.getFields("projectId");
		return id;
	}
	
	
	public String saveModule(ModuleModel moduleModel){
		String urlString = url  + "/moduleController/addModule";
		AddModuleModel addMM = (AddModuleModel) RObject.to(moduleModel, AddModuleModel.class);
		SJson sj = new SJson(addMM);
		String result = HttpRequest.postRequest(urlString, sj.getJsonString());
		SJson resultJson = new SJson(result);
		String id = (String) resultJson.getFields("moduleId");
		return id;
	}
	
	
	public String saveInterfac(InterfacModel interfacModel){
		String urlString = url+"/interfacController/addInterfac";
		AddInterfacModel  addIM = (AddInterfacModel ) RObject.to(interfacModel , AddInterfacModel.class);
		SJson sj = new SJson(addIM);
		String result = HttpRequest.postRequest(urlString, sj.getJsonString());
		SJson resultJson = new SJson(result);
		String id = (String) resultJson.getFields("interfacId");
		return id;
	}
	public void saveParameters(List<ParameterModel> parameterModels){
		String urlString = url+"/parameterController/addParameter";
		JSONArray jsonA = JSONArray.fromObject(parameterModels);
	    HttpRequest.postRequest(urlString, jsonA.toString());
	}
	
	
	public void saveParameterReturn(List<ParameterModel> parameterModels){
		String urlString = url+"/parameterReturnController/addParameterReturns";
		JSONArray jsonA = JSONArray.fromObject(parameterModels);
	    HttpRequest.postRequest(urlString, jsonA.toString());
		 
	}
	
}
