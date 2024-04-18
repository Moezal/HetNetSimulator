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
package Calulation;

import Networks_Package.Network;
import Peers_Package.Peer;

/**
 *
 * @author mohamad
 */
public class CalculateSignalStrength {

    public static double calculate_signal_strength(Peer p, Network n) {
//        SpectralEfficiency.calculateSpectralEffiecincy(p.getTotal_interference_LTE(), n.isAP(), p.getLocation().distanceTo(n.getCenter()));
        double result = 1 - (Math.log10(p.getLocation().distanceTo(n.getCenter()))) / (Math.log10(n.getRadius()));
//        System.out.println("calculated signal strength from user:" + p.getPeerID() + " to network:" + n.getName() + "=" + result);
        return result;

    }

}
