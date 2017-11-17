#include <iostream.h>     //包含头文件
#include <stdlib.h>
using namespace std;

double fuc(double x, double y) //定义函数
{
    if(y==0)
    {
        throw y;     //除数为0，抛出异常
    }
    return x*y;     //否则返回两个数的商
}

void main()
{
    double res;
    int * i = new int[10];
    try  //定义异常
    {
        res=fuc(4,5);
        cout<<"The result of x/y is : "<<res<<endl;
        res=fuc(4,0); //出现异常，函数内部会抛出异常
    }
    catch(int)             //捕获并处理异常
    {
         cerr<<"error of dividing zero.\n";
         exit(1);                //异常退出程序
    }
	if(y==0)
    {
        throw y;     //除数为0，抛出异常
    }
}