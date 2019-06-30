package restful.webauthentication.MavenProject2;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import sun.misc.BASE64Decoder;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("factorial/{id}")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Object getUserById(@HeaderParam("authorization") String authString,@PathParam("id")int number){
       if(authString==null){
    	   return "Authorization Required";
       }
    	
         int factorial=1;
        if(!isUserAuthenticated(authString)){
            return "User not Authorized";
        }else{
        	for(int i=1;i<=number;i++){
            	factorial=factorial*(i*1);
            }	
        }
        
        return "factorial("+number+")"+factorial;
       
    }     
    private boolean isUserAuthenticated(String authString){
        
       
    	String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        // Decode the data back to original string
        byte[] bytes = null;
        try {
            bytes = new BASE64Decoder().decodeBuffer(authInfo);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String decodedAuth = new String(bytes);
        System.out.println(decodedAuth);
        final StringTokenizer tokenizer = new StringTokenizer(decodedAuth, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();
        if(username.equals("kamesh")&& password.equals("admin123@")){
        	return true;
        }
       
         
        return false;
    }
}
