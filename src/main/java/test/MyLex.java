package test;

public class MyLex {

    public static final int EOI = 0;
    public static final int SEMI = 1; //分号
    public static final int PLUS = 2; //+
    public static final int SUB = 3; //-
    public static final int TIMES = 4; // *
    public static final int DIV = 5; // /
    public static final int LP = 6; // (
    public static final int RP = 7; //  )
    public static final int NUM_OR_ID = 8;// 数字或者id


    private String buffer;
    private int curIndex = -1;
    private int eIndex = -1;
    private String yytext;
    private int tokenType = -1;


    public void runLexer() {
        while (!match(EOI)) {
            advance();
            System.out.println("Token: " + token() + " ,Symbol: " + yytext);
        }
    }

    private String token() {
        return tokenType >= 0 ? tokenType + "" : "";
    }

    public boolean match(int b) {
        return tokenType == b;
    }

    private char getNextChar() {
        if (curIndex < eIndex - 1) {
            curIndex++;
            return buffer.charAt(curIndex);
        }
        curIndex = eIndex;
        return '\0';
    }

    public void advance() {

        while (true) {
            char c = getNextChar();
            if (c == '\0') {
                tokenType = EOI;
                yytext = "EOI";
                return ;
            } else if (c == ';') {
                tokenType = SEMI;
                yytext = ";";
                break;
            } else if (c == '+') {
                tokenType = PLUS;
                yytext = "+";
                break;
            } else if (c == '-') {
                tokenType = SUB;
                yytext = "-";
                break;
            } else if (c == '*') {
                tokenType = TIMES;
                yytext = "*";
                break;
            } else if (c == '/') {
                tokenType = DIV;
                yytext = "/";
                break;
            } else if (c == '(') {
                tokenType = LP;
                yytext = "(";
                break;
            } else if (c == ')') {
                tokenType = RP;
                yytext = ")";
                break;
            } else if (c == ' ' || c == '\r' || c == '\n') {
                tokenType = -1;
                yytext = "";
            } else {
                yytext = "";
                tokenType = NUM_OR_ID;
                for (int i = curIndex; i < eIndex; i++) {
                    c = buffer.charAt(i);
                    if (isNumOrAlpha(c)) {
                        curIndex = i;
                        yytext = yytext + c;
                    } else {
                        break;
                    }
                }
                break;
            }
        }
    }

    private boolean isNumOrAlpha(char c) {
        return Character.isDigit(c) || Character.isAlphabetic(c);
    }

    private void setInput(String s) {
        this.buffer = s;
        this.curIndex = -1;
        this.eIndex = s.length();
    }

    public static void main(String[] args) {
        MyLex lex = new MyLex();
        lex.setInput("( 21 + 45) * 60 +350 /34;");
        lex.runLexer();
    }

}
