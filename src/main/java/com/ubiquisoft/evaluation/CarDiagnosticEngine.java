package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {
		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 *
		 *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 *
		 *      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 *
		 *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 *
		 * Important:
		 *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */

		// step 1: Validate the 3 data fields are present
		if (car == null) {
			System.out.println("Car data is incomplete. Must have Year, Make, Model info.");
			return;
		}
		StringBuilder strb = new StringBuilder("The following data are missing: ");
		boolean isValidateData = true;
		if (car.getYear() == null) {
			strb.append(" YEAR");
			isValidateData = false;
		}
		if (car.getMake() == null) {
			strb.append(" MAKE");
			isValidateData = false;
		}
		if (car.getModel() == null) {
			strb.append(" MODEL");
			isValidateData = false;
		}
		if (!isValidateData) {
			System.out.println(strb.toString());
			return;
		}

		// step 2: Validate that no parts are missing
		Map<PartType, Integer> missingParts = car.getMissingPartsMap();
		if (missingParts != null && missingParts.size() > 0) {
			for (Map.Entry<PartType, Integer> entry : missingParts.entrySet()) {
				printMissingPart(entry.getKey(), entry.getValue());
			}
			return;
		}

		// step 3: Validate that all parts are in working condition
		Map<PartType, ConditionType> nonWorkingParts = car.getNonWorkingConditionMap();
		if (nonWorkingParts != null && nonWorkingParts.size() > 0) {
			for (Map.Entry<PartType, ConditionType> entry : nonWorkingParts.entrySet()) {
				printDamagedPart(entry.getKey(), entry.getValue());
			}
			return;
		}

		System.out.println("Validation is successful. Your car is ready to hit the road");
	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
