package com.sooncode.verification_apidoc.service;

 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;

import com.sooncode.verification_apidoc.model.ArrayModel;
import com.sooncode.verification_apidoc.model.InterfacModel;
import com.sooncode.verification_apidoc.model.ModuleModel;
import com.sooncode.verification_apidoc.model.ParameterModel;
import com.sooncode.verification_apidoc.model.ProjectModel;


/**controller验证aop
 * @author pc
 *
 */
public class ApiDocAop {

    public final static Log logger = LogFactory.getLog(ApiDocAop.class); 
    public ProjectModel pm;
    private String authorName;
    private String projectCode;
    private String projectName;
    private String urlPrefix;
    private String companyId;
    

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	 
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setConfLocation(String confLocation) throws FileNotFoundException{
        
        File file =  ResourceUtils.getFile(confLocation);
        String fileNames[];
        fileNames=file.list();
        ProjectModel pm = new ProjectModel();
        pm.setAuthorName(this.authorName);
        pm.setProjectCode(this.projectCode);
        pm.setUrlPrefix(this.urlPrefix);
        pm.setProjectName(this.projectName);
        pm.setCompanyId(this.companyId);
        List<ModuleModel> moduleModels = new ArrayList<>();
        for(int i=0;i<fileNames.length;i++)
        {
            File f =  ResourceUtils.getFile(confLocation+File.separatorChar+ fileNames[i]);
            
            ModuleModelService mms = new ModuleModelService(f);
            
            ModuleModel mm =  mms.getModuleModel();
            moduleModels.add(mm);
        }
        pm.setModuleModels(moduleModels);
        this.pm = pm;
         

    } 
    
    public void setApiDocServiceUrl(String apiDocServiceUrl)  {
    	
    	HttpSaveModelService hsms = new HttpSaveModelService(apiDocServiceUrl);
    	String projectId = hsms.saveProject(pm);
    	for (ModuleModel mm : pm.getModuleModels()) {
			mm.setProjectId(projectId);
			String moduleId = hsms.saveModule(mm);
			
			for(InterfacModel im : mm.getInterfacModels()){
				im.setUrl(this.urlPrefix + "/" + im.getUrl());
				im.setModuleId(moduleId);
				Map<String,Object> map = getMap4Json4Interfac(im);
				im.setJsonParameters(getJsonString(map));
				String interfacId = hsms.saveInterfac(im);
				for(ParameterModel pm : im.getParameterModels()){
					pm.setInterfacId(interfacId);
					String parameterId = hsms.saveParameter(pm);
				}
			}
		}
    }
 
    
    public Map<String,Object> getParameter4Json (List<ParameterModel> pmes){
    	
    	Map<String,Object> map = new HashMap<>();
    	for (ParameterModel pm : pmes) {
    		map.put(pm.getParameterCode(), pm.getParameterExample());
    		
    	}
    	return map;
    }
    
    
    public Map<String,Object> getArray4Json (List<ArrayModel> arrayModels){
    	Map<String,Object> map = new LinkedHashMap<>();
    	for (ArrayModel arrays : arrayModels) {
    		Map<String,Object> paraMap = getParameter4Json(arrays.getParameterModels());
    		map.put(arrays.getKey(), paraMap) ;
    	}
    	return map;
    }
    
    
    public Map<String,Object> getMap4Json4Interfac ( InterfacModel interfacModel){
     
    	Map<String,Object> map = new HashMap<>();
    	map.putAll(getParameter4Json(interfacModel.getParameterModels()));
    	map.putAll(getArray4Json(interfacModel.getArrayModels()));
    	return map;
    	 
    }
    
    public String getJsonString (Map<String,Object> map){
    	
    	SJson sj = new SJson(map);
    	return sj.getJsonString();
    	
    	
    }
     
}
