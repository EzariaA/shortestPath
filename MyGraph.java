/*
 * This class implements the graph representation by implementing the graph interface.
 */
import java.util.*;

/*
 * A representation of a graph.
 * Assumes that we do not have negative cost edges in the graph.
 */
public class MyGraph implements Graph 
{
	private int size; //saves the number of vertices
	private Set<Vertex> vertices;  // saves the vertices passed in
	private Set<Edge> edges;  // saves the edges passed in
	private Map<Vertex, Integer> vertexIndex; //assign each vertex with index, able to locate
	private Map<VertexTuple, Edge> edgesMap; //stores edges, able to find edge by key vertexTuple
	private ArrayList<LinkedList<Vertex>> adjacencyList;  //stores the graph

	/*
	 * Creates a MyGraph object with the given collection of vertices
	 * and the given collection of edges.
	 * @param v a collection of the vertices in this graph
	 * @param e a collection of the edges in this graph
	 */
	public MyGraph(Collection<Vertex> vs, Collection<Edge> es) 
   {
		// set up, assign all global variables
		// i.e HashSet to avoid duplicates
		size = vs.size();
		vertices = new HashSet<Vertex>();
		edges = new HashSet<Edge>();
		vertexIndex = new HashMap<Vertex, Integer>();
      Map<String, Vertex> labelMap = new HashMap<>();
		edgesMap = new HashMap<VertexTuple, Edge>();
		adjacencyList = new ArrayList<LinkedList<Vertex>>();
		int i = 0;
		
		// adds each vertex in vs into vertices and updates the corresponding data
		for (Vertex v : vs) 
      {
			vertices.add(v);
			vertexIndex.put(v, i);
         labelMap.put(v.getLabel(), v); 
         adjacencyList.add(new LinkedList<Vertex>());
			i++;
		}

		// adds each edge in es into edgeMap and updates the corresponding data
		for (Edge e : es) 
      {
			Vertex from = labelMap.get(e.getSource().getLabel());
         Vertex to = labelMap.get(e.getDestination().getLabel());			VertexTuple vt = new VertexTuple(from, to);
			if(edgesMap.containsKey(vt) && edgesMap.get(vt).getWeight()!=e.getWeight())
				throw new IllegalArgumentException("duplicate edges with different weight!");
			int fromIndex = vertexIndex.get(from);
			adjacencyList.get(fromIndex).add(to); //goes to the vertex from bucket, add to
			edgesMap.put(new VertexTuple(from, to), e);
			edges.add(e);
		}
	}

	/* 
	 * Return the collection of vertices of this graph
	 * @return the vertices as a collection (which is anything iterable)
	 */
	public Collection<Vertex> vertices() 
   {
		Set<Vertex> result = new HashSet<Vertex>();
		for(Vertex v: vertices)
			result.add(v);
		return result;
	}


	/* 
	 * Return the collection of edges of this graph
	 * @return the edges as a collection (which is anything iterable)
	 */
	public Collection<Edge> edges() 
   {
		Set<Edge> result = new HashSet<Edge>();
		for(Edge e: edges) //copies out
			result.add(e);
		return result;
	}

	/*
	 * Return a collection of vertices adjacent to a given vertex v.
	 *   i.e., the set of all vertices w where edges v -> w exist in the graph.
	 * Return an empty collection if there are no adjacent vertices.
	 * @param v one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException if v does not exist.
	 */
	public Collection<Vertex> adjacentVertices(Vertex v) 
   {
		checkExceptions(v);
		Set<Vertex> s = new HashSet<Vertex>(); //copies out
		for(Vertex temp: adjacencyList.get(vertexIndex.get(v)))
			s.add(temp);
		return s;
	}

	/*
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
	 * Assumes that we do not have negative cost edges in the graph.
	 * @param a one vertex
	 * @param b another vertex
	 * @return cost of edge if there is a directed edge from a to b in the graph, 
	 * return -1 otherwise.
	 * @throws IllegalArgumentException if a or b do not exist.
	 */
	public int edgeCost(Vertex a, Vertex b) 
   {
		checkExceptions(a);
		checkExceptions(b);
		
		if(edgesMap.containsKey(new VertexTuple(a,b)))
      {
			int i = edgesMap.get(new VertexTuple(a, b)).getWeight();
			return i;
		}
		return -1;
	}

