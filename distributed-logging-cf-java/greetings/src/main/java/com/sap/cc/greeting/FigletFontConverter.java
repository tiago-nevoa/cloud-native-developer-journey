package com.sap.cc.greeting;

import com.github.lalyos.jfiglet.FigletFont;
import org.springframework.stereotype.Service;

@Service
public class FigletFontConverter {
    public String convertOneLine(String message) {
        return FigletFont.convertOneLine(message);
    }
}
