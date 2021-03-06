package com.techelevator.tenmo;

import java.util.Arrays;



import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfers;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TenmoApplicationServices;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    
    TenmoApplicationServices appService = new TenmoApplicationServices(API_BASE_URL);    
        
    public static void main(String[] args) throws AuthenticationServiceException {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() throws AuthenticationServiceException {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() throws AuthenticationServiceException {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			} 
		}
	}
	
	private void viewCurrentBalance() throws AuthenticationServiceException {
	    String authToken = currentUser.getToken();
	    Long longUserId = ((long) appService.getIdByUsername(currentUser.getUser().getUsername(), authToken));
		System.out.println("Your current account balance is: $" + String.format("%.2f", 
				appService.getBalanceByUserId(longUserId, authToken).getBalance()));
	} 

	private void viewTransferHistory() throws AuthenticationServiceException {
	    String authToken = currentUser.getToken();
	    Long longUserId = ((long) appService.getIdByUsername(currentUser.getUser().getUsername(), authToken));

		System.out.println("-----------------------------------------------------");
		System.out.println("                   T R A N S F E R S               \n");
		System.out.println(" ID                  FROM/TO                  AMOUNT ");		
		System.out.println("-----------------------------------------------------");
		
		formattedTransferList(appService.getTransfersByAccount(longUserId, authToken));
		
		System.out.println("-----------------------------------------------------\n");
		
		String transferId = console.getUserInput("Please enter transfer ID to view details (0 to cancel)");
		
		if (transferId.equals("0")){
			mainMenu();
		} else {
			try{
				System.out.println("\n------------------------------------------------");
				System.out.println("         T R A N S F E R  D E T A I L S         ");	
				System.out.println("------------------------------------------------");
				
				Long longTransferId = Long.parseLong(transferId);
				formattedTransferDetails(appService.getTransfersByTransferId(longTransferId, authToken));			

				System.out.println("------------------------------------------------\n");

			} catch (Exception e) {
				System.out.println("\nInvalid entry. Please try again.\n");
				sendBucks();
			}
		}	
	}

	private void viewPendingRequests() {
	}

	private void sendBucks() throws AuthenticationServiceException {
		
	    String authToken = currentUser.getToken();
	    Long longUserId = ((long) appService.getIdByUsername(currentUser.getUser().getUsername(), authToken));
		
		System.out.println("------------------------------------------------");
		System.out.println("             S E N D  T E  B U C K S            \n");
		System.out.println(" USER ID              NAME                      ");		
		System.out.println("------------------------------------------------");
		
		formattedUserList(appService.listUsers(authToken));
		
		System.out.println("------------------------------------------------\n");
		
		String idSendingTo = console.getUserInput("Enter ID of user you are sending to (0 to cancel)");
		
		if (idSendingTo.equals("0")){
			mainMenu();
		} else {
			try{
				Long longIdSendingTo = Long.parseLong(idSendingTo);
				String enteredAmount = console.getUserInput("Enter amount");
				Double formattedAmount = Double.parseDouble(enteredAmount);

				if (formattedAmount > appService.getBalanceByUserId(longUserId, authToken).getBalance()) {
					System.out.println("\nInsufficient funds for transfer.\n");
					sendBucks();
				} else {
					Transfers myNewTransfer = createSendTransfer(longUserId, longIdSendingTo, formattedAmount);
					Long myNewTransferId = myNewTransfer.getTransferId();
				
					appService.pushTransfer(myNewTransferId, myNewTransfer, authToken);		
					appService.updateBalances(myNewTransferId, myNewTransfer, authToken);
			
					System.out.println("\nYou have transferred $" + String.format("%.2f", formattedAmount) + " to " + appService.getUsernameById(Long.parseLong(idSendingTo), authToken) + ".");
					}
				} catch (Exception e) {
				System.out.println("\nInvalid entry. Please try again.\n");
				sendBucks();
			}
		}
	}	

	private void requestBucks() {
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
	
	public void formattedUserList(User[] userList){
	    for(User myUser : userList){
	        System.out.print("    " + myUser.getId() + "                 " + myUser.getUsername() + "\n");
	    }
	}
	
	public void formattedTransferList(Transfers[] transferList) throws AuthenticationServiceException{
	    String authToken = currentUser.getToken();
	    for(Transfers myTransfer : transferList){
	    		    	
	    		String numberOne = (" "+myTransfer.getTransferId()+"");
	       
	    		String numberTwo; 
	        	if (myTransfer.getAccountFrom().equals(appService.findAccountByUsername(currentUser.getUser().getUsername(), authToken))){
		        numberTwo = ("To: " + appService.findUsernameByAccount(myTransfer.getAccountTo(), authToken));
	        	} else {
	        	numberTwo = ("From: " + appService.findUsernameByAccount(myTransfer.getAccountFrom(), authToken));
	        	}
	        
	        	String numberThree = (String.format("$%.2f",myTransfer.getAmount()));
	        	
		    	System.out.printf("%-20s %-19s %11s\n", numberOne, numberTwo, numberThree);
	    }
   }
	
	public void formattedTransferDetails(Transfers transferDetails) throws AuthenticationServiceException{ 
		String authToken = currentUser.getToken();
		System.out.println("Id: " + transferDetails.getTransferId());
		System.out.println("From: " + appService.findUsernameByAccount(transferDetails.getAccountFrom(), authToken));
		System.out.println("To: " + appService.findUsernameByAccount(transferDetails.getAccountTo(), authToken));
		System.out.println("Type: " + toFromWords(transferDetails.getTransferTypeId()));
		System.out.println("Status: " + transferStatusWords(transferDetails.getTransferStatusId()));
		System.out.println("Amount: $" + String.format("%.2f",transferDetails.getAmount()));
	}
	
	public String transferStatusWords(Long transferStatusId) {
		String statusReturn = "";
		if (transferStatusId == 1){
			statusReturn = "Pending";
		}
		if (transferStatusId == 2) {
			statusReturn = "Approved";
		}
		if (transferStatusId == 3){
			statusReturn = "Rejected";
		}
		return statusReturn;
	}
	
	public String toFromWords(Long toFrom) {
		String wordsReturn = "";
		if (toFrom == 1){
			wordsReturn = "Request";
		}
		if (toFrom == 2) {
			wordsReturn = "Send";
		}
		return wordsReturn;
	}
	
	public Transfers createSendTransfer(Long accountFrom, Long accountTo, double amount) throws AuthenticationServiceException {
	    String authToken = currentUser.getToken();
		Transfers myTransfer = new Transfers();
		myTransfer.setTransferId(appService.getNextTransferId(authToken));
		myTransfer.setTransferTypeId(Long.parseLong("2"));
		myTransfer.setTransferStatusId(Long.parseLong("2"));
		myTransfer.setAccountFrom(accountFrom);
		myTransfer.setAccountTo(accountTo);
		myTransfer.setAmount(amount);
		return myTransfer;
	}
}