import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Implementacao extends UnicastRemoteObject implements QuestionarioInterface {

	protected Implementacao() throws RemoteException {
		super();
		
	}
	// declaracao das variaveis da classe "Implementacao"
	private int controlador = 0;
	private int identificador = 0;	
	private double peso=0.;
	private double altura=0.;
	private int idade;
	private double imc = 0.;

	public int getCountCliente() throws RemoteException {
		return identificador;
	}

	public void setCountCliente(int identificador) throws RemoteException {
		this.identificador += identificador;
	}
	
	// incrementa a variavel "controlador" para fazer controle da vez de cada cliente.
	public int getProximoCliente() throws RemoteException {

		if (controlador == 0) {
			controlador = 1;
		} else if (controlador == 1) {
			controlador = 2;
		} else {
			controlador = 1;
		}

		return controlador;
	}

	public int getCliente() throws RemoteException {
		return controlador;
	}
	
	public void limpezaParcial() throws RemoteException {
		controlador = 0;
		identificador = 0;
		imc = 0;
	}
	
	@Override
	public void setPeso(double peso) throws RemoteException {
		this.peso = peso;
	}

	@Override
	public double getPeso() throws RemoteException {
		return this.peso;
	}

	@Override
	public void setAltura(double altura) throws RemoteException {
		this.altura = altura;
	}

	@Override
	public double getAltura() throws RemoteException {
		return this.altura;
	}

	@Override
	public void setIdade(int idade) throws RemoteException {
		this.idade = idade;
	}

	public void calculaImc() throws  RemoteException{
		imc = (peso/(altura*altura));
	}
	
	@Override
	public int getIdade() throws RemoteException {
		return this.idade;
	}

	public double getImc()  throws RemoteException{
		return imc;
	}

	public void setImc(double imc) {
		this.imc = imc;
	}
}
