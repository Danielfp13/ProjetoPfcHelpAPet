package com.projeto.helpapet.resources.execepton;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> lista = new ArrayList<FieldMessage>();

	public ValidationError(Integer status, String msg, Long timeStanp) {
		super(status, msg, timeStanp);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getErrors() {
		return lista;
	}

	public void addError(String fielname, String message) {
		lista.add(new FieldMessage(fielname,message));
	}
	
	

}
