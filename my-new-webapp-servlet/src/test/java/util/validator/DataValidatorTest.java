package util.validator;

import binding.request.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DataValidatorTest {

    DataValidator dataValidator;

    @Before
    public void setUp(){
        dataValidator = new DataValidator();
    }

    @Test
    public void isNotNulLReturnFalseIfParamIsNull() {
        boolean status = dataValidator.isNotNull(null);
        assertFalse(status);
    }

    @Test
    public void isNotNulLReturnTrueIfParamIsString() {
        boolean status = dataValidator.isNotNull("kalif");
        assertTrue(status);
    }

    @Test
    public void isLengthValidReturnTrueIfLengthOfStringParamBiggerThanIntegerParam() {
        boolean status = dataValidator.isLengthValid("hello", 3);
        assertTrue(status);
    }

    @Test
    public void isLengthValidReturnTrueIfLengthOfStringParamEqualIntegerParam() {
        boolean status = dataValidator.isLengthValid("hello", 5);
        assertTrue(status);
    }

    @Test
    public void isLengthValidReturnFalseIfLengthOfStringParamLowerThanIntegerParam() {
        boolean status = dataValidator.isLengthValid("hell", 5);
        assertFalse(status);
    }

    @Test
    public void isLengthValidReturnFalseStringNull() {
        boolean status = dataValidator.isLengthValid(null, 5);
        assertFalse(status);
    }

    @Test
    public void isPasswordValidReturnTrueIfStringContainLettersDigitsSpecialSigns() {
        boolean fullStringStatus = dataValidator.isPasswordValid("login12!");
        boolean noSpecialSignsStatus = dataValidator.isPasswordValid("login12");
        boolean noDigitsStatus = dataValidator.isPasswordValid("login!");
        boolean noLettersStatus = dataValidator.isPasswordValid("12!");

        assertTrue(fullStringStatus);
        assertTrue(noSpecialSignsStatus);
        assertTrue(noDigitsStatus);
        assertTrue(noLettersStatus);
    }

    @Test
    public void isPasswordValidReturnFalseIfStringNotContainsDigitsOrSpecialSignsOrLetters() {

        boolean nullStingStatus = dataValidator.isPasswordValid(null);


        assertFalse(nullStingStatus);
    }

    @Test
    public void isLoginValidReturnTrueIfStringContainLettersDigits() {
        boolean status = dataValidator.isLoginValid("login12");

        assertTrue(status);
    }

    @Test
    public void isLoginValidReturnTrueIfStringNotContainsLettersOrDigits() {
        boolean noDigitsStatus = dataValidator.isLoginValid("12login");
        boolean notLettersStatus = dataValidator.isLoginValid("12");

        assertFalse(noDigitsStatus);
        assertFalse(notLettersStatus);

    }

    @Test
    public void isNameValidReturnFalseIfStringContainsDigits() {
        boolean status = dataValidator.isNameValid("Alex12");

        assertFalse(status);
    }

    @Test
    public void isNameValidReturnTrueIfStringNotNull() {
        boolean status = dataValidator.isNameValid("Alex");

        assertTrue(status);
    }

    @Test
    public void isNameValidReturnFalseIfStringNull() {
        boolean status = dataValidator.isNameValid(null);

        assertFalse(status);
    }

    @Test
    public void isIdValidReturnFalseIfIntegerNull(){
        boolean status =  dataValidator.isIdValid((Integer) null);

      assertFalse(status);
    }

    @Test
    public void isIdValidReturnFalseIfIntegerLowerThan0(){
        boolean status = dataValidator.isIdValid(-1);

        assertFalse(status);
    }

    @Test
    public void isIdValidReturnTrueIfIntegerBiggerThan0(){
        boolean status = dataValidator.isIdValid(124);

        assertTrue(status);
    }

    @Test
    public void isIdValidReturnFalseIfStringNotParsedOrParsedNumberLower0(){
        boolean minusNumberStatus = dataValidator.isIdValid("-12");
        boolean parsedNumberStatus = dataValidator.isIdValid("12mdsa");

        assertFalse(parsedNumberStatus);
        assertFalse(minusNumberStatus);
    }

    @Test
    public void isIdValidReturnTrueIfStringParse(){
        boolean status = dataValidator.isIdValid("123");

        assertTrue(status);
    }

    @Test
    public void isTransactionStatusValidReturnTrue(){
        boolean created = dataValidator.isTransactionStatusValid("CREATED");
        boolean paid = dataValidator.isTransactionStatusValid("PAID");
        boolean boxing = dataValidator.isTransactionStatusValid("BOXING");
        boolean delivering = dataValidator.isTransactionStatusValid("DELIVERING");
        boolean complete = dataValidator.isTransactionStatusValid("COMPLETE");

        assertTrue(created);
        assertTrue(paid);
        assertTrue(boxing);
        assertTrue(delivering);
        assertTrue(complete);
    }

    @Test
    public void isTransactionStatusValidReturnFalse(){
        boolean created = dataValidator.isTransactionStatusValid("CREsdATED");
        boolean paid = dataValidator.isTransactionStatusValid("PAdasID");
        boolean boxing = dataValidator.isTransactionStatusValid("ds");
        boolean delivering = dataValidator.isTransactionStatusValid("DELIdsVERING");
        boolean complete = dataValidator.isTransactionStatusValid("COMPLadETE");

        assertFalse(created);
        assertFalse(paid);
        assertFalse(boxing);
        assertFalse(delivering);
        assertFalse(complete);
    }

    @Test
    public void isTransactionPaymentTypeValidReturnFalse(){
        boolean creditcard = dataValidator.isTransactionPaymentTypeValid("CREDvsdITCARD");
        boolean cash = dataValidator.isTransactionPaymentTypeValid("CvsdASH");
        boolean loyalty = dataValidator.isTransactionPaymentTypeValid("LOYALvsdTY");

        assertFalse(creditcard);
        assertFalse(cash);
        assertFalse(loyalty);
    }

    @Test
    public void isTransactionPaymentTypeValidReturnTrue(){
        boolean creditcard = dataValidator.isTransactionPaymentTypeValid("CREDITCARD");
        boolean cash = dataValidator.isTransactionPaymentTypeValid("CASH");
        boolean loyalty = dataValidator.isTransactionPaymentTypeValid("LOYALTY");

        assertTrue(creditcard);
        assertTrue(cash);
        assertTrue(loyalty);

    }

    @Test
    public void arePasswordsEqual() {
        boolean equal = dataValidator.arePasswordsEqual("alex12!","alex12!");

        assertTrue(equal);
    }

    @Test
    public void arePasswordsNotEqual() {
        boolean equal = dataValidator.arePasswordsEqual("al12!","alex12!");

        assertFalse(equal);
    }

    @Test
    public void arePasswordsNotEqualIfNull() {
        boolean equal = dataValidator.arePasswordsEqual(null,null);

        assertFalse(equal);
    }

    @Test
    public void isLogInFormValidReturnFalseIfParamsNull() {
        boolean statusLoginAndPassAreNull = dataValidator.isLogInDataValid(null, null);
        boolean statusLoginIsNull = dataValidator.isLogInDataValid(null, "1234Qwe!");
        boolean statusPassIsNull = dataValidator.isLogInDataValid("alexan21", null);

        assertFalse(statusLoginAndPassAreNull);
        assertFalse(statusLoginIsNull);
        assertFalse(statusPassIsNull);
    }

    @Test
    public void isLogInFormValidReturnTrue() {
        boolean status = dataValidator.isLogInDataValid("admin12", "1234!qwdz");

        assertTrue(status);
    }

    @Test
    public void isCustomerDataValidReturnTrue() {
        boolean status = dataValidator.isCustomerDataValid("admin12","alex",
                "1234qwer!", "1234qwer!");
        assertTrue(status);
    }

    @Test
    public void isCustomerDataValidReturnFalseIfParamsNull() {
        boolean statusLoginNull = dataValidator.isCustomerDataValid(null,"alex",
                "1234qwer!", "1234qwer!");
        boolean statusNameNull = dataValidator.isCustomerDataValid("login12",null,
                "1234qwer!", "1234qwer!");
        boolean statusPass1Null = dataValidator.isCustomerDataValid("login12","alex",
                null, "1234qwer!");
        boolean statusPass2Null = dataValidator.isCustomerDataValid("login12","alex",
                "1234qwer!", null);
        boolean statusLoginNameNull = dataValidator.isCustomerDataValid(null, null,
                "1234qwer!", "1234qwer!");
        boolean statusLoginPass1Null = dataValidator.isCustomerDataValid(null, "alex",
                null, "1234qwer!");
        boolean statusLoginPass2Null = dataValidator.isCustomerDataValid(null, "alex",
                "1234qwer!", null);
        boolean statusNamePass1Null = dataValidator.isCustomerDataValid("login12", "alex",
                null, "1234qwer!");
        boolean statusNamePass2Null = dataValidator.isCustomerDataValid("login12", null,
                "1234qwer!", null);
        boolean statusLoginIsNotNull = dataValidator.isCustomerDataValid(null, "alex",
                "1234qwer!", "1234qwer!");
        boolean statusNameIsNotNull = dataValidator.isCustomerDataValid("login12", null,
                "1234qwer!", "1234qwer!");
        boolean statusPass1IsNotNull = dataValidator.isCustomerDataValid("login12", "alex",
                null, "1234qwer!");
        boolean statusPass2IsNotNull = dataValidator.isCustomerDataValid("login12", "alex",
                "1234qwer!", null);

        boolean statusAllNull = dataValidator.isCustomerDataValid(null, null,
                null, null);

        assertFalse(statusAllNull);
        assertFalse(statusNameNull);
        assertFalse(statusLoginNull);
        assertFalse(statusPass1Null);
        assertFalse(statusPass2Null);
        assertFalse(statusLoginNameNull);
        assertFalse(statusLoginPass1Null);
        assertFalse(statusLoginPass2Null);
        assertFalse(statusNamePass1Null);
        assertFalse(statusNamePass2Null);
        assertFalse(statusLoginIsNotNull);
        assertFalse(statusNameIsNotNull);
        assertFalse(statusPass1IsNotNull);
        assertFalse(statusPass2IsNotNull);
    }

    @Test
    public void isCustomerUpdateDataValidReturnFalse() {
        boolean statusLoginNull = dataValidator.isCustomerUpdateDataValid(12, null,"alex",
                "1234qwer!", "1234qwer!");
        boolean statusNameNull = dataValidator.isCustomerUpdateDataValid(12,"login12",null,
                "1234qwer!", "1234qwer!");
        boolean statusPass1Null = dataValidator.isCustomerUpdateDataValid(12, "login12","alex",
                null, "1234qwer!");
        boolean statusPass2Null = dataValidator.isCustomerUpdateDataValid(12,"login12","alex",
                "1234qwer!", null);
        boolean statusLoginNameNull = dataValidator.isCustomerUpdateDataValid(12,null, null,
                "1234qwer!", "1234qwer!");
        boolean statusLoginPass1Null = dataValidator.isCustomerUpdateDataValid(12, null, "alex",
                null, "1234qwer!");
        boolean statusLoginPass2Null = dataValidator.isCustomerUpdateDataValid(12,null, "alex",
                "1234qwer!", null);
        boolean statusNamePass1Null = dataValidator.isCustomerUpdateDataValid(12,"login12", "alex",
                null, "1234qwer!");
        boolean statusNamePass2Null = dataValidator.isCustomerUpdateDataValid(12, "login12", null,
                "1234qwer!", null);
        boolean statusLoginIsNotNull = dataValidator.isCustomerUpdateDataValid(12, null, "alex",
                "1234qwer!", "1234qwer!");
        boolean statusNameIsNotNull = dataValidator.isCustomerUpdateDataValid(12, "login12", null,
                "1234qwer!", "1234qwer!");
        boolean statusPass1IsNotNull = dataValidator.isCustomerUpdateDataValid(12,"login12", "alex",
                null, "1234qwer!");
        boolean statusPass2IsNotNull = dataValidator.isCustomerUpdateDataValid(12,"login12", "alex",
                "1234qwer!", null);

        boolean statusAllNull = dataValidator.isCustomerUpdateDataValid(12,null, null,
                null, null);

        boolean statusIdNotValid = dataValidator.isCustomerUpdateDataValid(null,null, null,
                null, null);

        assertFalse(statusAllNull);
        assertFalse(statusNameNull);
        assertFalse(statusLoginNull);
        assertFalse(statusPass1Null);
        assertFalse(statusPass2Null);
        assertFalse(statusLoginNameNull);
        assertFalse(statusLoginPass1Null);
        assertFalse(statusLoginPass2Null);
        assertFalse(statusNamePass1Null);
        assertFalse(statusNamePass2Null);
        assertFalse(statusLoginIsNotNull);
        assertFalse(statusNameIsNotNull);
        assertFalse(statusPass1IsNotNull);
        assertFalse(statusPass2IsNotNull);
        assertFalse(statusIdNotValid);
    }

    @Test
    public void isCustomerUpdateDataValidReturnTrueIfIdNotNullAndCustomerDataValid() {
        boolean status = dataValidator.isCustomerUpdateDataValid(12,"admin12","alex",
                "1234qwer!", "1234qwer!");

        assertTrue(status);
    }

    @Test
    public void isWelcomeDataValidReturnTrue() {
        boolean status = dataValidator.isWelcomeDataValid("124", "admin12");
        assertTrue(status);
    }

    @Test
    public void isWelcomeDataValidReturnFalseIfParamsNull(){
        boolean statusAllNull = dataValidator.isWelcomeDataValid(null, null);
        boolean statusIdNull = dataValidator.isWelcomeDataValid(null, "login12");
        boolean statusLoginNull = dataValidator.isWelcomeDataValid("123", null);

        assertFalse(statusAllNull);
        assertFalse(statusIdNull);
        assertFalse(statusLoginNull);
    }

    @Test
    public void isWelcomeDataValidReturnFalseIfParamsNotValid(){
        boolean statusLoginNotValid = dataValidator.isWelcomeDataValid(null, "12das");
        boolean statusIdNotValid = dataValidator.isWelcomeDataValid(null, "log12");

        assertFalse(statusLoginNotValid);
        assertFalse(statusIdNotValid);
    }

    @Test
    public void isTransactionDataValidReturnTrue(){
        boolean status = dataValidator.isTransactionDataValid(124, 212,
                "CREDITCARD");
        assertTrue(status);
    }

    @Test
    public void isTransactionDataValidReturnFalse(){
        boolean statusCustomerNull = dataValidator.isTransactionDataValid(null,41,
                "CREDITCARD");
        boolean statusProductNull = dataValidator.isTransactionDataValid(12,null,
                "CREDITCARD");
        boolean statusPaymentNull = dataValidator.isTransactionDataValid(12,41,
                null);
        boolean statusCustomerProductNull = dataValidator.isTransactionDataValid(null,null,
                "CREDITCARD");
        boolean statusCustomerPaymentNull = dataValidator.isTransactionDataValid(null, 41,
                null);
        boolean statusProductPaymentNull = dataValidator.isTransactionDataValid(12, 41,
                null);
        boolean statusProductCardNull = dataValidator.isTransactionDataValid(12, null,
                "CREDITCARD");
        boolean statusCustomerIsNotNull = dataValidator.isTransactionDataValid(12, null,
                null);
        boolean statusProductIsNotNull = dataValidator.isTransactionDataValid(null, 41,
                null);
        boolean statusPaymentIsNotNull = dataValidator.isTransactionDataValid(null, null,
                "CREDITCARD");
        boolean statusCardIsNotNull = dataValidator.isTransactionDataValid(null, null,
                null);

        boolean statusAllNull = dataValidator.isTransactionDataValid(null, null,
                null);

        assertFalse(statusAllNull);
        assertFalse(statusCustomerNull);
        assertFalse(statusProductNull);
        assertFalse(statusPaymentNull);
        assertFalse(statusCustomerProductNull);
        assertFalse(statusProductCardNull);
        assertFalse(statusCustomerPaymentNull);
        assertFalse(statusProductPaymentNull);
        assertFalse(statusCardIsNotNull);
        assertFalse(statusProductIsNotNull);
        assertFalse(statusCustomerIsNotNull);
        assertFalse(statusPaymentIsNotNull);
    }

    @Test
    public void isTransactionUpdateDataValidReturnTrue(){
        boolean status = dataValidator.isTransactionUpdateDataValid(121, "COMPLETE");

        assertTrue(status);
    }

    @Test
    public void isTransactionUpdateDataValidReturnFalse(){
        boolean statusNotValid = dataValidator.isTransactionUpdateDataValid(123,"DACX");
        boolean statusNull = dataValidator.isTransactionUpdateDataValid(123, null);
        boolean idNull = dataValidator.isTransactionUpdateDataValid(null, "COMPLETE");
        boolean statusAndIdNull = dataValidator.isTransactionUpdateDataValid(null, null);
        boolean idMinusValue = dataValidator.isTransactionUpdateDataValid(-12, "COMPLETE");

        assertFalse(statusNotValid);
        assertFalse(statusNull);
        assertFalse(idNull);
        assertFalse(statusAndIdNull);
        assertFalse(idMinusValue);
    }

    @Test
    public void isRoleDataValidReturnTrue(){
        boolean status = dataValidator.isRoleDataValid("kasldas");

        assertTrue(status);
    }

    @Test
    public void isRoleDataValidReturnFalse(){
        boolean status = dataValidator.isRoleDataValid(null);

        assertFalse(status);
    }

    @Test
    public void isRoleUpdateDataValidReturnTrue(){
        boolean status = dataValidator.isRoleUpdateDataValid(121, "admin");

        assertTrue(status);
    }

    @Test
    public void isRoleUpdateDataValidReturnFalse(){
        boolean statusNull = dataValidator.isRoleUpdateDataValid(123, null);
        boolean idNull = dataValidator.isRoleUpdateDataValid(null, "admin");
        boolean statusAndIdNull = dataValidator.isRoleUpdateDataValid(null, null);
        boolean idMinusValue = dataValidator.isRoleUpdateDataValid(-12, "admin");

        assertFalse(statusNull);
        assertFalse(idNull);
        assertFalse(statusAndIdNull);
        assertFalse(idMinusValue);
    }

    @Test
    public void isProductDataValidReturnTrue(){
        boolean status = dataValidator.isProductDataValid("fish", "foods", 12.3,1.1);
        boolean statusDiscountNull = dataValidator.isProductDataValid("fish", "foods", 12.3,null);
        assertTrue(status);
        assertTrue(statusDiscountNull);
    }

    @Test
    public void isProductDataValidReturnFalse(){
        boolean statusNameNull = dataValidator.isProductDataValid(null,"foods",
                12.3, 0.1);
        boolean statusCategoryNull = dataValidator.isProductDataValid("lemon",null,
                12.3, 0.1);
        boolean statusPriceNull = dataValidator.isProductDataValid("lemon","foods",
                null, 0.1);
        boolean statusNameCategoryNull = dataValidator.isProductDataValid(null,null,
                12.3, 0.1);
        boolean statusNamePriceNull = dataValidator.isProductDataValid(null, "foods",
                null, 0.1);
        boolean statusCategoryDiscountNull = dataValidator.isProductDataValid("lemon", null,
                12.3, null);
        boolean statusNameIsNotNull = dataValidator.isProductDataValid("lemon", null,
                null, null);
        boolean statusCategoryIsNotNull = dataValidator.isProductDataValid(null, "foods",
                null, null);
        boolean statusPriceIsNotNull = dataValidator.isProductDataValid(null, null,
                12.3, null);
        boolean statusDiscountIsNotNull = dataValidator.isProductDataValid(null, null,
                null, 0.1);

        boolean statusAllNull = dataValidator.isProductDataValid(null, null,
                null, null);

        assertFalse(statusAllNull);
        assertFalse(statusNameNull);
        assertFalse(statusCategoryNull);
        assertFalse(statusPriceNull);
        assertFalse(statusNameCategoryNull);
        assertFalse(statusNamePriceNull);
        assertFalse(statusCategoryDiscountNull);
        assertFalse(statusNameIsNotNull);
        assertFalse(statusPriceIsNotNull);
        assertFalse(statusDiscountIsNotNull);
        assertFalse(statusCategoryIsNotNull);
    }

    @Test
    public void isProductUpdateDataValidReturnTrue(){
        boolean status = dataValidator.isProductUpdateDataValid(121, "fish","foods", 32.2, 0.1 );
        boolean discountNull = dataValidator.isProductUpdateDataValid(121, "fish","foods", 32.2, null );

        assertTrue(status);
        assertTrue(discountNull);
    }

    @Test
    public void isProductUpdateDataValidReturnFalse(){
        boolean statusNameNull = dataValidator.isProductUpdateDataValid(12,null,"foods",
                12.3, 0.1);
        boolean statusCategoryNull = dataValidator.isProductUpdateDataValid(12,"lemon",null,
                12.3, 0.1);
        boolean statusPriceNull = dataValidator.isProductUpdateDataValid(12,"lemon","foods",
                null, 0.1);
        boolean statusNameCategoryNull = dataValidator.isProductUpdateDataValid(12,null,null,
                12.3, 0.1);
        boolean statusNamePriceNull = dataValidator.isProductUpdateDataValid(12,null, "foods",
                null, 0.1);
        boolean statusCategoryDiscountNull = dataValidator.isProductUpdateDataValid(12,"lemon", null,
                12.3, null);
        boolean statusNameIsNotNull = dataValidator.isProductUpdateDataValid(12,"lemon", null,
                null, null);
        boolean statusCategoryIsNotNull = dataValidator.isProductUpdateDataValid(12,null, "foods",
                null, null);
        boolean statusPriceIsNotNull = dataValidator.isProductUpdateDataValid(12,null, null,
                12.3, null);
        boolean statusDiscountIsNotNull = dataValidator.isProductUpdateDataValid(12,null, null,
                null, 0.1);

        boolean statusAllNull = dataValidator.isProductUpdateDataValid(12,null, null,
                null, null);

        boolean statusIdNull = dataValidator.isProductUpdateDataValid(12,null, null,
                null, null);


        assertFalse(statusAllNull);
        assertFalse(statusIdNull);
        assertFalse(statusNameNull);
        assertFalse(statusCategoryNull);
        assertFalse(statusPriceNull);
        assertFalse(statusNameCategoryNull);
        assertFalse(statusNamePriceNull);
        assertFalse(statusCategoryDiscountNull);
        assertFalse(statusNameIsNotNull);
        assertFalse(statusPriceIsNotNull);
        assertFalse(statusDiscountIsNotNull);
        assertFalse(statusCategoryIsNotNull);

    }





}