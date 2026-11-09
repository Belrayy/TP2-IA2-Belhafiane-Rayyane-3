package com.example.belhafiane.test;

import com.example.belhafiane.outil.meteo.MeteoTool;

public class TestMeteo {

    public static void main(String[] args) {

        MeteoTool meteoTool = new MeteoTool();

        double latitude = 48.85;
        double longitude = 2.35;

        int nbJoursDebut = 0;
        int nbJours = 3;

        boolean pluie = meteoTool.isPluiePrevue(
                latitude,
                longitude,
                nbJoursDebut,
                nbJours
        );

        if (pluie) {
            System.out.println("De la pluie est prévue.");
        } else {
            System.out.println("Aucune pluie importante prévue..");
        }
    }
}
