package codegen;

import ast.AstNode;
import ast.NodeKey;
import parse.SyntaxProductionInit;

import java.util.ArrayList;

/**
 * @author dejavudwh isHudw
 */

public class ArgsGenerate extends BaseGenerate {

    @Override
    public Object generate(AstNode root) {
        int production = (Integer) root.getAttribute(NodeKey.PRODUCTION);
        ArrayList<Object> argList = new ArrayList<>();
        ArrayList<Object> symList = new ArrayList<>();
        AstNode child;

        switch (production) {
            case SyntaxProductionInit.NoCommaExpr_TO_Args:
                child = (AstNode) generateChild(root, 0);
                Object objVal = child.getAttribute(NodeKey.VALUE);
                argList.add(objVal);
                objVal = child.getAttribute(NodeKey.SYMBOL);
                symList.add(objVal);

                break;

            case SyntaxProductionInit.NoCommaExpr_Comma_Args_TO_Args:
                child = generateChild(root, 0);
                objVal = child.getAttribute(NodeKey.VALUE);
                argList.add(objVal);
                objVal = child.getAttribute(NodeKey.SYMBOL);
                symList.add(objVal);

                child = (AstNode) generateChild(root, 1);
                ArrayList<Object> list = (ArrayList<Object>) child.getAttribute(NodeKey.VALUE);
                argList.addAll(list);
                list = (ArrayList<Object>) child.getAttribute(NodeKey.SYMBOL);
                for (int i = 0; i < list.size(); i++) {
                    symList.add(list.get(i));
                }
                break;

            default:
                break;
        }

        root.setAttribute(NodeKey.VALUE, argList);
        root.setAttribute(NodeKey.SYMBOL, symList);

        return root;
    }

}
