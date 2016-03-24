package com.redhat.developer.msa.namaste;

import feign.RequestLine;

import java.util.List;

public interface ChainedGreeting {

	@RequestLine("GET /")
	public List<String> greetings();

}
