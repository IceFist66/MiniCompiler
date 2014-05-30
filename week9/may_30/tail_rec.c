
long fact(long n)
{
   if (n <= 1)
   {
      return 1;
   }
   else
   {
      return n * fact(n - 1);
   }
}

long factrec(long n, long acc)
{
   if (n <= 1)
   {
      return acc;
   }
   else
   {
      return factrec(n - 1, n * acc);
   }
}

long fact2(long n)
{
   return factrec(n, 1);
}
