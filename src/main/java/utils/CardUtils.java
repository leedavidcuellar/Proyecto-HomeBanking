package utils;

import java.util.Random;

public final class CardUtils {

    private CardUtils() {
    }

    public static String generateNumberAleatorio(int cant){
        String generateNumber = "";
        for(int i = 0; i < cant; i++) {
            int newNumber = (int) (Math.random() * 10);
            generateNumber += String.valueOf(newNumber);
        }
        return generateNumber;
    }

    public static String generateNumber(){
        Random aleatorio = new Random();
        String aux = "";
        int num = aleatorio.nextInt(0, 999);
        if (num < 10) {
            return aux = "VIN00" + num;
        }
        if (num < 100) {
            return aux = "VIN0" + num;
        }
        return aux = "VIN" + num;

    }

}

/*
    //Generador de CVV
    public String generateCvv1() {
        String newCvv = "";
        for(int i = 0; i < 3; i++) {
            int newNumber = (int) (Math.random() * 10);
            newCvv += String.valueOf(newNumber);
        }
        return newCvv;
    }

    public String generateNumber(){
        String generateNumber = "";
        for(int i = 0; i < 12; i++) {
            int newNumber = (int) (Math.random() * 10);
            generateNumber += String.valueOf(newNumber);
        }
        return "4545"+generateNumber;
    }

    public String generateNumber2(){
        String generateNumber = "";
        for(int i = 0; i < 16; i++) {
            int newNumber = (int) (Math.random() * 10);
            generateNumber += String.valueOf(newNumber);
        }
        return generateNumber;
    }

 private String generateCVC(){
        int newCVC = (int) (Math.random() * (999 - 100)+100);
        if(newCVC<10){
            return "00"+ String.valueOf(newCVC);
        }
        if(newCVC<100){
            return "0"+ String.valueOf(newCVC);
        }
        return String.valueOf(newCVC);
    }
  /*
    public double getBalance(){
     double total =0.0;
        for(Transaction transaction:gertTransacion()){
            switch(transaction.getType()){
             case DEBIT: total = total + amount;
                    break;
             case CREDIT: total = total + amount;
                    break;
            }
        }
    }
    */