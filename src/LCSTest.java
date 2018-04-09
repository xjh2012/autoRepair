/**
 * Created by xjh on 2017/12/18.
 */

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 获取两个字符串的最长公共子序列
 * @author kpp
 *
 */
public class LCSTest {
    private static String lcs = "";
    public static HashMap<ASTNode, Integer> weightOfNode = new HashMap<>();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //保留空字符串是为了getLength()方法的完整性也可以不保留
        //但是在getLength()方法里面必须额外的初始化c[][]第一个行第一列
        String firstStr = "ABCBDAB";
        String secondStr = "BDCABA";

        //E:\autoRepair\
        String basicSourceFile = System.getProperty("user.dir") + File.separator;

        //模板程序,语法分析
        String modelSourceFile = basicSourceFile + "JavaModelProgram" + File.separator + "threeNumbersPlus.java";
        File file = new File(modelSourceFile);//模板程序路径
        JavaParser javaParser = new JavaParser();//模板程序的语法树
        String fileString = javaParser.getCode(file);//模板程序内容

        CompilationUnit modelUnit = javaParser.getCompilationUnit(fileString);
        JavaVisitor javaVisitor = new JavaVisitor();
        modelUnit.accept(javaVisitor);
        List<ASTNode> nodeList = javaVisitor.nodeList;//模板的节点列表

        for(ASTNode astNode : nodeList){
            weightOfNode.put(astNode, 2);
        }

        //需要纠正的程序JavaParser
        String sourceFile = basicSourceFile + "JavaTestFiles" + File.separator + "threeNumbersPlus.java";
        File sourceFillePath = new File(sourceFile);//源程序路径
        JavaParser sourceJavaParser = new JavaParser();//模板程序的语法树
        fileString = javaParser.getCode(sourceFillePath);//模板程序内容

        CompilationUnit sourceUnit = sourceJavaParser.getCompilationUnit(fileString);
        JavaVisitor sourceJavaVisitor = new JavaVisitor();
        sourceUnit.accept(sourceJavaVisitor);
        List<ASTNode> sourceNodeList = sourceJavaVisitor.nodeList;//错误源程序的节点列表

        /*String[] x = new String[firstStr.length()+1];
        String[] y = new String[secondStr.length()+1];*/

        String[] x = strToArray(firstStr);
        String[] y = strToArray(secondStr);
        /*String[] x = {"", "A", "B", "C", "B", "D", "A", "B"};
        String[] y = {"", "B", "D", "C", "A", "B", "A"}; */

        int[][] b = getSearchRoad(nodeList, sourceNodeList);

        Display(b, nodeList, nodeList.size() - 1, sourceNodeList.size() - 1);

//

        //System.out.println("lcs:"+lcs);
    }

    public static void LCS(List<ASTNode> nodeList, List<ASTNode> sourceNodeList){


        int[][] b = getSearchRoad(nodeList, sourceNodeList);

        Display(b, nodeList, nodeList.size() - 1, sourceNodeList.size() - 1);

    }
    /**
     * 字符串转数组，并且数组的第0个元素为""
     * @param str
     * @return
     */
    private static String[] strToArray(String str){

        String[] strArray = new String[str.length()+1];

        strArray[0] = "";

        for(int i = 1; i < strArray.length;i++){
            strArray[i] = ""+str.charAt(i-1);
        }
        return strArray;
    }


    /**
     * 获得LCS矩阵的路径走向
     * @param x 第一个数组
     * @param y 第二个数组
     * @return 返回一个记录决定搜索的方向的数组
     */
    public static int[][] getSearchRoad(List<ASTNode> x, List<ASTNode> y)
    {
        int[][] b = new int[x.size()][y.size()];
        int[][] c = new int[x.size()][y.size()];

        for(int j = 0; j < y.size(); j ++){
            c[0][j] = 0;
        }
        for(int i = 0; i < x.size(); i ++){
            c[i][0] = 0;
        }

        for(int i = 1; i < x.size(); i ++)
        {
            for(int j = 1; j < y.size(); j ++)
            {
                //对应第一个性质
                //x[i].equals(y[j])是指x[i]与y[j]的值相同
                if( x.get(i).toString().equals(y.get(j).toString()))
                {
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = 1;
                }
                //x[i] == y[j]是指x[i]与y[j]的地址相同
                /*if( x[i] == y[j])
                {
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = 1;
                }*/
                //对应第二或者第三个性质
                else if(c[i-1][j] >= c[i][j-1])
                {
                    c[i][j] = c[i-1][j];
                    b[i][j] = 0;
                }
                //对应第二或者第三个性质
                else
                {
                    c[i][j] = c[i][j-1];
                    b[i][j] = -1;
                }
            }
        }

        return b;
    }
    /**
     * 自矩阵右下至左上回溯，根据搜索路径获取LCS
     * @param b 搜索路径数组
     * @param x 第一个数组
     * @param i
     * @param j
     */
    public static void Display(int[][] b, List<ASTNode> x, int i, int j)
    {

        if(i == 0 || j == 0)
            return ;

        if(b[i][j] == 1)
        {
            Display(b, x, i-1, j-1);
            weightOfNode.put(x.get(i),1);
           // lcs += x.get(i);
        }
        else if(b[i][j] == 0)
        {
            Display(b, x, i-1, j);
        }
        else if(b[i][j] == -1)
        {
            Display(b, x, i, j-1);
        }
    }

}