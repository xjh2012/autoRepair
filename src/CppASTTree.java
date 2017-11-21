import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.parser.ParserLanguage;
import org.eclipse.core.runtime.CoreException;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jing on 10/26/16.
 * 显示指定代码的AST
 */
public class CppASTTree {

    //存1，0
    private HashMap<IASTNode,Integer> nodeNumA = new HashMap<>();
    private HashMap<IASTNode,Integer> nodeNumB = new HashMap<>();


    private HashMap<HashMap<IASTNode, IASTNode>, Double> nodeSimilar= new HashMap<>();

    private int cnt = 0;
    public List<IASTNode> notSameNodeA = new ArrayList<>();
    public List<IASTNode> notSameNodeB = new ArrayList<>();

    public CppASTTree() {
        System.out.println( System.getProperty("user.dir") + File.separator + "testFiles" + File.separator);

        //Create the nodes.语法树根节点
        String sourceFile = System.getProperty("user.dir") + File.separator+ File.separator + "testFiles" + File.separator + "exception.cpp";
        String sourceFileTmp = System.getProperty("user.dir") + File.separator+ File.separator + "testFiles" + File.separator + "exception1.cpp";

        IASTTranslationUnit tu = CppParser.parse(sourceFile, ParserLanguage.CPP, false);
        IASTTranslationUnit tuTmp = CppParser.parse(sourceFileTmp, ParserLanguage.CPP, false);

        //遍历语法树，添加所有子节点进top
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("TU");

        createNodes(tu, nodeNumA);
        createNodes(tuTmp, nodeNumB);
        //遍历两棵语法树,判断不同节点
       // matchNodes(tu, tuTmp);

        System.out.println("simpleTreeMatching : " + simpleTreeMatching(tu, tuTmp));
        //System.out.println("A Tree : ");
       printNodes(tu, notSameNodeA, nodeNumA);
       // System.out.println("B Tree : ");
        printNodes(tuTmp, notSameNodeB, nodeNumB);
        //Create a tree that allows one selection at a time.

    }

    private class NodeInfo {
        IASTNode node;

        NodeInfo(IASTNode node) {
            this.node = node;
        }

        public String toString() {
            return node.getClass().getSimpleName();
        }
    }

    //create childNode, traverse the tree.
    private void createNodes(IASTNode node , HashMap<IASTNode, Integer> nodeNum) {
        //System.out.println(node.getRawSignature());
        //标号节点，按照先序遍历顺序
        nodeNum.put(node, 0);
        for (IASTNode child : node.getChildren()) {
            createNodes(child , nodeNum);
        }
    }

    //print childNode, traverse the tree.
    private void printNodes(IASTNode node, List<IASTNode> notSameNode, HashMap<IASTNode, Integer> nodeNum) {
        //System.out.println(node.getRawSignature());
        //标号节点，按照先序遍历顺序
        cnt ++;

        //不是叶子节点的不同节点
        if(nodeNum.get(node) == 0 && node.getChildren().length != 0 && !node.getChildren()[0].getClass().getSimpleName().equals("CPPASTName")){
          //  if(node.getChildren()[0].getRawSignature())
            notSameNode.add(node);
            //System.out.println("print not same : " + node.getRawSignature());

        }

        for (IASTNode child : node.getChildren()) {
            printNodes(child, notSameNode, nodeNum);
        }
    }

    /**
     * 简单树匹配算法实现;
     * @param A
     * @param B
     * @return
     */
    public int simpleTreeMatching(IASTNode A, IASTNode B) {

        //结构名不同则不匹配，或不是对应的结构名，如for和while
        String aTag = A.getClass().getSimpleName();
        String bTag = B.getClass().getSimpleName();

        //表达式要判断内容是否相等,表达式内容不相等则节点不匹配
        if(aTag.endsWith("Expression") && bTag.endsWith("Expression")){
            if(!A.getRawSignature().equals(B.getRawSignature())){
                return 0;
            }
        }
        //非表达式则结构不相等则不匹配，结构相等则可以当作相同
        if (!aTag.equals(bTag)) {
            return 0;
        }
        // 只有标签节点，不包含文本节点

        //叶子节点不算不同节点
        int aChildNum = A.getChildren().length;
        int bChildNum = B.getChildren().length;
        if (aChildNum == 0 || bChildNum == 0) {
            nodeNumA.put(A, 1);
            nodeNumB.put(B, 1);
            return 1;
        }

        int[][] m = new int[aChildNum + 1][bChildNum + 1];
        int[][] w = new int[aChildNum + 1][bChildNum + 1];

        // 当A没有子树时，只有０个匹配
        for(int i = 0; i < aChildNum + 1; i++) {
            m[i][0] = 0;
        }
        //　当B没有子树时，只有０个匹配
        for(int j = 0; j < bChildNum + 1; j++) {
            m[0][j] = 0;
        }

        for(int i = 1; i < aChildNum + 1; i++) {
            for(int j = 1; j < bChildNum + 1; j++) {
                //两棵树最大公共子节点数量
                w[i][j] = simpleTreeMatching(A.getChildren()[i - 1], B.getChildren()[j - 1]);

                //两个节点相似度
                HashMap<IASTNode, IASTNode> nodeDictionary = new HashMap<>();
                nodeDictionary.put(A.getChildren()[i - 1], B.getChildren()[j - 1]);
                nodeSimilar.put(nodeDictionary,
                        (double)w[i][j]/((A.getChildren()[i - 1].getChildren().length + B.getChildren()[j - 1].getChildren().length)/2));

                m[i][j] = Math.max(Math.max(m[i][j-1], m[i - 1][j]),
                        m[i-1][j-1] + w[i][j]);

            }
        }
        if( m[aChildNum-1][bChildNum-1] + w[aChildNum][bChildNum] > Math.max(m[aChildNum][bChildNum-1], m[aChildNum - 1][bChildNum])){
            //System.out.println("corresponding : " + A.getRawSignature());
            nodeNumA.put(A, 1);
            nodeNumB.put(B, 1);
        }
        //A和B的根节点类型相同，加1
        int weight = 0;
        if(A.getParent() != null && B.getParent() != null && A.getParent().getClass().getSimpleName().equals(B.getParent().getClass().getSimpleName())){
            weight = m[aChildNum][bChildNum] + 1;
        }else{
            weight = m[aChildNum][bChildNum];
        }

        //A和Ｂ内容完全相同，加１
        if(A.getRawSignature().equals(B.getRawSignature())){
            return weight + 1;
        }else{
            return weight;
        }
    }

    //create childNode, traverse the tree.
    private void matchNodes(IASTNode node, IASTNode nodeTmp) {
        new NodeInfo(node);

        //表达式级别的节点不同
        if(!node.getClass().getSimpleName().equals("CPPASTTranslationUnit")
                && !node.getClass().getSimpleName().equals("CPPASTFunctionDefinition")
                && !node.getClass().getSimpleName().endsWith("Statement")
                &&!node.getRawSignature().equals(nodeTmp.getRawSignature()) ){

            //输出不同节点
            System.out.println("not same : " + node.getClass().getSimpleName());
            System.out.println();
            //新建节点复制错误程序节点,接到正确程序上,
            IASTNode newNode = node.copy();
            newNode.setParent(nodeTmp.getParent());
            //System.out.println("new parents : " + newNode.getTranslationUnit().getRawSignature());

        }

        for(int i = 0; i < node.getChildren().length; i ++){
            matchNodes(node.getChildren()[i], nodeTmp.getChildren()[i]);
        }
    }




    public static void main(String[] args) throws CoreException, IOException, InterruptedException {
        new CppASTTree();
    }
}
