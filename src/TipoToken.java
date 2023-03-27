public enum TipoToken {
    // Palabras reservadas
    IF, ELSE, WHILE, FOR, BREAK, CONTINUE, RETURN, DO, TRUE, FALSE, PUBLIC,
    PRIVATE, PROTECTED, STATIC, FINAL, ABSTRACT, CLASS,
    INTERFACE, EXTENDS, IMPLEMENTS, THIS, SUPER, NEW, INSTANCEOF,
    PACKAGE, IMPORT, THROW, THROWS, TRY, CATCH, FINALLY, ENUM, ASSERT,

    // Tipos de datos
    INT, FLOAT, DOUBLE, CHAR, STRING, BOOLEAN, VOID,

    // Identificadores y literales
    IDENTIFIER, NUMBER,

    // Operadores aritméticos
    PLUS, MINUS, STAR, SLASH, MOD, DECREMENT, INCREMENT,

    // Operadores de comparación
    EQUAL, NOT_EQUAL, GREATER, LESS, GREATER_EQUAL, LESS_EQUAL, EQUAL_EQUAL,

    // Operadores lógicos
    AND, OR, NOT,

    // Operadores de asignación
    PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, SLASH_ASSIGN, MODULO_ASSIGN,

    // Otros símbolos
    LPAREN, RPAREN, LBRACE, RBRACE, LBRACKET, RBRACKET, SEMICOLON, COMMA, DOT, QUOTES,

    //Fin del documento
    EOF
}
