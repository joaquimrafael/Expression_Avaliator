package Avaliator;
import java.util.*;

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
 * Henrique Arabe Neres de Farias- 42246830
 */

public class Main {
    public static void main(String args[]){
    	//Chamada da função que tem acesso as outras funções
        int step = 1;
        displayMenu(step);
    }

    public static void validate(String expression){
    	//Declarando operandos válidos
        String operands = "+-*/^()";
        Pilha<Character> stack = new Pilha<Character>(expression.length());
        for(int i=0;i<expression.length();i++){
            char value = expression.charAt(i);
            if(i>0){
            	//Se estiver no segundo elemento para frente, verifica char anterior é letra e o atual também
                char before = expression.charAt(i - 1);
                if(Character.isLetter(before) && Character.isLetter(value)){
                    throw new IllegalArgumentException("A expressão possui variável com mais de uma letra!");
                }
            }
            //Verificando existência de dígito
            if(Character.isDigit(value)){
                throw new IllegalArgumentException("A expressão possui números!");
            }
            //Verificando se não é letra e não e operando
            if(!Character.isLetter(value) && (operands.indexOf(value) == -1)){
                throw new IllegalArgumentException("A expressão possui caractere ilegal!");
            }
        }
        //Verificando parentêses e desempilhando o que está na pilha ao achar o fechamento
        for (int i=0;i<expression.length();i++){
            if(expression.charAt(i) == '(') {
                stack.push(expression.charAt(i));
            }
            if(expression.charAt(i) == ')'){
                if(stack.isEmpty()) {
                	throw new IllegalArgumentException("O uso de parênteses na expressão está incorreto!");
                }
                stack.pop();
            }
        }
        //Verificando se algum parentêses ficou sem fechamento
        if(stack.count()>0){
            throw new IllegalArgumentException("O uso de parênteses na expressão está incorreto!");
        }
    }

    public static String conversionPolishNotation(String expression) {
        Pilha<Character> stack2 = new Pilha<>();
        String operands = "+-*/^";
        StringBuilder output = new StringBuilder();
        //Percorrendo String
        for (int i=0;i<expression.length();i++) {
        	//Adicionando variável na pilha
            if (Character.isLetter(expression.charAt(i))) {
                output.append(expression.charAt(i));
            //Verificando se é operando
            } else if(operands.contains(String.valueOf(expression.charAt(i)))){
            	//Verificando precedência e checando se a precedência é igual
                while(!stack2.isEmpty() && (precedence(expression.charAt(i))<=precedence(stack2.top()))){
                    output.append(stack2.pop());
                }
                //Colocando operando na pilha
                stack2.push(expression.charAt(i));
            //Fechando parenteses e colocando parenteces de abertura
            } else if(expression.charAt(i) == '(') {
                stack2.push(expression.charAt(i));
            } else if(expression.charAt(i) == ')') {
            	//Esvaziando até achar fechamento
                while(!stack2.isEmpty() && stack2.top() !='('){
                    output.append(stack2.pop());
                }
                //Tirando fechamento da pilha
                if(!stack2.isEmpty() && stack2.top() =='('){
                    stack2.pop();
                }
            }
        }
        //Tirando remanescente do stack
        while(!stack2.isEmpty()){
            output.append(stack2.pop());
        }
        return output.toString();
    }
    //Funçao que retorna int significando precedencia
    private static int precedence(char operator) {
        if (operator == '^') {
            return 3;
        }else if (operator == '*' || operator == '/') {
            return 2;
        }else if (operator == '+' || operator == '-') {
            return 1;
        }else {
            return 0;
        }
    }

