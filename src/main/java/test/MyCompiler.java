package test;

import java.text.BreakIterator;
import java.util.List;

public class MyCompiler {

    //( 21 + 45) * 60 +350 /34;
    /**
     * stmt: exp
     * exp: id opt id  |  '(' exp  ')'
     * opt: '*' | '-' | '+' |  '/'
     * id: [0-9]
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
            if (token.tokenType == MyToken.EOI || token.tokenType==MyToken.SEMI) {
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
        if (curToken.tokenType == MyToken.NUM_OR_ID) {
            MyToken opt1 = getNextToken();
            matchOpt();
            MyToken id2 = getNextToken();
            matchId(MyToken.NUM_OR_ID);
        } else if (curToken.tokenType == MyToken.LP) {
            getNextToken();
            exp();
            getNextToken();
            matchId(MyToken.RP);
        }else if(curToken.tokenType==MyToken.SEMI){
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
        lex.setInput("( 21 + 45) * 60 +350 /34;");
        MyCompiler compiler=new MyCompiler(lex);
        compiler.compiler();

    }
}
