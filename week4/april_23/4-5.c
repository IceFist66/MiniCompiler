#include <stdio.h>

int i,j;

void simple(int x); //Had to declare this first

int main(int ke)
{
    i = 0;
    printf("%d",i);
    simple(i);
    return 0;
}

void simple(int x)
{
   int k;
   j = 3;
   k = i+j;
   printf("%d\n", k);
}
