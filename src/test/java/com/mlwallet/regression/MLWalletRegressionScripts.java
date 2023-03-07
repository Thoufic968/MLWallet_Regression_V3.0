package com.mlwallet.regression;


import com.driverInstance.DriverManager;
import com.utility.Utilities;
import org.testng.annotations.*;
import com.business.mlwallet.MLWalletBusinessLogic;


public class MLWalletRegressionScripts {

	public static String deviceName;
	public static String portno;
	public  static MLWalletBusinessLogic MLWalletBusinessLogic;
	
	
	//@BeforeSuite(groups = { "All" })
	 @Parameters({"deviceName","portno"})
	 @BeforeMethod
	public void before(String deviceName,String portno) throws Exception {
		MLWalletRegressionScripts.deviceName=deviceName;
		MLWalletRegressionScripts.portno= portno;
		 MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
	 }


	@Test(priority = 0)
	public void LogInScenarioWithValidMobNumber() throws Exception
	{
		MLWalletBusinessLogic.LogInScenarioWithValidMobNumber_Lgn_TC_01();
	}
	@Test(priority = 1)
	public void LogInScenarioWithInvalidMobNumber() throws Exception {
		MLWalletBusinessLogic.LogInScenarioWithInvalidMobNumber_Lgn_TC_02();
	}
	@Test(priority = 2)
	public void LogInScenarioWithValidOTP() throws Exception {
		 MLWalletBusinessLogic.LogInScenarioWithValidOTP();
	}

//===================================== CashOut/Withdraw =======================================================//
	@Test(priority = 3)
	public void cashOutWithdrawBank() throws Exception
	{
		MLWalletBusinessLogic.cashOutWithdrawBank("100");
	}

	@Test(priority = 4)
	public void cashOutWithInvalidAccNumber() throws Exception
	{
		MLWalletBusinessLogic.cashOutWithInvalidAccNumber();
	}

	@Test(priority = 5)
	public void cashOutWithdrawBankMaxAmount() throws Exception
	{
		MLWalletBusinessLogic.cashOutWithdrawBankMaxAmount("60000");
	}

	@Test(priority = 6)
	public void cashOutWithdrawMinTransactionLimit() throws Exception
	{
		MLWalletBusinessLogic.cashOutWithdrawMinTransactionLimit("10");
	}

	@Test(priority = 7)
	public void cashOutWithdrawBranch() throws Exception
	{
		MLWalletBusinessLogic.cashOutWithdrawBranch();
	}

	@Test(priority = 8)
	public void cashOutMaxLimit() throws Exception
	{
		MLWalletBusinessLogic.cashOutMaxLimit();
	}

	@Test(priority = 9)
	public void cashOutBuyerTierLevelAcc() throws Exception
	{
		MLWalletBusinessLogic.cashOutBuyerTierLevelAcc();
	}


//========================================================================================================================

	@Test(priority = 10)
	public void sendMoneyToMLBranch() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyToMLBranch_STB_TC_01();
	}

	@Test(priority = 11)
	public void sendMoneyAddRecipient() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyAddRecipient_STB_TC_03();
	}

	@Test(priority = 12)
	public void sendMoneyContactDuplicate() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyContactDuplicate_STB_TC_04();
	}

	@Test(priority = 13)
	public void sendMoneyToSavedRecipient() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyToSavedRecipient_STB_TC_02();
	}

	@Test(priority = 14)
	public void sendMoneyEditRecipient() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyEditRecipient_STB_TC_06();
	}
	@Test(priority = 15)
	public void sendMoneyDeleteRecipient() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyDeleteRecipient_STB_TC_05();
	}

	@Test(priority = 16)
	public void sendMoneyInvalidAmount() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyInvalidAmount_STB_TC_09("0");
	}

	@Test(priority = 17)
	public void sendMoneyInsufficientAmount() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyInsufficientAmount_STB_TC_10();
	}

	@Test(priority = 18)
	public void sendMoneyMaximumAmount() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyMaximumAmount_STB_TC_12();
	}

	@Test(priority = 19)
	public void sendMoneyRequiredDetails() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyRequiredDetails_STB_TC_08();
	}

	@Test(priority = 20)
	public void sendMoneyInvalidDetails() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyInvalidDetails_STB_TC_07();
	}

////============================================================================================================


	@Test(priority = 21)
	public void sendToMLWalletUser() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletUser_STW_TC_01();
	}

	@Test(priority = 22)
	public void sendMoneyMLWalletToExistingReceiver() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyMLWalletToExistingReceiver_STW_TC_02();

	}

	@Test(priority = 23)
	public void sendToMLWalletInvalidMobNumber() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletInvalidMobNumber();
	}


	@Test(priority = 24)
	public void sendToMLWalletUnRegisteredNumber() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletUnRegisteredNumber();
	}

	@Test(priority = 25)
	public void sendToMLWalletInvalidAmount() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletInvalidAmount("0");
	}

	@Test(priority = 26)
	public void sendToMLWalletInsufficientAmount() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletInsufficientAmount();
	}

	@Test(priority = 27)
	public void sendMoneyMLWalletMaximumAmount() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyMLWalletMaximumAmount();
	}

	@Test(priority = 28)
	public void sendMoneyDeleteFromFavorites() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyDeleteFromFavorites();
	}
