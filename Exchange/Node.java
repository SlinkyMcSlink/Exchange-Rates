public class Node {
   
   public String name;
   public double weight; 
   public Node next;

   public Node(String n, double w) {  // Connection Node
      name = n;
      weight = w; 
      next = null;
   }

   public Node(String n) {  // Vertex Node
      name = n;
      weight = 0.0;
      next = null; 
   }

   public String toString() {
      return "[" + name +", Weight: " + weight+ "]";
   }

   public String nodeInfo() {
      return "[Name: " + name +", Weight: " + weight + ", Next: " + next + "]";
   }

}