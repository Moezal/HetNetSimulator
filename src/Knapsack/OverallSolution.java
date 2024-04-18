package Knapsack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mohamad
 */
public class OverallSolution {

    private String solution_name;
    private double overall_weight, overall_value;
    private List<Item> items;

    public void setSolution_name(String solution_name) {
        this.solution_name = solution_name;
    }

    public void setOverall_weight(double overall_weight) {
        this.overall_weight = overall_weight;
    }

    public void setOverall_value(double overall_value) {
        this.overall_value = overall_value;
    }

    public String getSolution_name() {
        return solution_name;
    }

    public double getOverall_weight() {
        return overall_weight;
    }

    public double getOverall_value() {
        return overall_value;
    }

    public List<Item> getItems() {
        Collections.sort(items, Item.byLabel());
        return items;
    }

    public List getItemLabels() {
        List<Item> itemList = getItems();
        List<Integer> result = new LinkedList<Integer>();
        for (int i = 0; i < itemList.size(); i++) {
            result.add(itemList.get(i).label);
        }
        return result;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
