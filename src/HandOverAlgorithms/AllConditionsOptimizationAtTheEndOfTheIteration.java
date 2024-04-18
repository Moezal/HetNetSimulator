/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HandOverAlgorithms;

import Knapsack.BranchAndBoundSolver;
import Knapsack.Item;
import Knapsack.OverallSolution;
import Networks_Package.NetowrkRank;
import Networks_Package.Network;
import Peers_Package.Peer;
import Simulator.Simulator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class AllConditionsOptimizationAtTheEndOfTheIteration {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {

        List<Peer> peers = new LinkedList<Peer>();
        peers.addAll(Peers);
        for (Network n : Networks) {
//            n.setUsed_capacity(0);
//            n.setConnectedPeers(new LinkedList<Peer>());
            n.setPeersRequestingConnection(new LinkedList<Peer>());
        }
        for (Peer p : peers) {
            if (p.isConnected() || (p.getCurrentConnectedNetwork() == 0)) {
                if (p.availableNetworksChanged()) {
                    p.setConnected(false);
                    p.setConnectionFailed(false);
                }
            } else {
                p.setConnectionFailed(false);
            }
        }
        int counter_processed_peers = 0;
        do {

            System.out.println("number of processed peers: " + counter_processed_peers);
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
            for (Network n : Networks) {
                System.out.println("inside second for");
                if (n.getFreeCapacity() >= n.getTotalRequestedDataRate()) {
                    for (Peer p : n.getPeersRequestingConnection()) {
                        p.setConnected(true);
                        n.getConnectedPeers().add(p);
                        n.setUsed_capacity(n.getUsed_capacity() + p.getDatarate());

                    }
                    n.setPeersRequestingConnection(new LinkedList<Peer>());
                } else {
                    List<Item> items = new LinkedList<Item>();
                    List<Peer> peersToBeProcessed = new LinkedList<Peer>();
                    peersToBeProcessed.addAll(n.getConnectedPeers());
                    peersToBeProcessed.addAll(n.getPeersRequestingConnection());
                    n.setUsed_capacity(0);
                    n.setConnectedPeers(new LinkedList<Peer>());
                    n.setPeersRequestingConnection(new LinkedList<Peer>());
                    for (Peer p1 : peersToBeProcessed) {
                        p1.setConnected(false);
                        p1.setConnectionFailed(false);
                        items.add(new Item(p1.getPeerID(), p1.getNetworkRank(n), p1.getDatarate()));
                    }
                    Knapsack.KnapsackSolver solver = new BranchAndBoundSolver(items, n.getCapacity());
                    OverallSolution s = solver.solve().getOverallSolution();
                    System.out.println("optimization: " + s.getOverall_weight() + "/" + n.getCapacity());
                    List<Integer> peersAccepted = s.getItemLabels();
                    String s2 = "Items Lables: ";
                    for (Item i : items) {
                        s2 += i.label + ", ";
                    }
                    System.out.println(s2);
                    System.out.println("Peers accepted: " + peersAccepted);
                    for (Peer tempP : peersToBeProcessed) {
                        if (!peersAccepted.contains(tempP.getPeerID())) {
                            tempP.setConnected(false);
                            tempP.setNetworkRank(n, 0);
                        }
                        if (peersAccepted.contains(tempP.getPeerID())) {
                            tempP.setConnected(true);
                            n.getConnectedPeers().add(tempP);
                            n.setUsed_capacity(n.getUsed_capacity() + tempP.getDatarate());
                        }
                    }
//                    System.out.println("utilization: " + qwe + "/" + n.getCapacity());
//                    System.out.println("utilization with optimization: " + n.getUsed_capacity() + "/" + n.getCapacity());
                }

            }
        } while (counter_processed_peers < peers.size());
    }

    public static Network getNetwork(int i) {
        for (Network n : Simulator.sp.Netowrks) {
            if (n.getId() == i) {
                return n;
            }
        }
        return null;
    }

}
