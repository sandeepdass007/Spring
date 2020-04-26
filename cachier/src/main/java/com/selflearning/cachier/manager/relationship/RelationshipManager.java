package com.selflearning.cachier.manager.relationship;

import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.selflearning.cachier.CacheIdentifier;
import com.selflearning.cachier.exception.InvalidCacheIdentifierException;
import com.selflearning.cachier.manager.Cachier;
import com.selflearning.cachier.manager.relationship.exception.InvalidRelationshipIdentifierException;
import com.selflearning.cachier.relationship.RelationshipAccount;
import com.selflearning.cachier.relationship.RelationshipIdentifier;

@Component
public class RelationshipManager {

	@Autowired
	private Cachier cachier;
	
	private final HashMap<RelationshipIdentifier, RelationshipAccount> accounts = new HashMap<RelationshipIdentifier, RelationshipAccount>();

	private RelationshipIdentifier createRelationshipAccount() {
		RelationshipAccount relationshipAccount = new RelationshipAccount();
		final RelationshipIdentifier relationshipIdentifier = relationshipAccount.getRelationshipIdentifier();
		accounts.put(relationshipIdentifier, relationshipAccount);
		return relationshipIdentifier;
	}
	
	public boolean addRelationship(CacheIdentifier cacheIdentifier1, CacheIdentifier cacheIdentifier2) throws InvalidRelationshipIdentifierException, InvalidCacheIdentifierException {
		RelationshipIdentifier relationshipIdentifier = cachier.getRelationshipAccount(cacheIdentifier1);
		if(relationshipIdentifier == null) {
			relationshipIdentifier = cachier.getRelationshipAccount(cacheIdentifier2);
		}
		if(relationshipIdentifier == null) {
			relationshipIdentifier = createRelationshipAccount();
			cachier.addRelationshipAccount(cacheIdentifier1, relationshipIdentifier);
			cachier.addRelationshipAccount(cacheIdentifier2, relationshipIdentifier);
		}
		final RelationshipAccount relationshipAccount = accounts.get(relationshipIdentifier);
		if(relationshipAccount == null) {
			throw new InvalidRelationshipIdentifierException(relationshipIdentifier);
		}
		return relationshipAccount.addRelationship(cacheIdentifier1) && relationshipAccount.addRelationship(cacheIdentifier2);
	}

	public HashSet<CacheIdentifier> getAllRelationships(RelationshipIdentifier relationshipIdentifier) throws InvalidRelationshipIdentifierException {
		final RelationshipAccount relationshipAccount = accounts.get(relationshipIdentifier);
		if(relationshipAccount == null) {
			throw new InvalidRelationshipIdentifierException(relationshipIdentifier);
		}
		return relationshipAccount.getAllRelationships();
	}
	
	public HashSet<CacheIdentifier> getAllRelationships(CacheIdentifier cacheIdentifier) throws InvalidRelationshipIdentifierException, InvalidCacheIdentifierException {
		final RelationshipIdentifier relationshipIdentifier = cachier.getRelationshipAccount(cacheIdentifier);
		if(relationshipIdentifier == null) {
			throw new InvalidRelationshipIdentifierException(relationshipIdentifier);
		}
		return getAllRelationships(relationshipIdentifier);
	}
}