
package ags;

import common.SystemConstant;
import model.FileAssignmentView;
import model.ListFileAssignmentView;
import util.HighlighterHandler;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;

/*
 * 25092017 work file uploading
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

import service.ConstantService;
import service.CourseService;
import service.FileService;
import service.LogService;
import service.ParameterService;
import service.SystemService;
import service.UserService;

/**
 *
 * @author Yongsong
 */
public class SourceCompiler {
    // inputs needed
    private String language;
    private String srcDir;
    private String srcFilePath;

    private File binFile;

    private ProcessBuilder pb;

    private int answerID;
    private DbListener dbl;
    private PrintWriter out;
    private ConstantService cs;
    private SystemService ss;
    private ParameterService ps;
    private LogService ls;
    private UserService us;
    private CourseService css;
    private FileService fs;

    private boolean SuccessfulFlag = false;

    public void setBeanFactory(BeanFactory arg0) throws BeansException {

        cs = (ConstantService)arg0.getBean("constantService");
        ss = (SystemService)arg0.getBean("systemService");
        ps = (ParameterService)arg0.getBean("parameterService");
        ls = (LogService)arg0.getBean("logService");
        us = (UserService)arg0.getBean("userService");
        css = (CourseService)arg0.getBean("courseService");
        fs = (FileService)arg0.getBean("fileService");
    }
    //文件路径、文件名、语言、id、输出流
    public SourceCompiler(String sourceDir, String fileName, String lang, int anID, PrintWriter pw, String inputTestCase ,String outputException) throws IOException, InterruptedException {
        language = lang;
        srcDir = sourceDir;
        srcFilePath = srcDir + File.separator + "testFiles" + File.separator + fileName;

        String statusRequest="";
        String resultCompile="";
        String resultError ="";
        long compileStartTime ;
        long compileEndTime;
        String compileTime="";
        long runStartTime ;
        long runEndTime;
        String runTime="";
        String compileError ="";
        String outputCompile ="";

        double score = 0;
        double avgScore = 0;
        double totalScore = 0;
        String testCaseResultStatus = "";

        String resultRunCompile="";
        String resultGrade="";


        boolean requestSuccess=false;


        //String isSubmit = request.getParameter("hiddenSubmit");
        //boolean isRegister = (isSubmit!=null) && (isSubmit.equals("RegisterCourseAssignment"));
        boolean isValid = true;


        // PARAMETER FOR COMPILER LATER TO SCORE
        String assignmentInputType = "";
        String assignmentInput ="";
        String assignmentOutput = "";
        String assignmentSampleInput = inputTestCase;
        String assignmentSampleOutput = outputException;
        String outputText ="";

        //需赋值
        String assignmentId = "";


        if(language.equals("Java")){
            String baseName =  fileName.substring(0, fileName.lastIndexOf("."));
            this.binFile =new File(srcDir+"/"+ baseName+".class");
        }
        else{
            //Compile file
            if(language.equals("c"))
            {
                File binFile;
                ProcessBuilder pb;
                Process pc;

                //生成.exe文件
                binFile = new File(sourceDir + File.separator + "testResult" + File.separator + fileName.substring(0, fileName.lastIndexOf(".")) + "_gen.exe");
                if(binFile.exists()){
                    binFile.delete();
                }

                String strCompile[] ={"gcc",
                        sourceDir + File.separator + "testFiles" + File.separator + fileName,
                        "-o",
                        binFile.getAbsolutePath()
                };

                // System.out.println(strCompile[0]);

                //编译文件
                pb = new ProcessBuilder(strCompile);
                pc = pb.start();
                pc.waitFor();

                String[] cShell  =  {binFile.getAbsolutePath()};
                String[] strShell;
                strShell = cShell;

                File destination = new File(binFile.getAbsolutePath());
                try {
                    binFile.renameTo(new File(binFile.getAbsolutePath(),binFile.getName()));
                } catch (Exception e) {

                    statusRequest = statusRequest.concat("Failed transfering bin file");
                    requestSuccess = false;
                    System.out.println(e);

                } finally {
                    //response.flushBuffer();
                }
                String compileFileCommand = "gcc " + sourceDir + File.separator + "testFiles" + File.separator + fileName+" -o "+binFile.getAbsolutePath() ;

                compileStartTime = System.currentTimeMillis();
                compileEndTime = compileStartTime + 5000; //Five second

                Process processCompile = Runtime.getRuntime().exec(compileFileCommand);
                if(System.currentTimeMillis()<compileEndTime)
                {
                    long frmtCompileTime = (System.currentTimeMillis()-compileStartTime)/1000;
                    compileTime = frmtCompileTime+" ms - (No Time Limit Exceeded)";
                }
                else
                {
                    compileTime = (System.currentTimeMillis()-compileStartTime)/1000+" second - (Time Limit Exceeded)";

                }
               // System.out.println(compileTime);
                BufferedReader brCompileError = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
                String errorCompile = brCompileError.readLine();
                if (errorCompile != null)
                {
                    System.out.println("Error Compiler = " + errorCompile);
                    resultCompile += errorCompile +"\n";
                    compileError += errorCompile +"\n";
                }

                BufferedReader brCompileRun = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
                outputCompile = brCompileRun.readLine();
                if (outputCompile != null)
                {
                    System.out.println("Output Compiler = " + outputCompile);
                    resultCompile += outputCompile +"\n";
                }

                //================================== RUNNING COMPILED SOURCE =================================================
                //Check binFile
                boolean isCompiled = false;
                if(binFile.exists()){
                    isCompiled = true;
                    String runFileCommand = binFile.getAbsolutePath();
                    try
                    {
                        System.out.println("Running C File");
                        Process processRun;
                        BufferedReader brRun = null;
                        String errorRun="";
                        String outputRun="";
                        InputStreamReader is;
                        BufferedReader brResult = null;
                        StringBuilder sb;
                        // Allocate a output writer to write the response message into the network socket
                      //  PrintWriter out = response.getWriter();

                        //========================================== INPUT TYPE NO (SIMPLE ASSIGNMENT w/ ASSIGNMENT EXPECTED OUTPUT FILLED) =============================================================

                        //assignmentInputType == "ASSIGNMENT_INPUT_TYPE_NO"

                        //========================================== INPUT TYPE KEYBOARD =============================================================
                        //assignmentInputType == ASSIGNMENT_INPUT_TYPE_KEYBOARD
                        //if(assignmentInputType.equals(SystemConstant.ASSIGNMENT_INPUT_TYPE_.concat(SystemConstant.ASSIGNMENT_INPUT_TYPE_KEYBOARD)))
                        {
                            //TODO @Budi
                            //Testing input from database data

                            //输入不为空，测试用例
                            if(assignmentSampleInput!=null &&  !assignmentSampleInput.equals(""))
                            {
                                //GET DATA INPUT AND EXPECTED FROM ASSIGNMENT
                                int numTestCase=1; // default for DB only
                                int inputTest=0;
                                String delimiter = " ";
                                String input[] = assignmentSampleInput.split(delimiter);//测试用例

                                //String input[] = {"1","2","3"};

                                String output[] = assignmentSampleOutput.split(delimiter);
                                int length = input.length;

                                inputTest=input.length;

                                System.out.println("Start Executing...<br>");

                                PrintStream ps = null;
                                OutputStream os = null;
                                StringBuilder sbOut = null;
                                String[] runOutputs;
                                BufferedReader bfr = null;
                                runOutputs = new String[inputTest];

                                try{
                                    //build .exe文件
                                    pb = new ProcessBuilder(binFile.getAbsolutePath());
                                    pb.redirectErrorStream(true);

                                    //输出到文件中.txt
                                    String outputPath = binFile.getAbsolutePath().substring(0, binFile.getAbsolutePath().lastIndexOf("."));
                                    pb.redirectOutput(new File(outputPath+"_output.txt"));

                                    pc = pb.start();
                                    ps = new PrintStream(new BufferedOutputStream(pc.getOutputStream()));
                                    os = (new BufferedOutputStream(pc.getOutputStream()));

                                    // PASSING INPUT SIMULATED KEYBOARD INTO PROGRAM  测试用例
                                    for(int i = 0; i < inputTest; i ++){
                                        String inputKeyboard = input[i];
                                        ps.print(inputKeyboard);
                                        ps.print("\n");
                                        //System.out.println("inputcase : " + inputKeyboard);
                                        brResult = new BufferedReader(new InputStreamReader(pc.getInputStream()));

                                    }
                                    brResult = new BufferedReader(new InputStreamReader(pc.getInputStream()));

                                    ps.close();

                                    sbOut = new StringBuilder();
                                    int ch;
                                    bfr = new BufferedReader(new InputStreamReader(pc.getInputStream()));
                                    while((ch=bfr.read())!= -1){
                                        sbOut.append((char)ch);
                                    }
                                    pc.waitFor();

                                    //从文件中读取结果
                                    outputText = HighlighterHandler.getContent(outputPath+"_output.txt").substring(4);
                                    outputRun = outputText;
                                    System.out.println("outputcase : " + outputRun);

                                }
                                catch(IOException ex){

                                }
                                catch(InterruptedException ex){
                                    ex.printStackTrace();
                                }
                                finally{

                                    if(ps!=null ){
                                        ps.close();
                                    }
                                    if(brResult != null){

                                        try{
                                            if (outputRun != null)
                                            {
                                                //System.out.println("Output Run = " + outputRun);
                                                resultRunCompile = outputRun;
                                                //Simple assignment
                                                try
                                                {
                                                    System.out.println("Output Run = " + outputRun);
                                                    //预期结果，assignmentSampleOutput
                                                    if(outputRun.equals(assignmentSampleOutput) || checkOutput(outputRun,assignmentSampleOutput))
                                                    {

                                                        totalScore = 100;
                                                        resultGrade = "Score : "+totalScore +" - Success";
                                                        System.out.println(resultGrade);
                                                        this.SuccessfulFlag = true;
                                                    }
                                                    else
                                                    {
                                                        totalScore = 0;
                                                        resultRunCompile = "Incorrect result\n\n";
                                                        resultGrade = "Score : "+totalScore +" - Fail";
                                                        System.out.println(resultGrade);
                                                    }

                                                }
                                                catch (Exception e)
                                                {
                                                    System.out.println("Exception ");
                                                    System.out.println(e.getMessage());
                                                }


                                            }
                                            outputRun = brResult.readLine();
                                            brResult.close();
                                        }catch(IOException e){}
                                    }
                                }
                            }


                        }


                    } catch (Exception e)
                    {
                        // TODO: handle exception
                        System.out.println("Exception ");
                        System.out.println(e.getMessage());
                        resultRunCompile=e.toString();

                    }


                }
                else
                {

                    resultCompile = errorCompile.substring(errorCompile.lastIndexOf("//")+2)+"\n";
                    resultCompile += outputCompile+"\n";
                    resultRunCompile += "File didn't pass in compiled process..";
                }


                //TODO @Budi formatted output
                if(compileError!=null && !compileError.equals(""))
                {
                    compileError = compileError.substring(compileError.lastIndexOf("//")+2, compileError.length());
                    if(outputCompile!=null && !outputCompile.equals(""))
                    {
                        compileError = compileError+outputCompile;
                    }
                }


            }
        }

//        if(binFile.exists()){
//            binFile.delete();
//        }

        this.answerID = anID;
        this.out = pw;
        
        this.pb = null;
    }
    

    
    public File getBinFile(){
        return this.binFile;
    }

