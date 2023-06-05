package com.sap.cc.handsoff;

public class UnsupportedDevelopmentLanguageException extends Exception {

//██╗  ██╗ █████╗ ███╗   ██╗██████╗ ███████╗     ██████╗ ███████╗███████╗██╗██╗██╗
//██║  ██║██╔══██╗████╗  ██║██╔══██╗██╔════╝    ██╔═══██╗██╔════╝██╔════╝██║██║██║
//███████║███████║██╔██╗ ██║██║  ██║███████╗    ██║   ██║█████╗  █████╗  ██║██║██║
//██╔══██║██╔══██║██║╚██╗██║██║  ██║╚════██║    ██║   ██║██╔══╝  ██╔══╝  ╚═╝╚═╝╚═╝
//██║  ██║██║  ██║██║ ╚████║██████╔╝███████║    ╚██████╔╝██║     ██║     ██╗██╗██╗
//╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝     ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝╚═╝

	private static final long serialVersionUID = 1L;

	public UnsupportedDevelopmentLanguageException(String language) {
		super("Unsupported language: " + language);
	}
}
