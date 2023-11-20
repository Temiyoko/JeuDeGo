package jeu;

import java.util.Scanner;

public class JeuDeGo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {
            String saisie = sc.nextLine().trim();
            if (saisie.equals("quit")){
                System.out.println("=");
                break;
            }
            else{
                System.out.println("? unknown command");
            }
        }
        sc.close();
    }
}
