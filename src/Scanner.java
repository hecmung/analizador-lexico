import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private int position = 0;

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
        keyWords.put("Char", TipoToken.CHAR);
        keyWords.put("String", TipoToken.STRING);
        keyWords.put("boolean", TipoToken.BOOLEAN);
        keyWords.put("true", TipoToken.TRUE);
        keyWords.put("false", TipoToken.FALSE);
        keyWords.put("public", TipoToken.PUBLIC);
        keyWords.put("class", TipoToken.CLASS);
        keyWords.put("extends", TipoToken.EXTENDS);
        keyWords.put("implements", TipoToken.IMPLEMENTS);
        keyWords.put("interface", TipoToken.INTERFACE);
        keyWords.put("new", TipoToken.NEW);
        keyWords.put("this", TipoToken.THIS);
        keyWords.put("super", TipoToken.SUPER);
        keyWords.put("static", TipoToken.STATIC);
        keyWords.put("final", TipoToken.FINAL);
        keyWords.put("try", TipoToken.TRY);
        keyWords.put("catch", TipoToken.CATCH);
        keyWords.put("finally", TipoToken.FINALLY);
        keyWords.put("throw", TipoToken.THROW);
        keyWords.put("throws", TipoToken.THROWS);
        keyWords.put("private", TipoToken.PRIVATE);
        keyWords.put("protected", TipoToken.PROTECTED);
        keyWords.put("package", TipoToken.PACKAGE);
        keyWords.put("import", TipoToken.IMPORT);
        keyWords.put("instanceof", TipoToken.INSTANCEOF);
        keyWords.put("enum", TipoToken.ENUM);
        keyWords.put("assert", TipoToken.ASSERT);
        keyWords.put("abstract", TipoToken.ABSTRACT);
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
        while (position < source.length()) {
            char current = source.charAt(position);

            if (current == ' ' || current == '\t' || current == '\r') {
                //Sentencia para ignorar espacios y/o tabulaciones
                position++;
            }
            else if (current == '\n') {
                //Sentencia para ignorar saltos de línea
                position++;
                linea++;
            } else if (current == '/' && nextChar() == '/') {
                //Sentencia para ignorar comentario
                position += 2;
                while (position < source.length() && source.charAt(position) != '\n') {
                    position++;
                }
            } else if (current == '/' && nextChar() == '*') {
                //Sentencia para ignorar comentario de varias líneas
                position += 2;
                while (position + 1 < source.length() && !(source.charAt(position) == '*' && source.charAt(position + 1) == '/')) {
                    if (source.charAt(position) == '\n') {
                        linea++;
                    }
                    position++;
                }

                if (position + 1 < source.length()) {
                    position += 2;
                } else {
                    throw new RuntimeException("Unterminated comment");
                }
            } else if (current == '+') {
                if(nextChar() == '+') {
                    position += 2;
                    tokens.add(new Token(TipoToken.INCREMENT, "++", null, position));
                } else if(nextChar() == '=') {
                    position += 2;
                    tokens.add(new Token(TipoToken.PLUS_ASSIGN, "+=", null, position));
                } else {
                    position++;
                    tokens.add(new Token(TipoToken.PLUS, "+", null, position));
                }
            } else if (current == '-') {
                if(nextChar() == '-') {
                    position += 2;
                    tokens.add(new Token(TipoToken.DECREMENT, "--", null, position));
                } else if(nextChar() == '=') {
                    position += 2;
                    tokens.add(new Token(TipoToken.MINUS_ASSIGN, "-=", null, position));
                } else {
                    position++;
                    tokens.add(new Token(TipoToken.MINUS, "-", null, position));
                }
            } else if (current == '&' && nextChar() == '&') {
                position += 2;
                tokens.add(new Token(TipoToken.AND, "&&", null, position));
            } else if (current == '|' && nextChar() == '|') {
                position += 2;
                tokens.add(new Token(TipoToken.OR, "||", null, position));
            } else if (current == '=') {
                if (nextChar() == '=') {
                    position += 2;
                    tokens.add(new Token(TipoToken.EQUAL_EQUAL, "==", null, position));
                } else if (nextChar() == '-') {
                    position += 2;
                    tokens.add(new Token(TipoToken.MINUS_ASSIGN, "=-", null, position));
                } else if (nextChar() == '+') {
                    position += 2;
                    tokens.add(new Token(TipoToken.PLUS_ASSIGN, "=+", null, position));
                } else if(nextChar() == '*') {
                    position += 2;
                    tokens.add(new Token(TipoToken.STAR_ASSIGN, "=*", null, position));
                } else if(nextChar() == '/') {
                    position += 2;
                    tokens.add(new Token(TipoToken.SLASH_ASSIGN, "=/", null, position));
                } else if(nextChar() == '%') {
                    position += 2;
                    tokens.add(new Token(TipoToken.MODULO_ASSIGN, "=%", null, position));
                } else {
                    position++;
                    tokens.add(new Token(TipoToken.EQUAL, "=", null, position));
                }
            } else if (current == '!') {
                if (nextChar() == '=') {
                    position += 2;
                    tokens.add(new Token(TipoToken.NOT_EQUAL, "!=", null, position));
                } else {
                    position++;
                    tokens.add(new Token(TipoToken.NOT, "!", null, position));
                }
            } else if (current == '<') {
                if (nextChar() == '=') {
                    position += 2;
                    tokens.add(new Token(TipoToken.LESS_EQUAL, "<=", null, position));
                } else {
                    position++;
                    tokens.add(new Token(TipoToken.LESS, "<", null, position));
                }
            } else if (current == '>') {
                if (nextChar() == '=') {
                    position += 2;
                    tokens.add(new Token(TipoToken.GREATER_EQUAL, ">=", null, position));
                } else {
                    position++;
                    tokens.add(new Token(TipoToken.GREATER, ">", null, position));
                }
            } else if (current == '*') {
                if(nextChar() == '=') {
                    position += 2;
                    tokens.add(new Token(TipoToken.STAR_ASSIGN, "*=", null, position));
                }
                position++;
                tokens.add(new Token(TipoToken.STAR, "*", null, position));
            } else if (current == '/') {
                if(nextChar() == '=') {
                    position += 2;
                    tokens.add(new Token(TipoToken.SLASH_ASSIGN, "/=", null, position));
                }
                position++;
                tokens.add(new Token(TipoToken.SLASH, "/", null, position));
            } else if (current == '%') {
                if(nextChar() == '=') {
                    position += 2;
                    tokens.add(new Token(TipoToken.MODULO_ASSIGN, "%=", null, position));
                }
                position++;
                tokens.add(new Token(TipoToken.MOD, "%", null, position));
            } else if (current == ';') {
                position++;
                tokens.add(new Token(TipoToken.SEMICOLON, ";", null, position));
            } else if (current == ',') {
                position++;
                tokens.add(new Token(TipoToken.COMMA, ",", null, position));
            } else if (current == '.') {
                position++;
                tokens.add(new Token(TipoToken.DOT, ".", null, position));
            } else if (current == '(') {
                position++;
                tokens.add(new Token(TipoToken.LPAREN, "(", null, position));
            } else if (current == ')') {
                position++;
                tokens.add(new Token(TipoToken.RPAREN, ")", null, position));
            } else if (current == '{') {
                position++;
                tokens.add(new Token(TipoToken.LBRACE, "{", null, position));
            } else if (current == '}') {
                position++;
                tokens.add(new Token(TipoToken.RBRACE, "}", null, position));
            } else if (current == '[') {
                position++;
                tokens.add(new Token(TipoToken.LBRACKET, "[", null, position));
            } else if (current == ']') {
                position++;
                tokens.add(new Token(TipoToken.RBRACKET, "]", null, position));
            } else if (current == '\"') {
                int start = position, aux = 0;

                while (position < source.length()) {
                    if(nextChar() == '\"') {
                        position += 2;
                        aux++;
                        break;
                    }
                    else {
                        position++;
                    }
                }
                int end = position;

                if(start + 2 == position)
                    tokens.add(new Token(TipoToken.QUOTES, source.substring(start, position), source.substring(start, position), position));
                else if(aux == 0)
                    throw new RuntimeException("Character \" is missing: " + position);

                tokens.add(new Token(TipoToken.QUOTES, source.substring(start, position), source.substring(start + 1, end - 1), position));
            }else if (Character.isDigit(current)) {
                // Números
                int start = position;

                while (position < source.length() && (Character.isDigit(source.charAt(position)) || source.charAt(position) == '.')) {
                    position++;
                }
                tokens.add(new Token(TipoToken.NUMBER, source.substring(start, position), source.substring(start, position), start));
            } else if (Character.isLetter(current)) {
                // Identificador o palabra clave
                int start = position;

                while (position < source.length() && (Character.isLetter(source.charAt(position)))) {
                    position++;
                }

                String identifier = source.substring(start, position);
                TipoToken type = keyWords.get(identifier);

                if (type == null) {
                    tokens.add(new Token(TipoToken.IDENTIFIER, identifier, null, position));
                } else {
                    tokens.add(new Token(type, type.name(), null, position));
                }
            } else {
                throw new RuntimeException("Unexpected character: " + current + " " + position);
            }
        }
        tokens.add(new Token(TipoToken.EOF, "", null, linea));
        return tokens;
    }

    private char nextChar() {
        if (position + 1 >= source.length()) {
            return '\0';
        }

        return source.charAt(position + 1);
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