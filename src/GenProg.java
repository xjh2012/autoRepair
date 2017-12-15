import ags.SourceCompiler;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.text.edits.TextEditGroup;

import java.io.*;
import java.io.IOException;
import java.util.*;

import static org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition.freestanding;
import static org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition.leading;
import static org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition.trailing;

/**
 * Created by xjh on 2017/10/30.
 */
public class GenProg {

    //E:\autoRepair\
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

        String basicSourseFile = System.getProperty("user.dir") + File.separator;
        //模板程序
        String modelSourceFile = basicSourseFile + "modelProgram" + File.separator + "exception.c";
        CppASTTree cppASTTree = new CppASTTree(modelSourceFile);

        //需要纠正的程序
        String sourceFile = basicSourseFile + "testFiles" + File.separator + "exception1.c";
        cppASTTree.createNodes(sourceFile);
        //int simpleNodeNumber = cppASTTree.simpleNodeNumber;



        HashMap<IASTNode,Integer> nodeNumA = cppASTTree.nodeNumA;


        HashMap<IASTNode, IASTNode> modelParentNode = cppASTTree.modelParentNode;//模板程序的每个节点父节点


        int sizeOfA = cppASTTree.nodeNumA.size();
        int sizeOfB = cppASTTree.nodeNumB.size();

        //生成第一代变异体
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
        String testResult = basicSourseFile  + "testResult" + File.separator + "result.txt";
        PrintWriter pw = new PrintWriter(new FileWriter(new File(testResult)),true);
/*
        boolean findCorrectMutationFlag = false;
        int cnt = 0;//迭代次数,可调节
        //到找到正确程序为止
        while(cnt < 2){

            cnt++;
            //执行文件中所有变异体的循环
            int mutationNumber = 0;


            File[] mutationFiles = new File(basicSourseFile + "testFiles").listFiles();
            Map<String, Double> fitnessMap = new TreeMap<String, Double>();

            for(File file : mutationFiles){
                mutationNumber ++;
                //每个变异体执行所有测试用例，并比较输出结果，计算适应度
                int passedNumber = 0;
                int failedNumber = 0;
                System.out.println(file.getAbsolutePath());

                //模板程序和变异体语法树比较，返回相似节点数，每个节点对应父节点，每个节点的权重值
                String sourseProgram = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "exception" + mutationNumber + ".c";
                cppASTTree.createNodes(sourseProgram);
                int simpleNodeNumber = cppASTTree.simpleNodeNumber;

                try {
                    // read file content from file
                    StringBuffer sb= new StringBuffer("");

                    //读文件中的测试用例
                    FileReader inputReader = new FileReader(basicSourseFile + "input" + File.separator + "threeWordPlus_input.txt");
                    BufferedReader inputCase = new BufferedReader(inputReader);

                    //读文件中的期待输出
                    FileReader outputExceptionReader = new FileReader(basicSourseFile + "correctOutput" + File.separator + "compareOuput.txt");
                    BufferedReader outputCase = new BufferedReader(outputExceptionReader);

                    //输入的每行，期待输出的每行
                    String input_str = null;
                    String output_str = null;

                    //对每一个测试用例
                    while((input_str = inputCase.readLine()) != null) {
                        sb.append(input_str + "/n");

                        output_str = outputCase.readLine();
                        System.out.println(input_str +  " =  " + output_str);

                        SourceCompiler srcCompiler = new SourceCompiler(basicSourseFile  , "exception" + mutationNumber + ".c", "c", 1, pw, input_str, output_str);

                        if(srcCompiler.getSuccessfulFlag()){
                            passedNumber ++;
                        }else{
                            failedNumber ++;
                        }

                        System.out.println("successful test cases : " + srcCompiler.getSuccessfulFlag());

                    }

                    inputCase.close();
                    inputReader.close();

                    // write string to file
//            FileWriter writer = new FileWriter("c://test2.txt");
//            BufferedWriter bw = new BufferedWriter(writer);
//            bw.write(sb.toString());
//
//            bw.close();
//            writer.close();
                }
                catch(FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch(IOException e) {

                    e.printStackTrace();
                }
                System.out.println("passed : " + passedNumber + "  failed : " + failedNumber);

                if(failedNumber == 0){
                    System.out.println("find the correct mutation : " + file.getAbsolutePath());
                    findCorrectMutationFlag = true;
                    break;
                }

                //计算适应度，每个变异体一个适应度，按适应度排序，取前50%
                Double fitness = new Double(0);
                fitness = 1.0 *passedNumber + 2.0 * failedNumber + 0.1 * simpleNodeNumber;
                fitnessMap.put(file.getAbsolutePath(), fitness);

            }

            //跳出while循环,找到正确程序
            if(findCorrectMutationFlag){
                break;
            }

            //变异，交叉，生成新的变异体

            //这里将map.entrySet()转换成list
            List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(fitnessMap.entrySet());
            //然后通过比较器来实现排序
            Collections.sort(list,new Comparator<Map.Entry<String,Double>>() {
                //降序排序
                public int compare(Map.Entry<String, Double> o1,
                                   Map.Entry<String, Double> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }

            });

            HashMap<IASTNode,Integer> nodeNumB = cppASTTree.nodeNumB;
            HashMap<IASTNode, IASTNode> parentNode = cppASTTree.parentNode;//变异体的每个节点父节点
           // List<>
            int listLength = list.size();
            int listCnt = 0;
            for(Map.Entry<String,Double> mapping:list){
                listCnt ++;
                if(listCnt <= listLength/2){
                    //fitness前50%的每个变异体
                    //变异体与模板程序随机选4对修改节点，随机选修改方式，形成一个变异序列
                    //变异序列
                    for(int i = 0; i < 5 ; i ++){
                        int n = 0;
                        IASTNode modelProgramParent = T.getRandomKeyFromMap(nodeNumB);
                        IASTNode mutationProgramParent = T.getRandomKeyFromMap(nodeNumA);
                        while(n < 10 && modelProgramParent.getParent() != null && mutationProgramParent.getParent() != null && modelProgramParent.getParent().getClass().getSimpleName() != mutationProgramParent.getParent().getClass().getSimpleName()){
                            n++;
                            modelProgramParent = T.getRandomKeyFromMap(nodeNumB);
                            mutationProgramParent = T.getRandomKeyFromMap(nodeNumA);
                        }
                        int mutation = T.getRandomInt(2);
                        if(mutation == 0){
                            //insert(modelProgramParent,mutationProgramParent);
                        }else if(mutation == 1){
                            //delete(mutationProgramParent);
                        }else{
                            //replace(modelProgramParent,mutationProgramParent);
                        }
                    }


                    System.out.println(mapping.getKey()+" : "+mapping.getValue());
                }

            }

            


        }
*/
    }



}

