/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Willy Mateo
 */
public class TestReadCsv {
    public static void main(String[] args) {
        DataSet dataset = new DataSet();
        dataset.loadData("resources/pacientes.csv");
        dataset.actGini("DEATH_EVENT");
        
        System.out.println("/**Lista de pacientes**/");
        for (Map.Entry<String, ArrayList<Boolean>> entry : dataset.getDataset().entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue() +" Size = "+ entry.getValue().size());
        
        System.out.println("/**Gini**/");
        for (Map.Entry<String, Double> entry : dataset.getGini().entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
    }
}
