/ Rami Qamhieh, 1913 Project 2
class Poly{
  private Term first;
  private Term last;
  private class Term{
    private int coef;
    private int expo;
    private Term next;
    private Term(int coef, int expo, Term next){
      this.coef = coef;
      this.expo = expo;
      this.next = next;
    }
  }
  public Poly(){
    first = new Term(0, Integer.MAX_VALUE, null);
    last = new Term(0, Integer.MAX_VALUE, null);
  }
  public boolean isZero(){
    return this.first == null;
  }
  public Poly minus(){
    result = new Poly();
    temp = result.first;
    while(temp != null){
      z = -1*temp.coef;
      neg = new Term(z, temp.expo, null);
      result.append(neg);
      temp = result.first.next;
    }
  }
  public Poly plus(Poly that){
    result = new Poly();
    Term left = this.first;
    Term right = that.first;
    while(left != null && right != null){
      if(left.expo > right.expo){
        result.plus(left.coef, left.expo);
        left = left.next;
      }
      if(left.expo < right.expo){
        result.plus(right.coef, right.expo);
        right = right.next;
      }
      if(left.expo == right.expo){
        int c = left.coef + right.coef;
        if(c != 0){
          result.plus(c, left.expo);
        }
        left = left.next;
        right = right.next;
      }
    }
    if(left != null){
      result.last = left;
    }
    else{
      result.last = right;
    }
    return result;
  }
  public Poly plus(int coef, int expo){
    if(coef == 0 || expo < 0 || expo >= this.last){
      throw new IllegalArgumentException();
    }
    temp = new Term(coef, expo, null);
    this.last.next = temp;
    this.last = temp;
    return this;
  }
  public String toString(){
    StringBuilder ans;
    Term current = this.first;
    while(current != null){
      ans.append(current.coef + "x");
      ans.append(current.expo);
      ans.append(" + ");
      current = current.next;
    }
    return ans;
  }
}

// Test driver class below
class PollyEsther
{
    public static void main(String[] args)
    {
      Poly p = new Poly().plus(3,5).plus(2,4).plus(2,3).plus(−1,2).plus(5,0);
      Poly q = new Poly().plus(7,4).plus(1,2).plus(−4,1).plus(−3,0);
      Poly z = new Poly();

      System.out.println(p);                 // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰
      System.out.println(q);                 // 7x⁴ + 1x² - 4x¹ - 3x⁰
      System.out.println(z);                 // 0

      System.out.println(p.minus());         // -3x⁵ - 2x⁴ - 2x³ + 1x² - 5x⁰
      System.out.println(q.minus());         // -7x⁴ - 1x² + 4x¹ + 3x⁰
      System.out.println(z.minus());         // 0

      System.out.println(p.plus(q));         // 3x⁵ + 9x⁴ + 2x³ - 4x¹ + 2x⁰
      System.out.println(p.plus(z));         // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰
      System.out.println(p.plus(q.minus())); // 3x⁵ - 5x⁴ + 2x³ - 2x² + 4x¹ + 8x⁰
    }
}
