/*
 * Copyright (C) 2016 mohamad
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

/**
 *
 * @author mohamad
 */
public class SpectralEfficiency {

    public static double calculateSpectralEffiecincy(double totalInterference, boolean AP, double distanceToNetwork) {
        if (distanceToNetwork < 1) {
            distanceToNetwork = 1;
        }
        if (AP) {
            double pathLoss = CalculatePathLoss.calculatePathLoss(AP, distanceToNetwork);
            double SNR = Simulator.Simulator.sp.WIFI_TX_POWER * pathLoss / Math.pow(10, Simulator.Simulator.sp.WIFI_Noise / 10);
//            double SNR = 0.2 * pathLoss / Math.pow(10, -120 / 10);
            return Math.log(SNR + 1) / Math.log(2);
        } else {
            double pathLoss = CalculatePathLoss.calculatePathLoss(false, distanceToNetwork);
//            System.out.println("pathloss:" +pathLoss);
            double receivedPower = Simulator.Simulator.sp.LTE_TX_POWER_per_RB * pathLoss;
//            double receivedPower = 0.2 * pathLoss;
            double interferenceAndNoise = totalInterference - receivedPower + Math.pow(10, Simulator.Simulator.sp.LTE_Noise / 10);
//            double interferenceAndNoise =   Math.pow(10, -141.45 / 10);
            double SINR = receivedPower / interferenceAndNoise;
            return Math.log(SINR + 1) / Math.log(2);

        }
    }

//    public static void main(String[] args) {
//        System.out.println("AP-10: " + calculateSpectralEffiecincy(0, true, 10));
//        System.out.println("AP-100: " + calculateSpectralEffiecincy(0, true, 100));
//        System.out.println("AP-250: " + calculateSpectralEffiecincy(0, true, 250));
//        System.out.println("AP-500: " + calculateSpectralEffiecincy(0, true, 500));
//        System.out.println("AP-1000: " + calculateSpectralEffiecincy(0, true, 1000));
//        System.out.println("BS-10: " + calculateSpectralEffiecincy(0, false, 10));
//        System.out.println("BS-100: " + calculateSpectralEffiecincy(0, false, 100));
//        System.out.println("BS-250: " + calculateSpectralEffiecincy(0, false, 250));
//        System.out.println("BS-500: " + calculateSpectralEffiecincy(0, false, 500));
//        System.out.println("BS-1000: " + calculateSpectralEffiecincy(0, false, 1000));
//    }
}
