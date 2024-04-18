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

import Calulation.CostFunction;
import Knapsack.BranchAndBoundSolver;
import Knapsack.DynamicProgrammingSolver;
import Knapsack.GreedySolver;
import Knapsack.Item;
import Knapsack.OverallSolution;
import Networks_Package.NetowrkRank;
import Networks_Package.Network;
import Peers_Package.Peer;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class OptimizedCostFunctionHandover {

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
                        p.setConnectedNetwork(topNetwork);
                    } else {
                        if (p.getNetworkRank(topNetwork) < topNetwork.getMinimumProfit()) {
                            p.setNetworkRank(topNetwork, 0);
                            continue;
                        }

                        List<Item> items = new LinkedList<Item>();
                        p.setConnected(true);
                        p.setConnectedNetwork(topNetwork);
                        topNetwork.getConnectedPeers().add(p);
                        for (int i = 0; i < topNetwork.getConnectedPeers().size(); i++) {
                            Peer p1 = topNetwork.getConnectedPeers().get(i);
                            items.add(new Item(p1.getPeerID(), p1.getNetworkRank(topNetwork), p1.getDatarate()));
                        }
//                        Knapsack.KnapsackSolver solver = new BranchAndBoundSolver(items, topNetwork.getCapacity());
                        Knapsack.KnapsackSolver solver = new DynamicProgrammingSolver(items, topNetwork.getCapacity());
//                        Knapsack.KnapsackSolver solver = new GreedySolver(items, topNetwork.getCapacity());
//                        Date d = new Date();
                        OverallSolution s = solver.solve().getOverallSolution();
//                        Date d1 = new Date();
//                        long diff = d1.getTime() - d.getTime();
//                        System.out.println("difference in time: " + diff + " -----number of items: " + items.size() + "----network: " + topNetwork.getName());
                        List<Integer> peersAccepted = s.getItemLabels();
                        int counter = 0;
                        for (Peer tempP : topNetwork.getConnectedPeers()) {
                            if (!peersAccepted.contains(tempP.getPeerID())) {
                                counter++;
                                tempP.setConnected(false);
                                tempP.setNetworkRank(topNetwork, 0);
                            }
                        }
//                        System.err.println("inseide if: " + counter + "/" + topNetwork.getConnectedPeers().size());
                        List<Peer> connectedPeersList = new LinkedList<Peer>();
                        connectedPeersList.addAll(topNetwork.getConnectedPeers());
                        topNetwork.setUsed_capacity(0);
                        topNetwork.setConnectedPeers(new LinkedList<Peer>());
                        for (Peer p2 : connectedPeersList) {
                            if (peersAccepted.contains(p2.getPeerID())) {
                                p2.setConnected(true);
                                topNetwork.getConnectedPeers().add(p2);
                                topNetwork.setUsed_capacity(topNetwork.getUsed_capacity() + p2.getDatarate());
                                p2.setConnected_network_rank(p2.getNetworkRank(topNetwork));
                                p2.setCurrentConnectedNetwork(topNetwork.getId());
                                p2.setConnectedNetwork(topNetwork);
                            }
                        }
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
