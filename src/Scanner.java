import java.util.HashMap;
import java.util.Map;

public class Scanner {
    private final String source;
    private char currentChar;
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
        this.currentChar = source.charAt(position);
    }

    public Token getNextToken() {
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }

            if(currentChar == '\n' || currentChar == '\r' || currentChar == '\t') {

                advance();
            }

            if (Character.isLetter(currentChar)) {
                return processIdentifier();
            }

            if (currentChar == ',') {
                return advanceAndCreateToken(TipoToken.COMMA, ",", position);
            }

            if (currentChar == '.') {
                return advanceAndCreateToken(TipoToken.PUNTO, ".", position);
            }

            if (currentChar == '*') {
                return advanceAndCreateToken(TipoToken.ASTERISCO, "*", position);
            }

            error("Carácter no válido: " + currentChar);
        }

        return new Token(TipoToken.EOF, "", "", position);
    }

    private Token advanceAndCreateToken(TipoToken type, String value, int position) {
        advance();
        return new Token(type, value, "", position);
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
        return new Token(tokenType, identifier, "", position);
    }

    private void error(String errorMessage) {
        throw new RuntimeException("Error léxico: " + errorMessage);
    }
}