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
package SubGradient;

import HOAlgorithmsWithServiceLevels.*;
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
public class DistributedApproximationPerHzAlgorithm {

    List<Peer> peersServiceLevel;
    List<Network> Networks;
    double[][] globalCostFunction;
    double[][] result;
    double[] networkFreeCapacity;
    List<Peer> alteredPeers;
    double[][] feasibleReults;

    public DistributedApproximationPerHzAlgorithm(List<Peer> peersServiceLevel, List<Network> Networks, double[][] globalCostFunction, double[][] result, double[] networkFreeCapacity) {
        this.peersServiceLevel = peersServiceLevel;
        this.Networks = Networks;
        this.globalCostFunction = globalCostFunction;
        this.result = result;
        this.networkFreeCapacity = networkFreeCapacity;
        alteredPeers = new LinkedList<Peer>();
        feasibleReults = new double[peersServiceLevel.size()][Networks.size()];
        for (int i = 0; i < peersServiceLevel.size(); i++) {
            for (int j = 0; j < Networks.size(); j++) {
                feasibleReults[i][j] = 0;
            }
        }
    }

    public double[][] getFeasibleApproximation() {

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

                    ProphitWeightRatio Pnow = new ProphitWeightRatio(globalCostFunction[i][j], peersServiceLevel.get(i));
//                        ProphitWeightRatio Pnow = new ProphitWeightRatio(NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(i), Networks.get(j), globalMaxRss, globalMinPC) / (double) peersServiceLevel.get(i).getDatarate(), peersServiceLevel.get(i));
//                        ProphitWeightRatio Pnow = new ProphitWeightRatio(Calulation.CostFunction.calculate_cost_function(peersServiceLevel.get(i), peersServiceLevel.get(i).getNetworkSignalStrength(Networks.get(j).getId()))*peersServiceLevel.get(i).getDatarate(),peersServiceLevel.get(i));
                    Pnow.setNetowrkProfitRatio(Networks.get(j));
                    Pnow.setNetworkInitialForDistributed(j);
                    Pnow.setPeerInitialForDistributed(i);
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
                        if (networkFreeCapacity[peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(j).getNetworkInitialForDistributed()] < peersServiceLevel.get(i).getRequestedResources(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(j).getNetowrkProfitRatio().getId()) || result[i][peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(j).getNetworkInitialForDistributed()] == 0) {
                            peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().remove(j);
                        }
                    }
                    Collections.sort(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio());
                    if (peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().size() < 1) {//connection failed
                        peersServiceLevel.get(i).setConnectionFailed(true);
                        alteredPeers.add(peersServiceLevel.get(i));
                    } else if (peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().size() == 1) {
                        peerWithOneNetwork = true;
                        peersWithOneNetwork.add(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0));
                        ProphitWeightRatio Pnow = new ProphitWeightRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getRatio(), peersServiceLevel.get(i));
                        Pnow.setNetowrkProfitRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetowrkProfitRatio());
                        Pnow.setNetworkInitialForDistributed(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetworkInitialForDistributed());
                        Pnow.setPeerInitialForDistributed(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getPeerInitialForDistributed());
                        peersWithOneNetwork.add(Pnow);
                    } else {
                        ProphitWeightRatio Pnow = new ProphitWeightRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getRatio() - peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(1).getRatio(), peersServiceLevel.get(i));
                        Pnow.setNetworkInitialForDistributed(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetworkInitialForDistributed());
                        Pnow.setPeerInitialForDistributed(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getPeerInitialForDistributed());
                        Pnow.setNetowrkProfitRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetowrkProfitRatio());
                        pwr.add(Pnow);
                    }
                } else {//increment counter to see if we should exit the loop
                    exitLoopCounter++;

                }
            }
            if (exitLoopCounter == peersServiceLevel.size()) {
                exitLoop = true;
            } else {
                if (peerWithOneNetwork) {
                    Collections.sort(peersWithOneNetwork);
                    for (int k = 0; k < peersWithOneNetwork.size(); k++) {
                        if (peersWithOneNetwork.get(k).getPeer().getRequestedResources(peersWithOneNetwork.get(k).getNetowrkProfitRatio().getId()) <= networkFreeCapacity[peersWithOneNetwork.get(k).getNetworkInitialForDistributed()]) {
                            //connect 1 peer to his network
                            connectPeerToNetwork(peersWithOneNetwork.get(k).getPeer(), peersWithOneNetwork.get(k).getNetowrkProfitRatio(), peersWithOneNetwork.get(k));
                            break;
                        } else {//connection failed for 1 peer
                            peersWithOneNetwork.get(k).getPeer().setConnectionFailed(true);
                            alteredPeers.add(peersWithOneNetwork.get(k).getPeer());
                            break;
                        }
                    }
                } else {
                    Collections.sort(pwr);
                    for (int l = 0; l < pwr.size(); l++) {
                        if (pwr.get(l).getPeer().getRequestedResources(pwr.get(l).getNetowrkProfitRatio().getId()) <= networkFreeCapacity[pwr.get(l).getNetworkInitialForDistributed()]) {
                            connectPeerToNetwork(pwr.get(l).getPeer(), pwr.get(l).getNetowrkProfitRatio(), pwr.get(l));
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
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

                    ProphitWeightRatio Pnow = new ProphitWeightRatio(globalCostFunction[i][j], peersServiceLevel.get(i));
//                        ProphitWeightRatio Pnow = new ProphitWeightRatio(NetworkCostFunctionSmaller.calculate_Network_cost_function(peersServiceLevel.get(i), Networks.get(j), globalMaxRss, globalMinPC) / (double) peersServiceLevel.get(i).getDatarate(), peersServiceLevel.get(i));
//                        ProphitWeightRatio Pnow = new ProphitWeightRatio(Calulation.CostFunction.calculate_cost_function(peersServiceLevel.get(i), peersServiceLevel.get(i).getNetworkSignalStrength(Networks.get(j).getId()))*peersServiceLevel.get(i).getDatarate(),peersServiceLevel.get(i));
                    Pnow.setNetowrkProfitRatio(Networks.get(j));
                    Pnow.setNetworkInitialForDistributed(j);
                    Pnow.setPeerInitialForDistributed(i);
                    peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().add(Pnow);
                }
            }
            Collections.sort(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio());
        }
        for (Peer px : alteredPeers) {
//            px.setConnected(false);
            if (!px.isConnected()) {
                px.setConnectionFailed(false);
            }
        }
        exitLoop = false;
        exitLoopCounter = 0;
        while (!exitLoop) {
            peerWithOneNetwork = false;
            pwr = new LinkedList<ProphitWeightRatio>();
            peersWithOneNetwork = new LinkedList<ProphitWeightRatio>();
            exitLoopCounter = 0;
            for (int i = 0; i < peersServiceLevel.size(); i++) {

                if (!peersServiceLevel.get(i).isConnected() && !peersServiceLevel.get(i).isConnectionFailed()) {
                    for (int j = 0; j < peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().size(); j++) {
                        if (networkFreeCapacity[peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(j).getNetworkInitialForDistributed()] < peersServiceLevel.get(i).getRequestedResources(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(j).getNetowrkProfitRatio().getId())) {
                            peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().remove(j);
                        }
                    }
                    Collections.sort(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio());
                    if (peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().size() < 1) {//connection failed
                        peersServiceLevel.get(i).setConnectionFailed(true);
                        alteredPeers.add(peersServiceLevel.get(i));
                    } else if (peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().size() == 1) {
                        peerWithOneNetwork = true;
                        peersWithOneNetwork.add(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0));
                        ProphitWeightRatio Pnow = new ProphitWeightRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getRatio(), peersServiceLevel.get(i));
                        Pnow.setNetowrkProfitRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetowrkProfitRatio());
                        Pnow.setNetworkInitialForDistributed(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetworkInitialForDistributed());
                        Pnow.setPeerInitialForDistributed(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getPeerInitialForDistributed());
                        peersWithOneNetwork.add(Pnow);
                    } else {
                        ProphitWeightRatio Pnow = new ProphitWeightRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getRatio() - peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(1).getRatio(), peersServiceLevel.get(i));
                        Pnow.setNetworkInitialForDistributed(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetworkInitialForDistributed());
                        Pnow.setPeerInitialForDistributed(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getPeerInitialForDistributed());
                        Pnow.setNetowrkProfitRatio(peersServiceLevel.get(i).getAvailableNetworksProfitWeightRatio().get(0).getNetowrkProfitRatio());
                        pwr.add(Pnow);
                    }
                } else {//increment counter to see if we should exit the loop
                    exitLoopCounter++;

                }
            }
            if (exitLoopCounter == peersServiceLevel.size()) {
                exitLoop = true;
            } else {
                if (peerWithOneNetwork) {
                    Collections.sort(peersWithOneNetwork);
                    for (int k = 0; k < peersWithOneNetwork.size(); k++) {
                        if (peersWithOneNetwork.get(k).getPeer().getRequestedResources(peersWithOneNetwork.get(k).getNetowrkProfitRatio().getId()) <= networkFreeCapacity[peersWithOneNetwork.get(k).getNetworkInitialForDistributed()]) {
                            //connect 1 peer to his network
                            connectPeerToNetwork(peersWithOneNetwork.get(k).getPeer(), peersWithOneNetwork.get(k).getNetowrkProfitRatio(), peersWithOneNetwork.get(k));
                            break;
                        } else {//connection failed for 1 peer
                            peersWithOneNetwork.get(k).getPeer().setConnectionFailed(true);
                            alteredPeers.add(peersWithOneNetwork.get(k).getPeer());
                            break;
                        }
                    }
                } else {
                    Collections.sort(pwr);
                    for (int l = 0; l < pwr.size(); l++) {
                        if (pwr.get(l).getPeer().getRequestedResources(pwr.get(l).getNetowrkProfitRatio().getId()) <= networkFreeCapacity[pwr.get(l).getNetworkInitialForDistributed()]) {
                            connectPeerToNetwork(pwr.get(l).getPeer(), pwr.get(l).getNetowrkProfitRatio(), pwr.get(l));
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

//        for (Network nx : Networks) {
////            System.err.println("Second: "+Simulator.Simulator.sp.current_simulation_time+"-- network utilization "+nx.getName()+"  " + (double) (nx.getUsed_capacity() / (double) nx.getCapacity()));
//            if (nx.getCapacity() < nx.getUsed_capacity()) {
//                
//                System.err.println("Error Used capacity > capacity\n "
//                        + "Error Used capacity > capacity\n"
//                        + "Error Used capacity > capacity\n"
//                        + "Error Used capacity > capacity\n"
//                        + "Error Used capacity > capacity\n"
//                        + "Error Used capacity > capacity\n");
//                System.exit(-1);
//            }
//        }
//        for (Peer px : peersServiceLevel) {
//            if (px.isConnected()) {
//                if (!px.isNetworkAvailable(px.getConnectedNetwork())) {
//                    System.err.println("Error connected to un available network ");
//                    System.exit(-1);
//                }
//            }
//        }
        for (Peer px : alteredPeers) {
            px.setConnected(false);
            px.setConnectionFailed(false);
        }
        
        return feasibleReults;
    }

    public void connectPeerToNetwork(Peer p, Network n, ProphitWeightRatio pwr) {
        networkFreeCapacity[pwr.getNetworkInitialForDistributed()] = networkFreeCapacity[pwr.getNetworkInitialForDistributed()] - p.getRequestedResources(n.getId());
        p.setConnected(true);
        alteredPeers.add(p);
        feasibleReults[pwr.getPeerInitialForDistributed()][pwr.getNetworkInitialForDistributed()] = 1;
    }
}
