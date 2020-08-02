import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import com.google.gson.*;

public class Data {
	public static void writeJSON(BlockChain obj, String Filename)
	{
		try(Writer writer = new FileWriter(Filename)){
			Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
				@Override
				public boolean shouldSkipField(FieldAttributes arg0) {
					return false;
				}
				
				@Override
				public boolean shouldSkipClass(Class<?> arg0)
				{
					return arg0 == PublicKey.class || arg0 == PrivateKey.class || arg0 == byte[].class;
				}
			}).setPrettyPrinting().create();
			gson.toJson(obj,writer);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

