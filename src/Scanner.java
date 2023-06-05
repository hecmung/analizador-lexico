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
        keyWords.put("SELECT", TipoToken.SELECT);
        keyWords.put("FROM", TipoToken.FROM);
        keyWords.put("DISTINCT", TipoToken.DISTINCT);
        keyWords.put("COMMA", TipoToken.COMMA);
        keyWords.put("PUNTO", TipoToken.PUNTO);
        keyWords.put("ASTERISCO", TipoToken.ASTERISCO);
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
        int estado = 0;
        char caracter = 0;
        String lexema = "";
        int inicioLexema = 0;

        for(int i=0; i<source.length(); i++){
            caracter = source.charAt(i);

            switch (estado){
                case 0:
                    if(caracter == '*'){
                        tokens.add(new Token(TipoToken.ASTERISCO, "*","", i + 1));
                    }
                    else if(caracter == ','){
                        tokens.add(new Token(TipoToken.COMMA, ",", "", i + 1));
                    }
                    else if(caracter == '.'){
                        tokens.add(new Token(TipoToken.PUNTO, ".", "", i + 1));
                    }
                    else if(Character.isAlphabetic(caracter)){
                        estado = 1;
                        lexema = lexema + caracter;
                        inicioLexema = i;
                    }
                    break;

                case 1:
                    if(Character.isAlphabetic(caracter) || Character.isDigit(caracter) ){
                        lexema = lexema + caracter;
                    }
                    else{
                        TipoToken tt = keyWords.get(lexema.toUpperCase());
                        if(tt == null){
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, lexema, "", inicioLexema + 1));
                        }
                        else{
                            tokens.add(new Token(tt, lexema, "", inicioLexema + 1));
                        }

                        estado = 0;
                        i--;
                        lexema = "";
                        inicioLexema = 0;
                    }
                    break;
            }
        }
        tokens.add(new Token(TipoToken.EOF, "", "", source.length()));
        return tokens;
    }

    private char nextChar() {
        if (position + 1 >= source.length()) {
            return '\0';
        }

        return source.charAt(position + 1);
    }
}