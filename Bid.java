package assignment7Client;

public class Bid {
	double amount;
	long timestamp;
	int userID;
	
	protected Bid(double amount, long timestamp, int userID) {
		this.amount = amount;
		this.timestamp = timestamp;
		this.userID = userID;
	}

}
