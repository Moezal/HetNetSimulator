/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HandOverAlgorithms;

import Knapsack.Item;
import Networks_Package.NetowrkRank;
import Networks_Package.Network;
import Peers_Package.Peer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class CostFunctionAtTheEndOfIteration {

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
        double Network_processing_time = 0;
        double processingTime = 0;
        do {
            long HOEProcessingTimeStart = System.nanoTime();;
            counter_processed_peers = 0;
            for (Iterator<Peer> iterator = peers.iterator(); iterator.hasNext();) {
                Peer p = iterator.next();
                if (p.isConnected() || p.isConnectionFailed()) {
                    counter_processed_peers++;
                    continue;
                }
                Iterator<Networks_Package.NetowrkRank> nrIterator = p.getNRank().iterator();
                while (true) {
                    if (nrIterator.hasNext()) {
                        NetowrkRank nr = nrIterator.next();
                        if (nr.getRank() == 0 || (nr.getNetwork().getFreeCapacity() < p.getDatarate())) {
                            nrIterator.remove();
                            if (nrIterator.hasNext()) {
                                nr = nrIterator.next();
                            } else {
                                p.setConnectionFailed(true);
                                counter_processed_peers++;
                                break;
                            }
                        }
                        Network topNetwork = nr.getNetwork();
                        topNetwork.getPeersRequestingConnection().add(p);
                        break;
                    } else {
                        p.setConnectionFailed(true);
                        break;
                    }
                }

            }
            long HOEProcessingTimeEnd = System.nanoTime();
//            ProcessingTimePerIterationForHOE.add((HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000);
            processingTime += (HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000;
//            maxProcessingTimeForThisIteration = 0;
//            number_of_iterations++;
//            long NetworkProcessingTimeStart = System.nanoTime();
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
//                    peersToBeProcessed.addAll(n.getConnectedPeers());
                    peersToBeProcessed.addAll(n.getPeersRequestingConnection());
                    for (Peer px : peersToBeProcessed) {
                        if (n.getFreeCapacity() >= px.getDatarate()) {
                            px.setConnected(true);
                            px.setConnectedNetwork(n);
                            n.getConnectedPeers().add(px);
                            n.setUsed_capacity(n.getUsed_capacity() + px.getDatarate());
                            px.setConnected_network_rank(px.getNetworkRank(n));
                            px.setCurrentConnectedNetwork(n.getId());
                        } else {
                            px.setConnected(false);
                            px.setNetworkRank(n, 0);
                            px.setCurrentConnectedNetwork(0);

                        }

                    }
                    n.setPeersRequestingConnection(new LinkedList<Peer>());
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
//                if (maxProcessingTimeForThisIteration < processingTimeForThisIteration) {
//                    maxProcessingTimeForThisIteration = processingTimeForThisIteration;
//                }
            }
//            long NetworkProcessingTimeEnd = System.nanoTime();
//            Network_processing_time += ((NetworkProcessingTimeEnd - NetworkProcessingTimeStart) / (double) 1000000);
//            maxProcessingTimePerIterationForNetworks.add(maxProcessingTimeForThisIteration);
        } while (counter_processed_peers < peers.size());
        Simulator.Simulator.stat.processingTime.add(processingTime);
//        System.out.println("network processing time: " + processing_time);
//        System.out.println("number of iterations: " + number_of_iterations);
//        for (Network nx : Simulator.Simulator.sp.Netowrks) {
//            System.out.println(nx.getName() + "  number request lists: " + nx.getNumberOfRequestListsReceived() + " number users in lists " + nx.getTotalNumberOfUsersInList());
//        }
//        if (maxProcessingTimePerIterationForNetworks.size() != ProcessingTimePerIterationForHOE.size()) {
//            System.exit(-1);
//        } else {
//            System.out.println("for this second\nNumber of iterations:  " + maxProcessingTimePerIterationForNetworks.size() + ""
//                    + "\n");
//            double additionalDelay = 0;
//            for (int d = 0; d < maxProcessingTimePerIterationForNetworks.size(); d++) {
//                System.out.println("Iteration " + d + "  HOE processing time: " + ProcessingTimePerIterationForHOE.get(d) + "   max network processing time: " + maxProcessingTimePerIterationForNetworks.get(d));
//                additionalDelay += maxProcessingTimePerIterationForNetworks.get(d) + ProcessingTimePerIterationForHOE.get(d);
//            }
//            System.out.println("additional delayfor this second: " + (additionalDelay + number_of_iterations));
//        }
    }

}
