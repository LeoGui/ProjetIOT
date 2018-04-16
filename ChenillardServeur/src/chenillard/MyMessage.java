package chenillard;
//package serveur;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.MultivaluedMap;
//
//import serveur.DimmableSwitch;
//
//@Path("msg")
//public class MyMessage {
//
//	private final static String NAME = "name";
//	private final static String ADDRESS = "address";
//	private final static String VALUE = "value";
//	public String test ;
//
//	//DimmableSwitch sw = new DimmableSwitch(false, "test 1", 56);
//	
//
//
////	@POST
////	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
////	@Produces(MediaType.APPLICATION_JSON)
////
////	public DimmableSwitch postDimmableSwitch(MultivaluedMap<String, String> switchParams) {
////		String name = switchParams.getFirst(NAME);
////		String address = switchParams.getFirst(ADDRESS);
////		String value = switchParams.getFirst(VALUE);
////		System.out.println("Storing posted " + name + " " + address + " " + value);
////		sw.setName(name);
////		sw.setAddress(address);
////		sw.setValue(Integer.parseInt(value));
////		System.out.println("Switch info: " + sw.getName() + " " + sw.getAddress() + " " + sw.getValue());
////		test = "Switch info: " + sw.getName() + " " + sw.getAddress() + " " + sw.getValue();
////		getMessage(test);
////		return sw;
////		
////	}
//	
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getMessage(String text) {
//		return text;
//	}
//}
