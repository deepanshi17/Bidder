package assignment7Client;

import java.util.*;

public class Auctioneer {
	AuctionItem Item;
	ArrayList<User> userList;
	Bid bestBid;
	User bestBidUser;
	Bid nextBestBid;
	User nextBestUser;
	boolean status;
	
	
	Auctioneer(AuctionItem item){
		this.Item = item;
		this.userList = new ArrayList<>();
		this.bestBid = new Bid(item.minPrice, System.nanoTime());
		this.bestBidUser = null;
		this.nextBestBid = null;
		this.nextBestUser = null;
		this.status = true;
	}
	
	protected int JoinAuction(User bidder, Bid bid) {
		if (bid.amount <= bestBid.amount) return -1;
		else if(this.status == false) return -2;
		else {
			if (!userList.contains(bidder)) {
				userList.add(bidder);
			}
			nextBestBid = bestBid;
			nextBestUser = bestBidUser;
			bestBid = bid;
			bestBidUser = bidder;
			bidder.updateHistory(bid);
			if(getTimeRemaining() <= 0) {
				status = false;
			}
			return 0;
		}
	}
	
	protected long getTimeRemaining() {
		return (Item.timeout * 1000000000) - (System.nanoTime() - Item.startTime);
	}
	

}
