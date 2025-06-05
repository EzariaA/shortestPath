# Navigation System - Shortest Path Finder

This Java project implements a **directed weighted graph** to simulate a  map and uses **Dijkstra's algorithm** to find the shortest path between two buildings.

---

## 🧠 Features

- Graph built using custom `Vertex`, `Edge`, and `MyGraph` classes
- Supports:
  - Multiple valid paths
  - Cycles
  - Disconnected vertices
- Computes shortest path cost and displays the route
- Handles invalid user input gracefully

---

## 🛠 Tech Stack

- Java
- `java.util` collections
- Command-line I/O
- Dijkstra's Algorithm (custom implementation)

---

## 📂 File Structure

```
.
├── Vertex.java          # Represents a building/node
├── Edge.java            # Represents a weighted connection between buildings
├── MyGraph.java         # Graph structure + Dijkstra's algorithm
├── Path.java            # Represents the result of a shortest path
├── FindShortestPath.java # User-facing interactive console program
├── vertices.txt         # (optional) Input list of buildings
├── edges.txt            # (optional) Input list of paths and distances
```
---

## 🚀 How to Run

### 1. Compile the project:
```bash
javac *.java
```

### 2. Run the interactive program:
```bash
java FindShortestPath
```

## 📸Sample Output
```
Enter Start Building:
A
Enter End Building:
D
Shortest path: A B D
Total cost: 3
```

## ✅ Test Cases Covered

- Multiple shortest paths with same cost (A→B→D or A→C→D)
- Disconnected nodes (E is isolated)
- Same start and end building (A→A)
- Cycles (C→B→D still handled correctly)


## 📚 Learning Outcomes

- Custom graph implementation
- Using priority queues and adjacency lists
- Designing and testing data structures
- Applying algorithmic concepts to real-world scenarios

## 📬 Future Improvements

- Load graph from `.txt` files (e.g., `vertices.txt`, `edges.txt`)
- Add GUI (JavaFX or Swing)
- Visual path display with campus map

---
## 👩🏽‍💻 Author
**Ezaria Alexander**  
Computer Science Major  
GitHub:(https://github.com/EzariaA)
