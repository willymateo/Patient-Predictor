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
    
    private class Node {
        private DataSet datosPacientes;
        private String atributoDominante;
        private String decision;
        private Node left, right, parent;
        
        public DataSet getDatosPacientes() {
            return datosPacientes;
        }

        public void setDatosPacientes(DataSet datosPacientes) {
            this.datosPacientes = datosPacientes;
        }

        public String getAtributoDominante() {
            return atributoDominante;
        }

        public void setAtributoDominante(String atributoDominante) {
            this.atributoDominante = atributoDominante;
        }

        public String getDecision() {
            return decision;
        }

        public void setDecision(String decision) {
            this.decision = decision;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }
        
        public boolean isLeaf(){
            return left == null && right == null;
        }
    }
}
