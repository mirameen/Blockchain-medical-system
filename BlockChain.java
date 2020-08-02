import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;

public class BlockChain implements Serializable{
	private static final long serialVersionUID = 1L;
	int difficulty = 4;
	public ArrayList<Block> Chain = new ArrayList<Block>();
	public ArrayList<Transaction> undoneTransaction = new ArrayList<Transaction>();
	public BlockChain() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		generateGenesisBlock();
	}
	
	public void generateGenesisBlock() {
		Wallet genesis = new Wallet("genesis");
		Transaction genesisTransaction = new Transaction(genesis.publickey,genesis.publickey,genesis.name,genesis.name,"Hello I am genesis block");
		undoneTransaction.add(genesisTransaction);
		Block genesisBlock = new Block("0",undoneTransaction);
		genesisBlock.mineBlock(difficulty);
		//System.out.println(genesisBlock);
		genesisBlock.printBlock();
		undoneTransaction.remove(0);
		Chain.add(genesisBlock);
	}
	
    public boolean verifyTransaction(ArrayList<Transaction> list) {
    	for(Transaction trans : list) {
    		if(!trans.verifySignature()) 
    			return false;
    	}
    	return true;
    }
    
    public boolean createBlock() {
    	if(verifyTransaction(undoneTransaction)) {
    		String previousHash = Chain.get(Chain.size()-1).hash;
    		Block newBlock = new Block(previousHash,undoneTransaction);
    		newBlock.mineBlock(difficulty);
    		Chain.add(newBlock);
    		newBlock.printBlock();
    		//System.out.println(newBlock);
    		undoneTransaction.clear();
    		return true;
    	}
    	return false;
    }
    
    public String viewUser(PublicKey publickey,String name) {
    	String str="";
    	str+="User: \n"+name+"\n"+"My public key is \n"+publickey+"\n";
    	try {
    		File datah = new File("data.txt");
    		datah.createNewFile();
    		FileWriter fw = new FileWriter(datah.getName(),true);
    		for(Block block : Chain) {
    			for(Transaction trans : block.transactionList) {

    				if(trans.sender == publickey) {
    					//fw.write("\n"+"Sent data: "+trans.data+" to "+ trans.receiver+" timestamp: "+Long.toString(block.timestamp));
    					str+="Sent data: "+trans.data+ " to \n" + trans.receiverName+"\n"+trans.receiver;
    				}
    				if(trans.receiver == publickey) {
    					//fw.write("\n"+"Received data: "+trans.data+" from "+ trans.sender+" timestamp: "+Long.toString(block.timestamp));
    					str+="Received data: "+trans.data + " from \n" + trans.senderName+"\n"+trans.sender;
    				}
    			}
    		}
    	fw.write(str);
    	fw.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return str;
    }
}