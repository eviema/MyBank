/**
 * Created by Evie on 14/05/2017.
 */

public class MonBankConstants {

    // statuses of bank-related activities, e.g. success/failure
    public static final String SUCCEEDED = "0";
    public static final String SUCCEEDED_DEPOSIT_SAVINGS = "1";
    public static final String SUCCEEDED_WITHDRAW_SAVINGS = "2";
    public static final String FAILED_AMOUNT_OUTSIDE_LIMIT_RANGE = "2";
    public static final String FAILED_ACCOUNT_DOES_NOT_EXIST = "3";

    // account types
    public static final String SAVINGS = "savings";
    public static final String TERM_DEPOSIT = "term deposit";
    public static final String CREDIT_CARD = "credit card";
    public static final String HOME_LOAN = "home loan";

    // transaction types
    public static final String DEPOSIT = "deposit";
    public static final String WITHDRAW = "withdraw";
    public static final String TRANSFER = "transfer";
    public static final String TAKE_LOAN = "take loan";
    public static final String MODIFY_DAILY_LIMIT = "modify daily limit";
    public static final String MAKE_REPAYMENT = "make repayment";

}
