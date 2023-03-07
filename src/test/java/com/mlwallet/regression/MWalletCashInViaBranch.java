package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MWalletCashInViaBranch {
    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MWalletCashInViaBranch.deviceName=deviceName;
        MWalletCashInViaBranch.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }

//======================================================================================================//


    //******************* Cash In Via Branch ====================================/

    @Test(priority = 1)
    public void mlwallet_cashInViaBranch_validAmount() throws Exception
    {
        MLWalletBusinessLogic.mlWalletCashInviaBranch_ValidAmount_Scenario();
    }

    @Test(priority = 2)
    public void mlwallet_cashInViaBranch_cancel_transaction() throws Exception
    {
        MLWalletBusinessLogic.mlWalletCashInviaBranch_CancelTransaction_Scenarion();
    }

    @Test(priority = 3)
    public void mlwallet_cashInViaBranch_invalid_amount() throws Exception
    {
        MLWalletBusinessLogic.mlWalletCashInviaBranch_Invalid_Amount();
    }

    @Test(priority = 4)
    public void mlwallet_cashInViaBranch_maximum_limit_transaction() throws Exception
    {
        MLWalletBusinessLogic.mlWalletCashInviaBranch_Maximum_Limit_Amount();
    }

}
