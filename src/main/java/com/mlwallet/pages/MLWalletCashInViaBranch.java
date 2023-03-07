package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletCashInViaBranch {
	
	public static By objCashInMenu=By.xpath("//*[@text='Cash In']");
	public static By objCashInOptionPage=By.xpath("//*[@text='Cash In options']");
	
	public static By objBranchName=By.xpath("//*[@text='ML Branch']");
	public static By objBranchCashInPage=By.xpath("//*[@text='Branch Cash In']");
	public static By objAmountTextField=By.xpath("//*[@text='PHP']/following-sibling::android.widget.EditText");
	public static By objNextButton=By.xpath("//*[@text='Next']");
	public static By objWarningPopup=By.xpath("(//*[android.view.ViewGroup]/child::android.widget.TextView)[2]");
	public static By objContinueButton=By.xpath("//*[@text='Continue']");
	public static By objkptnId=By.xpath("//*[@resource-id='transaction-code']");
	public static By objCrossBtn=By.xpath("//*[@text='Cash In']/preceding-sibling::android.view.ViewGroup/child::android.widget.TextView");
	public static By objCancelTransactionBtn=By.xpath("//*[@resource-id='cancel-transaction-button']");
	public static By objCanceLTransactionPopup=By.xpath("//*[@text='Would you like cancel Transaction?']");
	public static By objCancelBtn1=By.xpath("//*[@text='Cancel Transaction']");
	public static By objTransactionCancelSuccessfulMsg=By.xpath("//*[@resource-id='badge-text']");
	//Device Location
	public static By objLocationPopup=By.xpath("//*[@text='Allow ML Wallet to access this device’s location?']");
	public static By objAllowButton=By.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_allow_foreground_only_button']");
}
