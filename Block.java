
import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	Long timestamp;
    String hash;
    String previousHash;
    Long nonce = (long) 0;
    ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
    String merkelroot;
    
    public Block(String previousHash, ArrayList<Transaction> list) {
    	this.timestamp = System.currentTimeMillis();
    	this.previousHash = previousHash;
    	this.transactionList = new ArrayList<Transaction>(list);
    	this.merkelroot = totalTransaction();
    	this.hash = calcHash();
    }
    
    public String totalTransaction() {      //like a merkel root
    	String data = "";
    	for(Transaction trans: transactionList) {
    		data = data + trans.toString();
    	}
    	return data;
    }
    
    public String calcHash() {
    	String hash = StringUtil.applySha256(
    			previousHash +
				Long.toString(timestamp) +
				Long.toString(nonce) + 
				merkelroot
    			);
    	return hash;
    }
    
    public boolean mineBlock(int difficulty) {
    	String target = StringUtil.getDificultyString(difficulty);
    	nonce = (long) 0;
    	while(!target.equals(hash.substring(0, difficulty))) {
    		nonce++ ;
    		hash = calcHash();
    	}
    	return true;
    }
    
    public String toString() {
    	return StringUtil.getJson(this);
    }
    
    public void printBlock() {
		System.out.println("			|");
		System.out.println("			|");
		System.out.println("			|");
    	System.out.println("*******************************************************");
    	System.out.println("timestamp: "+timestamp);
    	System.out.println("hash: "+ hash);
    	System.out.println("prevHash: "+ previousHash);
    	System.out.println("nonce: "+ nonce);
    	
    	for(Transaction trans: transactionList) {
    		System.out.println("\n");
    		System.out.println("Sender name: ");
    		System.out.println(trans.senderName);
    		System.out.println("Sender publickey");
    		System.out.println(trans.sender);
    		System.out.println("Receiver name: ");
    		System.out.println(trans.receiverName);
    		System.out.println("Receiver publickey");
    		System.out.println(trans.receiver);
    		System.out.println(trans.data);
    		System.out.println("\n");
    		System.out.println("----------------------------");
    	}
    	System.out.println("*********************************************************");
    	//System.out.println("\n\n");
    }
    
}