    public boolean getSuccessfulFlag() {
        return this.SuccessfulFlag;
    }

    private boolean checkOutput(String actualOutput, String testcaseOutput){
        System.out.println("Output Run = checkout" );
        String[] testTokens = testcaseOutput.split("\\s");
        boolean allNumeric=true;
        for(String token:testTokens){
            allNumeric= allNumeric && isNumeric(token);
        }

        boolean result =true;
        if(allNumeric){
            String[] actualTokens = actualOutput.split("\\s");
            for(int i=0; i<testTokens.length; i++){
                result = result && checkNumber(actualTokens[i], testTokens[i]);
            }
            return result;

        }
        else{
            // check by line
            String[] actualLines=rtrim(actualOutput).split("\r\n|\r|\n");
            String[] testLines = rtrim(testcaseOutput).split("\r\n|\r|\n");

            if(actualLines.length!= testLines.length){
                return false;
            }
            for(int i=0; i< testLines.length; i++){
                result = result && checkLine(actualLines[i], testLines[i]);
            }
            return result;
        }

    }
    private boolean checkNumber(String actual, String test){
        double actualNum = Double.parseDouble(actual);
        double testNum = Double.parseDouble(test);
        if(actualNum== testNum){
            return true;
        }
        else return false;
    }

    private boolean checkLine(String actual, String test){
        if(isNumeric(test)){
            return checkNumber(actual, test);
        }
        else{
            if(rtrim(actual).equals(rtrim(test))){
                return true;
            }
            else return false;
        }
    }


    public static boolean isNumeric(String str){
        try {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }

    public static String rtrim(String str){
        if(str==null)
            return null;

        int index = str.length();
        while (index > 0 && Character.isWhitespace(str.charAt(index-1)))
            index--;

        // at here charAt(index) is not whitespace
        // index == 0 means the entire string is white space; substring will return the empty string
        str = str.substring(0, index);
        return str;
    }
}
