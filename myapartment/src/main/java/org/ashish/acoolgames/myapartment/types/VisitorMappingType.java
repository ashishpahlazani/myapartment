package org.ashish.acoolgames.myapartment.types;

import java.util.HashMap;
import java.util.Map;

public enum VisitorMappingType {
	RESIDENT(1),
	HOUSEHOLD(2),
	VISITOR(3);
	
	private static Map<Integer, VisitorMappingType> visitorTypeMap = new HashMap<Integer, VisitorMappingType>();
	
	static
	{
		for(VisitorMappingType mappingType: VisitorMappingType.values())
		{
			visitorTypeMap.put(mappingType.getCode(), mappingType);
		}
	}
	
	int code;
	
	private VisitorMappingType(int code)
	{
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static VisitorMappingType convert(Integer code)
	{
		return visitorTypeMap.get(code);
	}
	
}
