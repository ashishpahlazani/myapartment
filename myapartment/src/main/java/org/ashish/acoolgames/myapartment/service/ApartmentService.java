package org.ashish.acoolgames.myapartment.service;

import java.util.List;

import org.ashish.acoolgames.myapartment.db.ApartmentDao;
import org.ashish.acoolgames.myapartment.model.Apartment;

public class ApartmentService {
	private static ApartmentDao apartmentDao = new ApartmentDao();
	
	public List<Apartment> getAllApartment() {
		return apartmentDao.getAllApartments();
	}
	
	public Apartment getApartment(Long apartmentId) {
		return apartmentDao.getApartmentById(apartmentId);
	}

	public Apartment addApartment(Apartment apartment) {
		apartment.validate(apartment);
		return apartmentDao.insertApartment(apartment);
	}

	public Apartment removeApartment(Long apartmentId) {
		return apartmentDao.deleteApartment(apartmentId);
	}
}
