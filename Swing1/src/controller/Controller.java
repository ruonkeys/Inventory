package controller;

import gui.FormEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {
	
	Database db = new Database();
	
	public List<Person> getPeople()
	{
		return db.getPeople();
	}
	
	public void removePerson(int index)
	{
		db.removePerson(index);
	}
	
	public void addPerson(FormEvent e)
	{
		String name = e.getName();
		String occupation = e.getOccupation();
		int ageCatId = e.getAgeCategory();
		String empCat = e.getEmploymentCategory();
		boolean isUs = e.isUsCitizen();
		String taxId = e.getTaxId();
		String gender = e.getGender();
		
		AgeCategory ageCategory = null;
		switch(ageCatId)
		{
		case 0: ageCategory = AgeCategory.child;
		break;
		case 1: ageCategory = AgeCategory.adult;
		break;
		case 2: ageCategory = AgeCategory.senior;
		break;
		}
		
		EmploymentCategory empCategory;
		if(empCat.equals("employed"))
		{
			empCategory = EmploymentCategory.employed;
		}
		else if(empCat.equals("self-employed"))
		{
			empCategory = EmploymentCategory.selfEmployed;
		}
		else if(empCat.equals("unemployed"))
		{
			empCategory = EmploymentCategory.unEmployed;
		}
		else
		{
			empCategory = EmploymentCategory.other;
		}
		Gender genderCat = null;
		if(gender.equals("male"))
		{
			genderCat = Gender.male;
		}
		else if(gender.equals("female"))
		{
			genderCat = Gender.female;
		}
		
		Person person = new Person(name, occupation, ageCategory, empCategory, 
				taxId, isUs, genderCat);
		
		db.addPeople(person);
	}
	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}
	
	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}
}
