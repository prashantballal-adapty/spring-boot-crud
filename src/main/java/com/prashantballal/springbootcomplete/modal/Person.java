package com.prashantballal.springbootcomplete.modal;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
	private final UUID myid;
	private final String name;
	
	public Person(@JsonProperty("id") UUID myid,
			@JsonProperty("name") String name) {
		this.myid = myid;
		this.name = name;
	}
	
	@NotBlank
	public UUID getMyid() {
		return myid;
	}

	@NotBlank
	public String getName() {
		return name;
	}	
}
