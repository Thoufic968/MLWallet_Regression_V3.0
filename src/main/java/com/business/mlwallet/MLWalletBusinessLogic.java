package com.business.mlwallet;


import com.driverInstance.CommandBase;
import com.driverInstance.DriverManager;
import com.mlwallet.pages.*;
import com.propertyfilereader.PropertyFileReader;
import com.utility.ExtentReporter;
import com.utility.LoggingUtils;
import com.utility.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.asserts.SoftAssert;

import java.util.List;



public class MLWalletBusinessLogic {

	static LoggingUtils logger = new LoggingUtils();
	private int timeout;
	private int retryCount;

	public static SoftAssert softAssert = new SoftAssert();

	public static PropertyFileReader prop = new PropertyFileReader(".\\properties\\testdata.properties");

	public MLWalletBusinessLogic(String Application, String deviceName, String portno) throws InterruptedException {
		new CommandBase(Application, deviceName, portno);
		init();
	}

	public void init() {
		PropertyFileReader handler = new PropertyFileReader("properties/Execution.properties");
		setTimeout(Integer.parseInt(handler.getproperty("TIMEOUT")));
		setRetryCount(Integer.parseInt(handler.getproperty("RETRY_COUNT")));
		logger.info("Loaded the following properties" + " TimeOut :" + getTimeout() + " RetryCount :" + getRetryCount());
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void tearDown() {
		softAssert.assertAll();
		logger.info("Session ID: " + ((RemoteWebDriver) DriverManager.getAppiumDriver()).getSessionId());
		ExtentReporter.extentLogger("", "Session ID: " + ((RemoteWebDriver) DriverManager.getAppiumDriver()).getSessionId());
		logger.info("Session is quit");
		ExtentReporter.extentLogger("", "Session is quit");

		Utilities.setScreenshotSource();
		DriverManager.getAppiumDriver().quit();
	}

	//================================ LOG IN==============================================//
	public void mlWalletLogin(String sTier) throws Exception {
		Utilities.explicitWaitVisible(MLWalletLoginPage.objMobileNumberTextField, 10);
		Utilities.click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
		Utilities.type(MLWalletLoginPage.objMobileNumberTextField, sTier, "Mobile Number Text Field");
		Utilities.click(MLWalletLoginPage.objLoginBtn, "Login Button");
		enterOTP(prop.getproperty("Valid_OTP"));
		Utilities.explicitWaitVisible(MLWalletLoginPage.objAvailableBalance, 10);
		if (Utilities.verifyElementPresent(MLWalletLoginPage.objAvailableBalance, Utilities.getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("Application Logged In Successfully");
		} else {
			logger.info("Application not get Logged In Successfully");
		}
	}

	//===================================LOG OUT=============================================================//
	public void mlWalletLogout() throws Exception {
		if (Utilities.verifyElementPresent(MLWalletLogOutPage.objHamburgerMenu, "Hamburger Menu")) {
			Utilities.click(MLWalletLogOutPage.objHamburgerMenu, "Hamburger Menu");
			Utilities.click(MLWalletLogOutPage.objLogoutBtn, Utilities.getTextVal(MLWalletLogOutPage.objLogoutBtn, "Button"));
			Thread.sleep(2000);
			Utilities.click(MLWalletLogOutPage.objLogoutBtn, Utilities.getTextVal(MLWalletLogOutPage.objLogoutBtn, "Button"));
			Utilities.click(MLWalletLogOutPage.objChangeNumber, Utilities.getTextVal(MLWalletLogOutPage.objChangeNumber, "Link"));
		}
		if (Utilities.verifyElementPresent(MLWalletLoginPage.objLoginBtn, Utilities.getTextVal(MLWalletLoginPage.objLoginBtn, "Link"))) {
			logger.info("Application Logged Out Successfully");
		} else {
			logger.info("Application not get Logged Out Successfully");
		}

	}
//================================== Enter OTP ===================================================//

	public void enterOTP(String OTP) throws Exception {
		Utilities.explicitWaitVisible(MLWalletLoginPage.objOneTimePin, 5);
		Utilities.verifyElementPresent(MLWalletLoginPage.objOneTimePin, Utilities.getTextVal(MLWalletLoginPage.objOneTimePin, "Page"));
		Utilities.verifyElementPresent(MLWalletLoginPage.objOtpTextField, "OTP text Field");
		Thread.sleep(3000);
		Utilities.type(MLWalletLoginPage.objOtpTextField, OTP, "OTP Text Field");
	}

	public void backArrowBtn(int nNumber) throws Exception {
		for (int i = 1; i <= nNumber; i++) {
			Utilities.click(SendTransferPage.objBackArrow, "Back Arrow Button");
			Thread.sleep(2000);
		}
	}


	public void enterMLPin () throws Exception {
		Utilities.explicitWaitVisible(MLWalletLoginPage.objMLPin,5);
		Utilities.verifyElementPresent(MLWalletLoginPage.objMLPin,Utilities.getTextVal(MLWalletLoginPage.objMLPin,"Page"));
		Thread.sleep(3000);
		Utilities.click(MLWalletLoginPage.objOneBtn,Utilities.getTextVal(MLWalletLoginPage.objOneBtn,"Button"));
		Utilities.click(MLWalletLoginPage.objOneBtn,Utilities.getTextVal(MLWalletLoginPage.objOneBtn,"Button"));
		Utilities.click(MLWalletLoginPage.objOneBtn,Utilities.getTextVal(MLWalletLoginPage.objOneBtn,"Button"));
		Utilities.click(MLWalletLoginPage.objOneBtn,Utilities.getTextVal(MLWalletLoginPage.objOneBtn,"Button"));
	}

	public void enableLocation_PopUp() throws Exception {
		String loc = Utilities.getText(MLWalletLoginPage.objLocationPopup);
		if (loc.contains("Allow")) {
			logger.info(loc + " Pop Up is Displayed");
			ExtentReporter.extentLoggerPass("pop up", loc + " Pop Up is Displayed");
			Utilities.click(MLWalletCashOutPage.objLocationPermission, "Allow Button");
		} else {
			logger.info(" Location Pop Up is not Displayed");
			ExtentReporter.extentLoggerPass("pop up", "Location Pop Up is not Displayed");
		}
	}


//========================================= LOGIN SCENARIOS======================================//

	public void LogInScenarioWithValidMobNumber_Lgn_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With Valid Mobile Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		if(Utilities.verifyElementPresent(MLWalletLoginPage.objAvailableBalance, Utilities.getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("Lgn_TC_01, Logged In Successfully and redirected to Dashboard");
			ExtentReporter.extentLoggerPass("Lgn_TC_01", "Lgn_TC_01, Logged In Successfully and redirected to Dashboard");
			System.out.println("-----------------------------------------------------------");

		}
	}

	public void LogInScenarioWithInvalidMobNumber_Lgn_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With Invalid Mobile Number");
		Utilities.explicitWaitVisibility(MLWalletLoginPage.objMobileNumberTextField, 10);
		Utilities.click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
		Utilities.type(MLWalletLoginPage.objMobileNumberTextField, prop.getproperty("Invalid_MobileNumber"), "Mobile Number Text Field");
		Utilities.click(MLWalletLoginPage.objLoginBtn, "Login Button");
		if (Utilities.verifyElementPresent(MLWalletLoginPage.objInvalidMobNumberTxt, Utilities.getTextVal(MLWalletLoginPage.objInvalidMobNumberTxt, "Error Message"))) {
			logger.info("Lgn_TC_02, Mobile number is Invalid Error Message is Displayed");
			ExtentReporter.extentLoggerPass("Lgn_TC_02", "Lgn_TC_02, Mobile number is Invalid Error Message is Displayed");
			Utilities.setScreenshotSource();
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void LogInScenarioWithValidOTP() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With Valid OTP");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		if (Utilities.verifyElementPresent(MLWalletLoginPage.objAvailableBalance, Utilities.getTextVal(MLWalletLoginPage.objAvailableBalance, "Text"))) {
			logger.info("Lgn_TC_03, Logged In Successfully and redirected to Dashboard");
			ExtentReporter.extentLoggerPass("Lgn_TC_03", "Lgn_TC_03, Logged In Successfully and redirected to Dashboard");
			Utilities.setScreenshotSource();
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void LogInScenarioWithInValidOTP() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With InValid OTP");
		Utilities.explicitWaitVisibility(MLWalletLoginPage.objMobileNumberTextField, 10);
		Utilities.click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
		Utilities.type(MLWalletLoginPage.objMobileNumberTextField, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
		Utilities.click(MLWalletLoginPage.objLoginBtn, "Login Button");
		Utilities.type(MLWalletLoginPage.objOtpTextField, prop.getproperty("InValid_OTP"), "OTP Text Field");
	}


//========================================CASH IN VIA BRANCH===============================================//
//======================================= Generalized methods =============================================//


	public void enterBankDetails(String sAccountNumber) throws Exception {
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objBankInformation, Utilities.getTextVal(MLWalletCashOutPage.objBankInformation, "Button"))) {
			Utilities.type(MLWalletCashOutPage.objAccountNumberField, sAccountNumber, "Account Number Field");
			Utilities.type(MLWalletCashOutPage.objFirstname, prop.getproperty("First_Name"), "Account Holder First Name");
			Utilities.type(MLWalletCashOutPage.objMiddleName, prop.getproperty("Middle_Name"), "Account Holder Middle Name");
			Utilities.click(MLWalletCashOutPage.objCheckBox, "Check Box");
			Utilities.type(MLWalletCashOutPage.objLastName, prop.getproperty("Last_Name"), "Account Holder Last Name");
			Utilities.Swipe("UP", 1);
			Utilities.type(MLWalletCashOutPage.objEmailAddress, prop.getproperty("Email"), "Account Holder Email Address");
			Utilities.click(MLWalletCashOutPage.objConfirmBtn, Utilities.getTextVal(MLWalletCashOutPage.objConfirmBtn, "Button"));
		}

	}

	public void enterAmountMLBranch(String nAmount) throws Exception {
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyMLBranch, Utilities.getTextVal(MLWalletCashOutPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyMLBranch, Utilities.getTextVal(MLWalletCashOutPage.objToAnyMLBranch, "Button"));
			Utilities.verifyElementPresent(MLWalletCashOutPage.objCashOutToBranch,Utilities.getTextVal(MLWalletCashOutPage.objCashOutToBranch,"Page"));
			Utilities.type(MLWalletCashOutPage.objAmountField, nAmount, "Amount to Send");
			Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			Utilities.click(MLWalletCashOutPage.objContinueBtn, Utilities.getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
			Thread.sleep(3000);
		}
	}

//===================================================================================================================//
	public void cashOutWithdrawBank(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button")) {
			Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
			if (Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
				Utilities.click(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
				Utilities.click(MLWalletCashOutPage.BogusBank, Utilities.getTextVal(MLWalletCashOutPage.BogusBank, "Bank"));
				enterBankDetails(prop.getproperty("AccountNumber"));
				Thread.sleep(3000);
				Utilities.type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
				Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
				Utilities.click(MLWalletCashOutPage.objContinueBtn, Utilities.getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
				Utilities.Swipe("UP", 2);
				Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
				enableLocation_PopUp();
				enterOTP(prop.getproperty("Valid_OTP"));
				if (Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionReceipt, Utilities.getTextVal(MLWalletCashOutPage.objTransactionReceipt, "Text"))) {
					Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionSuccessMessage, Utilities.getTextVal(MLWalletCashOutPage.objTransactionSuccessMessage, "Message"));
					String sTransactionSuccess = Utilities.getText(MLWalletCashOutPage.objTransactionSuccessMessage);
					Utilities.assertionValidation(sTransactionSuccess, "Transaction Successful");
					Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionNo, Utilities.getTextVal(MLWalletCashOutPage.objTransactionNo, "Transaction Number"));
					String sTransactionNumber = Utilities.getText(MLWalletCashOutPage.objTransactionNo);
					System.out.println(sTransactionNumber);
					Utilities.Swipe("UP", 2);
					Utilities.click(MLWalletCashOutPage.objBackToHomeBtn, Utilities.getTextVal(MLWalletCashOutPage.objBackToHomeBtn, "Button"));
					Thread.sleep(3000);
					Utilities.Swipe("DOWN", 2);
					Utilities.Swipe("UP", 1);
					Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
					Utilities.click(MLWalletHomePage.objCashOutButton, Utilities.getTextVal(MLWalletHomePage.objCashOutButton, "Text"));
					if (Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, Utilities.getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
						String sReferenceNumberInCashOut = Utilities.getText(MLWalletCashOutPage.objReferenceNumberInCashOut);
						System.out.println(sReferenceNumberInCashOut);
						Utilities.assertionValidation(sReferenceNumberInCashOut, sTransactionNumber);
						logger.info("WM_TC_01, Successfully Withdraw Money to Bank");
						ExtentReporter.extentLoggerPass("WM_TC_01", "WM_TC_01, Successfully Withdraw Money to Bank");
						Utilities.setScreenshotSource();
						System.out.println("-----------------------------------------------------------");
					}
				}
			}
		}
	}

	public void cashOutWithInvalidAccNumber() throws Exception {
		ExtentReporter.HeaderChildNode("cashOut With Invalid Account Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
			Utilities.click(MLWalletCashOutPage.BogusBank, Utilities.getTextVal(MLWalletCashOutPage.BogusBank, "Bank"));
				enterBankDetails(prop.getproperty("Invalid_AccountNumber"));
				Thread.sleep(3000);
				if (Utilities.verifyElementPresent(MLWalletCashOutPage.objAccInvalidErrorMsg, Utilities.getTextVal(MLWalletCashOutPage.objAccInvalidErrorMsg, "Text Message"))) {
					String sInvalidAccTxt = Utilities.getText(MLWalletCashOutPage.objAccInvalidErrorMsg);
					String ExpectedTxt = "Bank Account provided not valid. Please check the account details and try again.";
					Utilities.assertionValidation(sInvalidAccTxt, ExpectedTxt);
					logger.info("WM_TC_02, Bank Account provided not valid. Error Message is Validated");
					ExtentReporter.extentLoggerPass("WM_TC_02", "WM_TC_02, Bank Account provided not valid. Error Message is Validated");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
		}

	}




	public void cashOutWithdrawBankMaxAmount(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch Max Amount");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
			Utilities.click(MLWalletCashOutPage.BogusBank, Utilities.getTextVal(MLWalletCashOutPage.BogusBank, "Bank"));
			enterBankDetails(prop.getproperty("AccountNumber"));
			Thread.sleep(3000);
			Utilities.type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
			Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			Thread.sleep(3000);
			String sDragonPopUpMsg = Utilities.getText(MLWalletCashOutPage.objDragonPayPopUpMsg);
			String sExpectedMsg = "Dragon Pay charges a fee of 35.00 pesos for this transaction. Do you wish to continue with your transaction?";
			Utilities.assertionValidation(sDragonPopUpMsg, sExpectedMsg);
			Utilities.click(MLWalletCashOutPage.objContinueBtn, Utilities.getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
			Utilities.Swipe("UP", 2);
			Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			Thread.sleep(5000);
			String sErrorMsg = Utilities.getText(MLWalletCashOutPage.objBankMaxLimitTxt);
			String sExpectedErrorMsg = "The maximum Bank Cash-out per transaction set for your verification level is P50,000.00. Please try again.";
			Utilities.assertionValidation(sErrorMsg, sExpectedErrorMsg);
			logger.info("WM_TC_03, The Maximum Bank Cash-out per transaction Msg is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_03", "WM_TC_03, The Maximum Bank Cash-out per transaction Msg is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}



	public void cashOutWithdrawMinTransactionLimit(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch Max Amount");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
			Utilities.click(MLWalletCashOutPage.BogusBank, Utilities.getTextVal(MLWalletCashOutPage.BogusBank, "Bank"));
			enterBankDetails(prop.getproperty("AccountNumber"));
			Utilities.explicitWaitVisible(MLWalletCashOutPage.objAmountField,5);
			Utilities.type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
			Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			Thread.sleep(5000);
			String sMinimumTransactionErrorMsg = Utilities.getText(MLWalletCashOutPage.objMinimumTransactionErrorMsg);
			String sExpectedMsg = "The supplied amount is less than the required minimum transaction limit";
			Utilities.assertionValidation(sMinimumTransactionErrorMsg, sExpectedMsg);
			logger.info("WM_TC_04, The supplied amount is less than the required minimum transaction limit Error Msg is validated");
			ExtentReporter.extentLoggerPass("WM_TC_04", "WM_TC_04, The supplied amount is less than the required minimum transaction limit Error Msg is validated");
			Utilities.setScreenshotSource();
			System.out.println("-----------------------------------------------------------");
		}

	}


	public void cashOutWithdrawBranch() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objCashOutToBranch, Utilities.getTextVal(MLWalletCashOutPage.objCashOutToBranch, "Page"))) {
			Utilities.verifyElementPresent(MLWalletCashOutPage.objCreatedDate, Utilities.getTextVal(MLWalletCashOutPage.objCreatedDate, "Date"));
			Utilities.verifyElementPresent(MLWalletCashOutPage.objReferenceNumber, Utilities.getTextVal(MLWalletCashOutPage.objReferenceNumber, "Reference Number"));
			String nReference = Utilities.getText(MLWalletCashOutPage.objReferenceNumber);
			System.out.println(nReference);
			String sReferenceNumber = nReference.substring(5, 16);
			System.out.println(sReferenceNumber);
			Utilities.click(MLWalletCashOutPage.objBackToHomeBtn, Utilities.getTextVal(MLWalletCashOutPage.objBackToHomeBtn, "Button"));
			Utilities.Swipe("DOWN", 2);
			Utilities.Swipe("UP", 1);
			Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			Utilities.click(MLWalletHomePage.objCashOutButton, Utilities.getTextVal(MLWalletHomePage.objCashOutButton, "Text"));
			if (Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, Utilities.getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
				String sReferenceNumberInCashOut = Utilities.getText(MLWalletCashOutPage.objReferenceNumberInCashOut);
				System.out.println(sReferenceNumberInCashOut);
				Utilities.assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
				logger.info("Reference Number is matching with recent Transaction");
				logger.info("WM_TC_05, Successfully Withdraw Money to ML Branch");
				ExtentReporter.extentLoggerPass("WM_TC_05", "WM_TC_05, Successfully Withdraw Money to ML Branch");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
	}
	public void cashOutMaxLimit() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("100000");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objMaxLimitTxt, Utilities.getTextVal(MLWalletCashOutPage.objMaxLimitTxt, "Text Message"))) {
			String sMaxLimitTxt = Utilities.getText(MLWalletCashOutPage.objMaxLimitTxt);
			String ExpectedTxt = "The maximum Branch Cash-out per day set for your verification level is P40,000.00. Please try again.";
			Utilities.assertionValidation(sMaxLimitTxt, ExpectedTxt);
			logger.info("WM_TC_06, The supplied amount us less than the required minimum transaction limit. Error Message is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_06", "WM_TC_06, The supplied amount us less than the required minimum transaction limit. Error Message is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashOutInsufficientBalance() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Insufficient Balance");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("35000");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objInsufficientBalance, Utilities.getTextVal(MLWalletCashOutPage.objInsufficientBalance, "Text Message"))) {
			String sInsufficientBalancePopupTxt = Utilities.getText(MLWalletCashOutPage.objInsufficientBalance);
			String ExpectedTxt = "There is insufficient balance to proceed with this transaction. Please try again.";
			Utilities.assertionValidation(sInsufficientBalancePopupTxt, ExpectedTxt);
			logger.info("WM_TC_07, Insufficient Balance pop up validated");
			ExtentReporter.extentLoggerPass("WM_TC_07", "WM_TC_07, Insufficient Balance pop up validated");
			System.out.println("-----------------------------------------------------------");
		}

	}
	public void cashOutBuyerTierLevelAcc() throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Buyer_Tier"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		enterAmountMLBranch("100");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objMaxLimitTxt, Utilities.getTextVal(MLWalletCashOutPage.objMaxLimitTxt, "Text Message"))) {
			String sErrorMessage = Utilities.getText(MLWalletCashOutPage.objMaxLimitTxt);
			String ExpectedTxt = "Branch Cash-out is not allowed for customers at this verification level. Please upgrade your account to use this service.";
			Utilities.assertionValidation(sErrorMessage, ExpectedTxt);
			logger.info("WM_TC_08, Branch Cash-out is not allowed for customers at this verification level. Error Message is Validated");
			ExtentReporter.extentLoggerPass("WM_TC_08", "WM_TC_08, Branch Cash-out is not allowed for customers at this verification level. Error Message is Validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

//================================ Send/Transfer To any ML Branch ============================================//
//=============================== General methods For send transfer ============================================//

	public void enterMLBranchDetails() throws Exception {
		Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala,5);
		if (Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"))) {
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
		}
	}

	public void enterAmountToKwartaPadala(String nAmount) throws Exception {
		Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
		Utilities.type(SendTransferPage.objAmountTxtField, nAmount, "Amount text Field");
		Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSelectPaymentMethod, Utilities.getTextVal(SendTransferPage.objSelectPaymentMethod, "Page"));
		Thread.sleep(3000);
		Utilities.click(SendTransferPage.objMLWalletBalance, Utilities.getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails, Utilities.getTextVal(SendTransferPage.objConfirmDetails, "Page"));
		Utilities.click(SendTransferPage.objConfirmBtn, Utilities.getTextVal(SendTransferPage.objConfirmBtn, "Button"));
	}

	public void selectSavedRecipient() throws Exception {
		Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
		if (Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"))) {
			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSavedRecipients, 5);
			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Page"));
			Utilities.type(SendTransferPage.objSearchRecipient, prop.getproperty("Last_Name"), "Search Recipient Text Field");
			Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			Utilities.click(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			Thread.sleep(3000);
		}
	}

	public void addRecipient() throws Exception {
		if (Utilities.verifyElementPresent(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Button"))) {
			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Button"));
			Utilities.click(SendTransferPage.objAddRecipient, Utilities.getTextVal(SendTransferPage.objAddRecipient, "Button"));
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objNickName, prop.getproperty("Nick_Name"), "Nick Name Text Field");
			Utilities.click(SendTransferPage.ObjSaveRecipient, Utilities.getTextVal(SendTransferPage.ObjSaveRecipient, "Button"));
		}
	}




