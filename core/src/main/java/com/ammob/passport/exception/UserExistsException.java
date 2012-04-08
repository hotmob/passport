package com.ammob.passport.exception;

import java.util.HashSet;
import java.util.Set;

import com.ammob.passport.enumerate.StateEnum;


/**
 * An exception that is thrown by classes wanting to trap unique 
 * constraint violations.  This is used to wrap Spring's 
 * DataIntegrityViolationException so it's checked in the web layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class UserExistsException extends Exception {
	
    private static final long serialVersionUID = 4050482305178810162L;
    
    private final Set<StateEnum> types = new HashSet<StateEnum>();

    /**
     * Constructor for UserExistsException.
     *
     * @param message exception message
     */
    public UserExistsException(final String message) {
    	this(message, StateEnum.USERNAME_EXISTENCE);
    }
    
    /**
     * Constructor for UserExistsException.
     *
     * @param message exception message
     */
    public UserExistsException(final String message, StateEnum type) {
    	super(message);
    	types.add(type);
    }
    
    /**
     * Constructor for UserExistsException.
     *
     * @param message exception message
     */
    public UserExistsException(final String message, final Set<StateEnum> types) {
        super(message);
        this.types.addAll(types);
    }
    
    /**
     * return exception type
     * @return
     */
    public StateEnum [] getTypes() {
    	StateEnum [] typeArray = new StateEnum [types.size()];
    	return types.toArray(typeArray);
    }
    
    /**
     * return exception type
     * @return
     */
    public boolean isContainsType(StateEnum type) {
    	return types.contains(type);
    }
}
