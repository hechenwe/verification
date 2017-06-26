package com.sooncode.verification_apidoc.service;

import java.util.List;

import com.sooncode.verification_apidoc.model.ModuleModel;

public class ControllerCache {

	private static List<ModuleModel> modules;
	
	public static void addModuleModel(ModuleModel moduleModel){
		modules.add(moduleModel);
	}
	
}
