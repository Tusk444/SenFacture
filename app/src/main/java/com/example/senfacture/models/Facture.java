package com.example.senfacture.models;

public class Facture {
    private int idFacture;
    private String intitule;
    private String entreprise;
    private double montant;
    private String numero;
    private String dateEcheance;
    private boolean isPaye;
    private int idUser;

    public Facture(int idFacture, String intitule, String entreprise, double montant, String numero, String dateEcheance, boolean isPaye, int idUser) {
        this.idFacture = idFacture;
        this.intitule = intitule;
        this.entreprise = entreprise;
        this.montant = montant;
        this.numero = numero;
        this.dateEcheance = dateEcheance;
        this.isPaye = isPaye;
        this.idUser = idUser;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(String dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public boolean isPaye() {
        return isPaye;
    }

    public void setPaye(boolean paye) {
        isPaye = paye;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
