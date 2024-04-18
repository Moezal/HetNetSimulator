package Knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class GreedySolver extends KnapsackSolver {
   
   public GreedySolver(List<Item> items, int capacity) {
      super(items, capacity);
   }
   
   @Override
   public KnapsackSolution solve() {
   
      KnapsackSolution greedy = new KnapsackSolution();
      
      greedy.items = new ArrayList<Item>(items);
      
      Collections.sort(greedy.items, Item.byRatio());
      
      double capUsed = 0;
      double value = 0;
      int i;
      List<Item> temp_greedy_items = new ArrayList<Item>();
      
      for (i = 0; i < greedy.items.size(); i++) {
         Item item = greedy.items.get(i);
         if (capUsed + item.weight > capacity) continue;
         temp_greedy_items.add(item);
         capUsed += item.weight;
         value += item.value;
      }
      
      greedy.items = temp_greedy_items;
      greedy.weight = capUsed;
      greedy.value = value;
      greedy.approach = "Greedy solution (not necessarily optimal)";
      
      return greedy;
   }
}
