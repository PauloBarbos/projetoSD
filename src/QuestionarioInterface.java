import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuestionarioInterface extends Remote {
	
	void setPeso(double peso) throws RemoteException;
	double getPeso() throws RemoteException;
	
	void setAltura(double altura) throws RemoteException;
	double getAltura() throws RemoteException;
	
	void setIdade(int idade) throws RemoteException;
	int getIdade() throws RemoteException;
	
	double getImc() throws RemoteException;
	
	int getCountCliente() throws RemoteException;
	void setCountCliente(int identificador) throws RemoteException;
	int getCliente() throws RemoteException;
	int getProximoCliente() throws RemoteException;
	void limpezaParcial() throws RemoteException;
	void calculaImc() throws  RemoteException;
	
}
