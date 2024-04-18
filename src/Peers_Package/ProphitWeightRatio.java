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
package Peers_Package;

import Networks_Package.Network;

/**
 *
 * @author mohamad
 */
public class ProphitWeightRatio implements Comparable<ProphitWeightRatio> {

    double ratio;
    Peer peer;
    Network netowrkProfitRatio;
    int networkInitialForDistributed;
    int peerInitialForDistributed;

    public int getNetworkInitialForDistributed() {
        return networkInitialForDistributed;
    }

    public void setNetworkInitialForDistributed(int networkInitialForDistributed) {
        this.networkInitialForDistributed = networkInitialForDistributed;
    }

    public int getPeerInitialForDistributed() {
        return peerInitialForDistributed;
    }

    public void setPeerInitialForDistributed(int peerInitialForDistributed) {
        this.peerInitialForDistributed = peerInitialForDistributed;
    }

    public ProphitWeightRatio(double ratio, Peer peer) {
        this.peer = peer;
        this.ratio = ratio;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public Peer getPeer() {
        return peer;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    public Network getNetowrkProfitRatio() {
        return netowrkProfitRatio;
    }

    public void setNetowrkProfitRatio(Network netowrkProfitRatio) {
        this.netowrkProfitRatio = netowrkProfitRatio;
    }

    @Override
    public int compareTo(ProphitWeightRatio t) {
        if (this.ratio < t.ratio) {
            return 1;
        } else if (this.ratio == t.ratio) {

//                System.err.println("topNetwork: " + this.peer.getNRank().get(0).getNetwork().getId() + "!="
//                        + "" + t.peer.getNRank().get(0).getNetwork().getId());
            return 0;
        } else {
            return -1;

        }

    }

    @Override
    public String toString() {
        return "peer " + peer.getPeerID() + "have profit per bandwidth unit " + ratio;
    }
}
