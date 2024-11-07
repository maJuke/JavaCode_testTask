package com.karelin.testProject_javaCode.service;

import com.karelin.testProject_javaCode.model.Wallet;
import com.karelin.testProject_javaCode.model.WalletUpdater;
import com.karelin.testProject_javaCode.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void saveWallet(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    public Optional<Wallet> getWalletById(UUID id) {
        return walletRepository.findById(id);
    }

    public void deleteWallet(UUID id) {
        walletRepository.deleteById(id);
    }

    public String updateWalletAmount(WalletUpdater walletUpdater) {
        /* Обработка ошибки неправильного UUID */
        Optional<Wallet> walletOptional = walletRepository.findById(walletUpdater.getValletId());
        if (walletOptional.isEmpty()) {
            throw new WalletNotFoundException("Wallet with ID " + walletUpdater.getValletId() + " not found");
        }

        Wallet wallet = walletOptional.get();
        double amount = walletUpdater.getAmount();
        switch(walletUpdater.getOperationType().toLowerCase()){
            case ("deposit"):
                wallet.setAmount(wallet.getAmount() + amount);
                walletRepository.save(wallet);
                return "The wallet has been updated successfully!";
            case ("withdraw"):
                if (wallet.getAmount() - amount > 0) {
                    wallet.setAmount(wallet.getAmount() - amount);
                    walletRepository.save(wallet);
                    return "The wallet has been updated successfully!";
                } else
                    return "There is not enough money in wallet!";
            default:
                return "Wrong operation type!";
        }
    }

    public static class WalletNotFoundException extends RuntimeException {
        public WalletNotFoundException(String message) {
            super(message);
        }
    }
}
