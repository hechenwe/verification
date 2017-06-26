package com.sooncode.verification_apidoc.model;



public class AddModuleModel {

	 /** 模块编号 */ 
	 private String moduleId; 
	 
	 /** 模块名称 */
	 private String moduleName ;
	 
	 
	 /** 模块代码 */
	 private String moduleCode ;
	 
	 /** 项目_编号 */
	 private String projectId ;

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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	 
	 
}
