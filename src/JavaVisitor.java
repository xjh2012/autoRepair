
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 2017/12/11.
 */
public class JavaVisitor extends ASTVisitor {

    public List<ASTNode> nodeList = new ArrayList<>();
    public ASTNode mainStatement;

    List<Assignment> assignment = new ArrayList<>();
    List<VariableDeclarationStatement> vds = new ArrayList<>();
    List<ExpressionStatement> es = new ArrayList<>();

    @Override
    public boolean visit(VariableDeclarationStatement node) {
        vds.add(node);
        return super.visit(node);
    }

    @Override
    public boolean visit(Assignment node) {
        assignment.add(node);
       // System.out.println(node);
        return super.visit(node);
    }


    @Override
    public boolean visit(ExpressionStatement node) {

        //System.out.println(node);
        nodeList.add(node);
        es.add(node);
        return true;
    }

    public boolean visit(IfStatement node) {

        //System.out.println(node);
        nodeList.add(node);
        return true;
    }

    public boolean visit(WhileStatement node) {

        //System.out.println(node);
        //nodeList.add(node);
        return true;
    }

    public boolean visit(ForStatement node) {

        //System.out.println(node);
        nodeList.add(node);
        return true;
    }

    public boolean visit(ReturnStatement node) {

        //System.out.println(node);
        nodeList.add(node);
        return true;
    }

    public boolean visit(MethodDeclaration node) {

       // System.out.println(node.getName());
        if(node.getName().toString().equals("main")){
            //System.out.println(node.getBody().getParent() );
            mainStatement = node.getBody().getParent();
        }
        //nodeList.add(node);
        return true;
    }

    public List<VariableDeclarationStatement> getVDS() {
        return vds;
    }

    public List<ExpressionStatement> getEs() {
        return es;
    }

    public List<Assignment> getAssignment() {
        return assignment;
    }

//    public boolean visit(Block node) {
//
//        System.out.println(node);
//        nodeList.add(node);
//        return true;
//    }
}

