package empresa;

import java.util.LinkedHashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static empresa.Sexo.MULHER;

public class Banheiro {
    
    // Capacidade de pessoas no banheiro
    private static final int CAPACIDADE = 3;
    // Singleton
    private static Banheiro instance = new Banheiro(CAPACIDADE);
    //Using sex
    private Sexo sexoAtual;
    // Bathroom capacidade
    private final int capacidade;
    // Users list
    private LinkedHashSet<Pessoas> usuarios;
    // Lock
    private Lock lock = new ReentrantLock();

    /* Construtor */
    public Banheiro(int capacidade) {
        this.capacidade = capacidade;
        this.sexoAtual = Sexo.NONE;
        this.usuarios = new LinkedHashSet<>();
    }

    /* Instancia banheiro**/
    public static Banheiro getInstance() {
        return instance;
    }

    /* Adiciona pessoas no banheiro */
    public void addUser(Pessoas pessoa) {
       
        System.out.println(pessoa.getName() + " quer entrar");
        if(pessoa.getSexo().equals(MULHER)){
            this.lock.lock();

            try {
                //verifica se é a primeira pessoa a entrar no banheiro
                if (this.banheiroVazio()) {     
                        this.sexoAtual = pessoa.getSexo();
                }
                
                // Verifica se o banheiro não está vazio
                if (!this.banheiroCheio() && !this.usuarios.contains(pessoa) && sexoAtual().equals(pessoa.getSexo())) {
                    // Add the pessoa
                    if (this.usuarios.add(pessoa)) {
                        System.out.println(pessoa.getName() + " entrou no banheiro");
                    }

                    // Verifica se o banheiro está cheio
                    if (this.banheiroCheio()) {
                        System.out.println("O banheiro está cheio! ");
                    }
                }
            } finally {
                this.lock.unlock();
            }
        } else {
            
            this.lock.lock();

            try {
                //verifica se é a primeira pessoa a entrar no banheiro
                if (this.banheiroVazio()) {     
                        this.sexoAtual = pessoa.getSexo();

                }

                // Verifica se o banheiro não está vazio
                if (!this.banheiroCheio() && !this.usuarios.contains(pessoa) && sexoAtual().equals(pessoa.getSexo())) {
                    // Add the pessoa
                    if (this.usuarios.add(pessoa)) {
                        System.out.println(pessoa.getName() + " entrou no banheiro");
                    }

                    // Verifica se o banheiro está cheio
                    if (this.banheiroCheio()) {
                        System.out.println("O banheiro está cheio! ");
                    }
                }
            } finally {
                this.lock.unlock();
            }   
        }
    }

    /* Remove a pessoa do banheiro */
    public void removeUser(Pessoas pessoa) {
        this.lock.lock();
        try {
            // Checa se o banheiro não está vazio
            if (!this.banheiroVazio()) {
                if (this.usuarios.remove(pessoa)) {
                    System.out.println(pessoa.getName() + " saiu do banheiro");
                }
                // Checa se o banheiro está vazio
                if (this.banheiroVazio()) {
                    System.out.println("O banheiro está vazio");
                    this.sexoAtual = Sexo.NONE;
                }
            }
        } finally {
            this.lock.unlock();
        }
    }

    /* Seja verdadeiro se a pessoa selecionada estiver no banheiro e falsa de outra forma. */
    public boolean estaNoBanheiro(Pessoas pessoa) {
        return this.usuarios.contains(pessoa);
    }

    /* Retorna true se o banheiro está cheio e false se está vazio */
    public boolean banheiroCheio() {
        return this.capacidade == this.usuarios.size();
    }

    /* Retorna true se o banheiro esta vazio e false se está cheio */
    public boolean banheiroVazio() {
        return this.usuarios.isEmpty();
    }

    /* Pega o sexo atual de quem está querendoo entrar */ 
    public Sexo sexoAtual() {
        return this.sexoAtual;
    }

    @Override
    public String toString() {
        return "Banheiro {" + "sexoAtual = " + this.sexoAtual
                + ", capacidade = " + this.capacidade
                + ", numberOfUsers = " + this.usuarios.size() + '}';
    }
}
