package eu.eurofel.services;

import java.util.Collection;

import org.springframework.stereotype.Service;

import eu.eurofel.entities.Address;
import eu.eurofel.entities.Facility;

@Service
public interface DisseminationService {

	public void disseminateInformation(Address address);
	
	public void disseminateInformation(Address address, Collection<Facility> facilities);
	
}
