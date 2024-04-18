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
package HOAlgorithmsWithServiceLevels;

import HandOverAlgorithms.*;
import Calulation.CalculateSignalStrength;
import Calulation.NetworkCostFunction;
import Calulation.NetworkCostFunctionSmaller;
import Knapsack.Item;
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
public class ApproximationPerHzAlgorithm {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {
//        Collections.shuffle(Peers, Simulator.Simulator.sp.randGenerator);
//        Collections.shuffle(Networks, Simulator.Simulator.sp.randGenerator);
        for (Network n : Networks) {
            n.setUsed_capacity(0);
            n.setConnectedPeers(new LinkedList<Peer>());
            n.setPeersRequestingConnection(new LinkedList<Peer>());
            n.setTotalNumberOfUsersInList(1);
            n.setNumberOfRequestListsReceived(1);
        }

        double processingTime = 0;
        long HOEProcessingTimeStart = System.nanoTime();
        List<Peer> peersServiceLevel = new LinkedList<Peer>();
        int SLRange;
        if (Simulator.Simulator.isSL) {
            SLRange = 3;
        } else {
            SLRange = 1;
        }
        for (int ed = 0; ed < SLRange; ed++) {
//            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Service Level: "+ed+"  $$$$$$$$$$$$$$$$$$$$$$$");
            peersServiceLevel = new LinkedList<Peer>();
            for (Peer ped : Peers) {
                if (ped.getService_level() == ed) {
                    peersServiceLevel.add(ped);
                }
            }
            double globalMinPC = 0, globalMaxRss = 0;

            for (Peer p123 : peersServiceLevel) {
                p123.setConnected(false);
                p123.setConnectionFailed(false);
                p123.setCurrentConnectedNetwork(0);
                if (globalMinPC == 0) {
                    globalMinPC = p123.getMin_available_pc();
                }
                if (globalMaxRss == 0) {
                    globalMaxRss = p123.getMax_available_s();
                }
                if (p123.getMin_available_pc() < globalMinPC) {
                    globalMinPC = p123.getMin_available_pc();
                }
                if (p123.getMax_available_s() > globalMaxRss) {
                    globalMaxRss = p123.getMax_available_s();
                }
            }
//            Collections.shuffle(peersServiceLevel, Simulator.Simulator.sp.randGenerator);
//            Collections.shuffle(Networks, Simulator.Simulator.sp.randGenerator);
//            System.out.println("Service Level List Size: "+peersServiceLevel.size());
//            double[][] globalCostFunction = new double[peersServiceLevel.size()][Networks.size()];
            for (int i = 0; i < peersServiceLevel.size(); i++) {
                peersServiceLevel.get(i).setAvailableNetworksProfitWeightRatio(new LinkedList<ProphitWeightRatio>());
                for (int j = 0; j < Networks.size(); j++) {
                    if (peersServiceLevel.get(i).isNetworkAvailable(Networks.get(j))) {
                        double requestedResourcesInHz;
                        if (Networks.get(j).isAP()) {
                            requestedResourcesInHz = peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId()) * Simulator.Simulator.sp.WIFI_BW / Simulator.Simulator.sp.NUM_WIFI_TIME_SLOTS;
                        } else {
                            requestedResourcesInHz = peersServiceLevel.get(i).getRequestedResources(Networks.get(j).getId()) * 180 / (Simulator.Simulator.sp.NUM_LTE_TIME_SLOTS);
                        }

                        ProphitWeightRatio Pnow = new ProphitWeightRatio(NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(i), Networks.get(j), globalMaxRss, globalMinPC) / requestedResourcesInHz, peersServiceLevel.get(i));
//                        ProphitWeightRatio Pnow = new ProphitWeightRatio(NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(i), Networks.get(j), globalMaxRss, globalMinPC) / (double) peersServiceLevel.get(i).getDatarate(), peersServiceLevel.get(i));
//                        ProphitWeightRatio Pnow = new ProphitWeightRatio(Calulation.CostFunction.calculate_cost_function(peersServiceLevel.get(i), peersServiceLevel.get(i).getNetworkSignalStrength(Networks.get(j).getId()))*peersServiceLevel.get(i).getDatarate(),peersServiceLevel.get(i));
                        Pnow.setNetowrkProfitRatio(Networks.get(j));
                        peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().add(Pnow);
                    }
                }
                Collections.sort(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio());
            }
            boolean exitLoop = false;
            int exitLoopCounter = 0;
            boolean peerWithOneNetwork;
            List<ProphitWeightRatio> pwr;
            List<ProphitWeightRatio> peersWithOneNetwork;
            while (!exitLoop) {
                peerWithOneNetwork = false;
                pwr = new LinkedList<ProphitWeightRatio>();
                peersWithOneNetwork = new LinkedList<ProphitWeightRatio>();
                exitLoopCounter = 0;
                for (int i = 0; i < peersServiceLevel.size(); i++) {

                    if (!peersServiceLevel.get(i).isConnected() && !peersServiceLevel.get(i).isConnectionFailed()) {
                        for (int j = 0; j < peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().size(); j++) {
                            if (peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(j).getNetowrkProfitRatio().getFreeCapacity() < peersServiceLevel.get(i).getRequestedResources(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(j).getNetowrkProfitRatio().getId())) {
                                peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().remove(j);
                            }
                        }
                        Collections.sort(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio());
                        if (peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().size() < 1) {//connection failed
                            peersServiceLevel.get(i).setConnectionFailed(true);
                        } else if (peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().size() == 1) {
                            peerWithOneNetwork = true;
                            peersWithOneNetwork.add(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0));
                            ProphitWeightRatio Pnow = new ProphitWeightRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getRatio(), peersServiceLevel.get(i));
                            Pnow.setNetowrkProfitRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetowrkProfitRatio());
                            peersWithOneNetwork.add(Pnow);
                        } else {
                            ProphitWeightRatio Pnow = new ProphitWeightRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getRatio() - peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(1).getRatio(), peersServiceLevel.get(i));
                            Pnow.setNetowrkProfitRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetowrkProfitRatio());
                            pwr.add(Pnow);
                        }
                    } else {//increment counter to see if we should exit the loop
                        exitLoopCounter++;

                    }
                }
