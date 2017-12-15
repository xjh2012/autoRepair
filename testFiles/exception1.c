#include <stdio.h>//主函数
int main()
{
    int a;
	int i = 0;
	int x = 0;
	
if(i == 0){
  x = 1;
}
	for(i = 0; i < 3; i ++){
		scanf("%d",&a);
		x += a;
	}
    printf("%d\n",x+1);//打印结果
    return 0;
}
