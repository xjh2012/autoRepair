#include <stdio.h>

main(int argc,char *argv[])
{
    int i;
    int a;
    int b;
    if(argc<2)
    {
        fprintf(stdout,"Error:must have at least one integers");
	exit(1);
    }
    a=atoi(argv[1]);
    b=atoi(argv[2]);
    if(a<b)
        i=1;
    else
        i=0;
    printf("%d",i);
    exit(0);
}
