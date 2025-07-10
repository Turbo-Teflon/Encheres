package fr.eni.encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class BuisnessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5476816355215964047L;
	private List<String> messages;
	
	public BuisnessException() {
		messages = new ArrayList<String>();
	}
	


	public Iterable<String> getMessages() {
		return messages;
	}


	public void add(String message) {
		messages.add(message);
	}
	
	public boolean hasError() {
		return !messages.isEmpty();
	}

}
