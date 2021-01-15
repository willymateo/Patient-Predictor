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
public class MaxAreTrueAdjuster extends Umbral implements Adjuster{

    public MaxAreTrueAdjuster(double middleValue) {
        super(middleValue);
    }

    @Override
    public boolean convert(double value) {
        return value > middleValue;
    }
    
}
