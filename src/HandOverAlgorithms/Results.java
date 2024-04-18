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
package HandOverAlgorithms;

import Networks_Package.Network;
import Peers_Package.Peer;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class Results {

    int second, number_connected_peers, number_unconnected_peers;
    List<Network> networks;
    List<Peer> peers;
    
    public Results(int second, List<Network> networks, List<Peer> peers) {
        this.second = second;
        this.networks = networks;
        this.peers = peers;
        Simulator.Simulator.sp.report_writer.println(this);
    }

    @Override
    public String toString() {
        String result = "for Second: " + second + "\n";
        int number_connected_peers = 0;
        for (Peer p : peers) {
            if (p.isConnected()) {
                number_connected_peers++;
            }
        }
        result += "number of connected peers= " + number_connected_peers + "-- percentage connected: " + (number_connected_peers * 100) / peers.size() + "\n";
        number_unconnected_peers = peers.size() - number_connected_peers;
        result += "number of unconnected peers= " + number_unconnected_peers + "-- percentage unconnected: " + (number_unconnected_peers * 100) / peers.size() + "\n";
        result += "percentage utilization of each network:\n";
        for (Network n : networks) {
            result += n.getName() + " : " + (n.getUsed_capacity() * 100) / n.getCapacity() + "\n";
            if (n.getUsed_capacity() > n.getCapacity()) {
                System.err.println("ErrorErrorErrorErrorErrorErrorError\n"
                        + "ErrorErrorErrorErrorErrorErrorError\nErrorErrorErrorErrorErrorErrorError\n"
                        + "ErrorErrorErrorErrorErrorErrorError\nErrorErrorErrorErrorErrorErrorError\n"
                        + "ErrorErrorErrorErrorErrorErrorError\nErrorErrorErrorErrorErrorErrorError\n"
                        + "ErrorErrorErrorErrorErrorErrorError\nErrorErrorErrorErrorErrorErrorError\n"
                        + "ErrorErrorErrorErrorErrorErrorError\nErrorErrorErrorErrorErrorErrorError\n"
                        + "ErrorErrorErrorErrorErrorErrorError\nErrorErrorErrorErrorErrorErrorError\n");
            }
        }
        return result;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public void setPeers(List<Peer> peers) {
        this.peers = peers;
    }

}
