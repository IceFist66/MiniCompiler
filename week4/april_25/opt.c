#include <stdio.h>
#include <stdlib.h>

long square(long x)
{
   return x * x;
}

void print_them(long a1, long a2, long a3, long a4, long a5, long a6, long a7)
{
   printf("%ld %ld %ld %ld %ld %ld %ld\n", a1, a2, a3, a4, a5, a6, a7);
}

int main(void)
{
   print_them(1, 2, 3, 4, 5, 6, square(10));

   return 0;
}
