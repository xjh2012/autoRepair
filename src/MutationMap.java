
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;

import java.util.*;

public class MutationMap {
    public static Map<String,String> mutationMap = new HashMap<>();//变量映射表

    public static void mutationMap() throws BadLocationException, CoreException {
        //这段改成模板程序和源程序代码分别执行JAvaASTTree（filename），设置传参，得到两个程序的key和value值

        JavaASTTree javaASTTree = new JavaASTTree();
        Map<String,List<String>> modelMap=new HashMap<>();
        modelMap = javaASTTree.modelMap;//模板程序的map执行值序列, key:变量名，value:值序列

        JavaASTSourceTree javaASTSourceTree = new JavaASTSourceTree();
        Map<String,List<String>> sourcelMap=new HashMap<>();
        sourcelMap = javaASTSourceTree.modelMap;//原程序的map执行值序列


        //不用改，两个程序的变量值和序列用LCS匹配
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
            //获得变量映射表
            mutationMap.put(entry.getKey(),similar);

            System.out.println();

        }

//        List<String> modelList = new ArrayList<>();
//        for (Map.Entry<String,List<String>> entry : modelMap.entrySet()) {
//            for(int i = 0; i < entry.getValue().size(); i ++){
//                modelList.add(entry.getValue().get(i));
//            }
//        }
//
//        List<String> sourcelList = new ArrayList<>();
//        for (Map.Entry<String,List<String>> entry : sourcelMap.entrySet()) {
//            for(int i = 0; i < entry.getValue().size(); i ++){
//                sourcelList.add(entry.getValue().get(i));
//            }
//        }
//        LCSforArray lcSforArray = new LCSforArray();
//        int lcsLength = lcSforArray.LCS(modelList, sourcelList);
//
//        double similar = lcsLength/modelList.size();
    }
}
