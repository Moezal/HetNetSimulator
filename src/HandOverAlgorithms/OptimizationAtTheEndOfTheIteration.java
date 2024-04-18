/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HandOverAlgorithms;

import Calulation.CalculateSignalStrength;
import Calulation.NetworkCostFunction;
import Knapsack.Item;
import Knapsack.OverallSolution;
import Networks_Package.NetowrkRank;
import Networks_Package.Network;
import Peers_Package.Peer;
import Peers_Package.ProphitWeightRatio;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class OptimizationAtTheEndOfTheIteration {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {
        LinkedList<Double> maxProcessingTimePerIterationForNetworks = new LinkedList<Double>();
        LinkedList<Double> ProcessingTimePerIterationForHOE = new LinkedList<Double>();
        List<Peer> peers = new LinkedList<Peer>();
        peers.addAll(Peers);
        for (Network n : Networks) {
            n.setUsed_capacity(0);
            n.setConnectedPeers(new LinkedList<Peer>());
            n.setPeersRequestingConnection(new LinkedList<Peer>());
        }
        for (Peer p : peers) {
            p.setConnected(false);
            p.setConnectionFailed(false);
            p.setCurrentConnectedNetwork(0);
        }
        int counter_processed_peers = 0;
        double maxProcessingTimeForThisIteration = 0;
        int number_of_iterations = 0;
        double processingTime = 0;
        do {
            long HOEProcessingTimeStart = System.nanoTime();
//            System.out.println("number of processed peers: "+counter_processed_peers);
//            int counter =0;
//            int not_connected_counter=0;
//            for(Peer s : peers){
//                if(s.isConnected()){
//                    counter++;
//                }else{
//                    not_connected_counter++;
//                }
//            }
//            System.out.println("number of connected peers: "+counter);
//            System.out.println("number of not connected peers: "+not_connected_counter);
            counter_processed_peers = 0;
            for (Iterator<Peer> iterator = peers.iterator(); iterator.hasNext();) {
                Peer p = iterator.next();
                if (p.isConnected() || p.isConnectionFailed()) {
                    counter_processed_peers++;
                    continue;
                }
                Iterator<Networks_Package.NetowrkRank> nrIterator = p.getNRank().iterator();
                if (nrIterator.hasNext()) {
                    NetowrkRank nr = nrIterator.next();
                    if (nr.getRank() == 0) {
                        nrIterator.remove();
                        if (nrIterator.hasNext()) {
                            nr = nrIterator.next();
                        } else {
                            p.setConnectionFailed(true);
                            counter_processed_peers++;
                            continue;
                        }
                    }
                    Network topNetwork = nr.getNetwork();
                    topNetwork.getPeersRequestingConnection().add(p);
                } else {
                    p.setConnectionFailed(true);
                }
            }
            long HOEProcessingTimeEnd = System.nanoTime();
            processingTime += (HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000;
//            ProcessingTimePerIterationForHOE.add((HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000);
//            maxProcessingTimeForThisIteration = 0;
            number_of_iterations++;
            for (Network n : Networks) {

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
//                    Knapsack.KnapsackSolver solver = new Knapsack.GreedySolver(items, n.getCapacity());
                    Knapsack.KnapsackSolver solver = new Knapsack.DynamicProgrammingSolver(items, n.getCapacity());
//                    Knapsack.KnapsackSolver solver = new Knapsack.BranchAndBoundSolver(items, n.getCapacity());
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
                    for (Peer rp : rejectedPeers) {
                        rejectedPeersProfitWeight.add(new ProphitWeightRatio(rp.getNetworkRank(n) / rp.getDatarate(), rp));
                    }
                    Collections.sort(rejectedPeersProfitWeight);
                    List<Integer> rejectedPeersAccepted = new LinkedList<Integer>();
                    for (int i = 0; i < rejectedPeersProfitWeight.size(); i++) {
                        if (n.getFreeCapacity() == 0) {
//                            System.err.println("inside if==0");
                            break;
                        } else {

                            if ((n.getUsed_capacity() + rejectedPeersProfitWeight.get(i).getPeer().getDatarate()) <= n.getCapacity()) {
//                                System.err.println("inside if");
//                                System.err.println("inside if");
//                                System.err.println("inside if");
//                                System.err.println("inside if");
//                                System.err.println("inside if");
//                                System.err.println("inside if");
//                                System.err.println("inside if");
//                                System.err.println("inside if");
//                                System.err.println("inside if");

                                rejectedPeersAccepted.add(rejectedPeersProfitWeight.get(i).getPeer().getPeerID());
                                rejectedPeersProfitWeight.get(i).getPeer().setConnected(true);
                                rejectedPeersProfitWeight.get(i).getPeer().setConnectedNetwork(n);
                                n.getConnectedPeers().add(rejectedPeersProfitWeight.get(i).getPeer());
                                n.setUsed_capacity(n.getUsed_capacity() + rejectedPeersProfitWeight.get(i).getPeer().getDatarate());
                                rejectedPeersProfitWeight.get(i).getPeer().setConnected_network_rank(rejectedPeersProfitWeight.get(i).getPeer().getNetworkRank(n));
                                rejectedPeersProfitWeight.get(i).getPeer().setCurrentConnectedNetwork(n.getId());
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
                long networkProcessingTimeEnd = System.nanoTime();

                double processingTimeForThisIteration = (networkProcessingTimeEnd - networkProcessingTimeStart) / (double) 1000000;
                processingTime += processingTimeForThisIteration;
                if (maxProcessingTimeForThisIteration < processingTimeForThisIteration) {
                    maxProcessingTimeForThisIteration = processingTimeForThisIteration;
                }
            }
            maxProcessingTimePerIterationForNetworks.add(maxProcessingTimeForThisIteration);
        } while (counter_processed_peers < peers.size());
        Simulator.Simulator.stat.processingTime.add(processingTime);
//        System.out.println("number of iterations: " + number_of_iterations);
//        for (Network nx : Simulator.Simulator.sp.Netowrks) {
//            System.out.println(nx.getName() + "  number request lists: " + nx.getNumberOfRequestListsReceived() + " number users in lists " + nx.getTotalNumberOfUsersInList());
//        }
//        if (maxProcessingTimePerIterationForNetworks.size() != ProcessingTimePerIterationForHOE.size()) {
//            System.exit(-1);
//        } else {
//            System.out.println("for this second\nNumber of iterations:  " + maxProcessingTimePerIterationForNetworks.size() + ""
//                    + "\n");
//            double additionalDelay=0;
//            for (int d = 0; d < maxProcessingTimePerIterationForNetworks.size(); d++) {
//                System.out.println("Iteration " + d + "  HOE processing time: " + ProcessingTimePerIterationForHOE.get(d) + "   max network processing time: " + maxProcessingTimePerIterationForNetworks.get(d));
//                additionalDelay+=maxProcessingTimePerIterationForNetworks.get(d)+ProcessingTimePerIterationForHOE.get(d);
//            }
//            System.out.println("additional delayfor this second: "+(additionalDelay+200*number_of_iterations));
//        }
    }

}
