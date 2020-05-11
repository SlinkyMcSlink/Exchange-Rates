import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.text.DecimalFormat;

public class Exchange {

   public static Scanner userIn = new Scanner(System.in);
   public static DecimalFormat df = new DecimalFormat("#.##");
   public static Random rand = new Random();
   public static Scanner fileIn;
   public static ArrayList<Node> adjList;
   public static Node startingCurrency, currentCurr; 
   public static double investment;
   
   public static void main(String[] args) {
      df.setGroupingUsed(true);
      df.setGroupingSize(3);
      start();
      menu();
      
   }

   public static void start() {
      System.out.println("Enter exchange rate file: ");
      String fileName = userIn.nextLine();
      try {
         fileIn = new Scanner(new File(fileName)); 
      }
      catch (Exception e) {
         System.out.println("Error!");
         System.exit(0);
      }
      System.out.println("File Scanner Made.");
      createGraph();
  //    for(int i = 0; i < adjList.size(); i++) {
  //        printEdges(adjList.get(i));
  //    }
   }

   public static void menu() {
      System.out.println("Hello! Let's make some money!");
      while (true) {
         System.out.println("Starting currency?");
         System.out.println("Enter your currency choice:");
         for (int i = 0; i < adjList.size(); i++) {
            System.out.println("> " + adjList.get(i).name);
         }
         String sc = userIn.nextLine();
         int ind  = -1;
         for (int i = 0; i < adjList.size(); i++) {
            if(sc.equals(adjList.get(i).name)) {
               ind = i;
            }
         }
         if (ind != -1) {
            System.out.println("Enter investment amount:");
            investment = userIn.nextDouble();
            userIn.nextLine();
            startingCurrency = adjList.get(ind);
            System.out.println("Starting Trading...");
            trading();
            System.out.println("Start again? (y/n)");
            sc = userIn.nextLine();
            if (sc.equals("y") || sc.equals("y")) {}
            else if (sc.equals("n") || sc.equals("N")) {
               System.out.println("Thank you for playing, goodbye!");
               System.exit(0);
            }
            else {
               System.out.println("I'll take that as being done...");
               System.exit(0);
            }
         }
         else {
            System.out.println(sc + " isn't a currency in the list, try again.");
         }

      }
   }


   public static void trading() {
      int numTrades = rand.nextInt(9)+2;
      boolean done = false;
      Node current;
      Node jumpPointer;
      ArrayList<String> path = new ArrayList<String>();

      while (!done) {
         double tempInvest = investment;
         current = startingCurrency;
         path.add(current.name);
         int ind;
         for (int i = 0; i < numTrades; i++) {
            jumpPointer = current;
            ind = rand.nextInt(8)+1;
            for (int j = 0; j < ind; j++) {
               jumpPointer = jumpPointer.next;
            }
            for (int j = 0; j < adjList.size(); j++) {
               if(jumpPointer.name.equals(adjList.get(j).name)) {
                  ind = j;
               }
            }
            current = adjList.get(ind);
            path.add(current.name);
            tempInvest *= jumpPointer.weight;
         }
         if (current.name.equals(startingCurrency.name)) {}
         else {
            jumpPointer = current.next;
            for (int j = 0; j < 9; j++) {
               if(jumpPointer.name.equals(startingCurrency.name)) {
                  path.add(jumpPointer.name);
                  tempInvest *= jumpPointer.weight;
                  j = 10;
               }
               else {
                  jumpPointer = jumpPointer.next;
               }
            }
         }
         
         if (tempInvest <= investment) {
            System.out.println("No profit was made on current path, restarting...");
            path.clear();
         }
         else {
            System.out.println("");
            System.out.println("Profitable path found!");
            System.out.println("Ending Investment: $" + df.format(tempInvest));
            System.out.println("Profit made: $" + df.format((tempInvest - investment)));
            System.out.println("Path:");
            for (int k = 0; k < path.size()-1; k++) {
               System.out.print(path.get(k) + " ---> ");
            }
            System.out.println(path.get(path.size()-1));
            System.out.println("");
            done = true;
         }
      }
   }

   public static void createGraph() {
      String line = fileIn.nextLine();
      while(!line.contains("***")) {
         line = fileIn.nextLine();
      }
      int vNum = Integer.parseInt(fileIn.nextLine());
     // debug("vNum: " + vNum);
      adjList = new ArrayList<Node>();
      while (fileIn.hasNext()) {
         String a = fileIn.next();
         String b = fileIn.next();
         double c = fileIn.nextDouble();
         fileIn.nextLine();
    //     debug("From: " + a + ", To: " + b + ", Rate: " + c);
         boolean foundA = false;
         boolean foundB = false;
         for (int i = 0; i < adjList.size(); i++) {
            if (adjList.get(i).name.equals(a)) {
               foundA = true;
            }
            if (adjList.get(i).name.equals(b)) {
               foundB = true;
            }
          }
          if (! foundA) {
             adjList.add(new Node(a));
          }
          if (! foundB) {
             adjList.add(new Node(b));
          }
          addLink(a, b, c);
          addLink(b, a, (1/c));
      }
 //     debug("NumVert: " + adjList.size());
   }

   public static void addLink(String a, String b, double c) {
      Node current = null;
      for (int i = 0; i < adjList.size(); i++) {
         if (adjList.get(i).name.equals(a)) {
            current = adjList.get(i);
         }
      }
      while (current.next != null) {
         if (current.name.equals(b)) {
            return;
         }
         else {
            current = current.next;
         }
      }
      if (current.name.equals(b)) {
         return;
      }
      else {
         current.next = new Node(b, c);
      }

   }

   public static void printEdges(Node vertex) {
      Node current = vertex;
      System.out.println("Vertex: " + current.name);
      while (current != null) {
         System.out.println(current);
         current = current.next;
      }
      System.out.println("");
   }

   public static void debug(String thing) {
      System.out.println(thing);
   }
   
}