//
	@Test(priority = 29)
	public void mlWalletTransactionHistory_Scenario() throws Exception
	{
		MLWalletBusinessLogic.mlWallet_TransactionHistory();
	}

	@Test(priority = 30)
	public void mlWalletShopItems_Scenario() throws Exception
	{
		MLWalletBusinessLogic.mlWallet_ShopItems_without_Input_Otp();

	}

	@Test(priority = 31)
	public void mlWallet_ShopItems_with_Insufficient_Balance() throws Exception {
		MLWalletBusinessLogic.mlWallet_ShopItems_with_Insufficient_Balance();
	}


//==================================================================================================================//


	@Test(priority = 32)
	public void cashInViaBank() throws Exception {
		MLWalletBusinessLogic.cashInViaBank_CIBA_TC_01();
	}

	@Test(priority = 33)
	public void cashInViaBankMinimumTransactionLimit() throws Exception {
		MLWalletBusinessLogic.cashInViaBankMinimumTransactionLimit_CIBA_TC_03();
	}

	@Test(priority = 34)
	public void cashInViaBankMaximumTransaction() throws Exception {
		MLWalletBusinessLogic.cashInViaBankMaximumTransaction();
	}
//===============================================================================================================//
	@Test(priority = 35)
	public void payBillsValidation() throws Exception {
		MLWalletBusinessLogic.payBillsPageValidation_PB_TC_01();
	}

	@Test(priority = 36)
	public void billerCategoriesValidation() throws Exception {
		MLWalletBusinessLogic.billerCategories_PB_TC_02();
	}

	@Test(priority = 37)
	public void billersInAlphabeticalOrder() throws Exception {
		MLWalletBusinessLogic.billersInAlphabeticalOrder_PB_TC_03();
	}

	@Test(priority = 38)
	public void selectBiller() throws Exception {
		MLWalletBusinessLogic.selectBiller_PB_TC_04();
	}

	@Test(priority = 39)
	public void searchBiller() throws Exception {
		MLWalletBusinessLogic.searchBiller_PB_TC_05();
	}

	@Test(priority = 40)
	public void billingInformationInput() throws Exception {
		MLWalletBusinessLogic.billingInformationInput_PB_TC_06();
	}


	@Test(priority = 41)
	public void billingInformationInvalidInput() throws Exception {
		MLWalletBusinessLogic.billingInformationInvalidInput_PB_TC_10();
	}

//   @Test(priority = 42)
//    public void payBillsWithValidInputs() throws Exception {
//        MLWalletBusinessLogic.payBillsWithValidInputs();
//    }
//
//   @Test(priority = 43)
//    public void addBillerToPayBills() throws Exception {
//        MLWalletBusinessLogic.addBillerToPayBills();
//    }

	@Test(priority = 44)
	public void addBillerInvalidInputs() throws Exception {
		MLWalletBusinessLogic.addBillerInvalidInputs();
	}
































//	@Test(priority = 1)
//	public void mlWalletCashOutWithdraw() throws Exception
//	{
//		MLWalletBusinessLogic.cashOutWithdrawBank("100");
//		MLWalletBusinessLogic.cashOutWithInvalidAccNumber();
//		MLWalletBusinessLogic.cashOutWithdrawBankMaxAmount("60000");
//		MLWalletBusinessLogic.cashOutWithdrawMinTransactionLimit("10");
//		MLWalletBusinessLogic.cashOutWithdrawBranch("10");
//		MLWalletBusinessLogic.cashOutMaxLimit("40000");
//		MLWalletBusinessLogic.cashOutBuyerTierLevelAcc("100");
//	}
//
//
//	@Test(priority = 2)
//	public void mlWalletSendMoneyToMLBranch() throws Exception
//	{
//		MLWalletBusinessLogic.sendMoneyToMLBranch("100");
//		MLWalletBusinessLogic.sendMoneyAddRecipient();
//		MLWalletBusinessLogic.sendMoneyContactDuplicate();
//		MLWalletBusinessLogic.sendMoneyToSavedRecipient("100");
//		MLWalletBusinessLogic.sendMoneyEditRecipient();
//		MLWalletBusinessLogic.sendMoneyDeleteRecipient();
//		MLWalletBusinessLogic.sendMoneyInvalidAmount("0");
//		MLWalletBusinessLogic.sendMoneyInsufficientAmount("10000");
//		MLWalletBusinessLogic.sendMoneyMaximumAmount("100000");
//		MLWalletBusinessLogic.sendMoneyRequiredDetails();
//		MLWalletBusinessLogic.sendMoneyInvalidDetails();
//	}
//
//
////	@Test(priority = 3)
//	public void mlWalletSendMoneyToWallet() throws Exception
//	{
//		MLWalletBusinessLogic.sendToMLWalletUser("10");
//		MLWalletBusinessLogic.sendMoneyMLWalletToExistingReceiver("10");
//		MLWalletBusinessLogic.sendToMLWalletInvalidMobNumber();
//		MLWalletBusinessLogic.sendToMLWalletUnRegisteredNumber();
//		MLWalletBusinessLogic.sendToMLWalletInvalidAmount("0");
//		MLWalletBusinessLogic.sendToMLWalletInsufficientAmount("30000");
//		MLWalletBusinessLogic.sendMoneyMLWalletMaximumAmount("100000");
//		MLWalletBusinessLogic.sendMoneyDeleteFromFavorites();
//
//	}










//	@Test
//	public void mlWalletLoginScenario1() throws Exception
//	{
//		MLWalletrBusinessLogic.mlWalletLoginScenario1();
//	}
	
	


//	  @AfterMethod
//	  public synchronized void tearDown() {
//		 MLWalletBusinessLogic.tearDown();
//
//	 }


}