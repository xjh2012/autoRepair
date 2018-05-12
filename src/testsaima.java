
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;

import java.util.*;

public class testsaima {

    public static void main(String[] args) throws BadLocationException, CoreException {
        JavaASTTree javaASTTree = new JavaASTTree();
        Map<String,List<String>> modelMap=new HashMap<>();
        modelMap = javaASTTree.modelMap;//模板程序的map执行值序列

        JavaASTSourceTree javaASTSourceTree = new JavaASTSourceTree();
        Map<String,List<String>> sourcelMap=new HashMap<>();
        sourcelMap = javaASTSourceTree.modelMap;//原程序的map执行值序列

        for (Map.Entry<String,List<String>> entry : modelMap.entrySet()) {
            int max = 0;//最多相似节点数
            String similar = "";//对应相似节点

            for(Map.Entry<String,List<String>> entrySource : sourcelMap.entrySet()){
               // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
               LCSforArray lcSforArray = new LCSforArray();
               int lcsLength = lcSforArray.LCS(entry.getValue(), entrySource.getValue());
               //System.out.println("ModelValue = " + entry.getValue() + ", SourceValue = " + entrySource.getValue() + ", LCS = " + lcsLength);
               if(lcsLength > max) {
                   max = lcsLength;
                   similar = entrySource.getKey();
                   if(max == entry.getValue().size()){
                       break;
                   }
               }
            }

           // sourcelMap.remove(similar);

            System.out.println("ModelKey = " + entry.getKey() + ", SourceKey = " + similar);
            System.out.println();

        }

        List<String> modelList = new ArrayList<>();
        for (Map.Entry<String,List<String>> entry : modelMap.entrySet()) {
            for(int i = 0; i < entry.getValue().size(); i ++){
                modelList.add(entry.getValue().get(i));
            }
        }

        List<String> sourcelList = new ArrayList<>();
        for (Map.Entry<String,List<String>> entry : sourcelMap.entrySet()) {
            for(int i = 0; i < entry.getValue().size(); i ++){
                sourcelList.add(entry.getValue().get(i));
            }
        }
        LCSforArray lcSforArray = new LCSforArray();
        int lcsLength = lcSforArray.LCS(modelList, sourcelList);

        double similar = lcsLength/modelList.size();
    }
}
