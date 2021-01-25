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
        
        System.out.println("/**Lista de pacientes**/");
        for (Map.Entry<String, ArrayList<Boolean>> entry : dataset.getDataset().entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue() +" Size = "+ entry.getValue().size());
        System.out.println("/**Gini**/");
        for (Map.Entry<String, Double> entry : dataset.getGini().entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
         /*Actualizan los valores de acuerdo al atributo Death Event*/
        dataset.actualizarGini("DEATH_EVENT");
        
        System.out.println("/**Gini Actualizado**/");
        for (Map.Entry<String, Double> entry : dataset.getGini().entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
        
         ArrayList<DataSet> nuevoSeg = dataset.segmentarData("DEATH_EVENT");
         
        
        /*Datos segmentados y nuevos calculos - pruebas*/
        //nuevoSeg.get(0).segmentarData("DEATH_EVENT");
        //nuevoSeg.get(1).segmentarData("DEATH_EVENT");
        
    }
}
