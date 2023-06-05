package com.sap.cc.handsoff;

import java.util.List;

public class Hackathon implements DeveloperEvent{
    @Override
    public String codeTogether(List<CodeCreator> codeCreators) {

        StringBuilder output = new StringBuilder();

        for (CodeCreator codeCreator : codeCreators) {
            try {
                output.append(codeCreator.code());
            } catch (UnsupportedDevelopmentLanguageException e) {
                output.append(e.getMessage());
            }
            output.append("\n");
        }
        return output.toString();
    }
}