    public static int doCalculation(String expression, Map<Character, Integer> variableMap) {
        Pilha<Integer> stack3 = new Pilha<>();
        String operands = "+-*/^";
        //Percorrendo a expressão
        for(int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            //Verificando se achou variável
            if(Character.isLetter(currentChar)) {
            	//Verificando se faz parte do map
                if(!variableMap.containsKey(currentChar)) {
                    throw new IllegalArgumentException("Variável não encontrada: " + currentChar);
                }
                //Colocando variável na pilha
                int varValue = variableMap.get(currentChar);
                stack3.push(varValue);
                //Verificando se é operando e preparando variáveis para operação
            }else {
            	int result = 0;
            	if(operands.contains(String.valueOf(currentChar))) {
                    int num2 = stack3.pop();
                    int num1 = stack3.pop();
                    //Fazendo operações
                    switch (currentChar) {
                        case '^':
                            result = (int)Math.pow(num1, num2);
                            break;
                        case '/':
                            result = num1/num2;
                            break;
                        case '*':
                            result = num1*num2;
                            break;
                        case '+':
                            result = num1+num2;
                            break;
                        case '-':
                            result = num1-num2;
                            break;
                    }
            	}
                //Colocando resultado na pilha para ser operado novamente ou ser o resultado final
                stack3.push(result);
            }
        }
        //Retornando o valor final
        return stack3.pop();
    }

    public static void displayMenu(int step){
        Scanner int_input = new Scanner(System.in);
        Scanner str_input = new Scanner(System.in);

        int step_user;
        int count = 0;
        //Mapear variáveis com os valores que irá obter
        Map<Character,Integer> variableMap = new HashMap<>();
        String expression = null;
        String polish_expression = null;
        boolean cond = true;
        int result;
        while(cond){
            System.out.println("<<– Avaliador de expressões matemáticas ->>");
            System.out.println("1. Inserir a expressão matemática infixa");
            System.out.println("2. Inserir os valores numéricos associados às variáveis");
            System.out.println("3. Converter a expressão, da notação infixa para posfixa, e exibir a expressão convertida");
            System.out.println("4. Avaliar a expressão");
            System.out.println("5. Encerrar");
            System.out.print("Digite a etapa a ser realizada (siga a ordem e não pule nenhum passo!): ");
            step_user = int_input.nextInt();
            //Fazendo verificação de exigência de passos anteriores
            if(step_user == 5) {cond = false; continue;}
            if (step_user <= step){
                step++;
                //Listando os possíveis casos
                switch(step_user){
                    case 1:
                    	//Obtendo expressão do usuário
                        System.out.print("Digite a expressão matemática infixa: ");
                        expression = str_input.nextLine();
                        expression = expression.replaceAll(" ", "");
                        for(int i=0;i<expression.length();i++) {
                            if(Character.isLetter(expression.charAt(i))) {
                                count++;
                            }
                        }
                        //Validando se expressão é válida ou não
                        validate(expression);
                        continue;
                    case 2:
                    	//Colocando os números em um hashmap para cada variável
                        for(int i=0;i<count;i++){
                            System.out.print("Digite o valor para a variável " + (char) ('A' + i) + " : ");
                            int value = int_input.nextInt();
                            variableMap.put((char) ('A' + i), value);
                        }
                        continue;
                    case 3:
                    	//Convertendo para expressão polonesa
                        polish_expression = conversionPolishNotation(expression);
                        System.out.println("Expressão em notação pós-fixa com variáveis: " + polish_expression);
                        continue;
                    case 4:
                    	//Fazendo cálculo
                        result = doCalculation(polish_expression, variableMap);
                        System.out.print("O resultado da expressão: ");
                        //Printando o cálculo com as variáveis ja substituídas
                        for(int i=0;i<polish_expression.length();i++) {
                        	if(variableMap.containsKey(polish_expression.charAt(i))) {
                        		System.out.print(variableMap.get(polish_expression.charAt(i)));
                        	}else {
                        		System.out.print(polish_expression.charAt(i));
                        	}
                        }
                        System.out.print(" ");
                        //Printando resultado da operação
                        System.out.println("é igual a " + result);
                        continue;

                    //Qualquer outro input fora de 1<=x<=6
                    default:
                        System.out.println("Digite uma etapa válida!");
                        continue;
                }
            }else{
                System.out.println("SIGA A ORDEM DOS PASSOS, DIGITE UMA ETAPA VÁLIDA!");
                continue;
            }
        }
        int_input.close();
        str_input.close();
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
*https://www.devmedia.com.br/hashmap-java-trabalhando-com-listas-key-value/29811
*https://www.devmedia.com.br/conhecendo-a-interface-map-do-java/37463
*https://www.arquivodecodigos.com.br/dicas/3592-java-como-usar-o-metodo-append-para-adicionar-mais-conteudo-ao-final-de-um-stringbuffer.html
*/