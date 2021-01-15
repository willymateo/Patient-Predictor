/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.umbral;

/**
 *
 * @author Willy Mateo
 */
public class MinAreTrueAdjuster extends Umbral implements Adjuster{

    public MinAreTrueAdjuster(double middleValue) {
        super(middleValue);
    }

    @Override
    public boolean convert(double value) {
        return value<middleValue;
    }
    
}
