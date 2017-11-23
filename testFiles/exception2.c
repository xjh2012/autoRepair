#include <stdio.h>//主函数
int main()
{
    int a;
	int i = 0;
	int x = 0;
	
	for(i = 0; i < 2; i ++){
		scanf("%d",&a);
		x += a;
	}
    printf("%d\n",x);//打印结果
    return 0;
}
