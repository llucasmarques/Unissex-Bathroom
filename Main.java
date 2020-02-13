package empresa;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Universidade Federal de Mato Grosso
 * Ciência da Computação
 * Programação Paralela e Distribuida
 *
 * Alunos: João Victor Macedo
 *         Lucas Marques
 *         Henrico Vilela
 * 
 * Controle de Entrada de Pessoas em um Banheiro Unissex
 * 
 */
public class Main {

    public static void main(String[] args) {
        // Cria o banheiro
        Banheiro banheiro = Banheiro.getInstance();
        // Cria uma lista de pessoas que utilização o banheiro
        List<Pessoas> usuarios = new ArrayList<>();
        // Cria 10 pessoas e adiciona na lista
        for (int i = 0; i < 10; i++) {
            // Cria as mulheres quando for par e homens quando for impar
            if (i % 2 == 0) {
                usuarios.add(new Pessoas(("Maria" + i), Sexo.MULHER, banheiro));
            } else {
                usuarios.add(new Pessoas(("João" + i), Sexo.HOMEM, banheiro));  
            }
        }
        
        // Inicia uma thread para cada pessoa na lista
        usuarios.stream().map((Pessoas) -> new Thread(Pessoas)).forEach((t) -> {
            t.start();
        });
        
        // Espera cada thread de pessoa na lista terminar
        usuarios.stream().map((Pessoas) -> new Thread(Pessoas)).forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}