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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mohamad
 */
public class CostFunctionHandover {

    public static void startHandingOver(List<Peer> Peers, List<Network> Networks) {

        List<Peer> peers = new LinkedList<Peer>();
        peers.addAll(Peers);
        Network topNetwork;
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
        do {
            for (Iterator<Peer> iterator = peers.iterator(); iterator.hasNext();) {
                Peer p = iterator.next();
                Iterator<Networks_Package.NetowrkRank> nrIterator = p.getNRank().iterator();
                if (nrIterator.hasNext()) {
                    topNetwork = nrIterator.next().getNetwork();
                    if (topNetwork.getCapacity() >= (topNetwork.getUsed_capacity() + p.getDatarate())) {
                        topNetwork.setUsed_capacity(topNetwork.getUsed_capacity() + p.getDatarate());
                        topNetwork.getConnectedPeers().add(p);
                        p.setConnected(true);
                        p.setConnectedNetwork(topNetwork);
                        p.setConnected_network_rank(p.getNetworkRank(topNetwork));
                        p.setCurrentConnectedNetwork(topNetwork.getId());
                        iterator.remove();
                    } else {
                        nrIterator.remove();
                    }
                } else {
                    iterator.remove();
                }
            }
        } while (!peers.isEmpty());
    }

}
