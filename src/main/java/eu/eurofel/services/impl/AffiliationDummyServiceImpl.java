package eu.eurofel.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import eu.eurofel.entities.Affiliation;
import eu.eurofel.services.AffiliationService;

public class AffiliationDummyServiceImpl implements AffiliationService {

	private Collection<Affiliation> affiliations = null;

	
	public Collection<Affiliation> getAffiliations() {
		return buildAffiliations();
	}

	
	public Collection<Affiliation> searchAffiliations(String term) {
		return buildAffiliations();
	}

	
	public Collection<Affiliation> searchAffiliations(Affiliation affiliation) {
		return buildAffiliations();
	}

	private Collection<Affiliation> buildAffiliations() {
		if (affiliations == null) {
			affiliations = new ArrayList<Affiliation>();
			affiliations.add(new Affiliation(
					"Paul Scherrer Institut\n5232 Villigen PSI\nSwitzerland"));
			affiliations
					.add(new Affiliation(
							"ESRF\n6 RUE JULES HOROWITZ\nBP 220\n38043 GRENOBLE CEDEX 9\nFRANCE"));
			affiliations
					.add(new Affiliation(
							"ISIS\nScience and Technology Facilities Council\nRutherford Appleton Laboratory\nHarwell Science and Innovation Campus\nDidcot\nOX11 OQX\nUnited Kingdom"));
			affiliations
					.add(new Affiliation(
							"Diamond Light Source Ltd\nDiamond House\nHarwell Science and Innovation Campus\nDidcot\nOxfordshire\nOX11 0DE\nUnited Kingdom"));

			// affiliations
			// .add(new Affiliation(
			// "Lund University\nMAX-lab\nP.O. Box 188\nSE-221 00 Lund\nSweden"));
			affiliations
					.add(new Affiliation(
							"Deutsches Elektronen-Synchrotron DESY\nD-22603 Hamburg\nGermany"));
			affiliations
					.add(new Affiliation(
							"SOLEIL\nDivision Expériences\nL’orme des Merisiers\nSt Aubin - BP 48\n91192 Gif-sur-Yvette Cedex\nFRANCE"));
			affiliations
					.add(new Affiliation(
							"Science and Technology Facilities Council\nPolaris House\nNorth Star Avenue\nSwindon\nSN2 1SZ\nUnited Kingdom"));
			affiliations
					.add(new Affiliation(
							"Sincrotrone Trieste S.C.p.A. di interesse nazionale\n\nStrada Statale 14\n34149 Basovizza, Trieste\n ITALY"));
			affiliations.add(new Affiliation(
					"Lungotevere Thaon di Revel, 76\n00196 Rome\nITALY"));
			affiliations
					.add(new Affiliation(
							"Wilhelm-Conrad-Röntgen Campus\nBESSY II\nAlbert-Einstein-Str. 15\n12489 Berlin\nGermany"));
			affiliations
					.add(new Affiliation(
							"The Andrzej Soltan Institute for Nuclear Studies (IPJ)\n05-400 Swierk/Otwock\nPoland"));
		}
		return affiliations;
	}
}
