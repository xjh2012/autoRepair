import ags.SourceCompiler;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.text.edits.TextEditGroup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition.freestanding;
import static org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition.leading;
import static org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition.trailing;

/**
 * Created by xjh on 2017/10/30.
 */
public class GenProg extends CppASTTree{

    private static String sourceFile = System.getProperty("user.dir") + File.separator;


    public GenProg(){

    }

    private void genProg(){

    }


    private static void replace(IASTNode A, IASTNode B) throws CoreException {
        IASTNode newMutation = B.getParent().copy();
        newMutation.setParent(A.getParent());


//        CppASTVisitor visitor = new CppASTVisitor();
//        ast.accept(visitor);
//        System.out.println("The RawSignature is: " +
//                visitor.node.getRawSignature());
//        System.out.println("This AST contains node: "
//                +ast.contains(visitor.node));

//        ASTRewrite rewrite = ASTRewrite.create(B.getTranslationUnit());
//        rewrite.replace(B, A,null);               // here the Exception occures
//        Change change = rewrite.rewriteAST();
//        ASTRewrite.CommentPosition pos = leading;
        System.out.println(newMutation.getTranslationUnit().getRawSignature());
        //输入到文件中，变异体
        String mutationFile = sourceFile  + "mutation" + File.separator + "exception.c";
        T.writeFile(mutationFile, newMutation.getTranslationUnit().getRawSignature());
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

    public static void main(String[] args) throws CoreException, IOException, InterruptedException {
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

        //形成变异体
//        replace(cppASTTree.notSameNodeA.get(0),cppASTTree.notSameNodeB.get(0));

        //编译程序exception.c
        String testResult = sourceFile  + "testResult" + File.separator + "result.txt";
        PrintWriter pw = new PrintWriter(new FileWriter(new File(testResult)),true);

        //输入文件流，解析每行，for循环每行测试用例，
        //程序输入和输出
        String inputString[]= new String[10];
        String outputString[]= new String[10];
        String inputTestCase = "1 2 3";
        String outputException = "6";
        SourceCompiler srcCompiler = new SourceCompiler(sourceFile + "testResult"  , "exception.c", "c", 1, pw, inputTestCase, outputException);

        System.out.println("successful test cases : " + srcCompiler.getSuccessfulFlag());
        //srcCompiler.compile();






    }
}
