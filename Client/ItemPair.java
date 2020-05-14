package assignment7Client;

import javafx.beans.property.*;

public class ItemPair {
	private final SimpleStringProperty id;
	private final SimpleStringProperty amount;
	

	protected ItemPair(String iD, String aMount) {
		this.id = new SimpleStringProperty(iD);
		this.amount = new SimpleStringProperty(aMount);
	}
	
	public String getId() {
		return id.get();
	}
	
	public String getAmount() {
		return amount.get();
	}
	
	public void setId(String iD) {
        id.set(iD);
    }
    
    public void setAmount(String aMount) {
        amount.set(aMount);
    }
	
}
