
package ags;

import common.SystemConstant;
import model.FileAssignmentView;
import model.ListFileAssignmentView;
import org.springframework.web.servlet.ModelAndView;
import util.HighlighterHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

/*
 * 25092017 work file uploading
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.Charsets;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import ags.AnswerJudge;
import ags.DBSGradingJudge;
import common.SystemConstant;
import model.AssignmentView;
import model.FileAssignmentView;
import model.FileView;
import model.ListFileAssignmentView;
import model.ParameterView;
import service.ConstantService;
import service.CourseService;
import service.FileService;
import service.LogService;
import service.ParameterService;
import service.SystemService;
import service.UserService;
import util.HighlighterHandler;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;

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
    public SourceCompiler(String sourceDir, String fileName, String lang, int anID, PrintWriter pw) throws IOException, InterruptedException {
        language = lang;
        srcDir = sourceDir;
        srcFilePath = srcDir+"/"+ fileName;

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
        String assignmentSampleInput = "1 2 3";
        String assignmentSampleOutput = "";
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
                binFile = new File(sourceDir + File.separator + fileName.substring(0, fileName.lastIndexOf(".")) + "_gen.exe");
                if(binFile.exists()){
                    binFile.delete();
                }

                String strCompile[] ={"gcc",
                        sourceDir + File.separator + fileName,
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
                String compileFileCommand = "gcc " + sourceDir + File.separator + fileName+" -o "+binFile.getAbsolutePath() ;

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

                        if(assignmentInputType.equals(SystemConstant.ASSIGNMENT_INPUT_TYPE_.concat(SystemConstant.ASSIGNMENT_INPUT_TYPE_NO)))
                        {
                            //TODO @Budi no input file or test case
                            runStartTime = System.currentTimeMillis();
                            runEndTime = compileStartTime + 5000; //Five second

                            //System.out.println("Running C File");

                            pb = new ProcessBuilder(binFile.getAbsolutePath());
                            pb.redirectErrorStream(true);
                            String outputPath = binFile.getAbsolutePath().substring(0, binFile.getAbsolutePath().lastIndexOf("."));
                            pb.redirectOutput(new File(outputPath+"_output.txt"));

                            pc = pb.start();
                            sb = new StringBuilder();
                            brResult = new BufferedReader(new InputStreamReader(pc.getInputStream()));

                            String line="";
                            try {

                                while ((line = brResult.readLine()) != null) {
                                    System.out.println(line);
                                    sb.append(line+'\n');
                                }//System.out.println(" ====================================  " + outputRun);
                                Thread.sleep(1000); // 1 second @Budi to delay for system to prepare the new output and read it existed bug caching before
                                outputText = HighlighterHandler.getContent(outputPath+"_output.txt").substring(4);
                                outputRun = outputText;


                            } catch (IOException e) {
                                e.printStackTrace();
                               // System.out.println(" ====================================  " + outputRun);
                            } finally {
                                if (brResult != null || outputRun !=null) {
                                    try {
                                        if (outputRun != null)
                                        {
                                            System.out.println("==Output Run = " + outputRun + "==========");
                                            resultRunCompile=outputRun;
                                            //Simple assignment
                                            try
                                            {
                                                //System.out.println("Output Run = " + outputRun);
                                               // if(outputRun.equals(assignmentSampleOutput) || checkOutput(outputRun,assignmentSampleOutput))
                                                if(outputRun.equals(assignmentSampleOutput) )
                                                {System.out.println("Output Run = " + outputRun);
                                                    totalScore = 100;
                                                    resultGrade = "Score : "+totalScore +" - Success";
                                                }
                                                else
                                                {
                                                    totalScore = 0;
                                                    resultRunCompile = "Incorrect result\n\n";
                                                    resultGrade = "Score : "+totalScore +" - Fail";
                                                }

                                            }
                                            catch (Exception e)
                                            {
                                                System.out.println("Exception ");
                                                System.out.println(e.getMessage());
                                            }


                                        }
                                        brResult.close();
                                        //processRun.destroy();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }
                       // System.out.println(" ====================================  " + outputRun);
                        //========================================== INPUT TYPE FILE TEST CASE =============================================================

                        if(assignmentInputType.equals(SystemConstant.ASSIGNMENT_INPUT_TYPE_.concat(SystemConstant.ASSIGNMENT_INPUT_TYPE_FILE)))
                        {
                            //TODO @Budi
                            // to handle the fopen stream
                        }
                        //========================================== INPUT TYPE KEYBOARD =============================================================
                        //if(assignmentInputType.equals(SystemConstant.ASSIGNMENT_INPUT_TYPE_.concat(SystemConstant.ASSIGNMENT_INPUT_TYPE_KEYBOARD)))
                        {
                            //TODO @Budi
                            //Testing input from database data


                            if(assignmentSampleInput!=null &&  !assignmentSampleInput.equals(""))
                            {
                                //GET DATA INPUT AND EXPECTED FROM ASSIGNMENT
                                int numTestCase=1; // default for DB only
                                int inputTest=0;
                                String delimiter = " ";
                                String input[] = assignmentSampleInput.split(delimiter);

                                //String input[] = {"1","2","3"};

                                String output[] = assignmentSampleOutput.split(delimiter);
                                int length = input.length;
                                inputTest=input.length;


                                System.out.println("Start Executing...<br>");

                                PrintStream ps=null;
                                OutputStream os = null;
                                StringBuilder sbOut=null;
                                String[] runOutputs;
                                BufferedReader bfr=null;
                                runOutputs=new String[inputTest];
                                try{
                                    pb = new ProcessBuilder(binFile.getAbsolutePath());
                                    pb.redirectErrorStream(true);
                                    String outputPath = binFile.getAbsolutePath().substring(0, binFile.getAbsolutePath().lastIndexOf("."));
                                    pb.redirectOutput(new File(outputPath+"_output.txt"));

                                    pc = pb.start();
                                    ps = new PrintStream(new BufferedOutputStream(pc.getOutputStream()));
                                    os = (new BufferedOutputStream(pc.getOutputStream()));
                                    // PASSING INPUT SIMULATED KEYBOARD INTO PROGRAM
                                    for(int i=0; i<inputTest; i++){
                                        String inputKeyboard = input[i];
                                        ps.print(inputKeyboard);
                                        ps.print("\n");
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

                                    outputText = HighlighterHandler.getContent(outputPath+"_output.txt").substring(4);
                                    outputRun = outputText;
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
                                    if(brResult!=null){
                                        try{
                                            if (outputRun != null)
                                            {
                                                System.out.println("Output Run = " + outputRun);
                                                resultRunCompile=outputRun;
                                                //Simple assignment
                                                try
                                                {
                                                    System.out.println("Output Run = " + outputRun);
                                                    if(outputRun.equals(assignmentSampleOutput) || checkOutput(outputRun,assignmentSampleOutput))
                                                    {

                                                        totalScore = 100;
                                                        resultGrade = "Score : "+totalScore +" - Success";
                                                    }
                                                    else
                                                    {
                                                        totalScore = 0;
                                                        resultRunCompile = "Incorrect result\n\n";
                                                        resultGrade = "Score : "+totalScore +" - Fail";
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
                            else
                            {
                                //Testing input from testcase
                                //GET DATA INPUT AND EXPECTED FROM FILE ASSIGNMENT
                                int numTestCase=1; // default for DB only
                                int inputTest=0;
                                String delimiter = "\\W+";

                                List<ListFileAssignmentView> testCaseList = new ArrayList<ListFileAssignmentView>();
                                ListFileAssignmentView searchTestCase = new ListFileAssignmentView();
                                searchTestCase.setAssignmentId(Integer.parseInt(assignmentId));
                                searchTestCase.setFileAssignmentType(SystemConstant.FILE_ASSIGNMENT_.concat(SystemConstant.FILE_ASSIGNMENT_NAME_TEST_CASE));
                                searchTestCase.setIsDelete(SystemConstant.FALSE.toString());

                                testCaseList = fs.getListFileAssigmentByParam(searchTestCase);


                                System.out.println("Start Executing...<br>");

                                PrintStream ps=null;
                                OutputStream os = null;
                                StringBuilder sbOut=null;
                                String[] runOutputs;
                                BufferedReader bfr=null;
                                runOutputs=new String[inputTest];
                                try{


                                    // PASSING INPUT SIMULATED KEYBOARD INTO PROGRAM
                                    String outputPath ="";
                                    for(int i=0; i<testCaseList.size();i++)
                                    {
                                        //TODO @Budi with input file based on test case
                                        runStartTime = System.currentTimeMillis();
                                        runEndTime = compileStartTime + 5000; //Five second
                                        pb = new ProcessBuilder(binFile.getAbsolutePath());
                                        pb.redirectErrorStream(true);
                                        outputPath = testCaseList.get(i).getParFileFullPath().substring(0, testCaseList.get(i).getParFileFullPath().lastIndexOf("_"));
                                        pb.redirectOutput(new File(outputPath+"_output.txt"));

                                        pc = pb.start();
                                        ps = new PrintStream(new BufferedOutputStream(pc.getOutputStream()));
                                        os = (new BufferedOutputStream(pc.getOutputStream()));
                                        String inputTestCaseFiles = HighlighterHandler.getContent(testCaseList.get(i).getParFileFullPath()).substring(4);
                                        String input[] = inputTestCaseFiles.split(delimiter);
                                        String rawOutput = testCaseList.get(i).getFileAssignmentExpectedOutput();
                                        String output[]=null;
                                        if(rawOutput!=null && !rawOutput.equals(""))
                                        {
                                            output = rawOutput.split(delimiter);
                                        }
                                        else
                                        {
                                            //TODO @Budi generate output from sample source
                                            try
                                            {
                                                outputText = HighlighterHandler.getContent(outputPath+"_output.txt").substring(4);

                                            }catch(Exception e)
                                            {
                                                System.out.println(e);
                                            }

                                            ;
                                        }

                                        int length = input.length;
                                        inputTest=input.length;
                                        for(int j=0; j<inputTest; j++){
                                            String inputKeyboard = input[j];
                                            ps.print(inputKeyboard);
                                            ps.print("\n");
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

                                        outputText = HighlighterHandler.getContent(outputPath+"_output.txt").substring(4);
                                        outputRun = outputText;
                                        //TODO @Budi 23102017
                                        //because it is the admin or authorized so update the assignment  sample output expected in db
                                        //as it will be compiled by the correct sample source
                                        //This purpose to generate expected output automatically by correct sample source
                                        //Init the value


                                        assignmentSampleOutput = testCaseList.get(i).getFileAssignmentExpectedOutput();


                                        if(assignmentSampleOutput==null || assignmentSampleOutput.equals(""))
                                        {
                                            //update

                                            FileAssignmentView fileAssignment = new FileAssignmentView();
                                            fileAssignment.setAssignmentId(Integer.parseInt(assignmentId));
                                            fileAssignment.setId((testCaseList.get(i).getFileAssignmentId()));
                                            fileAssignment.setExpectedOutput(outputText);
                                            fileAssignment.setCreatedBy(001);//Compile System
                                            fs.updateFileAssignment(fileAssignment);


                                            assignmentSampleOutput = outputText;

                                        }

                                        if(brResult!=null){

                                            try{
                                                if (outputRun != null)
                                                {
                                                    System.out.println("Output Run = " + outputRun);
                                                    resultRunCompile=outputRun;
                                                    //Simple assignment
                                                    try
                                                    {

                                                        if(outputRun.equals(assignmentSampleOutput) || checkOutput(outputRun,assignmentSampleOutput))
                                                        {
                                                            score = 100;
                                                            totalScore += score;
                                                            testCaseResultStatus = "Testcase"+(i+1)+" - Success ("+ score+")\n".concat(testCaseResultStatus);
                                                            resultGrade = "Score : " + totalScore/(i+1);
                                                            System.out.println("Result : \n"+testCaseResultStatus);
                                                        }
                                                        else
                                                        {
                                                            resultRunCompile = "Incorrect result\n\n";
                                                            score = 0;
                                                            totalScore += score;
                                                            testCaseResultStatus = "Testcase"+(i+1)+" - Fail ("+ score+")\n".concat(testCaseResultStatus);
                                                            resultGrade = "Score : "+ totalScore/(i+1);
                                                            System.out.println("Result : \n" +testCaseResultStatus);

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
                                catch(IOException ex){

                                }
                                catch(InterruptedException ex){
                                    ex.printStackTrace();
                                }
                                finally{

                                    if(ps!=null ){
                                        ps.close();
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
        if(binFile.exists()){
            binFile.delete();
        }

        this.answerID = anID;
        this.out = pw;
        
        this.pb = null;
    }
    

    
    public File getBinFile(){
        return this.binFile;
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
