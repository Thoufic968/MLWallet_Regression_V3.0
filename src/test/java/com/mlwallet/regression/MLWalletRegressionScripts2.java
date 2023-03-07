package com.mlwallet.regression;

import com.utility.Utilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.business.mlwallet.MLWalletBusinessLogic;

public class MLWalletRegressionScripts2 {

    public  MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    //@BeforeSuite
    public void beforeMethod(String deviceName,String portno) throws Exception {
        Utilities.relaunch = true;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }


	@Test(priority = 1)
	public void sendToMLWalletUser() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletUser_STW_TC_01();
	}

	@Test(priority = 2)
	public void sendMoneyMLWalletToExistingReceiver() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyMLWalletToExistingReceiver_STW_TC_02();

	}

	@Test(priority = 3)
	public void sendToMLWalletInvalidMobNumber() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletInvalidMobNumber();
	}


	@Test(priority = 4)
	public void sendToMLWalletUnRegisteredNumber() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletUnRegisteredNumber();
	}

	@Test(priority = 5)
	public void sendToMLWalletInvalidAmount() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletInvalidAmount("0");
	}

	@Test(priority = 6)
	public void sendToMLWalletInsufficientAmount() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletInsufficientAmount();
	}

	@Test(priority = 7)
	public void sendMoneyMLWalletMaximumAmount() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyMLWalletMaximumAmount();
	}

	@Test(priority = 8)
	public void sendMoneyDeleteFromFavorites() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyDeleteFromFavorites();
	}

	@Test(priority = 9)
	public void mlWalletTransactionHistory_Scenario() throws Exception
	{
		MLWalletBusinessLogic.mlWallet_TransactionHistory();
	}

	@Test(priority = 10)
	public void mlWallet_ShopItems_without_Input_Otp() throws Exception
	{
		MLWalletBusinessLogic.mlWallet_ShopItems_without_Input_Otp();

	}

	@Test(priority = 11)
	public void mlWallet_ShopItems_with_Insufficient_Balance() throws Exception {
		MLWalletBusinessLogic.mlWallet_ShopItems_with_Insufficient_Balance();
	}


}
