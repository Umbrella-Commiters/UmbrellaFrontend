package eu.eurofel.services.impl;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import eu.eurofel.entities.dao.FacilityDao;
import eu.eurofel.services.DaoService;

public class DaoServiceImpl implements DaoService {

	private BeanFactory beanFactory;

	public DaoServiceImpl() {

		Resource resource = new ClassPathResource(
				"spring/ibatis/spring-ibatis.xml");
		beanFactory = new XmlBeanFactory(resource);
	}

	public FacilityDao getFacilityDao() {
		return (FacilityDao) beanFactory.getBean("facilityDao");
	}

}
