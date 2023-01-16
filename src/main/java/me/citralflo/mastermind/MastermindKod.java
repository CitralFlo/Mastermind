package me.citralflo.mastermind;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class MastermindKod {


    Scanner czytelnik = new Scanner(System.in);
    private int []kod;
    private int []proba;
    private int numer = 0;
    ArrayList<String> wynik = new ArrayList<String>();
    Random r = new Random();

    public MastermindKod(int n ) {
        this.kod = new int [4];
        this.proba = new int [4];
        numer = n;
        for(int i = 0; i < numer; i++) kod[i] = 0;
    }

    public void generuj() {
        for (int i = 0; i <4; i++) {
            this.kod[i] = r.nextInt(6)+1 ;
        }
    }
    public void zapiszPodejscie(int []podejscie){
        for(int i = 0; i < 4; i++){
            proba[i] = podejscie[i];
        }

    }
    public void wprowadzenie(int licznik) {
        for( int i = 0;i < 4; i++){
            System.out.print("Podaj " + (i+1) + ". cyfre kodu: ");
            int c;

            try {
            	c = czytelnik.nextInt();
            	if (c > 6 || c < 1)
                {
                    throw new IllegalArgumentException();
                } else {
                	proba[i] = c;
                }
            } catch(IllegalArgumentException e) {
            	System.out.println("Cyfry kodu musza zawierac sie w przedziale <1-6>\n");
            	i--;
            }
//			  catch(InputMismatchException e) {
//        		System.out.println("Cyfry kodu musza byc cyfra z przedzia�u <1-6>\n");
//        		i--;
//        	}
        }
    }
    
    public boolean finalsprawdzenie(){
        if (proba[0] == kod[0] && proba[1] == kod[1] && proba[2] == kod[2] && proba[3] == kod[3]){
        return true;
        } else {
            return false;
        }
    }
    public ArrayList<String> sprawdzenie() {
        wynik = new ArrayList<>();

        if (proba[0] == kod[0]){
            wynik.add("+");
        }
        else if(proba[0] == kod[1] || proba[0] == kod[2] || proba[0] == kod[3]) {
            wynik.add("-");
        }
        else{
            wynik.add(" ");
        }

        if (proba[1] == kod[1]) {
            wynik.add("+");
        }
        else if (proba[1] == kod[0] || proba[1] == kod[2] || proba[1] == kod[3]) {
            wynik.add("-");
        }
        else{
            wynik.add(" ");
        }

        if (proba[2] == kod[2]){
            wynik.add("+");
        }
        else if (proba[2] == kod[0] || proba[2] == kod[1] || proba[2] == kod[3]){
            wynik.add("-");
        }
        else{
            wynik.add(" ");
        }

        if (proba[3] == kod[3]){
            wynik.add("+");
        }
        else if (proba[3] == kod[0] || proba[3] == kod[1] || proba[3] == kod[2]){
            wynik.add("-");
        }
        else{
            wynik.add(" ");
        }
        return wynik;
    }
    public void pokaz()
    {
        for(int i = 0; i < 4; i++){
           // System.out.print(this.kod[i]);
        }
        System.out.println("");
    }
    public void zapisz(int probaNr, String nazwa){
        Writer output;

        try {
            output = new BufferedWriter(new FileWriter("Wyniki.txt", true));

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            output.append("Gracz " + nazwa +" odgadnął w podejściu nr : " + (probaNr) +
                    " dnia: " + dtf.format(now) +  "\n");
            output.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Wynik zapisany, dziekujemy! ");
    }

}
