public class Parser {
    private Scanner scanner;
    private Token currentToken;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.currentToken = scanner.getNextToken();
    }

    public void parse() {
        query();
    }

    private void query() {
        selectStatement();
    }

    private void selectStatement() {
        eat(TipoToken.SELECT);
        if (currentToken.tipo == TipoToken.DISTINCT) {
            eat(TipoToken.DISTINCT);
        }
        if (currentToken.tipo == TipoToken.ASTERISCO) {
            eat(TipoToken.ASTERISCO);
        }
        selectList();
        eat(TipoToken.FROM);
        tableName();
    }

    private void selectList() {
        columnName();

        while (currentToken.tipo == TipoToken.COMMA) {
            eat(TipoToken.COMMA);
            columnName();
        }
    }

    private void columnName() {
        eat(TipoToken.IDENTIFICADOR);
    }

    private void tableName() {
        eat(TipoToken.IDENTIFICADOR);
    }

    private void eat(TipoToken expectedTokenType) {
        if (currentToken.tipo == expectedTokenType) {
            currentToken = scanner.getNextToken();
        } else {
            error("Se esperaba " + expectedTokenType.toString() + ". Encontrado: " + currentToken.tipo.toString());
        }
    }

    private void error(String errorMessage) {
        throw new RuntimeException("Error de análisis sintáctico: " + errorMessage);
    }
}
