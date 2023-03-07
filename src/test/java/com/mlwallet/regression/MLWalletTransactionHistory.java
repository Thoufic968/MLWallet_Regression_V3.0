package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletTransactionHistory {
    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletTransactionHistory.deviceName=deviceName;
        MLWalletTransactionHistory.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }
//===================================================================================================//


    @Test(priority = 1)
    public void mlWalletTransactionHistory_Scenario() throws Exception
    {
        MLWalletBusinessLogic.mlWallet_TransactionHistory();
    }



}