//                System.out.println("exitLoopCounter: "+ exitLoopCounter+" peers Size: "+peersServiceLevel.size());
                if (exitLoopCounter == peersServiceLevel.size()) {
                    exitLoop = true;
                } else {
                    if (peerWithOneNetwork) {
                        Collections.sort(peersWithOneNetwork);
                        for (int k = 0; k < peersWithOneNetwork.size(); k++) {
                            if (peersWithOneNetwork.get(k).getPeer().getRequestedResources(peersWithOneNetwork.get(k).getNetowrkProfitRatio().getId()) <= peersWithOneNetwork.get(k).getNetowrkProfitRatio().getFreeCapacity()) {
                                //connect 1 peer to his network
                                connectPeerToNetwork(peersWithOneNetwork.get(k).getPeer(), peersWithOneNetwork.get(k).getNetowrkProfitRatio());
                                break;
                            } else {//connection failed for 1 peer
                                peersWithOneNetwork.get(k).getPeer().setConnectionFailed(true);
                                break;
                            }
                        }
                    } else {
                        Collections.sort(pwr);
                        for (int l = 0; l < pwr.size(); l++) {
                            if (pwr.get(l).getPeer().getRequestedResources(pwr.get(l).getNetowrkProfitRatio().getId()) <= pwr.get(l).getNetowrkProfitRatio().getFreeCapacity()) {
                                connectPeerToNetwork(pwr.get(l).getPeer(), pwr.get(l).getNetowrkProfitRatio());
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }

        }
        long HOEProcessingTimeEnd = System.nanoTime();
        processingTime += (HOEProcessingTimeEnd - HOEProcessingTimeStart) / (double) 1000000;
        Simulator.Simulator.stat.processingTime.add(processingTime);
        for (Network nx : Networks) {
//            System.err.println("Second: "+Simulator.Simulator.sp.current_simulation_time+"-- network utilization "+nx.getName()+"  " + (double) (nx.getUsed_capacity() / (double) nx.getCapacity()));
            if (nx.getCapacity() < nx.getUsed_capacity()) {

                System.err.println("Error Used capacity > capacity\n "
                        + "Error Used capacity > capacity\n"
                        + "Error Used capacity > capacity\n"
                        + "Error Used capacity > capacity\n"
                        + "Error Used capacity > capacity\n"
                        + "Error Used capacity > capacity\n");
                System.exit(-1);
            }
        }
        for (Peer px : Peers) {
            if (px.isConnected()) {
                if (!px.isNetworkAvailable(px.getConnectedNetwork())) {
                    System.err.println("Error connected to un available network ");
                    System.exit(-1);
                }
            }
        }

/////////////////////////////
    }

    public static void connectPeerToNetwork(Peer p, Network n) {
        p.setConnectionFailed(false);
        p.setConnected(true);
        p.setConnectedNetwork(n);
        p.setConnected_network_rank(p.getNetworkRank(n));
        p.setCurrentConnectedNetwork(n.getId());
        n.setUsed_capacity(n.getUsed_capacity() + p.getRequestedResources(n.getId()));
        n.getConnectedPeers().add(p);
    }
}
