import java.awt.HeadlessException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import javax.swing.*;

public class Cliente {

	// declaracao de variaveis.
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
			
		// da erro se nao conseguir.
		}catch(Exception e) {
			
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	// metodo "menu" que mostra as opcoes de servidores para o usuario.
	public static void menu() {

		String opcao = JOptionPane.showInputDialog(null,
				"Opcoes:\n\n1) Para acessar o SERVIDOR 1\n\n2) Para acessar o SERVIDOR 2\n\n3) Digite 3 para SAIR! ",
				"ESCOLHA DE SERVIDORES", JOptionPane.PLAIN_MESSAGE);
		try {

			switch (opcao) {
			case "1":
				
//				HelloInterface hello = (HelloInterface)Naming.lookup("//localhost/Hello");
//				This Naming.lookup() call inspects the RMI Registry running in the localhost for a binding under the name "Hello".
//				It returns an Object that has to be cast to whatever remote interface you're expecting it to be.
//				You can then use that object to call the remote methods defined in the interface.
				
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

	// Metodo "inicializar" faz controle da ordem de execucao dos metodos "cliente1" e "cliente2".
	public static void inicializar() {
		
		try {

			if (controlador.getCountCliente() == 1) {
				JOptionPane.showMessageDialog(null, "Aguardando o Segundo Cliente", "Informacao",
						JOptionPane.WARNING_MESSAGE);
			}

			while (controlador.getCountCliente() != 2) { // aguarda ate que existam pelo menos dois clientes.
			}

			if (cliente == 1) { // caso a variavel "cliente" tenha valor 1, o metodo "cliente1" eh chamado.
				cliente1();
			}

			if (cliente == 2) { // caso a variavel "cliente" tenha valor 2, o metodo "cliente2" eh chamado.
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
				
			while (!vezCliente()) { //enquanto nao for a vez do cliente, nap avanca.
			
			}
			
			// o cliente 1 entra com o peso.
			
			do{
				
				captura = JOptionPane.showInputDialog(null, "Digite seu peso: ");			
				
			}while(captura.matches("[a-zA-z]+") || captura.isEmpty());
																	   
			peso = Double.parseDouble(captura);
			
			controlador.setPeso(peso);
			
			controlador.getProximoCliente(); // o proximo cliente faz o que tem que fazer.
			
			while (!vezCliente()) { //enquanto nao for a vez do cliente, nao avanca.
			
			}

			// cliente 1 entra com idade.
			
			do{
				
				captura = JOptionPane.showInputDialog(null, "Digite sua idade: ");
			
			}while(captura.matches("[a-zA-z]+") || captura.isEmpty()); // caso o que foi digitado seja letra ou campo em branco ele pergunta.  
																	   // de novo.
			
			idade = Integer.parseInt(captura); // parse de "String" para "int" depois joga dentro da variavel "idade".
			
			controlador.setIdade(idade);	// usa o metodo "set" do objeto "controlador" que eh uma interface onde estah declarado o mesmo.
		    controlador.calculaImc();		// usa o metodo "calcularImc" do objeto "controlador" que eh uma interface onde estah declarado o mesmo.
			
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

			while (!vezCliente()) { // enquanto nao for a vez do cliente, nao avanca.
				
			}
			
			// cliente 2 entra com altura.
			
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
				
			}
			
			JOptionPane.showMessageDialog(null, "Cliente 2 Finalizando!");
			
			menu();
			
		} catch (Exception e) {

		}
	}
	
	// faz o controle para verificar se aquela vez eh realmente do cliente em questao.
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

					JOptionPane.showMessageDialog(null ,
					     "<html><div color=red>Obsidade grave!" , "Perigo!" , JOptionPane.WARNING_MESSAGE);
				}
			
		}catch(Exception ex) {
			
		}
	}
}
