package com.sooncode.verification.moduler;

import java.util.ArrayList;
import java.util.List;

public class Arrays {
	 private List<Array> arrays = new ArrayList<>();
	 public void add(Array array){
		 arrays.add(array); 
	 }
	 public void add(String arrayKey,Parameteres parameters){
		 Array a = new Array();
		 arrays.add(a); 
	 }
	public List<Array> getArrays() {
		return arrays;
	}
	 
	 
}

