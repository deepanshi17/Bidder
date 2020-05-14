package assignment7Client;

import java.util.*;

public class User {
	String name;
	String userName;
	ArrayList<Bid> bidHistory;
	
	protected User() {
		this.name = null;
		this.userName = null;
		this.bidHistory = new ArrayList<>();
	}
	
	protected User(String name, String userName) {
		this.name = name;
		this.userName = userName;
		this.bidHistory = new ArrayList<>();
	}
	
	protected void updateHistory(Bid bid) {
		bidHistory.add(bid);
	}
	
	

}
