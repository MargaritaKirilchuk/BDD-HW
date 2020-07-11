package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static VerificationCode getWrongVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("75894675");
    }

    @Value
    public static class Cards {
        private String cardNumbers;

        public static Cards card1() {
            return new Cards("5559 0000 0000 0001");
        }

        public static Cards card2() {
            return new Cards("5559 0000 0000 0002");
        }

        public static Cards invalidCard() {
            return new Cards("5559 0000 0000 0003");
        }
    }

    @Value
    public static class Balance {
        private int amount;

        public static Balance getBalance(int balance) {
            if (balance > 0) {
                int amount = (int) (Math.random() * balance);
                return new Balance(amount);
            }
            return new Balance(0);
        }
    }
}
