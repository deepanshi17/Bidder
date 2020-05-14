package assignment7Client;

public class Message {
	String type;		// upper, lower, or strip
	String command;
	String input;
	String name;
	String password;
	int number;
	double amount;
	long timestamp;
	String username;
	int id;

	protected Message() {
		this.type = "";
		this.input = "";
		this.command = "";
		this.number = 0;
		System.out.println("client-side message created");
	}
	
	protected Message(String command) {
		this.type = "";
		this.input = command;
		this.command = command;
		this.number = 0;
		System.out.println("client-side message created");
	}

	protected Message(String type, String command, String input, int number) {
		this.type = type;
		this.input = input;
		this.number = number;
		this.command = command;
		System.out.println("client-side message created");
	}
	
	protected Message(String command, String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.command = command;
		System.out.println("client-side message created");
	}
	
	protected Message(String command, String username, Double amount, Long timestamp, int id) {
		this.username = username;
		this.command = command;
		this.amount = amount;
		this.timestamp = timestamp;
		this.id = id;
		System.out.println("client-side message created");
	}
	
	protected Message(String command, String input, int number) {
		this.type = "lower";
		this.input = input;
		this.number = number;
		this.command = command;
		System.out.println("client-side message created");
	}
	
	protected Message(String command, String user) {
		this.type = "lower";
		this.input = command;
		this.command = command;
		this.username = user;
		System.out.println("client-side message created");
	}
}
