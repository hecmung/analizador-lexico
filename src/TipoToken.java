public enum TipoToken {
    // Palabras reservadas
    IF, WHILE, FOR, RETURN, TRUE, FALSE, CLASS, THIS, SUPER, ELSE,
    FUN, NULL, PRINT, VAR,

    // Identificadores y literales
    IDENTIFIER, NUMBER, STRING,

    // Operadores aritméticos
    PLUS, MINUS, STAR, SLASH,

    // Operadores de comparación
    EQUAL, NOT_EQUAL, GREATER, LESS, GREATER_EQUAL, LESS_EQUAL, EQUAL_EQUAL,

    // Operadores lógicos
    AND, OR, NOT,

    // Otros símbolos
    LPAREN, RPAREN, LBRACE, RBRACE, SEMICOLON, COMMA, DOT,

    //Fin del documento
    EOF
}
