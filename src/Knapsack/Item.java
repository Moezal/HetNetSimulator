package Knapsack;

import java.util.Comparator;



public class Item {

    public int label;
    public double value;
    public double weight;

    public Item(int label, double value, double weight) {
        this.label = label;
        this.value = value;
        this.weight = weight;
    }

    public Item() {
    }

    public static Comparator<Item> byLabel() {
        return new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                return i1.label - i2.label;
            }
        };
    }

    public static Comparator<Item> byRatio() {
        return new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                return Double.compare(i2.getRatio(), i1.getRatio());
            }
        };
    }

    public double getRatio() {
        return value / weight;
    }
}
