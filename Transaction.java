import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Transaction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public PublicKey sender;
	public String senderName;
	public PublicKey receiver;
	public String receiverName;
	public String senderstring;
	public String receiverstring;
	public String data;
	public byte[] signature;
	
	public Transaction(PublicKey sender,PublicKey receiver,String senderName,String receiverName,String data){
		this.sender = sender;
		this.receiver = receiver;
		this.data = data;
		this.senderstring = sender.toString();
		this.receiverstring = receiver.toString();
		this.senderName=senderName;
		this.receiverName=receiverName;
	}
	
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + this.data;
		signature =  StringUtil.applyECDSASig(privateKey, data);
	}
	
	public boolean verifySignature() {	
		String input = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + this.data;
		return StringUtil.verifyECDSASig(sender, input, signature);
	}
	
	public String toString() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + this.data;
		return data;
	}
	
	
}