package study.guava;

import lombok.Data;

public @Data class Employee implements Comparable<Employee> {

	private int id;
	private String name;
	private int yearsOfService;

	public Employee(int id, String name, int yearsOfService ) {

		super();
		this.id = id;
		this.name = name;
		this.yearsOfService = yearsOfService;
	}

	public int compareTo(Employee employee) {
		return this.getName().compareTo(employee.getName());
	}

}
