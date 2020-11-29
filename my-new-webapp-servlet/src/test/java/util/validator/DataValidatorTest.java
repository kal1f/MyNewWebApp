package util.validator;

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
        boolean status =  dataValidator.isIdValid(null);

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
        boolean statusLoginAndPassAreNull = dataValidator.isLogInFormValid(null, null);
        boolean statusLoginIsNull = dataValidator.isLogInFormValid(null, "1234Qwe!");
        boolean statusPassIsNull = dataValidator.isLogInFormValid("alexan21", null);

        assertFalse(statusLoginAndPassAreNull);
        assertFalse(statusLoginIsNull);
        assertFalse(statusPassIsNull);
    }

    @Test
    public void isLogInFormValidReturnTrue() {
        boolean status = dataValidator.isLogInFormValid("admin12", "1234!qwdz");

        assertTrue(status);
    }

    @Test
    public void isRegisterFormValidReturnTrue() {
        boolean status = dataValidator.isRegisterFormValid("admin12","alex",
                "1234qwer!", "1234qwer!");
        assertTrue(status);
    }

    @Test
    public void isRegisterFormValidReturnFalseIfParamsNull() {
        boolean statusLoginNull = dataValidator.isRegisterFormValid(null,"alex",
                "1234qwer!", "1234qwer!");
        boolean statusNameNull = dataValidator.isRegisterFormValid("login12",null,
                "1234qwer!", "1234qwer!");
        boolean statusPass1Null = dataValidator.isRegisterFormValid("login12","alex",
                null, "1234qwer!");
        boolean statusPass2Null = dataValidator.isRegisterFormValid("login12","alex",
                "1234qwer!", null);
        boolean statusLoginNameNull = dataValidator.isRegisterFormValid(null, null,
                "1234qwer!", "1234qwer!");
        boolean statusLoginPass1Null = dataValidator.isRegisterFormValid(null, "alex",
                null, "1234qwer!");
        boolean statusLoginPass2Null = dataValidator.isRegisterFormValid(null, "alex",
                "1234qwer!", null);
        boolean statusNamePass1Null = dataValidator.isRegisterFormValid("login12", "alex",
                null, "1234qwer!");
        boolean statusNamePass2Null = dataValidator.isRegisterFormValid("login12", null,
                "1234qwer!", null);
        boolean statusLoginIsNotNull = dataValidator.isRegisterFormValid(null, "alex",
                "1234qwer!", "1234qwer!");
        boolean statusNameIsNotNull = dataValidator.isRegisterFormValid("login12", null,
                "1234qwer!", "1234qwer!");
        boolean statusPass1IsNotNull = dataValidator.isRegisterFormValid("login12", "alex",
                null, "1234qwer!");
        boolean statusPass2IsNotNull = dataValidator.isRegisterFormValid("login12", "alex",
                "1234qwer!", null);

        boolean statusAllNull = dataValidator.isRegisterFormValid(null, null,
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
    public void isWelcomeFormValidReturnTrue() {
        boolean status = dataValidator.isWelcomeFormValid(124, "admin12");
        assertTrue(status);
    }

    @Test
    public void isWelcomeFormValidReturnFalseIfParamsNull(){
        boolean statusAllNull = dataValidator.isWelcomeFormValid(null, null);
        boolean statusIdNull = dataValidator.isWelcomeFormValid(null, "login12");
        boolean statusLoginNull = dataValidator.isWelcomeFormValid(123, null);

        assertFalse(statusAllNull);
        assertFalse(statusIdNull);
        assertFalse(statusLoginNull);
    }

    @Test
    public void isWelcomeFormValidReturnFalseIfParamsNotValid(){
        boolean statusLoginNotValid = dataValidator.isWelcomeFormValid(null, "12das");
        boolean statusIdNotValid = dataValidator.isWelcomeFormValid(null, "log12");

        assertFalse(statusLoginNotValid);
        assertFalse(statusIdNotValid);
    }
}