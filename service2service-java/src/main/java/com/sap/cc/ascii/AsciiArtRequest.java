package com.sap.cc.ascii;

public class AsciiArtRequest {

    private String toConvert;
    private String fontId;

    public AsciiArtRequest(String toConvert, String fontId){
        this.toConvert = toConvert;
        this.fontId = fontId;
    }

    public String getToConvert() {
        return toConvert;
    }

    public void setToConvert(String toConvert) {
        this.toConvert = toConvert;
    }

    public String getFontId() {
        return fontId;
    }

    public void setFontId(String fontId) {
        this.fontId = fontId;
    }

}
