/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HandOverAlgorithms;

import Calulation.CalculateSignalStrength;
import Calulation.NetworkCostFunction;
import Knapsack.Item;
import Networks_Package.NetowrkRank;
import Networks_Package.Network;
import Peers_Package.Peer;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class MaxProfitFist {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {

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
        do {

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
            for (Network n : Networks) {
                if (n.getFreeCapacity() >= n.getTotalRequestedDataRate()) {
                    for (Peer p : n.getPeersRequestingConnection()) {
                        p.setConnected(true);
                        n.getConnectedPeers().add(p);
                        n.setUsed_capacity(n.getUsed_capacity() + p.getDatarate());
                        p.setConnected_network_rank(p.getNetworkRank(n));
                        p.setCurrentConnectedNetwork(n.getId());
                        p.setConnectedNetwork(n);
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
                    for (Peer p1 : peersToBeProcessed) {
                        items.add(new Item(p1.getPeerID(), (NetworkCostFunction.calculate_Network_cost_function(p1, n, max_mc, max_pc, max_s, min_mc, min_pc, min_s, overall_requested_BUs, overall_mc, overall_pc, overall_s)), ((p1.getDatarate()))));
                    }
                    n.setUsed_capacity(0);
                    n.setConnectedPeers(new LinkedList<Peer>());
                    n.setPeersRequestingConnection(new LinkedList<Peer>());
                    //we can first arrange the users in the decreasing order of weight just by uncommenting the below line
//                    Collections.sort(peersToBeProcessed, Collections.reverseOrder(Peer.byWeight()));
                    Collections.sort(peersToBeProcessed, Peer.byProfit(n));
//                    for (Peer px : peersToBeProcessed) {
//                        System.out.println("" + px.getNetworkRank(n));
//                    }

//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(MaxProfitFist.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    System.err.println("Sorting peers");
//                    for (Peer ptbp : peersToBeProcessed) {
//                        System.out.println(ptbp.toString(n));
//                    }
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(MaxProfitFist.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    System.err.println("Sorting peers end");
//                    System.out.println("Iteration new");
//                    for (int i = 0; i < items.size(); i++) {
//                        System.out.println("item: " + items.get(i).getRatio());
//                    }
//                    System.out.println("end of iteration");
//                    System.out.println("optimization: " + s.getOverall_weight() + "/" + n.getCapacity());
//                    System.out.println("added peers:");
                    List<Integer> peersAccepted = new LinkedList<Integer>();
                    for (int i = 0; i < peersToBeProcessed.size(); i++) {
                        if (n.getFreeCapacity() == 0) {
//                            System.out.println("inside if");
                            break;
                        } else {
                            if ((n.getUsed_capacity() + peersToBeProcessed.get(i).getDatarate()) <= n.getCapacity()) {
                                peersAccepted.add(peersToBeProcessed.get(i).getPeerID());
                                n.setUsed_capacity(n.getUsed_capacity() + peersToBeProcessed.get(i).getDatarate());
                            }
                        }
                    }
//                    System.out.println("");
                    n.setUsed_capacity(0);
//                    String s2 = "Items Lables: ";
//                    for (Item i : items) {
//                        s2 += i.label + ", ";
//                    }
//                    System.out.println(s2);
//                    System.out.println("Peers accepted: " + peersAccepted);
                    for (Peer tempP : peersToBeProcessed) {
                        if (!peersAccepted.contains(tempP.getPeerID())) {
                            tempP.setConnected(false);
                            tempP.setNetworkRank(n, 0);
                            tempP.setCurrentConnectedNetwork(0);
                        }
                        if (peersAccepted.contains(tempP.getPeerID())) {
                            tempP.setConnected(true);
                            n.getConnectedPeers().add(tempP);
                            n.setUsed_capacity(n.getUsed_capacity() + tempP.getDatarate());
                            tempP.setConnected_network_rank(tempP.getNetworkRank(n));
                            tempP.setCurrentConnectedNetwork(n.getId());
                            tempP.setConnectedNetwork(n);
                        }
                    }
                    if (n.getCapacity() < n.getUsed_capacity()) {
                        System.err.println("Error Used capacity > capacity\n "
                                + "Error Used capacity > capacity\n"
                                + "Error Used capacity > capacity\n"
                                + "Error Used capacity > capacity\n"
                                + "Error Used capacity > capacity\n"
                                + "Error Used capacity > capacity\n");
                    }
//                    System.out.println("utilization: " + qwe + "/" + n.getCapacity());
//                    System.out.println("utilization with optimization: " + n.getUsed_capacity() + "/" + n.getCapacity());
                }

            }
        } while (counter_processed_peers < peers.size());
    }

}
