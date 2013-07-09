package eu.eurofel.entities;

import java.io.Serializable;

public class Affiliation implements Serializable {

	private static final long serialVersionUID = 8738772773029964125L;

	public Affiliation() {
	}

	public Affiliation(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
