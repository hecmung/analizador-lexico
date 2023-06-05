import java.util.HashMap;
import java.util.Map;

public class Scanner {
    private final String source;
    private int linea = 1;
    private int currentChar = 0;
    private int position;
    private static final Map<String, TipoToken> keyWords;
    static {
        keyWords = new HashMap<>();
        keyWords.put("SELECT", TipoToken.SELECT);
        keyWords.put("FROM", TipoToken.FROM);
        keyWords.put("DISTINCT", TipoToken.DISTINCT);
        keyWords.put("COMMA", TipoToken.COMMA);
        keyWords.put("PUNTO", TipoToken.PUNTO);
        keyWords.put("ASTERISCO", TipoToken.ASTERISCO);
    }

    Scanner(String source){
        this.source = source;
        this.position = 0;
    }

    public Token getNextToken() {
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }

            if (Character.isLetter(currentChar)) {
                return processIdentifier();
            }

            if (currentChar == ',') {
                advance();
                return new Token(TipoToken.COMMA, ",", "", currentChar);
            }

            if (currentChar == '.') {
                advance();
                return new Token(TipoToken.PUNTO, ".", "", currentChar);
            }

            if (currentChar == '*') {
                advance();
                return new Token(TipoToken.ASTERISCO, "*", "", currentChar);
            }

            error("Carácter no válido: " + currentChar);
        }

        return new Token(TipoToken.EOF, "", "", currentChar);
    }

    private void advance() {
        position++;
        if (position < source.length()) {
            currentChar = source.charAt(position);
        } else {
            currentChar = '\0';
        }
    }

    private void skipWhitespace() {
        while (currentChar != '\0' && Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private Token processIdentifier() {
        StringBuilder builder = new StringBuilder();
        while (currentChar != '\0' && Character.isLetterOrDigit(currentChar)) {
            builder.append(currentChar);
            advance();
        }

        String identifier = builder.toString().toUpperCase();
        TipoToken tokenType = keyWords.getOrDefault(identifier, TipoToken.IDENTIFICADOR);
        return new Token(tokenType, identifier, "", currentChar);
    }

    private void error(String errorMessage) {
        throw new RuntimeException("Error léxico: " + errorMessage);
    }
}