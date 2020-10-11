package project;

public class ConnectFactory {

	
	/**
	 * 
	 * @return a new ConnectImp instance
	 */
	public static Connect getConnectInstance() {
		Connect con=new ConnectImpl();
		return con;
	}



}