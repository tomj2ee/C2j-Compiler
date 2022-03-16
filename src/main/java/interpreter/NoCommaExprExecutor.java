package interpreter;

import ast.AstNode;
import ast.NodeKey;
import parse.SyntaxProductionInit;

/**
 * @author dejavudwh isHudw
 */

public class NoCommaExprExecutor extends BaseExecutor {
    ExecutorFactory factory = ExecutorFactory.getInstance();

    @Override
    public Object execute(AstNode root) {
        executeChildren(root);

        int production = (int) root.getAttribute(NodeKey.PRODUCTION);
        Object value;
        AstNode child;
        switch (production) {
            case SyntaxProductionInit.Binary_TO_NoCommaExpr:
                child = root.getChildren().get(0);
                copyChild(root, child);
                break;

            case SyntaxProductionInit.NoCommaExpr_Equal_NoCommaExpr_TO_NoCommaExpr:
                child = root.getChildren().get(0);
                String t = (String) child.getAttribute(NodeKey.TEXT);
                ValueSetter setter;
                setter = (ValueSetter) child.getAttribute(NodeKey.SYMBOL);
                child = root.getChildren().get(1);
                value = child.getAttribute(NodeKey.VALUE);

                try {
                    setter.setValue(value);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Runtime Error: Assign Value Error");
                }

                child = root.getChildren().get(0);
                child.setAttribute(NodeKey.VALUE, value);
                copyChild(root, root.getChildren().get(0));
                break;

            default:
                break;
        }


        return root;
    }
}
