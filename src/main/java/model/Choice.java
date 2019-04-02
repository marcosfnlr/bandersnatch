/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raphaelcja
 */
public class Choice {
    private final int idChoice;
    private final String text;
    private final boolean locked;
    private final boolean unique;
    private final int idParagOrigin;
    private final int idParagDest;
    private List<Integer> origins = new ArrayList<Integer>();
    private final String creator; //??

    public Choice(int idChoice, String text, boolean locked, boolean unique, int idParagOrigin, int idParagDest, String creator) {
        this.idChoice = idChoice;
        this.text = text;
        this.locked = locked;
        this.unique = unique;
        this.idParagOrigin = idParagOrigin;
        this.idParagDest = idParagDest;
        this.creator = creator;
        origins.add(idParagOrigin);
    }

    public int getIdChoice() {
        return idChoice;
    }

    public String getText() {
        return text;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isUnique() {
        return unique;
    }

    public int getIdParagOrigin() {
        return idParagOrigin;
    }

    public int getIdParagDest() {
        return idParagDest;
    }

    public List<Integer> getOrigins() {
        return origins;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "Choice{" + "idChoice=" + idChoice + ", text=" + text + ", locked=" + locked + ", unique=" + unique + ", idParagOrigin=" + idParagOrigin + ", idParagDest=" + idParagDest + ", origins=" + origins + ", creator=" + creator + '}';
    }
    
    

    
    
    
}
