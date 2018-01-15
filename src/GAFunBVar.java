/**
 * Created by xjh on 2017/12/7.
 */
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
/**
 * 采用遗传算法求下列二元函数的最大值
 * max f(x1,x2)=x1^2+x2^2;
 * s.t.x1∈{1,2,3,4,5,6,7};x2∈{1,2,3,4,5,6,7};
 */

public class GAFunBVar
{
    private int x1,x2;
    private int groupSize;//群体尺寸
    private String[] group;//群体
    private double r;//每一步通过交叉取代群体成员的比例
    private double m;//变异率
    private String maxFitEntity;//最大适应度个体
    private int maxFitness;//保存最大适应度
    private final int SWAPSIZE=100;
    private int noSwapNum=0;//最大适应度未交换次数，当该值大于某个SWAPSIZE,算法结束，付出最大适应度和满足的个体
    private double[] pr;
    private String[] thrbin={"000","001","010","011","100","101","110","111"};
    private static final int GENERATION=50;//进化多少代后停止。

    public GAFunBVar(int x1,int x2,int groupSize,double r,double m)
    {
        this.x1=x1;
        this.x2=x2;
        this.groupSize=groupSize;
        group=new String[groupSize];
        this.r=r;
        this.m=m;
        pr=new double[groupSize];
    }
    /*
     * 编码
     * @x1,x2 要编码的两个十进制数
     * @binStr 返回的二进制串
     */
    private String coding(int x1,int x2)
    {
        return thrbin[x1]+thrbin[x2];
    }
    /*
     * 解码
     * @binStr 要解码的二进制串
     * @int[] x 保存解码出来的两个十进制数
     */
    private int[] enCoding(String binStr)
    {
        int[] x=new int[2];
        String x1Str=binStr.substring(0,3);
        String x2Str=binStr.substring(3);
        for(int i=0;i<thrbin.length;i++)
        {
            if(x1Str.equals(thrbin[i]))
                x[0]=i;
            if(x2Str.equals(thrbin[i]))
                x[1]=i;
        }
        return x;
    }
    /*
     * 初始群体产生
     */
    private void InitGroup()
    {
        int i,m;
        String[] temp=new String[groupSize];//为了产生不重复的个体，设置的临时群体
        for(int j=0;j<groupSize;j++)
            temp[j]=null;

        for(i=0;i<groupSize;i++)
        {

            group[i]=thrbin[(int)(7*Math.random())+1]+thrbin[(int)(7*Math.random())+1];//群体
            for(m=0;m<i;m++)
            {
                if(group[i].equals(temp[m]))//如果这个个体已经被选择，则对第i个个体重新随机选择
                {
                    i--;
                    break;
                }
            }
            if(m==i)//说明该个体第一次选择，加入临时群体
                temp[i]=group[i];
        }

    }
    /*
     * 适应度计算
     */
    private int countFitness(String entityStr)
    {
        int[] x=new int[2];
        x=enCoding(entityStr);
        return x[0]*x[0]+x[1]*x[1];
    }
    /*
     * 整个群体的每个个体适应度计算，并和最大适应度比较，选出新的最大适应度，及相应的个体
     * @group 群体
     * @fitness 群体对应的适应度
     */
    private void getMaxFitness(String[] group)
    {
        int[] fitness=new int[groupSize];//每个个体对应的适应度
        int flag=0;//标记交换情况
        for(int i=0;i<groupSize;i++)
        {
            fitness[i]=countFitness(group[i]);
            if(fitness[i]>maxFitness)
            {
                flag=1;
                maxFitness=fitness[i];
                maxFitEntity=group[i];
                noSwapNum=0;
            }
        }
        if(flag==0)
        {
            noSwapNum++;
        }

    }
    /*
     * 选择（）,在此之前要进行假设被选择的概率的计算
     * @rnewEntNum 要选择的成员个数
     */

