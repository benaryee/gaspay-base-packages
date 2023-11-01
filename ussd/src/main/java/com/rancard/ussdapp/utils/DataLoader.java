package com.rancard.ussdapp.utils;


import com.rancard.ussdapp.model.dao.mongo.UssdMenuDao;
import com.rancard.ussdapp.model.mongo.UssdMenu;
import com.rancard.ussdapp.model.payload.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.rancard.ussdapp.model.enums.MenuKey.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UssdMenuDao menuDao;

    @Override
    public void run(String... args) throws Exception {
        if (menuDao.count() < 1) {
            menuDao.saveAll(List.of(

                    new UssdMenu(UNREGISTERED_USER_INITIAL_RESPONSE, "Welcome to Gaspay", Arrays.asList(
                            new Option(1, "Create an Account"),
                            new Option(2, "Make Enquiry")
                    )),
                    new UssdMenu(MAIN_ENQUIRY_RESPONSE, "What do you want to know?"),
                    new UssdMenu(ENQUIRY_REQUEST_CALLBACK_MSISDN_CONFIRMATION_RESPONSE, "Confirm that we can reach you on [msisdn]", Arrays.asList(
                            new Option(1, "Confirm"),
                            new Option(2, "Change Number")
                    )),

                    new UssdMenu( ENQUIRY_REQUEST_CALLBACK_REQUEST_RESPONSE, "Would you like a callback?", Arrays.asList(
                            new Option(1, "Yes"),
                            new Option(2, "No")
                    )),
                    new UssdMenu(ENQUIRY_REQUEST_CALLBACK_MSISDN_CONFIRMATION_RESPONSE, "Confirm that we can reach you on [msisdn]", Arrays.asList(
                            new Option(1, "Confirm"),
                            new Option(2, "Change Number")
                    )),
                    new UssdMenu(ENQUIRY_END_ENQUIRY_RESPONSE_CALLBACK, "Your enquiry has been duly noted. Someone from support will reach out if need be. Thank you"),
                    new UssdMenu(ENQUIRY_END_ENQUIRY_RESPONSE_NO_CALLBACK, "Your enquiry will be reviewed and responded to via SMS"),
                    new UssdMenu( REGISTRATION_FIRST_NAME_RESPONSE, "Please enter your first name"),
                    new UssdMenu( REGISTRATION_LAST_NAME_RESPONSE, "Please enter your last name"),
                    new UssdMenu( REGISTRATION_ADDRESS_RESPONSE, "Please enter your digital address. \neg. GA-1234-56"),
                    new UssdMenu( REGISTRATION_CURRENT_FUEL_SOURCE_RESPONSE, "What kind of gas do you currently use.", Arrays.asList(
                            new Option(1, "LPG"),
                            new Option(2, "Firewood"),
                            new Option(3, "Charcoal"),
                            new Option(4, "Kerosene"),
                            new Option(5, "None of these")
                    )),
                    new UssdMenu(REGISTRATION_FAMILY_SIZE_RESPONSE, "What is the size of your family?", Arrays.asList(
                            new Option(1, "1 - 2"),
                            new Option(2, "3 - 5"),
                            new Option(3, "5 - 8"),
                            new Option(4, "8 - 10"),
                            new Option(5, "Other")
                    )),
                    new UssdMenu( INVALID_USER_SELECTION_RESPONSE, "The entry you selected is invalid. Kindly select an option between [min] and [max]"),
                    new UssdMenu(MAIN_MENU_RESPONSE, "Welcome , [name]", Arrays.asList(
                            new Option(1, "Purchase Cylinder"),
                            new Option(2, "Top up Wallet"),
                            new Option(3, "My Account"),
                            new Option(4, "Order History"),
                            new Option(5, "Report An Incident")
                    )),
                    new UssdMenu(REGISTRATION_COMPLETE_RESPONSE, "Registration Complete!. \nYou can now access the Gaspay services.", Arrays.asList(
                            new Option(1, "Proceed to Main Menu"),
                            new Option(2, "Exit")
                    )),
                    new UssdMenu(REGISTRATION_COMPLETE_NO_FOLLOW_RESPONSE, "Thank you for choosing Gaspay"),
                    new UssdMenu(UNDER_CONSTRUCTION_RESPONSE, "Selected option is unavailable\nTry Again Later."),
                    new UssdMenu(MY_ACCOUNT_MAIN_MENU_RESPONSE, "My Account", Arrays.asList(
                            new Option(1, "Change My Name"),
                            new Option(2, "Change My Location"),
                            new Option(3, "Change My Pin")
                    )),
                    new UssdMenu(ORDER_HISTORY_FULL_ORDER_HISTORY_RESPONSE, "Order History: \n[orderHistory]"),
                    new UssdMenu(REGISTRATION_ADDRESS_INVALID, "Invalid Ghana Post Address.\nEnter a valid address eg.AK-484-9321 "),
                    new UssdMenu(REGISTRATION_SET_PIN_RESPONSE, "We value your security. Enter a 4-digit PIN to keep your account safe"),
                    new UssdMenu(REGISTRATION_CONFIRM_PIN_RESPONSE, "Almost there!\nKindly confirm your PIN"),
                    new UssdMenu(REGISTRATION_PIN_INVALID, "Invalid PIN!\nSet a 4 digit PIN"),
                    new UssdMenu(REGISTRATION_CONFIRM_PIN_INVALID, "PIN Mismatch!\nConfirm your 4-digit PIN"),
                    new UssdMenu(TOPUP_AMOUNT_RESPONSE, "How much are you adding to your Gaspay Wallet?"),
                    new UssdMenu(TOPUP_AMOUNT_CONFIRMATION_RESPONSE, "Kindly confirm you are adding GHS[amount] to your Gaspay Wallet", Arrays.asList(
                            new Option(1, "Confirm"),
                            new Option(2, "Cancel")
                    )),
                    new UssdMenu(TOPUP_AWAITING_APPROVAL_RESPONSE, "You will receive a prompt to approve your topup."),
                    new UssdMenu(PURCHASE_MAIN_MENU_RESPONSE, "What do you want to do", Arrays.asList(
                            new Option(1, "Buy a Cylinder"),
                            new Option(2, "Refill a Cylinder"),
                            new Option(3, "Return a Cylinder"),
                            new Option(0, "Help")
                    )),
                    new UssdMenu(PURCHASE_SIZE_OPTIONS_RESPONSE, "Pick a size \n[sizes]"),
                    new UssdMenu(PURCHASE_DIGITAL_ADDRESS_CONFIRM_OR_ENTER, "Confirm your location\nGPS : [ghanaPostGps]\nCity : [city]\nStreet : [street]", Arrays.asList(
                            new Option(1, "Confirm"),
                            new Option(2, "Enter New Location")
                    )),
                    new UssdMenu(PURCHASE_ORDER_CONFIRMATION_RESPONSE, "Order Summary\n[size] Cylidner\nSub Total : GHS[price]\nDelivery : GHS [delivery]\nTotal : GHS[total]", Arrays.asList(
                            new Option(1, "Confirm"),
                            new Option(2, "Go Back")
                    )),
                    new UssdMenu(PURCHASE_PAYMENT_METHOD, "Select a payment method", Arrays.asList(
                            new Option(1, "GasPay Wallet - GHS[balance]"),
                            new Option(2, "MoMo Wallet")
                    )),
                    new UssdMenu(WALLET_PASSWORD_RESPONSE, "Enter your PIN"),
                    new UssdMenu(MY_ACCOUNT_MAIN_MENU_RESPONSE, "My Account", Arrays.asList(
                            new Option(1, "Change My Name"),
                            new Option(2, "Change My Location"),
                            new Option(3, "Change My Pin")
                    ))
            ));

        }
    }
}
