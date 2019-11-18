package com.snehee.ganpati.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.snehee.ganpati.entity.Customer;
import com.snehee.ganpati.entity.Idol;

public interface Constants {

	List<Customer> CUSTOMER_LIST = new CopyOnWriteArrayList<>();

	List<Idol> IDOL_LIST = new CopyOnWriteArrayList<>();

	static void refreshCustomerList(final List<Customer> customerListTobeUpdated) {
		Constants.CUSTOMER_LIST.clear();
		Constants.CUSTOMER_LIST.addAll(customerListTobeUpdated);
	}

	static void refreshIdolList(final List<Idol> idolListTobeUpdated) {
		Constants.IDOL_LIST.clear();
		Constants.IDOL_LIST.addAll(idolListTobeUpdated);
	}

	/*
	 * Common constants
	 */
	String ID = "id";
	String USER_ID = "userId";
	String CUSTOMER_ID = "customerId";
	String IDOL_ID = "idolId";

	String NAME = "name";
	String COMMENTS = "comments";
	String INFO = "info";
	String STATUS = "status";

	/*
	 * Idol constants
	 */
	String TYPE = "type";
	String SPECS = "specs";
	String SIZE = "size";
	String COST = "cost";
	String PRICE = "price";
	String QUANTITY = "quantity";
	String REPARABLE_QTY = "reparableQty";
	String DAMAGED_QTY = "damagedQty";

	/*
	 * Customer Constants
	 */
	String PRIMARY_MOBILE = "primaryMobile";
	String SECONDARY_MOBILE = "secondaryMobile";
	String LANDLINE = "landline";
	String ADDRESS = "address";

	/*
	 * Booking Constants
	 */
	String BOOKING_DATE = "bookingDate";

	String BOOKING_AMOUNT = "bookingAmt";
	String BALANCE_AMOUNT = "balanceAmt";
	String TOTAL_AMOUNT = "totalAmt";
	String REASON = "reason";
	String LOCATION = "location";
	String SHIPMENT_DATE = "shipmentDate";

	/*
	 * User Constants
	 */
	String USERNAME = "username";
	String PASSWORD = "password";
	String EMAIL = "email";

	/*
	 * Customer perspective constants
	 */
	String CUSTOMER_NAME = "customerName";
	String CUSTOMER_PRIMARY_MOBILE = "customerPrimaryMobile";
	String CUSTOMER_SECONDARY_MOBILE = "customeSecondaryMobile";
	String CUSTOMER_LANDLINE = "customerLandline";

	/*
	 * Idol perspective constants
	 */
	String IDOL_NAME = "idol_name";
}
