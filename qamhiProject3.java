import java.util.Random;
class ShuffleTree<Value>{
    private class Node{
        private String key;
        private Value value;
        private Node left;
        private Node right;
        private Node(String key, Value value){
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
    private String[] keys;
    private Value[] values;
    private int count;
    private Node root;
    private Random generator;
    public ShuffleTree(int size){
     if(size < 0){
          throw new IllegalArgumentException();
      }

      generator = new Random();
      int h = Math.abs(generator.nextInt())%size;
      keys = new String[h];
      values = (Value[]) new Object[h];
      count = 0;
      Node root;
    }
    private void flush(){
        count = keys.length;
        for(int i = 0; i < count-2; i++){
            int j = Math.abs(generator.nextInt())%(count-i);
            String x = keys[i];
            String y = keys[i+j];
            keys[i] = y;
            keys[i+j] = x;
            Value w = values[i];
            Value z = values[i+j];
            values[i] = z;
            values[i+j] = w;
        }
        Node subtree = root;
        for(int i = 0; i <= count; i++){
        int test = keys[i].compareTo(subtree.key);
        if (test < 0){
          if (subtree.left == null){
            subtree.left = new Node(keys[i], values[i]);
            return;
          }
          else{
            subtree = subtree.left;
          }
        }
        else if (test > 0){
          if (subtree.right == null){
            subtree.right = new Node(keys[i], values[i]);
            return;
          }
          else{
            subtree = subtree.right;
          }
        }
        else{
          subtree.value = values[i];
          return;
        }
      }
        for(int i = 0; i < count; i++){
            keys[i] = null;
            values[i] = null;
        }
    }
    public Value get(String key){
        if(keys[0] != null && values[0] != null){
            flush();
        }
        if(key == null)
            throw new IllegalArgumentException("Key is null");
        Node subtree = root;
        while(subtree != null){
            if(key.compareTo(subtree.key) < 0)
                subtree = subtree.left;
            else if(key.compareTo(subtree.key) > 0)
                subtree = subtree.right;
            else
                break;
        }
        Node temp = new Node(subtree.key, subtree.value);
        while(temp!=null){
            if(temp.key.equals(key))
                return temp.value;
            else
                temp = temp.right;
        }
        throw new IllegalArgumentException("Key is not on the list");
    }
    public int height(){
        if(keys[0] != null && values[0] != null){
            flush();
        }
        return height(root);
    }
    private int height(Node subtree){
        if(subtree == null){
            return 0;
        }
        else{
            int left = height(subtree.left);
            int right = height(subtree.right);
            if(left > right){
                return left + 1;
            }
            else{
                return right + 1;
            }
        }
    }
    public void put(String key, Value value){
        if(key == null)
            throw new IllegalArgumentException("Key is null");
        else if(count == keys.length){
            flush();
        }
        count ++;
        keys[count] = key;
        values[count] = value;
    }
}

//  SHUFFLE BORED. Test the SHUFFLE TREE class.

class ShuffleBored
{

//  RESERVED. A sorted array of some Java reserved names.

  private final static String[] reserved =
   { "abstract",     "assert",    "boolean",     "break",
     "byte",         "case",      "catch",       "char",
     "class",        "const",     "continue",    "default",
     "do",           "double",    "else",        "extends",
     "final",        "finally",   "float",       "for",
     "goto",         "if",        "implements",  "import",
     "instanceof",   "int",       "interface",   "long",
     "native",       "new",       "package",     "private",
     "protected",    "public",    "return",      "short",
     "static",       "super",     "switch",      "synchronized",
     "this",         "throw",     "throws",      "transient",
     "try",          "void",      "volatile",    "while" };

//  MAIN. Main program. Make a SHUFFLE TREE, whose keys are RESERVED names, and
//  whose values are INTs. Print its height, keys, and their values.

  public static void main(String[] args)
  {
    ShuffleTree<Integer> tree = new ShuffleTree<Integer>(30);

    for (int index = 0; index < reserved.length; index += 1)
    {
      tree.put(reserved[index], index);
    }

    System.out.println(tree.height());

    for (int index = 0; index < reserved.length; index += 1)
    {
      System.out.format("%02d %s", tree.get(reserved[index]), reserved[index]);
      System.out.println();
    }
  }
}
