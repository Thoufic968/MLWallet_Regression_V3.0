package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletTransactionHistoryPage {
	
	public static By objRecentTransaction=By.xpath("//*[@text='Recent Transactions']");
	public static By objSeeMoreBtn=By.xpath("//*[@text='See More']");

	public static By objTransactionHistory = By.xpath("//*[@text='Transaction History']");
	
	public static By objBillsPayTab=By.xpath("//*[@text='Billspay']");
	public static By objeLoadTab=By.xpath("//*[@text='eLoad']");
	public static By objSendMoneyTab=By.xpath("//*[@text='Send Money']");
	public static By objCashInTab=By.xpath("//*[@text='Cash In']");
	public static By objCashOutTab=By.xpath("//*[@text='Cash Out']");
	public static By objReceiveMoneyTab=By.xpath("//*[@text='Receive Money']");
	public static By objBalanceAdjustmentTab=By.xpath("//*[@text='Balance Adjustment']");
	public static By objMlShopTab=By.xpath("//*[@text='ML Shop']");
//	public static By objPayBillsHistory=By.xpath("//*[@text='Pay Bills' or @text='No Recent Transaction']");
	//public static By objPayBillsTransctionList=By.xpath("//*[@text='Pay Bills']/(following-sibling::android.widget.TextView)[2]");
	
	
	
	public static By objPayBillsTransctionList1(String moduleName)
	{
	   return	By.xpath("//*[@text='"+moduleName+"']/parent::android.view.ViewGroup/following-sibling::android.view.ViewGroup/(child::android.widget.TextView)[1]");
	}
	public static By objBillHistory(String billModule,String transaction)
	{
		return By.xpath("//*[@text='"+billModule+"' or @text='"+transaction+"']");
	}

	public static By  objAllTab = By.xpath("//*[@text='All']");

	public static By objFirstTransaction = By.xpath("(//*[@class='android.view.ViewGroup' and ./parent::*[@class='android.widget.ScrollView']]/*/*/*/*[@class='android.view.ViewGroup' and ./*[./*[./*[@text]] and ./*[@text]]])[1]");

	public static By objTransactionDetails = By.xpath("//*[@text='Transaction Details']");

	public static By objReceiverMobileNo= By.xpath("(//*[@resource-id='Receiver Mobile No.'])[2]");
	public static By objPaymentMethod = By.xpath("(//*[@resource-id='Payment Method'])[2]");

	public static By objTransactionType = By.xpath("(//*[@resource-id='Transaction Type'])[2]");

	public static By objAmount = By.xpath("(//*[@resource-id='Amount to Send'])[2]");

	public static By objServiceFee = By.xpath("(//*[@resource-id='Service Fee'])[2]");

	public static By objTotalAmount = By.xpath("(//*[@resource-id='Total'])[2]");

	public static By objReferenceNumberInTransactionDetails = By.xpath("(//*[@resource-id='Reference Number'])[2]");

}
