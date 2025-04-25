package fit5171.monash.edu;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Passenger extends Person
{
    private String email;
    private String phoneNumber;
    private String cardNumber;
    private int securityCode;
    private String passport;

    // Regular expression for email
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Regular expressions for mobile phone numbers - Australia, China and the United States
    private static final Map<String, Pattern> PHONE_PATTERNS = new HashMap<>();
    static {
        // Aus: 04XX XXX XXX or +61 4XX XXX XXX
        PHONE_PATTERNS.put("AU", Pattern.compile("^(\\+61|0)[4]\\d{8}$"));

        // China: 1XXXXXXXXX or +86 1XXXXXXXXX
        PHONE_PATTERNS.put("CN", Pattern.compile("^(\\+86|0)?1\\d{10}$"));

        // US: XXX-XXX-XXXX or +1 XXX-XXX-XXXX
        PHONE_PATTERNS.put("US", Pattern.compile("^(\\+1|0)?[2-9]\\d{2}[\\-]?\\d{3}[\\-]?\\d{4}$"));
    }

    // Regular expression and length of passport number
    private static final Map<String, Pattern> PASSPORT_PATTERNS = new HashMap<>();
    static {
        // Australia: X + 7-digit number
        PASSPORT_PATTERNS.put("AU", Pattern.compile("^[A-Za-z]\\d{7}$"));

        // China: G + 8-digit number
        PASSPORT_PATTERNS.put("CN", Pattern.compile("^[G]\\d{8}$"));

        // US :nine digits
        PASSPORT_PATTERNS.put("US", Pattern.compile("^\\d{9}$"));
    }

    public Passenger(){}

    public Passenger(String firstName, String secondName, int age, String gender,
                     String email, String phoneNumber, String passport,
                     String cardNumber, int securityCode)
    {
        super(firstName, secondName, age, gender);

        // Verify all fields
        if(email == null || phoneNumber == null || passport == null ||
                cardNumber == null || email.trim().isEmpty() ||
                phoneNumber.trim().isEmpty() || passport.trim().isEmpty() ||
                cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("All passenger fields are mandatory");
        }

        // Verify the email format
        if(!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Verify the format of the phone number
        if(!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        // Verify the format of the passport number
        if(!isValidPassport(passport)) {
            throw new IllegalArgumentException("Invalid passport format");
        }

        this.securityCode = securityCode;
        this.cardNumber = cardNumber;
        this.passport = passport;
        this.email = email;
        this.phoneNumber = standardizePhoneNumber(phoneNumber);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if(!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        this.email = email;
    }

    public String getFirstName() {
        return super.getFirstName();
    }

    public String getSecondName() {
        return super.getSecondName();
    }

    public void setSecondName(String secondName) {
        super.setSecondName(secondName);
    }

    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    public String getPassport() {
        return passport;
    }

    public void setGender(String gender) {
        super.setGender(gender);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if(cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be empty");
        }
        this.cardNumber = cardNumber;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    public void setPassport(String passport) {
        if(passport == null || passport.trim().isEmpty()) {
            throw new IllegalArgumentException("Passport cannot be empty");
        }

        if(!isValidPassport(passport)) {
            throw new IllegalArgumentException("Invalid passport format");
        }

        this.passport = passport;
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        if(!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        this.phoneNumber = standardizePhoneNumber(phoneNumber);
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    // Verify the email format
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // Verify the format of the phone number
    private boolean isValidPhoneNumber(String phoneNumber) {
        for(Pattern pattern : PHONE_PATTERNS.values()) {
            if(pattern.matcher(phoneNumber).matches()) {
                return true;
            }
        }
        return false;
    }

    // Verify the format of the passport number
    private boolean isValidPassport(String passport) {
        for(Pattern pattern : PASSPORT_PATTERNS.values()) {
            if(pattern.matcher(passport).matches()) {
                return true;
            }
        }
        return false;
    }

    // Standardized format of phone numbers
    private String standardizePhoneNumber(String phoneNumber) {
        // Remove all non-numeric characters
        String digitsOnly = phoneNumber.replaceAll("[^\\d+]", "");

        // Australian numbers
        if(PHONE_PATTERNS.get("AU").matcher(phoneNumber).matches()) {
            // If it starts with 0, replace it with 61
            if(digitsOnly.startsWith("0")) {
                return "+61" + digitsOnly.substring(1);
            }
            return digitsOnly;
        }

        // Chinese numbers
        if(PHONE_PATTERNS.get("CN").matcher(phoneNumber).matches()) {
            // If it doesn't start with +, add +86
            if(!digitsOnly.startsWith("+")) {
                return "+86" + digitsOnly.replaceFirst("^0?", "");
            }
            return digitsOnly;
        }

        // American numbers
        if(PHONE_PATTERNS.get("US").matcher(phoneNumber).matches()) {
            // If it doesn't start with +, add +1
            if(!digitsOnly.startsWith("+")) {
                return "+1" + digitsOnly.replaceFirst("^0?", "");
            }
            return digitsOnly;
        }

        return phoneNumber; // Keep it as it is if any rules do not match
    }

    @Override
    public String toString()
    {
        return "Passenger{" + " Fullname= "+ super.getFirstName()+" "+super.getSecondName()+
                " ,email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passport='" + passport +
                '}';
    }
}
