package com.sooncode.verification_apidoc.model;

import java.util.List;

public class ProjectModel {

	/** 项目代码 */
	private String projectCode;
	/** 作者姓名 */
	private String authorName;

	/** 资源前缀 */
	private String urlPrefix;
	/** 项目名称 */
	private String projectName;
	private String companyId;
	
	private String number ;
	
	
	private List<ModuleModel> moduleModels;
	
	
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<ModuleModel> getModuleModels() {
		return moduleModels;
	}

	public void setModuleModels(List<ModuleModel> moduleModels) {
		this.moduleModels = moduleModels;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	

}
