package com.rancard.paymentservice.service.impl;


import com.rancard.paymentservice.exception.ServiceException;
import com.rancard.paymentservice.model.dto.wallet.CreateWalletDto;
import com.rancard.paymentservice.model.dto.wallet.TopupupRequestDto;
import com.rancard.paymentservice.model.dto.wallet.DebitWalletDto;
import com.rancard.paymentservice.model.dto.wallet.EditWalletDto;
import com.rancard.paymentservice.model.mongo.Wallet;
import com.rancard.paymentservice.repository.WalletRepository;
import com.rancard.paymentservice.service.PaymentService;
import com.rancard.paymentservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rancard.paymentservice.model.enums.ServiceError.WALLET_ALREADY_EXISTS;
import static com.rancard.paymentservice.model.enums.ServiceError.WALLET_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;

    private final PaymentService paymentService;

    @Override
    public Wallet createWallet(CreateWalletDto createWalletDto) {
        Wallet wallet = modelMapper.map(createWalletDto, Wallet.class);

        if(walletRepository.findByWalletId(createWalletDto.getWalletId()).isPresent()){
            throw new ServiceException(WALLET_ALREADY_EXISTS);
        }

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet editWallet(EditWalletDto createWalletDto) {
        return null;
    }

    @Override
    public Wallet creditWallet(TopupupRequestDto createWalletDto) {
        return null;
    }

    @Override
    public Wallet debitWallet(DebitWalletDto createWalletDto) {
        return null;
    }

    @Override
    public Wallet deleteWallet(String walletId) {
        return null;
    }

    @Override
    public Wallet getWallet(String id) {
        Wallet wallet = walletRepository.findById(id).orElse(null);
        if (wallet == null){
            throw new ServiceException(WALLET_NOT_FOUND);
        }

        return wallet;
    }

    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public Wallet topupWallet(TopupupRequestDto creditWalletDto, String sessionId) {
        Wallet wallet = walletRepository.findById(creditWalletDto.getUser().getWalletId()).orElse(null);
        if (wallet == null){
            throw new ServiceException(WALLET_NOT_FOUND);
        }

        paymentService.makePayment(creditWalletDto, sessionId);
        return wallet;
    }
}
