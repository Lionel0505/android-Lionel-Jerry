package com.example.zanakay;

public class Jeux {

    public static char egal = '=';
    int chiffre1, chiffre2, reponse;
    char signe;

    public int getChiffre1() {
        return chiffre1;
    }

    public void setChiffre1(int chiffre1) {
        this.chiffre1 = chiffre1;
    }

    public int getChiffre2() {
        return chiffre2;
    }

    public void setChiffre2(int chiffre2) {
        this.chiffre2 = chiffre2;
    }

    public char getSigne() {
        return signe;
    }

    public void setSigne(char signe) {
        this.signe = signe;
    }

    public int getReponse() {
        return reponse;
    }

    public void setReponse(int reponse) {
        this.reponse = reponse;
    }

    public Jeux() {
    }

    public Jeux(int chiffre1, int chiffre2, char signe) {
        setChiffre1(chiffre1);
        setChiffre2(chiffre2);
        setSigne(signe);
        int r = 0;
        switch (signe) {
            case '+': r = chiffre1 + chiffre2;break;
            case '-': r = chiffre1 - chiffre2;break;
            case '*': r = chiffre1 * chiffre2;break;
        }
        setReponse(r);
    }

    public static Jeux setJeux(int cat){
        Niveau n = Niveau.getValeur(cat);
        int n1 = (int) ((Math.random() * (n.getChiffre()[1] - n.getChiffre()[0])) + n.getChiffre()[0]);
        int n2 = (int) ((Math.random() * (n.getChiffre()[1] - n.getChiffre()[0])) + n.getChiffre()[0]);
        int temp = 0;
        if(n1 < n2){
            temp = n2;
            n2 = n1;
            n1 = temp;
        }
        int randomSigne = (int) ((Math.random() * ((n.getSigne().length-1) - 0)) + 0);
        char signe = n.getSigne()[randomSigne];

        Jeux jeux = new Jeux(n1,n2,signe);
        return jeux;
    }
}
