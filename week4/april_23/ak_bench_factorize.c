#include <stdio.h>

int mod (int num, int i)
{
   int temp, temp1, temp2;
   temp = num / i;
   temp1 = temp * i;
   temp2 = num - temp1;
   return temp2;
}

void factorize (int num)
{
   int i, flag;
   i = 2;
   flag = 0;
   while ((i <= num) && (flag == 0))
   {
      if ((mod (num, i)) == 0)
      {
         printf("%d",i);
         factorize (num / i);
         flag = 1;
      }
      else 
      {
         i = i + 1;
      }
   }
}

int main ()
{
   int num;
   num = 3;
   factorize (num);
   printf("0\n");
   return 1;
}
