/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

/**
 *
 * @author USER
 */
public class ArbolDecision {
    private Node root;

    public ArbolDecision(DataSet datosPacientes, String target) {
        root = new Node(datosPacientes, target);
        buildDecisionTree(target);
    }
    
    private void buildDecisionTree(String target){
        
    }
    
    private void buildDecisionTree(String target, Node p){
        
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
        private final String decision;
        private Node left, right, parent;

        public Node(DataSet datosPacientes, String target) {
            this.datosPacientes = datosPacientes;
            atributoDominante = datosPacientes.getMinGini();
            decision = calculateDecision(target);
        }
        
        private String calculateDecision(String target){
            if (datosPacientes.getMostRepeatedValue(target))
                return "SI";
            return "NO";
        }
        
        public boolean isLeaf(){
            return left == null && right == null;
        }
    }
}
