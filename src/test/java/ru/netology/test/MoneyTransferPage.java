package ru.netology.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardsPage;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferPage {
    @BeforeEach
    void setUpAll() {
        open("http://0.0.0.0:7777/");
    }

    @Nested
    class TransferTest {
        LoginPage loginPage;
        VerificationPage verificationPage;
        DashboardPage dashboardPage;
        CardsPage cardsPage;
        DataHelper.AuthInfo authInfo;
        DataHelper.VerificationCode verificationCode;


        @Test
        void shouldTransferFromCard2() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            val dashboardPage = verificationPage.validVerify(verificationCode);
            val cardsPage = dashboardPage.getCardPage();
            int balanceCard1 = cardsPage.getCurrentBalance("5559 0000 0000 0001");
            int balanceCard2 = cardsPage.getCurrentBalance("5559 0000 0000 0002");
            val transferPage = cardsPage.CardsForTransfer("5559 0000 0000 0001");
            val amountToTransfer = DataHelper.Balance.getBalance(balanceCard2).getAmount();
            transferPage.shouldTransferFromCard2(amountToTransfer);
            int actual = cardsPage.getCurrentBalance("5559 0000 0000 0001");
            int expected = balanceCard1 + amountToTransfer;
            assertEquals(expected, actual);
        }

        @Test
        void shouldTransferFromCard1() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            val dashboardPage = verificationPage.validVerify(verificationCode);
            val cardsPage = dashboardPage.getCardPage();
            int balanceCard2 = cardsPage.getCurrentBalance("5559 0000 0000 0002");
            int balanceCard1 = cardsPage.getCurrentBalance("5559 0000 0000 0001");
            val transferPage = cardsPage.CardsForTransfer("5559 0000 0000 0002");
            val amountToTransfer = DataHelper.Balance.getBalance(balanceCard1).getAmount();
            transferPage.shouldTransferMoneyFromCard1(amountToTransfer);
            int actual = cardsPage.getCurrentBalance("5559 0000 0000 0002");
            int expected = balanceCard2 + amountToTransfer;
            assertEquals(expected, actual);
        }

        @Test
        void shouldNotTransferInvalidCard() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            val dashboardPage = verificationPage.validVerify(verificationCode);
            val cardsPage = dashboardPage.getCardPage();
            int balanceCard2 = cardsPage.getCurrentBalance("5559 0000 0000 0002");
            val transferPage = cardsPage.CardsForTransfer("5559 0000 0000 0002");
            val amountToTransfer = DataHelper.Balance.getBalance(balanceCard2).getAmount();
            transferPage.shouldNotTransferInvalidCard(amountToTransfer);
        }

        //bug
        @Test
        void shouldNotTransferFromCard1EmptyAmount() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            val dashboardPage = verificationPage.validVerify(verificationCode);
            val cardsPage = dashboardPage.getCardPage();
            int balanceCard2 = cardsPage.getCurrentBalance("5559 0000 0000 0002");
            int balanceCard1 = cardsPage.getCurrentBalance("5559 0000 0000 0001");
            val transferPage = cardsPage.CardsForTransfer("5559 0000 0000 0002");
            val amountToTransfer = DataHelper.Balance.getBalance(balanceCard2).getAmount();
            transferPage.shouldNotTransferFromCard1EmptyAmount();
        }

        @Test
        void shouldNotTransferEmptyCardNumber(){
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            val dashboardPage = verificationPage.validVerify(verificationCode);
            val cardsPage = dashboardPage.getCardPage();
            int balanceCard2 = cardsPage.getCurrentBalance("5559 0000 0000 0002");
            val transferPage = cardsPage.CardsForTransfer("5559 0000 0000 0001");
            val amountToTransfer = DataHelper.Balance.getBalance(balanceCard2).getAmount();
            transferPage.shouldNotTransferEmptyCardNumber(amountToTransfer);
        }

        //bug
        @Test
        void shouldNotTransferMoneyFromCard1AmountLessThanBalance(){
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            val dashboardPage = verificationPage.validVerify(verificationCode);
            val cardsPage = dashboardPage.getCardPage();
            int balanceCard2 = cardsPage.getCurrentBalance("5559 0000 0000 0002");
            int balanceCard1 = cardsPage.getCurrentBalance("5559 0000 0000 0001");
            val transferPage = cardsPage.CardsForTransfer("5559 0000 0000 0002");
            val amountToTransfer = DataHelper.Balance.getBalance(balanceCard1).getAmount();
            transferPage.shouldNotTransferMoneyFromCard1AmountLessThanBalance(amountToTransfer);
            $(withText("Недостаточно средств на карте")).waitUntil(Condition.visible, 15);
        }

        //bug
        @Test
        void shouldNotTransferMoneyFromCard2AmountLessThanBalance(){
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            val dashboardPage = verificationPage.validVerify(verificationCode);
            val cardsPage = dashboardPage.getCardPage();
            int balanceCard1 = cardsPage.getCurrentBalance("5559 0000 0000 0001");
            int balanceCard2 = cardsPage.getCurrentBalance("5559 0000 0000 0002");
            val transferPage = cardsPage.CardsForTransfer("5559 0000 0000 0001");
            val amountToTransfer = DataHelper.Balance.getBalance(balanceCard2).getAmount();
            transferPage.shouldNotTransferMoneyFromCard2AmountLessThanBalance(amountToTransfer);
        }

        //bug
        @Test
        void shouldNotTransferFromCard1ToCard1(){
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            val dashboardPage = verificationPage.validVerify(verificationCode);
            val cardsPage = dashboardPage.getCardPage();
            int balanceCard1 = cardsPage.getCurrentBalance("5559 0000 0000 0001");
            val transferPage = cardsPage.CardsForTransfer("5559 0000 0000 0001");
            val amountToTransfer = DataHelper.Balance.getBalance(balanceCard1).getAmount();
            transferPage.shouldNotTransferFromCard1ToCard1(amountToTransfer);
        }

        //bug
        @Test
        void shouldNotTransferFromCard2ToCard2() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            val dashboardPage = verificationPage.validVerify(verificationCode);
            val cardsPage = dashboardPage.getCardPage();
            int balanceCard2 = cardsPage.getCurrentBalance("5559 0000 0000 0002");
            val transferPage = cardsPage.CardsForTransfer("5559 0000 0000 0002");
            val amountToTransfer = DataHelper.Balance.getBalance(balanceCard2).getAmount();
            transferPage.shouldNotTransferFromCard2ToCard2(amountToTransfer);
        }
    }

    @Nested
    class VerificationCodeTest{
        LoginPage loginPage;
        VerificationPage verificationPage;
        DataHelper.AuthInfo authInfo;
        DataHelper.VerificationCode verificationCode;

        @Test
        void shouldSubmitRequestValidVerificationCode(){
            loginPage = new LoginPage();
            authInfo = DataHelper.getAuthInfo();
            verificationPage = loginPage.validLogin(authInfo);
            verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            verificationPage.validVerify(verificationCode);
        }

        @Test
        void shouldNotSubmitRequestWrongVerificationCode(){
            loginPage = new LoginPage();
            authInfo = DataHelper.getAuthInfo();
            verificationPage = loginPage.validLogin(authInfo);
            verificationCode = DataHelper.getWrongVerificationCode(authInfo);
            verificationPage.invalidVerify(verificationCode);
        }

        @Test
        void shouldNotSubmitRequestEmptyVerificationCode(){
            loginPage = new LoginPage();
            authInfo = DataHelper.getAuthInfo();
            verificationPage = loginPage.validLogin(authInfo);
            verificationPage.emptyVerifyField(verificationCode);
        }
    }

    @Nested
    class AuthorisationTest{

        @Test
        void shouldAuthorizeValidUser(){
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            loginPage.validLogin(authInfo);
        }

        @Test
        void shouldNotAuthorizeInvalidLogin(){
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getOtherAuthInfo();
            loginPage.validLogin(authInfo);
        }

        @Test
        void shouldNotAuthorizeEmptyLogin() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            loginPage.emptyLogin(authInfo);
        }

        @Test
        void shouldNotAuthorizeInvalidPassword() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getOtherAuthInfo();
            loginPage.validPassword(authInfo);
        }

        @Test
        void shouldNotAuthorizeEmptyPassword(){
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            loginPage.emptyPassword(authInfo);
        }
    }
}
