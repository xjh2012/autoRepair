import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;

import util.DynamicCompileTest;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

import static thredds.featurecollection.FeatureCollectionConfig.PartitionType.file;
import static util.CodeFileReader.getCode;

/**
 * Created by xjh on 2017/12/11.
 */
public class JavaGenProg {
    //E:\autoRepair\
    private static String sourceFile = System.getProperty("user.dir") + File.separator;
    private static void replace(IASTNode A, IASTNode B) throws CoreException {

        //输入到文件中，变异体
        String mutationFile = sourceFile  + "mutation" + File.separator + "exception.c";
        T.writeFile(mutationFile, null);
    }

    private static void delete(IASTNode A){

        // System.out.println(iastNode.getTranslationUnit().getRawSignature());
    }

    private static void insert(IASTNode A, IASTNode B){

        // System.out.println(B.getTranslationUnit().getRawSignature());
    }

    public static void main(String[] args) throws CoreException, IOException, InterruptedException, BadLocationException {

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

        //需要纠正的程序JavaParser
        String sourceFile = basicSourceFile + "JavaTestFiles" + File.separator + "threeNumbersPlus.java";
        File sourceFilePath = new File(sourceFile);//源程序路径
        JavaParser sourceJavaParser = new JavaParser();//模板程序的语法树
        fileString = javaParser.getCode(sourceFilePath);//模板程序内容

        CompilationUnit sourceUnit = sourceJavaParser.getCompilationUnit(fileString);
        JavaVisitor sourceJavaVisitor = new JavaVisitor();
        sourceUnit.accept(sourceJavaVisitor);
        List<ASTNode> sourceNodeList = sourceJavaVisitor.nodeList;//错误源程序的节点列表

        LCSTest lcsTest = new LCSTest();
        lcsTest.LCS(nodeList,sourceNodeList);


        int sizeOfModel = nodeList.size();
        int sizeOfSource = sourceNodeList.size();

        //存储变异序列，几次变异
        //LinkedList<ArrayList<Integer>> mutationSeq = new LinkedList<>();

        //存储种群，种群用n个变异序列表示，二维数组，三个数一个操作
        ArrayList<ArrayList<Integer>> group = new ArrayList<>();

        //备用种群
        ArrayList<ArrayList<Integer>> groupTemp = new ArrayList<>();

        int cnt = 0;
        //生成第一代变异体,model中每个节点，对source中每个节点replace
        for(int i = 0; i < sizeOfModel; i ++){
            for(int j = 0; j < sizeOfSource; j ++){
                Document document;
                document = new Document(fileString);

                ASTRewrite rewriter = ASTRewrite.create(sourceUnit.getAST());
                AST ast = sourceUnit.getAST();
                TextEditGroup textEdits = new TextEditGroup("test");

                TextElement textElement = ast.newTextElement();
                textElement.setText(nodeList.get(j).toString());

             //   System.out.println(textElement.getText());

//                ast.newName(sourceNodeList.get(j).toString());
               // rewriter.remove(sourceNodeList.get(i), textEdits);
               // System.out.println(sourceNodeList.get(i).toString());
               // System.out.println("B : \n" + rewriter.createStringPlaceholder(sourceNodeList.get(i).toString(), sourceNodeList.get(i).getNodeType()));


                    //两行不是完全相同的节点，父节点结构相同,才可以替换
                    if(!sourceNodeList.get(j).toString().equals(nodeList.get(i).toString())
                            && sourceNodeList.get(j).getParent().getNodeType() == nodeList.get(i).getParent().getNodeType()){
                        cnt ++;
                        // nodeList.get(i).setProperty(sourceNodeList.get(j).toString(),null);
                        System.out.println(cnt + "\nA : \n" + nodeList.get(i).toString() + "\n" + sourceNodeList.get(j).toString() + "\n");
                        rewriter.replace(sourceNodeList.get(i), textElement,textEdits);

                        //存储变异过程<0,2,3>代表replace A2和B3节点，<1,4,0>代表delete A4节点
                        ArrayList<Integer> mutationAct = new ArrayList<>();

                        mutationAct.add(0);
                        mutationAct.add(j);//源程序节点
                        mutationAct.add(i);//模板程序节点

                        group.add(mutationAct);//种群中存储一个变异序列

                        TextEdit edits = rewriter.rewriteAST(document, null);
                        edits.apply(document);

                        //输入到文件中，变异体
                        String mutationFile = basicSourceFile  + "JavaMutation" + File.separator + "TNPMutation" + cnt + ".java";

                        T.writeFile(mutationFile, document.get());
                    }


            }
        }//一代变异体生成结束


        boolean findCorrectMutationFlag = false;
        int iter_cnt = 0;//迭代次数,可调节
        //到找到正确程序为止
        while(iter_cnt < 7){

            iter_cnt++;
            File dir = new File(basicSourceFile + "JavaMutation"); //变异体目录
            // 如果 \temp 不存在 就创建
            if (!dir.exists()) {
                dir.mkdir();
            }

            //执行文件中所有变异体的循环
            int mutationNumber = 0;

            File[] mutationFiles = new File(basicSourceFile + "JavaMutation").listFiles();

            //每个变异体的适应度映射，用变异序列和fitness映射
            Map<ArrayList<Integer>, Double> fitnessMap = new HashMap<>();

            for(File mutation_file : mutationFiles){
                mutationNumber ++;
                //每个变异体执行所有测试用例，并比较输出结果，计算适应度
                int passedNumber = 0;
                int failedNumber = 0;
                //System.out.println(mutation_file.getAbsolutePath());

                try {
                    File sourceDir;

                        sourceDir = new File(System.getProperty("user.dir") + File.separator + "JavaMutation"); //临时目录


                    String mutation_name = "TNPMutation" + mutationNumber + ".java";

                    //编译执行变异体，直接结果写入文件中
                    DynamicCompileTest dynamicCompileTest = new DynamicCompileTest();
                    dynamicCompileTest.compile(sourceDir, mutation_name);

                    // read file content from file
                    StringBuffer sb = new StringBuffer("");


                    //读文件中的期待输出
                    FileReader outputExceptionReader = new FileReader(basicSourceFile + "JavaCorrectOutput" + File.separator + "compareOuput.txt");
                    BufferedReader outputCase = new BufferedReader(outputExceptionReader);

                    //读变异体的输出
                    FileReader mutationOutputReader = new FileReader(basicSourceFile + "JavaTestResult" + File.separator + "threeWordPlus_output.txt");
                    BufferedReader mutationOutputCase = new BufferedReader(mutationOutputReader);

                    //输入的每行，期待输出的每行
                    String mutation_output_str = null;
                    String model_output_str = null;

                    //对每一个测试用例
                    while((mutation_output_str = outputCase.readLine()) != null && (model_output_str = mutationOutputCase.readLine()) != null) {

                        if(mutation_output_str.equals(model_output_str)){
                            passedNumber ++;
                        }else{
                            failedNumber ++;
                        }

                    }

                    outputExceptionReader.close();
                    outputCase.close();
                    mutationOutputReader.close();
                    mutationOutputCase.close();

                }
                catch(FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch(IOException e) {

                    e.printStackTrace();
                }

                System.out.println("passed : " + passedNumber + "  failed : " + failedNumber);

                if(passedNumber == 9){
                    System.out.println("find the correct mutation : " + mutation_file.getAbsolutePath());
                    findCorrectMutationFlag = true;
                    break;
                }

                //计算适应度，每个变异体一个适应度，按适应度排序，取前50%
                Double fitness = new Double(0);
                if(passedNumber + failedNumber != 0){
                    fitness = Double.valueOf(passedNumber/(passedNumber + failedNumber));
                }
                else{
                    fitness = Double.valueOf(0);
                }


                //变异体的变异序列，与适应度，映射
                fitnessMap.put(group.get(mutationNumber-1), fitness);

            }//全部执行完毕

            //跳出while循环,找到正确程序
            if(findCorrectMutationFlag){
                break;
            }

            //清空group，因为序列都存到map里了
            group = new ArrayList<>();

            //变异，交叉，生成新的变异体

            //这里将map.entrySet()转换成list
            List<Map.Entry<ArrayList<Integer>,Double>> list = new ArrayList<>(fitnessMap.entrySet());
            //然后通过比较器来实现排序
            //降序排序，按照适应度从大到小排序
            Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));


            //fitness前50%的每个变异体，全部变异，增加一个修改
            int listLength = list.size();
            int listCnt = 0;
            int mutation_cnt = 0;
            for(Map.Entry<ArrayList<Integer>,Double> mapping:list) {
                listCnt++;

                //fitness前50%的每个变异体
                if (listCnt <= listLength / 2) {

                    //前一半变异体的变异序列,0,1,2
                    ArrayList<Integer> mutationAct = mapping.getKey();

                    cnt = 0;

                    //每个变异体的下一步动作，随机生成个数，暂时全是replace动作
                    double random = Math.random();
                    int mutationRandomAct = (int) (random * sizeOfSource);
                    System.out.println("random = " + mutationRandomAct);

                    int modelRandomAct = 0;
                    int sameFatherCnt = 0;
                    while(sameFatherCnt < 10){
                        sameFatherCnt ++;
                        random = Math.random();
                        modelRandomAct = (int) (random * sizeOfModel);
                        if(!sourceNodeList.get(mutationRandomAct).toString().equals(nodeList.get(modelRandomAct).toString())
                                && sourceNodeList.get(mutationRandomAct).getParent().getNodeType() == nodeList.get(modelRandomAct).getParent().getNodeType()){
                            System.out.println("random = " + modelRandomAct);
                            break;
                        }

                    }


                    //变异
                    //每个变异体一个序列，记录下一步动作
                    mutationAct.add(0);
                    mutationAct.add(mutationRandomAct);
                    mutationAct.add(modelRandomAct);

                    group.add(mutationAct);//种群中存储一个变异序列

                    for(int i = 0; i < mutationAct.size(); i ++){
                        System.out.print(mutationAct.get(i) + " ");
                    }
                    System.out.println();
                }
            }

            //交叉
            int groupLength = group.size();
            int doubleGroupLength = groupLength * 2;
            while(group.size() < doubleGroupLength){

                int crossNum = (int) (Math.random() * groupLength);//选一个变异体交叉
                int crossTempNum = (int) (Math.random() * groupLength);//选另一个变异体交叉

                int crossLocation = (int) (Math.random() * (group.get(crossNum).size()/3));//交叉位置
                int crossTempLocation = (int)(Math.random() * (group.get(crossTempNum).size()/3));//交叉位置

                ArrayList<Integer> newMutant = new ArrayList<>();//交叉后的变异体
                ArrayList<Integer> crossMutant = group.get(crossNum);//交叉的一个变异序列
                ArrayList<Integer> crossTempMutant = group.get(crossTempNum);//交叉的另一个变异序列

                //前一半，第一个变异体的前一半
                for(int i = 0; i < crossLocation * 3; i ++){
                    newMutant.add(crossMutant.get(i));
                }

                //后一半，第二个变异体的后一半
                for(int i = crossTempLocation * 3; i < group.get(crossTempNum).size(); i ++){
                    newMutant.add(crossTempMutant.get(i));
                }

                //种群中添加一个交叉后的变异体序列
                group.add(newMutant);

                for(int i = 0; i < newMutant.size(); i ++){
                    System.out.print(newMutant.get(i) + " ");
                }
                System.out.println();

                newMutant = new ArrayList<>();//新的变异序列，再交叉一次
                //前一半，第二个变异体的前一半
                for(int i = 0; i < crossTempLocation * 3; i ++){
                    newMutant.add(crossTempMutant.get(i));
                }

                //后一半，第一个变异体的后一半
                for(int i = crossLocation * 3; i < group.get(crossNum).size(); i ++){
                    newMutant.add(crossMutant.get(i));
                }

                //种群中添加一个交叉后的变异体序列
                group.add(newMutant);

                for(int i = 0; i < newMutant.size(); i ++){
                    System.out.print(newMutant.get(i) + " ");
                }System.out.println();
            }

            //清空文件中的残余变异体，保存新的
            delAllFile(basicSourceFile + "JavaMutation");
            //将种群中的变异序列全部生成变异体
            for(int i = 1; i < group.size(); i ++){

                Document document;
                document = new Document(fileString);//源程序路径

                ASTRewrite rewriter = ASTRewrite.create(sourceUnit.getAST());//变异体重写语法树
                AST ast = sourceUnit.getAST();
                TextEditGroup textEdits = new TextEditGroup("test");

                TextEdit edits;
                ArrayList<Integer> mutant = group.get(i);//一个变异序列，三个数字为一个操作
                for(int j = 0; j < mutant.size(); j ++){
                    if(j % 3 == 0){

                    }
                    else if(j % 3 == 2){
                        TextElement textElement = ast.newTextElement();
                        textElement.setText(nodeList.get(mutant.get(j)).toString());

                        rewriter.replace(sourceNodeList.get(mutant.get(j-1)),
                                textElement, textEdits);//rewriter.remove(sourceNodeList.get(i), textEdits);
                    }
                }

                edits = rewriter.rewriteAST(document, null);
                edits.apply(document);
                //输入到新的文件中，变异体

                String mutationFile = basicSourceFile + "JavaMutation"  + File.separator + "TNPMutation" + i + ".java";

                //System.out.println(mutationFile);
                T.writeFile(mutationFile, document.get());
            }

        }

    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
//              delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
}
