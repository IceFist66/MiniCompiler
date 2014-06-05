int main(int ke)
{
   int i, j, k;
   bool b;
   int h;
   {
      b = true;
      i = 3;
      j = 4;
      k = i - j + 3;
   }
   while(i < 5){
      i = i + 1;
   }
   k = simple(i, j);
   return k;
}

int simple(int x, int y)
{
   return x+y;
}
