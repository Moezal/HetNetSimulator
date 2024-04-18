/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networks_Package;

import Networks_Package.Network;
import java.text.DecimalFormat;

/**
 *
 * @author mohamad
 */
public class NetowrkRank implements Comparable<Networks_Package.NetowrkRank> {

    Networks_Package.Network network;
    double rank;

    public NetowrkRank(Network network, double rank) {
        this.network = network;
        this.rank = rank;
    }

    @Override
    public int compareTo(NetowrkRank t) {
        if (this.rank < t.rank) {
            return 1;
        } else if (this.rank == t.rank) {
            return 0;
        } else {
            return -1;
        }
    }
    public String toString() {
        DecimalFormat df = new DecimalFormat("###.##");
        return "Network: " + network.getName() + "-Rank: " + df.format(rank);
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }
    

}
