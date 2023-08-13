package com.rancard.paymentservice.service;

import com.rancard.paymentservice.model.dto.wallet.CreateWalletDto;
import com.rancard.paymentservice.model.dto.wallet.TopupupRequestDto;
import com.rancard.paymentservice.model.dto.wallet.DebitWalletDto;
import com.rancard.paymentservice.model.dto.wallet.EditWalletDto;
import com.rancard.paymentservice.model.mongo.Wallet;

import java.util.List;

public interface WalletService {

    Wallet createWallet(CreateWalletDto createWalletDto);
    Wallet editWallet(EditWalletDto createWalletDto);
    Wallet creditWallet(TopupupRequestDto createWalletDto);
    Wallet debitWallet(DebitWalletDto createWalletDto);
    Wallet deleteWallet(String walletId);

    Wallet getWallet(String id);

    List<Wallet> getAllWallets();

    Wallet topupWallet(TopupupRequestDto creditWalletDto, String sessionId);
}
