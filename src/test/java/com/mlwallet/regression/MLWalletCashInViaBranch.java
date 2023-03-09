package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletCashInViaBranch {
    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletCashInViaBranch.deviceName=deviceName;
        MLWalletCashInViaBranch.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }

//======================================================================================================//


    //******************* Cash In Via Branch ====================================/

    @Test(priority = 1)
    public void mlWallet_cashInViaBranch_validAmount() throws Exception
    {
        MLWalletBusinessLogic.mlWalletCashInviaBranch_ValidAmount_Scenario();
    }

    @Test(priority = 2)
    public void mlWallet_cashInViaBranch_cancel_transaction() throws Exception
    {
        MLWalletBusinessLogic.mlWalletCashInViaBranchCancelTransactionScenario();
    }

    @Test(priority = 3)
    public void mlWallet_cashInViaBranch_invalid_amount() throws Exception
    {
        MLWalletBusinessLogic.mlWalletCashInviaBranch_Invalid_Amount();
    }

    @Test(priority = 4)
    public void mlWallet_cashInViaBranch_maximum_limit_transaction() throws Exception
    {
        MLWalletBusinessLogic.mlWalletCashInviaBranch_Maximum_Limit_Amount();
    }

}
