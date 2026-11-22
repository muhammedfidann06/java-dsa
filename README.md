# 🧮 Data Structures & Algorithms in Java

[![ci](https://github.com/muhammedfidann06/java-dsa/actions/workflows/ci.yml/badge.svg)](https://github.com/muhammedfidann06/java-dsa/actions/workflows/ci.yml)

Classic data structures and algorithms implemented **from scratch** in Java 21 – lists, stack, queue, heap, hash map and tree are built on plain arrays and nodes (the graph algorithms then use JDK collections). Every class is tested with JUnit 5 (incl. randomized tests against the JDK), and coverage is measured with JaCoCo.

## Contents
| Area | Implementation | Complexity |
|---|---|---|
| Lists | `DynamicArray` (growing array), `SinglyLinkedList` (reverse in place, middle with fast/slow pointers) | add O(1)*, get O(1) / O(n) |
| Stack & queue | `ArrayStack` (+ bracket check, RPN calculator), `RingBufferQueue` | O(1)* |
| Heap | `MinHeap` with custom `Comparator` | add/poll O(log n) |
| Hashing | `ChainedHashMap` (separate chaining, resize at load factor 0.75), `LruCache` (hash map + doubly linked list) | O(1) average |
| Trees | `BinarySearchTree` (put, get, remove with all 3 cases), `AvlTree` (self-balancing, 4 rotation cases), `Trie` (prefix search, autocomplete) | O(h), O(log n), O(L) |
| Sorting & selection | insertion, merge (hybrid), quick (random pivot, 3-way partition), heap, counting, binary search, `QuickSelect` (k-th smallest, median) | see Javadoc table, QuickSelect O(n) avg |
| Graphs | `Graph` – BFS, DFS, components, Dijkstra with path, topological sort (Kahn); `BellmanFord` (negative weights, cycle detection); `Kruskal` MST | O(V + E), O((V+E) log V), O(V·E), O(E log E) |
| Disjoint sets | `UnionFind` with path compression and union by size | ~O(1) |
| Range queries | `FenwickTree` (prefix/range sums with updates), `SegmentTree` (range minimum with updates) | O(log n) |
| Strings | `Kmp` – Knuth-Morris-Pratt search with prefix function, finds overlapping matches | O(n + m) |
| Dynamic programming | edit distance (Levenshtein), longest common subsequence, 0/1 knapsack, longest increasing subsequence (patience sorting) | O(n·m), O(n·W), O(n log n) |
| More graphs | `FloydWarshall` (all pairs, negative cycle check), `StronglyConnected` (Tarjan, iterative – no stack overflow) | O(V³), O(V + E) |
| Graphs, part 3 | `Prim` MST (checked against Kruskal), `Bipartite` (BFS two-colouring, finds odd cycles) | O(E log V), O(V + E) |
| Hashing, part 2 | `RabinKarp` (rolling hash search, verified on hash match), `BloomFilter` (probabilistic set, optimal m and k from n and p) | O(n + m) avg, O(k) |
| Number theory | gcd/lcm (Euclid), modular exponentiation, sieve of Eratosthenes, prime factorization | O(log n), O(n log log n) |

\* amortized

## Run
```bash
mvn verify                                                     # tests + coverage report
java -cp target/classes io.github.muhammedfidann06.dsa.Benchmark  # compare the sorting algorithms
```
The CI also runs the benchmark and uploads the coverage report as an artifact.

## Testing approach
- **Edge cases:** empty, single element, sorted, reversed, many duplicates, negative numbers
- **Randomized comparison:** `ChainedHashMap` vs. `java.util.HashMap`, `BinarySearchTree` vs. `TreeSet` – thousands of random operations with a fixed seed
- **Parameterized tests:** every sorting algorithm gets the same inputs

## Kurzbeschreibung (Deutsch)
Klassische Datenstrukturen und Algorithmen, selbst programmiert in Java: Listen, Stapel, Warteschlange, Heap,
Hash-Tabelle, Suchbaum, Sortierverfahren und Graph-Algorithmen (BFS, DFS, Dijkstra).
Alles ist mit JUnit getestet. Die Kommentare im Code erklären die Ideen und die Laufzeit.
