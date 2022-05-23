package com.example.zanakay;

public class Niveau {

    char[] signe;
    int[] chiffre;

    public char[] getSigne() {
        return signe;
    }

    public void setSigne(char[] signe) {
        this.signe = signe;
    }

    public int[] getChiffre() {
        return chiffre;
    }

    public void setChiffre(int[] chiffre) {
        this.chiffre = chiffre;
    }

    public Niveau(char[] signe, int[] chiffre) {
        setSigne(signe);
        setChiffre(chiffre);
    }

    public static Niveau getValeur(int cat){
        Niveau n = null;
        char[] s = null;
        int[] c = null;
        if(cat == 1){
            s = new char[1];
            s[0] = '+';
            c = new int[2];
            c[0] = 0;
            c[1] = 9;
        }
        else if(cat == 2){
            s = new char[2];
            s[0] = '+';
            s[1] = '-';
            c = new int[2];
            c[0] = 0;
            c[1] = 20;
        }
        else{
            s = new char[3];
            s[0] = '+';
            s[1] = '-';
            s[2] = '*';
            c = new int[2];
            c[0] = 0;
            c[1] = 50;
        }

        n = new Niveau(s,c);
        return n;
    }
}
