package com.selflearning.cachier.manager.relationship.exception;

import com.selflearning.cachier.relationship.RelationshipIdentifier;

public class InvalidRelationshipIdentifierException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3223845967235439362L;
	
	private RelationshipIdentifier relationshipIdentifier;
	
	public InvalidRelationshipIdentifierException(RelationshipIdentifier relationshipIdentifier) {
		super();
		this.relationshipIdentifier = relationshipIdentifier;
	}
	
	@Override
	public String toString() {
		return "Relationship Identifier is invalid: " + relationshipIdentifier;
	}
}
