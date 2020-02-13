package empresa;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

public class Pessoas implements Runnable {

   
    private final String nome;
    private final Sexo sexo;
    private final Banheiro banheiro;
    // Pode sair
    private boolean podeSair;
    // Precisa ir no banheiro
    private boolean precisoEntrar;

 
    public Pessoas(String nome, Sexo sexo, Banheiro banheiro) {
        this.nome = nome;
        this.sexo = sexo;
        this.banheiro = banheiro;
        this.podeSair = false;
        this.precisoEntrar = true;
    }

    /* Função para utilização do banheiro */
    public void entrarBanheiro() {
        // Enter the banheiro
        this.banheiro.addUser(this);
        // System.out.println(this.getName() + " entered the banheiro.");
        if (this.banheiro.estaNoBanheiro(this)) {
            try {
                // Spend the time inside
                TimeUnit.SECONDS.sleep((new Random()).nextInt(1) + 1);
                this.podeSair = true;
                System.out.println(getName() + " terminou");
            } catch (InterruptedException ex) {
                Logger.getLogger(Pessoas.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    /* Sair do banheiro */
    public void sairBanheiro() {
        // Leave the banheiro
        this.banheiro.removeUser(this);
        // System.out.println(this.getName() + " left the banheiro.");
        this.podeSair = false;
        this.precisoEntrar = false;
    }

    /* Retorna o nome da pessoa */
    public String getName() {
        return this.nome;
    }

    /* Retorna o sexo da pessoa */
    public Sexo getSexo() {
        return this.sexo;
    }

    @Override
    public void run() {
        //System.out.println(this.getName());
        // If the person needs to go to the banheiro
        while (this.precisoEntrar) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pessoas.class.getName()).log(Level.SEVERE, null, ex);
            }
            // If they want to use
            if ((this.banheiro.sexoAtual().equals(this.getSexo())
                    || this.banheiro.sexoAtual().equals(Sexo.NONE))
                    && !this.banheiro.banheiroCheio()
                    && !this.banheiro.estaNoBanheiro(this)) {
                this.entrarBanheiro();
            }
            // If they want to leave
            if (this.podeSair) {
                this.sairBanheiro();
            }
        }
    }

    @Override
    public String toString() {
        return "Pessoa{" + "nome = " + this.nome + ", sexo = " + this.sexo + '}';
    }
}
