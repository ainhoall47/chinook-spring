package org.ainhoall.controlador;

import org.springframework.web.util.HtmlUtils;

public final class Html {

    private Html(){
    }

    public static String pagina(String titulo, String cuerpo) {
        return "<!DOCTYPE html>"
                + "<html lang=\"es\"><head><meta charset=\"UTF-8\">"
                + "<title>" + esc(titulo) + " · Chinook</title></head>"
                + "<body>"
                + "<p><a href=\"/\">Inicio</a> · <a href=\"/generos\">Géneros</a> · "
                + "<a href=\"/artistas/buscar\">Buscar artistas</a></p>"
                + "<h1>" + esc(titulo) + "</h1>"
                + cuerpo
                + "</body></html>";
    }

    public static String esc(String texto) {
        if (texto == null) {
            return "";
        }
        return HtmlUtils.htmlEscape(texto, "UTF-8");
    }
}
