#include <stdio.h>

int function (int n)
{
  int i; int j;
  if (n<=0)
  {
    return 0;
  }
  i=0;
  while (i<n*n)
  {
    j=i+n;
    printf("%d ", j);
    i=i+1;
  }
  return function(n-1);
}

int main()
{
  int num;
  scanf("%d",&num);
  function(num);

  printf("%d\n",0);
  return 0;
}

