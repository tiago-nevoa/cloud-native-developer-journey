package com.sap.cc.ascii;

public class AsciiArtResponse {
    private String beautifiedText;
    private String fontName;

    public AsciiArtResponse() {
    }

    public AsciiArtResponse(String beautifiedText, String fontName) {
        this.beautifiedText = beautifiedText;
        this.fontName = fontName;
    }

    public String getBeautifiedText() {
        return beautifiedText;
    }

    public String getFontName() {
        return fontName;
    }
}
