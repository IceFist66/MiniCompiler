#include <stdio.h>

void foo(long n, long k)
{
   long i;

   for (i = 0; i < n; i++)
   {
      printf("%ld %ld\n", i, k * i + 2);
   }
}