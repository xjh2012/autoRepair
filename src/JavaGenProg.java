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
        File sourceFillePath = new File(sourceFile);//源程序路径
        JavaParser sourceJavaParser = new JavaParser();//模板程序的语法树
        fileString = javaParser.getCode(sourceFillePath);//模板程序内容

        CompilationUnit sourceUnit = sourceJavaParser.getCompilationUnit(fileString);
        JavaVisitor sourceJavaVisitor = new JavaVisitor();
        sourceUnit.accept(sourceJavaVisitor);
        List<ASTNode> sourceNodeList = sourceJavaVisitor.nodeList;//错误源程序的节点列表

        int sizeOfModel = nodeList.size();
        int sizeOfSource = sourceNodeList.size();



        int cnt = 0;
        //生成第一代变异体,model中每个节点，对sourse中每个节点replace
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

                if(Math.random()<0.6){
                    //两行不是完全相同的节点，父节点结构相同,才可以替换
                    if(!sourceNodeList.get(i).toString().equals(nodeList.get(j).toString())
                            && sourceNodeList.get(i).getParent().getNodeType() == nodeList.get(j).getParent().getNodeType()){
                        cnt ++;
                        // nodeList.get(i).setProperty(sourceNodeList.get(j).toString(),null);
                        System.out.println(cnt + "\nA : \n" + nodeList.get(i).toString() + "\n" + sourceNodeList.get(j).toString() + "\n");
                        rewriter.replace(sourceNodeList.get(i),
                                textElement,textEdits);
                    }
                }

                TextEdit edits = rewriter.rewriteAST(document, null);
                edits.apply(document);

                //输入到文件中，变异体
                String mutationFile = basicSourceFile  + "JavaMutation" + File.separator + "TNPMutation" + cnt + ".java";

                T.writeFile(mutationFile, document.get());

            }
        }//一代变异体生成结束

        //System.out.println();

        //形成变异体
//        replace(cppASTTree.notSameNodeA.get(0),cppASTTree.notSameNodeB.get(0));

        boolean findCorrectMutationFlag = false;
        int iter_cnt = 0;//迭代次数,可调节
        //到找到正确程序为止
        while(iter_cnt < 2){

            iter_cnt++;
            File dir = new File(basicSourceFile + "JavaMutation" + iter_cnt); //变异体目录
            // 如果 \temp 不存在 就创建
            if (!dir.exists()) {
                dir.mkdir();
            }

            //执行文件中所有变异体的循环
            int mutationNumber = 0;

            File[] mutationFiles = new File(basicSourceFile + "JavaMutation").listFiles();
            Map<String, Double> fitnessMap = new TreeMap<String, Double>();

            for(File mutation_file : mutationFiles){
                mutationNumber ++;
                //每个变异体执行所有测试用例，并比较输出结果，计算适应度
                int passedNumber = 0;
                int failedNumber = 0;
                System.out.println(mutation_file.getAbsolutePath());

                try {
                    File sourceDir;
                    if(iter_cnt == 1){
                        sourceDir = new File(System.getProperty("user.dir") + File.separator + "JavaMutation"); //临时目录
                    }
                    else{
                        sourceDir = new File(System.getProperty("user.dir") + File.separator + "JavaMutation" + (iter_cnt - 1)); //临时目录
                    }

                    String mutation_name = "TNPMutation" + mutationNumber + ".java";

                    //编译执行变异体，直接结果写入文件中
                    DynamicCompileTest dynamicCompileTest = new DynamicCompileTest();
                    dynamicCompileTest.compile(sourceDir, mutation_name);

                    // read file content from file
                    StringBuffer sb= new StringBuffer("");


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


                //变异体路径，适应度，映射
                fitnessMap.put(mutation_file.getAbsolutePath(), fitness);

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


            // List<>
            int listLength = list.size();
            int listCnt = 0;
            int mutation_cnt = 0;
            for(Map.Entry<String,Double> mapping:list){
                listCnt ++;

                //fitness前50%的每个变异体
                if(listCnt <= listLength/2){

                    //变异体语法树返回变异体程序的节点
                    String sourceProgram = mapping.getKey();

                    sourceFillePath = new File(sourceProgram);//变异体路径
                    sourceJavaParser = new JavaParser();//变异体的语法树
                    fileString = javaParser.getCode(sourceFillePath);//变异体程序内容

                    sourceUnit = sourceJavaParser.getCompilationUnit(fileString);
                    sourceJavaVisitor = new JavaVisitor();
                    sourceUnit.accept(sourceJavaVisitor);
                    sourceNodeList = sourceJavaVisitor.nodeList;//变异体程序的节点列表
                    sizeOfSource = sourceNodeList.size();

                    cnt = 0;

                    for(int i = 0; i < sizeOfSource; i ++){
                        for(int j = 0; j < sizeOfModel; j ++) {

                            mutation_cnt ++;
                            Document document;
                            document = new Document(fileString);//变异体路径

                            System.out.println("document Program path :::: " + sourceProgram);

                            ASTRewrite rewriter = ASTRewrite.create(sourceUnit.getAST());//变异体重写语法树
                            AST ast = sourceUnit.getAST();
                            TextEditGroup textEdits = new TextEditGroup("test");

                            TextElement textElement = ast.newTextElement();
                            textElement.setText(nodeList.get(j).toString());
                            //   System.out.println(textElement.getText());

//                        ast.newName(sourceNodeList.get(j).toString());
                            //
                            // System.out.println(sourceNodeList.get(i).toString());
                            // System.out.println("B : \n" + rewriter.createStringPlaceholder(sourceNodeList.get(i).toString(), sourceNodeList.get(i).getNodeType()));
                            TextEdit edits;
                            if (Math.random() < 0.6) {
                                //两行不是完全相同的节点，父节点结构相同,才可以替换
                                if (!sourceNodeList.get(i).toString().equals(nodeList.get(j).toString())
                                        && sourceNodeList.get(i).getParent().getNodeType() == nodeList.get(j).getParent().getNodeType()) {
                                    cnt++;
                                    // nodeList.get(i).setProperty(sourceNodeList.get(j).toString(),null);
                                    System.out.println(cnt + "\nA : \n" + nodeList.get(i).toString() + "\n" + sourceNodeList.get(j).toString() + "\n");

                                    if(Math.random() > 0.5){
                                        rewriter.replace(sourceNodeList.get(i),
                                            textElement, textEdits);
                                        //insert(modelProgramParent,mutationProgramParent);
                                    }else{
                                        rewriter.remove(sourceNodeList.get(i), textEdits);
                                        //delete(mutationProgramParent);
                                    }

                                    edits = rewriter.rewriteAST(document, null);
                                    edits.apply(document);

                                    //输入到新的文件中，变异体

                                    String mutationFile = basicSourceFile + "JavaMutation" + iter_cnt + File.separator + "TNPMutation" + mutation_cnt + ".java";

                                    System.out.println(mutationFile);
                                    T.writeFile(mutationFile, document.get());
                                }
                            }


                        }


                    }


                    System.out.println(mapping.getKey()+" : "+mapping.getValue());
                }

            }




        }

    }


}
