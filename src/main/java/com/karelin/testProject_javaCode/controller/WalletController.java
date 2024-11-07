package com.karelin.testProject_javaCode.controller;

import com.karelin.testProject_javaCode.model.Wallet;
import com.karelin.testProject_javaCode.model.WalletUpdater;
import com.karelin.testProject_javaCode.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/v1/createwallet")
    public ResponseEntity<Void> saveWallet (@RequestBody Wallet wallet, UriComponentsBuilder uriComponentsBuilder) {
        this.walletService.saveWallet(wallet);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/v1/wallets/{id}")
                        .build(wallet.getId())).build();
        //return ResponseEntity.ok(walletService.saveWallet(wallet));
    }

    @GetMapping("/v1/wallets")
    public List<Wallet> getAllWallets() {
        return walletService.getAllWallets();
    }

    @GetMapping("/v1/wallets/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable UUID id) {
        Optional<Wallet> wallet = walletService.getWalletById(id);
        return wallet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/v1/deletewallet/{id}")
    public ResponseEntity<?> deleteWalletById(@PathVariable UUID id) {
        walletService.deleteWallet(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/wallet")
    public ResponseEntity<String> updateWalletAmount(@Valid @RequestBody WalletUpdater walletUpdater) {
        return ResponseEntity.ok(walletService.updateWalletAmount(walletUpdater));
    }
}
