package com.rancard.paymentservice.service.impl;


import com.rancard.paymentservice.exception.ServiceException;
import com.rancard.paymentservice.model.dto.wallet.*;
import com.rancard.paymentservice.model.mongo.Wallet;
import com.rancard.paymentservice.repository.WalletRepository;
import com.rancard.paymentservice.service.PaymentService;
import com.rancard.paymentservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.rancard.paymentservice.model.enums.ServiceError.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;

    @Autowired
    @Lazy
    private PaymentService paymentService;

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
    public Wallet creditWallet(CreditWalletDto creditWalletDto, String sessionId) {

        log.info("[{}] Crediting wallet with id: {} amount : {}", sessionId, creditWalletDto.getId(), creditWalletDto.getAmount());
        Wallet wallet = walletRepository.findByWalletId(creditWalletDto.getId()).orElse(null);
        if(wallet == null){
            throw new ServiceException(WALLET_NOT_FOUND);
        }

        wallet.setBalance(wallet.getBalance().add(creditWalletDto.getAmount()));

        log.info("[{}] Wallet balance after debit: {}", sessionId, wallet.getBalance());
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet debitWallet(DebitWalletDto debitWalletDto, String sessionId){
        log.info("[{}] Debiting wallet with id: {} amount : {}", sessionId, debitWalletDto.getId(), debitWalletDto.getAmount());
        Wallet wallet = walletRepository.findByWalletId(debitWalletDto.getId()).orElse(null);
        if(wallet == null){
            throw new ServiceException(WALLET_NOT_FOUND);
        }

        int compareBalance = wallet.getBalance().compareTo(debitWalletDto.getAmount());

        if(compareBalance < 0){
            throw new ServiceException(INSUFFICIENT_BALANCE);
        }
        wallet.setBalance(wallet.getBalance().subtract(debitWalletDto.getAmount()));

        log.info("[{}] Wallet balance after debit: {}", sessionId, wallet.getBalance());
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet deleteWallet(String walletId) {
        return null;
    }

    @Override
    public Wallet getWallet(String id) {
        Wallet wallet = walletRepository.findByWalletId(id).orElse(null);
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
