package eu.eurofel.services;

import java.util.Collection;

import org.springframework.stereotype.Service;

import eu.eurofel.entities.Affiliation;

@Service
public interface AffiliationService {

	/**
	 * Get all existing affiliations
	 * 
	 * @return all affiliations
	 */
	public Collection<Affiliation> getAffiliations();

	/**
	 * Search the affiliation which contains the search term
	 * 
	 * @param term
	 *            - the search term after which to search
	 * @return all affiliations from query results
	 */
	public Collection<Affiliation> searchAffiliations(String term);

	/**
	 * Search the affiliation which contains the values set in the parameter
	 * 
	 * @param affiliation
	 *            - search for an affiliation which contains the values set in
	 *            the parameter
	 * @return all affiliations from query results
	 */
	public Collection<Affiliation> searchAffiliations(Affiliation affiliation);

}
