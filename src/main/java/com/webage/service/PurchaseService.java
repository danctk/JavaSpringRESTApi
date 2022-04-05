package com.webage.service;

import com.webage.domain.Purchase;

import java.util.Optional;

public interface PurchaseService {
	public void savePurchase(Purchase purchase);
	public Iterable<Purchase> findAllPurchases();
	public Optional<Purchase> findPurchaseById(long id);
}