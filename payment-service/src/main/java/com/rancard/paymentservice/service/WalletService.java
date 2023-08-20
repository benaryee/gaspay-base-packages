package com.rancard.paymentservice.service;

import com.rancard.paymentservice.model.dto.wallet.*;
import com.rancard.paymentservice.model.mongo.Wallet;

import java.util.List;

public interface WalletService {

    Wallet createWallet(CreateWalletDto createWalletDto);
    Wallet editWallet(EditWalletDto createWalletDto);
    Wallet creditWallet(CreditWalletDto creditWalletDto , String sessionId);
    Wallet debitWallet(DebitWalletDto debitWalletDto , String sessionId);
    Wallet deleteWallet(String walletId);

    Wallet getWallet(String id);

    List<Wallet> getAllWallets();

    Wallet topupWallet(TopupupRequestDto creditWalletDto, String sessionId);
}
