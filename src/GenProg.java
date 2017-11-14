import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.text.edits.TextEditGroup;

import static org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition.freestanding;
import static org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition.leading;
import static org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition.trailing;

/**
 * Created by xjh on 2017/10/30.
 */
public class GenProg extends CppASTTree{

    public GenProg(){

    }

    private void genProg(){

    }


    private static void replace(IASTNode A, IASTNode B){
//        IASTNode newMutation = B.getParent().copy();
//        newMutation.setParent(A.getParent());


//        CppASTVisitor visitor = new CppASTVisitor();
//        ast.accept(visitor);
//        System.out.println("The RawSignature is: " +
//                visitor.node.getRawSignature());
//        System.out.println("This AST contains node: "
//                +ast.contains(visitor.node));

        ASTRewrite rewrite = ASTRewrite.create(B.getTranslationUnit());
        rewrite.replace(B, A,null);               // here the Exception occures
      // Change change = rewrite.rewriteAST();
        ASTRewrite.CommentPosition pos = leading;
        System.out.println(rewrite);
    }

    private static void delete(IASTNode A){
        IASTNode iastNode = A.copy();
        iastNode = null;
        iastNode.setParent(A.getParent());
       // System.out.println(iastNode.getTranslationUnit().getRawSignature());
    }

    private static void insert(IASTNode A, IASTNode B){
        B.setParent(A.getParent());
       // System.out.println(B.getTranslationUnit().getRawSignature());
    }

    public static void main(String[] args) {
        CppASTTree cppASTTree = new CppASTTree();

        int sizeOfA = cppASTTree.notSameNodeA.size();
        int sizeOfB = cppASTTree.notSameNodeB.size();

        for(int i = 0; i < sizeOfA; i ++){
            //System.out.println(cppASTTree.notSameNodeA.get(i).getRawSignature());

        }
        //System.out.println();
        for(int i = 0; i < sizeOfB; i ++){
           // System.out.println(cppASTTree.notSameNodeB.get(i).getRawSignature());
        }

        replace(cppASTTree.notSameNodeA.get(0),cppASTTree.notSameNodeB.get(0));
    }
}
