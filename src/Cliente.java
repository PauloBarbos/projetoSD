import java.awt.Font;
import java.awt.HeadlessException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class Cliente {

	// declaraçao de variaveis.
	private static QuestionarioInterface controlador = null;
	private static int cliente = 0;
	private static double peso=0.;
	private static double altura=0.;
	private static int idade=0;
	private static String captura;
	
	// metodo principal "main".
	public static void main(String[] args) throws HeadlessException, NumberFormatException, RemoteException {
		
		// chama o metodo "menu()".
		try {
			
			menu();
			
		// dá erro se não conseguir.
		}catch(Exception e) {
			
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	// metodo "menu" que mostra as opcoes de servidores para o usuario.
	public static void menu() {

		String opcao = JOptionPane.showInputDialog(null,
				"OPÃ‡Ã•ES:\n\n1) Para acessar o SERVIDOR 1\n\n2) Para acessar o SERVIDOR 2\n\n3) Digite 3 para SAIR! ",
				"ESCOLHA DE SERVIDORES", JOptionPane.PLAIN_MESSAGE);
		try {

			switch (opcao) {
			case "1":
				controlador = (QuestionarioInterface) Naming.lookup("//10.0.200.6/rmi"); 
				controlador.setCountCliente(1);
				cliente = controlador.getCountCliente();
				inicializar();
				break;

			case "2":
				controlador = (QuestionarioInterface) Naming.lookup("//localhost/rmi");
				controlador.setCountCliente(1);
				cliente = controlador.getCountCliente();
				inicializar();
				break;

			case "3":
				break;

			default:
				menu();
				break;
			}

		} catch (Exception e) {
			menu();
		}
	}

	/**
	 * DISTRIBUI AS FUNCIONALIDADES PARA OS CLIENTES
	 */

	// Metodo "inicializar" faz controle a ordem de execucao dos metodos "cliente1" e "cliente2".
	public static void inicializar() {
		
		try {

			if (controlador.getCountCliente() == 1) {
				JOptionPane.showMessageDialog(null, "Aguardando o Segundo Cliente", "InformaÃ§Ã£o",
						JOptionPane.WARNING_MESSAGE);
			}

			while (controlador.getCountCliente() != 2) { // aguarda até que existam pelo menos dois clientes.
			}

			if (cliente == 1) { // caso a variavel "cliente" tenha valor 1, o metodo "cliente1" é chamado.
				cliente1();
			}

			if (cliente == 2) { // caso a variavel "cliente" tenha valor 2, o metodo "cliente2" é chamado.
				cliente2();
			}

		} catch (Exception e) {

		}
	}

	/**
	 * FUNCIONALIDADES DO CLIENTE 1
	 **/
	
	// metodo "cliente1".
	public static void cliente1() {

		try {	
				
				controlador.getProximoCliente(); // passa a vez para o proximo cliente.
				
			while (!vezCliente()) { //enquanto nÃ£o for a vez do cliente, nÃ£o avanÃ§a.
			}
			
			// o cliente 1 entra com o peso.
			
			do{
				
				captura = JOptionPane.showInputDialog(null, "Digite seu peso: ");			
				
			}while(captura.matches("[a-zA-z]+") || captura.isEmpty());
																	   
			peso = Double.parseDouble(captura);
			
			controlador.setPeso(peso);
			
			System.out.print("Cliente 1 perguntou");
			controlador.getProximoCliente(); // o prÃ³ximo cliente faz o que tem que fazer.
			
			while (!vezCliente()) { //enquanto nÃ£o for a vez do cliente, nÃ£o avanÃ§a.
			
			}

			// cliente 1 entra com idade.
			
			do{
				
				captura = JOptionPane.showInputDialog(null, "Digite sua idade: ");
			
			}while(captura.matches("[a-zA-z]+") || captura.isEmpty()); // caso o que foi digitado seja letra ou campo em branco ele pergunta  
																	   // de novo.
			
			idade = Integer.parseInt(captura); // parse de "String" para "int" e joga dentro da variavel "idade".
			
			controlador.setIdade(idade);	// usa o metodo "set" do objeto "controlador" que é uma interface onde está declarado o mesmo.
		    controlador.calculaImc();		// usa o metodo "calcularImc" do objeto "controlador" que é uma interface onde está declarado o mesmo.
			
		    imc(); // chamada do metodo "imc".
			
			JOptionPane.showMessageDialog(null, "Cliente 1 Finalizando!");
			
			controlador.limpezaParcial(); // limpa as variaveis.
			
			menu(); // chama o menu novamente.
			
		} catch (Exception e) {
			
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * FUNCIONALIDADES DO CLIENTE 2
	 */
	// metodo "cliente2".
	public static void cliente2() {

		try {

				JOptionPane.showMessageDialog(null, "Aguardando pergunta do CLIENTE (1). (CLIENTE 2)","",
					JOptionPane.INFORMATION_MESSAGE);

			while (!vezCliente()) { // enquanto nÃ£o for a vez do cliente, nÃ£o avanÃ§a.
				
			}
			
			// cliente 2 entra com altura.
			
			// faz uma pergunta aqui.
			
			do{
				
				captura = JOptionPane.showInputDialog(null, "Digite sua altura: ");
			
			}while(captura.matches("[a-zA-z]+") || captura.isEmpty());
			
			   														   
			altura = Double.parseDouble(captura);

			if(altura > 100){
				
				altura = (altura / 100);
			}
			
			controlador.setAltura(altura);
			controlador.getProximoCliente();
			
			while( controlador.getImc() == (double) 0 ) {
				
				System.out.println(controlador.getImc());
			}
			
			JOptionPane.showMessageDialog(null, "Cliente 2 Finalizando!");
			
			menu();
			
		} catch (Exception e) {

		}
	}
	
	// faz o controle para verificar se aquela vez é realmente do cliente em questao.
	public static boolean vezCliente() {
		
		try {
		
			return cliente == controlador.getCliente();
	
		}catch (Exception e) {
			
			return false;
		}
	}
    
	public static void imc() {
		
		try{
				if(controlador.getImc() < 18.5){
				
					JOptionPane.showMessageDialog(null, "Magro");			
				}
				else if(controlador.getImc() >= 18.5 && controlador.getImc() <= 24.9){
				
					JOptionPane.showMessageDialog(null, "Normal");	
				}
				else if(controlador.getImc() >= 25 && controlador.getImc() <= 29.9){
				
					JOptionPane.showMessageDialog(null, "Sobrepeso");
				}
				else if(controlador.getImc() >= 30 && controlador.getImc() <= 39.9){
				
					JOptionPane.showMessageDialog(null, "Obsidade");
				}
				else{

					UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("ARIAL", Font.PLAIN, 25)));
					JOptionPane.showMessageDialog(null ,
					     "<html><div color=red>Obsidade grave!" , "Perigo!" , JOptionPane.WARNING_MESSAGE);
				}
			
		}catch(Exception ex) {
			
		}
	}
}
