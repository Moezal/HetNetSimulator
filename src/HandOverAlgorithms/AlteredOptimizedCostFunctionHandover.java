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
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class AlteredOptimizedCostFunctionHandover {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {

        List<Peer> peers = new LinkedList<Peer>();
        peers.addAll(Peers);
        Network topNetwork;
        for (Network n : Networks) {
            n.setUsed_capacity(0);
            n.setConnectedPeers(new LinkedList<Peer>());
        }
        for (Peer p : peers) {
            p.setConnected(false);
            p.setConnectionFailed(false);
        }
        int counter_processed_peers = 0;
        do {

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
                        continue;
                    }
                    topNetwork = nr.getNetwork();
                    if (topNetwork.getCapacity() >= (topNetwork.getUsed_capacity() + p.getDatarate())) {
                        topNetwork.setUsed_capacity(topNetwork.getUsed_capacity() + p.getDatarate());
                        p.setConnected(true);
                        topNetwork.getConnectedPeers().add(p);
                        p.setConnected_network_rank(p.getNetworkRank(topNetwork));
                        p.setCurrentConnectedNetwork(topNetwork.getId());
                    } else {
                        if (p.getNetworkRank(topNetwork) < topNetwork.getMinimumProfit()) {
                            p.setNetworkRank(topNetwork, 0);
                            continue;
                        }
                        List<Peer> removedPeers = topNetwork.freeUp(p.getDatarate());
                        List<Item> items = new LinkedList<Item>();
                        removedPeers.add(p);
                        for (int i = 0; i < removedPeers.size(); i++) {
                            Peer p1 = removedPeers.get(i);
                            items.add(new Item(p1.getPeerID(), p1.getNetworkRank(topNetwork), p1.getDatarate()));
                        }
                        Knapsack.KnapsackSolver solver = new BranchAndBoundSolver(items, topNetwork.getCapacity() - topNetwork.getUsed_capacity());
//                        Date d = new Date();
                        OverallSolution s = solver.solve().getOverallSolution();
//                        Date d1 = new Date();
//                        long diff = d1.getTime() - d.getTime();
//                        System.out.println("difference in time: " + diff + " -----number of items: " + items.size() + "----network: " + topNetwork.getName());
                        List<Integer> peersAccepted = s.getItemLabels();
                        int counter = 0;
                        for (Peer tempP : removedPeers) {
                            if (!peersAccepted.contains(tempP.getPeerID())) {
                                counter++;
                                tempP.setConnected(false);
                                tempP.setNetworkRank(topNetwork, 0);
                            }
                            if (peersAccepted.contains(tempP.getPeerID())) {
                                tempP.setConnected(true);
                                topNetwork.getConnectedPeers().add(tempP);
                                topNetwork.setUsed_capacity(topNetwork.getUsed_capacity() + tempP.getDatarate());
                                p.setConnected_network_rank(p.getNetworkRank(topNetwork));
                                p.setCurrentConnectedNetwork(topNetwork.getId());
                            }
                        }
//                        System.err.println("inseide if: " + counter + "/" + topNetwork.getConnectedPeers().size());
//                        System.out.println("network utilization after optimization: " + topNetwork.getUsed_capacity() + "/" + topNetwork.getCapacity());

                    }
                } else {
                    p.setConnectionFailed(true);
                    p.setCurrentConnectedNetwork(0);
                }
            }
        } while (counter_processed_peers < peers.size());
    }

}
