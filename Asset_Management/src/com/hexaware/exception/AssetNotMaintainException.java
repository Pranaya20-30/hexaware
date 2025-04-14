package com.hexaware.exception;

public class AssetNotMaintainException extends Exception {
	private static final long serialVersionUID = 2L;


    public AssetNotMaintainException() {
        super("Asset cannot be maintained due to invalid status or missing information.");
    }

    public AssetNotMaintainException(String message) {
        super(message);
    }
}
