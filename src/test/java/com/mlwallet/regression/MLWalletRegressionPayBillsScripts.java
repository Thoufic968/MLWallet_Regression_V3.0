package com.mlwallet.regression;

import com.business.mlwallet.MLWalletBusinessLogic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MLWalletRegressionPayBillsScripts {

    public static String deviceName;
    public static String portno;
    public  static com.business.mlwallet.MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    public void before(String deviceName,String portno) throws Exception {
        MLWalletRegressionPayBillsScripts.deviceName=deviceName;
        MLWalletRegressionPayBillsScripts.portno= portno;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }


//============================== Pay Bills Scripts==========================================================//


    @Test(priority = 1)
    public void payBillsValidation() throws Exception {
        MLWalletBusinessLogic.payBillsPageValidation_PB_TC_01();
    }

    @Test(priority = 2)
    public void billerCategoriesValidation() throws Exception {
        MLWalletBusinessLogic.billerCategories_PB_TC_02();
    }

    @Test(priority = 3)
    public void billersInAlphabeticalOrder() throws Exception {
        MLWalletBusinessLogic.billersInAlphabeticalOrder_PB_TC_03();
    }

    @Test(priority = 4)
    public void selectBiller() throws Exception {
        MLWalletBusinessLogic.selectBiller_PB_TC_04();
    }

    @Test(priority = 5)
    public void searchBiller() throws Exception {
        MLWalletBusinessLogic.searchBiller_PB_TC_05();
    }

    @Test(priority = 6)
    public void billingInformationInput() throws Exception {
        MLWalletBusinessLogic.billingInformationInput_PB_TC_06();
    }

    @Test(priority = 7)
    public void billingInformationInvalidInput() throws Exception {
        MLWalletBusinessLogic.billingInformationInvalidInput_PB_TC_10();
    }

//   @Test(priority = 8)
//    public void payBillsWithValidInputs() throws Exception {
//        MLWalletBusinessLogic.payBillsWithValidInputs();
//    }

//   @Test(priority = 9)
//    public void addBillerToPayBills() throws Exception {
//        MLWalletBusinessLogic.addBillerToPayBills();
//    }

    @Test(priority = 10)
    public void addBillerInvalidInputs() throws Exception {
        MLWalletBusinessLogic.addBillerInvalidInputs();
    }


}
