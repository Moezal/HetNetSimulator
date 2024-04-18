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
package HandOverAlgorithms;

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
public class NetworkProcessingThread extends Thread {

    Network n;
//    public boolean isAlive = true;

    public NetworkProcessingThread(Network n) {
        this.n = n;
        this.setPriority(MAX_PRIORITY);
    }

    @Override
    public void run() { 
        long networkProcessingTimeStart = System.nanoTime();
        if (n.getPeersRequestingConnection().size() != 0) {
            int numberOfUsersInTheList = n.getPeersRequestingConnection().size();
//                    for (Peer py : n.getPeersRequestingConnection()) {
//                        if (py.getPreviousConnectedNetwork() == n.getId()) {
//                            continue;
//                        }
//                        numberOfUsersInTheList++;
//                    }
            if (numberOfUsersInTheList > 0) {
                n.setTotalNumberOfUsersInList(n.getTotalNumberOfUsersInList() + numberOfUsersInTheList);
                n.setNumberOfRequestListsReceived(n.getNumberOfRequestListsReceived() + 1);
            }
        }
        if (n.getFreeCapacity() >= n.getTotalRequestedDataRate()) {
            for (Peer p : n.getPeersRequestingConnection()) {
                p.setConnected(true);
                p.setConnectedNetwork(n);
                n.getConnectedPeers().add(p);
                n.setUsed_capacity(n.getUsed_capacity() + p.getDatarate());
                p.setConnected_network_rank(p.getNetworkRank(n));
                p.setCurrentConnectedNetwork(n.getId());
            }
            n.setPeersRequestingConnection(new LinkedList<Peer>());
        } else {
            List<Item> items = new LinkedList<Item>();
            List<Peer> peersToBeProcessed = new LinkedList<Peer>();
            peersToBeProcessed.addAll(n.getConnectedPeers());
            peersToBeProcessed.addAll(n.getPeersRequestingConnection());
            double max_s = 0, max_mc = 0, max_pc = 0, min_s = 0, min_mc = 0, min_pc = 0;
            double overall_requested_BUs = 0;
            double overall_pc = 0;
            double overall_mc = 0;
            double overall_s = 0;
            for (Peer p1 : peersToBeProcessed) {
                double s = CalculateSignalStrength.calculate_signal_strength(p1, n);
                overall_s += s;
                overall_pc += n.getPc(p1.getDatarate());
                overall_mc += p1.getDatarate() * n.getMc();
                overall_requested_BUs += p1.getDatarate();
                if (max_s == 0) {
                    max_s = s;
                }
                if (max_mc == 0) {
                    max_mc = p1.getDatarate() * n.getMc();
                }
                if (max_pc == 0) {
                    max_pc = n.getPc(p1.getDatarate());
                }
                if (min_s == 0) {
                    min_s = s;
                }
                if (min_mc == 0) {
                    min_mc = p1.getDatarate() * n.getMc();
                }
                if (min_pc == 0) {
                    min_pc = n.getPc(p1.getDatarate());
                }
                if (max_s < s) {
                    max_s = s;
                }
                if (max_mc < (p1.getDatarate() * n.getMc())) {
                    max_mc = p1.getDatarate() * n.getMc();
                }
                if (max_pc < n.getPc(p1.getDatarate())) {
                    max_pc = n.getPc(p1.getDatarate());
                }
                if (min_s > s) {
                    min_s = s;
                }
                if (min_mc > (p1.getDatarate() * n.getMc())) {
                    min_mc = p1.getDatarate() * n.getMc();
                }
                if (min_pc > n.getPc(p1.getDatarate())) {
                    min_pc = n.getPc(p1.getDatarate());
                }
            }
            n.setUsed_capacity(0);
            n.setConnectedPeers(new LinkedList<Peer>());
            n.setPeersRequestingConnection(new LinkedList<Peer>());
            for (Peer p1 : peersToBeProcessed) {
                items.add(new Item(p1.getPeerID(), (NetworkCostFunction.calculate_Network_cost_function(p1, n, max_mc, max_pc, max_s, min_mc, min_pc, min_s, overall_requested_BUs, overall_mc, overall_pc, overall_s)), ((p1.getDatarate()))));
            }
//                    System.out.println("number of items in optimization: "+items.size());
//            Knapsack.KnapsackSolver solver = new Knapsack.GreedySolver(items, n.getCapacity());
            Knapsack.KnapsackSolver solver = new Knapsack.DynamicProgrammingSolver(items, n.getCapacity());
//            System.out.println("algorithm used DP!!!!!!!");
//            Knapsack.KnapsackSolver solver = new Knapsack.BranchAndBoundSolver(items, n.getCapacity());
            OverallSolution s = solver.solve().getOverallSolution();
            double remaining_capacity = 0;
//                    if (s.getOverall_weight() < n.getCapacity()) {
//                        remaining_capacity = n.getCapacity() - s.getOverall_weight();
//                    }
//                    System.out.println("optimization: " + s.getOverall_weight() + "/" + n.getCapacity());
            List<Integer> peersAccepted = s.getItemLabels();
            List<Peer> rejectedPeers = new LinkedList<Peer>();
//                    String s2 = "Items Lables: ";
//                    for (Item i : items) {
//                        s2 += i.label + ", ";
//                    }
//                    System.out.println(s2);
//                    System.out.println("Peers accepted: " + peersAccepted);
            for (Peer tempP : peersToBeProcessed) {
                if (!peersAccepted.contains(tempP.getPeerID())) {

                    rejectedPeers.add(tempP);

                }
                if (peersAccepted.contains(tempP.getPeerID())) {
                    tempP.setConnected(true);
                    tempP.setConnectedNetwork(n);
                    n.getConnectedPeers().add(tempP);
                    n.setUsed_capacity(n.getUsed_capacity() + tempP.getDatarate());
                    tempP.setConnected_network_rank(tempP.getNetworkRank(n));
                    tempP.setCurrentConnectedNetwork(n.getId());
                }
            }
            List<ProphitWeightRatio> rejectedPeersProfitWeight = new LinkedList<ProphitWeightRatio>();
            double max_s_rejectedPeersProfitWeight = 0, max_mc_rejectedPeersProfitWeight = 0, max_pc_rejectedPeersProfitWeight = 0, min_s_rejectedPeersProfitWeight = 0, min_mc_rejectedPeersProfitWeight = 0, min_pc_rejectedPeersProfitWeight = 0;
            double overall_requested_BUs_rejectedPeersProfitWeight = 0;
            double overall_pc_rejectedPeersProfitWeight = 0;
            double overall_mc_rejectedPeersProfitWeight = 0;
            double overall_s_rejectedPeersProfitWeight = 0;
            for (Peer rpp : rejectedPeers) {
                double s_rejectedPeersProfitWeight = CalculateSignalStrength.calculate_signal_strength(rpp, n);
                overall_s_rejectedPeersProfitWeight += s_rejectedPeersProfitWeight;
                overall_pc_rejectedPeersProfitWeight += n.getPc(rpp.getDatarate());
                overall_mc_rejectedPeersProfitWeight += rpp.getDatarate() * n.getMc();
                overall_requested_BUs_rejectedPeersProfitWeight += rpp.getDatarate();
                if (max_s_rejectedPeersProfitWeight == 0) {
                    max_s_rejectedPeersProfitWeight = s_rejectedPeersProfitWeight;
                }
                if (max_mc_rejectedPeersProfitWeight == 0) {
                    max_mc_rejectedPeersProfitWeight = rpp.getDatarate() * n.getMc();
                }
                if (max_pc_rejectedPeersProfitWeight == 0) {
                    max_pc_rejectedPeersProfitWeight = n.getPc(rpp.getDatarate());
                }
                if (min_s_rejectedPeersProfitWeight == 0) {
                    min_s_rejectedPeersProfitWeight = s_rejectedPeersProfitWeight;
                }
                if (min_mc_rejectedPeersProfitWeight == 0) {
                    min_mc_rejectedPeersProfitWeight = rpp.getDatarate() * n.getMc();
                }
                if (min_pc_rejectedPeersProfitWeight == 0) {
                    min_pc_rejectedPeersProfitWeight = n.getPc(rpp.getDatarate());
                }
                if (max_s_rejectedPeersProfitWeight < s_rejectedPeersProfitWeight) {
                    max_s_rejectedPeersProfitWeight = s_rejectedPeersProfitWeight;
                }
                if (max_mc_rejectedPeersProfitWeight < (rpp.getDatarate() * n.getMc())) {
                    max_mc_rejectedPeersProfitWeight = rpp.getDatarate() * n.getMc();
                }
                if (max_pc_rejectedPeersProfitWeight < n.getPc(rpp.getDatarate())) {
                    max_pc_rejectedPeersProfitWeight = n.getPc(rpp.getDatarate());
                }
                if (min_s_rejectedPeersProfitWeight > s_rejectedPeersProfitWeight) {
                    min_s_rejectedPeersProfitWeight = s_rejectedPeersProfitWeight;
                }
                if (min_mc_rejectedPeersProfitWeight > (rpp.getDatarate() * n.getMc())) {
                    min_mc_rejectedPeersProfitWeight = rpp.getDatarate() * n.getMc();
                }
                if (min_pc_rejectedPeersProfitWeight > n.getPc(rpp.getDatarate())) {
                    min_pc_rejectedPeersProfitWeight = n.getPc(rpp.getDatarate());
                }
            }
            for (Peer rp : rejectedPeers) {
                rejectedPeersProfitWeight.add(new ProphitWeightRatio(NetworkCostFunction.calculate_Network_cost_function(rp, n, max_mc_rejectedPeersProfitWeight, max_pc_rejectedPeersProfitWeight, max_s_rejectedPeersProfitWeight, min_mc_rejectedPeersProfitWeight, min_pc_rejectedPeersProfitWeight, min_s_rejectedPeersProfitWeight, overall_requested_BUs_rejectedPeersProfitWeight, overall_mc_rejectedPeersProfitWeight, overall_pc_rejectedPeersProfitWeight, overall_s_rejectedPeersProfitWeight) / rp.getDatarate(), rp));
            }
            Collections.sort(rejectedPeersProfitWeight);
//            System.out.println(rejectedPeersProfitWeight.toString());
            List<Integer> rejectedPeersAccepted = new LinkedList<Integer>();
            for (int i = 0; i < rejectedPeersProfitWeight.size(); i++) {
                if (n.getFreeCapacity() == 0) {
                    break;
                } else {

                    if ((n.getUsed_capacity() + rejectedPeersProfitWeight.get(i).getPeer().getDatarate()) <= n.getCapacity()) {
                        rejectedPeersAccepted.add(rejectedPeersProfitWeight.get(i).getPeer().getPeerID());
                        rejectedPeersProfitWeight.get(i).getPeer().setConnected(true);
                        rejectedPeersProfitWeight.get(i).getPeer().setConnectedNetwork(n);
                        n.getConnectedPeers().add(rejectedPeersProfitWeight.get(i).getPeer());
                        n.setUsed_capacity(n.getUsed_capacity() + rejectedPeersProfitWeight.get(i).getPeer().getDatarate());
                        rejectedPeersProfitWeight.get(i).getPeer().setConnected_network_rank(rejectedPeersProfitWeight.get(i).getPeer().getNetworkRank(n));
                        rejectedPeersProfitWeight.get(i).getPeer().setCurrentConnectedNetwork(n.getId());
                        System.err.println("added user that the algorithm rejected");
                    }
                }
            }
            for (Peer rp : rejectedPeers) {
                if (!rejectedPeersAccepted.contains(rp.getPeerID())) {
                    rp.setConnected(false);
                    rp.setNetworkRank(n, 0);
                    rp.setCurrentConnectedNetwork(0);
                }
            }
//                    System.out.println("utilization: " + qwe + "/" + n.getCapacity());
//                    System.out.println("utilization with optimization: " + n.getUsed_capacity() + "/" + n.getCapacity());
        }
        if (n.getCapacity() < n.getUsed_capacity()) {
            System.err.println("Error Used capacity > capacity\n "
                    + "Error Used capacity > capacity\n"
                    + "Error Used capacity > capacity\n"
                    + "Error Used capacity > capacity\n"
                    + "Error Used capacity > capacity\n"
                    + "Error Used capacity > capacity\n");
        }
//        long networkProcessingTimeEnd = System.nanoTime();
//        double processingTimeForThisIteration = (networkProcessingTimeEnd - networkProcessingTimeStart) / (double) 1000000;
//        isAlive = false;

    }

}
