package com.srai.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** Person data model. */
@Entity
@Table(name = "people")
public class Person extends CommonBaseModel {

  /** First name. */
  private String firstName;

  /** Last name. */
  private String lastName;


  @ManyToOne(cascade = { CascadeType.ALL })
  @JoinColumn(name = "manager_id")
  Person manager;

  @OneToMany(mappedBy = "manager", cascade = { CascadeType.ALL })
  List<Person> subordinates = new ArrayList<Person>();

  /** First name getter. */
  public String getFirstName() {
    return this.firstName;
  }

  /** First name setter. */
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  /** Last name getter. */
  public String getLastName() {
    return this.lastName;
  }

  /** Last name setter. */
  public void setLastName(final String lastname) {
    this.lastName = lastname;
  }

  public Person getManager() {
    return manager;
  }

  public void setManager(Person manager) {
    this.manager = manager;
  }

  public List<Person> getSubordinates() {
    return subordinates;
  }

  public void setSubordinates(List<Person> subordinates) {
    this.subordinates = subordinates;
  }

  /** String representation. */
  @Override
  public String toString() {
    return "Person [firstName=" + this.firstName + ", lastName=" + this.lastName
        + "]";
  }

}
