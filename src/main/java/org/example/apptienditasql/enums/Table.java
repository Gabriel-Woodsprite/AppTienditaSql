package org.example.apptienditasql.enums;

public enum Table {
	CATEGORY("category"),
	LOCATION("location"),
	PRESENTATION("presentation"),
	UNITS("units");

	private final String name;

	Table(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