//===============================================================================================================//
	public void sendMoneyToMLBranch_STB_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			enterMLBranchDetails();
			enterAmountToKwartaPadala("100");
			enableLocation_PopUp();
			enterOTP(prop.getproperty("Valid_OTP"));
			if (Utilities.verifyElementPresent(SendTransferPage.objSendMoneySuccessful, Utilities.getTextVal(SendTransferPage.objSendMoneySuccessful, "Message"))) {
				Utilities.verifyElementPresent(SendTransferPage.objPHPAmount, Utilities.getTextVal(SendTransferPage.objPHPAmount, "Amount"));
				Utilities.verifyElementPresent(SendTransferPage.objDate, Utilities.getTextVal(SendTransferPage.objDate, "Date"));
				Utilities.verifyElementPresent(SendTransferPage.objReferenceNumber, Utilities.getTextVal(SendTransferPage.objReferenceNumber, "Reference Number"));
				String sReference = Utilities.getText(SendTransferPage.objReferenceNumber);
				System.out.println(sReference);
				String sReferenceNumber = sReference.substring(9, 20);
				System.out.println(sReferenceNumber);
				Utilities.Swipe("UP", 2);
				Utilities.click(SendTransferPage.objBackToHomeBtn, Utilities.getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
				Thread.sleep(3000);
				Utilities.Swipe("DOWN", 2);
				Utilities.Swipe("UP", 1);
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.click(MLWalletHomePage.objKwartaPadala, Utilities.getTextVal(MLWalletHomePage.objKwartaPadala, "Text"));
				if (Utilities.verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, Utilities.getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
					String sReferenceNumberInCashOut = Utilities.getText(SendTransferPage.objReferenceNumberInTransactionDetails);
					System.out.println(sReferenceNumberInCashOut);
					Utilities.assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
					logger.info("STB_TC_01, Successfully sent Amount to ML Branch and Transaction Details is validated");
					ExtentReporter.extentLoggerPass("STB_TC_01", "STB_TC_01, Successfully sent Amount to ML Branch and Transaction Details is validated");
					System.out.println("-----------------------------------------------------------");
				}
			}
		}
	}

	public void sendMoneyRequiredDetails_STB_TC_08() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Invalid Bank Details");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objFirstNameRequiredMsg, Utilities.getTextVal(SendTransferPage.objFirstNameRequiredMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objFirstNameRequiredMsg);
				String sExpectedMsg = "First name is required";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.hideKeyboard();
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objMiddleNameRequiredMsg, Utilities.getTextVal(SendTransferPage.objMiddleNameRequiredMsg, "Error Message"))) {
				String sMiddleNameRequiredMsg = Utilities.getText(SendTransferPage.objMiddleNameRequiredMsg);
				String sExpectedMsg = "Middle name is required";
				Utilities.assertionValidation(sMiddleNameRequiredMsg, sExpectedMsg);
			}
			Thread.sleep(3000);
			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objLastNameRequiredMsg, Utilities.getTextVal(SendTransferPage.objLastNameRequiredMsg, "Error Message"))) {
				String sLastNameRequiredMsg = Utilities.getText(SendTransferPage.objLastNameRequiredMsg);
				String sExpectedMsg = "Last name is required";
				Utilities.assertionValidation(sLastNameRequiredMsg, sExpectedMsg);
			}
			Thread.sleep(3000);
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objMobileNumberRequiredMsg, Utilities.getTextVal(SendTransferPage.objMobileNumberRequiredMsg, "Error Message"))) {
				String sMobileNumberRequiredMsg = Utilities.getText(SendTransferPage.objMobileNumberRequiredMsg);
				String sExpectedMsg = "Mobile Number is required";
				Utilities.assertionValidation(sMobileNumberRequiredMsg, sExpectedMsg);
			}
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Last Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			if (Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "page"))) {
				logger.info("STB_TC_08, Prompt msg for Receiver's Details required is validated");
				ExtentReporter.extentLoggerPass("STB_TC_08", "STB_TC_08, Prompt msg for Receiver's Details required is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void sendMoneyInvalidDetails_STB_TC_07() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Invalid Bank Details");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("Invalid_First_Name"), "First Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objFirstNameErrorMsg, Utilities.getTextVal(SendTransferPage.objFirstNameErrorMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objFirstNameErrorMsg);
				String sExpectedMsg = "First name must only contain letters and spaces";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");


			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Invalid_Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objMiddleNameErrorMsg, Utilities.getTextVal(SendTransferPage.objMiddleNameErrorMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objMiddleNameErrorMsg);
				String sExpectedMsg = "Middle name must only contain letters and spaces";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.Swipe("UP", 1);

			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Invalid_Last_Name"), "Last Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objLastNameErrorMsg, Utilities.getTextVal(SendTransferPage.objLastNameErrorMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objLastNameErrorMsg);
				String sExpectedMsg = "Last name must only contain letters and spaces";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");


			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Invalid_MobileNumber"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objMobileNumberErrorMsg, Utilities.getTextVal(SendTransferPage.objMobileNumberErrorMsg, "Error Message"))) {
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objMobileNumberErrorMsg);
				String sExpectedMsg = "Mobile number is invalid";
				Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			}
			Utilities.clearField(SendTransferPage.objMobileNumber, "Mobile Number Text Field");
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));

			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			if (Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "page"))) {
				logger.info("STB_TC_07, Prompt msg for Receiver's Details Invalid is validated");
				ExtentReporter.extentLoggerPass("STB_TC_07", "STB_TC_07, Prompt msg for Receiver's Details Invalid is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyAddRecipient_STB_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
//			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Button"));
//			Utilities.click(SendTransferPage.objAddRecipient, Utilities.getTextVal(SendTransferPage.objAddRecipient, "Button"));
//			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
//			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
//			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
//			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
//			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Last Name Text Field");
//			Utilities.type(SendTransferPage.objNickName, prop.getproperty("Nick_Name"), "Nick Name Text Field");
//			Utilities.click(SendTransferPage.ObjSaveRecipient, Utilities.getTextVal(SendTransferPage.ObjSaveRecipient, "Button"));
			addRecipient();
			Utilities.type(SendTransferPage.objSearchRecipient, prop.getproperty("Last_Name"), "Search Recipient Text Field");
			if (Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"))) {
				logger.info("STB_TC_03, The Added Recipient is displayed in Saved Recipient Page");
				ExtentReporter.extentLoggerPass("STB_TC_03", "STB_TC_03, The Added Recipient is displayed in Saved Recipient Page");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void sendMoneyToSavedRecipient_STB_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
//			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
//			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
//			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Button"));
//			Utilities.explicitWaitVisible(SendTransferPage.objSavedRecipients, 5);
//			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Page"));
//			Utilities.type(SendTransferPage.objSearchRecipient, prop.getproperty("Last_Name"), "Search Recipient Text Field");
//			Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
//			Utilities.click(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			selectSavedRecipient();
			Thread.sleep(3000);
			Utilities.click(SendTransferPage.objSelectRecipient, Utilities.getTextVal(SendTransferPage.objSelectRecipient, "Button"));
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.Swipe("UP", 1);
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Thread.sleep(5000);
			enterAmountToKwartaPadala("100");
			enableLocation_PopUp();
			enterOTP(prop.getproperty("Valid_OTP"));
			if (Utilities.verifyElementPresent(SendTransferPage.objSendMoneySuccessful, Utilities.getTextVal(SendTransferPage.objSendMoneySuccessful, "Message"))) {
				Utilities.verifyElementPresent(SendTransferPage.objPHPAmount, Utilities.getTextVal(SendTransferPage.objPHPAmount, "Amount"));
				Utilities.verifyElementPresent(SendTransferPage.objDate, Utilities.getTextVal(SendTransferPage.objDate, "Date"));
				Utilities.verifyElementPresent(SendTransferPage.objReferenceNumber, Utilities.getTextVal(SendTransferPage.objReferenceNumber, "Reference Number"));
				String sReference = Utilities.getText(SendTransferPage.objReferenceNumber);
				System.out.println(sReference);
				String sReferenceNumber = sReference.substring(9, 20);
				System.out.println(sReferenceNumber);
				Utilities.Swipe("UP", 2);
				Utilities.click(SendTransferPage.objBackToHomeBtn, Utilities.getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
				Thread.sleep(3000);
				Utilities.Swipe("DOWN", 2);
				Utilities.Swipe("UP", 1);
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.click(MLWalletHomePage.objKwartaPadala, Utilities.getTextVal(MLWalletHomePage.objKwartaPadala, "Text"));
				if (Utilities.verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, Utilities.getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
					String sReferenceNumberInCashOut = Utilities.getText(SendTransferPage.objReferenceNumberInTransactionDetails);
					System.out.println(sReferenceNumberInCashOut);
					Utilities.assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
					logger.info("STB_TC_02, Successfully sent Amount to saved Recipient and Transaction Details is validated");
					ExtentReporter.extentLoggerPass("STB_TC_02", "STB_TC_02, Successfully sent Amount to saved Recipient and Transaction Details is validated");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
			}
		}
	}


	public void sendMoneyContactDuplicate_STB_TC_04() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Contact Duplicate");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
//			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Button"));
//			Utilities.click(SendTransferPage.objAddRecipient, Utilities.getTextVal(SendTransferPage.objAddRecipient, "Button"));
//			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
//			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
//			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
//			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
//			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
//			Utilities.type(SendTransferPage.objNickName, prop.getproperty("Nick_Name"), "Nick Name Text Field");
//			Utilities.click(SendTransferPage.ObjSaveRecipient, Utilities.getTextVal(SendTransferPage.ObjSaveRecipient, "Button"));
			addRecipient();
			if (Utilities.verifyElementPresent(SendTransferPage.objContactAlreadyExistMsg, Utilities.getTextVal(SendTransferPage.objContactAlreadyExistMsg, "Error Message"))) {
				String sContactDuplicatePopupMsg = Utilities.getText(SendTransferPage.objContactAlreadyExistMsg);
				String sExpectedPopupMsg = "Contact already exists.";
				Utilities.assertionValidation(sContactDuplicatePopupMsg, sExpectedPopupMsg);
				logger.info("STB_TC_04, Contact already exists popup message Validated");
				ExtentReporter.extentLoggerPass("STB_TC_04", "STB_TC_04, Contact already exists popup message Validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyEditRecipient_STB_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			selectSavedRecipient();
			Utilities.click(SendTransferPage.objEditRecipient, Utilities.getTextVal(SendTransferPage.objEditRecipient, "Button"));
			Utilities.type(SendTransferPage.objEditRecipientLastName, prop.getproperty("Edited_Last_name"), "Last Name Text Field");
			Utilities.click(SendTransferPage.ObjSaveRecipient, Utilities.getTextVal(SendTransferPage.ObjSaveRecipient, "Button"));
			Utilities.type(SendTransferPage.objSearchRecipient, prop.getproperty("Edited_Last_name"), "Search Recipient Text Field");
			if (Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"), prop.getproperty("First_Name")), "Recipient"))) {
				logger.info("STB_TC_06, Successfully edited the Saved Recipient");
				ExtentReporter.extentLoggerPass("STB_TC_06", "STB_TC_06, Successfully edited the Saved Recipient");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyDeleteRecipient_STB_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			selectSavedRecipient();
			Utilities.click(SendTransferPage.objDeleteRecipient, Utilities.getTextVal(SendTransferPage.objDeleteRecipient, "Button"));
			Utilities.verifyElementPresent(SendTransferPage.objPopupMsg, Utilities.getTextVal(SendTransferPage.objPopupMsg, "Pop Up message"));
			String sDeleteConfirmationPopup = Utilities.getText(SendTransferPage.objPopupMsg);
			String sExceptedMsg = "Are you sure you want to remove this saved recipient?";
			Utilities.assertionValidation(sDeleteConfirmationPopup, sExceptedMsg);
			Utilities.click(SendTransferPage.objRemoveBtn, Utilities.getTextVal(SendTransferPage.objRemoveBtn, "Button"));
			Utilities.clearField(SendTransferPage.objSearchRecipient, "Search Field");
			Thread.sleep(3000);
			if (Utilities.verifyElementNotPresent(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"), prop.getproperty("First_Name")), 5)) {
				logger.info("STB_TC_05, Saved Recipient from Saved Recipients page not got deleted Successfully");
			} else {
				logger.info("STB_TC_05, Saved Recipient from Saved Recipients page deleted Successfully");
				ExtentReporter.extentLoggerPass("STB_TC_05", "STB_TC_05, Saved Recipient from Saved Recipients page deleted Successfully");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}

		}

	}

	public void sendMoneyInvalidAmount_STB_TC_09(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			enterMLBranchDetails();
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objInvalidAmountMsg, Utilities.getTextVal(SendTransferPage.objInvalidAmountMsg, "Error Message"))) {
				String sInvalidAmountErrorMsg = Utilities.getText(SendTransferPage.objInvalidAmountMsg);
				String sExpectedErrorMsg = "The amount should not be less than 1";
				Utilities.assertionValidation(sInvalidAmountErrorMsg, sExpectedErrorMsg);
				logger.info("STB_TC_09, The amount should not be less than 1 - Error Message is validated");
				ExtentReporter.extentLoggerPass("STB_TC_09", "STB_TC_09, The amount should not be less than 1 - Error Message is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void sendMoneyInsufficientAmount_STB_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified_LowBalance"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			enterMLBranchDetails();
			enterAmountToKwartaPadala("35000");
			if (Utilities.verifyElementPresent(SendTransferPage.objInsufficientAmountMsg, Utilities.getTextVal(SendTransferPage.objInsufficientAmountMsg, "Error Message"))) {
				String sInsufficientBalanceErrorMsg = Utilities.getText(SendTransferPage.objInsufficientAmountMsg);
				String sExpectedErrorMsg = "There is insufficient balance to proceed with this transaction. Please try again.";
				Utilities.assertionValidation(sInsufficientBalanceErrorMsg, sExpectedErrorMsg);
				logger.info("STB_TC_10, Insufficient Balance - Error Message is validated");
				ExtentReporter.extentLoggerPass("STB_TC_10", "STB_TC_10, Insufficient Balance - Error Message is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyMaximumAmount_STB_TC_12() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			enterMLBranchDetails();
			enterAmountToKwartaPadala("100000");
			if (Utilities.verifyElementPresent(SendTransferPage.objMaxLimitErrorMsg, Utilities.getTextVal(SendTransferPage.objMaxLimitErrorMsg, "Error Message"))) {
				String sMaximumLimitErrorMsg = Utilities.getText(SendTransferPage.objMaxLimitErrorMsg);
				String sExpectedErrorMsg = "The maximum Send Money per transaction set for your verification level is P50,000.00. Please try again.";
				Utilities.assertionValidation(sMaximumLimitErrorMsg, sExpectedErrorMsg);
				logger.info("STB_TC_12, The maximum send money per transaction - Error Message is validated");
				ExtentReporter.extentLoggerPass("STB_TC_12", "STB_TC_12, The maximum send money per transaction - Error Message is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
	}




//===============================================Send/Transfer To a ML Wallet User=============================//
//========================== Generalized methods for Send/Transfer To a ML Wallet User========================//


	public void enterMobileNumberMLWallet(String nMobileNumber) throws Exception {
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.type(SendTransferPage.objMobileNumberField, nMobileNumber, "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
		}
	}

	public void enterAmountAndSendToMLWallet(String nAmount) throws Exception {
		Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
		if (Utilities.verifyElementPresent(SendTransferPage.objToMLWalletUser, Utilities.getTextVal(SendTransferPage.objToMLWalletUser, "Page"))) {
			Utilities.type(SendTransferPage.objAmountTxtField, nAmount, "Amount Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.click(SendTransferPage.objMLWalletBalance, Utilities.getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails, Utilities.getTextVal(SendTransferPage.objConfirmDetails, "Page"));
			Utilities.Swipe("UP", 1);
			Utilities.click(SendTransferPage.objSendPHPBtn, Utilities.getTextVal(SendTransferPage.objSendPHPBtn, "Button"));
		}
	}





//======================================================================================================================//
	public void sendToMLWalletUser_STW_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		enterMobileNumberMLWallet(prop.getproperty("Fully_verified"));
		enterAmountAndSendToMLWallet("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWallet, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWallet, "Message"))) {
			Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
			Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
			Utilities.verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, Utilities.getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
			String sReferenceNumber = Utilities.getText(SendTransferPage.objMLWalletReferenceNumber);
			System.out.println(sReferenceNumber);
			Utilities.Swipe("UP", 2);
			Utilities.click(SendTransferPage.objBackToHomeBtn, Utilities.getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
			Thread.sleep(3000);
			Utilities.Swipe("DOWN", 2);
			Utilities.Swipe("UP", 1);
			Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
			Utilities.verifyElementPresent(MLWalletHomePage.objWalletToWallet, Utilities.getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
			Utilities.click(MLWalletHomePage.objWalletToWallet, Utilities.getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
			if (Utilities.verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, Utilities.getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
				String sReferenceNumberInWalletToWallet = Utilities.getText(SendTransferPage.objReferenceNumberInTransactionDetails);
				System.out.println(sReferenceNumberInWalletToWallet);
				Utilities.assertionValidation(sReferenceNumberInWalletToWallet, sReferenceNumber);
				logger.info("STW_TC_01, Successfully Amount sent from Wallet to Wallet and Transaction Details is validated");
				ExtentReporter.extentLoggerPass("STW_TC_01", "STW_TC_01, Successfully Amount sent from Wallet to Wallet and Transaction Details is validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void sendMoneyAddToFavorites() throws Exception {
//		ExtentReporter.HeaderChildNode("Send Money Add To Favorites");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		enterMobileNumberMLWallet(prop.getproperty("Fully_verified"));
		enterAmountAndSendToMLWallet("10");
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
		if (Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWallet, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWallet, "Message"))) {
			Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
			Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
			Utilities.verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, Utilities.getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
			String sReferenceNumber = Utilities.getText(SendTransferPage.objMLWalletReferenceNumber);
			System.out.println(sReferenceNumber);
			Utilities.Swipe("UP", 1);
			Utilities.click(SendTransferPage.objSaveToMyFavorite, Utilities.getTextVal(SendTransferPage.objSaveToMyFavorite, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objAddedToFavoritesMsg, Utilities.getTextVal(SendTransferPage.objAddedToFavoritesMsg, "Message"))) {
				Utilities.click(SendTransferPage.objOkBtn, Utilities.getTextVal(SendTransferPage.objOkBtn, "Button"));
			}
		}
	}


	public void sendMoneyMLWalletToExistingReceiver_STW_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet To Existing Receiver");
		sendMoneyAddToFavorites();
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.verifyElementPresent(SendTransferPage.objSelectFavorite, Utilities.getTextVal(SendTransferPage.objSelectFavorite, "Text"));
			Utilities.click(SendTransferPage.objSelectFavorite, Utilities.getTextVal(SendTransferPage.objSelectFavorite, "Text"));
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			enterAmountAndSendToMLWallet("10");
			enterOTP(prop.getproperty("Valid_OTP"));
			if (Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWallet, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWallet, "Message"))) {
				Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
				Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
				Utilities.verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, Utilities.getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
				String sReferenceNumber = Utilities.getText(SendTransferPage.objMLWalletReferenceNumber);
				System.out.println(sReferenceNumber);
				Utilities.Swipe("UP", 2);
				Utilities.click(SendTransferPage.objBackToHomeBtn, Utilities.getTextVal(SendTransferPage.objBackToHomeBtn, "Button"));
				Thread.sleep(3000);
				Utilities.Swipe("DOWN", 2);
				Utilities.Swipe("UP", 1);
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.verifyElementPresent(MLWalletHomePage.objWalletToWallet, Utilities.getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
				Utilities.click(MLWalletHomePage.objWalletToWallet, Utilities.getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
				if (Utilities.verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, Utilities.getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
					String sReferenceNumberInWalletToWallet = Utilities.getText(SendTransferPage.objReferenceNumberInTransactionDetails);
					System.out.println(sReferenceNumberInWalletToWallet);
					Utilities.assertionValidation(sReferenceNumberInWalletToWallet, sReferenceNumber);
					logger.info("STW_TC_02, Successfully Amount sent from Wallet to Wallet to Recently added favorite and Transaction Details is validated");
					ExtentReporter.extentLoggerPass("STW_TC_02", "STW_TC_02, Successfully Amount sent from Wallet to Wallet to Recently added favorite and Transaction Details is validated");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
			}
		}
	}


	public void sendToMLWalletInvalidMobNumber() throws Exception {
		ExtentReporter.HeaderChildNode("Send To ML Wallet to Invalid Mobile Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		enterMobileNumberMLWallet(prop.getproperty("Invalid_MobileNumber"));
		Utilities.explicitWaitVisible(SendTransferPage.objMobileNumberErrorMsg,5);
		if (Utilities.verifyElementPresent(SendTransferPage.objMobileNumberErrorMsg, Utilities.getTextVal(SendTransferPage.objMobileNumberErrorMsg, "Error Message"))) {
			String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objMobileNumberErrorMsg);
			String sExpectedMsg = "Mobile number is invalid";
			Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			logger.info("STW_TC_03, Mobile number is invalid - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_03", "STW_TC_03, Mobile number is invalid - Error Message is validated");
			Utilities.setScreenshotSource();
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendToMLWalletUnRegisteredNumber() throws Exception {
		ExtentReporter.HeaderChildNode("Send To ML Wallet to Invalid Mobile Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		enterMobileNumberMLWallet(prop.getproperty("Unregistered_MobileNumber"));
		Utilities.explicitWaitVisible(SendTransferPage.objUnRegisteredMobNumber,10);
		if (Utilities.verifyElementPresent(SendTransferPage.objUnRegisteredMobNumber, Utilities.getTextVal(SendTransferPage.objUnRegisteredMobNumber, "Error Message"))) {
			String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objUnRegisteredMobNumber);
			String sExpectedMsg = "Receiver not Found!";
			Utilities.assertionValidation(sFirstNameErrorMsg, sExpectedMsg);
			logger.info("STW_TC_04, Receiver not Found - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_04", "STW_TC_04, Receiver not Found - Error Message is validated");
			Utilities.setScreenshotSource();
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendToMLWalletInvalidAmount(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		enterMobileNumberMLWallet(prop.getproperty("Fully_verified"));
		Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
		Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount Text Field");
		Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
		if (Utilities.verifyElementPresent(SendTransferPage.objInvalidAmountMsg, Utilities.getTextVal(SendTransferPage.objInvalidAmountMsg, "Error Message"))) {
			String sInvalidAmountErrorMsg = Utilities.getText(SendTransferPage.objInvalidAmountMsg);
			String sExpectedErrorMsg = "The amount should not be less than 1";
			Utilities.assertionValidation(sInvalidAmountErrorMsg, sExpectedErrorMsg);
			logger.info("STW_TC_05, The amount should not be less than 1 - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_05", "STW_TC_05, The amount should not be less than 1 - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendToMLWalletInsufficientAmount() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin("9999999997");
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		enterMobileNumberMLWallet(prop.getproperty("Branch_Verified"));
		enterAmountAndSendToMLWallet("35000");

		Utilities.explicitWaitVisible(SendTransferPage.objInsufficientAmountMsg,5);
		if (Utilities.verifyElementPresent(SendTransferPage.objInsufficientAmountMsg, Utilities.getTextVal(SendTransferPage.objInsufficientAmountMsg, "Error Message"))) {
			String sInsufficientBalanceErrorMsg = Utilities.getText(SendTransferPage.objInsufficientAmountMsg);
			String sExpectedErrorMsg = "There is insufficient balance to proceed with this transaction. Please try again.";
			Utilities.assertionValidation(sInsufficientBalanceErrorMsg, sExpectedErrorMsg);
			logger.info("STW_TC_06, Insufficient Balance - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_06", "STW_TC_06, Insufficient Balance - Error Message is validated");
			Utilities.setScreenshotSource();
			System.out.println("-----------------------------------------------------------");
		}
	}


	public void sendMoneyMLWalletMaximumAmount() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet Maximum Amount");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		enterMobileNumberMLWallet(prop.getproperty("Fully_verified"));
		enterAmountAndSendToMLWallet("100000");
		if (Utilities.verifyElementPresent(SendTransferPage.objMaxLimitErrorMsg, Utilities.getTextVal(SendTransferPage.objMaxLimitErrorMsg, "Error Message"))) {
			String sMaximumLimitErrorMsg = Utilities.getText(SendTransferPage.objMaxLimitErrorMsg);
			String sExpectedErrorMsg = "The maximum Send Money per transaction set for your verification level is P50,000.00. Please try again.";
			Utilities.assertionValidation(sMaximumLimitErrorMsg, sExpectedErrorMsg);
			logger.info("STW_TC_07, The maximum send money per transaction - Error Message is validated");
			ExtentReporter.extentLoggerPass("STW_TC_07", "STW_TC_07, The maximum send money per transaction - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}



	public void sendMoneyDeleteFromFavorites() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Delete From Favorites");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.click(SendTransferPage.objViewAllBtn, Utilities.getTextVal(SendTransferPage.objViewAllBtn, "Text"));
			Utilities.click(SendTransferPage.objEllipsisBtn, "Ellipsis Button");
			Utilities.click(SendTransferPage.objDeleteBtn, Utilities.getTextVal(SendTransferPage.objDeleteBtn, "Button"));
			Utilities.click(SendTransferPage.objConfirmBtn, Utilities.getTextVal(SendTransferPage.objConfirmBtn, "Button"));
			if (Utilities.verifyElementPresent(SendTransferPage.objFavRemovedMsg, Utilities.getTextVal(SendTransferPage.objFavRemovedMsg, "Pop up Message"))) {
				String sRemovedSuccessfulMsg = Utilities.getText(SendTransferPage.objFavRemovedMsg);
				String sExpectedMsg = "Successfully Removed";
				Utilities.assertionValidation(sRemovedSuccessfulMsg, sExpectedMsg);
				logger.info("STW_TC_09, Successfully removed Favorite Contact from favorites list is validated");
				ExtentReporter.extentLoggerPass("STW_TC_09", "STW_TC_09, Successfully removed Favorite Contact from favorites list is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


//================================================ Transaction History ===========================================//


	public void mlWallet_TransactionHistory_Generic_Steps(String billModule, String transaction) throws Exception {
		String PayBillsHistory = Utilities.getText(MLWalletTransactionHistoryPage.objBillHistory(billModule, transaction));
		if (PayBillsHistory.equals(billModule))// "Pay Bills"
		{
			List<WebElement> values = Utilities
					.findElements(MLWalletTransactionHistoryPage.objPayBillsTransctionList1(billModule));
			for (int i = 0; i < values.size(); i++) {
				String billPayTransaction = values.get(i).getText();
				logger.info(billModule + " All Transactions : " + billPayTransaction);
				ExtentReporter.extentLogger(" ", billModule + " All Transactions : " + billPayTransaction);
			}
		} else if (PayBillsHistory.equals(transaction))// "No Recent Transaction"
		{
			logger.info("No Recent Transactions Are Available for " + billModule + " Module");
			ExtentReporter.extentLogger("", "No Recent Transactions Are Available for " + billModule + " Module");
		}
	}

	public void mlWallet_TransactionHistory() throws Exception {
		ExtentReporter.HeaderChildNode("MLWallet_TransactionHistory_Scenario");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.verifyElementPresent(MLWalletTransactionHistoryPage.objRecentTransaction, Utilities.getText(MLWalletTransactionHistoryPage.objRecentTransaction));
		Utilities.Swipe("UP", 2);
		Utilities.click(MLWalletTransactionHistoryPage.objSeeMoreBtn, "See More Button");
		logger.info("TH_TC_01, All Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_01", "'TH_TC_01', All Transactions are displayed");

		Utilities.click(MLWalletTransactionHistoryPage.objBillsPayTab, "Bills Pay");
		mlWallet_TransactionHistory_Generic_Steps("Pay Bills", "No Recent Transaction");
		logger.info("TH_TC_02, Bills pay Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_02", "'TH_TC_02', Bills pay Transactions are displayed");

		Utilities.click(MLWalletTransactionHistoryPage.objeLoadTab, "eLoad");
		mlWallet_TransactionHistory_Generic_Steps("Buy Eload", "No Recent Transaction");
		logger.info("TH_TC_03, eLoad Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_03", "'TH_TC_03', eLoad Transactions Transactions are displayed");


		Utilities.click(MLWalletTransactionHistoryPage.objSendMoneyTab, "Send Money");
		mlWallet_TransactionHistory_Generic_Steps("Wallet to Wallet", "No Recent Transaction"); // Kwarta Padala
		logger.info("TH_TC_04, Send Money Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_04", "'TH_TC_04', Send Money Transactions are displayed");


		Utilities.scrollToFirstHorizontalScrollableElement("Receive Money");
		Utilities.click(MLWalletTransactionHistoryPage.objCashInTab, "Cash In");
		Thread.sleep(3000);
		mlWallet_TransactionHistory_Generic_Steps("Cash In", "No Recent Transaction");
		logger.info("TH_TC_05, Cash In Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_05", "'TH_TC_05',  Cash In Transactions are displayed");


		Utilities.click(MLWalletTransactionHistoryPage.objCashOutTab, "Cash Out");
		Thread.sleep(3000);
		mlWallet_TransactionHistory_Generic_Steps("Cash Out", "No Recent Transaction");
		logger.info("TH_TC_06,  Cash Out Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_06", "'TH_TC_06', Cash Out Transactions are displayed");

		Utilities.click(MLWalletTransactionHistoryPage.objReceiveMoneyTab, "Receive Money");
		Thread.sleep(3000);
		mlWallet_TransactionHistory_Generic_Steps("Receive Money", "No Recent Transaction");
		logger.info("TH_TC_07, Receive Money Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_07", "'TH_TC_07', Receive Money Transactions are displayed");

		Utilities.scrollToFirstHorizontalScrollableElement("ML Shop");
		Utilities.click(MLWalletTransactionHistoryPage.objBalanceAdjustmentTab, "Balance Adjustment");
		Thread.sleep(2000);
		mlWallet_TransactionHistory_Generic_Steps("Balance Adjustment", "No Recent Transaction");
		logger.info("TH_TC_08, Balance Adjustment Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_08", "'TH_TC_08', Balance Adjustment Transactions are displayed");
		Thread.sleep(2000);
		Utilities.click(MLWalletTransactionHistoryPage.objMlShopTab, "ML Shop");
		Thread.sleep(2000);
		mlWallet_TransactionHistory_Generic_Steps("ML Shop", "No Recent Transaction");
		logger.info("TH_TC_09, ML Shop Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_09", "'TH_TC_09', ML Shop Transactions are displayed");

		Utilities.click(MLWalletShopItemsPage.objBackArrowBtn, "Back Arrow Button");
		Utilities.Swipe("down", 2);
		if (Utilities.verifyElementPresent(MLWalletShopItemsPage.objAvailableBalance, "Available Balance")) {
			logger.info("Navigated to Home Page");
			ExtentReporter.extentLogger("", "Navigated to Home Page");
		} else {
			logger.info("Failed to Navigate Home Page");
			ExtentReporter.extentLogger("", "Failed to Navigate Home Page");
		}

		mlWalletLogout();

	}

//=================================== ML Wallet Shop Items ==========================================================//


	public void mlWallet_ShopItems_Generic_Steps() throws Exception {
		// ExtentReporter.HeaderChildNode("Shop_Items");
		Utilities.click(MLWalletShopItemsPage.objShopItemsTab, "Shop Items Icon");
		Utilities.verifyElementPresentAndClick(MLWalletShopItemsPage.objMLShopPage, "ML Shop Page");
		Thread.sleep(5000);
		Utilities.Swipe("UP", 2);
		Utilities.click(MLWalletShopItemsPage.objItemMenu, "Rings Item");
		Utilities.click(MLWalletShopItemsPage.objSelectItem, Utilities.getTextVal(MLWalletShopItemsPage.objSelectItem, "Item"));
		Utilities.Swipe("up", 2);
		Utilities.click(MLWalletShopItemsPage.objAddToCartBtn, "Add to cart Button");
		Utilities.Swipe("down", 4);
		Utilities.click(MLWalletShopItemsPage.objHambergerMenu, "Hamburger Menu");
		Utilities.click(MLWalletShopItemsPage.objyourBagMenu, "Your Bag");
		Utilities.click(MLWalletShopItemsPage.objCheckBox, "Check Box");
		Utilities.click(MLWalletShopItemsPage.objCheckOutBtn, "Checkout Button");
		Utilities.click(MLWalletShopItemsPage.objEditAddress, "Edit Address Tab");
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objSelectBranchPage, Utilities.getTextVal(MLWalletShopItemsPage.objSelectBranchPage, "Page"));
		Utilities.click(MLWalletShopItemsPage.objInputFieldOne, "Select Branch Field 1");
		Utilities.click(MLWalletShopItemsPage.objBranchName, Utilities.getTextVal(MLWalletShopItemsPage.objBranchName, "Branch Name"));
		Utilities.click(MLWalletShopItemsPage.objInputFieldTwo, "Select Branch Field 2");
		Utilities.click(MLWalletShopItemsPage.objSubBranchName, Utilities.getTextVal(MLWalletShopItemsPage.objSubBranchName, "Branch Name"));
		Utilities.click(MLWalletShopItemsPage.objInputFieldThree, "Select Branch Field 3");
		Utilities.click(MLWalletShopItemsPage.objSubBranchNameTwo, Utilities.getTextVal(MLWalletShopItemsPage.objSubBranchNameTwo, "Branch Name"));
		Utilities.click(MLWalletShopItemsPage.objSaveBtn, "Save Button");
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objAddressSuccessfulMsg, Utilities.getTextVal(MLWalletShopItemsPage.objAddressSuccessfulMsg, "Message"));
		Utilities.click(MLWalletShopItemsPage.objOkBtn, "OK Button");
		Utilities.scrollToVertical("Place Order");
		Utilities.click(MLWalletShopItemsPage.objPlaceOrderBtn, "Place Order Button");

	}

	public void mlWallet_ShopItems_Successful_Purchase() throws Exception {
		ExtentReporter.HeaderChildNode("mlWalletShopItems_Successful_Purchase");
		mlWallet_ShopItems_Generic_Steps();
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objOtpPage, Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		Utilities.click(MLWalletShopItemsPage.objOtpTextField, "Otp Text Field");
		Utilities.handleOtp(prop.getproperty("otp"));
		Utilities.click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		// code for successful purchase message validation
	}

	public void mlWallet_ShopItems_with_Insufficient_Balance() throws Exception {
		ExtentReporter.HeaderChildNode("mlWallet_ShopItems_with_Insufficient_Balance");
		mlWalletLogin(prop.getproperty("Buyer_Tier"));
		mlWallet_ShopItems_Generic_Steps();
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objOtpPage, Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		Utilities.click(MLWalletShopItemsPage.objOtpTextField, "Otp Text Field");
		Utilities.handleOtp(prop.getproperty("OTP"));
		Utilities.click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		String oOpsMsg = Utilities.getText(MLWalletShopItemsPage.objInvalidOtpPopUp);
		String supplyFieldsMsg = Utilities.getText(MLWalletShopItemsPage.objInvalidOtpPopUpMsg);
		logger.info(oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");
		ExtentReporter.extentLogger("", oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");
		logger.info("MLS_TC_02, Oops... Insufficient Balance. - Error message is validated ");
		ExtentReporter.extentLoggerPass("MLS_TC_02", "MLS_TC_02, Oops... Insufficient Balance. - Error message is validated");
		System.out.println("-----------------------------------------------------------");
	}

	public void mlWallet_ShopItems_with_Incorrect_Otp() throws Exception {
		ExtentReporter.HeaderChildNode("mlWallet_ShopItems_with_Incorrect_Otp");
		mlWallet_ShopItems_Generic_Steps();
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objOtpPage, Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		Utilities.click(MLWalletShopItemsPage.objOtpTextField, "Otp Text Field");
		Utilities.handleOtp(prop.getproperty("incorrectOtp"));
		Utilities.click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		// Code to be written to validate incorrect otp msg
	}

	public void mlWallet_ShopItems_without_Input_Otp() throws Exception {
		ExtentReporter.HeaderChildNode("mlWallet_ShopItems_without_Input_Otp");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		mlWallet_ShopItems_Generic_Steps();
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objOtpPage, Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		Utilities.click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		String oOpsMsg = Utilities.getText(MLWalletShopItemsPage.objInvalidOtpPopUp);
		String supplyFieldsMsg = Utilities.getText(MLWalletShopItemsPage.objInvalidOtpPopUpMsg);
		logger.info(oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");
		ExtentReporter.extentLogger("", oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");

		logger.info("MLS_TC_04, Oops... Please supply all fields. - Error message is validated");
		ExtentReporter.extentLoggerPass("MLS_TC_04", "MLS_TC_04, Oops... Please supply all fields. - Error message is validated");
		System.out.println("-----------------------------------------------------------");
	}



//=========================================== Cash In Via Bank ================================================//
//======================= Generalized methods for Cash In Via Bank ===========================================//

	public void selectBankAndInputAmount(String sAmount) throws Exception {
		if (Utilities.verifyElementPresent(MLWalletCashInBank.objCashIn, Utilities.getTextVal(MLWalletCashInBank.objCashIn, "Icon"))) {
			Utilities.click(MLWalletCashInBank.objCashIn, Utilities.getTextVal(MLWalletCashInBank.objCashIn, "Icon"));
			Utilities.click(MLWalletCashInBank.objMyBankAccount, Utilities.getTextVal(MLWalletCashInBank.objMyBankAccount, "Button"));
			Utilities.verifyElementPresent(MLWalletCashInBank.objSelectABank, Utilities.getTextVal(MLWalletCashInBank.objSelectABank, "Page"));
			Utilities.click(MLWalletCashInBank.objTestBankOnline, Utilities.getTextVal(MLWalletCashInBank.objTestBankOnline, "Bank"));
			Utilities.verifyElementPresent(MLWalletCashInBank.objDragonPay, Utilities.getTextVal(MLWalletCashInBank.objDragonPay, "Page"));
			Utilities.verifyElementPresent(MLWalletCashInBank.objBankCashIn, Utilities.getTextVal(MLWalletCashInBank.objBankCashIn, "Text"));
			Utilities.verifyElementPresent(MLWalletCashInBank.objServiceFeeMsg, Utilities.getTextVal(MLWalletCashInBank.objServiceFeeMsg, "Message"));
			String sServiceFeeMsg = Utilities.getText(MLWalletCashInBank.objServiceFeeMsg);
			String sExpectedMsg = "Service fee will be deducted from your initial amount";
			Utilities.assertionValidation(sServiceFeeMsg, sExpectedMsg);
			Utilities.type(MLWalletCashInBank.objAmountEditField, sAmount, "Amount Text Field");
			Utilities.click(MLWalletCashInBank.objNextBtn, Utilities.getTextVal(MLWalletCashInBank.objNextBtn, "Button"));
			Thread.sleep(3000);
		}
	}
	public void dragonPayChargesMsgValidation() throws Exception {
		if (Utilities.verifyElementPresent(MLWalletCashInBank.objDragonPayChargesMsg, Utilities.getTextVal(MLWalletCashInBank.objDragonPayChargesMsg, "Message"))) {
			String sDragonPayChargesMsg = Utilities.getText(MLWalletCashInBank.objDragonPayChargesMsg);
			String sExpectedDragonPayChargesMsg = "Dragon Pay charges a fee of 30 pesos for this transaction. Do you wish to continue with your transaction?";
			Utilities.assertionValidation(sDragonPayChargesMsg, sExpectedDragonPayChargesMsg);
			Utilities.click(MLWalletCashInBank.objContinueBtn, Utilities.getTextVal(MLWalletCashInBank.objContinueBtn, "Button"));
		}
	}
	public void reviewTransactionValidation() throws Exception {
		Utilities.verifyElementPresent(MLWalletCashInBank.objReviewTransaction,Utilities.getTextVal(MLWalletCashInBank.objReviewTransaction,"Page"));
		Utilities.Swipe("UP",1);
		if(Utilities.verifyElementPresent(MLWalletCashInBank.objBankTransferCutOffTime,Utilities.getTextVal(MLWalletCashInBank.objBankTransferCutOffTime,"Message"))){
			String sBankTransferTime = Utilities.getText(MLWalletCashInBank.objBankTransferCutOffTime);
			String sExpectedBankTransferTime ="Bank transfers after 1:00PM are posted on the next banking day.";
			Utilities.assertionValidation(sBankTransferTime,sExpectedBankTransferTime);
		}
		Utilities.click(MLWalletCashInBank.objNextBtn,Utilities.getTextVal(MLWalletCashInBank.objNextBtn,"Button"));
	}


	public void bankUserLogin(String sLoginId,String sPassword) throws Exception {
		Utilities.explicitWaitVisible(MLWalletCashInBank.objReferenceNumberMsg,5);
		if(Utilities.verifyElementPresent(MLWalletCashInBank.objReferenceNumberMsg,Utilities.getTextVal(MLWalletCashInBank.objReferenceNumberMsg,"Reference Information"))){
			Utilities.type(MLWalletCashInBank.objLoginIdTxtField,sLoginId,"Login Id Text Field");
			Utilities.type(MLWalletCashInBank.objPasswordTxtField,sPassword,"Password Text Field");

		}
	}


//===================================================================================================================//

	public void cashInViaBank_CIBA_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("100");
		dragonPayChargesMsgValidation();
		reviewTransactionValidation();
		enterMLPin();
		enableLocation_PopUp();
		bankUserLogin(prop.getproperty("Valid_LoginId"), prop.getproperty("Valid_Password"));
		Utilities.click(MLWalletCashInBank.objWebContinueBtn,"Continue Button");
		Utilities.click(MLWalletCashInBank.objPayBtn,Utilities.getTextVal(MLWalletCashInBank.objPayBtn,"Button"));
		Utilities.verifyElementPresent(MLWalletCashInBank.objBankReferenceNumber,Utilities.getTextVal(MLWalletCashInBank.objBankReferenceNumber,"Reference Number"));
		Utilities.verifyElementPresent(MLWalletCashInBank.objStatus,Utilities.getTextVal(MLWalletCashInBank.objStatus,"Status"));
		Utilities.verifyElementPresent(MLWalletCashInBank.objMessage,Utilities.getTextVal(MLWalletCashInBank.objMessage,"Message"));
		if(Utilities.verifyElementPresent(MLWalletCashInBank.objSuccessMsg,Utilities.getTextVal(MLWalletCashInBank.objSuccessMsg,"Message"))){
			logger.info("CIBA_TC_01, Cash In Via Bank validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_01", "CIBA_TC_01, Cash In Via Bank validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

//	public void cashInViaBankInvalidBankDetails() throws Exception {
//		ExtentReporter.HeaderChildNode("Cash In Via Bank Invalid Bank Details");
//		mlWalletLogin(prop.getproperty("Branch_Verified"));
//		selectBankAndInputAmount("100");
//		dragonPayChargesMsgValidation();
//		reviewTransactionValidation();
//		enterMLPin();
//		enableLocation_PopUp();
//		bankUserLogin(prop.getproperty("InValid_LoginId"), prop.getproperty("Invalid_Password"));
//	}

	public void cashInViaBankMinimumTransactionLimit_CIBA_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Minimum Transaction Limit");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("20");
		if(Utilities.verifyElementPresent(MLWalletCashInBank.objMinimumTransactionPopupMsg,Utilities.getTextVal(MLWalletCashInBank.objMinimumTransactionPopupMsg,"Pop Message"))){
			String sMinimumTransactionPopupMsg = Utilities.getText(MLWalletCashInBank.objMinimumTransactionPopupMsg);
			String sExpectedPopupMsg = "The supplied amount is less than the required minimum transaction limit";
			Utilities.assertionValidation(sMinimumTransactionPopupMsg,sExpectedPopupMsg);
			logger.info("CIBA_TC_03, Minimum transaction limit pop up message is validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_03", "CIBA_TC_03, Minimum transaction limit pop up message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void cashInViaBankMaximumTransaction() throws Exception {
		ExtentReporter.HeaderChildNode("Cash In Via Bank Maximum Transaction");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		selectBankAndInputAmount("60000");
		dragonPayChargesMsgValidation();
		reviewTransactionValidation();
		if (Utilities.verifyElementPresent(SendTransferPage.objMaxLimitErrorMsg, Utilities.getTextVal(SendTransferPage.objMaxLimitErrorMsg, "Error Message"))) {
			String sMaximumLimitErrorMsg = Utilities.getText(SendTransferPage.objMaxLimitErrorMsg);
			String sExpectedErrorMsg = "The maximum Bank Cash-in per transaction set for your verification level is P50,000.00. Please try again.";
			Utilities.assertionValidation(sMaximumLimitErrorMsg, sExpectedErrorMsg);
			logger.info("CIBA_TC_04, The maximum send money per transaction - Error Message is validated");
			ExtentReporter.extentLoggerPass("CIBA_TC_04", "CIBA_TC_04, The maximum send money per transaction - Error Message is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}


//=============================================Pay Bills===============================================================//
//=============================Generalized Methods=====================================================//

	public void getBillers(By sWebElement){
		List<WebElement> list = Utilities.findElements(sWebElement);

		for(int i=0 ;i<list.size(); i++){
			String billers= list.get(i).getText();
			logger.info(billers+ " Biller is displayed");
		}
	}

	public void searchBiller() throws Exception {
		Utilities.verifyElementPresent(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.type(MLWalletPayBillsPage.objSearchBiller,prop.getproperty("Biller_Name"),"Search Biller");
		Utilities.verifyElementPresent(MLWalletPayBillsPage.objMisBillsPayBiller,Utilities.getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller,"Biller"));
		Utilities.click(MLWalletPayBillsPage.objMisBillsPayBiller,Utilities.getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller,"Biller"));
	}

	public void billerDetails(String sFirstName,String sLastName,String sMiddleName, String sAmount) throws Exception {
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objBillsPayInformation,Utilities.getTextVal(MLWalletPayBillsPage.objBillsPayInformation,"Page"))){
			Utilities.type(MLWalletPayBillsPage.objAccountNumberField, prop.getproperty("AccountNumber"),"Account Number Text Field");
			Utilities.type(MLWalletPayBillsPage.objFirstNameField,sFirstName ,"First Name Text Field");
			Utilities.type(MLWalletPayBillsPage.objMiddleNameField,sMiddleName ,"Middle Name Text Field");
			Utilities.type(MLWalletPayBillsPage.objLastnameField,sLastName ,"Last Name Text Field");
			Utilities.Swipe("UP",1);
			Utilities.type(MLWalletPayBillsPage.objAmountField, sAmount,"Amount to Send Text Field");
			Utilities.click(MLWalletPayBillsPage.objConfirmBtn,Utilities.getTextVal(MLWalletPayBillsPage.objConfirmBtn,"Button"));
		}
	}

	public void addSelectedBiller() throws Exception {
		if (Utilities.verifyElementPresent(MLWalletPayBillsPage.objAddSeectedBiller, "Edit Biller")) {
			Utilities.click(MLWalletPayBillsPage.objAddSeectedBiller, "Edit Biller");
			Utilities.click(MLWalletPayBillsPage.objBillerListSearchBiller,"Biller List Search Biller");
			Utilities.type(MLWalletPayBillsPage.objBillerListSearchBiller, prop.getproperty("Biller_Name"), "Biller List Search Biller");
			Utilities.explicitWaitVisible(MLWalletPayBillsPage.objMisBillsPayBiller,5);
			Utilities.click(MLWalletPayBillsPage.objMisBillsPayBiller, Utilities.getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller, "Biller"));
			Utilities.click(MLWalletPayBillsPage.objMisBillsPayBiller, Utilities.getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller, "Biller"));
		}
	}

	public void addBiller() throws Exception {
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objSavedBiller, Utilities.getTextVal(MLWalletPayBillsPage.objSavedBiller, "Button"))) {
			Utilities.click(MLWalletPayBillsPage.objSavedBiller, Utilities.getTextVal(MLWalletPayBillsPage.objSavedBiller, "Button"));
			Utilities.explicitWaitVisible(MLWalletPayBillsPage.objAddBiller,5);
			Utilities.click(MLWalletPayBillsPage.objAddBiller, Utilities.getTextVal(MLWalletPayBillsPage.objAddBiller, "Button"));
			addSelectedBiller();
			if (Utilities.verifyElementPresent(MLWalletPayBillsPage.objAddBillers, Utilities.getTextVal(MLWalletPayBillsPage.objAddBillers, "Page"))) {
				Utilities.type(MLWalletPayBillsPage.objAddAccountNumber, prop.getproperty("AccountNumber"), "Account Number in Add Biller");
				Utilities.type(MLWalletPayBillsPage.objAddFirstName, prop.getproperty("First_Name"), "First Name in Add Biller");
				Utilities.type(MLWalletPayBillsPage.objAddMiddleName, prop.getproperty("Middle_Name"), "Middle Name in Add Biller");
				Utilities.type(MLWalletPayBillsPage.objAddLastName, prop.getproperty("Last_Name"), "Last Name in Add Biller");
				Utilities.type(MLWalletPayBillsPage.objAddNickName, prop.getproperty("Nick_Name"), "Nick Name in Add Biller");
				Utilities.click(MLWalletPayBillsPage.objProceedBtn, Utilities.getTextVal(MLWalletPayBillsPage.objProceedBtn, "button"));
			}
		}
	}

//================================================================================================================//


	public void payBillsPageValidation_PB_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills Page Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"))){
			Utilities.click(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
			if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objSelectBiller,Utilities.getTextVal(MLWalletPayBillsPage.objSelectBiller,"Page"))){
				Utilities.verifyElementPresent(MLWalletPayBillsPage.objRecentTransactions,Utilities.getTextVal(MLWalletPayBillsPage.objRecentTransactions,"Button"));
				Utilities.verifyElementExist(MLWalletPayBillsPage.objBillers,Utilities.getTextVal(MLWalletPayBillsPage.objBillers,"Text"));
				logger.info("PB_TC_01, Pay Bills Page validated");
				ExtentReporter.extentLoggerPass("PB_TC_01", "PB_TC_01, Pay Bills Page validated");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void billerCategories_PB_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Biller Categories");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.verifyElementPresent(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objCategories,Utilities.getTextVal(MLWalletPayBillsPage.objCategories,"Button"));
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objBankingAndFinance,Utilities.getTextVal(MLWalletPayBillsPage.objBankingAndFinance,"Button"))){
			Utilities.click(MLWalletPayBillsPage.objBankingAndFinance,Utilities.getTextVal(MLWalletPayBillsPage.objBankingAndFinance,"Biller Category"));
		}
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objCharityAndReligious,Utilities.getTextVal(MLWalletPayBillsPage.objCharityAndReligious,"Biller Category"))){
			Utilities.click(MLWalletPayBillsPage.objCharityAndReligious,Utilities.getTextVal(MLWalletPayBillsPage.objCharityAndReligious,"Biller Category"));
			getBillers(MLWalletPayBillsPage.objCharityAndReligiousBillers);
			Utilities.click(MLWalletPayBillsPage.objCharityAndReligious,Utilities.getTextVal(MLWalletPayBillsPage.objCharityAndReligious,"Biller Category"));
		}
		Utilities.Swipe("UP",1);
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objUtilities,Utilities.getTextVal(MLWalletPayBillsPage.objUtilities,"Biller Category"))) {
			Utilities.click(MLWalletPayBillsPage.objUtilities, Utilities.getTextVal(MLWalletPayBillsPage.objUtilities, "Biller Category"));
			Utilities.Swipe("UP", 1);
			getBillers(MLWalletPayBillsPage.objUtilitiesBillers);
			Utilities.click(MLWalletPayBillsPage.objUtilities, Utilities.getTextVal(MLWalletPayBillsPage.objUtilities, "Biller Category"));
		}
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objOthers,Utilities.getTextVal(MLWalletPayBillsPage.objOthers,"Biller Category"))){
			Utilities.click(MLWalletPayBillsPage.objOthers,Utilities.getTextVal(MLWalletPayBillsPage.objOthers,"Biller Category"));
			Utilities.Swipe("UP",1);
			getBillers(MLWalletPayBillsPage.objOthersBillers);
			Utilities.click(MLWalletPayBillsPage.objOthers,Utilities.getTextVal(MLWalletPayBillsPage.objOthers,"Biller Category"));
		}
		logger.info("PB_TC_02, Biller Categories validated");
		ExtentReporter.extentLoggerPass("PB_TC_02", "PB_TC_02, Biller Categories validated");
		System.out.println("-----------------------------------------------------------");
	}

	public void billersInAlphabeticalOrder_PB_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Billers In Alphabetical Order");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		Utilities.verifyElementPresent(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objAlphabetical,Utilities.getTextVal(MLWalletPayBillsPage.objAlphabetical,"Button"));
		Utilities.swipeDownUntilElementVisible("SAGIP KAPAMILYA");
		Utilities.swipeDownUntilElementVisible("ZYBITECH");
		logger.info("PB_TC_03, Billers swiped until Z Alphabet, and all the Billers are displayed in alphabetical order");
		ExtentReporter.extentLoggerPass("PB_TC_03", "PB_TC_03, Billers swiped until Z Alphabet, and all the Billers are displayed in alphabetical order");
		System.out.println("-----------------------------------------------------------");
	}

	public void selectBiller_PB_TC_04() throws Exception {
		ExtentReporter.HeaderChildNode("Select Biller");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		Utilities.verifyElementPresent(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objAlphabetical,Utilities.getTextVal(MLWalletPayBillsPage.objAlphabetical,"Button"));
		Utilities.swipeDownUntilElementVisible("AIR ASIA BILLSPAYMENT");
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objAirAsia,Utilities.getTextVal(MLWalletPayBillsPage.objAirAsia,"Biller"))){
			String sAirAsiaBillsPayment = Utilities.getText(MLWalletPayBillsPage.objAirAsia);
			Utilities.click(MLWalletPayBillsPage.objAirAsia,Utilities.getTextVal(MLWalletPayBillsPage.objAirAsia,"Biller"));
			Utilities.verifyElementPresent(MLWalletPayBillsPage.objBillsPayInformation,Utilities.getTextVal(MLWalletPayBillsPage.objBillsPayInformation,"Page"));
			if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objBillerNameInBillsPayInformation,Utilities.getTextVal(MLWalletPayBillsPage.objBillerNameInBillsPayInformation,"Biller Name"))){
				String sBillerInBillsPayInformation = Utilities.getText(MLWalletPayBillsPage.objBillerNameInBillsPayInformation);
				Utilities.assertionValidation(sAirAsiaBillsPayment,sBillerInBillsPayInformation);
				logger.info("PB_TC_04, Bills Pay Information page is displayed and Biller name is matched");
				ExtentReporter.extentLoggerPass("PB_TC_04", "PB_TC_04, Bills Pay Information page is displayed and Biller name is matched");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}

	public void searchBiller_PB_TC_05() throws Exception {
		ExtentReporter.HeaderChildNode("Search Biller");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		Utilities.verifyElementPresent(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Thread.sleep(5000);
		Utilities.type(MLWalletPayBillsPage.objSearchBiller,prop.getproperty("Biller_Name"),"Search Biller");
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objMisBillsPayBiller,Utilities.getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller,"Biller"))){
			String sMisBillsPayBiller = Utilities.getText(MLWalletPayBillsPage.objMisBillsPayBiller);
			Utilities.click(MLWalletPayBillsPage.objMisBillsPayBiller,Utilities.getTextVal(MLWalletPayBillsPage.objMisBillsPayBiller,"Biller"));
			Utilities.verifyElementPresent(MLWalletPayBillsPage.objBillsPayInformation,Utilities.getTextVal(MLWalletPayBillsPage.objBillsPayInformation,"Page"));
			if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objBillerNameInBillsPayInformation,Utilities.getTextVal(MLWalletPayBillsPage.objBillerNameInBillsPayInformation,"Biller Name"))){
				String sBillerInBillsPayInformation = Utilities.getText(MLWalletPayBillsPage.objBillerNameInBillsPayInformation);
				Utilities.assertionValidation(sMisBillsPayBiller,sBillerInBillsPayInformation);
				logger.info("PB_TC_05, Bills Pay Information page is displayed and Biller name is MIS BILLSPAY123456");
				ExtentReporter.extentLoggerPass("PB_TC_05", "PB_TC_05, Bills Pay Information page is displayed and Biller name is MIS BILLSPAY123456");
				System.out.println("-----------------------------------------------------------");
			}
		}
	}


	public void billingInformationInput_PB_TC_06() throws Exception {
		ExtentReporter.HeaderChildNode("Biller Information Input");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		searchBiller();
		billerDetails(prop.getproperty("First_Name"),prop.getproperty("Middle_Name"),prop.getproperty("Last_Name"),"10");
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objConfirmDetails,Utilities.getTextVal(MLWalletPayBillsPage.objConfirmDetails,"Page"))){
			Utilities.verifyElementPresent(MLWalletPayBillsPage.objBillerName,Utilities.getTextVal(MLWalletPayBillsPage.objBillerName,"Biller Name"));
			Utilities.verifyElementPresent(MLWalletPayBillsPage.objAccountName,Utilities.getTextVal(MLWalletPayBillsPage.objAccountName,"Account holder Name"));
			Utilities.verifyElementPresent(MLWalletPayBillsPage.objAccountNumber,Utilities.getTextVal(MLWalletPayBillsPage.objAccountNumber,"Account Number"));
			Utilities.verifyElementPresent(MLWalletPayBillsPage.objPaymentMethod,Utilities.getTextVal(MLWalletPayBillsPage.objPaymentMethod,"Payment Method"));
			logger.info("PB_TC_06, Confirm Details page displayed with transaction details is validated");
			ExtentReporter.extentLoggerPass("PB_TC_06", "PB_TC_06, Confirm Details page displayed with transaction details is validated");
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void payBillsWithValidInputs() throws Exception {
		ExtentReporter.HeaderChildNode("Pay Bills With Valid Inputs");
		ExtentReporter.HeaderChildNode("Biller Information Input");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		searchBiller();
		billerDetails(prop.getproperty("First_Name"), prop.getproperty("Middle_Name"), prop.getproperty("Last_Name"), "10");
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objConfirmDetails, Utilities.getTextVal(MLWalletPayBillsPage.objConfirmDetails, "Page"))) {
			Utilities.Swipe("UP",1);
			Utilities.click(MLWalletPayBillsPage.objPayBtn,Utilities.getTextVal(MLWalletPayBillsPage.objPayBtn,"Button"));
		}
		enableLocation_PopUp();
		enterOTP(prop.getproperty("Valid_OTP"));
	}



	public void billingInformationInvalidInput_PB_TC_10() throws Exception {
		ExtentReporter.HeaderChildNode("Biller Information Invalid Input");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		searchBiller();
		Utilities.click(MLWalletPayBillsPage.objConfirmBtn,Utilities.getTextVal(MLWalletPayBillsPage.objConfirmBtn,"Button"));
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objAccountNumberRequiredMsg,Utilities.getTextVal(MLWalletPayBillsPage.objAccountNumberRequiredMsg,"Error Message"))){
			String sAccountNumberRequiredErrorMsg = Utilities.getText(MLWalletPayBillsPage.objAccountNumberRequiredMsg);
			String sExceptedAccountNumberRequiredErrorMsg = "Account Number is required";
			Utilities.assertionValidation(sAccountNumberRequiredErrorMsg,sExceptedAccountNumberRequiredErrorMsg);
		}
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objFirstNameRequiredMsg,Utilities.getTextVal(MLWalletPayBillsPage.objFirstNameRequiredMsg,"Error Message"))){
			String sFirstNameRequiredErrorMsg = Utilities.getText(MLWalletPayBillsPage.objFirstNameRequiredMsg);
			String sExceptedFirstNameRequiredErrorMsg = "First name is required";
			Utilities.assertionValidation(sFirstNameRequiredErrorMsg,sExceptedFirstNameRequiredErrorMsg);
		}

		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objLastNameRequiredMsg,Utilities.getTextVal(MLWalletPayBillsPage.objLastNameRequiredMsg,"Error Message"))){
			String sLastNameRequiredErrorMsg = Utilities.getText(MLWalletPayBillsPage.objLastNameRequiredMsg);
			String sExceptedLastNameRequiredErrorMsg = "Last name is required";
			Utilities.assertionValidation(sLastNameRequiredErrorMsg,sExceptedLastNameRequiredErrorMsg);
		}

		billerDetails(prop.getproperty("Invalid_First_Name"),prop.getproperty("Invalid_Middle_Name"),prop.getproperty("Invalid_Last_Name"),"0.99");
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objInvalidFirstNameMsg,Utilities.getTextVal(MLWalletPayBillsPage.objInvalidFirstNameMsg,"Error Message"))){
			String sInvalidFirstNameErrorMsg = Utilities.getText(MLWalletPayBillsPage.objInvalidFirstNameMsg);
			String sExceptedFirstNameErrorMsg = "First name must only contain letters and spaces";
			Utilities.assertionValidation(sInvalidFirstNameErrorMsg,sExceptedFirstNameErrorMsg);
		}
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objInvalidSecondNameMsg,Utilities.getTextVal(MLWalletPayBillsPage.objInvalidSecondNameMsg,"Error Message"))){
			String sInvalidSecondNameErrorMsg = Utilities.getText(MLWalletPayBillsPage.objInvalidSecondNameMsg);
			String sExceptedSecondNameErrorMsg = "Middle name must only contain letters and spaces";
			Utilities.assertionValidation(sInvalidSecondNameErrorMsg,sExceptedSecondNameErrorMsg);
		}
		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objInvalidLastName,Utilities.getTextVal(MLWalletPayBillsPage.objInvalidLastName,"Error Message"))){
			String sInvalidThirdNameErrorMsg = Utilities.getText(MLWalletPayBillsPage.objInvalidLastName);
			String sExceptedThirdNameErrorMsg = "Last name must only contain letters and spaces";
			Utilities.assertionValidation(sInvalidThirdNameErrorMsg,sExceptedThirdNameErrorMsg);
		}
		billerDetails(prop.getproperty("First_Name"),prop.getproperty("Middle_Name"),prop.getproperty("Last_Name"),"0.99");
		Utilities.Swipe("UP",1);

		if(Utilities.verifyElementPresent(MLWalletPayBillsPage.objInvalidAmount,Utilities.getTextVal(MLWalletPayBillsPage.objInvalidAmount,"Error Message"))){
			String sInvalidAmountErrorMsg = Utilities.getText(MLWalletPayBillsPage.objInvalidAmount);
			String sExceptedAmountErrorMsg = "The amount should not be less than 1";
			Utilities.assertionValidation(sInvalidAmountErrorMsg,sExceptedAmountErrorMsg);
		}

		logger.info("PB_TC_10, Invalid biller Information Error messages validated");
		ExtentReporter.extentLoggerPass("PB_TC_10", "PB_TC_10, Invalid biller Information Error messages validated");
		System.out.println("-----------------------------------------------------------");
	}

	public void addBillerToPayBills() throws Exception {
		ExtentReporter.HeaderChildNode("Add Biller To Pay Bills");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.verifyElementPresent(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		addBiller();
	}

	public void addBillerInvalidInputs() throws Exception {
		ExtentReporter.HeaderChildNode("Add Biller Invalid Inputs");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.verifyElementPresent(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objPayBills,Utilities.getTextVal(MLWalletPayBillsPage.objPayBills,"Icon"));
		Utilities.click(MLWalletPayBillsPage.objSavedBiller, Utilities.getTextVal(MLWalletPayBillsPage.objSavedBiller, "Button"));
		Utilities.explicitWaitVisible(MLWalletPayBillsPage.objAddBiller,5);
		Utilities.click(MLWalletPayBillsPage.objAddBiller, Utilities.getTextVal(MLWalletPayBillsPage.objAddBiller, "Button"));
		addSelectedBiller();

	}



//============================ Settings Module ============================================================//
//================================Generalized methods=======================================================//


	public void navigateToProfile() throws Exception {
		Utilities.click(MLWalletSettinsPage.objProfileIcon, "Profile Icon");
		if (Utilities.verifyElementPresent(MLWalletSettinsPage.objAccountDetails, "Account Details Page")) {
		} else {
			DriverManager.getAppiumDriver().navigate().back();
			Utilities.click(MLWalletSettinsPage.objProfileIcon1, "Profile Icon");
		}
	}
//===========================================================================================================//
	public void accountDetailsValidation_SS_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Account Details validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		navigateToProfile();
		Utilities.click(MLWalletSettinsPage.objAccountDetails, "Account Details");
		Utilities.verifyElementPresent(MLWalletSettinsPage.objAccountDetails, Utilities.getTextVal(MLWalletSettinsPage.objAccountDetails,"Name"));
		Utilities.verifyElementPresent(MLWalletSettinsPage.objNameOfAccountHolder,Utilities.getTextVal(MLWalletSettinsPage.objNameOfAccountHolder, "Name"));
		Utilities.verifyElementPresent(MLWalletSettinsPage.objMailAddressOfAccountHolder,Utilities.getTextVal(MLWalletSettinsPage.objMailAddressOfAccountHolder, "Mail Address"));
		Utilities.verifyElementPresent(MLWalletSettinsPage.objMobileNoOfAccountHolder,Utilities.getTextVal(MLWalletSettinsPage.objMobileNoOfAccountHolder, "Mobile Number"));
		logger.info("SS_TC_01, Account Details validated");
		ExtentReporter.extentLoggerPass("SS_TC_01", "SS_TC_01, Account Details validated");
		System.out.println("-----------------------------------------------------------");
	}
	public void invalidMLPinValidation_SS_TC_03() throws Exception {
		ExtentReporter.HeaderChildNode("Invalid ML Pin Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		navigateToProfile();
		Utilities.click(MLWalletSettinsPage.objChangeMLPin, "Change ML Pin");
		Utilities.verifyElementPresent(MLWalletSettinsPage.objChangeMLPin, "Change ML Pin");

		handleMpin(prop.getproperty("wrongMpin"), "Entered Invalid ML PIN");
		logger.info(Utilities.getTextVal(MLWalletSettinsPage.objInvalaidMpinPopUp, "Pop up"));
		ExtentReporter.extentLoggerPass("Invalid Mpin Pop up",
				Utilities.getTextVal(MLWalletSettinsPage.objInvalaidMpinPopUp, "Pop up"));
		Utilities.click(MLWalletSettinsPage.objOKBtn, "Ok Button");
		logger.info("SS_TC_03, Invalid ML PIN validated");
		ExtentReporter.extentLoggerPass("SS_TC_03", "SS_TC_03, Invalid ML PIN validated");
		System.out.println("-----------------------------------------------------------");
	}


	public void validMLPinValidation_SS_TC_02() throws Exception {
		ExtentReporter.HeaderChildNode("Invalid ML Pin Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		navigateToProfile();
		Utilities.click(MLWalletSettinsPage.objChangeMLPin, "Change ML Pin");
		Utilities.verifyElementPresent(MLWalletSettinsPage.objChangeMLPin, "Change ML Pin");

		Utilities.waitTime(2000);
		handleMpin(prop.getproperty("mPin"), "Entered");
		Utilities.waitTime(2000);
		if (Utilities.verifyElementPresent(MLWalletSettinsPage.objEnterNewMMLpinText,
				Utilities.getTextVal(MLWalletSettinsPage.objEnterNewMMLpinText, "Page"))) {
			Utilities.waitTime(2000);
			handleMpin(prop.getproperty("newMpin"), "New ML PIN");
			Utilities.waitTime(2000);
			handleMpin(prop.getproperty("newMpin"), "Confirmed New ML ");
			logger.info(Utilities.getTextVal(MLWalletSettinsPage.objMLPinChangedPopup, "Pop Up"));
			ExtentReporter.extentLoggerPass("ML Pin Changed Pop Up",
					Utilities.getTextVal(MLWalletSettinsPage.objMLPinChangedPopup, "Pop Up"));
			Utilities.click(MLWalletSettinsPage.objGotItBtn, "Got It Button");
		} else if (Utilities.verifyElementPresent(MLWalletSettinsPage.objInvalaidMpinPopUp,
				Utilities.getTextVal(MLWalletSettinsPage.objInvalaidMpinPopUp, "Pop Up"))) {
			Utilities.click(MLWalletSettinsPage.objOKBtn, "OK Button");
			handleMpin(prop.getproperty("newMpin"), "New ML PIN");
			Utilities.waitTime(2000);
			handleMpin(prop.getproperty("mPin"), "Confirmed New ML ");
			Utilities.waitTime(2000);
			handleMpin(prop.getproperty("mPin"), "Confirmed New ML ");
			logger.info(Utilities.getTextVal(MLWalletSettinsPage.objMLPinChangedPopup, "Pop Up"));
			ExtentReporter.extentLoggerPass("ML Pin Changed Pop Up",
					Utilities.getTextVal(MLWalletSettinsPage.objMLPinChangedPopup, "Pop Up"));
			Utilities.click(MLWalletSettinsPage.objGotItBtn, "Got It Button");
		}
		logger.info("'SS_TC_02' To validate ML PIN validated");
		ExtentReporter.extentLoggerPass("SS_TC_02", "'SS_TC_02' To validate ML PIN validated");

	}

	public void mlWalletSettingAccRecovery() throws Exception {
		ExtentReporter.HeaderChildNode("Invalid ML Pin Validation");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		navigateToProfile();
		Utilities.click(MLWalletSettinsPage.objAccountRecovery, "Account Recovery");
		Utilities.verifyElementPresent(MLWalletSettinsPage.objMlWalletSupportPage,
				Utilities.getTextVal(MLWalletSettinsPage.objMlWalletSupportPage, "Page"));

		Utilities.click(MLWalletSettinsPage.objFullNameField, "First Name Field");
		Utilities.type(MLWalletSettinsPage.objFullNameField, prop.getproperty("firstName"), "First Name Field");
		Utilities.hideKeyboard();

		Utilities.click(MLWalletSettinsPage.objRegisteredEmail, "Registered Email Field");
		Utilities.type(MLWalletSettinsPage.objRegisteredEmail, prop.getproperty("eMailAddress"),
				"Registered Email Field");
		Utilities.hideKeyboard();
		Utilities.click(MLWalletSettinsPage.objMobileNumber, "Mobile Number Field");
		Utilities.type(MLWalletSettinsPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Field");
		Utilities.hideKeyboard();
		Utilities.click(MLWalletSettinsPage.objNatureOfReqRadioBtn,
				Utilities.getTextVal(MLWalletSettinsPage.objNatureOfReqRadioBtn, "Text"));
		Utilities.scrollToVertical("Next");
		Utilities.click(MLWalletSettinsPage.objNextBtn, "Next Button");
		Utilities.verifyElementPresent(MLWalletSettinsPage.objStolenPage,
				Utilities.getTextVal(MLWalletSettinsPage.objStolenPage, "Page"));

		Utilities.click(MLWalletSettinsPage.objYourAnswer, "Lost/Stolen Mobile Number Field");
		Utilities.type(MLWalletSettinsPage.objYourAnswer, prop.getproperty("Branch_Verified"),
				"Lost/Stolen Mobile Number Field");
		Utilities.hideKeyboard();

		Utilities.click(MLWalletSettinsPage.objNewMobNo, "New Mobile Number Field");
		Utilities.type(MLWalletSettinsPage.objNewMobNo, prop.getproperty("newMobileNumber"), "New Mobile Number Field");
		Utilities.hideKeyboard();

		Utilities.click(MLWalletSettinsPage.objFacebookMessangerName, "Facebook Messenger Name Field");
		Utilities.type(MLWalletSettinsPage.objFacebookMessangerName, prop.getproperty("messangerName"),
				"Facebook Messenger Name Field");
		Utilities.hideKeyboard();

		Utilities.scrollToVertical("Submit");
		Utilities.click(MLWalletSettinsPage.objSumbitBtn, "Submit Button");
		Utilities.verifyElementPresent(MLWalletSettinsPage.objReviewPage, "Review Page");
		String actualText = "Please allow us some time to review the details of your request. A customer service representative will contact you for updates and/or to get additional information.";
		String reviewExpectedText = Utilities.getText(MLWalletSettinsPage.objReviewPage);
		Utilities.assertionValidation(actualText, reviewExpectedText);
		logger.info("'SS_TC_05', To verify account recovery validated");
		ExtentReporter.extentLoggerPass("Account Recovery", "'SS_TC_05', To verify account recovery validated");

	}

	public static void handleMpin(String mPin, String validationText) throws Exception {
		for (int i = 0; i < mPin.length(); i++) {
			char ch = mPin.charAt(i);
			String ch1 = String.valueOf(ch);
			Utilities.click(MLWalletSettinsPage.objEnterMpinVal(ch1),
					Utilities.getTextVal(MLWalletSettinsPage.objEnterMpinVal(ch1), "MPIN"));
		}
		logger.info(validationText + " MPIN " + mPin + " Successfully");
		ExtentReporter.extentLogger("Enter MPIN", "" + validationText + " MPIN " + mPin + " Successfully");
	}



//=================================== Buy e - load ======================================================//
//==================================== Generalized methods ============================================//


	public void eLoad_generic(String typeOfAccount,String mobileNo, String status, int telcoOption) throws Exception
	{
		mlWalletLogin(typeOfAccount);
		Utilities.click(MLWalletEloadPage.objEloadTab, "Buy eLoad");

		if(status.equals("true")) {
			Utilities.verifyElementPresent(MLWalletEloadPage.objEloadtransactionPage, "eLoad Transaction Page");
			Utilities.click(MLWalletEloadPage.telcoOptions(telcoOption), Utilities.getTextVal(MLWalletEloadPage.telcoOptions(telcoOption), "Tab"));
		}
		Utilities.click(MLWalletEloadPage.objMobileNoField, "Mobile Number Field");
		Utilities.type(MLWalletEloadPage.objMobileNoField, mobileNo, "Mobile Number Field");
		Utilities.hideKeyboard();
		Utilities.click(MLWalletEloadPage.objNextBtn, "Next Button");
		Utilities.click(MLWalletEloadPage.objNextBtn, "Next Button");
		enableLocation_PopUp();
	}
//===================================================================================================//



	public void buying_eLoad(String accountType,int promotab) throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad Validation");
		eLoad_generic(accountType,prop.getproperty("sunMobileNumber"), "true", promotab);
		Utilities.verifyElementPresent(MLWalletEloadPage.objLoadSelectionPage, "Load Selection Page");
		Utilities.click(MLWalletEloadPage.objPromoLoadTab, "Promo Load Tab");
		Utilities.waitTime(5000);
		Utilities.click(MLWalletEloadPage.objTransaction, Utilities.getTextVal(MLWalletEloadPage.objTransaction, "Promo"));
		Utilities.verifyElementPresent(MLWalletEloadPage.objContinuePromoPopUp, Utilities.getTextVal(MLWalletEloadPage.objContinuePromoPopUp, "Pop Up"));
		Utilities.click(MLWalletEloadPage.objConfirmBtn, "Confirm Button");
		Utilities.waitTime(4000);
		Utilities.verifyElementPresent(MLWalletEloadPage.objTransactionDetailsPage, Utilities.getTextVal(MLWalletEloadPage.objTransactionDetailsPage, "Page"));
		Utilities.verifyElementPresent(MLWalletEloadPage.objTypeOfPromoUsed, Utilities.getTextVal(MLWalletEloadPage.objTypeOfPromoUsed, "Promo"));
		Utilities.verifyElementPresent(MLWalletEloadPage.objMobileNumber, Utilities.getTextVal(MLWalletEloadPage.objMobileNumber, "Mobile Number"));
		Utilities.waitTime(2000);
		Utilities.verifyElementPresent(MLWalletEloadPage.objAmountToSend, Utilities.getTextVal(MLWalletEloadPage.objAmountToSend, "Amount to Send"));
		Utilities.verifyElementPresent(MLWalletEloadPage.objServiceFee, Utilities.getTextVal(MLWalletEloadPage.objServiceFee, "Service Fee"));
		Utilities.verifyElementPresent(MLWalletEloadPage.objTotalAmount, Utilities.getTextVal(MLWalletEloadPage.objTotalAmount, "Total Amount"));
		Utilities.click(MLWalletEloadPage.objConfirmBtn, "Confirm Button");
		logger.info("'BE_TC_01', To verify buying eLoad validated ");
		ExtentReporter.extentLoggerPass("BE_TC_01", "'BE_TC_01', To verify buying eLoad validated ");
	}


	public void buying_eload_Invalid_Mob_Number() throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad using invalid mobile number");
		eLoad_generic(prop.getproperty("Fully_Verified"),prop.getproperty("inValidMobNumber"),"true", 3);
		Utilities.verifyElementPresent(MLWalletEloadPage.objErrorMsg, Utilities.getTextVal(MLWalletEloadPage.objErrorMsg, "Pop Up Message"));
		logger.info("'BE_TC_02', To verify buying eLoad using invalid mobile number input validated");
		ExtentReporter.extentLoggerPass("BE_TC_02", "'BE_TC_02', To verify buying eLoad using invalid mobile number input validated ");
	}

	public void buying_eload_without_input_Mob_Number() throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad without mobile number input");
		eLoad_generic(prop.getproperty("Fully_Verified"),"", "true", 3);
		Utilities.verifyElementPresent(MLWalletEloadPage.objErrorMsg, Utilities.getTextVal(MLWalletEloadPage.objErrorMsg, "Pop Up Message"));
		logger.info("'BE_TC_03', To verify buying eLoad without mobile number input validated");
		ExtentReporter.extentLoggerPass("BE_TC_03", "'BE_TC_03', To verify buying eLoad without mobile number input validated");
	}

	public void buying_eload_without_telecommunication_Selected() throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad without telecommunication selected");
		eLoad_generic(prop.getproperty("Fully_Verified"),prop.getproperty("validMobileNumber"),"false", 3);
		Utilities.waitTime(2000);
		Utilities.verifyElementPresent(MLWalletEloadPage.objErrorMsg, Utilities.getTextVal(MLWalletEloadPage.objErrorMsg, "Pop Up Message"));
		logger.info("'BE_TC_04', To verify buying eLoad without telecommunication selected validated");
		ExtentReporter.extentLoggerPass("BE_TC_04", "'BE_TC_04', To verify buying eLoad without telecommunication selected validated");
	}

	public void buying_eload_insufficient_balance(int  promotab) throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad with insufficient balance");
		buying_eLoad(prop.getproperty("Buyer_Tier"),promotab);
		handleMpin(prop.getproperty("MPin"), "Entered");
		Utilities.waitTime(2000);
		Utilities.verifyElementPresent(MLWalletEloadPage.objInsufficientBalPopup, Utilities.getTextVal(MLWalletEloadPage.objInsufficientBalPopup, "Pop up"));
		Utilities.click(MLWalletEloadPage.objOkBtn, "OK Button");
	}

	public void buying_eload_dailyMaximum_Limit() throws Exception
	{
		ExtentReporter.HeaderChildNode("Buying eLoad with daily maximum purchase limit");
		buying_eLoad(prop.getproperty("Branch_Verified"),3);
		handleMpin(prop.getproperty("MPin"), "Entered");
		Utilities.waitTime(2000);
	}

