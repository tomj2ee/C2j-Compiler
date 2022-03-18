package test;

import java.util.List;

public class MyCompiler {

    //( 21 + 45) * 60 +350 /34;
    /**
     * stmt: exp
     * exp -> "(" list ")"
     * exp -> exp  opt  exp
     * opt -> '*' | '-' | '+' |  '/'
     * exp -> number
     */

    private MyLex myLex;
    List<MyToken> allToken;
    private MyToken curToken;
    private int curIndex = -1;

    public MyCompiler(MyLex myLex) {
        this.myLex = myLex;
    }

    public void compiler() {
        allToken = myLex.runLexer();
        stmt();
    }

    private void stmt() {
        System.out.println("开始分析stmt ... ");
        for (; ; ) {
            MyToken token = getNextToken();
            if (token.tokenType == MyToken.EOI || token.tokenType == MyToken.SEMI) {
                break;
            }
            exp();
        }
    }

    private MyToken getNextToken() {
        if (curIndex < allToken.size() - 1) {
            curIndex++;
            curToken = allToken.get(curIndex);
            System.out.println(curToken);
            return curToken;
        }
        MyToken eof = new MyToken();
        eof.tokenType = MyToken.EOI;
        return eof;
    }

    private void exp() {
        if (curToken.tokenType == MyToken.LP) {
            getNextToken();
            exp();
            getNextToken();
            matchId(MyToken.RP);
        } else if (curToken.tokenType == MyToken.NUM_OR_ID) {
            MyToken opt1 = getNextToken();
            if (opt1.tokenType == MyToken.SEMI) {
                return;
            }
            matchOpt();
            MyToken id2 = getNextToken();
            matchId(MyToken.NUM_OR_ID);
        } else if (curToken.tokenType == MyToken.PLUS
                || curToken.tokenType == MyToken.DIV
                || curToken.tokenType == MyToken.TIMES
                || curToken.tokenType == MyToken.SUB

        ) {
            getNextToken();
            exp();
        } else {
            throw new RuntimeException("不匹配的格式>" + curToken);
        }

    }

    private boolean matchOpt() {
        int tokenType = curToken.tokenType;
        boolean ok = tokenType == MyToken.PLUS ||
                tokenType == MyToken.SUB ||
                tokenType == MyToken.TIMES ||
                tokenType == MyToken.DIV;
        if (!ok) {
            throw new RuntimeException("不匹配的操作符号>" + tokenType);
        }
        return true;
    }

    private boolean matchId(int tokenType) {
        boolean ok = curToken.tokenType == tokenType;
        if (!ok) {
            throw new RuntimeException("不配置的类型>" + tokenType);
        }
        return true;
    }

    public static void main(String[] args) {
        MyLex lex = new MyLex();
        lex.setInput(" (12+4)+ ( 21 + 45)( 60 +350 )/( 34 +45)");
        MyCompiler compiler = new MyCompiler(lex);
        compiler.compiler();

    }
}
