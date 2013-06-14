package study.guava;

import lombok.Data;

public @Data class Person implements Comparable<Person> {
	private String name;
	private String lastName;


	public Person(String name, String lastName ) {

		super();
		this.name = name;
		this.lastName = lastName;
	}


	public int compareTo(Person person) {

		return this.getName().compareTo(person.getName());
	}

}
