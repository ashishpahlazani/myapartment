package org.ashish.acoolgames.myapartment.types;

public enum VisitorMappingType {
	RESIDENT(1),
	HOUSEHOLD(2),
	VISITOR(3);
	
	int code;
	
	private VisitorMappingType(int code)
	{
		this.code = code;
	}
}
