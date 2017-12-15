import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 2017/12/11.
 */
public class JavaVisitor extends ASTVisitor {

    public List<ASTNode> nodeList = new ArrayList<>();

    @Override
    public boolean visit(ExpressionStatement node) {

        System.out.println(node);
        nodeList.add(node);
        return true;
    }
}

