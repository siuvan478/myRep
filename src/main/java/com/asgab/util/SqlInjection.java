package com.asgab.util;

public class SqlInjection implements Istrip {
	public String strip(String value) {
		return value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "");
	}
}
