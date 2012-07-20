package com.ammob.passport.compass.gps.device.jpa.entities;

import java.util.ArrayList;
import java.util.Map;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.compass.core.mapping.ResourceMapping;
import org.compass.gps.device.jpa.JpaGpsDevice;
import org.compass.gps.device.jpa.JpaGpsDeviceException;
import org.compass.gps.device.jpa.entities.EntityInformation;
import org.compass.gps.device.jpa.entities.JpaEntitiesLocator;
import org.compass.gps.device.jpa.queryprovider.HibernateJpaQueryProvider;
import org.compass.gps.spi.CompassGpsInterfaceDevice;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

/**
 * A specilized version that works with Hibernate. This class should be used instead of
 * {@link DefaultJpaEntitiesLocator} since it works with both hbm files and annotatios.
 *
 * @author kimchy
 */
public class HibernateJpaEntitiesLocator implements JpaEntitiesLocator {

    protected Log log = LogFactory.getLog(getClass());

    public EntityInformation[] locate(EntityManagerFactory entityManagerFactory, JpaGpsDevice device)
            throws JpaGpsDeviceException {

        CompassGpsInterfaceDevice gps = (CompassGpsInterfaceDevice) device.getGps();

        HibernateEntityManagerFactory hibernateEntityManagerFactory =
                (HibernateEntityManagerFactory) entityManagerFactory;
        SessionFactory sessionFactory = hibernateEntityManagerFactory.getSessionFactory();

        ArrayList<EntityInformation> entitiesList = new ArrayList<EntityInformation>();

        Map<String, ClassMetadata> allClassMetaData = sessionFactory.getAllClassMetadata();
        for (String entityname : allClassMetaData.keySet()) {
            if (!gps.hasMappingForEntityForIndex((entityname))) {
                if (log.isDebugEnabled()) {
                    log.debug("Entity [" + entityname + "] does not have compass mapping, filtering it out");
                }
                continue;
            }

            ClassMetadata classMetadata = (ClassMetadata) allClassMetaData.get(entityname);
            if (shouldFilter(entityname, classMetadata, allClassMetaData, device)) {
                continue;
            }
            Class<?> clazz = classMetadata.getMappedClass();
            ResourceMapping resourceMapping = gps.getMappingForEntityForIndex(entityname);
            EntityInformation entityInformation = new EntityInformation(clazz, entityname,
                    new HibernateJpaQueryProvider(clazz, entityname), resourceMapping.getSubIndexHash().getSubIndexes());
            entitiesList.add(entityInformation);
            if (log.isDebugEnabled()) {
                log.debug("Entity [" + entityname + "] will be indexed");
            }
        }
        return entitiesList.toArray(new EntityInformation[entitiesList.size()]);
    }

    /**
     * Returns <code>true</code> if the entity name needs to be filtered.
     *
     * <p>Implementation filteres out inherited hibernate mappings, since the select query
     * for the base class will cover any inherited classes as well.
     *
     * <p>Note, that this method is called after it has been verified that the class has
     * Compass mappings (either directly, or indirectly by an interface or a super class).
     *
     * @param entityname    The name of the entity
     * @param classMetadata The Hibernate class meta data.
     * @param device        The Jpa Gps device
     * @return <code>true</code> if the entity should be filtered out, <code>false</code> if not.
     */
    protected boolean shouldFilter(String entityname, ClassMetadata classMetadata, Map<?, ?> allClassMetaData,
                                   JpaGpsDevice device) {
        /** Class<?> clazz = **///classMetadata.getMappedClass();
        // if it is inherited, do not add it to the classes to index, since the "from [entity]"
        // query for the base class will return results for this class as well
        if (classMetadata.isInherited()) {
            String superClassEntityName = ((AbstractEntityPersister) classMetadata).getMappedSuperclass();
            ClassMetadata superClassMetadata = (ClassMetadata) allClassMetaData.get(superClassEntityName);
            Class<?> superClass = superClassMetadata.getMappedClass();
            // only filter out classes that their super class has compass mappings
            if (superClass != null
                    && ((CompassGpsInterfaceDevice) device.getGps()).hasMappingForEntityForIndex(superClass)) {
                if (log.isDebugEnabled()) {
                    log.debug("Entity [" + entityname + "] is inherited and super class ["
                            + superClass + "] has compass mapping, filtering it out");
                }
                return true;
            }
        }
        return false;
    }
}