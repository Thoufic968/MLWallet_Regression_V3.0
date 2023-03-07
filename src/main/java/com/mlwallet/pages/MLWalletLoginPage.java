package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletLoginPage {
	
	public static By objMobileNumberTextField=By.xpath("//*[android.view.ViewGroup]/descendant::android.widget.EditText");

//	public static By objMobileNumberTextField=By.xpath("//*[@resource-id='967123']");

	public static By objLoginBtn=By.xpath("//*[@resource-id='208465']");
	public static By objOtpTextField=By.xpath("//*[@resource-id='textInput']");

	public static By objAvailableBalance = By.xpath("//*[@resource-id='UTJ9TN']");

	public static By objInvalidMobNumberTxt = By.xpath("//*[@text='Mobile number is invalid']");

	public static By objOneTimePin = By.xpath("(//*[@text='One Time Pin'])[1]");

	public static By objMLPin = By.xpath("//*[@text='ML Pin']");

	public static By objMLPinEditField(int i) {
		return  By.xpath("((//*[@class='android.view.ViewGroup' and ./parent::*[@class='android.widget.ScrollView']]/*/*/*[@class='android.view.ViewGroup' and ./parent::*[@class='android.view.ViewGroup' and ./parent::*[@class='android.widget.ScrollView']]])[1]/*[@class='android.view.ViewGroup'])["+i+"]");
	}

	public static By objOneBtn = By.xpath("//*[@text='1']");

	public static By objLocationPopup=By.xpath("//*[@text='Allow ML Wallet to access this device’s location?']");

}
