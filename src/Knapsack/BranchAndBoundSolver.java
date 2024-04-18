package Knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;



public class BranchAndBoundSolver extends KnapsackSolver {

    private class Node implements Comparable<Node> {

        public int h;
        List<Item> taken;
        public double bound;
        public double value;
        public double weight;

        public Node() {
            taken = new ArrayList<Item>();
        }

        public Node(Node parent) {
            h = parent.h + 1;
            taken = new ArrayList<Item>(parent.taken);
            bound = parent.bound;
            value = parent.value;
            weight = parent.weight;
        }

        // Sort by bound
        public int compareTo(Node other) {
            return (int) (other.bound - bound);
        }

        public void computeBound() {
            int i = h;
            double w = weight;
            bound = value;
            Item item;
            do {
                item = items.get(i);
                if (w + item.weight > capacity) {
                    break;
                }
                w += item.weight;
                bound += item.value;
                i++;
            } while (i < items.size());
            bound += (capacity - w) * (item.value / item.weight);
        }
    }

    public BranchAndBoundSolver(List<Item> items, int capacity) {
        super(items, capacity);
    }

    @Override
    public KnapsackSolution solve() {

        Collections.sort(items, Item.byRatio());

        Node best = new Node();
        Node root = new Node();
        root.computeBound();

        PriorityQueue<Node> q = new PriorityQueue<Node>();
        q.offer(root);

        while (!q.isEmpty()) {
//            System.out.println(""+Runtime.getRuntime().totalMemory());
            if (((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024)) > (3 * 1024)) {
                System.out.println("inside if");
                System.gc();
            }
            Node node = q.poll();
            Node with = new Node(node);
            Node without = new Node(node);
            if (node.bound > best.value && node.h < items.size() - 1) {

//                Node with = new Node(node);
                with.h = node.h + 1;
//                with.taken = new ArrayList<Item>(node.taken);
                without.taken.clear();
                without.taken.addAll(node.taken);
                with.bound = node.bound;
                with.value = node.value;
                with.weight = node.weight;
                Item item = items.get(node.h);
                with.weight += item.weight;

                if (with.weight <= capacity) {

                    with.taken.add(items.get(node.h));
                    with.value += item.value;
                    with.computeBound();

                    if (with.value > best.value) {
                        best = with;
                    }
                    if (with.bound > best.value) {
                        q.offer(with);
                    }
                }
                without.h = node.h + 1;

//                without.taken = new ArrayList<Item>(node.taken);
                without.taken.clear();
                without.taken.addAll(node.taken);
//                without.taken.get(2).
                without.bound = node.bound;
                without.value = node.value;
                without.weight = node.weight;
//                Node without = new Node(node);
                without.computeBound();

                if (without.bound > best.value) {
                    q.offer(without);
                }
            }
        }

        KnapsackSolution solution = new KnapsackSolution();
        solution.value = best.value;
        solution.weight = best.weight;
        solution.items = best.taken;
        solution.approach = "Using Branch and Bound the best feasible solution found";

        return solution;
    }
}
