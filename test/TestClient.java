/*
 * Vsevolod Geraskin
 * A00242894
 */


import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;
import com.ericsson.otp.erlang.OtpErlangList;
import com.ericsson.otp.erlang.OtpErlangString;
import com.ericsson.otp.erlang.OtpErlangPid;

public class TestClient {

	private static OtpConnection conn;
	private static OtpSelf self;
	private static OtpPeer other;

	 public OtpErlangObject received;
	 private final String peer;
	 private final String cookie;
	 
	 public static void main(String []args){
		 new TestClient("chatserver@192.168.0.101","sev11");
	 }

	  public TestClient(String _peer, String _cookie) {
			OtpErlangObject[] elements;
			OtpErlangList erlangList;
			OtpErlangString name;
			OtpErlangPid id;

		  	peer = _peer;
		  	cookie = _cookie;
		  	connect();
		try {
			
			elements = new OtpErlangObject[] {self.pid(),new OtpErlangString("sev")};
			erlangList = new OtpErlangList(elements);	
		
			conn.sendRPC("genchat_server","subscribe",erlangList);
			received = conn.receiveRPC(); 
			System.out.println(received); 

			elements = new OtpErlangObject[] {new OtpErlangString("testing") };
			erlangList = new OtpErlangList(elements);	
			
			conn.sendRPC("genchat_server","broadcast",erlangList);
			received = conn.receiveRPC(); 

			System.out.println(received); 

			conn.sendRPC("genchat_server","clientlist",new OtpErlangList());

			received = conn.receiveRPC(); 
			received = conn.receiveRPC(); 
			
			erlangList = new OtpErlangList(received);

			System.out.println(erlangList);
	

			elements = new OtpErlangObject[] {self.pid(),new OtpErlangString("sev")};
			erlangList = new OtpErlangList(elements);	
		
			conn.sendRPC("genchat_server","unsubscribe",erlangList);
		}
	   catch (Exception exp) {
	     System.out.println("rpc error is :" + exp.toString());
	     exp.printStackTrace();
	   }
		//received = connection.receiveRPC();
           /*Do Calls to Rpc methods and then close the connection*/
		  disconnect();

	  }

	  private void connect() {
	   System.out.print("Please wait, connecting to "+peer+"....\n");

	   String javaClient ="testclient@192.168.0.101";
	   try {
	     self = new OtpSelf(javaClient, cookie.trim());
	     other = new OtpPeer(peer.trim());
	     conn = self.connect(other);
	     System.out.println("Connection Established with "+peer+"\n");
	   }
	   catch (Exception exp) {
	     System.out.println("connection error is :" + exp.toString());
	     exp.printStackTrace();
	   }

	 }

	 public void disconnect() {
	   System.out.println("Disconnecting....");
	   if(conn != null){
	     conn.close();
	   }
	   System.out.println("Successfuly Disconnected");
	 }

}
