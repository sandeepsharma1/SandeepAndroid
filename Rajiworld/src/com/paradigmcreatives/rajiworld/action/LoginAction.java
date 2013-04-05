package com.paradigmcreatives.rajiworld.action;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.paradigmcreatives.rajiworld.entities.Answers;
import com.paradigmcreatives.rajiworld.entities.Login;
import com.paradigmcreatives.rajiworld.entities.QuestionsAnswers;
import com.paradigmcreatives.rajiworld.sql.UserService;
import com.paradigmcreatives.rajiworld.util.mailing.UserMailing;

@ManagedBean(name = "loginAction")
@ApplicationScoped
public class LoginAction {
	UserService userService = new UserService();
	private Login login = null;
	private QuestionsAnswers qa = null;
	private Answers ans;
	static HttpSession session;
	FacesContext context;
	private String username;
	private QuestionsAnswers selectedTitle;
	private Answers selectedAnswer;
	private Collection<QuestionsAnswers> searchResults;
	private Collection<Answers> allAnswers;
	static int activeid;
	static String answ;
	static String currentUser;

	public LoginAction(HttpSession session) {
		session = (HttpSession) context.getExternalContext().getSession(true);
	}

	public LoginAction() {
		login = new Login();
		qa = new QuestionsAnswers();
		ans = new Answers();
		searchResults = userService.getAllTitles();
		allAnswers = userService.getAllAnswers(activeid);
	}
	
	/*public String login(){
		username = login.getUsername();
		String password = login.getPassword();
		//UserService.session.setAttribute("username",username);
		String uname = userService.verifylogincredentials(username,password);
		if(uname.equals(username)){
		
		boolean verify = userService.veryActivationStatus(username);
		if (verify == true) {
			return "welcome";
		} else
			return "verifyemail";
		}else
		return "login";
	}
	*/

