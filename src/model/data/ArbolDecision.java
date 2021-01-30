/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import java.util.Map;

/**
 *
 * @author USER
 */
public class ArbolDecision {
    private Node root;

    public ArbolDecision(DataSet datosPacientes) {
        root = new Node(datosPacientes);
    }
    
    public void buildDecisionTree(String target){
        root.calculateDecision(target);
        root = buildDecisionTree(target, root);
    }
    
    private Node buildDecisionTree(String target, Node p){
        if (p != null) {
            Map<Boolean, Integer> contador = p.datosPacientes.countValues(target);
            boolean hayMasAtributos = p.datosPacientes.getGini().keySet().size() >= 3;
            boolean sePuedeSegmentar = contador.get(true) != 0 && contador.get(false) != 0;
            if (hayMasAtributos && sePuedeSegmentar) {
                DataSet[] dataSegmentada = p.datosPacientes.segmentarData(target);
                Node left = new Node(dataSegmentada[0], target);
                p.left = left;
                left.parent = p;
                Node right = new Node(dataSegmentada[1], target);
                p.right = right;
                right.parent = p;
                p = buildDecisionTree(target, p.left);
                p = buildDecisionTree(target, p.right);
            }
        }
        return p;
    }
    
    /**
     * @param datosPacientes Dataset de los pacientes. 
     * @param atributoDominante Atributo con menor valor Gini.
     * @param decision Su valor es "SI" o "NO" dependiendo del valor que más se
     * repite en la lista de valores del Target en el DataSet de pacientes.
     */
    private class Node {
        private final DataSet datosPacientes;
        private final String atributoDominante;
        private String decision;
        private Node left, right, parent;

        public Node(DataSet datosPacientes) {
            this.datosPacientes = datosPacientes;
            atributoDominante = datosPacientes.getMinGini();
        }
        
        public Node(DataSet datosPacientes, String target) {
            this(datosPacientes);
            calculateDecision(target);
        }
        
        public void calculateDecision(String target){
            Map<Boolean, Integer> contador = datosPacientes.countValues(target);
            datosPacientes.actGini(target);
            if (contador.get(true) > contador.get(false))
                decision = "SI";
            else decision = "NO";
        }
        
        public boolean isLeaf(){
            return left == null && right == null;
        }
    }
}
