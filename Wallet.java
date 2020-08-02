import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.*;

public class Wallet {
	public PublicKey publickey;
	public PrivateKey privatekey;
	public String name;
	/*public Wallet(String name) {
		generateKeyPair();
		this.name=name;
	}*/
	long prime = 999999937L;
	long g = 23;
	long id ;
	long temp_r;
	public Wallet(String name) {
		this.name = name;
		Random rand =  new Random();
		id = Math.abs(rand.nextLong())% prime;
		System.out.println(powermod(g,10,prime)+" is the num");
		generateKeyPair();
	}
	public long getr() {
		return (long) (Math.random()*100000);
	}
	public int getb() {
		return (int) (Math.random()*25);
	}
	public long gety() {
		return powermod(g,id,prime);
	}
	public long geth() {
		temp_r = getr();
		return powermod(g,temp_r,prime);
	}
	public long gets(int b) {
		return (temp_r + b*id)%(prime -1);
	}
	public boolean verifyid(Wallet sender) {
		long h = sender.geth();
		int b = getb();
		long s = sender.gets(b);
		long y = sender.gety();
		long yb = powermod(y,b,prime);
		long hyb = (h*yb)%prime;
		if(powermod(g,s,prime) == hyb) {
			return true;
		}
		return false;
	}
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); //256 
	        KeyPair keyPair = keyGen.generateKeyPair();
	        // Set the public and private keys from the keyPair
	        privatekey = keyPair.getPrivate();
	        publickey = keyPair.getPublic();
	        
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	long powermod(long x, long y, long mod) {
		long temp = 1;
		x = x%mod;
		if(x==0) return 0;
		while(y>0) {
			if((y & 1L) == 1L) {
				temp = (temp*x)%mod;
			}
			y = y>>1;
			x = (x*x)%mod;
		}
		return temp;
	}
	public Transaction sendData(PublicKey receiver,String senderName,String receiverName,String data) {
		Transaction newTransaction = new Transaction(publickey,receiver,senderName,receiverName,data);
		newTransaction.generateSignature(privatekey);
		return newTransaction;
	}
}