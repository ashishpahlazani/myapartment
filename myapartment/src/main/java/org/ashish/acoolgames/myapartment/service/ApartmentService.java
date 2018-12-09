package org.ashish.acoolgames.myapartment.service;

import org.ashish.acoolgames.myapartment.db.ApartmentDao;
import org.ashish.acoolgames.myapartment.model.Apartment;

public class ApartmentService {
	private static ApartmentDao apartmentDao = new ApartmentDao();
	
	public Apartment getApartment(Integer apartmentId) {
		return apartmentDao.getApartmentById(apartmentId);
	}

	public Apartment addApartment(Apartment apartment) {
		return apartmentDao.insertApartment(apartment);
	}

	public Apartment removeApartment(Integer apartmentId) {
		return apartmentDao.deleteApartment(apartmentId);
	}
}