	/*
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path.  Assumes all edge weights are nonnegative.
	 * Uses Dijkstra's algorithm.
	 * @param a the starting vertex
	 * @param b the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *   and contains a (first) and b (last) and the cost is the cost of 
	 *   the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) 
   {
      checkExceptions(a);
      checkExceptions(b);
      
      if(a.equals(b))
      {
         return new Path(List.of(a),0);
      }
      
      Map<Vertex, Integer> distance = new HashMap<>();
      Map<Vertex, Vertex> prev = new HashMap<>();
      PriorityQueue<AugmentedVertex> PQ = new PriorityQueue<>();
      Set<Vertex> visited = new HashSet<>();
      
      for(Vertex v: vertices)
      {
         distance.put(v, Integer.MAX_VALUE);
      }
      
      distance.put(a,0);
      PQ.add(new AugmentedVertex(a,0));
      
      while (!PQ.isEmpty())
      {
         AugmentedVertex current = PQ.poll();
         Vertex u = current.v;
         
         if (visited.contains(u)) continue;
         visited.add(u);
         
         for (Vertex neighbor : adjacencyList.get(vertexIndex.get(u)))
         {
            int weight = edgeCost(u, neighbor);
            if (weight < 0) continue;
            int alt = distance.get(u) + weight;
            if (alt < distance.get(neighbor)) 
            {
               distance.put(neighbor, alt);
               prev.put(neighbor, u);
               PQ.add(new AugmentedVertex(neighbor, alt));
            }
         }
      }
      
      if (!distance.containsKey(b) || distance.get(b) == Integer.MAX_VALUE) 
      {
         return null; // No path found
      }
      LinkedList<Vertex> path = new LinkedList<>();
      Vertex step = b;
      while (step != null) 
      {
         path.addFirst(step);
         step = prev.get(step);
      }
      
      return new Path(path, distance.get(b));

	}
   
	
	// helper method, checks if parameter vertex a and b are vertices exist in the graph
	private void checkExceptions(Vertex a)
   {
		if((!vertices.contains(a)))
			throw new IllegalArgumentException("vertex passed in does not exist!");
	}
	
	// implementation of AugmentedVertex which enables us to compare two vertex according to the cost
	// and to find the prev vertex on the path
	private class AugmentedVertex implements Comparable<AugmentedVertex> 
   {
		private final Vertex v; 
		private int cost;
		private Vertex prev;
		
		public AugmentedVertex(Vertex v, int cost) 
      {
			this.v = v;
			this.cost = cost;
		}
		
      public int compareTo(AugmentedVertex other) 
      {
			return cost - other.cost;
		}
	}

	// Implementation of vertexTuple so we can construct vertexTuple, get hash code, and compare
	private class VertexTuple 
   {
		private final Vertex from; //stores the starting vertex 
		private final Vertex to; //stores the destination vertex
		
		//constructor
		public VertexTuple(Vertex from, Vertex to) 
      {
			this.from = from;
			this.to = to;
		}
		
		//auto-generated: hashes on from and to
		public int hashCode() 
      {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			return result;
		}
		
		// makes it comparable by comparing from and to vertices
		public boolean equals(Object obj) 
      {
			if (this == obj) //if equal
				return true;
			if (obj == null) //if obj passed in null 
				return false;
			if (getClass() != obj.getClass()) //compares the runtime class of the object
				return false;
			final VertexTuple other = (VertexTuple) obj;
			
			if (from == null) 
         {
				if (other.from != null)
					return false;  //from: a.null b.not null
			} 
         else if (!from.equals(other.from)) 
         { 
				return false; //from are different
			}
			if (to == null) //two from are same here
				return other.to == null; //if both to null
			else 
				return to.equals(other.to); //if two to are the same
		}
	}
}
