/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Peers_Package;

import Calulation.CalculatePathLoss;
import Calulation.CalculateSignalStrength;
import Calulation.CostFunction;
import Calulation.CostFunctionPeerSide;
import Calulation.SpectralEfficiency;
import Networks_Package.NetowrkRank;
import Networks_Package.Network;
import Networks_Package.NetworkRequestedResources;
import Networks_Package.NetworkSignalStrength;
import Point.Point;
import Simulator.SimulationParameters;
import Simulator.Simulator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class Peer {

    int service_level;
    int datarate;//IN BANDWIDTH UNITS BU
    double ws, wmc, wpc;//signal Strength weight and money cost weight
    int speed_level;
    Point location;
    int speed;
    char symbol;
    SimulationParameters sp = Simulator.sp;
    List<NetworkSignalStrength> availableNetowrks;
    double max_available_mc, max_available_pc, min_available_mc, min_available_pc;
    double max_available_s, min_available_s;
    boolean connected;
    boolean connectionFailed;
    int peerID;
    List<NetowrkRank> NRank;
    int currentConnectedNetwork, previousConnectedNetwork;
    List<Network> currentAvailableNetworks, previousAvailableNetworks;
    double best_network_rank, connected_network_rank;
    Network connectedNetwork;
    double money_paid = 0, power_consumed = 0;
//    double best_s_ratio;
    List<ProphitWeightRatio> availableNetworksProfitWeightRatio;
    double total_interference_LTE;
    List<NetworkRequestedResources> requestedResourcesList;

    public Peer(Point location) {
        peerID = sp.peerID++;
        connected = false;
        connectionFailed = false;
        this.location = location;
        this.currentConnectedNetwork = -1;
        this.previousConnectedNetwork = -1;
    }

    public Peer(int datarate, double ws, double wmc, double wpc, int speed_level, Point location) {
//        if (Simulator.setvice_level > 3) {
        if (!Simulator.isSL) {
            this.service_level = 0;

        } else {
            this.service_level = (Simulator.setvice_level++) % 3;
        }
        peerID = sp.peerID++;
        max_available_mc = 0;
        max_available_pc = 0;
        max_available_s = 0;
//        if (datarate == 1) {
//            this.datarate = sp.voice_BU;
//        } else if (datarate == 2) {
//            this.datarate = sp.video_BU;
//        } else if (datarate == 3) {
//            this.datarate = sp.download_bu;
//        }
//        System.out.println(""+sp.randGenerator.nextInt(sp.all_data_rates.length));
        if (Simulator.sp.Peers.size() == 0) {
            this.datarate = 1200;
        } else {
            this.datarate = sp.all_data_rates[sp.randGenerator.nextInt(sp.all_data_rates.length)];
        }
//        this.datarate = sp.all_data_rates[sp.datarateCounter];
//        if(++sp.datarateCounter==9){
//            sp.datarateCounter=0;
//        }
//        if(this.datarate<100){
//            this.datarate=this.datarate+sp.randGenerator.nextInt(100);
//        }else if(this.datarate<1000){
//            this.datarate=this.datarate+sp.randGenerator.nextInt(250);
//        }else{
//            this.datarate=this.datarate+sp.randGenerator.nextInt(500);
//        }
        int Low = 10;
        int High = 90;
//        int Result = sp.randGenerator.nextInt(High - Low) + Low;
        this.ws = ws;
//        System.out.println("ws="+this.ws);
        this.wmc = wmc;
        this.wpc = wpc;
        ws = this.ws;
        wpc = this.wpc;
        this.speed_level = sp.randGenerator.nextInt(3);
        speed_level = this.speed_level;
        this.location = location;
        if (speed_level == 0) {
            speed = 0;
        } else if (speed_level == 1) {
            speed = sp.slow_moving_min_speed + sp.randGenerator.nextInt(sp.slow_moving_max_speed - sp.slow_moving_min_speed);
        } else if (speed_level == 2) {
            speed = sp.fast_moving_min_speed + sp.randGenerator.nextInt(sp.fast_moving_max_speed - sp.fast_moving_min_speed);
        }
        connectionFailed = false;
        connected = false;
        this.currentConnectedNetwork = -1;
        this.previousConnectedNetwork = -1;
        currentAvailableNetworks = new LinkedList<Network>();
        previousAvailableNetworks = new LinkedList<Network>();

    }

    public int getService_level() {
        return service_level;
    }

    public void setService_level(int service_level) {
        this.service_level = service_level;
    }

    public double getMoney_paid() {
        return money_paid;
    }

    public void setMoney_paid(double money_paid) {
        this.money_paid = money_paid;
    }

    public double getPower_consumed() {
        return power_consumed;
    }

    public void setPower_consumed(double power_consumed) {
        this.power_consumed = power_consumed;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getDatarate() {
        return datarate;
    }

    public void setDatarate(int datarate) {
        this.datarate = datarate;
    }

    public double getWs() {
        return ws;
    }

    public void setWs(double ws) {
        this.ws = ws;
    }

    public double getWpc() {
        return wpc;
    }

    public void setWpc(double wpc) {
        this.wpc = wpc;
    }

    public double getWmc() {
        return wmc;
    }

    public void setWmc(double wmc) {
        this.wmc = wmc;
    }

    public int getSpeed_level() {
        return speed_level;
    }

    public void setSpeed_level(int speed_level) {
        this.speed_level = speed_level;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public List<NetworkSignalStrength> getAvailableNetowrks() {
        return availableNetowrks;
    }

    public void setAvailableNetowrks(List<NetworkSignalStrength> availableNetowrks) {
        this.availableNetowrks = availableNetowrks;
    }

    public void movePeer() {

        this.location.movePoint(this.speed);

//        testNetworks();
//        rankNetworks();
    }

    public void testNetworks() {
        total_interference_LTE = 0;
        max_available_mc = 0;
        max_available_pc = 0;
        max_available_s = 0;
        min_available_mc = 0;
        min_available_pc = 0;
        min_available_s = 0;
        this.availableNetowrks = new LinkedList<NetworkSignalStrength>();
        for (Network n : sp.Netowrks) {
            if (!n.isAP()) {
                total_interference_LTE += sp.LTE_TX_POWER_per_RB * CalculatePathLoss.calculatePathLoss(false, this.location.distanceTo(n.getCenter()));
            }
        }
        for (Network n : sp.Netowrks) {
//            if (!n.isAP()) {
//                total_interference_LTE += sp.LTE_TX_POWER_per_RB * CalculatePathLoss.calculatePathLoss(false, this.location.distanceTo(n.getCenter()));
//            }
            if (this.location.distanceTo(n.getCenter()) < n.getRadius()) {
                double signalStrength = CalculateSignalStrength.calculate_signal_strength(this, n);
//                double signalStrength = SpectralEfficiency.calculateSpectralEffiecincy(total_interference_LTE, n.isAP(), this.location.distanceTo(n.getCenter()));
                this.availableNetowrks.add(new NetworkSignalStrength(n, signalStrength));
                if (n.getMc() > max_available_mc) {
                    max_available_mc = n.getMc();
                }
//                if ((this.getDatarate() * (n.getMc())) > sp.max_mc_in_system) {
//                    sp.max_mc_in_system = this.getDatarate() * (n.getMc());
//                }
                if (n.getPc(getDatarate()) > max_available_pc) {
                    max_available_pc = n.getPc(getDatarate());
                }
                if (signalStrength > max_available_s) {
                    max_available_s = signalStrength;
                }
                if (min_available_mc == 0 || min_available_pc == 0 || min_available_s == 0) {
                    min_available_mc = n.getMc();
                    min_available_pc = n.getPc(getDatarate());
                    min_available_s = signalStrength;
                }
                if (max_available_pc == 0 || max_available_s == 0) {
                    max_available_pc = n.getPc(getDatarate());
                    max_available_s = signalStrength;
                }
                if (n.getMc() < min_available_mc) {
                    min_available_mc = n.getMc();
                }
                if (n.getPc(getDatarate()) < min_available_pc) {
                    min_available_pc = n.getPc(getDatarate());
                }
                if (signalStrength < min_available_s) {
                    min_available_s = signalStrength;
                }
            }
        }
//        System.out.println(""+availableNetowrks);
        currentAvailableNetworks = new LinkedList<Network>();
        int j = 0;
        for (NetworkSignalStrength ns : availableNetowrks) {
//            System.out.println(""+availableNetowrks.get(j++).getNetwork().getName());
            currentAvailableNetworks.add(ns.getNetwork());

        }
        requestedResourcesList = new LinkedList<NetworkRequestedResources>();
        for (Network nrequested : currentAvailableNetworks) {
            if (nrequested.isAP()) {
                double spectralEfficeincy = SpectralEfficiency.calculateSpectralEffiecincy(0, true, this.location.distanceTo(nrequested.getCenter()));
                double dataratePerTimeSlot = spectralEfficeincy * sp.WIFI_BW / sp.NUM_WIFI_TIME_SLOTS;
                double requestedTimeSlotsDouble = this.getDatarate() / dataratePerTimeSlot;
                int requestedTimeSlots = (int) Math.ceil(requestedTimeSlotsDouble);
                requestedResourcesList.add(new NetworkRequestedResources(nrequested, requestedTimeSlots));
            } else {
                double spectralEfficeincy = SpectralEfficiency.calculateSpectralEffiecincy(total_interference_LTE, false, this.location.distanceTo(nrequested.getCenter()));
                double dataratePerSchedulingBlock = spectralEfficeincy * 180 / sp.NUM_LTE_TIME_SLOTS;
                double requestedSBsDouble = this.getDatarate() / dataratePerSchedulingBlock;
                int requestedSB = (int) Math.ceil(requestedSBsDouble);
                requestedResourcesList.add(new NetworkRequestedResources(nrequested, requestedSB));
            }
        }

//        if (availableNetowrks.size() > 1) {
//            double RSSxijLnxij = 0;
//            double PCxijLnxij = 0;
//            for (NetworkSignalStrength ns : availableNetowrks) {
////            System.out.println(""+availableNetowrks.get(j++).getNetwork().getName());
//                RSSxijLnxij += (ns.getSignalStrength() / this.max_available_s) * Math.log1p(ns.getSignalStrength() / this.max_available_s);
//                PCxijLnxij += (this.min_available_pc / ns.getNetwork().getPc(this.datarate)) * Math.log1p(this.min_available_pc / ns.getNetwork().getPc(this.datarate));
//            }
//            this.ws = sp.ws * (1 - (1 / Math.log1p(availableNetowrks.size())) * RSSxijLnxij);
//            this.wpc = sp.wpc * (1 - (1 / Math.log1p(availableNetowrks.size())) * PCxijLnxij);
//
//        } else {
//            this.ws = sp.ws;
//            this.wpc = sp.wpc;
//        }
    }

    public int getRequestedResources(int networkID) {
//        return getDatarate();
        for (NetworkRequestedResources nrequested : requestedResourcesList) {
            if (nrequested.getNetwork().getId() == networkID) {
                return nrequested.getRequestedResources();
            }
        }
//        System.err.println("Error, cannot find the number of requested resources");
//        System.exit(-3);
        return 10000000;
    }

    public double getMin_available_mc() {
        return min_available_mc;
    }

    public void setMin_available_mc(double min_available_mc) {
        this.min_available_mc = min_available_mc;
    }

    public double getMin_available_pc() {
        return min_available_pc;
    }

    public void setMin_available_pc(double min_available_pc) {
        this.min_available_pc = min_available_pc;
    }

    public double getMin_available_s() {
        return min_available_s;
    }

    public void setMin_available_s(double min_available_s) {
        this.min_available_s = min_available_s;
    }

    public boolean availableNetworksChanged() {
        if (currentAvailableNetworks.size() != previousAvailableNetworks.size()) {
            return false;
        }
        for (Network cn : currentAvailableNetworks) {
            if (!wasInPreviousAvailableNetworks(cn)) {
                return false;
            }

        }
        return true;
    }

    private boolean wasInPreviousAvailableNetworks(Network n) {
        for (Network pn : previousAvailableNetworks) {
            if (pn.getId() == n.getId()) {
                return true;
            }
        }
        return false;
    }

    public void rankNetworks() {
        best_network_rank = 0;
        NRank = new LinkedList<NetowrkRank>();
        for (NetworkSignalStrength n : availableNetowrks) {
//            double rank = CostFunction.calculate_cost_function(this, n);
            double rank = CostFunctionPeerSide.calculate_cost_function(this, n);
            NRank.add(new NetowrkRank(n.getNetwork(), rank));
            if (best_network_rank < rank) {
                best_network_rank = rank;
            }

        }
        Collections.sort(NRank);
//        System.out.println(""+NRank);
//        System.out.println("the best rank of user: "+this.getPeerID()+" is "+best_network_rank+"\nnetwork ranking:"+NRank);
//        System.out.println("*********************************************************");
    }

    public double getMax_available_mc() {
        return max_available_mc;
    }

    public void setMax_available_mc(double max_available_mc) {
        this.max_available_mc = max_available_mc;
    }

    public double getMax_available_s() {
        if (max_available_s == 0) {
            System.err.println("Error maximum available s =0 in Peer.java");
            System.exit(-1);
            return 0.0001;
        }
        return max_available_s;
    }

    public void setMax_available_s(double max_available_s) {
        this.max_available_s = max_available_s;
    }

    public List<NetowrkRank> getNRank() {
        return NRank;
    }

    public void setNRank(List<NetowrkRank> NRank) {
        this.NRank = NRank;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnectionFailed() {
        return connectionFailed;
    }

    public void setConnectionFailed(boolean connectionFailed) {
        this.connectionFailed = connectionFailed;
        this.connected = false;
    }

    public double getNetworkRank(Network n) {
        for (NetowrkRank nr : this.NRank) {
            if (nr.getNetwork().getName().equalsIgnoreCase(n.getName())) {
                return nr.getRank();
            }
        }
        return 0;
    }

    public int getPeerID() {
        return peerID;
    }

    public void setPeerID(int peerID) {
        this.peerID = peerID;
    }

    public void setNetworkRank(Network n, double rank) {
        for (NetowrkRank nr : this.NRank) {
            if (nr.getNetwork().getName().equalsIgnoreCase(n.getName())) {
                nr.setRank(rank);
                return;
            }
        }
    }

    public void movementDone() {
        if (isConnected()) {
            power_consumed += getConnectedNetwork().getPc(getDatarate());
            money_paid += getConnectedNetwork().getMc();
        } else {
            currentConnectedNetwork = -1;
        }
    }

    public int getCurrentConnectedNetwork() {
        if (isConnected()) {
            return getConnectedNetwork().getId();
        } else {
            return -1;
        }
    }

    public void setCurrentConnectedNetwork(int currentConnectedNetwork) {
        this.currentConnectedNetwork = currentConnectedNetwork;
    }

    public int getPreviousConnectedNetwork() {
        return previousConnectedNetwork;
    }

    public void setPreviousConnectedNetwork(int previousConnectedNetwork) {
        this.previousConnectedNetwork = previousConnectedNetwork;
    }

    public List<Network> getCurrentAvailableNetworks() {
        return currentAvailableNetworks;
    }

    public void setCurrentAvailableNetworks(List<Network> currentAvailableNetworks) {
        this.currentAvailableNetworks = currentAvailableNetworks;
    }

    public List<Network> getPreviousAvailableNetworks() {
        return previousAvailableNetworks;
    }

    public void setPreviousAvailableNetworks(List<Network> previousAvailableNetworks) {
        this.previousAvailableNetworks = previousAvailableNetworks;
    }

    public double getBest_network_rank() {
        return best_network_rank;
    }

    public void setBest_network_rank(double best_network_rank) {
        this.best_network_rank = best_network_rank;
    }

    public double getConnected_network_rank() {
        return connected_network_rank;
    }

    public void setConnected_network_rank(double connected_network_rank) {
        this.connected_network_rank = connected_network_rank;
    }

    public static Comparator<Peer> byWeight() {
        return new Comparator<Peer>() {
            public int compare(Peer i1, Peer i2) {
                return Double.compare(i2.getDatarate(), i1.getDatarate());
            }
        };
    }

    public static Comparator<Peer> byProfit(final Network n) {
        return new Comparator<Peer>() {
            public int compare(Peer i1, Peer i2) {
                return Double.compare(i2.getNetworkRank(n), i1.getNetworkRank(n));
            }
        };
    }

    public String toString(Network n) {
        return "Weight: " + datarate + "\tProfit: " + getNetworkRank(n);
    }

    public Network getConnectedNetwork() {
        return connectedNetwork;
    }

    public void setConnectedNetwork(Network connectedNetwork) {
        this.connectedNetwork = connectedNetwork;
    }

    public double getMax_available_pc() {
        return max_available_pc;
    }

    public void setMax_available_pc(double max_available_pc) {
        this.max_available_pc = max_available_pc;
    }

    public NetworkSignalStrength getNetworkSignalStrength(int id) {
        for (NetworkSignalStrength n : availableNetowrks) {
            if (n.getNetwork().getId() == id) {
                return n;
            }
        }

        System.err.println("error trying to get signal strength of unreached network");
        System.exit(-1);
        return null;
    }

    public boolean isNetworkAvailable(Network n) {
        for (Network k : currentAvailableNetworks) {
            if (k.getId() == n.getId()) {
                return true;
            }
        }
        return false;
    }

    public List<ProphitWeightRatio> getAvailableNetworksProfitWeightRatio() {
        return availableNetworksProfitWeightRatio;
    }

    public void setAvailableNetworksProfitWeightRatio(List<ProphitWeightRatio> availableNetworksProfitWeightRatio) {
        this.availableNetworksProfitWeightRatio = availableNetworksProfitWeightRatio;
    }

    public double getTotal_interference_LTE() {
        return total_interference_LTE;
    }

    public void setTotal_interference_LTE(double total_interference_LTE) {
        this.total_interference_LTE = total_interference_LTE;
    }

}
