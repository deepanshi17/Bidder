package assignment7Client;

public class AuctionItem {
	String name;
	String description;
	int id;
	long timeout;
	long startTime;
	double minPrice;
	double currPrice;
	double buyItNow;
	String category;

	AuctionItem(String name, String description, int id, long timeout, double minPrice, double buyItNow,
			String category) {
		this.name = name;
		this.description = description;
		this.id = id;
		this.timeout = timeout;
		this.currPrice = minPrice;
		this.minPrice = minPrice;
		this.buyItNow = buyItNow;
		this.category = category;
	}

	public String toString() {
		return name + " " + Double.toString(currPrice) + " " + Double.toString(minPrice);
	}
}
