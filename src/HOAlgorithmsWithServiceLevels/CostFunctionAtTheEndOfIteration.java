/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HOAlgorithmsWithServiceLevels;

import Knapsack.Item;
import Networks_Package.NetowrkRank;
import Networks_Package.Network;
import Peers_Package.Peer;
import Simulator.Simulator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class CostFunctionAtTheEndOfIteration {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {
//        Collections.shuffle(Peers, sp.randGenerator);
//        Collections.shuffle(Networks, sp.randGenerator);
//        Collections.shuffle(Peers, Simulator.sp.randGenerator);
        List<Peer> peers = new LinkedList<Peer>();
//        for (Peer pcx : Peers) {
//            System.out.println("" + pcx.getPeerID());
//        }
        peers.addAll(Peers);
//        System.out.println("after add all");

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
        List<Peer> peersServiceLevel = new LinkedList<Peer>();
        int SLRange;
        if (Simulator.isSL) {
            SLRange = 3;
        } else {
            SLRange = 1;
        }
        for (int ed = 0; ed < SLRange; ed++) {
            peersServiceLevel = new LinkedList<Peer>();
            for (Peer ped : Peers) {
                if (ped.getService_level() == ed) {
                    peersServiceLevel.add(ped);
                }
            }
//            Collections.shuffle(peersServiceLevel, Simulator.sp.randGenerator);
//            Collections.shuffle(Networks, Simulator.sp.randGenerator);
//            System.out.println("Within Iterator");
            do {
                long HOEProcessingTimeStart = System.nanoTime();;
                counter_processed_peers = 0;
                for (Iterator<Peer> iterator = peersServiceLevel.iterator(); iterator.hasNext();) {
                    Peer p = iterator.next();
//                    System.out.println("" + p.getPeerID());
                    if (p.isConnected() || p.isConnectionFailed()) {
                        counter_processed_peers++;
                        continue;
                    }
                    Iterator<Networks_Package.NetowrkRank> nrIterator = p.getNRank().iterator();
                    while (true) {

                        if (nrIterator.hasNext()) {
                            NetowrkRank nr = nrIterator.next();

                            if (nr.getRank() == 0) {
                                nrIterator.remove();
                                if (nrIterator.hasNext()) {
//                                    nr = nrIterator.next();
//                                    skipThis=true;
                                } else {
                                    p.setConnectionFailed(true);
                                    counter_processed_peers++;
                                    break;
                                }
                            } else if ((nr.getNetwork().getFreeCapacity() < p.getRequestedResources(nr.getNetwork().getId()))) {
                                nrIterator.remove();
                                if (nrIterator.hasNext()) {
//                                    nr = nrIterator.next();
//                                    skipThis=true;
                                } else {
                                    p.setConnectionFailed(true);
                                    counter_processed_peers++;
                                    break;
                                }
                            } else {

                                Network topNetwork = nr.getNetwork();
                                topNetwork.getPeersRequestingConnection().add(p);
                                break;
                            }
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
                            n.setUsed_capacity(n.getUsed_capacity() + p.getRequestedResources(n.getId()));
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
                            if (n.getFreeCapacity() >= px.getRequestedResources(n.getId())) {
                                px.setConnected(true);
                                px.setConnectedNetwork(n);
                                n.getConnectedPeers().add(px);
                                n.setUsed_capacity(n.getUsed_capacity() + px.getRequestedResources(n.getId()));
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
            } while (counter_processed_peers < peersServiceLevel.size());
        }
        Simulator.stat.processingTime.add(processingTime);
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
