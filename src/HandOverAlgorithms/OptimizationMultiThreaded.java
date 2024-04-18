/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HandOverAlgorithms;

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
public class OptimizationMultiThreaded {

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
            long NetworkProcessingTimeStart = System.nanoTime();
            for (Network n : Networks) {
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