	public String login() {
		FacesContext context1 = FacesContext.getCurrentInstance();
		// username = login.getFirstName() +"."+login.getLastName();
		username = login.getUsername();
		String password = login.getPassword();
		// UserService.session.setAttribute("username",username);
		if (username != null || password != null) {
			UsernamePasswordToken token = new UsernamePasswordToken(username,
					password);
			System.out.println("username" + username);
			System.out.println("password" + password);

			try {
				Subject subject = SecurityUtils.getSubject();
				// token.setRememberMe(true);
				subject.login(token);
				currentUser = subject.getPrincipal().toString();
				System.out.println("current user...."+currentUser);
				//token.clear();

				boolean verify = userService.veryActivationStatus(username);
				if (verify == true) {
					return "welcome";
				} else
					return "verifyemail";
				// return "/welcome.jsf";
			} catch (UnknownAccountException ex) {
				// username provided was not found
				ex.printStackTrace();
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("username",
						new FacesMessage(ex.getMessage()));
				return "loginverification";

			} catch (IncorrectCredentialsException ex) {
				// password provided did not match password found in database
				// for the username provided
				ex.printStackTrace();
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("username",
						new FacesMessage(ex.getMessage()));
				return "loginverification";
			} catch (Exception ex) {
				ex.printStackTrace();
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("username",
						new FacesMessage(ex.getMessage()));
				return "loginverification";
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("username", new FacesMessage(
					"Invalid UserName and Password"));
			return "loginverification";
		}
	}


	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	/**
	 * @return Login  class object 
	 */
	public Login getLogin() {
		if (this.login == null) {
			login = new Login();
		}

		return login;
	}

	/**
	 * @param ans
	 *            the ans to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}

	/**
	 * @return Answers class object
	 */
	public Answers getAns() {
		if (this.ans == null) {
			ans = new Answers();
		}
		return ans;
	}

	/**
	 * @param ans
	 *            the ans to set
	 */
	public void setAns(Answers ans) {
		this.ans = ans;
	}

	/**
	 * @return the selected title i.e  selectedTitle
	 */
	public QuestionsAnswers getSelectedTitle() {
		return selectedTitle;
	}

	/**
	 * @param selectedTitle
	 *            the selectedTitle to set
	 */
	public void setSelectedTitle(QuestionsAnswers selectedTitle) {
		this.selectedTitle = selectedTitle;
	}

	
	public Answers getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(Answers selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	public String register() {
		FacesContext context = FacesContext.getCurrentInstance();
		String uname = login.getUsername();

		int uid = login.getUserId();

		/*
		 * UserService.session.setAttribute("username", uname);
		 * UserService.session.setAttribute("userId", uid);
		 */

		int result = userService.registerUser(login);
		if (result != 0) {
			context.addMessage("userID", new FacesMessage(
					"Added New User Successfully"));
		} else {
			context.addMessage("userID", new FacesMessage(
					"Error in Registering New User"));
		}

		UserMailing usermail = new UserMailing();
		String fromMailID = "rajeswari.android@gmail.com";
		String fromUserName = login.getUsername();
		System.out.println("fromUserName:" + fromUserName);

		String toMailID = login.getEmailAddress();
		System.out.println("toMailID:" + toMailID);
		boolean res = usermail.send(fromMailID, fromUserName, toMailID);
		boolean verify = userService.verifyActivation(toMailID);
		if (verify == true) {
			return "welcome";
		} else
			return "verifyemail";
		// return "welcome";
	}
 
	/**
	 * @return allAnswers
	 */
	public Collection<Answers> getAllAnswers() {
		
		allAnswers = userService.getAllAnswers(activeid);
		/*String ans="";
		allAnswers = userService.getAllAnswers(activeid);
        for(int i=0;i<allAnswers.size();i++){
        	ans = ans +"\n"+ allAnswers.get(i)+"\n";
        }
        System.out.println("ans"+ans);*/
		return allAnswers;
	}

	/**
	 * @param allAnswers
	 *            the allAnswers to set
	 */
	public void setAllAnswers(Collection<Answers> allAnswers) {
		System.out.println("in all answers method " + activeid);
		this.allAnswers = userService.getAllAnswers(activeid);
	}

	/**
	 * @return searchResults
	 */
	public Collection<QuestionsAnswers> getSearchResults() {
		return searchResults;
	}

	/**
	 * @param searchResults
	 *            the searchResults to set
	 */
	public void setSearchResults(Collection<QuestionsAnswers> searchResults) {
		this.searchResults = userService.getAllTitles();
	}

	
	public String submitquestion() {
		FacesContext context = FacesContext.getCurrentInstance();

		
		System.out.println("questioned user........"+currentUser);
		
		
		String result = userService.postquestion(qa, login,currentUser);
		return "welcome.jsf";
	}

	/**
	 * @return QuestinsAnswers class object i.e qa
	 */

	public QuestionsAnswers getQa() {
		if (this.qa == null) {
			qa = new QuestionsAnswers();
		}
		return qa;
	}

	/**
	 * @param qa
	 *            the qa to set
	 */
	public void setQa(QuestionsAnswers qa) {
		this.qa = qa;
	}

	public void fetchselectedqa() {
		activeid = selectedTitle.getQid();
		System.out.println("active Title ..." + activeid);
	}

	public void submitAnswer() {
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println(" Title from dialog ..." + qa.getTitle());
		String result = userService.postAnswer(ans, activeid,currentUser);

	}
	public void edit(){
		
     answ = selectedAnswer.getAnswer();
    int ansid = selectedAnswer.getAnsid();
    System.out.println("answerid..."+ansid);
     System.out.println("hiiii............answer:"+answ);
	
	}
	public void modify(){
		System.out.println("Rajiiiiiiiiiiiii");
	}
	/*public void editAnswer(){
		
 String ansuser = userService.checkuser(activeid);
 String auser= selectedAnswer.getAusername();
 if(ansuser.equals(auser)){
	 Answers  ans = new Answers();
	userService.editAnswer(ans,answ);
 }
 
 
	}*/
	
}
