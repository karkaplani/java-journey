/*****************************************************************
 * File: PersonPojo.java Course materials (21S) CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import java.util.List;

import databank.model.PersonPojo;

/**
 * Description: API for the database C-R-U-D operations
 */
public interface PersonDao {

	List< PersonPojo> readAllPeople();

	// C
	PersonPojo createPerson( PersonPojo person);

	// R
	PersonPojo readPersonById( int personId);

	// U
	PersonPojo updatePerson( PersonPojo person);

	// D
	void deletePersonById( int personId);

}