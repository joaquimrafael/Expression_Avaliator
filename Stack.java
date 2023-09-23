package Avaliator;

/*-Programa avaliador de expressões matemáticas-
* Aplicação 1 - Estrutura de Dados I
* Faculdade de Computação e Informática
* Ciência da Computação
* Estrutura de Dados I – 3ª etapa – 2023.2
* Professor André Kishimoto
*/

/*
 * Grupo:
 * Lucas Trebacchetti Eiras - 32236905
 * Joaquim Rafael Mariano Prieto Pereira - 42201731
 * Antonio Carlos Sciamarelli Neto - 42209935 
 * Henrique Arabe - 42246830
 */

//criação da classe pilha
public class Pilha<T> {
	public Object[] stack;
	public static int MAX_SIZE = 100;
	public int pointer;

  //construtores da TAD pilha utilizada
	public Pilha() {
		this.stack = new Object[MAX_SIZE];
		this.pointer = -1;
	}
	
	public Pilha(int size) {
		this.stack = new Object[size];
		this.pointer = -1;
	}

  //coloca um elemento no topo da pilha
	public void push(T item) {
		if(this.pointer<this.stack.length) {
			this.pointer++;
			this.stack[pointer] = item;
		}else {
			System.out.println("A pilha já está cheia!");
		}
	}

  //tira o elemento do topo da pilha
	public T pop() {
		if(this.pointer-1>=-1) {
      return((T) this.stack[this.pointer--]);
		}else {
			return(null);
		}
	}

  //mostra o elemento do topo da pilha
	public T top() {
		if(this.pointer!=-1) {
			return((T) this.stack[this.pointer]);
		}else {
			throw new IllegalArgumentException ("A pilha está vazia");
		}
	}

  //mostra o tamanho da pilha
	public int size() {
		return(this.stack.length);
	}

  //conta o número de elementos
	public int count() {
		return(this.pointer+1);
	}

  //verifica se a pilha esta vazia
	public boolean isEmpty() {
		if(this.pointer==-1) {
			return(true);
		}
		return(false);
	}

  //verifica se a pilha esta cheia
	public boolean isFull() {
		if(this.pointer==this.stack.length-1) {
			return(true);
		}
		return(false);
	}

  //limpa a pilha
	public void clear() {
		this.pointer = -1;
	}
}

/*Referências:
*Material de aula:
*Programação de Computadores (versão "Java 101")
*POO - Conceitos básicos, classes e objetos (material do prof. Dr. Ivan Carlos Alcântara de *Oliveira)
*Tipos de dados
*Minizine sobre pilha
*https://www.devmedia.com.br/usando-generics-em-java/28981
*https://www.devmedia.com.br/trabalhando-com-arrays-em-java/25530
*https://www.devmedia.com.br/trabalhando-com-excecoes-em-java/27601
*ORACLE. Java Documentation. Disponível em: https://docs.oracle.com/en/java/.
*/