package Knapsack;

import java.util.Collections;
import java.util.List;




public class KnapsackSolution {

    String approach;
    public List<Item> items;
    public double weight;
    public double value;

    public OverallSolution getOverallSolution() {
        OverallSolution s = new OverallSolution();
        s.setSolution_name(approach);
        s.setOverall_value(value);
        s.setOverall_weight(weight);
        s.setItems(items);
        return s;

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(approach);
        builder.append(": ");
        builder.append(value);
        builder.append(" ");
        builder.append(weight);
        builder.append("\n");

        Collections.sort(items, Item.byLabel());

        for (Item item : items) {
            builder.append(item.label);
            builder.append(" ");
        }

        return builder.toString();
    }
}
