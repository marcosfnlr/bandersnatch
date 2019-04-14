/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author raphaelcja
 */
public class Choice {
    private int idChoice;
    private String text;
    private boolean locked;
    private boolean onlyChoice;
    private boolean condShouldPass;
    private Paragraph paragOrigin;
    private Paragraph paragDest;
    private Paragraph paragCond;

    public Choice(int idChoice, String text, boolean locked, boolean onlyChoice, boolean condShouldPass, Paragraph paragOrigin, Paragraph paragDest, Paragraph paragCond) {
        this.idChoice = idChoice;
        this.text = text;
        this.locked = locked;
        this.onlyChoice = onlyChoice;
        this.condShouldPass = condShouldPass;
        this.paragOrigin = paragOrigin;
        this.paragDest = paragDest;
        this.paragCond = paragCond;
    }

    public Choice(String text, boolean locked, boolean onlyChoice, boolean condShouldPass, Paragraph paragOrigin, Paragraph paragDest, Paragraph paragCond) {
        this.text = text;
        this.locked = locked;
        this.onlyChoice = onlyChoice;
        this.condShouldPass = condShouldPass;
        this.paragOrigin = paragOrigin;
        this.paragDest = paragDest;
        this.paragCond = paragCond;
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

    public boolean isOnlyChoice() {
        return onlyChoice;
    }

    public boolean isCondShouldPass() {
        return condShouldPass;
    }

    public Paragraph getParagOrigin() {
        return paragOrigin;
    }

    public Paragraph getParagDest() {
        return paragDest;
    }

    public Paragraph getParagCond() {
        return paragCond;
    }

    @Override
    public String toString() {
        return "Choice{" + "idChoice=" + idChoice + ", text=" + text + ", locked=" + locked + ", onlyChoice=" + onlyChoice + ", condShouldPass=" + condShouldPass  + ", paragOrigin=" + paragOrigin + ", paragDest=" + paragDest + ", paragCond=" + paragCond + '}';
    }
    
    

    
    
    
}
