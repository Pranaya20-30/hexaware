package com.hexaware.exception;

public class AssetNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;


    public AssetNotFoundException() {
        super("Asset not found in the system.");
    }

    public AssetNotFoundException(String message) {
        super(message);
    }
}
