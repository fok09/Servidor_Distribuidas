package server;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;

import tda.TDASistemaCine;
import app.*;

public class Server {
    
TDASistemaCine objetoRemoto;
	
	public static void main(String[] args)
	{
		new Server();
	}
	
	public Server() {
		iniciar();
	}
	
    public void iniciar() {
    	try {
    		LocateRegistry.createRegistry(1099);	
            TDASistemaCine sistemaCine = new SistemaCine();
            Naming.rebind ("//localhost/SistemaCine", sistemaCine);
            System.out.println("Fijado en //localhost/SistemaCine");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
