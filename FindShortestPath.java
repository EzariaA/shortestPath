import java.util.*;

public class FindShortestPath
{
   public static void main(String[] args) 
   {
      // Create vertices
      Vertex A = new Vertex("A");
      Vertex B = new Vertex("B");
      Vertex C = new Vertex("C");
      Vertex D = new Vertex("D");
      Vertex E = new Vertex("E"); // Unconnected node

        // Create edges
      Edge AB = new Edge(A, B, 1);
      Edge AC = new Edge(A, C, 1);
      Edge CB = new Edge(C, B, 1); // Cycle
      Edge BD = new Edge(B, D, 2);
      Edge CD = new Edge(C, D, 2); // Two equal-cost paths A→B→D and A→C→D

        // Build graph
      List<Vertex> vertices = List.of(A, B, C, D, E);
      List<Edge> edges = List.of(AB, AC, CB, BD, CD);
      MyGraph graph = new MyGraph(vertices, edges);
      
      Scanner input = new Scanner(System.in);
      System.out.println("Enter Start Building");
      String startIn = input.nextLine();
      System.out.println("Enter End Building");
      String endIn = input.nextLine();
      
      Vertex start = null;
      Vertex end = null;
      
      for(Vertex v: vertices)
      {
         if(v.getLabel().equals(startIn)) start = v;
         if(v.getLabel().equals(endIn)) end = v;
      }
      
      if(start == null || end == null)
      {
         System.out.println("Invalid Building name.");
         return;
      }
      Path result = graph.shortestPath(start, end);
      if(result == null)
      {
         System.out.println("No path found");
      }
      else
      {
         System.out.println("Shortest path: " + result.vertices);
         System.out.println("Total cost: " + result.cost);
      }
   }
}
