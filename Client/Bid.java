package assignment7Client;

public class Bid {
	double amount;
	long timestamp;
	String username;
	
	protected Bid(double amount, long timestamp, String userID) {
		this.amount = amount;
		this.timestamp = timestamp;
		this.username = userID;
	}
	
	protected Bid(double amount, long timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
		this.username = null;
	}

}
