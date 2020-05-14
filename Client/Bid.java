package assignment7Client;

public class Bid {
	double amount;
	long timestamp;
	String username;
	int item;
	
	public Bid(double amount, long timestamp, String userID, int item) {
		this.amount = amount;
		this.timestamp = timestamp;
		this.username = userID;
		this.item = item;
	}
	
	public Bid(double amount, long timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
		this.username = null;
	}

}
