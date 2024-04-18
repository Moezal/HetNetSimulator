/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networks_Package;

import Peers_Package.Peer;
import Point.Point;
import Simulator.SimulationParameters;
import Simulator.Simulator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class Network {

    boolean AP;
    int capacity;//maximum capacity IN BANDWIDTH UNITS BU
    double mc, pc;//money cost per BU , power consumption per BU
    double radius, speed_threshold;
    Point center;
    String name;
    int used_capacity;
    List<Peer> ConnectedPeers;
    List<Peer> PeersRequestingConnection;
    int id;
    SimulationParameters sp = Simulator.sp;
    List<Double> utilization;
    List<Integer> requested_BUs_list;
    int total_requested_BUs;
    int numberOfRequestListsReceived, totalNumberOfUsersInList;

    public Network(int capacity, double mc, double pc, double radius, double speed_threshold, Point center, String name, boolean AP) {
        this.capacity = capacity;
        this.mc = mc + sp.randGenerator.nextInt(sp.mc_variation);
        this.AP = AP;
        this.radius = radius;
        this.speed_threshold = speed_threshold;
        this.center = center;
        this.name = name;
        this.pc = pc;
        used_capacity = 0;
        this.id = sp.networkID++;
        ConnectedPeers = new LinkedList<Peer>();
        this.utilization = new LinkedList<Double>();
        this.requested_BUs_list = new LinkedList<Integer>();
        total_requested_BUs = 0;
        numberOfRequestListsReceived = 0;
        totalNumberOfUsersInList = 0;
    }

    public int getTotalNumberOfUsersInList() {
        return totalNumberOfUsersInList;
    }

    public void setTotalNumberOfUsersInList(int totalNumberOfUsersInList) {
        this.totalNumberOfUsersInList = totalNumberOfUsersInList;
    }

    public int getNumberOfRequestListsReceived() {
        return numberOfRequestListsReceived;
    }

    public void setNumberOfRequestListsReceived(int numberOfRequestListsReceived) {
        this.numberOfRequestListsReceived = numberOfRequestListsReceived;
    }

    public List<Double> getUtilization() {
        return utilization;
    }

    public List<Integer> getRequested_BUs_list() {
        return requested_BUs_list;
    }

    public void setRequested_BUs_list(List<Integer> requested_BUs_list) {
        this.requested_BUs_list = requested_BUs_list;
    }

    public int getTotal_requested_BUs() {
        return total_requested_BUs;
    }

    public void setTotal_requested_BUs(int total_requested_BUs) {
        this.total_requested_BUs = total_requested_BUs;
    }

    public boolean isAP() {
        return AP;
    }

    public void setAP(boolean AP) {
        this.AP = AP;
    }

    public double getPc(double BUs) {
        if (isAP()) {
//            System.out.println("Network "+getName());
            return sp.alpha_d_wifi * BUs + sp.beta_wifi;
        } else {

            return sp.alpha_d_lte * BUs + sp.beta_lte;
        }
    }

    public void setPc(double pc) {
        this.pc = pc;
    }

    public double getSpeed_threshold() {
        return speed_threshold;
    }

    public void setSpeed_threshold(double speed_threshold) {
        this.speed_threshold = speed_threshold;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getMc() {
        return mc;
    }

    public void setMc(double mc) {
        this.mc = mc;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsed_capacity() {
        return used_capacity;
    }

    public void setUsed_capacity(int used_capacity) {
        this.used_capacity = used_capacity;
    }

    public List<Peer> getConnectedPeers() {
        return ConnectedPeers;
    }

    public void setConnectedPeers(List<Peer> ConnectedPeers) {
        this.ConnectedPeers = ConnectedPeers;
    }

    public double getMinimumProfit() {
        double minProfit = ConnectedPeers.get(0).getNetworkRank(this);
        for (Peer p : ConnectedPeers) {
            if (p.getNetworkRank(this) < minProfit) {
                minProfit = p.getNetworkRank(this);
            }
        }
        return minProfit;
    }

    public List<Peer> freeUp(int requested_capacity) {
        List<Peer> removedPeers = new LinkedList<Peer>();
        do {
            Peer minProfit = ConnectedPeers.get(0);
            for (Peer p : ConnectedPeers) {
                if (p.getNetworkRank(this) < minProfit.getNetworkRank(this)) {
                    minProfit = p;
                }
            }
            ConnectedPeers.remove(minProfit);
            used_capacity -= minProfit.getDatarate();
            minProfit.setConnected(false);
            removedPeers.add(minProfit);
        } while ((capacity - used_capacity) < requested_capacity);
        return removedPeers;
    }

    public List<Peer> getPeersRequestingConnection() {
        return PeersRequestingConnection;
    }

    public void setPeersRequestingConnection(List<Peer> PeersRequestingConnection) {
        this.PeersRequestingConnection = PeersRequestingConnection;
    }

    public int getTotalRequestedDataRate() {
        int requestedDataRate = 0;
        for (Peer p : PeersRequestingConnection) {
            requestedDataRate += p.getRequestedResources(this.getId());
        }
        return requestedDataRate;
    }

    public int getFreeCapacity() {
        return capacity - used_capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
