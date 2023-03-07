package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletSendTransferToAnyMLBranchScripts {

    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletSendTransferToAnyMLBranchScripts.deviceName=deviceName;
        MLWalletSendTransferToAnyMLBranchScripts.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }

//============================================================================================//



    @Test(priority = 1)
    public void sendMoneyToMLBranch() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyToMLBranch_STB_TC_01();
    }

    @Test(priority = 2)
    public void sendMoneyAddRecipient() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyAddRecipient_STB_TC_03();
    }

    @Test(priority = 3)
    public void sendMoneyContactDuplicate() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyContactDuplicate_STB_TC_04();
    }

    @Test(priority = 4)
    public void sendMoneyToSavedRecipient() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyToSavedRecipient_STB_TC_02();
    }

    @Test(priority = 5)
    public void sendMoneyEditRecipient() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyEditRecipient_STB_TC_06();
    }
    @Test(priority = 6)
    public void sendMoneyDeleteRecipient() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyDeleteRecipient_STB_TC_05();
    }

    @Test(priority = 7)
    public void sendMoneyInvalidAmount() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyInvalidAmount_STB_TC_09("0");
    }

    @Test(priority = 8)
    public void sendMoneyInsufficientAmount() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyInsufficientAmount_STB_TC_10();
    }

    @Test(priority = 9)
    public void sendMoneyMaximumAmount() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyMaximumAmount_STB_TC_12();
    }

    @Test(priority = 10)
    public void sendMoneyRequiredDetails() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyRequiredDetails_STB_TC_08();
    }

    @Test(priority = 11)
    public void sendMoneyInvalidDetails() throws Exception
    {
        MLWalletBusinessLogic.sendMoneyInvalidDetails_STB_TC_07();
    }

}
