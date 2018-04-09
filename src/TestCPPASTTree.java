import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.parser.ParserLanguage;
import org.eclipse.core.runtime.CoreException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestCPPASTTree {
    //存1，0
    public static HashMap<IASTNode,Integer> nodeNumA = new HashMap<>();
    public HashMap<IASTNode,Integer> nodeNumB = new HashMap<>();

    public static HashMap<IASTNode, IASTNode> modelParentNode = new HashMap<>();//模板程序的每个节点父节点
    public HashMap<IASTNode, IASTNode> parentNode = new HashMap<>();//变异体的每个节点父节点


    private HashMap<HashMap<IASTNode, IASTNode>, Double> nodeSimilar= new HashMap<>();

    private int cnt = 0;
    public List<IASTNode> notSameNodeA = new ArrayList<>();
    public List<IASTNode> notSameNodeB = new ArrayList<>();

    int simpleNodeNumber = 0;

    static IASTTranslationUnit tu = null;

    //create childNode, traverse the tree.
    private static void recordParents(IASTNode node, HashMap<IASTNode, Integer> nodeNum, HashMap<IASTNode, IASTNode> parentsNode) {
        //System.out.println(node.getRawSignature());
        //标号节点，按照先序遍历顺序
        nodeNum.put(node, 0);
        for (IASTNode child : node.getChildren()) {
            recordParents(child , nodeNum, parentsNode);
            parentsNode.put(child, node);//记录每个节点的父节点
             System.out.println( child.getClass().getSimpleName());
        }
    }

    public static void main(String[] args){
       // System.out.println( System.getProperty("user.dir") + File.separator + "testFiles" + File.separator);
        //模板程序语法分析
        //Create the nodes.语法树根节点
        String basicSourseFile = System.getProperty("user.dir") + File.separator;
        //模板程序
        String modelSourceFile = basicSourseFile + "modelProgram" + File.separator + "exception.c";

        tu = CppParser.parse(modelSourceFile, ParserLanguage.C, false);
        recordParents(tu, nodeNumA, modelParentNode);
        // new CppASTTree();
    }
}
