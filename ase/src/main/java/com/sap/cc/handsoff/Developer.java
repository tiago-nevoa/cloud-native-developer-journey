package com.sap.cc.handsoff;

public class Developer extends CodeCreator{
    private String name;
    private String language;

    public Developer(String name, String language) {
        this.name = name;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String code() throws UnsupportedDevelopmentLanguageException {
        switch (language.toLowerCase()){
            case "go":
                return "fmt.Println(\"Hello, " + name + "!\")";
            case "nodejs":
                return "console.log(\"Hello, " + name + "!\")";
            case "python" :
                return "print(\"Hello, " + name + "!\")";
            default:
                throw new UnsupportedDevelopmentLanguageException(language);
        }
    }
}