//=========================================== Cash In  Via Branch ================================//

	public void mlWalletCashInviaBranch_ValidAmount_Scenario() throws Exception {
		ExtentReporter.HeaderChildNode("ML_Wallet_Cash_In_Via_Barnch_ValidAmount_Scenario");
		mlWalletCashInviaBranch_Generic_Steps(prop.getproperty("Amount"));
		Utilities.waitTime(2000);
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objWarningPopup,
				Utilities.getTextVal(MLWalletCashInViaBranch.objWarningPopup, "Pop Up"));
		Utilities.click(MLWalletCashInViaBranch.objContinueButton, "Continue Button");
		enableLocation_PopUp();
		Utilities.getTextVal(MLWalletCashInViaBranch.objkptnId, "KPTN Id");
		Utilities.click(MLWalletCashInViaBranch.objCrossBtn, "Cross Button");
		logger.info("'CIBR_TC_01', To validate valid Amount in Cash In ML Branch ");
		ExtentReporter.extentLoggerPass("'CIBR_TC_01", "'CIBR_TC_01', To validate valid Amount in Cash In ML Branch ");
		mlWalletCashInviaBranch_CancelTransaction_Scenarion();
	}

	public void mlWalletCashInviaBranch_CancelTransaction_Scenarion() throws Exception {
		// ExtentReporter.HeaderChildNode("ML_Wallet_Cash_In_Via_Barnch_CancelTransaction_Scenario");
		mlWalletCashInviaBranch_Generic_Steps(prop.getproperty("Amount"));
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objWarningPopup,Utilities.getTextVal(MLWalletCashInViaBranch.objWarningPopup, "Pop Up"));
		Utilities.click(MLWalletCashInViaBranch.objContinueButton, "Continue Button");
		enableLocation_PopUp();
		Utilities.getTextVal(MLWalletCashInViaBranch.objkptnId, "KPTN Id");
		Utilities.click(MLWalletCashInViaBranch.objCancelTransactionBtn, "Cancel Transaction");
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objCanceLTransactionPopup,Utilities.getTextVal(MLWalletCashInViaBranch.objCanceLTransactionPopup, "Pop up"));
		Utilities.click(MLWalletCashInViaBranch.objCancelBtn1, "Cancel Transaction");
		Utilities.getTextVal(MLWalletCashInViaBranch.objTransactionCancelSuccessfulMsg, "Message");
		Utilities.click(MLWalletCashInViaBranch.objCrossBtn, "Cross Button");
		logger.info("'CIBR_TC_02', To validate Cancel Transaction in Cash In ML Branch");
		ExtentReporter.extentLoggerPass("'CIBR_TC_02","'CIBR_TC_02', To validate Cancel Transaction in Cash In ML Branch");
	}

	public void mlWalletCashInviaBranch_Invalid_Amount() throws Exception {
		mlWalletCashInviaBranch_Generic_Steps("0");
		logger.info("'CIBR_TC_03', 'CIBR_TC_03' To validate Invalid Amount");
		ExtentReporter.extentLoggerPass("CIBR_TC_03", "'CIBR_TC_03', To validate Invalid Amount");
	}


	public void mlWalletCashInviaBranch_Maximum_Limit_Amount() throws Exception {
		mlWalletCashInviaBranch_Generic_Steps("50001");
		logger.info("'CIBR_TC_04', 'CIBR_TC_04' To validate Maximum Limit of transaction");
		ExtentReporter.extentLoggerPass("CIBR_TC_04", "'CIBR_TC_04', To validate Maximum Limit of transaction");
	}

	public void mlWalletCashInviaBranch_Generic_Steps(String amount) throws Exception {
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashInViaBranch.objCashInMenu, "Cash In");
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objBranchName, "Cash In Options Page");
		Utilities.click(MLWalletCashInViaBranch.objBranchName, "ML Branch");
		Utilities.verifyElementPresent(MLWalletCashInViaBranch.objBranchCashInPage, "Branch Cash In Page");
		Thread.sleep(2000);
		Utilities.click(MLWalletCashInViaBranch.objAmountTextField, "Amount Text Field");
		Utilities.type(MLWalletCashInViaBranch.objAmountTextField, amount, "Amount Text Field");
		Utilities.hideKeyboard();
		Utilities.Swipe("up", 1);
		Utilities.click(MLWalletCashInViaBranch.objNextButton, "Next Button");
	}









}







