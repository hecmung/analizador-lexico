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
        keyWords.put("and", TipoToken.AND);
        keyWords.put("class", TipoToken.CLASS);
        keyWords.put("else", TipoToken.ELSE);
        keyWords.put("false", TipoToken.FALSE);
        keyWords.put("for", TipoToken.FOR);
        keyWords.put("fun", TipoToken.FUN); //definir funciones
        keyWords.put("if", TipoToken.IF);
        keyWords.put("null", TipoToken.NULL);
        keyWords.put("or", TipoToken.OR);
        keyWords.put("print", TipoToken.PRINT);
        keyWords.put("return", TipoToken.RETURN);
        keyWords.put("super", TipoToken.SUPER);
        keyWords.put("this", TipoToken.THIS);
        keyWords.put("true", TipoToken.TRUE);
        keyWords.put("var", TipoToken.VAR); //definir variables
        keyWords.put("while", TipoToken.WHILE);
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

            switch (current) {
                //Sentencia para ignorar espacios y/o tabulaciones
                case ' ':
                case '\t':
                case '\r':
                    position ++;
                break;
                //Sentencia para ignorar saltos de línea
                case '\n':
                    position ++;
                    linea ++;
                break;
                case '/':
                    if (nextChar() == '/') {
                        //Sentencia para ignorar comentario
                        position += 2;
                        while (position < source.length() && source.charAt(position) != '\n') {
                            position++;
                        }
                    } else if (nextChar() == '*') {
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
                            Interprete.error(linea, "Unterminated comment, it's missing the simbols */");
                            System.exit(65);
                            break;
                        }
                    } else {
                        position++;
                        tokens.add(new Token(TipoToken.SLASH, "/", null, position));
                    }
                break;
                case '*':
                    if (nextChar() == '/') {
                        Interprete.error(linea,"Unexpected character: " + current + nextChar());
                        System.exit(65);
                        break;
                    } else {
                        position++;
                        tokens.add(new Token(TipoToken.STAR, "*", null, position));
                    }
                break;
                case '+':
                    position++;
                    tokens.add(new Token(TipoToken.PLUS, "+", null, position));
                break;
                case '-':
                    position++;
                    tokens.add(new Token(TipoToken.MINUS, "-", null, position));
                break;
                case '=':
                    if (nextChar() == '=') {
                        position += 2;
                        tokens.add(new Token(TipoToken.EQUAL_EQUAL, "==", null, position));
                    } else {
                        position++;
                        tokens.add(new Token(TipoToken.EQUAL, "=", null, position));
                    }
                break;
                case '!':
                    if (nextChar() == '=') {
                        position += 2;
                        tokens.add(new Token(TipoToken.NOT_EQUAL, "!=", null, position));
                    } else {
                        position++;
                        tokens.add(new Token(TipoToken.NOT, "!", null, position));
                    }
                break;
                case '<':
                    if (nextChar() == '=') {
                        position += 2;
                        tokens.add(new Token(TipoToken.LESS_EQUAL, "<=", null, position));
                    } else {
                        position++;
                        tokens.add(new Token(TipoToken.LESS, "<", null, position));
                    }
                break;
                case '>':
                    if (nextChar() == '=') {
                        position += 2;
                        tokens.add(new Token(TipoToken.GREATER_EQUAL, ">=", null, position));
                    } else {
                        position++;
                        tokens.add(new Token(TipoToken.GREATER, ">", null, position));
                    }
                break;
                case ';':
                    position++;
                    tokens.add(new Token(TipoToken.SEMICOLON, ";", null, position));
                break;
                case ',':
                    position++;
                    tokens.add(new Token(TipoToken.COMMA, ",", null, position));
                break;
                case '.':
                    position++;
                    tokens.add(new Token(TipoToken.DOT, ".", null, position));
                break;
                case '(':
                    position++;
                    tokens.add(new Token(TipoToken.LPAREN, "(", null, position));
                break;
                case ')':
                    position++;
                    tokens.add(new Token(TipoToken.RPAREN, ")", null, position));
                break;
                case '{':
                    position++;
                    tokens.add(new Token(TipoToken.LBRACE, "{", null, position));
                break;
                case '}':
                    position++;
                    tokens.add(new Token(TipoToken.RBRACE, "}", null, position));
                break;
                case '\"':
                    int start = position, aux = 0;

                    while (position < source.length()) {
                        if (nextChar() == '\"') {
                            position += 2;
                            aux++;
                            break;
                        } else {
                            position++;
                        }
                    }
                    int end = position;

                    if (start + 2 == position) {
                        tokens.add(new Token(TipoToken.STRING, source.substring(start, position), source.substring(start, position), position));
                    } else if (aux == 0) {
                        Interprete.error(linea, "Character \" is missing");
                        System.exit(65);
                        break;
                    }
                    tokens.add(new Token(TipoToken.STRING, source.substring(start, position), source.substring(start + 1, end - 1), position));
                break;
                default:
                    if (Character.isDigit(current)) {
                        // Números
                        start = position;

                        while (position < source.length() && (Character.isDigit(source.charAt(position)) || source.charAt(position) == '.')) {
                            position++;
                        }
                        tokens.add(new Token(TipoToken.NUMBER, source.substring(start, position), Double.valueOf(source.substring(start, position)), start));
                    } else if (Character.isLetter(current)) {
                        // Identificador o palabra clave
                        start = position;

                        while (position < source.length() && Character.isLetter(source.charAt(position))) {
                            if(Character.isDigit(nextChar())) {
                                position += 2;
                            } else {
                                position++;
                            }
                        }

                        String identifier = source.substring(start, position);
                        TipoToken type = keyWords.get(identifier);

                        if (type == null) {
                            tokens.add(new Token(TipoToken.IDENTIFIER, identifier, null, position));
                        } else {
                            tokens.add(new Token(type, type.name(), null, position));
                        }
                    } else {
                        Interprete.error(linea,"Unexpected character: " + current);
                        System.exit(65);
                        break;
                    }
                break;
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