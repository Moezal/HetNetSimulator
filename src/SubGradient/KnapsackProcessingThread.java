/*
 * Copyright (C) 2015 mohamad
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package SubGradient;

import HandOverAlgorithms.*;
import Calulation.CalculateSignalStrength;
import Calulation.NetworkCostFunction;
import Knapsack.Item;
import Knapsack.OverallSolution;
import Networks_Package.Network;
import Peers_Package.Peer;
import Peers_Package.ProphitWeightRatio;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class KnapsackProcessingThread extends Thread {

//    Network n;
    int networkInitial;
    double[][] globalCostFunction;
    double[][] userDataRate;
    double networkCapacity;
    int numberOfUsers;
    double[] result;
    boolean hasEnded;
//    public boolean isAlive = true;

    public KnapsackProcessingThread(int networkInitial, double[][] globalCostFunction, double[][] userDataRate, double networkCapacity, int numberOfUsers) {
//        this.n = n;
        hasEnded = false;
        this.networkCapacity = networkCapacity;
        this.networkInitial = networkInitial;
        this.globalCostFunction = globalCostFunction;
        this.userDataRate = userDataRate;
        this.numberOfUsers = numberOfUsers;
        result = new double[numberOfUsers];

        this.setPriority(MAX_PRIORITY);
    }

    @Override
    public void run() {
        long networkProcessingTimeStart = System.nanoTime();
        int totalRequestedDatarate = 0;
        for (int i = 0; i < numberOfUsers; i++) {
            if (globalCostFunction[i][networkInitial] != 0) {
                totalRequestedDatarate += userDataRate[i][networkInitial];
            }
        }
        if (totalRequestedDatarate <= networkCapacity) {
            for (int i = 0; i < numberOfUsers; i++) {
                if (globalCostFunction[i][networkInitial] != 0) {
                    result[i] = 1;
                } else {
                    result[i] = 0;
                }
            }
            hasEnded = true;
        } else {

        }
        List<Item> items = new LinkedList<Item>();
        for (int i = 0; i < numberOfUsers; i++) {
            items.add(new Item(i, globalCostFunction[i][networkInitial], userDataRate[i][networkInitial]));
        }
//                    System.out.println("number of items in optimization: "+items.size());
//            Knapsack.KnapsackSolver solver = new Knapsack.GreedySolver(items, n.getCapacity());
        Knapsack.KnapsackSolver solver = new Knapsack.DynamicProgrammingSolver(items, (int) networkCapacity);
//            System.out.println("algorithm used DP!!!!!!!");
//            Knapsack.KnapsackSolver solver = new Knapsack.BranchAndBoundSolver(items, n.getCapacity());
        OverallSolution s = solver.solve().getOverallSolution();
//        System.out.println("solved dp");
//        double remaining_capacity = 0;
//                    if (s.getOverall_weight() < n.getCapacity()) {
//                        remaining_capacity = n.getCapacity() - s.getOverall_weight();
//                    }
//                    System.out.println("optimization: " + s.getOverall_weight() + "/" + n.getCapacity());
        List<Integer> peersAccepted = s.getItemLabels();
//                    String s2 = "Items Lables: ";
//                    for (Item i : items) {
//                        s2 += i.label + ", ";
//                    }
//                    System.out.println(s2);
//                    System.out.println("Peers accepted: " + peersAccepted);

        for (int i = 0; i < numberOfUsers; i++) {
            if (peersAccepted.contains(i)) {
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }
        hasEnded = true;

    }

    public int getNetworkInitial() {
        return networkInitial;
    }

    public double[] getResult() {
        return result;
    }

    public boolean isHasEnded() {
        return hasEnded;
    }

}
