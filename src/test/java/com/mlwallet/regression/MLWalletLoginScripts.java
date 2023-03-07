package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletLoginScripts {
    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletLoginScripts.deviceName=deviceName;
        MLWalletLoginScripts.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }

//====================================================================================================//

    @Test(priority = 1)
    public void LogInScenarioWithValidMobNumber() throws Exception
    {
        MLWalletBusinessLogic.LogInScenarioWithValidMobNumber_Lgn_TC_01();
    }
    @Test(priority = 2)
    public void LogInScenarioWithInvalidMobNumber() throws Exception {
        MLWalletBusinessLogic.LogInScenarioWithInvalidMobNumber_Lgn_TC_02();
    }
    @Test(priority = 3)
    public void LogInScenarioWithValidOTP() throws Exception {
        MLWalletBusinessLogic.LogInScenarioWithValidOTP();
    }
}
