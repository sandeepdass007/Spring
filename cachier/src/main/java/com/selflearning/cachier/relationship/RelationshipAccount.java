package com.selflearning.cachier.relationship;

import java.util.HashSet;

import com.selflearning.cachier.CacheIdentifier;

final public class RelationshipAccount {

	private RelationshipIdentifier relationshipIdentifier = new RelationshipIdentifier();
	private HashSet<CacheIdentifier> refersToCacheIdentifiers = new HashSet<CacheIdentifier>();
	
	public RelationshipIdentifier getRelationshipIdentifier() {
		return relationshipIdentifier;
	}

	public boolean addRelationship(CacheIdentifier cacheIdentifier) {
		return refersToCacheIdentifiers.add(cacheIdentifier);
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<CacheIdentifier> getAllRelationships() {
		return (HashSet<CacheIdentifier>) refersToCacheIdentifiers.clone();
	}
}
