package assignment7Client;

import java.util.*;

public class User {
	String name;
	String userName;
	String password;
	ArrayList<Bid> bidHistory;
	
	protected User(String name, String userName, String password) {
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.bidHistory = new ArrayList<>();
	}
	
	protected void updateHistory(Bid bid) {
		bidHistory.add(bid);
	}
	
	

}
