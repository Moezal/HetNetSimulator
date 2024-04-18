/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HandOverAlgorithms;

import Calulation.CalculateSignalStrength;
import Calulation.NetworkCostFunction;
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
public class OptimizationMultiThreaded_AllUsersConnected {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {
//        LinkedList<Double> maxProcessingTimePerIterationForNetworks = new LinkedList<Double>();
//        LinkedList<Double> ProcessingTimePerIterationForHOE = new LinkedList<Double>();
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
        double processing_time = 0;
        do {
            long HOEProcessingTimeStart = System.nanoTime();;
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
//            ProcessingTimePerIterationForHOE.add((HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000);
            processing_time += (HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000;
            maxProcessingTimeForThisIteration = 0;
            number_of_iterations++;
            LinkedList<NetworkProcessingThread> networkThreads = new LinkedList<NetworkProcessingThread>();
//            LinkedList<BranchAndBoundNetworkProcessingThread> networkThreads = new LinkedList<BranchAndBoundNetworkProcessingThread>();
            long NetworkProcessingTimeStart = System.nanoTime();
            for (Network n : Networks) {
//                BranchAndBoundNetworkProcessingThread npt = new BranchAndBoundNetworkProcessingThread(n);
                NetworkProcessingThread npt = new NetworkProcessingThread(n);
                npt.start();
                networkThreads.add(npt);
            }
            boolean allThreadsIsAlive = true;
            while (allThreadsIsAlive) {
                boolean allThreadsTerminated = true;
                for (NetworkProcessingThread npt : networkThreads) {
                    allThreadsTerminated = allThreadsTerminated & (!npt.isAlive());
                }
                if (allThreadsTerminated) {
                    allThreadsIsAlive = false;
                }
            }
            long NetworkProcessingTimeEnd = System.nanoTime();
            processing_time += ((NetworkProcessingTimeEnd - NetworkProcessingTimeStart) / (double) 1000000);
            networkThreads = null;
//            System.gc();

//            maxProcessingTimePerIterationForNetworks.add(maxProcessingTimeForThisIteration);
        } while (counter_processed_peers < peers.size());
        long NetworkExtraProcessingTimeStart = System.nanoTime();
        LinkedList<Peer> peersCanConnect = new LinkedList<Peer>();
        for (Peer pcc : peers) {
            if (pcc.isConnectionFailed()) {
                pcc.rankNetworks();
                Iterator<Networks_Package.NetowrkRank> nrIterator = pcc.getNRank().iterator();
                while (nrIterator.hasNext()) {
                    NetowrkRank nr = nrIterator.next();
                    if (nr.getNetwork().getFreeCapacity() < pcc.getDatarate()) {
                        nrIterator.remove();
                    }
                }
                if (pcc.getNRank().size() > 0) {
                    peersCanConnect.add(pcc);
                    pcc.setConnectionFailed(false);
                }

            }
        }
        int pccCounter = 0;
        do {
            pccCounter = 0;
            for (Network n : Networks) {
//                n.setUsed_capacity(0);
//                n.setConnectedPeers(new LinkedList<Peer>());
                n.setPeersRequestingConnection(new LinkedList<Peer>());
            }
            for (Peer pcc : peersCanConnect) {
                if (pcc.isConnectionFailed() || pcc.isConnected()) {
                    pccCounter++;
                    continue;
                } else {
                    Iterator<Networks_Package.NetowrkRank> nrIterator = pcc.getNRank().iterator();
                    if (nrIterator.hasNext()) {
                        NetowrkRank nr = nrIterator.next();
                        if (nr.getRank() == 0) {
                            nrIterator.remove();
                            if (nrIterator.hasNext()) {
                                nr = nrIterator.next();
                            } else {
                                pcc.setConnectionFailed(true);
                                continue;
                            }
                        } else if (nr.getNetwork().getFreeCapacity() >= pcc.getDatarate()) {
                            nr.getNetwork().getPeersRequestingConnection().add(pcc);
                        } else {
                            nrIterator.remove();
                            if (!nrIterator.hasNext()) {
                                pcc.setConnectionFailed(true);
                            }
                        }

                    } else {
                        pcc.setConnectionFailed(true);
                    }

                }
            }
            for (Network n : Networks) {
                if (n.getPeersRequestingConnection().size() > 0) {
                    List<ProphitWeightRatio> rejectedPeersProfitWeight = new LinkedList<ProphitWeightRatio>();

                    double max_s_rejectedPeersProfitWeight = 0, max_mc_rejectedPeersProfitWeight = 0, max_pc_rejectedPeersProfitWeight = 0, min_s_rejectedPeersProfitWeight = 0, min_mc_rejectedPeersProfitWeight = 0, min_pc_rejectedPeersProfitWeight = 0;
                    double overall_requested_BUs_rejectedPeersProfitWeight = 0;
                    double overall_pc_rejectedPeersProfitWeight = 0;
                    double overall_mc_rejectedPeersProfitWeight = 0;
                    double overall_s_rejectedPeersProfitWeight = 0;
                    for (Peer rpp : n.getPeersRequestingConnection()) {
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

                    for (Peer rp : n.getPeersRequestingConnection()) {
                        rejectedPeersProfitWeight.add(new ProphitWeightRatio(NetworkCostFunction.calculate_Network_cost_function(rp, n, max_mc_rejectedPeersProfitWeight, max_pc_rejectedPeersProfitWeight, max_s_rejectedPeersProfitWeight, min_mc_rejectedPeersProfitWeight, min_pc_rejectedPeersProfitWeight, min_s_rejectedPeersProfitWeight, overall_requested_BUs_rejectedPeersProfitWeight, overall_mc_rejectedPeersProfitWeight, overall_pc_rejectedPeersProfitWeight, overall_s_rejectedPeersProfitWeight) / rp.getDatarate(), rp));
                    }
                    Collections.sort(rejectedPeersProfitWeight);
                    for (int i = 0; i < rejectedPeersProfitWeight.size(); i++) {
                        if (n.getFreeCapacity() == 0) {
//                            System.err.println("inside if==0");
                            break;
                        } else {

                            if ((n.getUsed_capacity() + rejectedPeersProfitWeight.get(i).getPeer().getDatarate()) <= n.getCapacity()) {                               
                                rejectedPeersProfitWeight.get(i).getPeer().setConnected(true);
                                rejectedPeersProfitWeight.get(i).getPeer().setConnectedNetwork(n);
                                n.getConnectedPeers().add(rejectedPeersProfitWeight.get(i).getPeer());
                                n.setUsed_capacity(n.getUsed_capacity() + rejectedPeersProfitWeight.get(i).getPeer().getDatarate());
                                rejectedPeersProfitWeight.get(i).getPeer().setConnected_network_rank(rejectedPeersProfitWeight.get(i).getPeer().getNetworkRank(n));
                                rejectedPeersProfitWeight.get(i).getPeer().setCurrentConnectedNetwork(n.getId());
                                System.err.println("Added a user that tour protocol forgot");
                            }
                        }
                    }
                    for (Peer rp : n.getPeersRequestingConnection()) {
                        if (!rp.isConnected()) {
                            rp.setConnected(false);
                            rp.setNetworkRank(n, 0);
                            rp.setCurrentConnectedNetwork(0);
                        }

                    }
                }
            }
        } while (pccCounter != peersCanConnect.size());
        long NetworkExtraProcessingTimeEnd = System.nanoTime();
        processing_time += ((NetworkExtraProcessingTimeEnd - NetworkExtraProcessingTimeStart) / (double) 1000000);
        Simulator.Simulator.stat.processingTime.add(processing_time);
//        System.out.println("Network processing time: " + processing_time);
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
//            System.out.println("additional delayfor this second: " + (additionalDelay + 200 * number_of_iterations));
//        }
    }

}
