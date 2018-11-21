/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author BrandCast
 */
public class FindSupplier {
    private static int position = 0;

    /**
     * @return the position
     */
    public static int getPosition() {
        return position;
    }

    /**
     * @param aPosition the position to set
     */
    public static void setPosition(int aPosition) {
        position = aPosition;
    }
}