    private void chooseNewEnt(int rnewEntNum)
    {
        choose_Pr();//计算每个群成员的适应度
        roulette(rnewEntNum,group);//轮盘赌算法

    }
    /*
     * 轮盘赌算法
     */
    private void roulette(int rnewEntNum,String[] group)//从群中选择rnewEntNum个成员
    {
        //取[0,1]之间的概率片段对应每一个假设成员
        double zero_one_Pr[]=new double[groupSize-1];
        for(int i=0;i<groupSize-1;i++)
        {
            if(i==0)zero_one_Pr[i]=pr[0];
            else if(i==groupSize-2)zero_one_Pr[i]=1-pr[groupSize-1];
            else {
                for(int j=0;j<=i;j++)
                    zero_one_Pr[i]+=pr[j];//pr是每个成员的适应度比例，被选择的概率
            }
            System.out.print(zero_one_Pr[i]+" ");
        }

        //概率，落到哪个成员身上
        double rand=0;
        for(int i=0;i<rnewEntNum;i++)
        {
            rand=Math.random();
            if(rand<zero_one_Pr[0])
                group[i]=this.group[0];
            else if(rand<zero_one_Pr[1]&&rand>=zero_one_Pr[0])
                group[i]=this.group[1];
            else if(rand<zero_one_Pr[2]&&rand>=zero_one_Pr[1])
                group[i]=this.group[2];
            else {
                group[i]=this.group[3];
            }

        }
    }
    /*
     * 选择群体中每个成员的概率
     */
    private void choose_Pr()
    {
        double sum_pr=0.0;
        for(String str:group)
        {
            sum_pr+=countFitness(str);//总适应度和
        }
        for(int i=0;i<groupSize;i++)
        {
            pr[i]=countFitness(group[i])/sum_pr;//每个成员适应度
            System.out.println("成员"+group[i]+"被选择的概率（Pr(hi)）:"+pr[i]);
        }

    }
    /*
     * 交叉(总是要在选择之后完成)
     */
    private void intersect(int inter_pair,int inter_start_index)//1,2
    {
        String[] temp_Group=new String[inter_pair*2];
        roulette(inter_pair*2,temp_Group);//轮盘赌算法，按概率选择群成员

        for(String str:temp_Group)
            System.out.println("交叉选择的成员："+str);

        int len=temp_Group.length;
        int index1,index2;
        List<Integer> list= new ArrayList<>();
        int length=len;
        boolean flag;
        int i=inter_start_index;
        while(length!=0)
        {
            do {
                index1=(int)(len*Math.random());
                flag=isRepeatIndex(index1,list);//判重
            } while (flag);
            do {
                index2=(int)(len*Math.random());
                flag=isRepeatIndex(index2,list);
            } while (flag);

            String[] temp;
            temp=intersect_B(temp_Group[index1],temp_Group[index2]);
            group[i++]=temp[0];
            group[i++]=temp[1];//群体，从2开始往后加
            length-=2;
        }

    }
    /*
     * 在交叉时，判断一个下标（@index）对应的成员是否已经被选择过了
     * @temp_index 保存已选择了的成员在临时group中对应的下标
     */
    private boolean isRepeatIndex(int index,List<Integer> list)
    {
        for(int i=0;i<list.size();i++)
        {
            if(index==(int)list.get(i))
                return true;
        }
        list.add(index);
        return false;
    }
    /*
     * 任意两个成员实现交叉(单点交叉)
     * @single_point 交叉点位置
     */
    private String[] intersect_B(String entity1,String entity2)
    {
        String[] son_Ent=new String[2];
        int single_point=(int)(6*Math.random());//随机一个交叉的位置
        //交叉
        son_Ent[0]=entity1.substring(0,single_point)+entity2.substring(single_point);
        son_Ent[1]=entity1.substring(single_point)+entity2.substring(0,single_point);
        return son_Ent;
    }
    /*
     * 变异
     *@group 要变异的群体
     *@ratio 变异率
     *@
     */
    private void variation(String[] group, double ratio)
    {
        int var_Size=(int)(groupSize*ratio);//变异数量
        boolean flag=false;
        List<Integer> list=new ArrayList<Integer>();
        int index;
        while(var_Size>0)
        {
            do {
                index=(int)(groupSize*Math.random());
                flag=isRepeatIndex(index, list);//不重复的选择变异
            } while (flag);

            int var_site=(int)(6*Math.random());//随机位置，变异
            if(group[index].charAt(var_site)=='0')
                group[index]=group[index].substring(0,var_site)+'1'+group[index].substring(var_site+1);
            else if(group[index].charAt(var_site)=='1')
                group[index]=group[index].substring(0,var_site)+'0'+group[index].substring(var_site+1);
            var_Size--;
        }
    }

    public static void main(String args[])
    {
        int x[]=new int[2];
        String strTest=null;
        GAFunBVar gaFunBVar=new GAFunBVar(5,6,4,0.5,0.5);
        strTest=gaFunBVar.coding(gaFunBVar.x1,gaFunBVar.x2);//二进制
        System.out.println(strTest);

        x=gaFunBVar.enCoding(strTest);//解码
        gaFunBVar.x1=x[0];
        gaFunBVar.x2=x[1];
        System.out.println(gaFunBVar.x1+" "+gaFunBVar.x2);

        gaFunBVar.InitGroup();//初始群体
        System.out.println("初始随机创建一个群体 ：");
        for(int i=0;i<gaFunBVar.groupSize;i++)
        {
            System.out.println(gaFunBVar.group[i]);
        }

        while(gaFunBVar.noSwapNum<GENERATION)//进化几代后停止
        {
            //1.选择（轮盘赌方法）
            int rnewEntNum=(int)((1-gaFunBVar.r)*gaFunBVar.groupSize);//r：交叉取代群体成员的比例,0.5初始，群数量的一半
            System.out.println("直接从父代选择成员作为后代的个数 ："+rnewEntNum);
            gaFunBVar.chooseNewEnt(rnewEntNum);//按概率选择成员，不交叉的，直接保留，按照适应度的概率，概率高的容易留下

            //2.交叉
            int inter_pair=(int)(gaFunBVar.r*gaFunBVar.groupSize/2);//群数量的四分之一对，还是一半
            System.out.println("要交叉的对数 ："+inter_pair);
            gaFunBVar.intersect(inter_pair,rnewEntNum);//其余选择的交叉，所以只交叉一部分

            //3.变异
            gaFunBVar.variation(gaFunBVar.group, gaFunBVar.m);//m变异率
            System.out.println("变异后的群体为 ：");
            for(String str:gaFunBVar.group)
                System.out.println(str);
            //
            gaFunBVar.getMaxFitness(gaFunBVar.group);
        }

        System.out.println("最大的适应度为 ："+gaFunBVar.maxFitness);
        System.out.println("达到最大适应度的个体为 ："+gaFunBVar.maxFitEntity);
    }
}