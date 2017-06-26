package com.sooncode.verification_apidoc.model;

import java.util.List;

public class ModuleModel {

	 /** 模块编号 */ 
	 private String moduleId; 
	 
	 /** 模块名称 */
	 private String moduleName ;
	 
	 
	 /** 模块代码 */
	 private String moduleCode ;
	 
	 private String projectId;
	 
	 private List<InterfacModel> interfacModels;
	 
	 
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	 
	public List<InterfacModel> getInterfacModels() {
		return interfacModels;
	}

	public void setInterfacModels(List<InterfacModel> interfacModels) {
		this.interfacModels = interfacModels;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	 
	 
}
