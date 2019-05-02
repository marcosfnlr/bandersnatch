/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 * @author raphaelcja
 */
public class Account {

    private final String idAccount;
    private final String password;
    private final String lastName;
    private final String firstName;

    public Account(String idAccount, String password, String lastName, String firstName) {
        this.idAccount = idAccount;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getNameLabel() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return "Account{" + "idAccount=" + idAccount + ", lastName=" + lastName + ", firstName=" + firstName + '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return idAccount.equals(account.idAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAccount);
    }
}
