package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;


	public Map<PartType, Integer> getMissingPartsMap() {
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */

		// Initialize the required parts. This should be passed in as a configuration since the requirement can change.
		HashMap<PartType, Integer> requiredParts = new HashMap<PartType, Integer>();
		requiredParts.put(PartType.ENGINE, new Integer(1));
		requiredParts.put(PartType.ELECTRICAL, new Integer(1));
		requiredParts.put(PartType.FUEL_FILTER, new Integer(1));
		requiredParts.put(PartType.OIL_FILTER, new Integer(1));
		requiredParts.put(PartType.TIRE, new Integer(4));

		// ENGINE, ELECTRICAL, TIRE, FUEL_FILTER, OIL_FILTER;
		Iterator<Part> itParts = parts.iterator();
		while (itParts.hasNext()) {
			Part prt = (Part) itParts.next();
			PartType prtType = prt.getType();
			if (prtType != null) {
				Integer count = (Integer) requiredParts.get(prtType);
				if ( count > 1 ) {
					requiredParts.put(prtType, count - 1);
				} else if (count == 1) {
					requiredParts.remove(prtType);
				}
			}
		}
		return requiredParts;
	}

	public boolean isAllPartsWorkingCondition() {
		if (parts == null) {
			return true;
		}
		Iterator<Part> itParts = parts.iterator();
		while (itParts.hasNext()) {
			Part prt = (Part) itParts.next();
			if (prt == null || !prt.isInWorkingCondition()) {
				return false;
			}
		}
		return true;
	}

	public Map<PartType, ConditionType> getNonWorkingConditionMap() {
		if (parts == null) {
			return null;
		}
		HashMap<PartType, ConditionType> workingConditionMap = new HashMap<PartType, ConditionType>();
		Iterator<Part> itParts = parts.iterator();
		while (itParts.hasNext()) {
			Part prt = (Part) itParts.next();
			if (prt != null && !prt.isInWorkingCondition()) {
				workingConditionMap.put(prt.getType(), prt.getCondition());
			}
		}
		return workingConditionMap;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
