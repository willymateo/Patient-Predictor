/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import model.umbral.*;

/**
 *
 * @author Willy Mateo
 */
public class DataSet {

    private Map<String, ArrayList<Boolean>> dataset;
    private Map<String, Double> gini;

    public DataSet() {
        dataset = new HashMap();
        gini = new HashMap(12);
    }

    public void loadData(String filePath) {
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] atributes = line.strip().split(",");
            for (String atributo : atributes) {
                dataset.put(atributo, new ArrayList<>(299));
                gini.put(atributo, 0.0);
            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.strip().split(",");
                valueConverter(parts[0], atributes[0], new MinAreTrueAdjuster(62));
                valueConverter(parts[1], atributes[1], new DefaultAdjuster());
                valueConverter(parts[2], atributes[2], new MinAreTrueAdjuster(2500));
                valueConverter(parts[3], atributes[3], new DefaultAdjuster());
                valueConverter(parts[4], atributes[4], new MinAreTrueAdjuster(43));
                valueConverter(parts[5], atributes[5], new DefaultAdjuster());
                valueConverter(parts[6], atributes[6], new MinAreTrueAdjuster(50000));
                valueConverter(parts[7], atributes[7], new MinAreTrueAdjuster(5));
                valueConverter(parts[8], atributes[8], new MaxAreTrueAdjuster(127));
                valueConverter(parts[9], atributes[9], new DefaultAdjuster());
                valueConverter(parts[10], atributes[10], new DefaultAdjuster());
                valueConverter(parts[11], atributes[11], new DefaultAdjuster());
            }
        } catch (IOException ioE) {
            System.out.println("Error al tratar de abrir el archivo:\n" + ioE);
        }
    }

    public void actualizarGini(String atributo){
        for (Map.Entry<String, ArrayList<Boolean>> informacionMapa : dataset.entrySet()){
            gini.put(informacionMapa.getKey(), obtenerGini(informacionMapa.getKey(),atributo));
        }    
    }

    public void inicializaGini() {
        for (String key : gini.keySet()) {
            gini.put(key, 0.0);
        }
    }

    private void valueConverter(String stringValue, String key, Adjuster adjuster) {
        double value = Double.parseDouble(stringValue);
        boolean converted = UmbralHandler.convert(adjuster, value);
        dataset.get(key).add(converted);
    }

    private Double obtenerGini(String atributoA, String atributoB){
        ArrayList<Boolean> primerAtributo = dataset.get(atributoA);
        ArrayList<Boolean> segundoAtributo = dataset.get(atributoB);
        
        int contadorSiPrimeroSiSegundo = 0;
        int contadorSiPrimeroNoSegundo = 0;
        int contadorNoPrimeroSiSegundo = 0;
        int contadorNoPrimeroNoSegundo = 0;
        int contadorSi = 0;
        int contadorNo = 0;
        
        /* En caso que el atributo y el target sean el mismo*/
        if(atributoA.equals(atributoB)){
            for(int i=0; i<primerAtributo.size(); i++){
                if(primerAtributo.get(i)==true){
                    contadorSi++;
                } else{
                    contadorNo++;
                }
            }
            double giniFinal = 1 - Math.pow(((double)contadorSi/primerAtributo.size()),2) - Math.pow(((double)contadorNo/primerAtributo.size()),2);
            return giniFinal;
        }
        
        /*El atributo y el target son diferentes*/
        for(int i=0; i<primerAtributo.size(); i++){
            if(primerAtributo.get(i)==true && segundoAtributo.get(i)==true){
                contadorSiPrimeroSiSegundo++;
                contadorSi++;
            } else if(primerAtributo.get(i)==true && segundoAtributo.get(i)==false){
                contadorSiPrimeroNoSegundo++;
                contadorSi++;
            } else if(primerAtributo.get(i)==false && segundoAtributo.get(i)==true){
                contadorNoPrimeroSiSegundo++;
                contadorNo++;
            }else{
                contadorNoPrimeroNoSegundo++;
                contadorNo++;
            }
        }
        double giniSiPrimero = 1 - Math.pow(((double)contadorSiPrimeroSiSegundo/contadorSi),2) - Math.pow(((double)contadorSiPrimeroNoSegundo/contadorSi),2);
        double giniNoPrimero = 1 - Math.pow(((double)contadorNoPrimeroSiSegundo/contadorNo),2) - Math.pow(((double)contadorNoPrimeroNoSegundo/contadorNo),2);
        
        double giniFinal = ((double)contadorSi/primerAtributo.size())*giniSiPrimero + ((double)contadorNo/primerAtributo.size())*giniNoPrimero;
        return giniFinal;
        
    }

   public ArrayList<DataSet> segmentarData(String atributo){
        ArrayList<DataSet> listaDatos = new ArrayList<>();
        DataSet datosP = new DataSet();
        DataSet datosN = new DataSet();
        String atributoMenorGini = "";
        double menorGini = Double.MAX_VALUE;
        
        this.actualizarGini(atributo);
        for (Map.Entry<String, Double> giniAtributo : gini.entrySet()){
            if(giniAtributo.getValue()<menorGini){
                atributoMenorGini = giniAtributo.getKey();
                menorGini = giniAtributo.getValue();
            }
        }
        if(atributoMenorGini.equals(atributo)){
            return null;
        }
        System.out.println("Atributo menor gini es "+atributoMenorGini+" y tiene un gini de "+menorGini);
        
        ArrayList<Boolean> listaSegmentar = dataset.remove(atributoMenorGini); //Remuevo el atributo del dataset original
        gini.remove(atributoMenorGini); // Remuevo el gini del dataset original
        
        /*Creacion de nuevos data sets sin el atributo con el menor gini*/
        for (Map.Entry<String, ArrayList<Boolean>> mapaDatos : dataset.entrySet()){
            datosP.dataset.put(mapaDatos.getKey(), new ArrayList<>());
            datosN.dataset.put(mapaDatos.getKey(), new ArrayList<>());
            
            datosP.gini.put(mapaDatos.getKey(), 0.0);
            datosP.gini.put(mapaDatos.getKey(), 0.0);
        }
        
        int contador = 0; //Variable para controlar el acceso al dataset
        for(Boolean valor: listaSegmentar){
            if(valor==true){
                for (Map.Entry<String, ArrayList<Boolean>> mapaDatos : dataset.entrySet()){
                    ArrayList<Boolean> listaAgregar = datosP.dataset.get(mapaDatos.getKey());
                    listaAgregar.add(mapaDatos.getValue().get(contador));
                    datosP.dataset.put(mapaDatos.getKey(), listaAgregar);
                }
            } else{
                for (Map.Entry<String, ArrayList<Boolean>> mapaDatos : dataset.entrySet()){
                    ArrayList<Boolean> listaAgregar = datosN.dataset.get(mapaDatos.getKey());
                    listaAgregar.add(mapaDatos.getValue().get(contador));
                    datosN.dataset.put(mapaDatos.getKey(), listaAgregar);
                }
            }
            contador++;
        }
        
        // Pruebas para apreciar la impresión de valores
        System.out.println("datosP");
        for (Map.Entry<String, ArrayList<Boolean>> entry : datosP.getDataset().entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue() +" Size = "+ entry.getValue().size());
        System.out.println("DatosN");
        for (Map.Entry<String, ArrayList<Boolean>> entry : datosN.getDataset().entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue() +" Size = "+ entry.getValue().size());
        
        listaDatos.add(datosP);
        listaDatos.add(datosN);
        return listaDatos;
    }

    public Map<String, ArrayList<Boolean>> getDataset() {
        return dataset;
    }

    public Map<String, Double> getGini() {
        return gini;
    }

}
