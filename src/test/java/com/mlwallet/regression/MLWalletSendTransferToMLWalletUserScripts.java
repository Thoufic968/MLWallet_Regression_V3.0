package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletSendTransferToMLWalletUserScripts {

    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletSendTransferToMLWalletUserScripts.deviceName=deviceName;
        MLWalletSendTransferToMLWalletUserScripts.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }


//===========================================================================================================//

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



}
