package dao;
import model.Client;
import java.util.*;

public class Test {

	public static void main(String[] args) {
		
		ClientDao d = new ClientDao();
		
		
		
		
		
		
		List<Client> clients= d.getAll();
        for(Client e : clients) {
        System.out.println(e);
        }
        
		
	}
	
	

}
