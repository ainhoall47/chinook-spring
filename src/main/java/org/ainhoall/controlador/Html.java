package org.ainhoall.controlador;

import org.springframework.web.util.HtmlUtils;

public final class Html {

    private Html(){
    }

    public static String esc(String texto) {
        if (texto == null) {
            return "";
        }
        return HtmlUtils.htmlEscape(texto, "UTF-8");
    }
}
