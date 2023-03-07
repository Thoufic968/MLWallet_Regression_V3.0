package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletCashOutWithdrawScripts {
    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletCashOutWithdrawScripts.deviceName=deviceName;
        MLWalletCashOutWithdrawScripts.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }

//============================================================================================================//


    @Test(priority = 1)
    public void cashOutWithdrawBank() throws Exception
    {
        MLWalletBusinessLogic.cashOutWithdrawBank("100");
    }

    @Test(priority = 2)
    public void cashOutWithInvalidAccNumber() throws Exception
    {
        MLWalletBusinessLogic.cashOutWithInvalidAccNumber();
    }

    @Test(priority = 3)
    public void cashOutWithdrawBankMaxAmount() throws Exception
    {
        MLWalletBusinessLogic.cashOutWithdrawBankMaxAmount("60000");
    }

    @Test(priority = 4)
    public void cashOutWithdrawMinTransactionLimit() throws Exception
    {
        MLWalletBusinessLogic.cashOutWithdrawMinTransactionLimit("10");
    }

    @Test(priority = 5)
    public void cashOutWithdrawBranch() throws Exception
    {
        MLWalletBusinessLogic.cashOutWithdrawBranch();
    }

    @Test(priority = 6)
    public void cashOutMaxLimit() throws Exception
    {
        MLWalletBusinessLogic.cashOutMaxLimit();
    }

    @Test(priority = 7)
    public void cashOutInsufficientBalance() throws Exception {
        MLWalletBusinessLogic.cashOutInsufficientBalance();
    }

    @Test(priority = 8)
    public void cashOutBuyerTierLevelAcc() throws Exception
    {
        MLWalletBusinessLogic.cashOutBuyerTierLevelAcc();
    }




}
