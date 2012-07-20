package com.ammob.passport.compass.gps.device.jpa.lifecycle;

import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;

import org.compass.gps.device.jpa.AbstractDeviceJpaEntityListener;
import org.compass.gps.device.jpa.JpaGpsDevice;
import org.compass.gps.device.jpa.JpaGpsDeviceException;
import org.compass.gps.device.jpa.lifecycle.JpaEntityLifecycleInjector;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerGroup;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.spi.ServiceRegistryImplementor;

public class HibernateJpaEntityLifecycleInjector implements JpaEntityLifecycleInjector {

    public static class HibernateEventListener extends AbstractDeviceJpaEntityListener implements PostInsertEventListener,
            PostUpdateEventListener, PostDeleteEventListener {
    	
		private static final long serialVersionUID = 1L;
		private JpaGpsDevice device;

        public HibernateEventListener(JpaGpsDevice device) {
            this.device = device;
        }

        protected JpaGpsDevice getDevice() {
            return this.device;
        }

        public void onPostInsert(PostInsertEvent postInsertEvent) {
            postPersist(postInsertEvent.getEntity());
        }

        public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
            postUpdate(postUpdateEvent.getEntity());
        }

        public void onPostDelete(PostDeleteEvent postDeleteEvent) {
            postRemove(postDeleteEvent.getEntity());
        }
    }

    protected final boolean registerPostCommitListeneres;

    public HibernateJpaEntityLifecycleInjector() {
        this(false);
    }

    /**
     * Creates a new lifecycle injector. Allows to control if the insert/update/delete
     * even listeners will be registered with post commit listeres (flag it <code>true</code>)
     * or with plain post events (triggered based on Hibrenate flushing logic).
     *
     * @param registerPostCommitListeneres <code>true</code> if post commit listeners will be
     * registered. <code>false</code> for plain listeners.
     */
    public HibernateJpaEntityLifecycleInjector(boolean registerPostCommitListeneres) {
        this.registerPostCommitListeneres = registerPostCommitListeneres;
    }

    public boolean requireRefresh() {
        return false;
    }

    public void injectLifecycle(EntityManagerFactory entityManagerFactory, JpaGpsDevice device)
            throws JpaGpsDeviceException {
        HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) entityManagerFactory;
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) hibernateEntityManagerFactory.getSessionFactory();
        ServiceRegistryImplementor serviceRegistry = sessionFactory.getServiceRegistry();
        EventListenerRegistry listenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

        //EventListeners eventListeners = sessionFactory.getEventListeners();

        Object hibernateEventListener = doCreateListener(device);

        if (hibernateEventListener instanceof PostInsertEventListener) {
        	EventListenerGroup<PostInsertEventListener> postInsertEventListeners;
            if (registerPostCommitListeneres) {
                //postInsertEventListeners = eventListeners.getPostCommitInsertEventListeners();
                postInsertEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_INSERT);
            } else {
                //postInsertEventListeners = eventListeners.getPostInsertEventListeners();
            	postInsertEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_INSERT);
            }
           PostInsertEventListener[] tempPostInsertEventListeners = new PostInsertEventListener[postInsertEventListeners.count() + 1];
            //TODO 
            //System.arraycopy(postInsertEventListeners, 0, tempPostInsertEventListeners, 0, postInsertEventListeners.count());
            new ArrayList<PostInsertEventListener>().toArray(tempPostInsertEventListeners);
            tempPostInsertEventListeners[postInsertEventListeners.count()] = (PostInsertEventListener) hibernateEventListener;
            if (registerPostCommitListeneres) {
            	//eventListeners.setPostCommitInsertEventListeners(tempPostInsertEventListeners);
            	//listenerRegistry.setListeners(EventType.POST_COMMIT_INSERT, tempPostInsertEventListeners);
            	listenerRegistry.appendListeners(EventType.POST_COMMIT_INSERT, (PostInsertEventListener) hibernateEventListener);
            } else {
                //eventListeners.setPostInsertEventListeners(tempPostInsertEventListeners);
            	//listenerRegistry.setListeners(EventType.POST_INSERT, tempPostInsertEventListeners);
            	listenerRegistry.appendListeners(EventType.POST_INSERT, (PostInsertEventListener) hibernateEventListener);
            }
        }

        if (hibernateEventListener instanceof PostUpdateEventListener) {
        	EventListenerGroup<PostUpdateEventListener> postUpdateEventListeners;
            if (registerPostCommitListeneres) {
                postUpdateEventListeners =  listenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE);
            } else {
                //postUpdateEventListeners = eventListeners.getPostUpdateEventListeners();
            	postUpdateEventListeners =  listenerRegistry.getEventListenerGroup(EventType.POST_UPDATE);
            }
            PostUpdateEventListener[] tempPostUpdateEventListeners = new PostUpdateEventListener[postUpdateEventListeners.count() + 1];
            //System.arraycopy(postUpdateEventListeners, 0, tempPostUpdateEventListeners, 0, postUpdateEventListeners.count());
            new ArrayList<PostUpdateEventListener>().toArray(tempPostUpdateEventListeners);
            tempPostUpdateEventListeners[postUpdateEventListeners.count()] = (PostUpdateEventListener) hibernateEventListener;
            if (registerPostCommitListeneres) {
               // eventListeners.setPostCommitUpdateEventListeners(tempPostUpdateEventListeners);
            	//listenerRegistry.setListeners(EventType.POST_COMMIT_UPDATE, tempPostUpdateEventListeners);
            	listenerRegistry.appendListeners(EventType.POST_COMMIT_UPDATE, (PostUpdateEventListener) hibernateEventListener);
            } else {
                //eventListeners.setPostUpdateEventListeners(tempPostUpdateEventListeners);
            	//listenerRegistry.setListeners(EventType.POST_UPDATE, tempPostUpdateEventListeners);
            	listenerRegistry.appendListeners(EventType.POST_UPDATE, (PostUpdateEventListener) hibernateEventListener);
            }
        }

        if (hibernateEventListener instanceof PostDeleteEventListener) {
        	EventListenerGroup<PostDeleteEventListener> postDeleteEventListeners;
            if (registerPostCommitListeneres) {
                //postDeleteEventListeners = eventListeners.getPostCommitDeleteEventListeners();
            	postDeleteEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_DELETE);
            } else {
                //postDeleteEventListeners = eventListeners.getPostDeleteEventListeners();
            	postDeleteEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_DELETE);
            }
            PostDeleteEventListener[] tempPostDeleteEventListeners = new PostDeleteEventListener[postDeleteEventListeners.count() + 1];
            //System.arraycopy(postDeleteEventListeners, 0, tempPostDeleteEventListeners, 0, postDeleteEventListeners.count());
            new ArrayList<PostDeleteEventListener>().toArray(tempPostDeleteEventListeners);
            tempPostDeleteEventListeners[postDeleteEventListeners.count()] = (PostDeleteEventListener) hibernateEventListener;
            if (registerPostCommitListeneres) {
                //eventListeners.setPostCommitDeleteEventListeners(tempPostDeleteEventListeners);
                //listenerRegistry.setListeners(EventType.POST_COMMIT_DELETE, tempPostDeleteEventListeners);
                listenerRegistry.appendListeners(EventType.POST_COMMIT_DELETE, (PostDeleteEventListener) hibernateEventListener);
            } else {
                //eventListeners.setPostDeleteEventListeners(tempPostDeleteEventListeners);
            	//listenerRegistry.setListeners(EventType.POST_DELETE, tempPostDeleteEventListeners);
            	listenerRegistry.appendListeners(EventType.POST_DELETE, (PostDeleteEventListener) hibernateEventListener);
            }
        }
    }

    public void removeLifecycle(EntityManagerFactory entityManagerFactory, JpaGpsDevice device) throws JpaGpsDeviceException {
        HibernateEntityManagerFactory hibernateEntityManagerFactory =
                (HibernateEntityManagerFactory) entityManagerFactory;
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) hibernateEntityManagerFactory.getSessionFactory();
        //EventListeners eventListeners = sessionFactory.getEventListeners();
        ServiceRegistryImplementor serviceRegistry = sessionFactory.getServiceRegistry();
        EventListenerRegistry listenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
        
        EventListenerGroup<PostInsertEventListener> postInsertEventListeners;
        if (registerPostCommitListeneres) {
            //postInsertEventListeners = eventListeners.getPostCommitInsertEventListeners();
            postInsertEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_INSERT);
        } else {
            //postInsertEventListeners = eventListeners.getPostInsertEventListeners();
        	postInsertEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_INSERT);
        }
        ArrayList<PostInsertEventListener> tempPostInsertEventListeners = new ArrayList<PostInsertEventListener>();
        for (PostInsertEventListener postInsertEventListener : postInsertEventListeners.listeners()) {
            if (!(postInsertEventListener instanceof HibernateEventListener)) {
                tempPostInsertEventListeners.add(postInsertEventListener);
            }
        }
        if (registerPostCommitListeneres) {
        	listenerRegistry.setListeners(EventType.POST_COMMIT_INSERT, tempPostInsertEventListeners.toArray(new PostInsertEventListener[tempPostInsertEventListeners.size()]));
            //eventListeners.setPostCommitInsertEventListeners(tempPostInsertEventListeners.toArray(new PostInsertEventListener[tempPostInsertEventListeners.size()]));
        } else {
            //eventListeners.setPostInsertEventListeners(tempPostInsertEventListeners.toArray(new PostInsertEventListener[tempPostInsertEventListeners.size()]));
        	listenerRegistry.setListeners(EventType.POST_INSERT, tempPostInsertEventListeners.toArray(new PostInsertEventListener[tempPostInsertEventListeners.size()]));
        }

        EventListenerGroup<PostUpdateEventListener> postUpdateEventListeners;
        if (registerPostCommitListeneres) {
            //postUpdateEventListeners = eventListeners.getPostCommitUpdateEventListeners();
        	postUpdateEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE);
        } else {
            //postUpdateEventListeners = eventListeners.getPostUpdateEventListeners();
            postUpdateEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_UPDATE);
        }
        ArrayList<PostUpdateEventListener> tempPostUpdateEventListeners = new ArrayList<PostUpdateEventListener>();
        for (PostUpdateEventListener postUpdateEventListener : postUpdateEventListeners.listeners()) {
            if (!(postUpdateEventListener instanceof HibernateEventListener)) {
                tempPostUpdateEventListeners.add(postUpdateEventListener);
            }
        }
        if (registerPostCommitListeneres) {
            //eventListeners.setPostCommitUpdateEventListeners(tempPostUpdateEventListeners.toArray(new PostUpdateEventListener[tempPostUpdateEventListeners.size()]));
            listenerRegistry.setListeners(EventType.POST_COMMIT_UPDATE, tempPostUpdateEventListeners.toArray(new PostUpdateEventListener[tempPostUpdateEventListeners.size()]));
        } else {
            //eventListeners.setPostUpdateEventListeners(tempPostUpdateEventListeners.toArray(new PostUpdateEventListener[tempPostUpdateEventListeners.size()]));
        	listenerRegistry.setListeners(EventType.POST_UPDATE, tempPostUpdateEventListeners.toArray(new PostUpdateEventListener[tempPostUpdateEventListeners.size()]));
        }

        EventListenerGroup<PostDeleteEventListener> postDeleteEventListeners;
        if (registerPostCommitListeneres) {
            //postDeleteEventListeners = eventListeners.getPostCommitDeleteEventListeners();
        	postDeleteEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_DELETE);
        } else {
            //postDeleteEventListeners = eventListeners.getPostDeleteEventListeners();
        	postDeleteEventListeners = listenerRegistry.getEventListenerGroup(EventType.POST_DELETE);
        }
        ArrayList<PostDeleteEventListener> tempPostDeleteEventListeners = new ArrayList<PostDeleteEventListener>();
        for (PostDeleteEventListener postDeleteEventListener : postDeleteEventListeners.listeners()) {
            if (!(postDeleteEventListener instanceof HibernateEventListener)) {
                tempPostDeleteEventListeners.add(postDeleteEventListener);
            }
        }
        if (registerPostCommitListeneres) {
            //eventListeners.setPostCommitDeleteEventListeners(tempPostDeleteEventListeners.toArray(new PostDeleteEventListener[tempPostDeleteEventListeners.size()]));
        	listenerRegistry.setListeners(EventType.POST_COMMIT_DELETE, tempPostDeleteEventListeners.toArray(new PostDeleteEventListener[tempPostDeleteEventListeners.size()]));
        } else {
            //eventListeners.setPostDeleteEventListeners(tempPostDeleteEventListeners.toArray(new PostDeleteEventListener[tempPostDeleteEventListeners.size()]));
        	listenerRegistry.setListeners(EventType.POST_DELETE, tempPostDeleteEventListeners.toArray(new PostDeleteEventListener[tempPostDeleteEventListeners.size()]));
        }
    }

    protected Object doCreateListener(JpaGpsDevice device) {
        return new HibernateEventListener(device);
    }
}