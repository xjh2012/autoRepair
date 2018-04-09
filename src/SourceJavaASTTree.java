import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import util.DynamicCompileTest;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xjh on 2017/12/12.
 */
public class SourceJavaASTTree {
    public static void main(String[] args) throws BadLocationException, CoreException {
        String basicSourceFile = System.getProperty("user.dir") + File.separator;

        //模板程序,语法分析
        String modelSourceFile = basicSourceFile + "JavaTestFiles" + File.separator + "threeNumbersPlus.java";
        File file = new File(modelSourceFile);//模板程序路径

        ASTParser parser = ASTParser.newParser(AST.JLS8);

        JavaParser javaParser = new JavaParser();//模板程序的语法树
        String fileString = javaParser.getCode(file);//模板程序内容
        parser.setSource(fileString.toCharArray());
        CompilationUnit unit = (CompilationUnit) parser.createAST(null);//语法单元

        //System.out.println(unit);

        // CompilationUnit modelUnit = javaParser.getCompilationUnit(fileString);
        JavaVisitor javaVisitor = new JavaVisitor();
        unit.accept(javaVisitor);

        JavaASTTree javaASTTree = new JavaASTTree();

        try {
            AddStatements(javaASTTree);
        } catch (MalformedTreeException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    private static void AddStatements(JavaASTTree javaASTTree) throws MalformedTreeException, BadLocationException, CoreException {

//        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("testAddComments");
//        IJavaProject javaProject = JavaCore.create(project);
//        IPackageFragment package1 = javaProject.getPackageFragments()[0];
//
//        // get first compilation unit
//        ICompilationUnit unit = package1.getCompilationUnits()[0];

        // parse compilation unit
        //CompilationUnit astRoot = parse(unit);

        //E:\autoRepair\
        String basicSourceFile = System.getProperty("user.dir") + File.separator;

        //模板程序,语法分析
        String modelSourceFile = basicSourceFile + "JavaTestFiles" + File.separator + "threeNumbersPlus.java";
        File file = new File(modelSourceFile);//模板程序路径
        JavaParser javaParser = new JavaParser();//模板程序的语法树
        String fileString = javaParser.getCode(file);//模板程序内容

        CompilationUnit modelUnit = javaParser.getCompilationUnit(fileString);
        JavaVisitor javaVisitor = new JavaVisitor();
        modelUnit.accept(javaVisitor);
        List<ExpressionStatement> nodeList = javaVisitor.es;//模板的节点列表

        // create a ASTRewrite
        AST ast = modelUnit.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);

        // for getting insertion position
        TypeDeclaration typeDecl = (TypeDeclaration) modelUnit.types().get(0);
        MethodDeclaration methodDecl = typeDecl.getMethods()[0];
        Block block = methodDecl.getBody();

        // create new statements for insertion
        MethodInvocation newInvocation = ast.newMethodInvocation();
        newInvocation.setName(ast.newSimpleName("add"));
        Statement newStatement = ast.newExpressionStatement(newInvocation);



        //插桩，每个变量定义后面插入语句
        for(VariableDeclarationStatement vds_p : javaVisitor.getVDS()){
            // System.out.println(vds_p.fragments().get(0).toString().split("=")[0].split("\\[]")[0]);

            TextElement textElement = ast.newTextElement();
            textElement.setText("System.out.println(\"instrumentation \" + \""+vds_p.fragments().get(0).toString().split("=")[0].split("\\[]")[0] +
                    "\" + \" \" + "+ vds_p.fragments().get(0).toString().split("=")[0].split("\\[]")[0] +");");


//            List<Statement> a = ((Block) vds_p.getParent()).statements();
//            a.add(e);//e is the artificial statement

//            TextElement textElement = ast.newTextElement();
//            textElement.setText("System.out.println(" " + );");

            ListRewrite lrw = rewriter.getListRewrite(vds_p.getParent(), Block.STATEMENTS_PROPERTY);
            lrw.insertAfter(textElement, vds_p,null);

        }

        for(Assignment node : javaVisitor.getAssignment()){
            //  System.out.println(node.fragments().get(0).toString().split("=")[0].split("\\[]")[0]);

            TextElement textElement = ast.newTextElement();
            textElement.setText("System.out.println(\"instrumentation \" + \"" + node +
                    "\" + \" \" + "+ node +");");

            //System.out.println(node.getLeftHandSide());

//            List<Statement> a = ((Block) vds_p.getParent()).statements();
//            a.add(e);//e is the artificial statement

//            TextElement textElement = ast.newTextElement();
//            textElement.setText("System.out.println(" " + );");

//            ListRewrite lrw = rewriter.getListRewrite(node.getParent(), Block.STATEMENTS_PROPERTY);
            //           lrw.insertAfter(textElement, node, null);
        }

        for(ExpressionStatement node : javaVisitor.getEs()){
            //  System.out.println(node.fragments().get(0).toString().split("=")[0].split("\\[]")[0]);

            TextElement textElement = ast.newTextElement();


            if(node.toString().contains("=")){
                System.out.println(node.toString());

                textElement.setText("System.out.println(\"instrumentation \" + \"" + node.toString().split("=")[0].split("\\+")[0] +
                        "\" + \" \" + "+ node.toString().split("=")[0].split("\\+")[0] +");");

                ListRewrite lrw = rewriter.getListRewrite(node.getParent(), Block.STATEMENTS_PROPERTY);
                lrw.insertAfter(textElement, node, null);
            }


//            List<Statement> a = ((Block) vds_p.getParent()).statements();
//            a.add(e);//e is the artificial statement

//            TextElement textElement = ast.newTextElement();
//            textElement.setText("System.out.println(" " + );");


        }


        //create ListRewrite
        ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);

//        System.out.println(listRewrite.getOriginalList());

//        listRewrite.insertAt(newStatement, 3, null);

        // apply the text edits to the compilation unit
        Document document = new Document(fileString);

        TextEdit edits = rewriter.rewriteAST(document, null);

        edits.apply(document);

        System.out.println(document.get());

        //输入到文件中，变异体
        String mutationFile = basicSourceFile  + "instrumentation" + File.separator + "TNPMutation.java";

        T.writeFile(mutationFile, document.get());

        //执行变异体
        try {
            File sourceDir;

            sourceDir = new File(System.getProperty("user.dir") + File.separator + "instrumentation"); //临时目录

            String mutation_name = "TNPMutation.java";

            String exe_mutation_name = "threeNumbersPlus" + ".java";

            File mutant_file = new File(sourceDir + File.separator + mutation_name);
            File exe_mutant_file = new File(sourceDir + File.separator + exe_mutation_name);
            JavaGenProg.copyFile(mutant_file, exe_mutant_file);

            //编译执行变异体，直接结果写入文件中
            DynamicCompileTest dynamicCompileTest = new DynamicCompileTest();
            dynamicCompileTest.compile(sourceDir, exe_mutation_name);

            Map<String,List<String>> sourceMap=dynamicCompileTest.map;


            for (Map.Entry<String,List<String>> entry : sourceMap.entrySet()) {

                for (Map.Entry<String,List<String>> sourceEntry : javaASTTree.modelMap.entrySet()) {


                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

                }
            }


        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {

            e.printStackTrace();
        }


        // this is the code for adding statements
//        unit.getBuffer().setContents(document.get());
    }
}
