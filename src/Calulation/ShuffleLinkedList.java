/*
 * Copyright (C) 2016 mzalghou
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
package Calulation;

import Peers_Package.Peer;
import static java.lang.Math.random;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author mzalghou
 */
public class ShuffleLinkedList {

    public static void ShuffleLinkedList(List<Peer> peersList) {
        int n = peersList.size();
        for (int i = 0; i < n; i++) {
            int change = i + Simulator.Simulator.sp.randGenerator.nextInt(n - i);
            swap(peersList, i, change);
        }
    }

    private static void swap(List<Peer> peersList, int i, int change) {
        Peer helper = peersList.get(i);
        peersList.set(i, peersList.get(change));
        peersList.set(change, helper);
    }

}
