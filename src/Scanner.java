import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> keyWords;
    static {
        keyWords = new HashMap<>();
        keyWords.put("if", TipoToken.IF);
        keyWords.put("else", TipoToken.ELSE);
        keyWords.put("for", TipoToken.FOR);
        keyWords.put("while", TipoToken.WHILE);
        keyWords.put("do", TipoToken.DO);
        keyWords.put("break", TipoToken.BREAK);
        keyWords.put("continue", TipoToken.CONTINUE);
        keyWords.put("return", TipoToken.RETURN);
        keyWords.put("void", TipoToken.VOID);
        keyWords.put("int", TipoToken.INT);
        keyWords.put("float", TipoToken.FLOAT);
        keyWords.put("double", TipoToken.DOUBLE);
        keyWords.put("char", TipoToken.CHAR);
        keyWords.put("string", TipoToken.STRING);
        keyWords.put("boolean", TipoToken.BOOLEAN);
        keyWords.put("true", TipoToken.TRUE);
        keyWords.put("false", TipoToken.FALSE);
    }

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }
}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */