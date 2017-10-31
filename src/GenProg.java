import org.eclipse.cdt.core.dom.ast.IASTNode;

/**
 * Created by xjh on 2017/10/30.
 */
public class GenProg extends CppASTTree{

    public GenProg(){

    }

    private void genProg(){

    }


    private void replace(IASTNode A, IASTNode B){
        A.setParent(B.getParent());
       // System.out.println(A.getTranslationUnit().getRawSignature());
    }

    private void delete(IASTNode A){
        IASTNode iastNode = A.copy();
        iastNode = null;
        iastNode.setParent(A.getParent());
       // System.out.println(iastNode.getTranslationUnit().getRawSignature());
    }

    private void insert(IASTNode A, IASTNode B){
        B.setParent(A.getParent());
       // System.out.println(B.getTranslationUnit().getRawSignature());
    }

    public static void main(String[] args) {
        CppASTTree cppASTTree = new CppASTTree();

        for(int i = 0; i < cppASTTree.A.size(); i ++){
            System.out.println(cppASTTree.A.get(i).getRawSignature());
        }




    }
}
