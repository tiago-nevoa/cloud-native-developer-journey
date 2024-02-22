package com.sap.cc.ascii;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AsciiArtServiceUrlProvider {

	@Value("${service.ascii.url}")
	private String serviceUrl;

	public String getServiceUrl() {
		return serviceUrl;
	}
}
