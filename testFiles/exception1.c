#include <stdio.h>//主函数
int max(int x, int y){
        int max;
        if(x > y){
            max = x;
        }
        else{
            max = y;
        }
        return max;
    }
int main()
{  
        int x = 4
        int y = 5
       printf("%d\n", max(x,y));
    
}
