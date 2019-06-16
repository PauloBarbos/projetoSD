import java.rmi.Naming;

public class Servidor1 {

	public static void main(String[] args) {

		try {
			
			Implementacao Servidor = new Implementacao(); // criacao do objeto "Servidor" do tipo "Implementacao"
			Naming.rebind("//localhost/rmi", Servidor); // 
			System.out.println("Servidor no ar"); // mensagem para dar o feedback indicando que o servidor está no ar
			
		} catch (Exception e) {
			
			System.err.println("Houve algum problema" + e); // mensagem de erro junto com a excecao ocorrida
			e.printStackTrace();
			System.exit(2);
		}
		
		System.out.println("Aguardando inicialização do objeto..."); // mensagem que indica que só falta inicializar os clientes
	}
}
